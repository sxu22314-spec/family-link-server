package com.example.linkfamily.Service;

import com.example.linkfamily.Model.Dto.FamilyPhotoRequest;
import com.example.linkfamily.Model.Vo.FamilyPhotoVO;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface FamilyPhotoService {

    /**
     * Query photos with pagination and filtering
     */
    List<FamilyPhotoVO> queryPhotos(
            int page,
            int pageSize,
            String theme,
            LocalDate dateFrom,
            LocalDate dateTo
    );

    /**
     * Get total count of photos
     */
    int getPhotoCount(String theme, LocalDate dateFrom, LocalDate dateTo);

    /**
     * Upload a photo
     */
    FamilyPhotoVO uploadPhoto(MultipartFile file, FamilyPhotoRequest request);

    /**
     * Delete a photo by id
     */
    void deletePhoto(Long id);
}
