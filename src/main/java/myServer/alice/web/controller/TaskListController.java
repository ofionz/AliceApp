/*
 * =============================================================================
 *
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package myServer.alice.web.controller;

import myServer.alice.business.entities.Purchase;
import myServer.alice.business.entities.Task;
import myServer.alice.business.services.BalanceService;
import myServer.alice.business.services.PurchasesService;
import myServer.alice.business.services.TaskService;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class TaskListController extends PageContoller implements ImplALiceController {


    public TaskListController() {
        super();
    }


    public void process(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final ServletContext servletContext,
            final ITemplateEngine templateEngine
    )
            throws Exception {

        //When we call task service and balance service initialization begins
        //all tasks and balance after it is updated to the current moment
        final TaskService taskService = new TaskService();
        final BalanceService bs = new BalanceService(taskService);
        final PurchasesService purcServ = new PurchasesService();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        int currentPoints=0;
        int amountOfAllPoints =0;
      int  amountOfAllFinePoints=0;

        LocalTime endTime = LocalTime.of(23,30);

        //request type status - invert status of current task
        if (parseRequestType(request).equals("status") && LocalTime.now().isBefore(endTime)) {
            taskService.invertTaskStatus(parseRequestId(request));
        }

        // promo - it is promocode that was encrypted by BASE-64
        // example "2019-12-25&500" where 2019-12-25 - Local date format date, 500 - it is points
        //promocode is active only one time and only one code to one day

        else if (parseRequestType(request).equals("promo")) {
            String code = request.getParameter("text");
            int amount = decodePromocode(code);
            if (amount != 0 && bs.addToBalanceByPromocode(amount)) {
                String text = "Использован промокод  ";
                purcServ.addPurchase(new Purchase(text, amount));
            }
        }

        //it is pattern Post-redirect-get
        if (!request.getMethod().equalsIgnoreCase("get")) response.sendRedirect("list#"+request.getParameter("dayTime"));

        Map<String, List<Task>> tasks = taskService.getAllCurrentTasks();


        //here we set all context variable
        for (String key : tasks.keySet()) {
            ctx.setVariable(key + "Tasks", tasks.get(key));
            int points = getDayTimePointsAmount(tasks.get(key));
            amountOfAllPoints+=getAmountOfAllPoints(tasks.get(key));
            amountOfAllFinePoints+=getAmountOfAllFinePoints(tasks.get(key));
            currentPoints += points;
            if (points>0){
                ctx.setVariable(key + "AmountPoints", "+" + points+" "+getNumEnding(points));
            }
            else
            ctx.setVariable(key + "AmountPoints",  points+" "+getNumEnding(points));

        }
        ctx.setVariable("curPoints", currentPoints);
        ctx.setVariable("allPoints", amountOfAllPoints);
        ctx.setVariable("allFinePoints", amountOfAllFinePoints);
        ctx.setVariable("balance", bs.getBalance().getAmount()+currentPoints);

        // start thymleaf process
        templateEngine.process("list", ctx, response.getWriter());


    }


    /**
     * decodePromocode is method  where we try decrypt input
     * string by  *Base64 code and test it to valiadation
     *
     * @param promocode - input string of code
     * @return int output points(default =0)
     */
    private int decodePromocode(String promocode) {
        int result = 0;
        try {
            String decodedString = new String(Base64.getDecoder().decode(promocode));
            int index = decodedString.lastIndexOf('&');
            String date = decodedString.substring(0, index);

            if (!LocalDate.parse(date).isEqual(LocalDate.now())) {
                return 0;
            }
            String amount = decodedString.substring(index + 1);
            result = Integer.parseInt(amount);
        } catch (Exception e) {
            //ignore wrong promocode
        }
        return result;

    }

    /**
     * getDayTimePointsAmount is method  where we calculate amount of
     * points in all today tasks
     *
     * @param list - input list
     * @return int output points(default =0)
     */
    private int getDayTimePointsAmount(List<Task> list) {
        int amount = 0;
        for (Task tsk : list) {
            if (tsk.isStatus()) amount += tsk.getPoints();
            else amount-=tsk.getFinepoints();
        }
        return amount;
    }

    private int getAmountOfAllPoints(List<Task> list) {
        int amount = 0;
        for (Task tsk : list) {
            amount += tsk.getPoints();
        }
        return amount;
    }

    private int getAmountOfAllFinePoints(List<Task> list) {
        int amount = 0;
        for (Task tsk : list) {
            amount += tsk.getFinepoints();
        }
        return amount;
    }



    private  String getNumEnding(int num)
    {
        String end="";
                int i=0;
        num = num % 100;
        if (num>=11 && num<=19) {
            end="очков";
        }
        else {
            i = num % 10;
            switch (i)
            {
                case (1): end = "очко"; break;
                case (2):case (3):case (4): end = "очка"; break;
                default: end = "очков";
            }
        }
        return end;
    }


}
