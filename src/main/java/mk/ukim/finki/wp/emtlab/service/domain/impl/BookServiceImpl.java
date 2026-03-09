package mk.ukim.finki.wp.emtlab.service.domain.impl;

import mk.ukim.finki.wp.emtlab.model.domain.Book;
import mk.ukim.finki.wp.emtlab.repository.BookRepository;
import mk.ukim.finki.wp.emtlab.service.domain.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> update(Long id, Book book) {
        return bookRepository.findById(id)
                .map((it) -> {
                    it.setName(book.getName());
                    it.setCategory(book.getCategory());
                    it.setAuthors(book.getAuthors());
                    it.setState(book.getState());
                    it.setAvailableCopies(book.getAvailableCopies());
                    return bookRepository.save(it);
                });
    }

    @Override
    public Optional<Book> deleteById(Long id) {
        return bookRepository.findById(id).map(book -> {
            if (book.getAvailableCopies() == 0 && book.getState().getState().equals("BAD")) {
                bookRepository.delete(book);
                return book;
            } else {
                throw new IllegalStateException("The book cannot be deleted because its condition is still "
                        + book.getState().getState() + " or it still has available copies");
            }
        });
    }

    @Override
    public Optional<Book> rent(Long id) {
        return bookRepository.findById(id).map((book) -> {
            if (book.getAvailableCopies() > 0 && book.getState().getState().equals("GOOD")) {
                book.setAvailableCopies(book.getAvailableCopies() - 1);
            } else {
                throw new IllegalStateException("The book cannot be rented because it's condition is " + book.getState().getState() + " or it does not have any available copies");
            }
            return bookRepository.save(book);
        });
    }
}
