/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.ProjectDesigner;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author BAOTHI
 */
@Local
public interface ProjectDesignerFacadeLocal {

    void create(ProjectDesigner projectDesigner);

    void edit(ProjectDesigner projectDesigner);

    void remove(ProjectDesigner projectDesigner);

    ProjectDesigner find(Object id);

    List<ProjectDesigner> findAll();

    List<ProjectDesigner> findRange(int[] range);

    int count();
     public List<ProjectDesigner> findProjectsByDesignerID(int designerID);
}
