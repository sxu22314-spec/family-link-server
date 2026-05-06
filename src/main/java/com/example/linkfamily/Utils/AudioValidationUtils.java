package com.example.linkfamily.Utils;

import com.example.linkfamily.Exception.FileException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public class AudioValidationUtils {

    private static final Set<String> ALLOWED_AUDIO_MIME_TYPES = Set.of(
            "audio/mpeg",
            "audio/wav",
            "audio/x-wav",
            "audio/mp4"
    );

    public static void validateAudioFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new FileException(400, "Audio file cannot be empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_AUDIO_MIME_TYPES.contains(contentType)) {
            throw new FileException(415, "Audio type not supported. Allowed: audio/mpeg, audio/wav, audio/mp4");
        }
    }
}
