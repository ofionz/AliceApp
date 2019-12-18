package myServer.alice.business.entities.DB;

import myServer.alice.business.entities.Balance;

public interface BalanceDAO extends DAO {
    /**
     * Function return current Balance and delete other/older copy of balances
     * if it exists
     * @return  {@link Balance}
     */
    public Balance getBalance();


}
