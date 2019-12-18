package myServer.alice.business.entities.DB;

import myServer.alice.business.entities.Balance;

import java.time.LocalDate;
import java.util.List;
import org.apache.log4j.Logger;

public class BalanceDAOImpl implements BalanceDAO {
    private static final Logger log = Logger.getLogger(HibernateSessionFactoryUtil.class);


/*This method selects only one balance from several,
 if it happened because in normal
 mode, the Balance  is only one ore return "0 balance"  if can not find any balance.
*/
    private Balance selectLastBalance(List<Balance> lst) {
        if (lst == null || lst.size() == 0) {
            Balance bl = new Balance();
            bl.setAmount(0);  // set 0 in balance because lst is empty
            bl.setDate(LocalDate.of(1900, 1, 1));
            log.warn("Balance in DB = null, select last balance return 0");
            return bl;
        }

        Balance maxDate = null;

        //here we are looking last time balance and delete older
        for (Balance bal : lst) {

            if (maxDate == null) {
                maxDate = bal;
            } else {
                log.warn("multiple balances !!!");
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


    /**
     * Function return current Balance and delete other/older copy of balances
     * if it exists
     * @return  {@link Balance}
     */

    public Balance getBalance() {
        List<Balance> balance = (List<Balance>) HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("from myServer.alice.business.entities.Balance")
                .list();
        return selectLastBalance(balance);
    }


}
