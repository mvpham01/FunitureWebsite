/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Designer;
import com.entitybeans.FeedbackDes;
import com.entitybeans.Users;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author 84338
 */
@Local
public interface FeedbackDesFacadeLocal {

    void create(FeedbackDes feedbackDes);

    void edit(FeedbackDes feedbackDes);

    void remove(FeedbackDes feedbackDes);

    FeedbackDes find(Object id);

    List<FeedbackDes> findAll();

    List<FeedbackDes> findRange(int[] range);

    int count();
    public List<FeedbackDes> getFeedbackByDesigner(int designerId);

    public FeedbackDes findByUserAndDesigner(Users user, Designer selectedDesigner);
}
