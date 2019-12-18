package myServer.alice.business.entities.DB;

import myServer.alice.business.entities.Product;

import java.util.List;

public interface ProductsDAO extends DAO {
    public Product findById(int id);

    public List<Product> getAll();


}
