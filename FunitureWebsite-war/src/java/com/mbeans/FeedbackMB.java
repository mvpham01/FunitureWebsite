
package com.mbeans;

import com.entitybeans.Designer;
import com.entitybeans.Feedbacks;
import com.entitybeans.FeedbackDes;
import com.entitybeans.Users;
import com.sessionbeans.FeedbacksFacadeLocal;
import com.sessionbeans.FeedbackDesFacadeLocal;
import com.sessionbeans.UsersFacadeLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named(value = "feedbackMB")
@RequestScoped
public class FeedbackMB {
    
     @EJB
    private FeedbacksFacadeLocal feedbacksFacade;
     @EJB
    private FeedbackDesFacadeLocal feedbackDesFacadeLocal;
     @EJB
    private UsersFacadeLocal UserFacade;
    private Feedbacks feedbacks;
    private FeedbackDes feedbackDes;
    public FeedbackDes getFeedbackDes() {
        return feedbackDes;
    }

    public void setFeedbackDes(FeedbackDes feedbackDes) {
        this.feedbackDes = feedbackDes;
    }
    
     private String feedbackType;  // For select dropdown value
     private int rating;
    
    public FeedbackMB() {
        feedbacks = new Feedbacks();
         feedbackDes = new FeedbackDes();

    }
    private List<FeedbackDes> feedbackList;

    public List<FeedbackDes> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<FeedbackDes> feedbackList) {
        this.feedbackList = feedbackList;
    }
    
    public List<FeedbackDes> loadFeedbackForDesigner() {
         FacesContext context = FacesContext.getCurrentInstance();
        Designer TempDesigner = (Designer) context.getExternalContext().getSessionMap().get("selectedDesigner");
          return feedbackDesFacadeLocal.getFeedbackByDesigner(TempDesigner.getDesignerID());
    }
     public String addFeedback() {
        if (feedbacks.getFeedbackID() == null) {
            feedbacks.setFeedbackType(feedbackType);
            feedbacks.setRating(rating);
            feedbacksFacade.create(feedbacks);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Feedback successfully submitted"));
        } else {
            Feedbacks existingFeedback = feedbacksFacade.find(feedbacks.getFeedbackID());
            if (existingFeedback != null) {
                if (feedbacks.getComment() != null) {
                    existingFeedback.setComment(feedbacks.getComment());
                }
                if (feedbacks.getYourname() != null) {
                    existingFeedback.setYourname(feedbacks.getYourname());
                }
                existingFeedback.setFeedbackType(feedbackType);
                existingFeedback.setRating(rating);
                feedbacksFacade.edit(existingFeedback);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Feedback successfully updated"));
            }
        }
        return "feedbackService"; 
    }
    
    public String deleteFeedbacks(int id) {
        feedbacksFacade.remove(feedbacksFacade.find(id));
        return "feedback";
    }

    public String findFeedbackbyID(int id) {
        feedbacks = feedbacksFacade.find(id);
        return "feedback";
    }

    public List<Feedbacks> showAllFeedbacks() {
        return feedbacksFacade.findAll();
    }

    public String resetForm() {
        feedbacks = null;
        return "feedback";
    }
    

    public FeedbacksFacadeLocal getFeedbacksFacade() {
        return feedbacksFacade;
    }

    public void setFeedbacksFacade(FeedbacksFacadeLocal feedbacksFacade) {
        this.feedbacksFacade = feedbacksFacade;
    }

    public Feedbacks getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(Feedbacks feedbacks) {
        this.feedbacks = feedbacks;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    public String submitFeedback() {
    try {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Users user = (Users) session.getAttribute("loggedInUser");
        FacesContext context = FacesContext.getCurrentInstance();
        Designer selectedDesigner = (Designer) context.getExternalContext().getSessionMap().get("selectedDesigner");

        // Check if the user has already submitted feedback for this designer
        FeedbackDes existingFeedback = feedbackDesFacadeLocal.findByUserAndDesigner(user, selectedDesigner);

        if (existingFeedback == null) {
            // No existing feedback, create a new one
            feedbackDes.setUserID(user);
            feedbackDes.setDesignerID(selectedDesigner);
            feedbackDesFacadeLocal.create(feedbackDes);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Feedback submitted successfully."));
        } else {
            // Update the existing feedback
            existingFeedback.setContent(feedbackDes.getContent());
            existingFeedback.setRating(feedbackDes.getRating());
            feedbackDesFacadeLocal.edit(existingFeedback);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Feedback updated successfully."));
        }

        // Reset the feedbackDes object
        feedbackDes = new FeedbackDes();

    } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error submitting feedback", null));
    }
    return null;
}
   
}
