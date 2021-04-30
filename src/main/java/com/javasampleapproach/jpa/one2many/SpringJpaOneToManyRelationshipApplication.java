package com.javasampleapproach.jpa.one2many;
 
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.javasampleapproach.jpa.one2many.karaulova.Static;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
import com.javasampleapproach.jpa.one2many.model.Company;
import com.javasampleapproach.jpa.one2many.model.Product;
import com.javasampleapproach.jpa.one2many.repository.CompanyRepository;
import com.javasampleapproach.jpa.one2many.repository.ProductRepository;
 
 
@SpringBootApplication
public class SpringJpaOneToManyRelationshipApplication implements CommandLineRunner{
    
    @Autowired
    CompanyRepository companyRepository;
     
    @Autowired
    ProductRepository productRepository;

//    @Autowired
//    EntityManager em;
 
    public static void main(String[] args) {
    	SpringApplication.run(SpringJpaOneToManyRelationshipApplication.class, args);
    }
 
    
    @Override
    public void run(String... arg0) throws Exception {
        Scanner sc = new Scanner(System.in);
        String command = "";
        while (true) {
            System.out.println("Введите команду");
            command = sc.nextLine();
            if (command.equalsIgnoreCase(Static.EXIT_COMMAND)) {
                break;
            } else if (command.equalsIgnoreCase(Static.SHOW_COMMAND)) {
                showData();
            } else if (command.equalsIgnoreCase(Static.DEL_COMMAND)) {
                clearData();
            } else if (command.equalsIgnoreCase(Static.SAVE_COMMAND)) {
                saveData();
            } else if (command.equalsIgnoreCase(Static.TEST)) {
                testLazyInitialization();
            } else if (command.equalsIgnoreCase(Static.UPDATE)) {
                update();
            }
        }
    }
    
    @Transactional
    private void clearData(){
    	companyRepository.deleteAll();
        //productRepository.deleteAll();
    }
    
    @Transactional
    private void saveData(){
        cascadeSave();
    }
    
    /**
     * Save Company objects that include Product list
     */

    private void testLazyInitialization() {
        Company company = companyRepository.findAll().stream().findFirst().orElse(null);
        System.out.println("Company");
        System.out.println(company.toString());
        company.getProducts().forEach(System.out::println);
    }

    private void update() {
        List<Company> companyLst = companyRepository.findAll();
        Company company = companyLst.get(0);
        List<Product> products = company.getProducts();
        products.remove(0);
        company.setProducts(products);
        companyRepository.save(company);
    }

    private void cascadeSave() {
        Company apple = new Company("Android");

        Product iphone7 = new Product("Iphone 7", apple);
        Product iPadPro = new Product("IPadPro", apple);
        Product iMac = new Product("iMac", apple);

        apple.setProducts(new ArrayList<>(){{
            add(iphone7);
            add(iPadPro);
            add(iMac);
        }});

        companyRepository.save(apple);
    }

//    private void saveDataWithApproach1(){
//        Company apple = new Company("Apple");
//        Company samsung = new Company("Samsung");
//
//        Product iphone7 = new Product("Iphone 7", apple);
//        Product iPadPro = new Product("IPadPro", apple);
//
//        Product galaxyJ7 = new Product("GalaxyJ7", samsung);
//        Product galaxyTabA = new Product("GalaxyTabA", samsung);
//
//        apple.setProducts(new HashSet<Product>(){{
//            add(iphone7);
//            add(iPadPro);
//        }});
//
//        samsung.setProducts(new HashSet<Product>(){{
//            add(galaxyJ7);
//            add(galaxyTabA);
//        }});
//
//        // save companies
//        companyRepository.save(apple);
//        companyRepository.save(samsung);
//    }

    
    /**
     * Save company first (not include product list). Then saving products which had attached a company for each.  
     */
    private void saveDataWithApproach2(){
    	
    	/*
    	 * Firstly persist companies (not include product list)
    	 */
        Company apple = new Company("Apple");
        Company samsung = new Company("Samsung");
        
        //save companies
        companyRepository.save(apple);
        companyRepository.save(samsung);
        
        /*
         * Then store products with had persisted companies.
         */
    	Product iphone7 = new Product("Iphone 7", apple);
        Product iPadPro = new Product("IPadPro", apple);
        
        Product galaxyJ7 = new Product("GalaxyJ7", samsung);
        Product galaxyTabA = new Product("GalaxyTabA", samsung);
 
        // save products
        productRepository.save(iphone7);
        productRepository.save(iPadPro);
        
        productRepository.save(galaxyJ7);
        productRepository.save(galaxyTabA);
    }
    
    @Transactional
    private void showData(){
    	// get All data
        List<Product> productLst = productRepository.findAll();
        List<Company> companyLst = companyRepository.findAll();

        System.out.println("===================Company List:==================");
        companyLst.forEach(System.out::println);

        //List<Product> products = productRepository.findAll();//findAllByCompanyId(10);

        System.out.println("===================Product List:==================");
        productLst.forEach(System.out::println);
    }
    
}