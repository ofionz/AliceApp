package myServer.alice.web.controller;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//very simple authentication type

public class LoginController extends PageContoller implements ImplALiceController {
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response, ServletContext servletContext, ITemplateEngine templateEngine) throws Exception {
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());


        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String type = request.getParameter("type");
        if (type != null) {
            request.getSession().setAttribute("admin", "false");
            response.sendRedirect("../alice");
        }
        if (login != null && password != null) {
            if (login.equals("admin") && password.equals("orange")) {
                request.getSession().setAttribute("admin", "true");
                response.sendRedirect("settings");
            } else response.sendRedirect("../alice");

        }


        templateEngine.process("login", ctx, response.getWriter());
    }
}
