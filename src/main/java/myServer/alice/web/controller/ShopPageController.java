package myServer.alice.web.controller;

import myServer.alice.business.entities.Balance;
import myServer.alice.business.entities.Product;
import myServer.alice.business.entities.Purchase;
import myServer.alice.business.services.BalanceService;
import myServer.alice.business.services.ProductsService;
import myServer.alice.business.services.PurchasesService;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShopPageController extends PageContoller implements ImplALiceController {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, ITemplateEngine templateEngine) throws Exception {


        final BalanceService bs = new BalanceService();
        final ProductsService ps = new ProductsService();
        final PurchasesService purcServ = new PurchasesService();

        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());


        if (parseRequestType(request).equals("buy")) {
            Product product = ps.findById(parseRequestId(request));
            if (product != null) {

                Balance currBalance = bs.getBalance();
                int newAmount = currBalance.getAmount() - product.getPrice();
                if (newAmount >= 0) {
                    purcServ.addPurchaseByProduct(product);
                    currBalance.setAmount(newAmount);
                }
                bs.updateBalance(currBalance);
            }

        }
        //it is pattern Post-redirect-get
        if (!request.getMethod().equalsIgnoreCase("get")) response.sendRedirect("shop");


        List<Purchase> allPurchases = purcServ.getAll();
        Collections.sort(allPurchases);

        //  Get only last 10 purchases
        List<Purchase> lastPurchases = new ArrayList<>();
        for (int i = 0; i < 10 && i < allPurchases.size(); i++) {
            lastPurchases.add(allPurchases.get(i));
        }

        ctx.setVariable("products", ps.getAll());
        ctx.setVariable("balance", bs.getBalance().getAmount());
        ctx.setVariable("purchases", lastPurchases);

        // start thymleaf process
        templateEngine.process("shop", ctx, response.getWriter());

    }
}
