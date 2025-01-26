module com.tool.avrotools {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires com.tools.avro;
//    requires lombok;

    opens com.tool.avrotools to javafx.fxml;
    exports com.tool.avrotools;
    exports com.tool.avrotools.avro;
    opens com.tool.avrotools.avro to javafx.fxml;
    exports com.tool.avrotools.utils;
    opens com.tool.avrotools.utils to javafx.fxml;
    exports com.tool.avrotools.model;
    opens com.tool.avrotools.model to javafx.fxml;
}