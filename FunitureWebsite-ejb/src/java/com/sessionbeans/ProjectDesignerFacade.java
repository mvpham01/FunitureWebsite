/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbeans;

import com.entitybeans.ProjectDesigner;
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
public class ProjectDesignerFacade extends AbstractFacade<ProjectDesigner> implements ProjectDesignerFacadeLocal {

    @PersistenceContext(unitName = "FunitureWebsite-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjectDesignerFacade() {
        super(ProjectDesigner.class);
    }

    @Override
    public List<ProjectDesigner> findProjectsByDesignerID(int designerID) {
    // Truy vấn để lấy danh sách project của designer theo ID
    TypedQuery<ProjectDesigner> query = em.createQuery(
        "SELECT pd FROM ProjectDesigner pd WHERE pd.designerID.designerID = :designerID", ProjectDesigner.class);
    query.setParameter("designerID", designerID);
    return query.getResultList();
}

}
