package com.mbeans;


import com.entitybeans.Consultations;
import com.entitybeans.Designer;
import com.entitybeans.ProjectDesigner;
import com.sessionbeans.ConsultationsFacadeLocal;
import com.sessionbeans.DesignerFacadeLocal;
import com.sessionbeans.ProjectDesignerFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.persistence.PersistenceException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.primefaces.PrimeFaces;

@Named(value = "designerMB")
@RequestScoped
public class DesignerMB implements Serializable {

    @EJB
    private ProjectDesignerFacadeLocal projectdesignerFacade;

    @EJB
    private ConsultationsFacadeLocal consultationsFacade;

    @EJB
    private DesignerFacadeLocal designerFacade;

  

   

    public void setConsultations(Consultations consultations) {
        this.consultations = consultations;
    }
    private Consultations consultations;

    public Consultations getConsultations() {
        return this.consultations;
    }

    
    private List<ProjectDesigner> projectdesigner;
    private Designer designer;
    private int designerID;

    public int getDesignerID() {
        return designerID;
    }

    public void setDesignerID(int designerID) {
        this.designerID = designerID;
    }
    private Designer selectedDesigner;
    private boolean editMode = false;

    public DesignerMB() {
        designer = new Designer();
        selectedDesigner = new Designer();
        consultations = new Consultations();
    }

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        String currentPage = context.getViewRoot().getViewId();

        designer = (Designer) context.getExternalContext().getSessionMap().get("loggedInDesigner");
        if (designer != null) {
            designer = designerFacade.find(designer.getDesignerID());
            if (currentPage.endsWith("profile.xhtml")) {
                projectdesigner = projectdesignerFacade.findProjectsByDesignerID(designer.getDesignerID());
            }
        } else {

            if (currentPage.endsWith("register.xhtml")) {
                designer = new Designer();
            }
            projectdesigner = null;

        }

    }

    public String edit(Designer designer) {
        this.designer = designer;
        try {
            designerFacade.edit(this.designer);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInDesigner", designerFacade.find(designer.getDesignerID()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Profile updated successfully."));
            return "profile.xhtml";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to update profile."));
            return null; 
        }
    }

    public List<Designer> showAllDesigner() {
        return designerFacade.findAll();
    }

    public String addDesign() {
        designerFacade.create(designer);
        return "/client/login.xhtml";
    }

    public String addDesigner() {
        try {
            designerFacade.create(designer);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Registration Successful", "Welcome " + designer.getFirstName()));
            return "/FunitureWebsite-war/faces/client/login.xhtml";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration Failed", "An error occurred while registering."));
            return null;
        }
    }

    public String resetForm() {
        designer = new Designer(); 
        return "register.xhtml";
    }

    public String loadUserProfile(String username) {
        designer = designerFacade.checkDesigner(username, username);
        return "profile.xhtml";  
    }

