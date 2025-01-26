package com.tool.avrotools.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.TreeItem;

public class JSONTreeView {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static TreeItem<String> buildTree(String json) {
        try {
            JsonNode node = objectMapper.readTree(json);
            return buildTree("", node);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static TreeItem<String> buildTree(String name, JsonNode node) {
        TreeItem<String> item = new TreeItem<>();

        if (node.isObject()) {
            item.setValue(name); // Set the name of the current node
            node.fields().forEachRemaining(entry -> {
                String childName = entry.getKey();
                JsonNode childNode = entry.getValue();
                TreeItem<String> child = buildTree(childName, childNode);
                item.getChildren().add(child);
            });
        } else if (node.isArray()) {
            item.setValue(name); // Set the name of the array
            for (int i = 0; i < node.size(); i++) {
                JsonNode childNode = node.get(i);
                TreeItem<String> child = buildTree("[" + i + "]", childNode); // Use index as child name
                item.getChildren().add(child);
            }
        } else {
            // It's a value node (text, number, boolean, or null)
            item.setValue(name + " : " + node.asText());
        }

        return item;
    }
}
