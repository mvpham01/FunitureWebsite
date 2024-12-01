/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Products;
import com.entitybeans.Subcategories;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author BAOTHI
 */
@Stateless
public class ProductsFacade extends AbstractFacade<Products> implements ProductsFacadeLocal {

    @PersistenceContext(unitName = "FunitureWebsite-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductsFacade() {
        super(Products.class);
    }

    @Override
    public Products find(Object id) {
        return em.find(Products.class, id);
    }

    @Override
    public List<Products> findBySubcategoryId(Subcategories subcategory) {
        return em.createQuery("SELECT p FROM Products p WHERE p.subcategoryID = :subcategory", Products.class)
                .setParameter("subcategory", subcategory)
                .getResultList();
    }

   @Override
public List<Products> findProducts(String searchKeyword, List<String> selectedSubcategoryId, String sortBy, String sortByName) {
    StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Products p WHERE 1=1");

    if (searchKeyword != null && !searchKeyword.isEmpty()) {
        queryBuilder.append(" AND p.productName LIKE :keyword");
    }

    if (selectedSubcategoryId != null && !selectedSubcategoryId.isEmpty()) {
        queryBuilder.append(" AND p.subcategoryID.subcategoryID IN :subcategoryIds");
    }

    // Sắp xếp theo giá
    if (sortBy != null) {
        queryBuilder.append(" ORDER BY p.unitPrice ").append(sortBy);
    }

    // Sắp xếp theo tên
    if (sortByName != null) {
        if (queryBuilder.indexOf("ORDER BY") != -1) {
            queryBuilder.append(", "); // Thêm dấu phẩy nếu đã có sắp xếp trước đó
        } else {
            queryBuilder.append(" ORDER BY "); // Nếu không có, khởi đầu một mới
        }
        queryBuilder.append("p.productName ").append(sortByName.equals("A-Z") ? "ASC" : "DESC");
    }

    TypedQuery<Products> query = em.createQuery(queryBuilder.toString(), Products.class);

    if (searchKeyword != null && !searchKeyword.isEmpty()) {
        query.setParameter("keyword", "%" + searchKeyword + "%");
    }

    if (selectedSubcategoryId != null && !selectedSubcategoryId.isEmpty()) {
        query.setParameter("subcategoryIds", selectedSubcategoryId);
    }

    return query.getResultList();
}



}
