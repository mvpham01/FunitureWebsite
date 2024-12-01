/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.Favorites;
import com.entitybeans.Products;
import com.entitybeans.Users;
import com.sessionbeans.FavoritesFacadeLocal;
import com.sessionbeans.ProductsFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author BAOTHI
 */
@Named(value = "favoritesMB")
@RequestScoped
public class FavoritesMB {

    @EJB
    private FavoritesFacadeLocal favoritesFacade;

    @EJB
    private ProductsFacadeLocal productsFacade;

    private List<Products> favoriteProducts;
    private Favorites favorites;

    public FavoritesMB() {
        favorites = new Favorites();
    }

    public List<Products> getFavoriteProducts() {
        FacesContext context = FacesContext.getCurrentInstance();
        Users loggedInUser = (Users) context.getExternalContext().getSessionMap().get("loggedInUser");

        if (loggedInUser != null) {
            favoriteProducts = favoritesFacade.findFavoritesByUserId(loggedInUser.getUserID());
        }

        return favoriteProducts;
    }

    public void removeFromFavorites(String productId) {
        FacesContext context = FacesContext.getCurrentInstance();
        Users loggedInUser = (Users) context.getExternalContext().getSessionMap().get("loggedInUser");

        if (loggedInUser != null) {
            // Gọi phương thức để xóa sản phẩm yêu thích
            favoritesFacade.removeFavorites(loggedInUser.getUserID(), productId);
            context.addMessage(null, new FacesMessage("Product removed from favorites!"));
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "You must be logged in to remove favorites.", null));
        }
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorites favorites) {
        this.favorites = favorites;
    }

}
