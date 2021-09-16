package com.fuvidy.springcache.service;

import com.fuvidy.springcache.entity.GoodEntity;

import java.util.List;

public interface GoodService {
    GoodEntity findById(Long id);

    String insert(GoodEntity good);

    String update(String name, Long price,Long id);

    List<GoodEntity> getAll();
}
