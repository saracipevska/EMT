package mk.ukim.finki.wp.emtlab.service.domain;

import mk.ukim.finki.wp.emtlab.model.domain.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAllByIds(List<Long> ids);
}
