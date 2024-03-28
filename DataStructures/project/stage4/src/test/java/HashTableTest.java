import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.stage4.impl.DocumentImpl;
import edu.yu.cs.com1320.project.stage4.impl.DocumentStoreImpl;
import edu.yu.cs.com1320.project.stage4.DocumentStore;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class HashTableTest{
    @Test
    public void largeTest() throws URISyntaxException, UnsupportedEncodingException, IOException{
        HashTableImpl<String, DocumentStoreImpl> storeTable = new HashTableImpl<>();
        DocumentStoreImpl store1 = new DocumentStoreImpl();
        DocumentStoreImpl store2 = new DocumentStoreImpl();
        DocumentStoreImpl store3 = new DocumentStoreImpl();
        DocumentStoreImpl store4 = new DocumentStoreImpl();
        DocumentStoreImpl store5 = new DocumentStoreImpl();
        DocumentStoreImpl store6 = new DocumentStoreImpl();
        DocumentStoreImpl store7 = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        URI uri2 = new URI("https://www.uri2.com");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        DocumentStore.DocumentFormat otherFormat = DocumentStore.DocumentFormat.BINARY;
        //byte[] Otherbytes = input.readAllBytes();
        //System.out.println(new String(Otherbytes));
        //System.out.println(new String(Otherbytes).isBlank());
        // DocumentImpl doc = new DocumentImpl(uri, text);
        /* byte[] reconstructedBytes = input.readAllBytes();
        String OtherText = new String(reconstructedBytes);
        System.out.println(OtherText);
        System.out.println(OtherText);
        System.out.println(text.isBlank());
        System.out.println(text.isBlank());
         */
        store1.put(input, uri2, format);
        input.reset();
        store1.put(input, uri2, otherFormat);
        input.reset();
        store1.setMetadata(uri2, "author", "me");
        store1.put(input, uri, format);
        input.reset();
        store2.put(input, uri, format);
        input.reset();
        store3.put(input, uri, format);
        input.reset();
        store4.put(input, uri, format);
        input.reset();
        store5.put(input, uri, format);
        input.reset();
        store6.put(input, uri, format);
        input.reset();
        store7.put(input, uri, format);
        storeTable.put("store1", store1);
        storeTable.put("store2", store2);
        storeTable.put("store3", store3);
        storeTable.put("store4", store4);
        storeTable.put("store5", store5);
        storeTable.put("store6", store1);
        storeTable.put("store7", store2);
        //assertEquals("me", store1.getMetadata(uri2, "author"));
        assertEquals(7, storeTable.size());
    }
    @Test
    public void containsKey1() throws URISyntaxException, UnsupportedEncodingException, IOException {
        HashTableImpl<String, String> storeTable = new HashTableImpl<>();
        storeTable.put("key1", "value1");
        storeTable.put("key2", "value2");
        storeTable.put("key3", "value3");
        storeTable.put("key4", "value4");
        assertEquals(true, storeTable.containsKey("key3"));
    }
    @Test
    public void containsKey2() throws URISyntaxException, UnsupportedEncodingException, IOException {
        HashTableImpl<String, String> storeTable = new HashTableImpl<>();
        storeTable.put("key1", "value1");
        storeTable.put("key2", "value2");
        storeTable.put("key3", "value3");
        storeTable.put("key4", "value4");
        assertEquals(false, storeTable.containsKey("key5"));
    }
    @Test
    public void sizetest1() throws URISyntaxException, UnsupportedEncodingException, IOException {
        HashTableImpl<String, String> storeTable = new HashTableImpl<>();
        storeTable.put("key1", "value1");
        storeTable.put("key2", "value2");
        storeTable.put("key3", "value3");
        storeTable.put("key4", "value4");
        assertEquals(4, storeTable.size());
    }
    @Test
    public void sizetest2() throws URISyntaxException, UnsupportedEncodingException, IOException {
        HashTableImpl<String, String> storeTable = new HashTableImpl<>();
        storeTable.put("key1", "value1");
        storeTable.put("key2", "value2");
        storeTable.put("key3", "value3");
        storeTable.put("key4", "value4");
        storeTable.put("key5", "value5");
        storeTable.put("key6", "value6");
        storeTable.put("key7", "value7");
        storeTable.put("key8", "value8");
        storeTable.put("key3", null);
        assertEquals(7, storeTable.size());
    }
    @Test
    public void returnOldValue1() throws URISyntaxException, UnsupportedEncodingException, IOException {
        HashTableImpl<String, String> storeTable = new HashTableImpl<>();
        storeTable.put("key1", "value1");
        storeTable.put("key2", "value2");
        storeTable.put("key3", "value3");
        storeTable.put("key4", "value4");
        storeTable.put("key5", "value5");
        storeTable.put("key6", "value6");
        storeTable.put("key7", "value7");
        storeTable.put("key8", "value8");
        assertEquals("value6", storeTable.put("key6", "value17"));
    }
    @Test
    public void returnOldValue2() throws URISyntaxException, UnsupportedEncodingException, IOException {
        HashTableImpl<String, String> storeTable = new HashTableImpl<>();
        storeTable.put("key1", "value1");
        storeTable.put("key2", "value2");
        storeTable.put("key3", "value3");
        storeTable.put("key4", "value4");
        storeTable.put("key5", "value5");
        storeTable.put("key6", "value6");
        storeTable.put("key7", "value7");
        storeTable.put("key8", "value8");
        assertEquals("value3", storeTable.put("key3", null));
    }
    @Test
    public void manyValues() throws URISyntaxException, UnsupportedEncodingException, IOException {
        HashTableImpl<String, String> storeTable = new HashTableImpl<>();
        storeTable.put("key1", "value1");
        storeTable.put("key2", "value2");
        storeTable.put("key3", "value3");
        storeTable.put("key4", "value4");
        storeTable.put("key5", "value5");
        storeTable.put("key6", "value6");
        storeTable.put("key7", "value7");
        storeTable.put("key8", "value8");
        storeTable.put("key9", "value1");
        storeTable.put("key10", "value10");
        storeTable.put("key11", "value11");
        storeTable.put("key12", "value12");
        storeTable.put("key13", "value13");
        storeTable.put("key14", "value14");
        storeTable.put("key15", "value15");
        storeTable.put("key16", "value16");
        storeTable.put("key17", "value17");
        storeTable.put("key18", "value18");
        storeTable.put("key19", "value19");
        storeTable.put("key20", "value20");
        storeTable.put("key21", "value21");
        storeTable.put("key22", "value22");
        storeTable.put("key23", "value23");
        storeTable.put("key24", "value24");
        storeTable.put("key25", "value25");
        storeTable.put("key26", "value26");
        storeTable.put("key27", "value27");
        storeTable.put("key28", "value28");
        storeTable.put("key29", "value29");
        storeTable.put("key30", "value30");
        storeTable.put("key31", "value31");
        storeTable.put("key32", "value32");
        assertEquals("value17", storeTable.put("key17", null));
    }
    @Test
    public void setReturn() throws URISyntaxException, UnsupportedEncodingException, IOException {
        HashTableImpl<String, String> storeTable = new HashTableImpl<>();
        storeTable.put("key1", "value1");
        storeTable.put("key2", "value2");
        storeTable.put("key3", "value3");
        storeTable.put("key4", "value4");
        storeTable.put("key5", "value5");
        storeTable.put("key6", "value6");
        storeTable.put("key7", "value7");
        storeTable.put("key8", "value8");
        storeTable.put("key9", "value1");
        storeTable.put("key10", "value10");
        storeTable.put("key11", "value11");
        storeTable.put("key12", "value12");
        storeTable.put("key13", "value13");
        storeTable.put("key14", "value14");
        storeTable.put("key15", "value15");
        storeTable.put("key16", "value16");
        storeTable.put("key17", "value17");
        storeTable.put("key18", "value18");
        storeTable.put("key19", "value19");
        storeTable.put("key20", "value20");
        storeTable.put("key21", "value21");
        storeTable.put("key22", "value22");
        storeTable.put("key23", "value23");
        storeTable.put("key24", "value24");
        storeTable.put("key25", "value25");
        storeTable.put("key26", "value26");
        storeTable.put("key27", "value27");
        storeTable.put("key28", "value28");
        storeTable.put("key29", "value29");
        storeTable.put("key30", "value30");
        storeTable.put("key31", "value31");
        storeTable.put("key32", "value32");
        assertEquals(32, storeTable.values().size());
    }
    @Test
    public void returnOldValue3() throws URISyntaxException, UnsupportedEncodingException, IOException {
        HashTableImpl<String, String> storeTable = new HashTableImpl<>();
        storeTable.put("key1", "value1");
        storeTable.put("key2", "value2");
        storeTable.put("key3", "value3");
        storeTable.put("key4", "value4");
        storeTable.put("key5", "value5");
        storeTable.put("key6", "value6");
        String returnString = storeTable.put("key6", "new String");
        assertEquals(true, returnString.equals("value6"));
    }
}