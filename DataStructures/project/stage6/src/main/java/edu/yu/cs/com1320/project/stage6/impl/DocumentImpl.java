package edu.yu.cs.com1320.project.stage6.impl;
import edu.yu.cs.com1320.project.stage6.*;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class DocumentImpl implements Document{
    HashMap<String, String> metaDataTable;
    HashMap<String, Integer> wordCountTable;
    String txt;
    byte[] binaryData;
    URI uri;
    long lastUseTime;
    public DocumentImpl(URI uri, String text, Map<String, Integer> wordCountMap){
        if(uri == null || uri.toString().isBlank()|| txt == null || txt.isBlank()){
            throw new IllegalArgumentException("DocumentImpl constructor 1");
        }
        this.metaDataTable = new HashMap<>();
        this.wordCountTable = new HashMap<>();
        this.txt = text;
        this.uri = uri;
        if (wordCountMap == null) {
            String[] words = txt.split(" ");
            for (int i = 0; i < words.length; i++) {
                words[i] = words[i].replaceAll("\\W", "");
                this.wordCountTable.put(words[i], this.wordCountTable.get(words[i]) == null ? 1 : this.wordCountTable.get(words[i]) + 1);
            }
        } else {
            this.wordCountTable = new HashMap<>(wordCountMap);
        }
    }
    public DocumentImpl(URI uri, byte[] binaryData){
        if(uri == null || uri.toString().isBlank()|| binaryData == null || binaryData.length == 0){
            throw new IllegalArgumentException("DocumentImpl constructor 2");
        }
        this.metaDataTable = new HashMap<>();
        this.binaryData = binaryData;
        this.uri = uri;
        this.txt = null;
        this.wordCountTable = new HashMap<>();
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
    public HashMap<String, String> getMetadata(){
        HashMap<String, String> tableCopy = new HashMap<>();
        Set<String> keys = this.metaDataTable.keySet();
        for(String key : keys){
            tableCopy.put(key, this.getMetadataValue(key));
        }
        return tableCopy;
    }
    public void setMetadata(HashMap<String, String> metadata){
        this.metaDataTable = metadata;
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
    public int compareTo(Document doc){
        long thisDocTime = this.getLastUseTime();
        long otherDocTime = doc.getLastUseTime();
        if (thisDocTime < otherDocTime){
            return -1;
        }else if (thisDocTime == otherDocTime){
            return 0;
        }
        return 1;
    }
    /**
     * how many times does the given word appear in the document?
     * @param word
     * @return the number of times the given words appears in the document. If it's a binary document, return 0.
     */
    public int wordCount(String word){
        return wordCountTable.get(word) == null ? 0 : wordCountTable.get(word);
    }

    /**
     * @return all the words that appear in the document
     */
    public Set<String> getWords(){
        return this.wordCountTable.keySet();
    }
    /**
     * return the last time this document was used, via put/get or via a search result
     * (for stage 4 of project)
     */
    public long getLastUseTime(){
        return this.lastUseTime;
    }
    public void setLastUseTime(long timeInNanoseconds){
        this.lastUseTime = timeInNanoseconds;
    }
    /**
     * @return a copy of the word to count map so it can be serialized
     */
    public HashMap<String, Integer> getWordMap(){
        return new HashMap<>(wordCountTable);
    }

    /**
     * This must set the word to count map durlng deserialization
     *
     * @param wordMap
     */
    public void setWordMap(HashMap<String, Integer> wordMap){
        this.wordCountTable = wordMap;
    }
}
