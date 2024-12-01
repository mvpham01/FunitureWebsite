/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.Favorites;
import com.entitybeans.Products;
import com.entitybeans.Users;
import com.sessionbeans.CategoriesFacadeLocal;
import com.sessionbeans.FavoritesFacadeLocal;
import com.sessionbeans.ProductsFacadeLocal;
import com.sessionbeans.SubcategoriesFacadeLocal;
import com.sessionbeans.SuppliersFacadeLocal;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 *
 * @author BAOTHI
 */
@Named(value = "productsMB")
@RequestScoped
public class ProductsMB {

    @EJB
    private FavoritesFacadeLocal favoritesFacade;

    @EJB
    private ProductsFacadeLocal productsFacade;

    @EJB
    private SubcategoriesFacadeLocal subcategoriesFacade;

    @EJB
    private SuppliersFacadeLocal suppliersFacade;

    private List<Products> filteredProducts;
    private String searchKeyword;
    private List<String> selectedSubcategoryId = new ArrayList<>();
    private String sortBy;
    private String sortByName; // Thêm thuộc tính này

    private Products products;
    private String subcategoryID;
    private String supplierID;
    private int quantity;
    private Products selectedProduct;
    private Part image;

    public ProductsMB() {
        products = new Products();

        searchKeyword = "";

        sortBy = "asc"; // Mặc định sắp xếp tăng dần theo giá

    }

    public List<Products> showAllProducts() {
        return productsFacade.findAll();
    }

    public void addToFavorites(Products product) {
    FacesContext context = FacesContext.getCurrentInstance();
    Users loggedInUser = (Users) context.getExternalContext().getSessionMap().get("loggedInUser");

    if (loggedInUser != null) {
        // Khởi tạo đối tượng Favorites
        Favorites favorite = new Favorites();
        favorite.setUserID(loggedInUser); // Đặt người dùng
        favorite.setProductID(product); // Đặt sản phẩm

        // Thêm sản phẩm vào danh sách yêu thích
        favoritesFacade.create(favorite);
        
        // Thông báo cho người dùng rằng sản phẩm đã được thêm vào yêu thích
        context.addMessage(null, new FacesMessage("Product added to favorites!"));
    } else {
        // Thông báo lỗi nếu người dùng chưa đăng nhập
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "You must be logged in to add favorites.", null));
    }
}




    public List<String> getSelectedSubcategoryId() {
        return selectedSubcategoryId;
    }

    public void setSelectedSubcategoryId(List<String> selectedSubcategoryId) {
        this.selectedSubcategoryId = selectedSubcategoryId;
    }

    public String addProducts() {
        try {

            if (image != null) {

                String uploadDirectory = "";
                String fileName = getFileName(image);
                String filePath = uploadDirectory + "/" + fileName;

                // Lưu file vào thư mục uploads
                try (InputStream input = image.getInputStream()) {
                    Path destination = Paths.get(uploadDirectory, fileName);
                    Files.copy(input, destination, StandardCopyOption.REPLACE_EXISTING);
                }

                products.setImage(filePath);
            } else {

                Products existingProduct = productsFacade.find(products.getProductID());
                if (existingProduct != null) {
                    products.setImage(existingProduct.getImage());
                }
            }

            products.setSubcategoryID(subcategoriesFacade.find(subcategoryID));
            products.setSupplierID(suppliersFacade.find(supplierID));

            if (products.getProductID() == null) {
                productsFacade.create(products);
            } else {
                productsFacade.edit(products);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "products";
    }

    public String deleteProducts(String productID) {
        Products productsToDelete = productsFacade.find(productID);
        if (productsToDelete != null) {
            // Xóa hình ảnh từ thư mục
            deleteImage(productsToDelete.getImage());

            // Xóa subcategory từ cơ sở dữ liệu
            productsFacade.remove(productsToDelete);

            // Hiển thị thông báo thành công
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(""));
        } else {
            // Hiển thị thông báo lỗi nếu không tìm thấy subcategory
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(""));
        }
        return "products";
    }

    private void deleteImage(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                imageFile.delete();
            }
        }
    }

    public String resetForm() {
        products = null;
        image = null;
        subcategoryID = null;

        supplierID = null;
        return "products";
    }

    public String findProductforDetail(String productID) {
        // Lưu trữ productID vào session hoặc làm các công việc cần thiết
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedProductID", productID);

        // Chuyển hướng đến trang chi tiết sản phẩm
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("shop-detail.xhtml?productID=" + productID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Trả về null để FacesContext xử lý việc chuyển hướng
        return null;
    }

    public void loadSelectedProduct() {
        String productID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedProductID");
        if (productID != null) {
            selectedProduct = productsFacade.find(productID);

        }
    }

    public String findProductforUpdate(String productID) {
        products = productsFacade.find(productID);
        if (products != null) {

            if (products.getSubcategoryID() != null) {
                subcategoryID = products.getSubcategoryID().getSubcategoryID();
            } else {
                subcategoryID = null;
            }

            if (products.getSupplierID() != null) {
                supplierID = products.getSupplierID().getSupplierID();
            } else {
                supplierID = null;
            }
        }
        return "products";
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public String getSubcategoryID() {
        return subcategoryID;
    }

    public void setSubcategoryID(String subcategoryID) {
        this.subcategoryID = subcategoryID;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public Products getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Products selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    public Part getImage() {
        return image;
    }

    public void setImage(Part image) {
        this.image = image;
    }

    public List<Products> getFilteredProducts() {
        if (filteredProducts == null) {
            updateProductList(); // Tải danh sách sản phẩm khi trang được load
        }
        return filteredProducts;
    }

    public void updateProductList() {
        // Cập nhật danh sách sản phẩm dựa trên điều kiện tìm kiếm, lọc và sắp xếp
        filteredProducts = productsFacade.findProducts(searchKeyword, selectedSubcategoryId, sortBy, sortByName);
    }

    // Getters and setters cho searchKeyword, selectedSubcategoryId và sortBy
    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
// Getter và setter cho sortByName

    public String getSortByName() {
        return sortByName;
    }

    public void setSortByName(String sortByName) {
        this.sortByName = sortByName;
    }
}
