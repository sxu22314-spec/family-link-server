package com.example.linkfamily.Service.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.linkfamily.Exception.FileException;
import com.example.linkfamily.Exception.PhotoException;
import com.example.linkfamily.Mapper.FamilyPhotoMapper;
import com.example.linkfamily.Model.Dto.FamilyPhotoRequest;
import com.example.linkfamily.Model.Entity.Photo;
import com.example.linkfamily.Model.Vo.FamilyPhotoVO;
import com.example.linkfamily.Service.FamilyPhotoService;
import com.example.linkfamily.Service.MinioService;
import com.example.linkfamily.Utils.FileValidationUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FamilyPhotoServiceImpl implements FamilyPhotoService {

    @Autowired
    private FamilyPhotoMapper familyPhotoMapper;

    @Autowired
    private MinioService minioService;

    @Override
    public List<FamilyPhotoVO> queryPhotos(int page, int pageSize, String theme, LocalDate dateFrom, LocalDate dateTo) {
        // Calculate offset
        int offset = (page - 1) * pageSize;

        // Query photos
        List<Photo> photos = familyPhotoMapper.queryByFilters(theme, dateFrom, dateTo, offset, pageSize);

        // Convert to VO
        return photos.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public int getPhotoCount(String theme, LocalDate dateFrom, LocalDate dateTo) {
        return familyPhotoMapper.countByFilters(theme, dateFrom, dateTo);
    }

    @Override
    public FamilyPhotoVO uploadPhoto(MultipartFile file, FamilyPhotoRequest request) {
        try {
            // Validate file
            FileValidationUtils.validateFile(file);

            // Upload file to MinIO
            String filePath = minioService.uploadFile(file, file.getOriginalFilename());

            // Create Photo entity
            Photo photo = new Photo();
            photo.setTitle(request.getTitle());
            photo.setTheme(request.getTheme());
            photo.setPhotoDate(request.getPhotoDate());
            photo.setCreateTime(LocalDateTime.now());
            photo.setImageUrl(filePath);
            photo.setType(2); // Type 2 for family-moments
            photo.setIsLocked(0);

            // Save to database
            familyPhotoMapper.insert(photo);

            log.info("Photo uploaded successfully: id={}, title={}", photo.getId(), request.getTitle());

            // Return VO
            return convertToVO(photo);

        } catch (FileException e) {
            log.error("File validation failed: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Upload photo failed: {}", e.getMessage());
            throw new PhotoException(500, "Failed to upload photo: " + e.getMessage());
        }
    }

    @Override
    public void deletePhoto(Long id) {
        try {
            // Fetch photo from database
            Photo photo = familyPhotoMapper.findById(id);
            if (photo == null) {
                throw new PhotoException(404, "Photo not found with id: " + id);
            }

            // Delete from MinIO
            if (photo.getImageUrl() != null && !photo.getImageUrl().isEmpty()) {
                minioService.deleteFile(photo.getImageUrl());
            }

            // Delete from database
            familyPhotoMapper.deleteById(id);

            log.info("Photo deleted successfully: id={}", id);

        } catch (PhotoException e) {
            throw e;
        } catch (Exception e) {
            log.error("Delete photo failed: {}", e.getMessage());
            throw new PhotoException(500, "Failed to delete photo: " + e.getMessage());
        }
    }

    /**
     * Convert Photo entity to FamilyPhotoVO
     */
    private FamilyPhotoVO convertToVO(Photo photo) {
        FamilyPhotoVO vo = new FamilyPhotoVO();
        vo.setId(photo.getId());
        vo.setPhotoUrl(minioService.generatePublicUrl(photo.getImageUrl()));
        vo.setTitle(photo.getTitle());
        vo.setTheme(photo.getTheme());
        vo.setCreateTime(photo.getCreateTime());
        vo.setPhotoDate(photo.getPhotoDate());
        return vo;
    }
}
