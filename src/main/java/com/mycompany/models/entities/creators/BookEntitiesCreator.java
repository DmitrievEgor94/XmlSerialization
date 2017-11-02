package com.mycompany.models.entities.creators;

import com.mycompany.models.Book;
import com.mycompany.models.entities.AuthorEntity;
import com.mycompany.models.entities.BookEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BookEntitiesCreator {
    static public List<BookEntity> getListBookEntities(List<Book> books, List<AuthorEntity> authorEntities) {

        if (books == null) return null;

        Map<String, Integer> mapAuthorNameId = authorEntities.stream()
                .collect(Collectors.toMap(AuthorEntity::getName, AuthorEntity::getId));

        AtomicInteger counter = new AtomicInteger(0);


        return books.stream()
                .map(b -> new BookEntity(b, mapAuthorNameId, counter.incrementAndGet()))
                .collect(Collectors.toList());
    }
}
