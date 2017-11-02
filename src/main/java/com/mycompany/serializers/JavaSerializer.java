package com.mycompany.serializers;

import com.mycompany.models.Author;
import com.mycompany.models.Book;
import com.mycompany.models.OriginalModelsContainer;
import com.mycompany.models.Publisher;
import com.mycompany.models.entities.AuthorEntity;
import com.mycompany.models.entities.BookEntity;
import com.mycompany.models.entities.PublisherEntity;
import com.mycompany.models.entities.creators.AuthorEntitiesCreator;
import com.mycompany.models.entities.creators.BookEntitiesCreator;
import com.mycompany.models.entities.creators.PublisherEntitiesCreator;
import com.mycompany.models.entities.restorators.AuthorsRestorator;
import com.mycompany.models.entities.restorators.BooksRestorator;
import com.mycompany.models.entities.restorators.PublishersRestorator;
import com.mycompany.models.readers.BooksListCreator;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;

public class JavaSerializer implements Serializer {

    private static final Logger log = Logger.getLogger(BooksListCreator.class);

    @Override
    public void serializeObjects(List<Author> authors, List<Book> books, List<Publisher> publishers, String fileWithObjects) throws IOException {
        ObjectOutputStream out =
                new ObjectOutputStream(
                        new FileOutputStream(
                                new File(fileWithObjects)));

        List<AuthorEntity> authorEntities = AuthorEntitiesCreator.getListAuthorEntities(authors);
        List<BookEntity> bookEntities = BookEntitiesCreator.getListBookEntities(books, authorEntities);
        List<PublisherEntity> publisherEntities = PublisherEntitiesCreator.getListPublisherEntities(publishers, bookEntities);

        out.writeObject(authorEntities);
        out.writeObject(bookEntities);
        out.writeObject(publisherEntities);

        out.flush();
        out.close();
    }

    @Override
    public OriginalModelsContainer deserializeObject(String fileWithObjects) throws IOException {
        ObjectInputStream in =
                new ObjectInputStream(
                        new FileInputStream(
                                new File(fileWithObjects)));

        try {
            List<AuthorEntity> authorEntities = (List<AuthorEntity>) in.readObject();
            List<BookEntity> bookEntities = (List<BookEntity>) in.readObject();
            List<PublisherEntity> publisherEntities = (List<PublisherEntity>) in.readObject();

            List<Author> authors = AuthorsRestorator.getListOfAuthors(authorEntities);
            List<Book> books = BooksRestorator.getListOfBooks(bookEntities, authors, authorEntities);
            List<Publisher> publishers = PublishersRestorator.getListOfPublishers(publisherEntities, bookEntities, books);

            return new OriginalModelsContainer(authors, books, publishers);

        }catch (ClassNotFoundException e){
            log.error(e);
        }

        return null;
    }
}
