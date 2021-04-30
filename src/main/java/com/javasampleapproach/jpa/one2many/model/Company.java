package com.javasampleapproach.jpa.one2many.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.json.JSONArray;
import org.json.JSONObject;

@Entity
@Table(name="company")
public class Company {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
    private String name;
    
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    //@Fetch(FetchMode.JOIN)
    private List<Product> products = Collections.emptyList();
    
    public Company(){
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company(String name){
    	this.name = name;
    }
    
    // name
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    // products
    public void setProducts(List<Product> products){
    	this.products = products;
    }
    
    public List<Product> getProducts(){
    	return this.products;
    }
    
    public String toString(){
    	String info = "";
        JSONObject jsonInfo = new JSONObject();
        jsonInfo.put("name",this.name);
        
//        JSONArray productArray = new JSONArray();
//        if(this.products != null){
//            this.products.forEach(product->{
//                JSONObject subJson = new JSONObject();
//                subJson.put("name", product.getName());
//                productArray.put(subJson);
//            });
//        }
      //  jsonInfo.put("products", productArray);
        info = jsonInfo.toString();
        return info;
    }
}
