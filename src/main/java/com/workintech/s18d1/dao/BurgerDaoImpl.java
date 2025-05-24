package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import com.workintech.s18d1.util.BurgerValidation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BurgerDaoImpl implements BurgerDao{

    EntityManager entityManager;

    @Autowired
    public BurgerDaoImpl(EntityManager entityManager){
        this.entityManager  = entityManager;
    }

    @Transactional
    @Override
    public Burger save(Burger burger) {
        entityManager.persist(burger);
        return burger;
    }

    @Override
    public Burger findById(Long id) {
        Burger burger = entityManager.find(Burger.class,id);
        if(burger == null)
            throw new BurgerException("Burger not found: " + id, HttpStatus.NOT_FOUND);
        return burger;
    }

    @Override
    public List<Burger> findAll() {
        TypedQuery<Burger> t = entityManager.createQuery("Select b from Burger b",Burger.class);
        return t.getResultList();
    }

    @Override
    public List<Burger> findByPrice(double price) {
        TypedQuery<Burger> t =
                entityManager.createQuery("Select b from Burger b where b.price > :price order by b.price desc",Burger.class)
                        .setParameter("price",price);
        return t.getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
        TypedQuery<Burger> t =
                entityManager.createQuery("Select b from Burger b where b.breadType = :breadType",Burger.class)
                        .setParameter("breadType",breadType);
        return t.getResultList();
    }

    @Override
    public List<Burger> findByContent(String content) {
        TypedQuery<Burger> t =
                entityManager.createQuery("Select b from Burger b where b.contents like :content",Burger.class)
                        .setParameter("content","%"+content+"%");
        return t.getResultList();
    }

    @Override
    @Transactional
    public Burger update(Burger burger) {
        entityManager.merge(burger);
        return burger;
    }

    @Transactional
    @Override
    public Burger remove(Long id) {
        Burger burger = entityManager.find(Burger.class,id);
        entityManager.remove(burger);
        return burger;
    }
}
