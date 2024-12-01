/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Users;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author BAOTHI
 */
@Stateless
public class UsersFacade extends AbstractFacade<Users> implements UsersFacadeLocal {

    @PersistenceContext(unitName = "FunitureWebsite-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsersFacade() {
        super(Users.class);
    }
     @Override
     public Users checkUsers(String uname, String pword) {

        String sql = "SELECT u FROM Users u WHERE u.userName = :userName and u.password = :password";
        Query query = em.createQuery(sql);
        query.setParameter("userName", uname);
        query.setParameter("password", pword);
        try {
            return (Users) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
     
}
