package com.mbeans;

import com.entitybeans.Products;
import com.sessionbeans.ProductsFacadeLocal;
import com.sessionbeansprocess.CartSBLocal;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@Named(value = "cartProcessMB")
@RequestScoped
public class CartProcessMB implements Serializable {

    private double totalCart;
    private double sumCart1;
    @EJB
    private ProductsFacadeLocal productsFacade;

    private int quantity = 1;
    private static Map<Products, Integer> cartMap = new HashMap<>();

    public CartProcessMB() {
        sumCart1 = calculateItemsTotal();
    }

    public String addCart(String idPro) {
        Products pro = productsFacade.find(idPro);
        if (cartMap.containsKey(pro)) {
            cartMap.replace(pro, quantity);
        } else {
            cartMap.put(pro, quantity);
        }
        return "shop-detail";
    }

    public String addCart1(String idPro) {
        Products pro = productsFacade.find(idPro);
        if (cartMap.containsKey(pro)) {
            cartMap.replace(pro, quantity);
        } else {
            cartMap.put(pro, quantity);
        }
        return "shop";
    }

    public String increaseCart(String idPro, int newquantity) {
        Products pro = productsFacade.find(idPro);
        cartMap.replace(pro, newquantity);
        return "cart";
    }

    public String decreaseCart(String idPro, int newquantity) {
        Products pro = productsFacade.find(idPro);
        cartMap.replace(pro, newquantity);
        return "cart";
    }

    public double sumCart() {
        double sum = 0;
        for (Map.Entry<Products, Integer> e : cartMap.entrySet()) {
            sum += e.getKey().getUnitPrice().doubleValue() * e.getValue().doubleValue();
        }
        return sum;
    }

    public int sumProductCart() {
        int sum = 0;
        for (Map.Entry<Products, Integer> e : cartMap.entrySet()) {
            sum += e.getValue();
        }
        return sum;
    }

    public String removeItemCart(String idPro) {
        Products pro = productsFacade.find(idPro);
        cartMap.remove(pro);
        return "cart";
    }

    public String clearnCart() {
        cartMap.clear();
        quantity = 1;
        return "cart";
    }

    public String checkout() {
        // Tính tổng giá tiền của giỏ hàng
        totalCart = sumCart();

        // Debug log
        System.out.println("Total Cart: " + totalCart);

        // Chuyển hướng sang trang thanh toán và truyền tổng giá tiền
        return "checkout.xhtml";
    }

    public int totalItemsInCart() {
        int totalItems = 0;
        if (cartMap != null) {
            for (Map.Entry<Products, Integer> entry : cartMap.entrySet()) {
                totalItems += entry.getValue(); // Sum the quantities of each product
            }
        }
        return totalItems;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Map<Products, Integer> getCartMap() {
        return cartMap;
    }

    public void setCartMap(Map<Products, Integer> cartMap) {
        CartProcessMB.cartMap = cartMap;
    }

    public double getTotalCart() {
        return totalCart;
    }

    public void setTotalCart(double totalCart) {
        this.totalCart = totalCart;
    }

    public void updateSumCart(double deliveryPrice) {
        sumCart1 = calculateItemsTotal() + deliveryPrice;
    }

    public double calculateItemsTotal() {
        // Calculate the total price of items in the cart
        return 100.0; // Example value
    }

    public double getSumCart1() {
        return sumCart1;
    }

    public void setSumCart1(double sumCart1) {
        this.sumCart1 = sumCart1;
    }

    public void updateTotalCart(double deliveryPrice) {
        totalCart = sumCart() + deliveryPrice;
    }
}
