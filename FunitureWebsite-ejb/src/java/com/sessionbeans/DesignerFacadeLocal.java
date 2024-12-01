/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.Designer;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author BAOTHI
 */
@Local
public interface DesignerFacadeLocal {

    void create(Designer designer);

    void edit(Designer designer);

    void remove(Designer designer);

    Designer find(Object id);

    List<Designer> findAll();

    List<Designer> findRange(int[] range);

    int count();

    public Designer checkDesigner(String uname, String pword);

    public Designer findDesignerById(Integer designerID);
    
}
