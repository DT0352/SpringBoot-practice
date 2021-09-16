package com.fuvidy.springcache.service.impl;

import com.fuvidy.springcache.entity.GoodEntity;
import com.fuvidy.springcache.mapper.GoodMapper;
import com.fuvidy.springcache.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"goodCache"})
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodMapper goodMapper;

    @Override
    @Cacheable(cacheNames = {"good"}, key = "#id") // 查询缓存
    public GoodEntity findById(Long id) {
        if (id != null) {
            System.out.println("若缓存不存在，则执行此方法");
            return goodMapper.findById(id);
        }
        return null;
    }

    @Override
    @CachePut(cacheNames = {"good"}, key = "#good.id") // 新增缓存
    public String insert(GoodEntity good) {
        goodMapper.insert(good);
        return "1";
    }

    @Override
    @CacheEvict(cacheNames = {"good"}, key = "#name")
    public String update(String name, Long price, Long id) {

        return goodMapper.update(name, price, id);
    }

    @Override
    @Cacheable("goodList") // 查询所有缓存
    public List<GoodEntity> getAll() {
        return goodMapper.getAll();
    }
}
