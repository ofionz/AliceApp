package myServer.alice.business.services;

import myServer.alice.business.entities.Balance;
import myServer.alice.business.entities.DB.BalanceDAO;
import myServer.alice.business.entities.DB.BalanceDAOImpl;
import myServer.alice.business.entities.Task;

import java.time.LocalDate;
import java.util.List;


public class BalanceService {
    private BalanceDAO balanceDAO = new BalanceDAOImpl();


    public BalanceService(TaskService taskService) {
        calculate(this, taskService);
    }

    public BalanceService() {
        calculate(this, new TaskService());
    }

    /**Method for adding or subtracting
     * points to the balance by promocode.
     * @param points
     * @return boolean  -if the promotional code was used today return false
     */
    public boolean addToBalanceByPromocode(Integer points) {
        Balance old = balanceDAO.getBalance();
        if (!old.isPromocode()) {
            Balance newBal = old.setAmount(balanceDAO.getBalance().getAmount() + points);
            newBal.setPromocode(true);
            balanceDAO.update(newBal);
            return true;
        }
        return false;
    }

    public void updateBalance(Balance balance) {
        balanceDAO.update(balance);
    }


    public Balance getBalance() {
        return balanceDAO.getBalance();
    }

    /*
        calculate balance while date in DB not current(yesterday date)
        thats mean : all yesterday task points alredy in balance
        but points what user can take today will be calc. only tomorrow
     */
    private void calculate(BalanceService bs, TaskService ts) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Balance balance = bs.getBalance();

        if (balance.getDate().isEqual(yesterday)) return;


        while (balance.getDate().isBefore(yesterday)) {
            LocalDate currentDate =balance.getDate().plusDays(1);
            List<Task> taskList = ts.findBy(currentDate);
            int temp = balance.getAmount();
            balance.setPromocode(false);

            for (Task currTask : taskList) {
                int dayNumber = currentDate.getDayOfWeek().getValue()-1;
                if (currTask.isDayOfWeek(dayNumber)) {
                    if (currTask.isStatus()) {
                        temp += currTask.getPoints();
                    } else temp -= currTask.getFinepoints();
                }
            }
            balance.setDate(balance.getDate().plusDays(1));
            balance.setAmount(temp);
        }
        bs.updateBalance(balance);
    }


}


