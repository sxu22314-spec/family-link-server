package com.example.linkfamily.Utils;

import com.example.linkfamily.Exception.FileException;
import org.springframework.web.multipart.MultipartFile;

public class FileValidationUtils {

    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png", "gif"};
    private static final String[] ALLOWED_MIME_TYPES = {
            "image/jpeg",
            "image/png",
            "image/gif"
    };

    /**
     * Validate uploaded file
     */
    public static void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileException(400, "File cannot be empty");
        }

        // Check MIME type
        String contentType = file.getContentType();
        if (!isAllowedMimeType(contentType)) {
            throw new FileException(415, "File type not supported. Allowed types: JPG, PNG, GIF");
        }

        // Check file extension
        String filename = file.getOriginalFilename();
        String extension = getFileExtension(filename);
        if (!isAllowedExtension(extension)) {
            throw new FileException(415, "File extension not supported. Allowed: jpg, jpeg, png, gif");
        }
    }

    /**
     * Get file extension
     */
    public static String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }

    /**
     * Check if MIME type is allowed
     */
    private static boolean isAllowedMimeType(String contentType) {
        if (contentType == null) {
            return false;
        }
        for (String type : ALLOWED_MIME_TYPES) {
            if (contentType.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if file extension is allowed
     */
    private static boolean isAllowedExtension(String extension) {
        for (String ext : ALLOWED_EXTENSIONS) {
            if (ext.equals(extension)) {
                return true;
            }
        }
        return false;
    }
}
