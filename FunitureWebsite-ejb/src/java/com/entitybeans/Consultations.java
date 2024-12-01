/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entitybeans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 84338
 */
@Entity
@Table(name = "Consultations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consultations.findAll", query = "SELECT c FROM Consultations c"),
    @NamedQuery(name = "Consultations.findByConsultationID", query = "SELECT c FROM Consultations c WHERE c.consultationID = :consultationID"),
    @NamedQuery(name = "Consultations.findByDay", query = "SELECT c FROM Consultations c WHERE c.day = :day"),
    @NamedQuery(name = "Consultations.findByUserID", query = "SELECT c FROM Consultations c WHERE c.userID = :userID"),
    @NamedQuery(name = "Consultations.findByScheduleddatetime", query = "SELECT c FROM Consultations c WHERE c.scheduleddatetime = :scheduleddatetime"),
    @NamedQuery(name = "Consultations.findByStatus", query = "SELECT c FROM Consultations c WHERE c.status = :status")})
public class Consultations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
      @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConsultationID")
    private Integer consultationID;
    @Column(name = "Day")
    @Temporal(TemporalType.DATE)
    private Date day;
    @Column(name = "Scheduled_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduleddatetime;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Notes")
    private String notes;
    @Size(max = 255)
    @Column(name = "Status")
    private String status;
    @JoinColumn(name = "DesignerID", referencedColumnName = "DesignerID")
    @ManyToOne
    private Designer designerID;
     private Integer userID;

    public Consultations() {
    }

    public Consultations(Integer consultationID) {
        this.consultationID = consultationID;
    }

    public Integer getConsultationID() {
        return consultationID;
    }

    public void setConsultationID(Integer consultationID) {
        this.consultationID = consultationID;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Date getScheduleddatetime() {
        return scheduleddatetime;
    }

    public void setScheduleddatetime(Date scheduleddatetime) {
        this.scheduleddatetime = scheduleddatetime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Designer getDesignerID() {
        return designerID;
    }

    public void setDesignerID(Designer designerID) {
        this.designerID = designerID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consultationID != null ? consultationID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consultations)) {
            return false;
        }
        Consultations other = (Consultations) object;
        if ((this.consultationID == null && other.consultationID != null) || (this.consultationID != null && !this.consultationID.equals(other.consultationID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entitybeans.Consultations[ consultationID=" + consultationID + " ]";
    }
    
}