//    public void loadSelectedDesigner() {
//        // Lấy designerID từ tham số URL
//        String designerIDStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("designerID");
//        if (designerIDStr != null) {
//            try {
//                int designerID = Integer.parseInt(designerIDStr);
//                // Truy vấn designer từ cơ sở dữ liệu
//                selectedDesigner = designerFacade.find(designerID);
//                if (selectedDesigner == null) {
//                    FacesContext.getCurrentInstance().addMessage(null,
//                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "No designer found with ID: " + designerID));
//                }
//            } catch (NumberFormatException e) {
//                FacesContext.getCurrentInstance().addMessage(null,
//                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid Designer ID."));
//            }
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", "Designer ID is missing."));
//        }
//    }
    // Phương thức chuyển đổi chế độ xem/chỉnh sửa
    public void toggleEditMode() {
        editMode = !editMode;
        if (!editMode) {
            
            init();
        }
    }

  
    public String cancelEdit() {
        editMode = false;
        init();
        return null;
    }


    public String saveChanges() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            designerFacade.edit(designer);
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Profile Updated", "Your profile has been updated successfully."));
            editMode = false;
            return null; 
        } catch (Exception e) {
            e.printStackTrace();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed", "An error occurred while updating your profile."));
            return null;
        }
    }
    public String findAndLoadDesigner(Integer designerId) {
        if (designerId != null) {
            try {
                selectedDesigner = designerFacade.find(designerId);
                if (selectedDesigner == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Designer not found."));
                    return null;
                }
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedDesigner", selectedDesigner);
                designerID = selectedDesigner.getDesignerID();
                return "designerDetail"; 
            } catch (Exception e) {
                e.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "An error occurred while loading designer details."));
                return null;
            }
        }
        return null; 
    }

    public void setFirstName(String firstName) {
        if (designer != null) {
            designer.setFirstName(firstName);
        }
    }


    public void setLastName(String lastName) {
        if (designer != null) {
            designer.setLastName(lastName);
        }
    }

    public void setPhone(String phone) {
        if (designer != null) {
            designer.setPhone(phone);
        }
    }

    public void setAddress(String address) {
        if (designer != null) {
            designer.setAddress(address);
        }
    }

    public void setImage(String image) {
        if (designer != null) {
            designer.setImage(image);
        }
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public Designer getDesigner() {
        return designer;
    }

    public void setDesigner(Designer designer) {
        this.designer = designer;
    }

    public Designer getSelectedDesigner() {
        return selectedDesigner;
    }

    public void setSelectedDesigner(Designer selectedDesigner) {
        this.selectedDesigner = selectedDesigner;
    }
    //nè
    public String deleteDesigner(int designerID) {
        try {
            // Gọi dịch vụ để xóa designer
            designerFacade.remove(designerFacade.find(designerID));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Designer deleted successfully."));
            return "successPage"; // điều hướng trang thành công
        } catch (PersistenceException e) {
            // Thông báo lỗi khóa ngoại bằng alert JavaScript qua PrimeFaces
            PrimeFaces.current().executeScript("alert('Cannot delete designer due to foreign key constraint.');");
            return null;
        } catch (Exception e) {
            // Thông báo lỗi khác bằng alert JavaScript qua PrimeFaces
            PrimeFaces.current().executeScript("alert('An error occurred while deleting the designer.');");
            return null;
        }
    }

    public String getFirstName() {
        return designer != null ? designer.getFirstName() : "";
    }

    public String getLastName() {
        return designer != null ? designer.getLastName() : "";
    }

    public String getPhone() {
        return designer != null ? designer.getPhone() : "";
    }

    public String getAddress() {
        return designer != null ? designer.getAddress() : "";
    }

    public String getImage() {
        return designer != null ? designer.getImage() : "";
    }

    public ConsultationsFacadeLocal getConsultationsFacade() {
        return consultationsFacade;
    }

    public void setConsultationsFacade(ConsultationsFacadeLocal consultationsFacade) {
        this.consultationsFacade = consultationsFacade;
    }

    public DesignerFacadeLocal getDesignerFacade() {
        return designerFacade;
    }

    public void setDesignerFacade(DesignerFacadeLocal designerFacade) {
        this.designerFacade = designerFacade;
    }

    public ProjectDesignerFacadeLocal getProjectdesignerFacade() {
        return projectdesignerFacade;
    }

    public void setProjectdesignerFacade(ProjectDesignerFacadeLocal projectdesignerFacade) {
        this.projectdesignerFacade = projectdesignerFacade;
    }

    public List<ProjectDesigner> getProjectdesigner() {
        return projectdesigner;
    }

    public void setProjectdesigner(List<ProjectDesigner> projectdesigner) {
        this.projectdesigner = projectdesigner;
    }

    private static class ProjectFacadeLocal {

        public ProjectFacadeLocal() {
        }
    }

//    public String createConsultation() {
//        FacesContext context = FacesContext.getCurrentInstance();
//        selectedDesigner = (Designer) context.getExternalContext().getSessionMap().get("selectedDesigner");
//
//        try {
//            // Kiểm tra designer
//            if (selectedDesigner == null) {
//                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                        "Designer not found.", null));
//                return null;
//            }
//
//            // Khởi tạo consultations nếu nó là null
//            if (consultations == null) {
//                consultations = new Consultations();
//            }
//
//            // Cập nhật thông tin cho tempConsultations
//            consultations.setDesignerID(selectedDesigner);
//            int selectedAvailability = consultations.getAvailabilityID();
//
//            if (selectedAvailability == 0) {
//                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                        "Please select a time slot.", null));
//                return null;
//            }
//
//            consultations.setAvailabilityID(selectedAvailability);
//
//            // Lưu thông tin người dùng (nếu có)
//            // Bạn có thể cần kiểm tra người dùng đăng nhập tương tự như đã làm ở đoạn mã trước
//            // consultations.setUserID(loggedInUser); // Uncomment và điều chỉnh nếu cần
//            // Ghi thông tin và thực hiện lưu vào cơ sở dữ liệu
//            if (consultations.getConsultationID() == null) {
//                consultationsFacade.create(consultations);
//                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Consultation booked successfully!", null));
//            } else {
//                Consultations existing = consultationsFacade.find(consultations.getConsultationID());
//                if (existing != null) {
//                    // Cập nhật các trường cần thiết
//                    existing.setDay(consultations.getDay());
//                    existing.setScheduleddatetime(consultations.getScheduleddatetime());
//                    existing.setDesignerID(consultations.getDesignerID());
//                    existing.setUserID(consultations.getUserID());
//                    existing.setNotes(consultations.getNotes());
//                    existing.setAvailabilityID(consultations.getAvailabilityID());
//
//                    consultationsFacade.edit(existing);
//                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Consultation updated successfully!", null));
//                }
//            }
//
//            // Xóa tempConsultations sau khi hoàn thành
//            context.getExternalContext().getSessionMap().remove("tempconsultations");
//            return null;
//
//        } catch (ConstraintViolationException e) {
//            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
//                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                        violation.getMessage(), null));
//            }
//        } catch (Exception e) {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                    "An unexpected error occurred. Please try again.", null));
//            e.printStackTrace(); // Ghi lại ngoại lệ để gỡ lỗi
//        }
//
//        return null;
//    }

   public String markDesigner() {
        FacesContext context = FacesContext.getCurrentInstance();
        Designer selectedDesigner = (Designer) context.getExternalContext().getSessionMap().get("selectedDesigner");
        if (selectedDesigner != null) {
            List<Designer> markedDesigners = (List<Designer>) context.getExternalContext().getSessionMap().get("markedDesigners");
            if (markedDesigners == null) {
                markedDesigners = new ArrayList<>();
                context.getExternalContext().getSessionMap().put("markedDesigners", markedDesigners);
            }

            if (!markedDesigners.contains(selectedDesigner)) {
                markedDesigners.add(selectedDesigner);
                context.addMessage(null, new FacesMessage("Designer marked successfully!"));
            } else {
                context.addMessage(null, new FacesMessage("Designer is already marked!"));
            }
        } else {
            context.addMessage(null, new FacesMessage("No designer selected!"));
        }
        
        return null; 
    }

    public List<Designer> getMarkedDesigners() {
        FacesContext context = FacesContext.getCurrentInstance();
        return (List<Designer>) context.getExternalContext().getSessionMap().get("markedDesigners");
    }
    public String removeDesigner(Designer designerToRemove) {
    FacesContext context = FacesContext.getCurrentInstance();
   
    List<Designer> markedDesigners = (List<Designer>) context.getExternalContext().getSessionMap().get("markedDesigners");

    if (markedDesigners != null) {
        markedDesigners.remove(designerToRemove);
        context.addMessage(null, new FacesMessage("Designer removed successfully!"));
    }

    return null; 
}

}
