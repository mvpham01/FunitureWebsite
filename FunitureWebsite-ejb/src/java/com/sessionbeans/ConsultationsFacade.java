/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Consultations;
import com.entitybeans.Users;
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
public class ConsultationsFacade extends AbstractFacade<Consultations> implements ConsultationsFacadeLocal {

    @PersistenceContext(unitName = "FunitureWebsite-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConsultationsFacade() {
        super(Consultations.class);
    }
    
  

    @Override
    public List<Consultations> findByUser(Integer userId) {
      return em.createNamedQuery("Consultations.findByUserID", Consultations.class)
                        .setParameter("userID", userId)
                        .getResultList();
    }

   
}
