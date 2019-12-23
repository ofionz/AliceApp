package myServer.alice.business.entities.DB;

import myServer.alice.business.entities.Book;
import myServer.alice.business.entities.Product;

import java.util.List;

public class BooksDaoImpl implements BooksDAO {
    public Book findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(Book.class, id);
    }

    public List<Book> getAll() {
        List<Book> books = (List<Book>) HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("from myServer.alice.business.entities.Book")
                .list();

        return books;
    }

}
