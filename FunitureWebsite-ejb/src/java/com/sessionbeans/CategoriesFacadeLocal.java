/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Categories;
import com.entitybeans.Subcategories;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author BAOTHI
 */
@Local
public interface CategoriesFacadeLocal {

    void create(Categories categories);

    void edit(Categories categories);

    void remove(Categories categories);

    Categories find(Object id);

    List<Categories> findAll();

    List<Categories> findRange(int[] range);

    int count();
    List<Subcategories> findSubcategoriesByCategoryId(String categoryId);
     public List<Subcategories> findByCategoryId(Categories category);
}
