module org.apache.avro {
    requires transitive java.logging;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;

    exports org.apache.avro;
    exports org.apache.avro.generic;
    exports org.apache.avro.io;
    exports org.apache.avro.specific;
    exports org.apache.avro.file;
    exports org.apache.avro.reflect;

    opens org.apache.avro.io to com.fasterxml.jackson.databind;
}