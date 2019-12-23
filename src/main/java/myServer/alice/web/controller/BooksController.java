package myServer.alice.web.controller;

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



        String urlBook ="";
        if (parseRequestType(request)=="book"){
           String bookName = request.getParameter("name");
           if (bookName!=null){
               if(bookName.equals("english"))urlBook="https://drive.google.com/file/d/1kEQ1LfYT1yEMkD2IUXgbe-2hrifEKpe_/preview";
               else if (bookName.equals("math")) urlBook ="https://drive.google.com/file/d/1tW3--EnkQfMJqBBKqttVLLi4RpuT5Mpe/preview";
           }
        }



        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("book",urlBook);
        templateEngine.process("books", ctx, response.getWriter());


    }

}

