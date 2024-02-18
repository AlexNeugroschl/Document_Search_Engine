import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.yu.cs.com1320.project.stage1.impl.*;
import edu.yu.cs.com1320.project.stage1.DocumentStore;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

public class DocumentStoreImplTest {
    @Test
    public void monsterTest() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        store.setMetadata(uri, "key1", "value");
        store.setMetadata(uri, "key2", "value");
        assertEquals(true, store.getMetadata(uri, "key1") == store.getMetadata(uri, "key2"));
    }
    @Test
    public void monsterTest2() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        store.setMetadata(uri, "key1", "value");
        store.setMetadata(uri, "key2", "values");
        assertEquals(false, store.getMetadata(uri, "key1") == store.getMetadata(uri, "key2"));
    }
}
