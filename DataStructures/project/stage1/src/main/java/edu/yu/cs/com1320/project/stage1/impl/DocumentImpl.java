package edu.yu.cs.com1320.project.stage1.impl;

import java.net.URI;
import java.util.HashMap;
import java.util.Arrays;


public class DocumentImpl implements edu.yu.cs.com1320.project.stage1.Document{
    HashMap<String, String> map;
    String txt;
    byte[] binaryData;
    URI uri;
    public DocumentImpl(URI uri, String txt){
        if(uri == null || uri.toString().isBlank()|| txt == null || txt.isBlank()){
            throw new IllegalArgumentException("DocumentImpl constructor 1");
        }
        this.map = new HashMap<>();
        this.txt = txt;
        this.uri = uri;
        this.binaryData = new byte[0];
    }
    public DocumentImpl(URI uri, byte[] binaryData){
        if(uri == null || uri.toString().isBlank()|| binaryData == null || binaryData.length == 0){
            throw new IllegalArgumentException("DocumentImpl constructor 2");
        }
        this.map = new HashMap<>();
        this.binaryData = binaryData;
        this.uri = uri;
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
        return this.map.put(key, value);
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
        return this.map.get(key);
    }

    /**
     * @return a COPY of the metadata saved in this document
     */
    public HashMap<String, String> getMetadata(){
        HashMap<String, String> mapCopy = new HashMap<>(this.map);
        return mapCopy;
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
        return result;
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
}
