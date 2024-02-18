import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import edu.yu.cs.com1320.project.stage1.impl.DocumentImpl;
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
}
