/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entitybeans;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author BAOTHI
 */
@Entity
@Table(name = "Feedbacks")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Feedbacks.findAll", query = "SELECT f FROM Feedbacks f"),
    @NamedQuery(name = "Feedbacks.findByFeedbackID", query = "SELECT f FROM Feedbacks f WHERE f.feedbackID = :feedbackID"),
    @NamedQuery(name = "Feedbacks.findByYourname", query = "SELECT f FROM Feedbacks f WHERE f.yourname = :yourname"),
    @NamedQuery(name = "Feedbacks.findByFeedbackType", query = "SELECT f FROM Feedbacks f WHERE f.feedbackType = :feedbackType"),
    @NamedQuery(name = "Feedbacks.findByRating", query = "SELECT f FROM Feedbacks f WHERE f.rating = :rating")})
public class Feedbacks implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "feedbackID")
    private Integer feedbackID;
    @Size(max = 255)
    @Column(name = "yourname")
    private String yourname;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "comment")
    private String comment;
    @Size(max = 50)
    @Column(name = "feedback_type")
    private String feedbackType;
    @Column(name = "rating")
    private Integer rating;

    public Feedbacks() {
    }

    public Feedbacks(Integer feedbackID) {
        this.feedbackID = feedbackID;
    }

    public Integer getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(Integer feedbackID) {
        this.feedbackID = feedbackID;
    }

    public String getYourname() {
        return yourname;
    }

    public void setYourname(String yourname) {
        this.yourname = yourname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (feedbackID != null ? feedbackID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Feedbacks)) {
            return false;
        }
        Feedbacks other = (Feedbacks) object;
        if ((this.feedbackID == null && other.feedbackID != null) || (this.feedbackID != null && !this.feedbackID.equals(other.feedbackID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entitybeans.Feedbacks[ feedbackID=" + feedbackID + " ]";
    }
    
}
