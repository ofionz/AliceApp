package myServer.alice.business.services;

import myServer.alice.business.entities.DB.PurchaseDAO;
import myServer.alice.business.entities.DB.PurchaseDaoImpl;
import myServer.alice.business.entities.Product;
import myServer.alice.business.entities.Purchase;

import java.util.List;

public class PurchasesService {

    private PurchaseDAO purchaseDAO = new PurchaseDaoImpl();


    public void addPurchase(Purchase pr) {
        purchaseDAO.save(pr);
    }

    public void updatePurchase(Purchase pr) {
        purchaseDAO.update(pr);
    }

    public void addPurchaseByProduct(Product prod) {

        purchaseDAO.save(new Purchase(prod.getText(), prod.getPrice()));

    }

    public List<Purchase> getAll() {
        return purchaseDAO.getAll();
    }

    public Purchase findById(int id) {
        return purchaseDAO.findById(id);
    }

}
