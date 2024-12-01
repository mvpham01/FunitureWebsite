/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.FAQs;
import com.sessionbeans.FAQsFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author BAOTHI
 */
@Named(value = "fAQsMB")
@RequestScoped
public class FAQsMB {

    @EJB
    private FAQsFacadeLocal fAQsFacade;
    private FAQs faqs;

    public FAQsMB() {
        faqs = new FAQs();
    }

   public String addFAQs() {
    System.out.println("ID: " + faqs.getFaqID());
    
    if (faqs.getFaqID() == null) {
        // Tạo mới FAQ nếu ID không tồn tại
        fAQsFacade.create(faqs);
    } else {
        // Lấy FAQ hiện tại từ cơ sở dữ liệu
        FAQs existingFaq = fAQsFacade.find(faqs.getFaqID());
        
        if (existingFaq != null) {
            // Cập nhật các thông tin mới vào FAQ hiện tại
            if (faqs.getContent()!= null) {
                existingFaq.setContent(faqs.getContent());
            }
            if (faqs.getReply()!= null) {
                existingFaq.setReply(faqs.getReply());
            }
            // Thêm các thuộc tính khác bạn muốn cập nhật...
            
            // Lưu lại các thay đổi
            fAQsFacade.edit(existingFaq);
        } else {
            // Tạo mới nếu không tìm thấy FAQ hiện tại
            fAQsFacade.create(faqs);
        }
    }
    
    return "faqs";
}


    public String deleteFAQs(int id) {
        fAQsFacade.remove(fAQsFacade.find(id));
        return "faqs";
    }

    public String findFAQbyID(int id) {
        faqs = fAQsFacade.find(id);
        return "faqs";
    }

    public List<FAQs> showAllFAQs() {
        return fAQsFacade.findAll();
    }

    public String resetForm() {
        faqs = null;
        return "faqs";
    }

    public FAQs getFaqs() {
        return faqs;
    }

    public void setFaqs(FAQs faqs) {
        this.faqs = faqs;
    }

}
