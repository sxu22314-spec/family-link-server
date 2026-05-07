package com.example.linkfamily.Controller;

import com.example.linkfamily.Exception.FileException;
import com.example.linkfamily.Exception.PhotoException;
import com.example.linkfamily.Model.Dto.FamilyPhotoRequest;
import com.example.linkfamily.Model.Vo.FamilyPhotoVO;
import com.example.linkfamily.Response.Response;
import com.example.linkfamily.Service.FamilyPhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/family-moment")
public class FamilyMomentController {

    @Autowired
    private FamilyPhotoService familyPhotoService;

    /**
     * Query photos with pagination and filtering
     * GET /family-moment/photos?page=1&pageSize=10&theme=vacation&dateFrom=2024-01-01&dateTo=2024-12-31
     */
    @GetMapping("/photos")
    public Response<Map<String, Object>> queryPhotos(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String theme
            // @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            // @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
    ) {
        try {
            log.info("Query photos: page={}, pageSize={}, theme={}, dateFrom={}, dateTo={}", 
                    page, pageSize, theme/*, dateFrom, dateTo*/);

            // Validate pagination parameters
            if (page < 1 || pageSize < 1 || pageSize > 100) {
                return new Response<>(400, "Invalid pagination parameters", null);
            }

            // Query photos
            List<FamilyPhotoVO> photos = familyPhotoService.queryPhotos(page, pageSize, theme, null, null);

            // Get total count
            int totalCount = familyPhotoService.getPhotoCount(theme, null, null);

            // Build response data
            Map<String, Object> data = new HashMap<>();
            data.put("photos", photos);
            data.put("totalCount", totalCount);
            data.put("page", page);
            data.put("pageSize", pageSize);
            data.put("totalPages", (totalCount + pageSize - 1) / pageSize);

            return Response.success("Query photos successfully", data);

        } catch (Exception e) {
            log.error("Query photos failed: {}", e.getMessage());
            return new Response<>(500, "Query photos failed: " + e.getMessage(), null);
        }
    }

    /**
     * Upload a photo
     * POST /family-moment/upload
     */
    @PostMapping("/upload")
    public Response<FamilyPhotoVO> uploadPhoto(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute FamilyPhotoRequest request
    ) {
        try {
            log.info("Upload photo: title={}, theme={}, photoDate={}, type={}", 
                    request.getTitle(), request.getTheme(), request.getPhotoDate(), request.getType());

            if (file == null || file.isEmpty()) {
                return new Response<>(400, "File cannot be empty", null);
            }

            // Upload photo
            FamilyPhotoVO photo = familyPhotoService.uploadPhoto(file, request);

            return Response.success("Photo uploaded successfully", photo);

        } catch (FileException e) {
            log.error("File validation failed: {}", e.getMessage());
            return new Response<>(e.getCode(), e.getMessage(), null);
        } catch (Exception e) {
            log.error("Upload photo failed: {}", e.getMessage());
            return new Response<>(500, "Upload photo failed: " + e.getMessage(), null);
        }
    }

    /**
     * Delete a photo by id
     * DELETE /family-moment/photos/{id}
     */
    @DeleteMapping("/photos/{id}")
    public Response<Object> deletePhoto(@PathVariable Long id) {
        try {
            log.info("Delete photo: id={}", id);

            if (id == null || id < 1) {
                return new Response<>(400, "Invalid photo id", null);
            }

            // Delete photo
            familyPhotoService.deletePhoto(id);

            return Response.success("Photo deleted successfully");

        } catch (PhotoException e) {
            log.error("Delete photo failed: {}", e.getMessage());
            return new Response<>(e.getCode(), e.getMessage(), null);
        } catch (Exception e) {
            log.error("Delete photo failed: {}", e.getMessage());
            return new Response<>(500, "Delete photo failed: " + e.getMessage(), null);
        }
    }
}
