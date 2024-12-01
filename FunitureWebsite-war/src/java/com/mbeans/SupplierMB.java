/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.Suppliers;
import com.sessionbeans.SuppliersFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author BAOTHI
 */
@Named(value = "supplierMB")
@RequestScoped
public class SupplierMB {

    @EJB
    private SuppliersFacadeLocal suppliersFacade;

   
    
    private Suppliers suppliers;
    public SupplierMB() {
        suppliers = new Suppliers();
    }
    public String addSuppliers() {
        System.out.println("ID: " + suppliers.getSupplierID());
        if (suppliers.getSupplierID() == null) {
            suppliersFacade.create(suppliers);
        } else {
            suppliersFacade.edit(suppliers);
        }
        return "suppliers";
    }

    public String deleteSuppliers(String id) {
        suppliersFacade.remove(suppliersFacade.find(id));
        return "suppliers";
    }

    public String findSupplierbyID(String id) {
        suppliers = suppliersFacade.find(id);
        return "suppliers";
    }

    public List<Suppliers> showAllSuppliers() {
        return suppliersFacade.findAll();
    }

    public String resetForm() {
        suppliers = null;
        return "suppliers";
    }

    public Suppliers getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Suppliers suppliers) {
        this.suppliers = suppliers;
    }
    
    
}
