/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.Categories;
import com.entitybeans.Subcategories;
import com.sessionbeans.CategoriesFacadeLocal;
import com.sessionbeans.SubcategoriesFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author BAOTHI
 */
@Named(value = "categoryMB")
@RequestScoped
public class CategoryMB {

    @EJB
    private SubcategoriesFacadeLocal subcategoriesFacade;

    
    @EJB
    private CategoriesFacadeLocal categoriesFacade;
    private Categories selectedCategory;

    Categories category;

    public CategoryMB() {
        category = new Categories();
    }

    public String addCategories() {
        System.out.println("ID: " + category.getCategoryID());
        if (category.getCategoryID() == null) {
            categoriesFacade.create(category);
        } else {
            categoriesFacade.edit(category);
        }
        return "category";
    }

    public String deleteCategories(String id) {
    Categories categoryToDelete = categoriesFacade.find(id);
    if (categoryToDelete != null) {
        // Tìm tất cả các Subcategory liên quan đến Category này
        List<Subcategories> subcategoriesList = subcategoriesFacade.findByCategory(id);
        
        // Cập nhật Category của các Subcategory đó thành null
        for (Subcategories subcategory : subcategoriesList) {
            subcategory.setCategoryID(null);
            subcategoriesFacade.edit(subcategory);
        }
        
        // Xóa Category từ cơ sở dữ liệu
        categoriesFacade.remove(categoryToDelete);

        // Hiển thị thông báo thành công
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Category deleted successfully."));
    } else {
        // Hiển thị thông báo lỗi nếu không tìm thấy Category
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Category not found."));
    }
    return "category";
}

    public String findCategorybyID(String id) {
        category = categoriesFacade.find(id);
        return "category";
    }

    public List<Categories> showAllCategories() {
        return categoriesFacade.findAll();
    }

    public String resetForm() {
        category = null;
        return "category";
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public Categories getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Categories selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

}
