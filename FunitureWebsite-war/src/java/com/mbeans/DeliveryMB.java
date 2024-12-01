package com.mbeans;

import com.entitybeans.Deliveries;
import com.sessionbeans.DeliveriesFacadeLocal;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "deliveryMB")
@RequestScoped
public class DeliveryMB {

    @Inject
    private CartProcessMB cartMB;  // Inject CartProcessMB to update sumCart

    @EJB
    private DeliveriesFacadeLocal deliveriesFacade;

    private Deliveries delivery;
    private Deliveries selectedDeliveries;

    public DeliveryMB() {
        delivery = new Deliveries();
    }

    public String addDeliveries() {
        System.out.println("ID: " + delivery.getDeliveryID());
        if (delivery.getDeliveryID() == null) {
            deliveriesFacade.create(delivery);
        } else {
            deliveriesFacade.edit(delivery);
        }
        return "deliveries";
    }

    public String deleteDeliveries(int id) {
        deliveriesFacade.remove(deliveriesFacade.find(id));
        return "deliveries";
    }

    public String findDeliverybyID(int id) {
        delivery = deliveriesFacade.find(id);
        return "deliveries";
    }

    public List<Deliveries> showAllDeliveries() {
        return deliveriesFacade.findAll();
    }

    public String resetForm() {
        delivery = new Deliveries(); // Reset delivery object
        return "deliveries";
    }

    public String findDeliveryID(int deliveryID) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        externalContext.getSessionMap().put("selectedDeliveryID", deliveryID);

        try {
            externalContext.redirect(externalContext.getRequestContextPath() + "/checkout.xhtml?id=" + deliveryID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

   public void loadSelectedDelivery() {
    Integer deliveryID = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedDeliveryID");
    if (deliveryID != null) {
        selectedDeliveries = deliveriesFacade.find(deliveryID);
    }
   }

    // Getters and setters
    public Deliveries getDelivery() {
        return delivery;
    }

    public void setDelivery(Deliveries delivery) {
        this.delivery = delivery;
    }

    public Deliveries getSelectedDeliveries() {
        return selectedDeliveries;
    }

    public void setSelectedDeliveries(Deliveries selectedDeliveries) {
        this.selectedDeliveries = selectedDeliveries;
    }

    public void setCartProcessMB(CartProcessMB cartProcessMB) {
        this.cartMB = cartProcessMB;
    }

    public void updateTotal() {
        double deliveryPrice = selectedDeliveries.getPrice(); // Assuming selectedDeliveries holds the selected delivery
        cartMB.updateTotalCart(deliveryPrice);
    }
}
