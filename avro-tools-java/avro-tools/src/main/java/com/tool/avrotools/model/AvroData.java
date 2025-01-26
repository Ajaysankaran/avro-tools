package com.tool.avrotools.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AvroData {
    private final SimpleIntegerProperty recordNo;
    private final SimpleStringProperty record;

    public AvroData(int recordNo, String record) {
        this.recordNo = new SimpleIntegerProperty(recordNo);
        this.record = new SimpleStringProperty(record);
    }

    public int getRecordNo() {
        return recordNo.get();
    }


    public String getRecord() {
        return record.get();
    }

}
