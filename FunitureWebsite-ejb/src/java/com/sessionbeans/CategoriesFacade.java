/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Categories;
import com.entitybeans.Subcategories;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author BAOTHI
 */
@Stateless
public class CategoriesFacade extends AbstractFacade<Categories> implements CategoriesFacadeLocal {

    @PersistenceContext(unitName = "FunitureWebsite-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriesFacade() {
        super(Categories.class);
    }

    @Override
    public List<Subcategories> findSubcategoriesByCategoryId(String categoryId) {
        return em.createQuery("SELECT s FROM Subcategory s WHERE s.category.categoryID = :categoryID")
                .setParameter("categoryID", categoryId)
                .getResultList();
    }
    @Override
    public List<Subcategories> findByCategoryId(Categories category) {
    return em.createQuery("SELECT s FROM Subcategories s WHERE s.categoryID = :category", Subcategories.class)
             .setParameter("category", category)
             .getResultList();
}

}
