package myServer.alice.business.services;

import myServer.alice.business.entities.Book;
import myServer.alice.business.entities.DB.BooksDAO;
import myServer.alice.business.entities.DB.BooksDaoImpl;


import java.util.List;

public class BookService {





        private BooksDAO booksDAO = new BooksDaoImpl();


        public void saveBook(Book bk) {
            booksDAO.save(bk);
        }


        public void deleteBook(Book bk) {
            booksDAO.delete(bk);
        }


        public void updateBook(int id, Book bk2 ) {
            Book bk = findById(id);
            bk.setName(bk2.getName());
            bk.setUrl(bk2.getUrl());
            booksDAO.update(bk);

        }

        public List<Book> getAll() {
            return booksDAO.getAll();
        }

        public Book  findById(int id) {
            return booksDAO.findById(id);
        }




}
