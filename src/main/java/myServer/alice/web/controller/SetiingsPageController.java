package myServer.alice.web.controller;

import myServer.alice.business.entities.Task;
import myServer.alice.business.services.BalanceService;
import myServer.alice.business.services.ProductsService;
import myServer.alice.business.services.TaskService;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class SetiingsPageController extends PageContoller implements ImplALiceController {


    public SetiingsPageController() {
        super();
    }


    public void process(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final ServletContext servletContext,
            final ITemplateEngine templateEngine)
            throws Exception {


        final TaskService taskService = new TaskService();
        final BalanceService bs = new BalanceService(taskService);
        final ProductsService ps = new ProductsService();
        String textPromocode = "Промокод активен один день!";
        boolean statusOfExistencePromocode = false;


        //Here we catch operation type in settings page
        switch (parseRequestType(request)) {
            case "delete": {
                if (taskService.findBy(LocalDate.now()).size() > 1)
                    taskService.deleteFromCurrentTasks(parseRequestId(request));
            }
            break;

            //operation update older version of task or create NEW TASK if it doesn`t exist
            case "update": {
                int id = parseRequestId(request);
                if (id < 0) taskService.saveTask(createTaskFromRequest(request));
                else taskService.updateTaskByOtherTask(id, createTaskFromRequest(request));
            }
            break;
            // in operation update product we can create new or edit old products
            case "updateproduct": {
                int id = parseRequestId(request);
                if (id < 0) ps.saveProduct(createProductFromRequest(request));
                else ps.updateProductByOtherProduct(id, createProductFromRequest(request));

            }
            break;

            // here we init promocode variable by current promocode
            case "promo": {
                if (request != null && request.getParameter("text") != null) {

                    statusOfExistencePromocode = true;
                    textPromocode = Base64.getEncoder().encodeToString((LocalDate.now() + "&" + request.getParameter("text")).getBytes());
                }

            }
            break;
            case "deleteproduct": {
                ps.deleteProduct(ps.findById(parseRequestId(request)));
            }
            break;
            //here we can manually edit balance
            case "editbalance":
                bs.updateBalance(bs.getBalance().setAmount(parseBalance(request)));
        }

        //post - redirect - get
        if ((!request.getMethod().equalsIgnoreCase("get")) && !statusOfExistencePromocode)
            response.sendRedirect("settings");

        Map<String, List<Task>> tasks = taskService.getAllCurrentTasks();

        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

        ctx.setVariable("morningTasks", tasks.get("morning"));
        ctx.setVariable("afternoonTasks", tasks.get("afternoon"));
        ctx.setVariable("dolgTasks", tasks.get("dolg"));
        ctx.setVariable("eveningTasks", tasks.get("evening"));
        ctx.setVariable("balance", bs.getBalance());
        ctx.setVariable("products", ps.getAll());
        ctx.setVariable("promo", textPromocode);

        templateEngine.process("settings", ctx, response.getWriter());

    }

    private Integer parseBalance(HttpServletRequest request) {
        int balance = 0;
        String req = "";
        if (request != null) {
            req = request.getParameter("amount");
            try {
                balance = Integer.valueOf(req);
            } catch (NumberFormatException e) {
                return balance;
            }

        }
        return balance;

    }


}




