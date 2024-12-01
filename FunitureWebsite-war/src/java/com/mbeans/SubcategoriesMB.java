package com.mbeans;

import com.entitybeans.Products;
import com.entitybeans.Subcategories;
import com.sessionbeans.CategoriesFacadeLocal;
import com.sessionbeans.ProductsFacadeLocal;
import com.sessionbeans.SubcategoriesFacadeLocal;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

@Named(value = "subcategoriesMB")
@RequestScoped
public class SubcategoriesMB {

    @EJB
    private ProductsFacadeLocal productsFacade;

    @EJB
    private CategoriesFacadeLocal categoriesFacade;

    @EJB
    private SubcategoriesFacadeLocal subcategoriesFacade;

     private Products product;
    private Subcategories subcategory;
    private String categoryID;
    private Part image;
    private String imageName;
 
    public SubcategoriesMB() {
        subcategory = new Subcategories();
    }

    public String addSubcategories() {
        try {
            if (image != null) {
                // Đường dẫn lưu trữ file
                String uploadDirectory = "";
                String fileName = getFileName(image);
                String filePath = uploadDirectory + "/" + fileName;

                // Lưu file vào thư mục uploads
                try (InputStream input = image.getInputStream()) {
                    Path destination = Paths.get(uploadDirectory, fileName);
                    Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
                }

                // Thiết lập đường dẫn hình ảnh cho subcategory
                subcategory.setImage(filePath);
            }

          
            if (subcategory.getSubcategoryID() == null) {
                subcategory.setCategoryID(categoriesFacade.find(categoryID));
                subcategoriesFacade.create(subcategory);
            } else {
                  subcategory.setCategoryID(categoriesFacade.find(categoryID));
                subcategoriesFacade.edit(subcategory);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        return "subcategory";
    }

    public List<Subcategories> showAllSubcategories() {
        return subcategoriesFacade.findAll();
    }

    public String resetForm() {
        subcategory = new Subcategories();
        image = null;
        categoryID = null;
        return "subcategory";
    }

 public String deleteSubcategories(String id) {
    Subcategories subcategoryToDelete = subcategoriesFacade.find(id);
    if (subcategoryToDelete != null) {
        // Lấy danh sách các sản phẩm liên quan đến subcategory
        List<Products> productsList = productsFacade.findBySubcategoryId(subcategoryToDelete);

        // Cập nhật SubcategoryID của các sản phẩm liên quan về null hoặc một giá trị khác
        for (Products product : productsList) {
            product.setSubcategoryID(null); // hoặc một giá trị khác
            productsFacade.edit(product);
        }
        
        // Xóa hình ảnh từ thư mục
        deleteImage(subcategoryToDelete.getImage());

        // Xóa subcategory từ cơ sở dữ liệu
        subcategoriesFacade.remove(subcategoryToDelete);

        // Hiển thị thông báo thành công
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Delete Successful"));
    } else {
        // Hiển thị thông báo lỗi nếu không tìm thấy subcategory
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Can't Delete Subcategory"));
    }
    return "subcategory";
}


    private void deleteImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                imageFile.delete();
            }
        }
    }

   public String findSubcategoryByID(String id) {
    subcategory = subcategoriesFacade.find(id);
    if (subcategory != null) {
       
        if (subcategory.getCategoryID() != null) {
            categoryID = subcategory.getCategoryID().getCategoryID();
        } else {
            categoryID = null; 
        }
    }
    return "subcategory"; 
}


    public Subcategories getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategories subcategory) {
        this.subcategory = subcategory;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
    }

    // Utility method to extract file name from Part
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
   public String findCategory(String categoryID) {
    // Set the category ID to filter subcategories
    this.categoryID = categoryID;

    // Redirect to the same page with the selected category ID as a query parameter
    FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
    try {
        externalContext.redirect(request.getRequestURI() + "?categoryID=" + categoryID);
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Return null to prevent navigation to a new page
    return null;
}

  

    public List<Subcategories> getFilteredSubcategories() {
        // Retrieve filtered subcategories based on the selected category ID
        if (categoryID != null && !categoryID.isEmpty()) {
            return subcategoriesFacade.findByCategory(categoryID);
        } else {
            // Return all subcategories if no category is selected
            return subcategoriesFacade.findAll();
        }
    }


}
