package com.mycompany.serializers.xmlformat.dom;

import com.mycompany.models.Author;
import com.mycompany.models.Book;
import com.mycompany.models.OriginalModelsContainer;
import com.mycompany.models.Publisher;
import com.mycompany.serializers.Serializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class XmlDomSerializer implements Serializer {

    private static final String PUBLISHER_CLASS_NAME = "Publisher";

    @Override
    public void serializeObjects(List<Author> authors, List<Book> books, List<Publisher> publishers, String fileWithObjects) throws IOException {
        throw new UnsupportedOperationException(this.getClass() + " doesn't support serializing!");
    }

    @Override
    public OriginalModelsContainer deserializeObject(String fileWithObjects) throws IOException, ClassNotFoundException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileWithObjects));

            Element documentElement = doc.getDocumentElement();

            List<Publisher> publishers = new ArrayList<>();

            for (int i = 0; i < documentElement.getChildNodes().getLength(); i++) {
                Node node = documentElement.getChildNodes().item(i);

                if (PUBLISHER_CLASS_NAME.equals(node.getNodeName())) {
                    Publisher publisher = PublisherGetter.get(node.getChildNodes());

                    publishers.add(publisher);
                }
            }

            List<Book> books = publishers.stream()
                    .map(Publisher::getBooks)
                    .flatMap(Collection::stream)
                    .distinct()
                    .collect(Collectors.toList());

            List<Author> authors = books.stream()
                    .map(Book::getAuthors)
                    .flatMap(Collection::stream)
                    .distinct()
                    .collect(Collectors.toList());

            return new OriginalModelsContainer(authors, books, publishers);
        } catch (ParserConfigurationException | SAXException e) {
            System.out.println(e);
        }

        return null;
    }


}
