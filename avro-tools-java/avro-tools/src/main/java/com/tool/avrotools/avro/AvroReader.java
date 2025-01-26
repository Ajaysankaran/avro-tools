package com.tool.avrotools.avro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class AvroReader {

    public static void readAvro(File file, Consumer<Object> consumer) {
        DatumReader<GenericRecord> reader = new GenericDatumReader<>();

        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, reader)) {
            while (dataFileReader.hasNext()) {
                GenericRecord record = dataFileReader.next();
                consumer.accept(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        readAvro(new File("/Users/ajaysankaran/Desktop/code/project/sample1.avro"), record -> {
            System.out.println(record);
        });
    }
}
