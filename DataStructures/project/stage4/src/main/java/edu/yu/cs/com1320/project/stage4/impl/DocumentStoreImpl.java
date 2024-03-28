package edu.yu.cs.com1320.project.stage4.impl;
import edu.yu.cs.com1320.project.HashTable;
import edu.yu.cs.com1320.project.stage4.DocumentStore;
import edu.yu.cs.com1320.project.stage4.impl.DocumentImpl;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import edu.yu.cs.com1320.project.stage4.Document;
import edu.yu.cs.com1320.project.impl.TrieImpl;
import edu.yu.cs.com1320.project.undo.CommandSet;
import edu.yu.cs.com1320.project.undo.GenericCommand;
import edu.yu.cs.com1320.project.undo.Undoable;
public class DocumentStoreImpl implements DocumentStore{
    HashTableImpl<URI, Document> table;
    StackImpl<Undoable> commandStack;
    TrieImpl<Document> docsText;
    TrieImpl<Document> metaData;
    public DocumentStoreImpl(){
            this.table = new HashTableImpl<>();
            this.commandStack = new StackImpl<>();
            this.docsText = new TrieImpl<>();
            this.metaData = new TrieImpl<>();
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
    public String setMetadata(URI uri, String key, String value){
        if(uri == null || uri.toString().isBlank() || key == null || key.isBlank() || table.get(uri) == null){
            throw new IllegalArgumentException("DocumentStore setMetaData error");
        }
        String oldValue = getMetadata(uri, key);
        commandStack.push(new GenericCommand<>(this.get(uri), doc -> doc.setMetadataValue(key, oldValue)));
        if(value != null) {
            this.metaData.put(key, this.get(uri));
        }else{
            this.metaData.delete(key, this.get(uri));
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
    public String getMetadata(URI uri, String key){
        if(uri == null || uri.toString().isBlank() || key == null || key.isBlank() || table.get(uri) == null){
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
    public int put(InputStream input, URI uri, DocumentFormat format) throws IOException{
        if(uri == null || uri.toString().isBlank() || format == null){
            throw new IllegalArgumentException("DocumentStore Put error");
        }
        try{
            if(input == null){
                Document deleted = table.put(uri, null);
                return deleted == null ? 0 : deleted.hashCode();
            }
            byte[] bytes = input.readAllBytes();
            input.close();
            String text = new String(bytes);
            DocumentImpl doc;
            if(format == DocumentFormat.TXT){
                doc = new DocumentImpl(uri, text);
                this.addTextToTrie(doc);
            }else{
                doc = new DocumentImpl(uri, bytes);
            }
            Document original = table.put(uri, doc);
            if(original == null){
                commandStack.push(new GenericCommand<>(this.get(uri), document -> this.table.put(document.getKey(), null)));
                return 0;
            }else{
                commandStack.push(new GenericCommand<>(this.get(uri), document -> this.table.put(document.getKey(), original)));
                return original.hashCode();
            }
        }catch(IOException e){
            throw new IOException("DocumentStoreImpl IOE exception");
        }
    }
    private void addTextToTrie(Document doc){
        Set<String> words = doc.getWords();
        for(String word : words){
            this.docsText.put(word, doc);
        }
    }
    /**
     * @param url the unique identifier of the document to get
     * @return the given document
     */
    public Document get(URI url){
        return table.get(url);
    }
    /**
     * @param url the unique identifier of the document to delete
     * @return true if the document is deleted, false if no document exists with that URI
     */
    public boolean delete(URI url){
        Document docToDelete = this.table.get(url);
        if(docToDelete == null){
            return false;
        }else{
            LinkedList<Document> doc = new LinkedList<>();
            doc.add(docToDelete);
            deleteDocuments(doc);
            return true;
        }
    }
    /**
     * undo the last put or delete command
     * @throws IllegalStateException if there are no actions to be undone, i.e. the command stack is empty
     */
    public void undo() throws IllegalStateException{
        Undoable topUndo = commandStack.pop();
        if(topUndo == null){
            throw new IllegalStateException("Stack is empty: undo()");
        }else{
            topUndo.undo();
        }
    }
    /**
     * undo the last put or delete that was done with the given URI as its key
     * @param url
     * @throws IllegalStateException if there are no actions on the command stack for the given URI
     */
    public void undo(URI url) throws IllegalStateException{
        StackImpl<Undoable> temp = new StackImpl<>();
        boolean undid = false;
        while(!undid) {
            Undoable currentUndo = commandStack.pop();
            if (currentUndo == null) {
                while (temp.size() > 0) {
                    commandStack.push(temp.pop());
                }
                throw new IllegalStateException("No URI in commandStack: undo(URI url)");
            }
            if (currentUndo instanceof GenericCommand<?> && ((GenericCommand<?>) currentUndo).getTarget().equals(this.get(url))) {
                while (temp.size() > 0) {
                    commandStack.push(temp.pop());
                }
                currentUndo.undo();
                undid = true;
            }
            if(currentUndo instanceof CommandSet<?> && ((CommandSet<Document>) currentUndo).containsTarget(this.get(url))){ //this.get(url) is null on delete undo
                ((CommandSet<Document>) currentUndo).undo(this.get(url));
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
     * @param keyword
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> search(String keyword){
        return docsText.getSorted(keyword, new DocumentComparator(keyword));
    }
    /**
     * Retrieve all documents that contain text which starts with the given prefix
     * Documents are returned in sorted, descending order, sorted by the number of times the prefix appears in the document.
     * Search is CASE SENSITIVE.
     * @param keywordPrefix
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> searchByPrefix(String keywordPrefix){
        return this.docsText.getAllWithPrefixSorted(keywordPrefix, new DocumentComparator(keywordPrefix));
    }
    /**
     * Completely remove any trace of any document which contains the given keyword
     * Search is CASE SENSITIVE.
     * @param keyword
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAll(String keyword){
        return deleteDocuments(new LinkedList<>(docsText.get(keyword)));
    }
    /**
     * Completely remove any trace of any document which contains a word that has the given prefix
     * Search is CASE SENSITIVE.
     * @param keywordPrefix
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAllWithPrefix(String keywordPrefix){
        return deleteDocuments(docsText.getAllWithPrefixSorted(keywordPrefix, new DocumentComparator(keywordPrefix)));
    }
    /**
     * @param keysValues metadata key-value pairs to search for
     * @return a List of all documents whose metadata contains ALL OF the given values for the given keys. If no documents contain all the given key-value pairs, return an empty list.
     */
    public List<Document> searchByMetadata(Map<String,String> keysValues){
        Set<String> keys = keysValues.keySet();
        Set<Document> docs = null;
        for(String key : keys){
            if(docs == null){
                docs = metaData.get(key);
            }
            for(Document doc : docs){
                if(!doc.getMetadataValue(key).equals(keysValues.get(key))){
                    docs.remove(doc);
                }
            }
        }
        return new LinkedList<>(docs);
    }
    /**
     * Retrieve all documents whose text contains the given keyword AND which has the given key-value pairs in its metadata
     * Documents are returned in sorted, descending order, sorted by the number of times the keyword appears in the document.
     * Search is CASE SENSITIVE.
     * @param keyword
     * @param keysValues
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> searchByKeywordAndMetadata(String keyword, Map<String,String> keysValues){
        List<Document> matchingMetaData = this.searchByMetadata(keysValues);
        List<Document> matchingText = new LinkedList<Document>(this.metaData.get(keyword));
        for(Document doc : matchingMetaData){
            if(!matchingText.contains(doc)){
                matchingMetaData.remove(doc);
            }
        }
        return matchingMetaData;
    }
    /**
     * Retrieve all documents that contain text which starts with the given prefix AND which has the given key-value pairs in its metadata
     * Documents are returned in sorted, descending order, sorted by the number of times the prefix appears in the document.
     * Search is CASE SENSITIVE.
     * @param keywordPrefix
     * @return a List of the matches. If there are no matches, return an empty list.
     */
    public List<Document> searchByPrefixAndMetadata(String keywordPrefix,Map<String,String> keysValues){
        List<Document> matchingMetaData = this.searchByMetadata(keysValues);
        List<Document> matchingPrefixes = docsText.getAllWithPrefixSorted(keywordPrefix, new DocumentComparator(keywordPrefix));
        for(Document doc : matchingMetaData){
            if(!matchingPrefixes.contains(doc)){
                matchingMetaData.remove(doc);
            }
        }
        return matchingMetaData;
    }
    /**
     * Completely remove any trace of any document which has the given key-value pairs in its metadata
     * Search is CASE SENSITIVE.
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAllWithMetadata(Map<String,String> keysValues){ // need to delete from both tries
        return deleteDocuments(this.searchByMetadata(keysValues));
    }
    /**
     * Completely remove any trace of any document which contains the given keyword AND which has the given key-value pairs in its metadata
     * Search is CASE SENSITIVE.
     * @param keyword
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAllWithKeywordAndMetadata(String keyword,Map<String,String> keysValues){
        return deleteDocuments(this.searchByKeywordAndMetadata(keyword, keysValues));
    }
    /**
     * Completely remove any trace of any document which contains a word that has the given prefix AND which has the given key-value pairs in its metadata
     * Search is CASE SENSITIVE.
     * @param keywordPrefix
     * @return a Set of URIs of the documents that were deleted.
     */
    public Set<URI> deleteAllWithPrefixAndMetadata(String keywordPrefix,Map<String,String> keysValues){
        return deleteDocuments(searchByPrefixAndMetadata(keywordPrefix, keysValues));
    }
    private Set<URI> deleteDocuments(List<Document> toDelete){
        Set<URI> deletedURIs = new HashSet<>();
        CommandSet<Document> undelete = new CommandSet<>();
        for(Document doc : toDelete){
            Set<String> metaDataKeys = doc.getMetadata().keySet();
            deletedURIs.add(doc.getKey());
            undelete.addCommand(new GenericCommand<>(doc, document -> {
                this.table.put(document.getKey(), document);
                this.addTextToTrie(document);
                for (String dataPoint : metaDataKeys){
                    metaData.put(dataPoint, document);
                    document.setMetadataValue(dataPoint, document.getMetadataValue(dataPoint));
                }
            }));
            commandStack.push(undelete);
            this.table.put(doc.getKey(), null);
            Set<String> words = doc.getWords();
            for(String word : words){
                docsText.delete(word, doc);
            }
            for(String dataPoint : metaDataKeys){
                metaData.delete(dataPoint, doc);
            }
        }
        return deletedURIs;
    }
    private class DocumentComparator implements Comparator<Document> {
        private String keyword;

        public DocumentComparator(String keyword) {
            this.keyword = keyword;
        }
        @Override
        public int compare(Document doc1, Document doc2) {
            int doc1Count = doc1.wordCount(this.keyword);
            int doc2Count = doc2.wordCount(this.keyword);
            if (doc1Count > doc2Count) {
                return -1;
            }
            if (doc1Count == doc2Count) {
                return 0;
            }
            return 1;
        }
    }
}