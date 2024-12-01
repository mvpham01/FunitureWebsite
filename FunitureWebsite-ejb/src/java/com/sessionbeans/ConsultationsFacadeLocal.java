/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Consultations;
import com.entitybeans.Users;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author BAOTHI
 */
@Local
public interface ConsultationsFacadeLocal {

    void create(Consultations consultations);

    void edit(Consultations consultations);

    void remove(Consultations consultations);

    Consultations find(Object id);

    List<Consultations> findAll();

    List<Consultations> findRange(int[] range);

    int count();
   public List<Consultations> findByUser(Integer userId) ;
}
