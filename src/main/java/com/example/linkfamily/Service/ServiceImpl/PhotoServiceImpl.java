package com.example.linkfamily.Service.ServiceImpl;

import com.example.linkfamily.Service.MinioService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.linkfamily.Mapper.PhotoMapper;
import com.example.linkfamily.Model.Entity.Photo;
import com.example.linkfamily.Model.Vo.PuzzlePhotoVO;
import com.example.linkfamily.Service.PhotoService;

@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {

    // 从配置文件读取 minio.endpoint
    @Value("${minio.endpoint}")
    private String minioEndpoint;

    // 从配置文件读取 minio.bucket
    @Value("${minio.bucket}")
    private String bucketName;

@Override
    public List<PuzzlePhotoVO> getAllPuzzles() {
        List<Photo> photos = this.baseMapper.selectList(
            new LambdaQueryWrapper<Photo>().eq(Photo::getType, 1)
        );

        // 核心逻辑：拼接基础前缀
        // 结果示例: http://192.168.1.104:9000/family-link/
        String urlPrefix = minioEndpoint + "/" + bucketName + "/";

        return photos.stream()
            .map(photo -> {
                String rawUrl = photo.getImageUrl();
                String fullUrl = rawUrl;

                // 如果原始路径不是以 http 开头，且不为空，则进行拼接
                if (rawUrl != null && !rawUrl.startsWith("http")) {
                    // 处理路径开头的斜杠，防止拼接出两个斜杠 (//)
                    String cleanPath = rawUrl.startsWith("/") ? rawUrl.substring(1) : rawUrl;
                    fullUrl = urlPrefix + cleanPath;
                }

                return new PuzzlePhotoVO(
                    String.valueOf(photo.getId()),
                    photo.getTitle(),
                    photo.getTheme() == null ? "" : photo.getTheme(),
                    fullUrl,
                    photo.getIsLocked()
                );
            })
            .toList();
    }

    @Override
    public boolean updateIsLocked(Long id, Integer isLocked){
        Photo photo = new Photo();
        photo.setId(id);
        photo.setIsLocked(isLocked);
        
        // MyBatis Plus 的方法：根据 ID 更新不为 null 的字段
        return this.updateById(photo);
    }
}
