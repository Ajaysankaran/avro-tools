package com.tool.avro_tools.service;

import com.tool.avro_tools.model.AvroRecord;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocalAvroViewerService {

    private List<AvroRecord> avroRecordList = new ArrayList<>();

    private DataFileReader<GenericRecord> dataFileReader;

    private DatumReader<GenericRecord> reader;

    private int index = 0;

    public boolean initReader(String filePath) {
        try {
            var file = new File(filePath);
            avroRecordList.clear();
            index = 0;
            reader = new GenericDatumReader<>();
            dataFileReader = new DataFileReader<>(file, reader);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<AvroRecord> getAvroRecord(int pageNo, int pageSize) {
        while (dataFileReader.hasNext()) {
            GenericRecord record = dataFileReader.next();
            index++;
            avroRecordList.add(new AvroRecord(index , record.get("record").toString()));
        }
        return avroRecordList;
    }

    public static void readAvro(String filePath) {
        DatumReader<GenericRecord> reader = new GenericDatumReader<>();
        var file = new File(filePath);

        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, reader)) {
            while (dataFileReader.hasNext()) {
                GenericRecord record = dataFileReader.next();
                System.out.println(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
