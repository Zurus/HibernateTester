package com.javasampleapproach.jpa.one2many.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javasampleapproach.jpa.one2many.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

    //List<Product> findAllByCompanyId(Integer companyId);
}