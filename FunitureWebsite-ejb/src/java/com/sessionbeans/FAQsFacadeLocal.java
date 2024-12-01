/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.FAQs;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author BAOTHI
 */
@Local
public interface FAQsFacadeLocal {

    void create(FAQs fAQs);

    void edit(FAQs fAQs);

    void remove(FAQs fAQs);

    FAQs find(Object id);

    List<FAQs> findAll();

    List<FAQs> findRange(int[] range);

    int count();
    
}
