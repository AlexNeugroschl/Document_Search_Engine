import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import edu.yu.cs.com1320.project.stage3.impl.*;
import edu.yu.cs.com1320.project.stage3.DocumentStore;
import java.io.StringBufferInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

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
}
