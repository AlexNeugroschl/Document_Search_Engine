import edu.yu.cs.com1320.project.stage6.Document;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.yu.cs.com1320.project.stage6.impl.*;
import edu.yu.cs.com1320.project.stage6.DocumentStore;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DocumentStoreImplTest {
    @Test
    public void initialTest() throws URISyntaxException, UnsupportedEncodingException, IOException {
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
    public void initialTest2() throws URISyntaxException, UnsupportedEncodingException, IOException {
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
    @Test
    public void setMetadatatestIAE1()throws URISyntaxException, UnsupportedEncodingException, IOException{
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        String key = null;
        assertThrows(IllegalArgumentException.class,()->{
            store.setMetadata(uri, key, "new text of doc");;
        });
    }
    @Test
    public void setMetadatatestIAE2()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        assertThrows(IllegalArgumentException.class, () -> {
            store.setMetadata(uri, "", "new text of doc");
            ;
        });
    }
    @Test
    public void setMetadatatestIAE3()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        URI badURI = null;
        assertThrows(IllegalArgumentException.class, () -> {
            store.setMetadata(badURI, "key", "new text of doc");
            ;
        });
    }
    @Test
    public void setMetadataTestIAE4()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        URI badURI = URI.create("");;
        assertThrows(IllegalArgumentException.class, () -> {
            store.setMetadata(badURI, "key", "new text of doc");
            ;
        });
    }
    @Test
    public void setMetadataTestIAE5()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        URI badURI = new URI("foo://example.com:8042/over/there?name=ferret#nose");
        assertThrows(IllegalArgumentException.class, () -> {
            store.setMetadata(badURI, "key", "new text of doc");
            ;
        });
    }
    @Test
    public void setMetadataTestReturnOldValue()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        store.setMetadata(uri, "key", "original text of doc");
        assertEquals("original text of doc", store.setMetadata(uri, "key", "new text of doc"));
    }
    @Test
    public void getMetadataTestIAE1()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        store.setMetadata(uri, "key", "original text of doc");
        URI badURI = new URI("foo://example.com:8042/over/there?name=ferret#nose");
        assertThrows(IllegalArgumentException.class, () -> {
            store.getMetadata(badURI, "key");
        });
    }
    @Test
    public void getMetadataTestIAE2()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        store.setMetadata(uri, "key", "original text of doc");
        URI badURI = URI.create("");
        assertThrows(IllegalArgumentException.class, () -> {
            store.getMetadata(badURI, "key");
            ;
        });
    }
    @Test
    public void getMetadataTestIAE3()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        store.setMetadata(uri, "key", "original text of doc");
        assertThrows(IllegalArgumentException.class, () -> {
            store.getMetadata(uri, "");
            ;
        });
    }
    @Test
    public void getMetadataTestIAE4()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        store.setMetadata(uri, "key", "original text of doc");
        String key = null;
        assertThrows(IllegalArgumentException.class, () -> {
            store.getMetadata(uri, key);
            ;
        });
    }
    @Test
    public void getMetadataTestIAE5()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        store.setMetadata(uri, "key", "original text of doc");
        String key = null;
        assertThrows(IllegalArgumentException.class, () -> {
            store.getMetadata(uri, key);
            ;
        });
    }
    @Test
    public void getMetadataTestIAE6()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        store.setMetadata(uri, "key", "original text of doc");
        URI badURI = null;
        assertThrows(IllegalArgumentException.class, () -> {
            store.getMetadata(badURI, "key");
            ;
        });
    }
    @Test
    public void getMetadataTestReturnnull()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        store.setMetadata(uri, "key", "original text of doc");
        assertEquals(null, store.getMetadata(uri, "newKey"));
    }
    @Test
    public void getMetadataTestReturnValue()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        store.setMetadata(uri, "key", "original text of doc");
        assertEquals("original text of doc", store.getMetadata(uri, "key"));
    }
    @Test
    public void putTestIAE1()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        URI badURI = null;
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        assertThrows(IllegalArgumentException.class, () -> {
            store.put(input, badURI, format);
            ;
        });
    }
    @Test
    public void putTestIAE2()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        URI badURI = URI.create("");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        assertThrows(IllegalArgumentException.class, () -> {
            store.put(input, badURI, format);
            ;
        });
    }
    @Test
    public void putTestIAE3()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        URI badURI = URI.create("");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = null;
        assertThrows(IllegalArgumentException.class, () -> {
            store.put(input, uri, format);
            ;
        });
    }
   @Test
   public void putTestReturns0()throws URISyntaxException, UnsupportedEncodingException, IOException {
       DocumentStoreImpl store = new DocumentStoreImpl();
       URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
       URI badURI = URI.create("");
       String text = "This is the text of the doc";
       byte[] bytes = text.getBytes();
       ByteArrayInputStream input = new ByteArrayInputStream(bytes);
       DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
       assertEquals(0,store.put(input, uri, format));
   }
    @Test
    public void putTestReturnsHash1()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        URI badURI = URI.create("");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        String otherText = "This is different text";
        byte[] otherBytes = otherText.getBytes();
        ByteArrayInputStream otherInput = new ByteArrayInputStream(otherBytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        DocumentImpl doc = new DocumentImpl(uri, text, null);
        int docHash = doc.hashCode();
        assertEquals(docHash, store.put(otherInput, uri, format));
    }
    @Test
    public void putTestReturnsHash2()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        String otherText = "This is different text";
        byte[] otherBytes = otherText.getBytes();
        ByteArrayInputStream otherInput = new ByteArrayInputStream(otherBytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        DocumentImpl doc = (DocumentImpl) store.get(uri);
        //DocumentImpl doc = new DocumentImpl(uri, text);
        int docHash = doc.hashCode();
        assertEquals(docHash, store.put(otherInput, uri, format));
    }
    @Test
    public void putTestInputNull1()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        DocumentImpl doc = new DocumentImpl(uri, text, null);
        int docHash = doc.hashCode();
        ByteArrayInputStream nullInput = null;
        store.put(input, uri, format);
        assertEquals(docHash,store.put(nullInput, uri, format));
    }
    @Test
    public void putTestInputNull2()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        URI badURI = URI.create("");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = null;
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        assertEquals(0, store.put(input, uri, format));
    }
    @Test
    public void getTest()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        DocumentImpl doc = new DocumentImpl(uri, text, null);
        assertEquals(true, doc.hashCode() == store.get(uri).hashCode());
    }
    @Test
    public void deleteTest1()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("http://www.geeksforgeeks.org/urlclassjavaexamples");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        assertEquals(true, store.delete(uri) && store.get(uri) == null);
    }
    @Test
    public void deleteTest2()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        assertEquals(false, store.delete(uri));
    }
    @Test
    public void deleteTest3()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("http://www.geeksforgeeks.org/url-class-java-examples");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        ByteArrayInputStream otherInput = null;
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        store.put(otherInput, uri, format);
        assertEquals(null, store.get(uri));
    }
    @Test
    public void properHashTest() throws URISyntaxException, UnsupportedEncodingException, IOException{
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        DocumentImpl doc = new DocumentImpl(uri, text, null);
        int docHash = doc.hashCode();
        assertEquals(docHash, 1632964363);
    }
    @Test
    public void setandgetMetadataTest() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        store.setMetadata(uri, "key1", "value1");
        store.setMetadata(uri, "key2", "value2");
        DocumentImpl doc = (DocumentImpl)store.get(uri);
        assertEquals("value1", doc.getMetadataValue("key1"));
    }
    @Test
    public void simpleUndoPut() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri, format);
        store.undo();
        assertEquals(null, store.get(uri));
    }
    @Test
    public void harderUndoPut() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("www.test10.com");
        URI uri2 = new URI("www.test20.com");
        URI uri3 = new URI("www.test30.com");
        URI uri4 = new URI("www.test40.com");
        URI uri5 = new URI("www.test50.com");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri1, format);
        input.reset();
        store.put(input, uri2, format);
        input.reset();
        store.put(input, uri3, format);
        input.reset();
        store.put(input, uri4, format);
        input.reset();
        store.put(input, uri5, format);
        store.undo(uri4);
        assertEquals(null, store.get(uri4));
    }
    @Test
    public void harderUndoPut2() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri1, format);
        input.reset();
        store.put(input, uri2, format);
        input.reset();
        store.put(input, uri3, format);
        input.reset();
        store.put(input, uri4, format);
        input.reset();
        store.put(input, uri5, format);
        store.undo(uri4);
        assertEquals("This is the text of the doc", store.get(uri5).getDocumentTxt());
    }
    @Test
    public void undoPutReplace() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text = "This is the text of the doc";
        String otherText = "OTHER STRING TEXT";
        byte[] bytes = text.getBytes();
        byte[] otherBytes = otherText.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        ByteArrayInputStream otherInput = new ByteArrayInputStream(otherBytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri1, format);
        input.reset();
        store.put(input, uri2, format);
        input.reset();
        store.put(input, uri3, format);
        input.reset();
        store.put(input, uri4, format);
        input.reset();
        store.put(input, uri5, format);
        input.reset();
        store.put(otherInput, uri4, format);
        store.undo(uri4);
        assertEquals("This is the text of the doc", store.get(uri4).getDocumentTxt());
    }
    @Test
    public void undoPutReplace2() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text = "This is the text of the doc";
        String otherText = "OTHER STRING TEXT";
        byte[] bytes = text.getBytes();
        byte[] otherBytes = otherText.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        ByteArrayInputStream otherInput = new ByteArrayInputStream(otherBytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri1, format);
        input.reset();
        store.put(input, uri2, format);
        input.reset();
        store.put(input, uri3, format);
        input.reset();
        store.put(otherInput, uri4, format);
        input.reset();
        store.put(input, uri5, format);
        input.reset();
        store.put(input, uri4, format);
        store.undo(uri4);
        assertEquals("OTHER STRING TEXT", store.get(uri4).getDocumentTxt());
    }
    @Test
    public void undoDelete() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("www.test11.com");
        URI uri2 = new URI("www.test21.com");
        URI uri3 = new URI("www.test31.com");
        URI uri4 = new URI("www.test41.com");
        URI uri5 = new URI("www.test51.com");
        String text = "This is the text of the doc";
        String otherText = "OTHER STRING TEXT";
        byte[] bytes = text.getBytes();
        byte[] otherBytes = otherText.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        ByteArrayInputStream otherInput = new ByteArrayInputStream(otherBytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(otherInput, uri1, format);
        otherInput.reset();
        store.put(otherInput, uri2, format);
        otherInput.reset();
        store.put(input, uri3, format);
        input.reset();
        store.delete(uri3);
        store.put(otherInput, uri4, format);
        otherInput.reset();
        store.put(otherInput, uri5, format);
        store.undo(uri3);
        assertEquals("This is the text of the doc", store.get(uri3).getDocumentTxt());
    }
    @Test
    public void undoSetMetadata1() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text = "This is the text of the doc";
        String otherText = "OTHER STRING TEXT";
        byte[] bytes = text.getBytes();
        byte[] otherBytes = otherText.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        ByteArrayInputStream otherInput = new ByteArrayInputStream(otherBytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri1, format);
        input.reset();
        store.put(input, uri2, format);
        input.reset();
        store.put(input, uri3, format);
        store.setMetadata(uri3, "Author", "Me");
        store.setMetadata(uri3, "Author", "It wasn't really me");
        input.reset();
        store.put(input, uri4, format);
        input.reset();
        store.put(input, uri5, format);
        store.undo(uri3);
        assertEquals("Me", store.getMetadata(uri3, "Author"));
    }
    @Test
    public void undoSetMetadata2() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text = "This is the text of the doc";
        String otherText = "OTHER STRING TEXT";
        byte[] bytes = text.getBytes();
        byte[] otherBytes = otherText.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        ByteArrayInputStream otherInput = new ByteArrayInputStream(otherBytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri1, format);
        input.reset();
        store.put(input, uri2, format);
        input.reset();
        store.put(input, uri3, format);
        store.setMetadata(uri3, "Author", "Me");
        input.reset();
        store.put(input, uri4, format);
        input.reset();
        store.put(input, uri5, format);
        store.undo(uri3);
        assertEquals(null, store.getMetadata(uri3, "Author"));
    }
    @Test
    public void undoStackIntact1() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("www.test12.com");
        URI uri2 = new URI("www.test22.com");
        URI uri3 = new URI("www.test32.com");
        URI uri4 = new URI("www.test42.com");
        URI uri5 = new URI("www.test52.com");
        String text = "This is the text of the doc";
        byte[] bytes = text.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri1, format);
        input.reset();
        store.put(input, uri2, format);
        input.reset();
        store.put(input, uri3, format);
        store.setMetadata(uri3, "Author", "Me");
        input.reset();
        store.put(input, uri4, format);
        input.reset();
        store.put(input, uri5, format);
        store.undo(uri3);
        store.undo();
        assertEquals(null, store.get(uri5));
    }
    @Test
    public void undoStackIntact2() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text = "This is the text of the doc";
        String otherText = "OTHER STRING TEXT";
        byte[] bytes = text.getBytes();
        byte[] otherBytes = otherText.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        ByteArrayInputStream otherInput = new ByteArrayInputStream(otherBytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri1, format);
        input.reset();
        store.put(input, uri2, format);
        input.reset();
        store.put(input, uri3, format);
        store.setMetadata(uri3, "Author", "Me");
        input.reset();
        store.put(input, uri4, format);
        input.reset();
        store.put(input, uri5, format);
        store.undo(uri2);
        store.undo(uri4);
        assertEquals(null, store.get(uri4));
    }
    @Test
    public void undoStackIntact3() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text = "This is the text of the doc";
        String otherText = "OTHER STRING TEXT";
        byte[] bytes = text.getBytes();
        byte[] otherBytes = otherText.getBytes();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        ByteArrayInputStream otherInput = new ByteArrayInputStream(otherBytes);
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        store.put(input, uri1, format);
        input.reset();
        store.put(input, uri2, format);
        input.reset();
        store.put(input, uri3, format);
        input.reset();
        store.put(input, uri4, format);
        input.reset();
        store.put(input, uri5, format);
        store.undo(uri2);
        store.undo(uri4);
        assertEquals("www.test3.com", store.get(uri3).getKey().toString());
    }
    @Test
    public void searchTest() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        String text5 = "This is the text of doc5";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format);
        store.put(input2, uri2, format);
        store.put(input3, uri3, format);
        store.put(input4, uri4, format);
        store.put(input5, uri5, format);
        List<Document> docs = store.search("is");
        assertEquals(5, docs.size());
    }
    @Test
    public void deleteAllTest() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        String text5 = "This is the text of doc5";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format);
        store.put(input2, uri2, format);
        store.put(input3, uri3, format);
        store.put(input4, uri4, format);
        store.put(input5, uri5, format);
        Set<URI> deleted = store.deleteAll("doc1");
        assertEquals(1, deleted.size());
    }
    @Test
    public void searchByPrefixTest() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        String text5 = "This is the text of doc5";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format);
        store.put(input2, uri2, format);
        store.put(input3, uri3, format);
        store.put(input4, uri4, format);
        store.put(input5, uri5, format);
        List<Document> searched = store.searchByPrefix("doc");
        assertEquals(5, searched.size());
    }
    @Test
    public void searchByPrefixTest2() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "This is the text of doc3minus2";
        String text2 = "This is the text of doc3minus1";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc3and1";
        String text5 = "This is the text of doc3and2";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format);
        store.put(input2, uri2, format);
        store.put(input3, uri3, format);
        store.put(input4, uri4, format);
        store.put(input5, uri5, format);
        List<Document> searched = store.searchByPrefix("doc3a");
        assertEquals(2, searched.size());
    }
    @Test
    public void weirdText() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "this This iS is i%s the the the text textes textingers o6f o$f of# dddoc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        String text5 = "This is the text of doc5";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format);
        store.put(input2, uri2, format);
        store.put(input3, uri3, format);
        store.put(input4, uri4, format);
        store.put(input5, uri5, format);
        List<Document> searched = store.searchByPrefix("this");
        assertEquals(1, searched.size());
    }
    @Test
    public void reproducingBug1() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "this This iS is i%s the the the text textes textingers o6f o$f of# ddd$oc1";
        String text2 = "This is th[e text of doc2";
        String text3 = "This is *the text of doc3";
        String text4 = "This is t$$$h)e text of doc4";
        String text5 = "This is *t^&h$%e$$ text of doc5";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format);
        store.put(input2, uri2, format);
        store.put(input3, uri3, format);
        store.put(input4, uri4, format);
        store.put(input5, uri5, format);
        List<Document> searched1 = store.searchByPrefix("the");
        List<Document> searched2 = store.searchByPrefix("doc2");
        List<Document> searched3 = store.searchByPrefix("iS");
        List<Document> searched4 = store.searchByPrefix("dddo");
        List<Document> searched5 = store.searchByPrefix("text");
        List<Document> searched6 = store.searchByPrefix("i");
        List<Document> searched7 = store.searchByPrefix("o");
        assertEquals(5, searched1.size());
    }
    @Test
    public void reproducingBug2() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format = DocumentStore.DocumentFormat.TXT;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "this This iS is i%s the the the text textes textingers o6f o$f of# ddd$oc1";
        String text2 = "This Is the text of doc2";
        String text3 = "This is? the text of doc3";
        String text4 = "This the text of doc4";
        String text5 = "This ^is the text of doc5";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format);
        store.put(input2, uri2, format);
        store.put(input3, uri3, format);
        store.put(input4, uri4, format);
        store.put(input5, uri5, format);
        assertEquals(3, store.deleteAllWithPrefix("is").size());
    }
    @Test
    public void maxDocCountWorks() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format1 = DocumentStore.DocumentFormat.TXT;
        DocumentStore.DocumentFormat format2 = DocumentStore.DocumentFormat.BINARY;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        String text5 = "This is the text of doc5";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format1);
        store.put(input2, uri2, format2);
        store.put(input3, uri3, format1);
        store.put(input4, uri4, format2);
        store.put(input5, uri5, format1);
        store.get(uri1);
        store.get(uri4);
        store.setMaxDocumentCount(3);
        assertEquals(true, store.get(uri2) != null && store.get(uri3) != null && store.get(uri4) != null && store.get(uri1) != null);
    }
    @Test
    public void maxBinaryCountWorks1() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format1 = DocumentStore.DocumentFormat.TXT;
        DocumentStore.DocumentFormat format2 = DocumentStore.DocumentFormat.BINARY;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        String text5 = "This is the text of doc5";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format1);
        store.put(input2, uri2, format2);
        store.put(input3, uri3, format1);
        store.put(input4, uri4, format2);
        store.put(input5, uri5, format1);
        store.get(uri1);
        store.get(uri4);
        store.setMaxDocumentBytes(25);
        assertEquals(true, store.get(uri4) != null && store.get(uri1) != null && store.get(uri3) != null && store.get(uri2) != null);
    }
    @Test
    public void maxBinaryCountWorks2() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format1 = DocumentStore.DocumentFormat.TXT;
        DocumentStore.DocumentFormat format2 = DocumentStore.DocumentFormat.BINARY;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        String text5 = "This is the text of doc5 but bigger";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format1);
        store.put(input2, uri2, format2);
        store.put(input3, uri3, format1);
        store.put(input4, uri4, format2);
        store.get(uri1);
        store.get(uri4);
        store.setMaxDocumentBytes(25);
        assertThrows(IllegalArgumentException.class , () -> store.put(input5, uri5, format1));
    }
    @Test
    public void maxDocWorksOnUndo() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format1 = DocumentStore.DocumentFormat.TXT;
        DocumentStore.DocumentFormat format2 = DocumentStore.DocumentFormat.BINARY;
        URI uri1 = new URI("www.test1121.com/doc");
        URI uri2 = new URI("www.test2121.com/doc");
        URI uri3 = new URI("www.test3121.com/doc");
        URI uri4 = new URI("www.test4121.com/doc");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        store.put(input1, uri1, format1);
        store.put(input2, uri2, format2);
        store.put(input3, uri3, format1);
        store.put(input4, uri4, format2);
        store.get(uri1);
        store.get(uri4);
        store.deleteAllWithPrefix("is");
        store.setMaxDocumentBytes(25);
        store.undo();
        assertEquals(2, store.search("is").size());
    }
    @Test
    public void tryingToRecreateBug1() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format1 = DocumentStore.DocumentFormat.TXT;
        DocumentStore.DocumentFormat format2 = DocumentStore.DocumentFormat.BINARY;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        String text5 = "This is the text of doc5";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format1);
        store.put(input2, uri2, format2);
        store.put(input3, uri3, format1);
        store.put(input4, uri4, format2);
        store.put(input5, uri5, format1);
        store.get(uri1);
        store.get(uri4);
        store.setMaxDocumentBytes(50);
        store.undo();
        assertEquals(2, store.search("is").size());
    }
    @Test
    public void tryingToRecreateBug2() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format1 = DocumentStore.DocumentFormat.TXT;
        DocumentStore.DocumentFormat format2 = DocumentStore.DocumentFormat.BINARY;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        String text5 = "This is the text of doc5";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format1);
        store.put(input2, uri2, format2);
        store.setMaxDocumentBytes(25);
        store.put(input3, uri3, format1);
        store.put(input4, uri4, format2);
        store.put(input5, uri5, format1);
        assertEquals(3, store.search("is").size());
    }
    @Test
    public void tryingToRecreateBug3() throws URISyntaxException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri1 = new URI("www.test1.com");
        String text1 = "This is the text of doc1";
        byte[] bytes1 = text1.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        store.put(input1, uri1, DocumentStore.DocumentFormat.TXT);
        store.setMetadata(uri1, "key1", "value1");
        store.setMaxDocumentBytes(20);
        assertEquals(1, store.search("is").size());
    }
    @Test
    public void tryingToRecreateSecondBug1() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format1 = DocumentStore.DocumentFormat.TXT;
        DocumentStore.DocumentFormat format2 = DocumentStore.DocumentFormat.BINARY;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        String text5 = "This is the text of doc5";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format1);
        store.put(input2, uri2, format2);
        store.put(input3, uri3, format1);
        store.put(input4, uri4, format2);
        store.put(input5, uri5, format1);
        store.setMetadata(uri1, "key", "value");
        store.setMetadata(uri3, "key", "value");
        store.setMetadata(uri5, "key", "value");
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertEquals(3, store.searchByMetadata(map).size());
    }
    @Test
    public void tryingToRecreateSecondBug2() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format1 = DocumentStore.DocumentFormat.TXT;
        DocumentStore.DocumentFormat format2 = DocumentStore.DocumentFormat.BINARY;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        String text5 = "This is the text of doc5";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format1);
        store.put(input2, uri2, format2);
        store.put(input3, uri3, format1);
        store.put(input4, uri4, format2);
        store.put(input5, uri5, format1);
        store.setMetadata(uri1, "key", "value");
        store.setMetadata(uri3, "key", "value");
        store.setMetadata(uri5, "key", "value");
        store.setMetadata(uri3, "key", null);
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertEquals(2, store.searchByMetadata(map).size());
    }
    @Test
    public void tryingToRecreateSecondBug3() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format1 = DocumentStore.DocumentFormat.TXT;
        DocumentStore.DocumentFormat format2 = DocumentStore.DocumentFormat.BINARY;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        String text5 = "This is the text of doc5";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format1);
        store.put(input2, uri2, format2);
        store.put(input3, uri3, format1);
        store.put(input4, uri4, format2);
        store.put(input5, uri5, format1);
        store.setMetadata(uri1, "key", "value");
        store.setMetadata(uri1, "key", "wrongvalue");
        store.setMetadata(uri1, "key", null);
        store.setMetadata(uri1, "wrongkey1", "value");
        store.setMetadata(uri1, "wrongkey2", "value");
        store.setMetadata(uri1, "wrongkey2", null);
        store.setMetadata(uri3, "key", "wrongvalue");
        store.setMetadata(uri5, "key", "value");
        store.setMetadata(uri3, "key", "value");
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertEquals(2, store.searchByMetadata(map).size());
    }
    @Test
    public void tryingToRecreateSecondBug4() throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        DocumentStore.DocumentFormat format1 = DocumentStore.DocumentFormat.TXT;
        DocumentStore.DocumentFormat format2 = DocumentStore.DocumentFormat.BINARY;
        URI uri1 = new URI("www.test1.com");
        URI uri2 = new URI("www.test2.com");
        URI uri3 = new URI("www.test3.com");
        URI uri4 = new URI("www.test4.com");
        URI uri5 = new URI("www.test5.com");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        String text5 = "This is the text of doc5";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        store.put(input1, uri1, format1);
        store.put(input2, uri2, format2);
        store.put(input3, uri3, format1);
        store.put(input4, uri4, format2);
        store.put(input5, uri5, format1);
        store.setMetadata(uri1, "key", "value");
        store.setMetadata(uri5, "key", "value");
        store.setMetadata(uri3, "key", "value");
        store.setMaxDocumentCount(2);
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        assertEquals(3, store.searchByMetadata(map).size());
    }
    @Test
    public void testSerialization1() throws URISyntaxException, IOException{
        DocumentStoreImpl store = new DocumentStoreImpl();
        store.setMaxDocumentCount(1);
        DocumentStore.DocumentFormat format1 = DocumentStore.DocumentFormat.TXT;
        URI uri1 = new URI("http://www.test1.com/test1/test12");
        URI uri2 = new URI("http://www.test2.com/test2/test22");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        store.put(input1, uri1, format1);
        store.put(input2, uri2, format1);
        assertEquals(2, store.search("is").size());
    }
    @Test
    public void testSerialization2() throws URISyntaxException, IOException{
        DocumentStoreImpl store = new DocumentStoreImpl();
        store.setMaxDocumentCount(1);
        DocumentStore.DocumentFormat format1 = DocumentStore.DocumentFormat.TXT;
        URI uri1 = new URI("http://www.test1.com/test1/test12");
        URI uri2 = new URI("http://www.test2.com/test2/test22");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        store.put(input1, uri1, format1);
        store.put(input2, uri2, format1);
        assertEquals(1, store.search("doc1").size());
    }
    @Test
    public void serializationFromNonDefaultBaseDir1() throws URISyntaxException, IOException{
        DocumentStoreImpl store = new DocumentStoreImpl(new File("Alex/is/almost/done"));
        store.setMaxDocumentCount(1);
        DocumentStore.DocumentFormat format1 = DocumentStore.DocumentFormat.TXT;
        URI uri1 = new URI("http://www.test1.com/test1/test12");
        URI uri2 = new URI("http://www.test2.com/test2/test22");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        store.put(input1, uri1, format1);
        store.put(input2, uri2, format1);
        assertEquals(1, store.search("doc1").size());
    }
    @Test
    public void largeTest() throws URISyntaxException, IOException{
        DocumentStoreImpl store = new DocumentStoreImpl(new File("I/am/done"));
        store.setMaxDocumentCount(1);
        DocumentStore.DocumentFormat format1 = DocumentStore.DocumentFormat.TXT;
        URI uri1 = new URI("http://www.test1.com/test1/test12");
        URI uri2 = new URI("http://www.test2.com/test2/test22");
        URI uri3 = new URI("http://www.test3.com/test3/test32");
        URI uri4 = new URI("http://www.test4.com/test4/test42");
        URI uri5 = new URI("http://www.test5.com/test5/test52");
        URI uri6 = new URI("http://www.test6.com/test6/test62");
        URI uri7 = new URI("http://www.test7.com/test7/test72");
        URI uri8 = new URI("http://www.test8.com/test8/test82");
        URI uri9 = new URI("http://www.test9.com/test9/test92");
        URI uri10 = new URI("http://www.test10.com/test10/test102");
        String text1 = "This is the text of doc1";
        String text2 = "This is the text of doc2";
        String text3 = "This is the text of doc3";
        String text4 = "This is the text of doc4";
        String text5 = "This is the text of doc5";
        String text6 = "This is the text of doc6";
        String text7 = "This is the text of doc7";
        String text8 = "This is the text of doc8";
        String text9 = "This is the text of doc9";
        String text10 = "This is the text of doc10";
        byte[] bytes1 = text1.getBytes();
        byte[] bytes2 = text2.getBytes();
        byte[] bytes3 = text3.getBytes();
        byte[] bytes4 = text4.getBytes();
        byte[] bytes5 = text5.getBytes();
        byte[] bytes6 = text6.getBytes();
        byte[] bytes7 = text7.getBytes();
        byte[] bytes8 = text8.getBytes();
        byte[] bytes9 = text9.getBytes();
        byte[] bytes10 = text10.getBytes();
        ByteArrayInputStream input1 = new ByteArrayInputStream(bytes1);
        ByteArrayInputStream input2 = new ByteArrayInputStream(bytes2);
        ByteArrayInputStream input3 = new ByteArrayInputStream(bytes3);
        ByteArrayInputStream input4 = new ByteArrayInputStream(bytes4);
        ByteArrayInputStream input5 = new ByteArrayInputStream(bytes5);
        ByteArrayInputStream input6 = new ByteArrayInputStream(bytes6);
        ByteArrayInputStream input7 = new ByteArrayInputStream(bytes7);
        ByteArrayInputStream input8 = new ByteArrayInputStream(bytes8);
        ByteArrayInputStream input9 = new ByteArrayInputStream(bytes9);
        ByteArrayInputStream input10 = new ByteArrayInputStream(bytes10);
        store.put(input1, uri1, format1);
        store.put(input2, uri2, format1);
        store.put(input3, uri3, format1);
        store.put(input4, uri4, format1);
        store.put(input5, uri5, format1);
        store.put(input6, uri6, format1);
        store.put(input7, uri7, format1);
        store.put(input8, uri8, format1);
        store.put(input9, uri9, format1);
        store.put(input10, uri10, format1);
        store.setMaxDocumentCount(5);
        store.delete(uri8);
        store.delete(uri2);
        store.setMetadata(uri4, "key1", "value1");
        store.get(uri1);
        store.setMaxDocumentCount(3);
        store.delete(uri3);
        store.undo(uri8);
        store.undo(uri4);
        store.undo();
        assertEquals(1, store.search("doc3").size());
    }
}
