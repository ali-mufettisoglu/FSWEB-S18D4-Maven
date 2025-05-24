package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDao;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.util.BurgerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/burger")
public class BurgerController {

    private BurgerDao burgerDao;

    @Autowired
    public BurgerController(BurgerDao burgerDao) {
        this.burgerDao = burgerDao;
    }

    @GetMapping
    public List<Burger> get(){
        return burgerDao.findAll();
    }

    @GetMapping("{id}")
    public Burger get(@PathVariable Long id){
        return burgerDao.findById(id);
    }

    @GetMapping("/price/{price}")
    public List<Burger> getByPrice(@PathVariable Integer price){
         return burgerDao.findByPrice(price);
    }

    @GetMapping("/breadType/{breadType}")
    public List<Burger> getByBread(@PathVariable String breadType){
        BreadType btEnum = BreadType.valueOf(breadType);
        return burgerDao.findByBreadType(btEnum);
    }

    @GetMapping("/content/{content}")
    public List<Burger> getByContent(@PathVariable String content){
        return burgerDao.findByContent(content);
    }

    @PostMapping
    public Burger post(@RequestBody Burger burger){
        BurgerValidation.checkName(burger.getName());
        burgerDao.save(burger);
        return burger;
    }

    @PutMapping
    public Burger put(@RequestBody Burger burger){
        burgerDao.update(burger);
        return burger;
    }

    @DeleteMapping("{id}")
    public Burger delete(@PathVariable Long id){
        return burgerDao.remove(id);
    }


}
