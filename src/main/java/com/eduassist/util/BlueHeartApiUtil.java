package com.eduassist.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BlueHeartApiUtil {

    @Value("${blueheart.api.url}")
    private String apiUrl;

    @Value("${blueheart.api.appId}")
    private String appId;

    @Value("${blueheart.api.appKey}")
    private String appKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 调用OCR API
     * @param imagePath 图片路径
     * @return OCR识别结果（包含文字和位置信息）
     * @throws Exception 异常
     */
    public String callOcrApi(String imagePath) throws Exception {
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            throw new RuntimeException("图片文件不存在");
        }
        byte[] imageBytes = new byte[(int) imageFile.length()];
        try (FileInputStream fis = new FileInputStream(imageFile)) {
            fis.read(imageBytes);
        }
        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);

        // 构建请求参数
        String uri = "/ocr/general_recognition";
        String method = "POST";
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonce = generateNonce(8);
        String businessId = "aigc" + appId;

        // 构建Body
        Map<String, String> body = new HashMap<>();
        body.put("image", imageBase64);
        body.put("pos", "2"); // 返回文字和相对位置信息
        body.put("businessid", businessId);

        // 构建查询参数（无）
        Map<String, String> queryParams = new HashMap<>();
        String canonicalQueryString = generateCanonicalQueryString(queryParams);

        // 构建签名
        String signedHeadersString = "x-ai-gateway-app-id:" + appId + "\n" +
                "x-ai-gateway-timestamp:" + timestamp + "\n" +
                "x-ai-gateway-nonce:" + nonce;
        String signingString = method + "\n" +
                uri + "\n" +
                canonicalQueryString + "\n" +
                appId + "\n" +
                timestamp + "\n" +
                signedHeadersString;
        String signature = generateSignature(appKey, signingString);

        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-AI-GATEWAY-APP-ID", appId);
        headers.add("X-AI-GATEWAY-TIMESTAMP", timestamp);
        headers.add("X-AI-GATEWAY-NONCE", nonce);
        headers.add("X-AI-GATEWAY-SIGNED-HEADERS", "x-ai-gateway-app-id;x-ai-gateway-timestamp;x-ai-gateway-nonce");
        headers.add("X-AI-GATEWAY-SIGNATURE", signature);

        // 构建请求体
        StringBuilder formData = new StringBuilder();
        for (Map.Entry<String, String> entry : body.entrySet()) {
            if (formData.length() > 0) {
                formData.append("&");
            }
            formData.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()))
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
        }

        // 发送请求
        String url = apiUrl + uri;
        HttpEntity<String> requestEntity = new HttpEntity<>(formData.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return parseOcrResponse(response.getBody());
        } else {
            throw new RuntimeException("OCR API调用失败，状态码: " + response.getStatusCodeValue());
        }
    }

    /**
     * 解析OCR响应
     * @param response 响应字符串
     * @return OCR结果（纯文本）
     * @throws Exception 异常
     */
    private String parseOcrResponse(String response) throws Exception {
        JsonNode jsonNode = objectMapper.readTree(response);
        int errorCode = jsonNode.get("error_code").asInt();
        if (errorCode != 0) {
            throw new RuntimeException("OCR API返回错误: " + jsonNode.get("error_msg").asText());
        }
        JsonNode resultNode = jsonNode.get("result");
        StringBuilder ocrText = new StringBuilder();
        JsonNode wordsArray = resultNode.get("words");
        for (JsonNode wordNode : wordsArray) {
            ocrText.append(wordNode.get("words").asText()).append("\n");
        }
        return ocrText.toString();
    }

    /**
     * 生成随机Nonce字符串
     * @param length 字符串长度
     * @return 随机字符串
     */
    private String generateNonce(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        return random.ints(length, 0, characters.length())
                .mapToObj(characters::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }

    /**
     * 生成规范查询字符串
     * @param params 查询参数
     * @return 规范化的查询字符串
     * @throws Exception 异常
     */
    private String generateCanonicalQueryString(Map<String, String> params) throws Exception {
        if (params == null || params.isEmpty()) {
            return "";
        }
        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);
        StringBuilder sb = new StringBuilder();
        for (String key : sortedKeys) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(URLEncoder.encode(key, StandardCharsets.UTF_8.toString()))
                    .append("=")
                    .append(URLEncoder.encode(params.get(key), StandardCharsets.UTF_8.toString()));
        }
        return sb.toString();
    }

    /**
     * 生成签名
     * @param appKey 应用密钥
     * @param signingString 签名字符串
     * @return 签名
     * @throws Exception 异常
     */
    private String generateSignature(String appKey, String signingString) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(appKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKey);
        byte[] hash = mac.doFinal(signingString.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }
}
