package com.mycompany.serializers.xmlformat.sax;

import com.mycompany.models.Author;
import com.mycompany.models.Book;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class GetterAuthorsFromBooks {
    static List<Author> get(List<Book> books) {
        return books.stream()
                .map(Book::getAuthors)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }
}
