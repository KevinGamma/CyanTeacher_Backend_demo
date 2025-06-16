package com.eduassist.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUploadUtil {
    private static final String UPLOAD_DIR = "/uploads/"; // 修改为实际的上传目录路径

    /**
     * 保存上传的文件
     * @param file 上传的文件
     * @return 文件保存路径
     * @throws IOException 如果保存失败
     */
    public static String saveFile(MultipartFile file) throws IOException {
        // 确保上传目录存在
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 生成唯一文件名
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        File dest = new File(UPLOAD_DIR + fileName);

        // 保存文件
        file.transferTo(dest);
        return dest.getAbsolutePath();
    }
}
