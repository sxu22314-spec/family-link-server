package com.example.linkfamily.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.linkfamily.Model.Entity.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface FamilyPhotoMapper extends BaseMapper<Photo> {

    /**
     * Query photos with filters and pagination
     */
    List<Photo> queryByFilters(
            @Param("theme") String theme,
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    /**
     * Count photos by filters
     */
    int countByFilters(
            @Param("theme") String theme,
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo
    );

    /**
     * Query photos by date range
     */
    List<Photo> queryByDateRange(
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo
    );

    /**
     * Query photos by theme
     */
    List<Photo> queryByTheme(@Param("theme") String theme);

    /**
     * Find photo by id
     */
    Photo findById(@Param("id") Long id);

    /**
     * Delete photo by id
     */
    int deleteById(@Param("id") Long id);
}
