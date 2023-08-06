package com.github.smuddgge.squishydatabase.implementation.mongo;

import com.github.smuddgge.squishydatabase.Query;
import com.github.smuddgge.squishydatabase.record.Record;
import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MongoTableSelection<R extends Record> extends AbstractMongoTableSelection<R> {

    /**
     * The tables name.
     */
    private final @NotNull String name;

    /**
     * Used to initiate a sqlite table selection.
     *
     * @param name The name of the record.
     */
    public MongoTableSelection(@NotNull String name, @NotNull MongoDatabase database) {
        this.name = name;
        this.link(database);
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public @Nullable R getFirstRecord(@NotNull Query query) {

        // Create filter.
        List<Bson> filterList = this.createFilter(query);

        // Get document.
        Document document = this.getCollection().find(Filters.and(filterList)).first();

        if (document == null) return null;

        // Convert to record.
        String json = document.toJson();
        Gson gson = new Gson();
        return (R) gson.fromJson(json, this.createRecord().getClass());
    }

    @Override
    public @NotNull List<R> getRecordList() {

        // Get document.
        FindIterable<Document> documentList = this.getCollection().find();

        // Get a list of records.
        List<R> recordList = new ArrayList<>();

        for (Document document : documentList) {
            String json = document.toJson();
            Gson gson = new Gson();
            recordList.add((R) gson.fromJson(json, this.createRecord().getClass()));
        }

        return recordList;
    }

    @Override
    public List<R> getRecordList(@NotNull Query query) {
        // Create filter.
        List<Bson> filterList = this.createFilter(query);

        // Get document.
        FindIterable<Document> documentList = this.getCollection().find(Filters.and(filterList));

        // Get a list of records.
        List<R> recordList = new ArrayList<>();

        for (Document document : documentList) {
            String json = document.toJson();
            Gson gson = new Gson();
            recordList.add((R) gson.fromJson(json, this.createRecord().getClass()));
        }

        return recordList;
    }

    @Override
    public int getAmountOfRecords() {
        return Integer.parseInt(String.valueOf(this.getCollection().countDocuments()));
    }

    @Override
    public int getAmountOfRecords(@NotNull Query query) {
        return Integer.parseInt(String.valueOf(
                this.getCollection().countDocuments(Filters.and(this.createFilter(query)))
        ));
    }

    @Override
    public boolean insertRecord(@NotNull R record) {
        try {
            assert this.getDatabase() != null;
            if (this.getDatabase().isDisabled()) return false;

            this.removeRecord(record);
            this.getCollection().insertOne(this.createDocument(record));
            return true;

        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeRecord(@NotNull Record record) {
        return this.getCollection().deleteOne(this.createPrimaryKeyFilter(record)).wasAcknowledged();
    }

    @Override
    public boolean removeAllRecords(@NotNull Query query) {
        return this.getCollection().deleteOne(Filters.and(this.createFilter(query))).wasAcknowledged();
    }
}
