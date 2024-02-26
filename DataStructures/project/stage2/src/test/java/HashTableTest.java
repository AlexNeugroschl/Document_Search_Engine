import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.stage2.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage2.impl.DocumentStoreImpl;
import edu.yu.cs.com1320.project.stage2.DocumentStore;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

public class HashTableTest{
    @Test
    public void largeTest() throws URISyntaxException, UnsupportedEncodingException, IOException{
        HashTableImpl<String, DocumentStoreImpl> storeTable = new HashTableImpl<>();
        DocumentStoreImpl store1 = new DocumentStoreImpl();
        /*
        DocumentStoreImpl store2 = new DocumentStoreImpl();
        DocumentStoreImpl store3 = new DocumentStoreImpl();
        DocumentStoreImpl store4 = new DocumentStoreImpl();
        DocumentStoreImpl store5 = new DocumentStoreImpl();
        DocumentStoreImpl store6 = new DocumentStoreImpl();
        DocumentStoreImpl store7 = new DocumentStoreImpl();
       */
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        DocumentImpl doc = new DocumentImpl(uri, text);

        store1.put(input, uri, format);
        storeTable.put("store1", store1);
        /*
        store2.put(input, uri, format);
        store3.put(input, uri, format);
        store4.put(input, uri, format);
        store5.put(input, uri, format);
        store6.put(input, uri, format);
        store7.put(input, uri, format);

        storeTable.put("store2", store2);
        storeTable.put("store3", store3);
        storeTable.put("store4", store4);
        storeTable.put("store5", store5);
        storeTable.put("store6", store1);
        storeTable.put("store7", store2);

         */
        assertEquals(1, storeTable.size());
    }
}