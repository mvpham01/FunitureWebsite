/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Subcategories;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author BAOTHI
 */
@Local
public interface SubcategoriesFacadeLocal {

    void create(Subcategories subcategories);

    void edit(Subcategories subcategories);

    void remove(Subcategories subcategories);

    Subcategories find(Object id);

    List<Subcategories> findAll();

    List<Subcategories> findRange(int[] range);

    int count();
     public List<Subcategories> findByCategory(String categoryID);
}
