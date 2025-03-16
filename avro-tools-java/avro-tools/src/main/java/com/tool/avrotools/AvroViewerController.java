package com.tool.avrotools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tool.avrotools.model.AvroData;
import com.tool.avrotools.utils.JSONTreeView;
import com.tool.avrotools.utils.TableViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

public class AvroViewerController {

    @FXML
    private TreeView<String> treeView1;

    @FXML
    private TableView<AvroData> viewerTable;

    @FXML
    private TableColumn<AvroData, Integer> recordNoColumn;

    @FXML
    private TableColumn<AvroData, String> recordColumn;

    @FXML
    private RadioButton fromFileRadioButton;

    @FXML
    private RadioButton fromS3RadioButton;

    @FXML
    private HBox fileInputContainer;

    @FXML
    private VBox s3InputContainer;

    @FXML
    private TextField selectedFileField;

    @FXML
    private TextField regionField;

    @FXML
    private TextField accessKeyField;

    @FXML
    private PasswordField secretKeyField;

    @FXML
    private TextField bucketNameField;

    @FXML
    private TextField keyField;

    @FXML
    public void initialize() throws JsonProcessingException {
        initializeRadioButtons();
        initializeTable();
    }


    @FXML
    private void handleLoadFile(ActionEvent event) throws JsonProcessingException {
        // Logic for loading a file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");

        // Set file extension filters (optional)
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Avro Files", "*.avro"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        // Open the file chooser dialog
        Stage stage = (Stage) selectedFileField.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Display the selected file in the TextField
        if (selectedFile != null) {
            selectedFileField.setText(selectedFile.getAbsolutePath());
            treeView1.setRoot(null);
            viewerTable.setItems(TableViewUtils.readAvroRecordsFromFile(selectedFile));
        }

    }

    @FXML
    private void handleSubmitS3() {
        // Collect S3 details and process
        String region = regionField.getText();
        String accessKey = accessKeyField.getText();
        String secretKey = secretKeyField.getText();
        String bucketName = bucketNameField.getText();
        String key = keyField.getText();

    }

    private void initializeRadioButtons() {
        fromFileRadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                fromS3RadioButton.setSelected(false);

                fileInputContainer.setVisible(true);
                fileInputContainer.setManaged(true);
                s3InputContainer.setVisible(false);
                s3InputContainer.setManaged(false);
            }
        });

        fromS3RadioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                fromFileRadioButton.setSelected(false);

                fileInputContainer.setVisible(false);
                fileInputContainer.setManaged(false);
                s3InputContainer.setVisible(true);
                s3InputContainer.setManaged(true);
            }
        });

        // Set default state
        fromFileRadioButton.setSelected(true);
        fileInputContainer.setVisible(true);
        s3InputContainer.setVisible(false);
    }

    private void initializeTable() {
        // Set up table
        recordNoColumn.setCellValueFactory(new PropertyValueFactory<>("recordNo"));
        recordColumn.setCellValueFactory(new PropertyValueFactory<>("record"));

        viewerTable.widthProperty().addListener((observable, oldValue, newValue) -> {
            double tableWidth = newValue.doubleValue();

            recordNoColumn.setPrefWidth(tableWidth * 0.2);
            recordColumn.setPrefWidth(tableWidth * 0.8);
        });

        viewerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showDataInJsonView();
            }
        });
    }

    @FXML
    public void onRowSelect(MouseEvent mouseEvent) {
        showDataInJsonView();
    }

    private void showDataInJsonView() {
        AvroData avroData = viewerTable.getSelectionModel().getSelectedItem();
        if (Objects.nonNull(avroData)) {
            TreeItem<String> treeItem = JSONTreeView.buildTree(avroData.getRecord());
            treeView1.setRoot(treeItem);
            treeView1.setShowRoot(false);
        }
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.C) {
            // Copy selected record to clipboard
            String record = viewerTable.getSelectionModel().getSelectedItem().getRecord();
            Clipboard clipboard = Clipboard.getSystemClipboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(record);
            clipboard.setContent(content);
        }
    }
}