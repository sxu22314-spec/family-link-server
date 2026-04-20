package com.example.linkfamily.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.linkfamily.Model.Entity.Photo;
import com.example.linkfamily.Model.Vo.PuzzlePhotoVO;

public interface PhotoService extends IService<Photo> {
    List<PuzzlePhotoVO> getAllPuzzles();

    boolean updateIsLocked(Long id, Integer isLocked);
}
