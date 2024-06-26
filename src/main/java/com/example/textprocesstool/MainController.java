package com.example.textprocesstool;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController {
    @FXML
//    public TextArea inputTextArea;
    public TextArea inputTextArea;
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private TextField regexField;

    @FXML
    private TextField replacementField;

    @FXML
    private Button matchButton;

    @FXML
    private Button replaceButton;

    @FXML
    private Button collectionButton;

    @FXML
    private ListView<String> resultListView;

    @FXML
    void initialize() {
        matchButton.setOnAction(event -> matchRegex());
        replaceButton.setOnAction(event -> replaceRegex());
        collectionButton.setOnAction(event -> manipulateCollection());
    }

    private void matchRegex() {
        String inputText = inputTextArea.getText();
        String regex = regexField.getText();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputText);
//        resultListView.getItems().clear();

        while (matcher.find()) {
            resultListView.getItems().add("Match: " + matcher.group() + " at positions " + matcher.start() + " - " + matcher.end());
        }
    }

    private void replaceRegex() {
        String inputText = inputTextArea.getText();
        String regex = regexField.getText();
        String replacement = replacementField.getText();
        String result = inputText.replaceAll(regex, replacement);
        resultListView.getItems().clear();
        resultListView.getItems().add("Result: " + result);
    }

    private void manipulateCollection() {
        // Example: Using a HashSet to remove duplicates
        String inputText = inputTextArea.getText();
        List<String> words = Arrays.asList(inputText.split("\\s+"));
        Set<String> uniqueWords = new HashSet<>(words);
        resultListView.getItems().clear();
        resultListView.getItems().add("Unique words: " + uniqueWords);
    }
}