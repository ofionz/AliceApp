package myServer.alice.web.controller;

import myServer.alice.business.entities.Book;
import myServer.alice.business.entities.Product;
import myServer.alice.business.entities.Task;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

abstract public class PageContoller {

    private static final Logger log = Logger.getLogger(PageContoller.class);

    /**
     * get id from request, return -1 if something wrong
     * @param request
     * @return ID
     */

    protected int parseRequestId(HttpServletRequest request) {
        int id = -1;
        String req = "";
        if (request != null) {
            try {
                req = request.getParameter("id");
                id = Integer.valueOf(req);
            } catch (NumberFormatException n) {
                return id;
            }
        }
        return id;

    }

    protected String parseRequestType(HttpServletRequest request) {
        if (request != null) {
            String req = request.getParameter("type");
            if (req != null) {
                switch (req) {
                    case "status":
                        return "status";
                    case "delete":
                        return "delete";
                    case "update":
                        return "update";
                    case "add":
                        return "add";
                    case "editbalance":
                        return "editbalance";
                    case "promo":
                        return "promo";
                    case "buy":
                        return "buy";
                    case "updateproduct":
                        return "updateproduct";
                    case "deleteproduct":
                        return "deleteproduct";
                    case "book":
                        return "book";
                    case "deletebook":
                        return "deletebook";
                    case "updatebook":
                        return "updatebook";
                    default:{
                        log.error("error of type parameters in request "+request);
                        return "error";
                    }

                }

            }

            return "";
        }
        log.error("request is null");
        return "error";
    }


    protected Product createProductFromRequest(HttpServletRequest request) {
        Product result = null;

        int poi = 0;
        if (request != null) {
            String text = request.getParameter("text");
            String points = request.getParameter("points");
            if (text != null && points != null) {
                try {
                    poi = Integer.parseInt(points);
                    if (poi < 0) throw new NumberFormatException();

                } catch (NumberFormatException nm) {
                    result = new Product("Neverno kolichestvo ochkov", poi);
                    return result;
                }
                result = new Product(text, poi);

            } else result = new Product("odin iz parametrov = null", poi);

        }
        return result;
    }

    protected Book createBookFromRequest(HttpServletRequest request) {
        Book result = null;

        if (request != null) {
            String name = request.getParameter("name");
            String url = request.getParameter("url");
            if (name != null && url != null) {

                result = new Book(name, url);

            } else result = new Book("odin iz parametrov = null", "");

        }

        return result;
    }






    protected Task createTaskFromRequest(HttpServletRequest request) {
        Task result = null;
        int poi = 0;
        int fin = 0;

        if (request != null) {
            String time = request.getParameter("time");
            String text = request.getParameter("text");
            String points = request.getParameter("points");
            String finePoints = request.getParameter("finepoints");
            boolean [] dayOfWeeks= parseDayOfWeek (request);

            if (time != null && text != null && points != null && finePoints != null) {
                if (time.contains("morning") || time.contains("afternoon") || time.contains("evening") || time.contains("dolg")) {
                    try {
                        poi = Integer.parseInt(points);
                        fin = Integer.parseInt(finePoints);
                        if (poi < 0 || fin < 0) throw new NumberFormatException();
                    } catch (NumberFormatException nm) {
                        result = new Task("Neverno kolichestvo ochkov", LocalDate.now(), false, time, poi, fin,dayOfWeeks);
                        return result;
                    }

                    result = new Task(text, LocalDate.now(), false, time, poi, fin,dayOfWeeks);
                }
            } else result = new Task("odin iz parametrov = null", LocalDate.now(), false, "morning", poi, fin,dayOfWeeks);
        }
        return result;
    }

    private boolean[] parseDayOfWeek(HttpServletRequest request) {
      boolean [] result = new boolean [7];
        for (int i=0;i<7;i++){
           if (request.getParameter("dOW"+i)!=null){
               result[i]=true;
           }
        }
        return result;

    }
}