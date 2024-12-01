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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author BAOTHI
 */
@Entity
@Table(name = "Deliveries")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deliveries.findAll", query = "SELECT d FROM Deliveries d"),
    @NamedQuery(name = "Deliveries.findByDeliveryID", query = "SELECT d FROM Deliveries d WHERE d.deliveryID = :deliveryID"),
    @NamedQuery(name = "Deliveries.findByDeliveryName", query = "SELECT d FROM Deliveries d WHERE d.deliveryName = :deliveryName"),
    @NamedQuery(name = "Deliveries.findByDistance", query = "SELECT d FROM Deliveries d WHERE d.distance = :distance"),
    @NamedQuery(name = "Deliveries.findByPrice", query = "SELECT d FROM Deliveries d WHERE d.price = :price")})
public class Deliveries implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DeliveryID")
    private Integer deliveryID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "DeliveryName")
    private String deliveryName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Distance")
    private Double distance;
    @Column(name = "Price")
    private Double price;

    public Deliveries() {
    }

    public Deliveries(Integer deliveryID) {
        this.deliveryID = deliveryID;
    }

    public Deliveries(Integer deliveryID, String deliveryName) {
        this.deliveryID = deliveryID;
        this.deliveryName = deliveryName;
    }

    public Integer getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(Integer deliveryID) {
        this.deliveryID = deliveryID;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deliveryID != null ? deliveryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deliveries)) {
            return false;
        }
        Deliveries other = (Deliveries) object;
        if ((this.deliveryID == null && other.deliveryID != null) || (this.deliveryID != null && !this.deliveryID.equals(other.deliveryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entitybeans.Deliveries[ deliveryID=" + deliveryID + " ]";
    }
    
}
