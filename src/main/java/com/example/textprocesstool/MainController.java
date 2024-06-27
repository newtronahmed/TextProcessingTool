package com.example.textprocesstool;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController {

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

        // Add a listener to the inputTextArea to update word counts in real-time
        inputTextArea.textProperty().addListener((observable, oldValue, newValue) -> updateCounts(newValue));
    }

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

    private void replaceRegex() {
        String inputText = inputTextArea.getText();
        String regex = regexField.getText();
        String replacement = replacementField.getText();
        String result = inputText.replaceAll(regex, replacement);
        resultListView.getItems().clear();
        resultListView.getItems().add("Result: " + result);
    }

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
    private void searchRegex() {
        String inputText = inputTextArea.getText();
        String regex = regexField.getText();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(inputText);
        resultListView.getItems().clear();

        while (matcher.find()) {
            resultListView.getItems().add("Found: " + matcher.group() + " at positions " + matcher.start() + " - " + matcher.end());
        }
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
}
