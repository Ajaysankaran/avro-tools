package com.tool.avrotools;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private TabPane tabPane;

    @FXML
    private void handleNew() {
        try {
            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("avro-viewer.fxml"));
            Parent root = loader.load();

            // Create a new Stage (window)
            Tab newTab = new Tab("New Avro Viewer");
            newTab.setContent(root); // Set the loaded FXML content inside the tab
            tabPane.getTabs().add(newTab);

            // Set the tab as the selected tab
            tabPane.getSelectionModel().select(newTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleNew();
    }
}
