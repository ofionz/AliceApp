package myServer.alice.web.controller;

import myServer.alice.business.services.BookService;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BooksController extends PageContoller implements ImplALiceController{




    public BooksController() {
        super();
    }


    public void process(
            final HttpServletRequest request, final HttpServletResponse response,
            final ServletContext servletContext, final ITemplateEngine templateEngine)
            throws Exception {

        BookService bookService = new BookService();

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("allBooks",bookService.getAll());
        templateEngine.process("books", ctx, response.getWriter());


    }

}

