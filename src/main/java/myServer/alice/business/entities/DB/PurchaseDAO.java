package myServer.alice.business.entities.DB;

import myServer.alice.business.entities.Purchase;

import java.util.List;

public interface PurchaseDAO extends DAO {
    public Purchase findById(int id);

    public List<Purchase> getAll();
}
