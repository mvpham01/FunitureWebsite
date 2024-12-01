/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Deliveries;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author BAOTHI
 */
@Local
public interface DeliveriesFacadeLocal {

    void create(Deliveries deliveries);

    void edit(Deliveries deliveries);

    void remove(Deliveries deliveries);

    Deliveries find(Object id);

    List<Deliveries> findAll();

    List<Deliveries> findRange(int[] range);

    int count();
    
}
