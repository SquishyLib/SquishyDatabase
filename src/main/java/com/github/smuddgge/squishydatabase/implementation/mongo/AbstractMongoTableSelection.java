package com.github.smuddgge.squishydatabase.implementation.mongo;

import com.github.smuddgge.squishydatabase.Query;
import com.github.smuddgge.squishydatabase.interfaces.TableSelection;
import com.github.smuddgge.squishydatabase.record.Record;
import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractMongoTableSelection<R extends Record>
        extends TableSelection<R, MongoDatabase> {

    /**
     * Used to get the instance of the mongo collection.
     * The collection represents the table.
     *
     * @return This mongo collection.
     */
    protected MongoCollection<Document> getCollection() {
        try {

            assert this.getDatabase() != null;
            return this.getDatabase().getDatabase().getCollection(this.getName());

        } catch (Exception exception) {
            exception.printStackTrace();

            if (this.getDatabase() != null) {
                this.getDatabase().setDisable(true);
            }
            return null;
        }
    }

    /**
     * Used to create a mongo filter.
     *
     * @param query The instance of the query.
     * @return The list of filters.
     */
    protected List<Bson> createFilter(@NotNull Query query) {
        List<Bson> filterList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : query.get().entrySet()) {
            filterList.add(Filters.eq(entry.getKey(), entry.getValue()));
        }

        return filterList;
    }

    protected <R extends Record> Document createDocument(R record) {
        Gson gson = new Gson();
        String json = gson.toJson(record);
        return Document.parse(json);
    }

    /**
     * Used to create a primary key filter.
     *
     * @param record The instance of a record.
     * @return The filter.
     */
    protected Bson createPrimaryKeyFilter(Record record) {
        return Filters.eq(record.getPrimaryKey().getKey(), record.getPrimaryKey().getValue());
    }
}
