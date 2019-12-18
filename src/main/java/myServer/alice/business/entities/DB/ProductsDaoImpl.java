package myServer.alice.business.entities.DB;

import myServer.alice.business.entities.Product;

import java.util.List;

public class ProductsDaoImpl implements ProductsDAO {
    @Override
    public Product findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Product.class, id);
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = (List<Product>) HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("from myServer.alice.business.entities.Product")
                .list();

        return products;
    }


}
