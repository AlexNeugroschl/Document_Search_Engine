package edu.yu.cs.com1320.project.stage6.impl;
import edu.yu.cs.com1320.project.stage6.*;
import edu.yu.cs.com1320.project.impl.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import edu.yu.cs.com1320.project.undo.*;

public class DocumentStoreImpl implements DocumentStore {
    BTreeImpl<URI, Document> table;
    StackImpl<Undoable> commandStack;
    TrieImpl<URI> docsText;
    TrieImpl<URI> metaData;
    MinHeapImpl<HeapEntry> useTimes;
    int maxDocs;
    int maxBytes;
    int bytesCount;
    int docCount;
    public DocumentStoreImpl() {
        this.table = new BTreeImpl<>();
        this.table.setPersistenceManager(new DocumentPersistenceManager(new File(System.getProperty("user.home"))));
        this.commandStack = new StackImpl<>();
        this.docsText = new TrieImpl<>();
        this.metaData = new TrieImpl<>();
        this.useTimes = new MinHeapImpl<>();
        this.maxDocs = 0;
        this.maxBytes = 0;
        this.bytesCount = 0;
        this.docCount = 0;
    }
    public DocumentStoreImpl(File dir) {
        this();
        if (dir != null) {
            dir.mkdirs();
            this.table.setPersistenceManager(new DocumentPersistenceManager(dir));
        }
    }
    /**
     * set the given key-value metadata pair for the document at the given uri
     *
     * @param uri
     * @param key
     * @param value
     * @return the old value, or null if there was no previous value
     * @throws IllegalArgumentException if the uri is null or blank, if there is no document stored at that uri, or if the key is null or blank
     */
    public String setMetadata(URI uri, String key, String value) {
        if (uri == null || uri.toString().isBlank() || key == null || key.isBlank() || this.getFromTree(uri) == null) {
            throw new IllegalArgumentException("DocumentStore setMetaData error");
        }
        String oldValue = this.getMetadata(uri, key);
        commandStack.push(new GenericCommand<>(uri, url -> {
            this.get(url).setMetadataValue(key, oldValue);
            if (oldValue == null) {
                this.metaData.delete(key, uri);
            }
        }));
        if (value != null) {
            this.metaData.put(key, uri);
        } else {
            this.metaData.delete(key, uri);
        }
        return this.get(uri).setMetadataValue(key, value);
    }
    /**
     * get the value corresponding to the given metadata key for the document at the given uri
     *
     * @param uri
     * @param key
     * @return the value, or null if there was no value
     * @throws IllegalArgumentException if the uri is null or blank, if there is no document stored at that uri, or if the key is null or blank
     */
    public String getMetadata(URI uri, String key) {
        if (uri == null || uri.toString().isBlank() || key == null || key.isBlank() || table.get(uri) == null) {
            throw new IllegalArgumentException("DocumentStore getMetaData error");
        }
        return this.get(uri).getMetadataValue(key);
    }
    /**
     * @param input  the document being put
     * @param uri    unique identifier for the document
     * @param format indicates which type of document format is being passed
     * @return if there is no previous doc at the given URI, return 0. If there is a previous doc, return the hashCode of the previous doc. If InputStream is null, this is a delete, and thus return either the hashCode of the deleted doc or 0 if there is no doc to delete.
     * @throws IOException              if there is an issue reading input
     * @throws IllegalArgumentException if uri is null or empty, or format is null
     */
    public int put(InputStream input, URI uri, DocumentFormat format) throws IOException {
        if (uri == null || uri.toString().isBlank() || format == null) {
            throw new IllegalArgumentException("DocumentStore Put error");
        }
        if (input == null) {
            Document deleted = this.getFromTree(uri);
            return this.delete(uri) ? deleted.hashCode() : 0;
        }
        byte[] bytes;
        try{
            bytes = input.readAllBytes();
        } catch (IOException e) {
            throw new IOException("DocumentStoreImpl IOE exception");
        }
        DocumentImpl doc = format == DocumentFormat.TXT ? new DocumentImpl(uri, new String(bytes), null) : new DocumentImpl(uri, bytes);
        Document original = this.getFromTree(uri);
        if (original != null){
            this.delete(original.getKey());
        }
        this.addToStore(doc);
        CommandSet<URI> undoCMD = new CommandSet<>();
        undoCMD.addCommand(new GenericCommand<>(uri, url -> {
            this.deleteFromStore(doc);
            this.addToStore(original);
        }));
        commandStack.push(undoCMD);
        return original == null ? 0 : original.hashCode();

    }

