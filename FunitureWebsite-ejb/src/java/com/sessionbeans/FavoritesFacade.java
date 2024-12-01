/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Favorites;
import com.entitybeans.Products;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author BAOTHI
 */
@Stateless
public class FavoritesFacade extends AbstractFacade<Favorites> implements FavoritesFacadeLocal {

    @PersistenceContext(unitName = "FunitureWebsite-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FavoritesFacade() {
        super(Favorites.class);
    }
  @Override
    public void create(Favorites favorites) {
        em.persist(favorites);
    }
    @Override
    public List<Products> findFavoritesByUserId(int userId) {
        TypedQuery<Products> query = em.createQuery("SELECT f.productID FROM Favorites f WHERE f.userID.userID = :userId", Products.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public void removeFavorites(int userId, String productId) {
        // Tìm sản phẩm yêu thích dựa trên userId và productId
        try {
            Favorites favorite = em.createQuery("SELECT f FROM Favorites f WHERE f.userID.userID = :userId AND f.productID.productID = :productId", Favorites.class)
                    .setParameter("userId", userId)
                    .setParameter("productId", productId)
                    .getSingleResult();

            // Nếu tìm thấy, xóa khỏi cơ sở dữ liệu
            if (favorite != null) {
                em.remove(favorite);
            }
        } catch (NoResultException e) {
            // Không tìm thấy sản phẩm yêu thích
        }
    }

}
