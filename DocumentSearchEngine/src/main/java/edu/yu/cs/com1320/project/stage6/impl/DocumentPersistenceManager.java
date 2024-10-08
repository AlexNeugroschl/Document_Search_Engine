package edu.yu.cs.com1320.project.stage6.impl;

import com.google.gson.*;
import edu.yu.cs.com1320.project.stage6.Document;
import edu.yu.cs.com1320.project.stage6.PersistenceManager;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.lang.reflect.Type;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DocumentPersistenceManager implements PersistenceManager <URI, Document>{
    File dir;
    public DocumentPersistenceManager(File dir){
        this.dir = dir;
    }

    public void serialize(URI uri, Document doc) throws IOException{
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DocumentImpl.class, new DocumentSerializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        String customJSON = gson.toJson(doc);
        String path = dir.getPath() + File.separator + uri.toString().substring(7).replace("/", File.separator) + ".json";
        File directory = new File(path.substring(0, path.lastIndexOf(File.separator) + 1));
        directory.mkdirs();
        File file = new File(path);
        file.createNewFile();
        file.setWritable(true);
        file.setReadable(true);
        file.setExecutable(true);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(customJSON);
            writer.close();
        } catch (Exception e){
            throw new IOException("Failed to write to disk");
        }

    }
    public Document deserialize(URI uri) throws IOException {
        JsonDeserializer<Document> deserializer = (json, typeOfT, context) -> {
            Document doc = null;
            JsonObject jsonDoc = json.getAsJsonObject();
            try {String text = jsonDoc.get("Text").getAsString();
                JsonObject wordJson = jsonDoc.get("wordCount").getAsJsonObject();
                Map<String, Integer> words = new HashMap<>();
                for (Map.Entry<String, JsonElement> entry : wordJson.entrySet()) {
                    words.put(entry.getKey(), entry.getValue().getAsInt());
                }
                doc = new DocumentImpl(uri, text, words);
            } catch (NullPointerException e) {
                byte[] base64Decoded = DatatypeConverter.parseBase64Binary(jsonDoc.get("ByteArray").getAsString());
                doc = new DocumentImpl(uri, base64Decoded);
            }
            JsonObject metadataJson = jsonDoc.get("MetaData").getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : metadataJson.entrySet()) {
                doc.setMetadataValue(entry.getKey(), entry.getValue().getAsString());
            }
            return doc;};
        try {String jsonPath = dir.getPath() + File.separator + uri.toString().substring(7).replace("/", File.separator) + ".json";
            BufferedReader reader = new BufferedReader(new FileReader(jsonPath));
            String json = "";
            String line;
            while ((line = reader.readLine()) != null) {
                json += line + "\n";}
            reader.close();
            Gson gson = new GsonBuilder().registerTypeAdapter(DocumentImpl.class, deserializer).create();
            return gson.fromJson(json, DocumentImpl.class);
        } catch (Exception e){throw new IOException("Failed to Read from disk");}}
    /**
     * delete the file stored on disk that corresponds to the given key
     * @param uri
     * @return true or false to indicate if deletion occured or not
     * @throws IOException
     */
    public boolean delete(URI uri) throws IOException{
        try {
            boolean deleted = Files.deleteIfExists(Paths.get(dir.getPath() + File.separator + uri.toString().substring(7).replace("/", File.separator) + ".json"));
            try{
                String directory = dir.getPath() + File.separator + uri.toString().substring(7).replace("/", File.separator);
                while(directory.indexOf(File.separator) > 0){
                    directory = directory.substring(0, directory.lastIndexOf(File.separator));
                    Files.deleteIfExists(Paths.get(directory));
                }
            } catch (Exception e){
            }

            return deleted;
        } catch (Exception e) {
            throw new IOException("Failed to delete from disk");
        }
    }
    public class DocumentSerializer implements JsonSerializer<Document> {
        @Override
        public JsonElement serialize(Document src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject jsonDoc = new JsonObject();
            jsonDoc.addProperty("URI", src.getKey().toString());
            JsonObject jsonMap = new JsonObject();
            for (Map.Entry<String, String> entry : src.getMetadata().entrySet()) {
                jsonMap.addProperty(entry.getKey(), entry.getValue());
            }
            jsonDoc.add("MetaData", jsonMap);
            if (src.getDocumentTxt() != null) {
                jsonDoc.addProperty("Text", src.getDocumentTxt());
                JsonObject jsonMap2 = new JsonObject();
                for (Map.Entry<String, Integer> entry : src.getWordMap().entrySet()) {
                    jsonMap2.addProperty(entry.getKey(), entry.getValue());
                }
                jsonDoc.add("wordCount", jsonMap2);
            } else {
                String base64Encoded = DatatypeConverter.printBase64Binary(src.getDocumentBinaryData());
                jsonDoc.addProperty("ByteArray", base64Encoded);
            }
            return jsonDoc;
        }
    }
}
