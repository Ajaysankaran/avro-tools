module org.apache.avro {
    requires java.base;
    requires transitive java.logging; // If Avro uses logging APIs
    requires transitive com.fasterxml.jackson.core; // Add this
    requires com.fasterxml.jackson.databind;       // Include if also needed
//    requires com.fasterxml.jackson.annotation;

    exports org.apache.avro;
    exports org.apache.avro.generic;
    exports org.apache.avro.io;
    exports org.apache.avro.specific;
    exports org.apache.avro.file;
    exports org.apache.avro.reflect;
    uses org.apache.avro.Conversion;

    opens org.apache.avro.io to java.base; // If reflection is used internally
}