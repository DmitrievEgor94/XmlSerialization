package com.mycompany.viewers;

import com.mycompany.models.Author;
import com.mycompany.models.Book;
import com.mycompany.models.Publisher;

import java.util.List;

public class PrinterOnConsole implements Viewer {
    public void show(List<Publisher> publishers) {

        for (Publisher publisher : publishers) {
            System.out.println("Publisher:");
            System.out.printf("Name: %s, Books: \n", publisher.getName());

            for (Book book : publisher.getBooks()) {
                int numberOfSpacesBooks = 3;
                String formatForBook = String.format("%" + numberOfSpacesBooks + "s", " ");

                System.out.println(formatForBook + "Book:");
                System.out.printf(formatForBook + "Title: %s, Publication date: %s, Authors: \n", book.getTitle(), book.getPublicationDate());

                for (Author author : book.getAuthors()) {
                    int numberOfSpacesAuthors = 5;
                    String formatForAuthor = String.format("%" + numberOfSpacesAuthors + "s", " ");

                    System.out.println(formatForAuthor + "Author:");
                    System.out.printf(formatForAuthor + "Name: %s, Birth day: %s, Death day:%s, Sex:%s \n",
                            author.getName(), author.getDayOfBirthday(), author.getDayOfDeath(), author.getSex());
                }
            }
        }
    }
}
