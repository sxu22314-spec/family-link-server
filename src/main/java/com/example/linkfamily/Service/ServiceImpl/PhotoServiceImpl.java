package com.example.linkfamily.Service.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.linkfamily.Mapper.PhotoMapper;
import com.example.linkfamily.Model.Entity.Photo;
import com.example.linkfamily.Model.Vo.PuzzlePhotoVO;
import com.example.linkfamily.Service.PhotoService;

@Service
public class PhotoServiceImpl extends ServiceImpl<PhotoMapper, Photo> implements PhotoService {

    @Override
    public List<PuzzlePhotoVO> getAllPuzzles() {
        // 替换 lambdaQuery()，改用 baseMapper
        List<Photo> photos = this.baseMapper.selectList(
            new LambdaQueryWrapper<Photo>().eq(Photo::getType, 1)
        );

        return photos.stream()
                .map(photo -> new PuzzlePhotoVO(
                        String.valueOf(photo.getId()),
                        photo.getTitle(),
                        photo.getTheme() == null ? "" : photo.getTheme(),
                        photo.getImageUrl()))
                .toList();
    }
}