    /**
     * @param url the unique identifier of the document to get
     * @return the given document
     */
    public Document get(URI url) {
        Document doc = this.getFromTree(url);
        if (doc != null) {
            this.updateDocsNanoTime(Arrays.asList(doc));
        }
        return doc;
    }
    /**
     * @param url the unique identifier of the document to delete
     * @return true if the document is deleted, false if no document exists with that URI
     */
    public boolean delete(URI url) {
        Document docToDelete = this.getFromTree(url);
        if (docToDelete == null) {
            return false;
        } else {
            deleteDocumentsAddingUndo((Arrays.asList(docToDelete)));
            return true;
        }
    }
    /**
     * undo the last put or delete command
     *
     * @throws IllegalStateException if there are no actions to be undone, i.e. the command stack is empty
     */
    public void undo() throws IllegalStateException {
        Undoable topUndo = commandStack.pop();
        if (topUndo == null) {
            throw new IllegalStateException("Stack is empty: undo()");
        }
        topUndo.undo();
    }
    /**
     * undo the last put or delete that was done with the given URI as its key
     *
     * @param url
     * @throws IllegalStateException if there are no actions on the command stack for the given URI
     */
    public void undo(URI url) throws IllegalStateException {
        StackImpl<Undoable> temp = new StackImpl<>();
        boolean undid = false;
        while (!undid) {
            Undoable currentUndo = commandStack.pop();
            if (currentUndo == null) {
                while (temp.size() > 0) {
                    commandStack.push(temp.pop());
                }
                throw new IllegalStateException("No URI in commandStack: undo(URI url)");
            }
            if (currentUndo instanceof GenericCommand<?> && ((GenericCommand<?>) currentUndo).getTarget().equals(url)) {
                while (temp.size() > 0) {
                    commandStack.push(temp.pop());
                }
                currentUndo.undo();
                undid = true;
            }
            if (currentUndo instanceof CommandSet<?> && ((CommandSet<URI>) currentUndo).containsTarget(url)) {
                ((CommandSet<URI>) currentUndo).undo(url);
                commandStack.push(currentUndo);
                while (temp.size() > 0) {
                    commandStack.push(temp.pop());
                }
                undid = true;
            }
            temp.push(currentUndo);
        }
    }
    /**
     * Retrieve all documents whose text contains the given keyword.
     * Documents are returned in sorted, descending order, sorted by the number of times the keyword appears in the document.
     * Search is CASE SENSITIVE.
     *
     * @param keyword
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> search(String keyword) {
        List<URI> uris = this.docsText.getSorted(keyword, new DocumentComparator(keyword));
        List<Document> docs = new LinkedList<>();
        for (URI uri : uris){
            docs.add(this.getFromTree(uri));
        }
        this.updateDocsNanoTime(docs);
        return docs;
    }
    /**
     * Retrieve all documents that contain text which starts with the given prefix
     * Documents are returned in sorted, descending order, sorted by the number of times the prefix appears in the document.
     * Search is CASE SENSITIVE.
     *
     * @param keywordPrefix
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> searchByPrefix(String keywordPrefix) {
        List<URI> uris = this.docsText.getAllWithPrefixSorted(keywordPrefix, new DocumentComparator(keywordPrefix));
        List<Document> docs = new LinkedList<>();
        for (URI uri : uris){
            docs.add(this.getFromTree(uri));
        }
        this.updateDocsNanoTime(docs);
        return docs;
    }
    /**
     * Completely remove any trace of any document which contains the given keyword
     * Search is CASE SENSITIVE.
     *
     * @param keyword
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAll(String keyword) {
        LinkedList<URI> uris = new LinkedList<>(docsText.get(keyword));
        LinkedList<Document> docs = new LinkedList<>();
        for (URI uri : uris){
            docs.add(this.getFromTree(uri));
        }
        return deleteDocumentsAddingUndo(docs);
    }
    /**
     * Completely remove any trace of any document which contains a word that has the given prefix
     * Search is CASE SENSITIVE.
     *
     * @param keywordPrefix
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAllWithPrefix(String keywordPrefix) {
        List<URI> uris = this.docsText.getAllWithPrefixSorted(keywordPrefix, new DocumentComparator(keywordPrefix));
        LinkedList<Document> docs = new LinkedList<>();
        for (URI uri : uris){
            docs.add(this.getFromTree(uri));
        }
        return deleteDocumentsAddingUndo(docs);
    }
    /**
     * @param keysValues metadata key-value pairs to search for
     * @return a List of all documents whose metadata contains ALL OF the given values for the given keys. If no documents contain all the given key-value pairs, return an empty list.
     */
    public List<Document> searchByMetadata(Map<String, String> keysValues) {
        Set<String> keys = keysValues.keySet();
        Set<Document> docs = null;
        for (String key : keys) {
            if (docs == null) {
                docs = new HashSet<Document>();
                Set<URI> uris = this.metaData.get(key);
                for (URI uri : uris){
                    docs.add(this.getFromTree(uri));
                }
            }
            for (Document doc : docs) {
                if (doc.getMetadataValue(key) != null && !doc.getMetadataValue(key).equals(keysValues.get(key))) {
                    docs.remove(doc);
                }
            }
        }
        if(docs == null){
            return new LinkedList<>();
        }
        this.updateDocsNanoTime(new LinkedList<>(docs));
        return new LinkedList<>(docs);
    }
    /**
     * Retrieve all documents whose text contains the given keyword AND which has the given key-value pairs in its metadata
     * Documents are returned in sorted, descending order, sorted by the number of times the keyword appears in the document.
     * Search is CASE SENSITIVE.
     *
     * @param keyword
     * @param keysValues
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> searchByKeywordAndMetadata(String keyword, Map<String, String> keysValues) {
        List<Document> matchingMetaData = this.searchByMetadata(keysValues);
        List<URI> matchingText = new LinkedList<>(this.metaData.get(keyword));
        List<Document> textDocs = new LinkedList<>();
        for (URI uri : matchingText){
            textDocs.add(this.getFromTree(uri));
        }
        for (Document doc : matchingMetaData) {
            if (!matchingText.contains(doc)) {
                matchingMetaData.remove(doc);
            }
        }
        return matchingMetaData;
    }
    /**
     * Retrieve all documents that contain text which starts with the given prefix AND which has the given key-value pairs in its metadata
     * Documents are returned in sorted, descending order, sorted by the number of times the prefix appears in the document.
     * Search is CASE SENSITIVE.
     *
     * @param keywordPrefix
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> searchByPrefixAndMetadata(String keywordPrefix, Map<String, String> keysValues) {
        List<Document> matchingMetaData = this.searchByMetadata(keysValues);
        List<URI> matchingPrefixes = docsText.getAllWithPrefixSorted(keywordPrefix, new DocumentComparator(keywordPrefix));
        for (Document doc : matchingMetaData) {
            if (!matchingPrefixes.contains(doc)) {
                matchingMetaData.remove(doc);
            }
        }
        return matchingMetaData;
    }
    /**
     * Completely remove any trace of any document which has the given key-value pairs in its metadata
     * Search is CASE SENSITIVE.
     *
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAllWithMetadata(Map<String, String> keysValues) {
        return deleteDocumentsAddingUndo(this.searchByMetadata(keysValues));
    }
    /**
     * Completely remove any trace of any document which contains the given keyword AND which has the given key-value pairs in its metadata
     * Search is CASE SENSITIVE.
     *
     * @param keyword
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAllWithKeywordAndMetadata(String keyword, Map<String, String> keysValues) {
        return deleteDocumentsAddingUndo(this.searchByKeywordAndMetadata(keyword, keysValues));
    }
    /**
     * Completely remove any trace of any document which contains a word that has the given prefix AND which has the given key-value pairs in its metadata
     * Search is CASE SENSITIVE.
     *
     * @param keywordPrefix
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAllWithPrefixAndMetadata(String keywordPrefix, Map<String, String> keysValues) {
        return deleteDocumentsAddingUndo(searchByPrefixAndMetadata(keywordPrefix, keysValues));
    }
    private Set<URI> deleteDocumentsAddingUndo(List<Document> toDelete) {
        Set<URI> deletedURIs = new HashSet<>();
        CommandSet<URI> undelete = new CommandSet<>();
        for (Document doc : toDelete) {
            deletedURIs.add(doc.getKey());
            undelete.addCommand(new GenericCommand<>(doc.getKey(), uri -> this.addToStore(doc)));
            this.deleteFromStore(doc);
        }
        commandStack.push(undelete);
        return deletedURIs;
    }
    /**
     * set maximum number of documents that may be stored
     * @param limit
     * @throws IllegalArgumentException if limit < 1
     */
    public void setMaxDocumentCount(int limit){
        if (limit < 1){
            throw new IllegalArgumentException("Cannot be less than 1");
        }
        this.maxDocs = limit;
        this.maintainMemory();
    }

