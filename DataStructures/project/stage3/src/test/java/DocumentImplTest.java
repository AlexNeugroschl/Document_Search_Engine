import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;
import edu.yu.cs.com1320.project.stage3.impl.DocumentImpl;
public class DocumentImplTest {
    @Test
    public void docConstructor1Works(){
        URI uri;
        try{
            uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
            if(uri != null) {
                DocumentImpl doc = new DocumentImpl(uri, "Text of doc");
                assertEquals("Text of doc", doc.getDocumentTxt(), "its broked");
            }
        }catch(URISyntaxException e){
            System.out.println("syntax error");
        }

    }
    @Test
    public void docConstructor2Works() throws URISyntaxException, UnsupportedEncodingException {
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        DocumentImpl doc = new DocumentImpl(uri, bytes);
        assertEquals("https://www.geeksforgeeks.org/url-class-java-examples/", doc.getKey().toString(), "Constructor 2 is broked");
    }
    @Test
    public void docConstructor1hashCode() throws URISyntaxException, UnsupportedEncodingException {
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        DocumentImpl doc = new DocumentImpl(uri, text);
        int result = uri.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result;
        assertEquals(true, doc.hashCode() == Math.abs(result));
    }
    @Test
    public void docConstructor1equals() throws URISyntaxException, UnsupportedEncodingException {
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text1 = "This is the text of the doc";
        String text2 = "This is the text of the doc";
        DocumentImpl doc1 = new DocumentImpl(uri, text1);
        DocumentImpl doc2 = new DocumentImpl(uri, text2);
        assertEquals(true, doc1.equals(doc2));
    }
    @Test
    public void getMetadataValueTest() throws URISyntaxException, UnsupportedEncodingException{
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        DocumentImpl doc = new DocumentImpl(uri, text);
        doc.setMetadataValue("Author","Myself");
        doc.setMetadataValue("Illustrator","Also Me");
        doc.setMetadataValue("Reader","Also Also Me");
        assertEquals("Also Me", doc.getMetadataValue("Illustrator"));
    }
}
