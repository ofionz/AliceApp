package myServer.alice.business.entities.DB;


import myServer.alice.business.entities.Purchase;

import java.util.List;

public class PurchaseDaoImpl implements PurchaseDAO {

    @Override
    public Purchase findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Purchase.class, id);
    }

    @Override
    public List<Purchase> getAll() {
        List<Purchase> purchases = (List<Purchase>) HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("from myServer.alice.business.entities.Purchase")
                .list();


        return purchases;
    }
}
