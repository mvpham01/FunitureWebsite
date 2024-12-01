/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.Consultations;
import com.entitybeans.Designer;
import com.entitybeans.Users;

import com.sessionbeans.ConsultationsFacadeLocal;
import com.sessionbeans.DesignerFacadeLocal;
import com.sessionbeans.UsersFacadeLocal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.primefaces.PrimeFaces;

/**
 *
 * @author BAOTHI
 */
@Named(value = "consultationsMB")
@RequestScoped
public class ConsultationsMB {

    @EJB
    private UsersFacadeLocal usersFacade;

    private List<Consultations> userConsultations;

    @EJB
    private DesignerFacadeLocal designerFacade;

    @EJB
    private ConsultationsFacadeLocal consultationsFacade;

    private int selectedDesignerID;

// Getter và Setter
    public int getSelectedDesignerID() {
        return selectedDesignerID;
    }

    public void setSelectedDesignerID(int selectedDesignerID) {
        this.selectedDesignerID = selectedDesignerID;
    }

    private Date selectedDate;
    private String fullName;
    private Integer userID;

    private Consultations consultations;

    Designer designer;
    private Date selectedTime;
    private Date scheduleddatetime;
    private Date startTime; // Thời gian bắt đầu
    private Date endTime; // Thời gian kết thúc
    private Long designerId;

    public ConsultationsMB() {
        consultations = new Consultations();
    }

    public List<Consultations> showAllConsultation() {
        return consultationsFacade.findAll();
    }

    public Consultations getConsultations() {
        return consultations;
    }

    public void setConsultations(Consultations consultations) {
        this.consultations = consultations;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

//    public void loadAvailability() {
//        if (designerID != null && selectedDate != null) {
//            availableTimes = availabilityFacade.findAvailabilityByDesignerAndDate(designerID, selectedDate);
//        }
//    }
    public DesignerFacadeLocal getDesignerFacade() {
        return designerFacade;
    }

    public void setDesignerFacade(DesignerFacadeLocal designerFacade) {
        this.designerFacade = designerFacade;
    }

    public ConsultationsFacadeLocal getConsultationsFacade() {
        return consultationsFacade;
    }

    public void setConsultationsFacade(ConsultationsFacadeLocal consultationsFacade) {
        this.consultationsFacade = consultationsFacade;
    }

//    public Integer getDesignerID() {
//        return designerID;
//    }
//
//    public void setDesignerID(Integer designerID) {
//        this.designerID = designerID;
//    }
    public Date getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(Date selectedTime) {
        this.selectedTime = selectedTime;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String deleteDesigner(int id) {
        consultationsFacade.remove(consultationsFacade.find(id));
        return "consultations";
        
    }

    public Date getScheduleddatetime() {
        return scheduleddatetime;
    }

    public void setScheduleddatetime(Date scheduleddatetime) {
        this.scheduleddatetime = scheduleddatetime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<Consultations> loadUserConsultations() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Users user = (Users) session.getAttribute("loggedInUser");

        if (user != null) {
            userConsultations = consultationsFacade.findByUser(user.getUserID());
            return userConsultations;
        }
        return null;
    }

    public List<Consultations> getUserConsultations() {
        return userConsultations;
    }

    public void setUserConsultations(List<Consultations> userConsultations) {
        this.userConsultations = userConsultations;
    }

    public String createConsultation() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Users user = (Users) session.getAttribute("loggedInUser");
        FacesContext context = FacesContext.getCurrentInstance();
        Designer selectedDesigner = (Designer) context.getExternalContext().getSessionMap().get("selectedDesigner");
        try {
            if (selectedDesigner == null) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Designer not found.", null));
                return null;
            }
            if (consultations == null) {
                consultations = new Consultations();
            }
            consultations.setDesignerID(selectedDesigner);
            consultations.setDay(new Date());
            consultations.setUserID(user.getUserID());
            consultations.setStatus("Booking");
            if (consultations.getConsultationID() == null) {
                consultationsFacade.create(consultations);
               return "booking";
            } else {
                Consultations existing = consultationsFacade.find(consultations.getConsultationID());
                if (existing != null) {
                    existing.setDay(new Date());  
                    existing.setScheduleddatetime(consultations.getScheduleddatetime());
                    existing.setDesignerID(consultations.getDesignerID());
                    existing.setUserID(consultations.getUserID());
                    existing.setNotes(consultations.getNotes());

                    consultationsFacade.edit(existing);
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Consultation updated successfully!", null));
                }
            }

            context.getExternalContext().getSessionMap().remove("tempconsultations");
            return null;

        } catch (ConstraintViolationException e) {
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        violation.getMessage(), null));
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "An unexpected error occurred. Please try again.", null));
            e.printStackTrace();
        }

        return null;
    }
    public String deleteConsultations(int id) {
        try {
           
           consultationsFacade.remove(consultationsFacade.find(id));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("consultations deleted successfully."));
            return null; // điều hướng trang thành công
        } catch (PersistenceException e) {
            // Thông báo lỗi khóa ngoại bằng alert JavaScript qua PrimeFaces
            PrimeFaces.current().executeScript("alert('Cannot delete consultations due to foreign key constraint.');");
            return null;
        } catch (Exception e) {
            // Thông báo lỗi khác bằng alert JavaScript qua PrimeFaces
            PrimeFaces.current().executeScript("alert('An error occurred while deleting the consultations.');");
            return null;
        }
    }
}
