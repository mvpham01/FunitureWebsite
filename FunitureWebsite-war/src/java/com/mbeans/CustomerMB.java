/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.Categories;
import com.entitybeans.Users;
import com.sessionbeans.UsersFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author BAOTHI
 */
@Named(value = "customerMB")
@RequestScoped
public class CustomerMB {

    @EJB
    private UsersFacadeLocal usersFacade;
  private Users users;

    public CustomerMB() {
        users = new Users();
    }
    public String addUser(){
        usersFacade.create(users);
        return "./login.xhtml";
    }
    public String resetForm(){
        users = null;
        return"register";
    }
  
 public List<Users> showAllUsers() {
        return usersFacade.findAll();
    }
      public String deleteUsers(int id) {
        usersFacade.remove(usersFacade.find(id));
        return "customer";
    }
    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
    
}
