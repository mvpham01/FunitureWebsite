/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Designer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author BAOTHI
 */
@Stateless
public class DesignerFacade extends AbstractFacade<Designer> implements DesignerFacadeLocal {

    @PersistenceContext(unitName = "FunitureWebsite-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DesignerFacade() {
        super(Designer.class);
    }
      @Override
     public Designer checkDesigner(String uname, String pword) {

        String sql = "SELECT u FROM Designer u WHERE u.username = :username and u.password = :password";
        Query query = em.createQuery(sql);
        query.setParameter("username", uname);
        query.setParameter("password", pword);
        try {
            return (Designer) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
   public Designer findDesignerById(Integer designerID) {
        if (designerID == null) {
            return null; // or throw an exception based on your application's needs
        }
        return em.find(Designer.class, designerID);
    }
}
