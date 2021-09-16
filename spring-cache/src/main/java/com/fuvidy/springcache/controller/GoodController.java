package com.fuvidy.springcache.controller;

import com.fuvidy.springcache.entity.GoodEntity;
import com.fuvidy.springcache.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/good")
public class GoodController {
    @Autowired
    private GoodService goodService;

    @GetMapping("/{id}")
    public GoodEntity good(@PathVariable("id") Long id) {
        return goodService.findById(id);
    }

    @PostMapping
    public String inSertGood(@RequestBody GoodEntity good) {
        return goodService.insert(good);
    }

    @PutMapping
    public String updateGood(@RequestParam("name") String name, @RequestParam("price") Long price, @RequestParam("id") Long id) {
        return goodService.update(name, price, id);
    }

    @GetMapping("/all")
    public List<GoodEntity> getGoodList() {
        return goodService.getAll();
    }


}
