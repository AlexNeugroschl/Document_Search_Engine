package edu.yu.cs.com1320.project.stage3.impl;
import edu.yu.cs.com1320.project.stage3.impl.DocumentImpl;
import edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.impl.StackImpl;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import edu.yu.cs.com1320.project.stage3.Document;
import edu.yu.cs.com1320.project.undo.Command;

public class DocumentStoreImpl implements edu.yu.cs.com1320.project.stage3.DocumentStore{
    HashTableImpl<URI, Document> table;
    StackImpl<Command> commandStack;
    public DocumentStoreImpl(){
            this.table = new HashTableImpl<>();
            this.commandStack = new StackImpl<>();
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
        commandStack.push(new Command(uri, url -> {
            setMetadata(url, key, oldValue);
        }));
        return this.table.get(uri).setMetadataValue(key, value);
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
        return this.table.get(uri).getMetadataValue(key);
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
                if(deleted == null) {
                    return 0;
                }else{
                    return deleted.hashCode();
                }
            }
            byte[] bytes = input.readAllBytes();
            input.close();
            String text = new String(bytes);
            DocumentImpl doc;
            if(format == DocumentFormat.TXT){
                doc = new DocumentImpl(uri, text);
            }else{
                doc = new DocumentImpl(uri, bytes);
            }
            Document original = table.put(uri, doc);
            if(original == null){
                commandStack.push(new Command(uri, url -> {
                    this.delete(url);
                }));
                return 0;
            }else{
                commandStack.push(new Command(uri, url -> {
                    this.table.put(url, original);
                }));
                return original.hashCode();
            }
        }catch(IOException e){
            throw new IOException("DocumentStoreImpl IOE exception");
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
        InputStream input = null;
        int deleted = 0;
        Document docToDelete;
        try{
            docToDelete = this.table.get(url);
            deleted = this.put(input, url, DocumentFormat.BINARY);
        }catch(IOException e){
                return false;
        }
        if(deleted == 0){
            return false;
        }else {
            commandStack.push(new Command(url, uri -> {
                this.table.put(uri, docToDelete);
            }));
            return true;
        }
    }
    /**
     * undo the last put or delete command
     * @throws IllegalStateException if there are no actions to be undone, i.e. the command stack is empty
     */
    public void undo() throws IllegalStateException{
        Command topUndo = commandStack.pop();
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
        StackImpl<Command> temp = new StackImpl<>();
        Boolean undid = false;
        while(undid == false) {
            Command currentUndo = commandStack.pop();
            if (currentUndo == null || currentUndo.getUri().equals(url)) {
                for (int i = 0; i < temp.size(); i++) {
                    commandStack.push(temp.pop());
                }
                if (currentUndo == null) {
                    throw new IllegalStateException("No URI in commandStack: undo(URI url)");
                } else {
                    currentUndo.undo();
                    undid = true;
                }
            }
            temp.push(currentUndo);
        }
    }
}