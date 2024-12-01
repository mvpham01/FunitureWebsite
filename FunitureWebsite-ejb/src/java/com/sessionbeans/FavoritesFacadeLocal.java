/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Favorites;
import com.entitybeans.Products;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author BAOTHI
 */
@Local
public interface FavoritesFacadeLocal {

    void create(Favorites favorites);

    void edit(Favorites favorites);

    void remove(Favorites favorites);

    Favorites find(Object id);

    List<Favorites> findAll();

    List<Favorites> findRange(int[] range);

    int count();

public List<Products> findFavoritesByUserId(int userId);

    public void removeFavorites(int userId, String productId);
    
}
