package edu.yu.cs.com1320.project.stage4.impl;
import  edu.yu.cs.com1320.project.impl.HashTableImpl;
import edu.yu.cs.com1320.project.HashTable;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;

public class DocumentImpl implements edu.yu.cs.com1320.project.stage4.Document{
    HashTableImpl<String, String> metaDataTable;
    HashMap<String, Integer> wordCountTable;
    String txt;
    byte[] binaryData;
    URI uri;
    public DocumentImpl(URI uri, String txt){
        if(uri == null || uri.toString().isBlank()|| txt == null || txt.isBlank()){
            throw new IllegalArgumentException("DocumentImpl constructor 1");
        }
        this.metaDataTable = new HashTableImpl<>();
        this.wordCountTable = new HashMap<>();
        this.txt = txt;
        this.uri = uri;
        String[] words = txt.split(" ");
        for(int i = 0; i < words.length; i++){
            words[i] = words[i].replaceAll("\\W", "");
            wordCountTable.put(words[i], wordCountTable.get(words[i]) == null ? 1 : wordCountTable.get(words[i])+1);
        }
        /*
        for(String word : words){
            char[] letters = word.toCharArray();
            for(char letter : letters){
                if((letter < 47) || (letter > 57 && letter < 65) || (letter > 90 && letter < 97) || (letter > 122)){
                    //letter = ' ';
                }
            }
            wordCountTable.put(new String(letters).replace(" ", ""), wordCountTable.get(new String(letters).replace(" ", "")));
        }
         */
    }
    public DocumentImpl(URI uri, byte[] binaryData){
        if(uri == null || uri.toString().isBlank()|| binaryData == null || binaryData.length == 0){
            throw new IllegalArgumentException("DocumentImpl constructor 2");
        }
        this.metaDataTable = new HashTableImpl<>();
        this.binaryData = binaryData;
        this.uri = uri;
        this.txt = null;
    }
    /**
     * @param key   key of document metadata to store a value for
     * @param value value to store
     * @return old value, or null if there was no old value
     * @throws IllegalArgumentException if the key is null or blank
     */
    public String setMetadataValue(String key, String value){
        if(key == null || key.isBlank()){
            throw new IllegalArgumentException();
        }
        return this.metaDataTable.put(key, value);
    }

    /**
     * @param key metadata key whose value we want to retrieve
     * @return corresponding value, or null if there is no such key
     * @throws IllegalArgumentException if the key is null or blank
     */
    public String getMetadataValue(String key){
        if(key == null || key.isEmpty()){
            throw new IllegalArgumentException();
        }
        return this.metaDataTable.get(key);
    }

    /**
     * @return a COPY of the metadata saved in this document
     */
    public HashTable<String, String> getMetadata(){
        HashTableImpl<String, String> tableCopy = new HashTableImpl<>();
        Set<String> keys = this.metaDataTable.keySet();
        for(String key : keys){
            tableCopy.put(key, this.getMetadataValue(key));
        }
        return tableCopy;
    }

    /**
     * @return content of text document
     */
    public String getDocumentTxt(){
        return this.txt;
    }

    /**
     * @return content of binary data document
     */
    public byte[] getDocumentBinaryData(){
        return this.binaryData;
    }

    /**
     * @return URI which uniquely identifies this document
     */
    public URI getKey(){
        return this.uri;
    }
    @Override
    public int hashCode() {
        int result = uri.hashCode();
        result = 31 * result + (txt != null ? txt.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(binaryData);
        return Math.abs(result);
    }
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof DocumentImpl)){
            return false;
        }
        DocumentImpl other = (DocumentImpl) obj;
        if(this.hashCode()==other.hashCode()) {
            return true;
        }else{
            return false;
        }
    }
    /**
     * how many times does the given word appear in the document?
     * @param word
     * @return the number of times the given words appears in the document. If it's a binary document, return 0.
     */
    public int wordCount(String word){
        if (wordCountTable == null){
            return 0;
        }
        return wordCountTable.get(word) == null ? 0 : wordCountTable.get(word);
    }

    /**
     * @return all the words that appear in the document
     */
    public Set<String> getWords(){
        return (Set<String>) wordCountTable.keySet();
    }
}
