package com.tool.avrotools.utils;

import com.tool.avrotools.avro.AvroReader;
import com.tool.avrotools.model.AvroData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TableViewUtils {

    public static ObservableList<AvroData> readAvroRecordsFromFile(File file) {

        ObservableList<AvroData> data = FXCollections.observableList(new ArrayList<>());
        AtomicInteger count = new AtomicInteger(1);
        AvroReader.readAvro(file, record -> data.add(new AvroData(count.getAndIncrement(), record.toString())));
        return data;
    }
}
