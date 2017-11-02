package com.mycompany.models.entities.creators;

import com.mycompany.models.Publisher;
import com.mycompany.models.entities.BookEntity;
import com.mycompany.models.entities.PublisherEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PublisherEntitiesCreator {
    static public List<PublisherEntity> getListPublisherEntities(List<Publisher> publishers
            , List<BookEntity> bookEntities) {

        if (publishers == null || bookEntities == null) return null;

        AtomicInteger counter = new AtomicInteger(0);

        Map<String, Integer> mapBooksTitleId = bookEntities.stream()
                .collect(Collectors.toMap(BookEntity::getTitle, BookEntity::getId));

        return publishers.stream()
                .map(p -> new PublisherEntity(p, mapBooksTitleId, counter.incrementAndGet()))
                .collect(Collectors.toList());
    }
}
