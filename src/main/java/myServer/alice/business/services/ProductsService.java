package myServer.alice.business.services;

import myServer.alice.business.entities.DB.ProductsDAO;
import myServer.alice.business.entities.DB.ProductsDaoImpl;
import myServer.alice.business.entities.Product;

import java.util.List;

public class ProductsService {


    private ProductsDAO productsDAO = new ProductsDaoImpl();


    public void saveProduct(Product pr) {
        productsDAO.save(pr);
    }


    public void deleteProduct(Product pr) {
        productsDAO.delete(pr);
    }


    public void updateProduct(int id, Product product2) {
        Product product = findById(id);
        product.setText(product2.getText());
        product.setPrice(product2.getPrice());
        productsDAO.update(product);

    }

    public List<Product> getAll() {
        return productsDAO.getAll();
    }

    public Product findById(int id) {
        return productsDAO.findById(id);
    }


}
