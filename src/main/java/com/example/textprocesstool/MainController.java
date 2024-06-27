package com.example.textprocesstool;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class MainController {

    public Button uploadButton;
    @FXML
    private TextArea inputTextArea;

    @FXML
    private TextField regexField;

    @FXML
    private TextField replacementField;

    @FXML
    private Button matchButton;

    @FXML
    private Button replaceButton;
    @FXML
    private Button searchButton;

    @FXML
    private Button collectionButton;

    @FXML
    private ListView<String> resultListView;

    @FXML
    private Label uniqueWordsCountLabel;

    @FXML
    private Label totalWordsCountLabel;

    @FXML
    void initialize() {
        matchButton.setOnAction(event -> matchRegex());
        replaceButton.setOnAction(event -> replaceRegex());
        collectionButton.setOnAction(event -> manipulateCollection());
// Enable drag-and-drop for TextArea
        enableDragAndDrop();
        searchButton.setOnAction(event -> searchRegex());
        uploadButton.setOnAction(event -> uploadFile());
        // Add a listener to the inputTextArea to update word counts in real-time
        inputTextArea.textProperty().addListener((observable, oldValue, newValue) -> updateCounts(newValue));
    }

    @FXML
    private void matchRegex() {
        String inputText = inputTextArea.getText();
        String regex = regexField.getText();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputText);
        resultListView.getItems().clear();

        while (matcher.find()) {
            resultListView.getItems().add("Match: " + matcher.group() + " at positions " + matcher.start() + " - " + matcher.end());
        }
    }

    @FXML
    private void replaceRegex() {
        String inputText = inputTextArea.getText();
        String regex = regexField.getText();
        String replacement = replacementField.getText();
        String result = inputText.replaceAll(regex, replacement);
        resultListView.getItems().clear();
        resultListView.getItems().add("Result: " + result);
    }

    @FXML
    private void manipulateCollection() {
        String inputText = inputTextArea.getText();
        List<String> words = Arrays.asList(inputText.split("\\s+"));
        Set<String> uniqueWords = new HashSet<>(words);
        resultListView.getItems().clear();
        resultListView.getItems().add("Unique words: " + uniqueWords);
    }

    private void updateCounts(String newText) {
        String[] words = newText.split("\\s+");
        updateUniqueWordsCount(words);
        updateWordCount (words);
    }
    @FXML
    private void searchRegex() {
        String inputText = inputTextArea.getText();
        String regex = regexField.getText().trim();  // Trim whitespace

        if (regex.isEmpty()) {
            // Show an alert or update a status label
            showAlert("Error", "Empty Regex", "Please enter a regular expression to search.");
            return;
        }

        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(inputText);
            resultListView.getItems().clear();

            boolean found = false;
            while (matcher.find()) {
                resultListView.getItems().add("Found: " + matcher.group() + " at positions " + matcher.start() + " - " + matcher.end());
                found = true;
            }

            if (!found) {
                resultListView.getItems().add("No matches found.");
            }
        } catch (PatternSyntaxException e) {
            showAlert("Error", "Invalid Regex", "The entered regular expression is invalid: " + e.getMessage());
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void updateWordCount(String[] words){
        //get total words(alphanumeric) in text
        long totalWords = Arrays.stream(words)
                .map(word -> word.replaceAll("[^a-zA-Z0-9]", ""))
                .filter(word -> !word.isEmpty())
                .count();
        totalWordsCountLabel.setText(String.valueOf(totalWords));
//        totalWordsCountLabel.setText(String.valueOf(totalWords));
    }
    private void updateUniqueWordsCount(String[] words) {
        // Update unique words count
        String [] filteredWords = Arrays.stream(words)
                .map(word -> word.replaceAll("[^a-zA-Z0-9]", ""))
                .filter(word -> !word.isEmpty())
                .toArray(String[]::new);
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(filteredWords));
        uniqueWordsCountLabel.setText(String.valueOf(uniqueWords.size()));
    }
    @FXML
    // Drag-and-drop functionality for TextArea
    private void enableDragAndDrop() {
        inputTextArea.setOnDragOver(event -> {
            if (event.getGestureSource() != inputTextArea && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        inputTextArea.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasFiles()) {
                File file = dragboard.getFiles().get(0);
                if (file.getName().toLowerCase().endsWith(".txt")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        StringBuilder content = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                        inputTextArea.setText(content.toString().trim());
                        success = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
    @FXML
    private void uploadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Text File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                inputTextArea.setText(content.toString().trim());
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File Error");
                alert.setHeaderText("Error loading file");
                alert.setContentText("An error occurred while reading the file.");
                alert.showAndWait();
            }
        }
    }
}
