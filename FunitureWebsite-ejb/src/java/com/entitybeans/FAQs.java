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
@Table(name = "FAQs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FAQs.findAll", query = "SELECT f FROM FAQs f"),
    @NamedQuery(name = "FAQs.findByFaqID", query = "SELECT f FROM FAQs f WHERE f.faqID = :faqID"),
    @NamedQuery(name = "FAQs.findByEmail", query = "SELECT f FROM FAQs f WHERE f.email = :email"),
    @NamedQuery(name = "FAQs.findByUsername", query = "SELECT f FROM FAQs f WHERE f.username = :username"),
    @NamedQuery(name = "FAQs.findByStatus", query = "SELECT f FROM FAQs f WHERE f.status = :status")})
public class FAQs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FaqID")
    private Integer faqID;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "Email")
    private String email;
    @Size(max = 100)
    @Column(name = "Username")
    private String username;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Content")
    private String content;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "Reply")
    private String reply;
    @Column(name = "Status")
    private Boolean status;

    public FAQs() {
    }

    public FAQs(Integer faqID) {
        this.faqID = faqID;
    }

    public Integer getFaqID() {
        return faqID;
    }

    public void setFaqID(Integer faqID) {
        this.faqID = faqID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (faqID != null ? faqID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FAQs)) {
            return false;
        }
        FAQs other = (FAQs) object;
        if ((this.faqID == null && other.faqID != null) || (this.faqID != null && !this.faqID.equals(other.faqID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entitybeans.FAQs[ faqID=" + faqID + " ]";
    }
    
}
