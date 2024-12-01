/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.Designer;
import com.entitybeans.ProjectDesigner;
import com.sessionbeans.DesignerFacadeLocal;
import com.sessionbeans.ProjectDesignerFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author BAOTHI
 */
@Named(value = "projectDesignerMB")
@RequestScoped
public class ProjectDesignerMB implements Serializable {

    @EJB
    private DesignerFacadeLocal designerFacade;

    @EJB
    private ProjectDesignerFacadeLocal projectDesignerFacade;

    private ProjectDesigner projectDesigner; 

    public ProjectDesigner getProjectDesigner() {
        return projectDesigner;
    }

    public void setProjectDesigner(ProjectDesigner projectDesigner) {
        this.projectDesigner = projectDesigner;
    }
    
    private List<ProjectDesigner> projectList; 

    public ProjectDesignerMB() {
        projectDesigner = new ProjectDesigner();
    }

    public List<ProjectDesigner> showAllProjectDesigner() {
        return projectDesignerFacade.findAll();
    }

  

    @PostConstruct
    public void init() {
        loadDesignerAndProjects();
    }

   

    public void loadDesignerAndProjects() {
        String idStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("designerID");
        System.out.println("Received designerID: " + idStr); 
        if (idStr != null) {
            try {
                int designerID = Integer.parseInt(idStr);
                System.out.println("Parsed designerID: " + designerID); 
                projectList = projectDesignerFacade.findProjectsByDesignerID(designerID);
                System.out.println("Projects found: " + projectList.size()); 
            } catch (NumberFormatException e) {
                e.printStackTrace(); 
            }
        }
    }

    public String deletePj(int id) {
        projectDesignerFacade.remove(projectDesignerFacade.find(id));
        return "projectdesigner";
    }

    public List<ProjectDesigner> getProjectList() {
        FacesContext context = FacesContext.getCurrentInstance();
        Designer selectedDesigner = (Designer) context.getExternalContext().getSessionMap().get("selectedDesigner");
        return projectList = projectDesignerFacade.findProjectsByDesignerID(selectedDesigner.getDesignerID());
    }

    public void setProjectList(List<ProjectDesigner> projectList) {
        this.projectList = projectList;
    }

    public String findProjectDesignerbyID(int id) {
        projectDesigner = projectDesignerFacade.find(id);
        return "projectdesigner";
    }

    public String resetForm() {
        projectDesigner = null;
        return "projectdesigner";
    }

    public DesignerFacadeLocal getDesignerFacade() {
        return designerFacade;
    }

    public void setDesignerFacade(DesignerFacadeLocal designerFacade) {
        this.designerFacade = designerFacade;
    }

    public ProjectDesignerFacadeLocal getProjectDesignerFacade() {
        return projectDesignerFacade;
    }

    public void setProjectDesignerFacade(ProjectDesignerFacadeLocal projectDesignerFacade) {
        this.projectDesignerFacade = projectDesignerFacade;
    }

    public String viewProjectDetails(ProjectDesigner project) {
        projectDesigner = project;
        return "projectDetails";
    }
}