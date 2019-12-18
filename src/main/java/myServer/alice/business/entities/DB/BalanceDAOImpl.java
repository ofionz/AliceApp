package myServer.alice.business.entities.DB;

import myServer.alice.business.entities.Balance;

import java.time.LocalDate;
import java.util.List;


public class BalanceDAOImpl implements BalanceDAO {


    private Balance selectLastBalance(List<Balance> lst) {
        if (lst == null || lst.size() == 0) {
            Balance bl = new Balance();
            bl.setAmount(-99999999);
            bl.setDate(LocalDate.of(1900, 1, 1));
            return bl;
        }
        Balance maxDate = null;

        for (Balance bal : lst) {

            if (maxDate == null) {
                maxDate = bal;
            } else {
                if (maxDate.getDate().compareTo(bal.getDate()) <= 0) {
                    delete(maxDate);
                    maxDate = bal;
                } else {
                    delete(bal);
                }

            }

        }
        return maxDate;
    }


    public Balance getBalance() {


        List<Balance> balance = (List<Balance>) HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("from myServer.alice.business.entities.Balance")
                .list();


        return selectLastBalance(balance);
    }


}