    /**
     * set maximum number of bytes of memory that may be used by all the documents in memory combined
     * @param limit
     * @throws IllegalArgumentException if limit < 1
     */
    public void setMaxDocumentBytes(int limit){
        if (limit < 1){
            throw new IllegalArgumentException("Cannot be less than 1");
        }
        this.maxBytes = limit;
        this.maintainMemory();
    }
    private void updateDocsNanoTime(List<Document> docs){
        long time = System.nanoTime();
        for(Document doc : docs) {
            doc.setLastUseTime(time);
            this.updateHeapTime(doc.getKey(), time);
        }
    }
    private void updateHeapTime(URI uri, long time){
        LinkedList<HeapEntry> removedItems = new LinkedList<>();
        boolean notFound = true;
        try{
            while(notFound) {
                HeapEntry removedDoc = this.useTimes.remove();
                if (uri.equals(removedDoc.getURI())){
                    notFound = false;
                    removedDoc.setTime(time);
                    for (HeapEntry item : removedItems){
                        this.useTimes.insert(item);
                    }
                    this.useTimes.insert(removedDoc);
                }else{
                    removedItems.add(removedDoc);
                }
            }
        }catch(NoSuchElementException e){
            for (HeapEntry item : removedItems){
                this.useTimes.insert(item);
            }
        }
    }
    private void deleteFromStore(Document doc) {
        if (this.heapContains(doc.getKey())){
            this.bytesCount -= doc.getDocumentTxt() == null ? doc.getDocumentBinaryData().length : doc.getDocumentTxt().getBytes().length;
            this.docCount--;
        }
        this.table.get(doc.getKey());
        this.deleteFromHeap(doc);
        this.table.put(doc.getKey(), null);
        Set<String> words = doc.getWords();
        for (String word : words) {
            this.docsText.delete(word, doc.getKey());
        }
        Set<String> metaDataKeys = doc.getMetadata().keySet();
        for (String dataPoint : metaDataKeys) {
            this.metaData.delete(dataPoint, doc.getKey());
        }
    }
    private void addToStore(Document doc){
        if (doc == null){
            return;
        }
        if (this.get(doc.getKey()) != null){
            return;
        }
        if ((this.maxBytes != 0 && doc.getDocumentTxt() == null && doc.getDocumentBinaryData().length >= this.maxBytes) || (this.maxBytes != 0 && doc.getDocumentTxt() != null && doc.getDocumentTxt().getBytes().length >= maxBytes)){
            throw new IllegalArgumentException("Document exceeds max bytes");
        }
        this.table.put(doc.getKey(), doc);
        this.useTimes.insert(new HeapEntry(doc.getKey(), doc.getLastUseTime()));
        Set<String> words = doc.getWords();
        for (String word : words) {
            docsText.put(word, doc.getKey());
        }
        Set<String> metaDataKeys = doc.getMetadata().keySet();
        for (String dataPoint : metaDataKeys) {
            metaData.put(dataPoint, doc.getKey());
        }
        this.bytesCount += doc.getDocumentTxt() == null ? doc.getDocumentBinaryData().length : doc.getDocumentTxt().getBytes().length;
        this.docCount++;
        this.maintainMemory();
    }
    private void maintainMemory(){
        while ((maxDocs != 0 && this.docCount > maxDocs) || (this.maxBytes != 0 && this.bytesCount > maxBytes)){
            try {
                HeapEntry doc = this.useTimes.remove();
                this.bytesCount -= this.table.get(doc.getURI()).getDocumentTxt() == null ? this.table.get(doc.getURI()).getDocumentBinaryData().length : this.table.get(doc.getURI()).getDocumentTxt().getBytes().length;
                this.table.moveToDisk(doc.getURI());
                this.docCount--;
            }catch (IOException e){
                throw new RuntimeException("Failed to move to disk");
            }
        }
    }
    private void deleteFromHeap(Document doc){
        LinkedList<HeapEntry> removedItems = new LinkedList<>();
        boolean notFound = true;
        try{
            while(notFound) {
                HeapEntry removedDoc = this.useTimes.remove();
                if (doc.getKey().equals(removedDoc.getURI())){
                    notFound = false;
                    for (HeapEntry item : removedItems){
                        this.useTimes.insert(item);
                    }
                }else{
                    removedItems.add(removedDoc);
                }
            }
        }catch(NoSuchElementException e){
            for (HeapEntry item : removedItems){
                this.useTimes.insert(item);
            }
        }
    }
    private Document getFromTree(URI uri){
        Document doc = this.table.get(uri);
        if ((!this.heapContains(uri)) && doc != null){
            this.useTimes.insert(new HeapEntry(uri, doc.getLastUseTime()));
            this.docCount++;
            this.bytesCount += doc.getDocumentTxt() == null ? doc.getDocumentBinaryData().length : doc.getDocumentTxt().getBytes().length;
            this.maintainMemory();
        }
        return doc;
    }
    private boolean heapContains(URI uri){
        LinkedList<HeapEntry> removedItems = new LinkedList<>();
        boolean notFound = true;
        try{
            while(notFound) {
                HeapEntry removedDoc = this.useTimes.remove();
                if (uri.equals(removedDoc.getURI())){
                    notFound = false;
                    for (HeapEntry item : removedItems){
                        this.useTimes.insert(item);
                    }
                    this.useTimes.insert(removedDoc);
                    return true;
                }else{
                    removedItems.add(removedDoc);
                }
            }
        }catch(NoSuchElementException e){
            for (HeapEntry item : removedItems){
                this.useTimes.insert(item);
            }
        }
        return false;
    }
    private class DocumentComparator implements Comparator<URI> {
        private String keyword;
        public DocumentComparator(String keyword) {
            this.keyword = keyword;
        }
        @Override
        public int compare(URI doc1, URI doc2) {
            int doc1Count = DocumentStoreImpl.this.getFromTree(doc1).wordCount(this.keyword);
            int doc2Count = DocumentStoreImpl.this.getFromTree(doc2).wordCount(this.keyword);
            if (doc1Count > doc2Count) {
                return -1;
            }
            return doc1Count == doc2Count ? 0 : 1;
        }
    }
    private class HeapEntry implements Comparable<HeapEntry> {
        URI uri;
        long time;
        public HeapEntry(URI uri, long time){
            this.uri = uri;
            this.time = time;;
        }

        public long getTime() {
            return time;
        }
        public URI getURI() {
            return uri;
        }
        public void setTime(long time){
            this.time = time;
        }
        public int compareTo(HeapEntry entry) {
            long otherDocTime = entry.getTime();
            if (this.time < otherDocTime){
                return -1;
            }else if (this.time == otherDocTime){
                return 0;
            }
            return 1;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof HeapEntry)){
                return false;
            }
            HeapEntry entry = (HeapEntry) obj;
            if ((entry.getURI() == this.getURI()) && (entry.getTime() == this.getTime())){
                return true;
            }
            return false;
        }
    }
}