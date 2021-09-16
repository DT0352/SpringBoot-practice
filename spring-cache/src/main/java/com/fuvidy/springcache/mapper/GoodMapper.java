package com.fuvidy.springcache.mapper;

import com.fuvidy.springcache.entity.GoodEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface GoodMapper {
    GoodEntity findById(Long id);

    void insert(GoodEntity good);

    String update(@Param("name") String name, @Param("price") Long price,@Param("id")Long id);

    List<GoodEntity> getAll();
}
