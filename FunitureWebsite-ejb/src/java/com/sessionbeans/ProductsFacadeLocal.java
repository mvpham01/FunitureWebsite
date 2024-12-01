/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Products;
import com.entitybeans.Subcategories;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author BAOTHI
 */
@Local
public interface ProductsFacadeLocal {

    void create(Products products);

    void edit(Products products);

    void remove(Products products);

    Products find(Object id);

    List<Products> findAll();

    List<Products> findRange(int[] range);

    int count();
      public List<Products> findBySubcategoryId(Subcategories subcategory);

    

    public List<Products> findProducts(String searchKeyword, List<String> selectedSubcategoryId, String sortBy, String sortByName);

 
}
