package myServer.alice.business.entities.DB;

import myServer.alice.business.entities.Book;

import java.util.List;

public interface BooksDAO extends DAO{

    public List<Book> getAll();
    public Book findById(int id);

}
