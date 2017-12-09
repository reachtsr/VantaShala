package com.vs.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.util.JSON;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GeetaKrishna on 03-Dec-17.
 **/
@Component
@Slf4j
public class MongoCollectionManager {
    private static final int BUFFER_SIZE = 8192; // 8K bytes / 2 bytes = 4K characters
    private static final int EOF = -1;
    private static final int START = 0;

    public static void cleanAndFill(DB mongoDB, String location, String name) {

        if (mongoDB.collectionExists(name)) {
            mongoDB.getCollection(name).drop();
        }
        DBCollection collection = mongoDB.createCollection(name, new BasicDBObject());
        fill(collection, location);
    }

    private static void fill(DBCollection collection, String collectionContentFilePath) {
        StringBuilder stringBuilder = new StringBuilder("[");

        try {
            InputStreamReader inputStreamReader = new InputStreamReader( //
                    MongoCollectionManager.class.getClassLoader().getResourceAsStream(collectionContentFilePath), "UTF-8");
            CharBuffer buf = CharBuffer.allocate(BUFFER_SIZE);
            for (int read = inputStreamReader.read(buf); read != EOF; read = inputStreamReader.read(buf)) {
                buf.flip();
                stringBuilder.append(buf, START, read);
            }
            stringBuilder.append("]");

            String json = stringBuilder.toString();
            json = json.replaceAll("\n", ",");
           // log.info("{}", json);

            List<BasicDBObject> jsonList = new ArrayList<>();

            JSONArray jArr = new JSONArray(json);

            for (int i = 0; i < jArr.length() - 1; i++) {
                try {
                    JSONObject innerObj = jArr.getJSONObject(i);
                   // log.info("{} - {}", i, innerObj.toString());
                    BasicDBObject jsnObject = (BasicDBObject) JSON.parse(innerObj.toString());
                    jsonList.add(jsnObject);
                } catch (JSONException e) {
                    log.error("Unable to read input stream due to an exception! Exception: ", e);
                    continue;
                }
            }

            collection.insert(jsonList);
        } catch (IOException e) {
            log.error("Unable to read input stream due to an exception! Exception: ", e);
            throw new IllegalStateException(e);
        } catch (JSONException e) {
            log.error("Unable to read input stream due to an exception! Exception: ", e);
            throw new IllegalStateException(e);
        }
    }
}
