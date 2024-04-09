import edu.yu.cs.com1320.project.stage4.Document;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.yu.cs.com1320.project.stage4.impl.*;
import edu.yu.cs.com1320.project.stage4.DocumentStore;
import java.io.StringBufferInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
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
            ;
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
        DocumentImpl doc = new DocumentImpl(uri, text);
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
        DocumentImpl doc = new DocumentImpl(uri, text);
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
        DocumentImpl doc = new DocumentImpl(uri, text);
        assertEquals(true, doc.hashCode() == store.get(uri).hashCode());
    }
    @Test
    public void deleteTest1()throws URISyntaxException, UnsupportedEncodingException, IOException {
        DocumentStoreImpl store = new DocumentStoreImpl();
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
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
        URI uri = new URI("https://www.geeksforgeeks.org/url-class-java-examples/");
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
        DocumentImpl doc = new DocumentImpl(uri, text);
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
        store.put(input2, uri1, format);
        store.put(input3, uri1, format);
        store.put(input4, uri1, format);
        store.put(input5, uri1, format);
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
        store.put(input2, uri1, format);
        store.put(input3, uri1, format);
        store.put(input4, uri1, format);
        store.put(input5, uri1, format);
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
}
