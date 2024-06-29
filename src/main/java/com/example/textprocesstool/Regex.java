package com.example.textprocesstool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    private String inputText;
    private String regex;
    private String replacement;
    private Pattern pattern;

    public Regex(String inputText, String regex) {
        this.inputText = inputText;
        this.regex = regex;
        this.pattern = Pattern.compile(regex);
    }
    public Regex(String inputText, String regex, String replacement) {
        this.inputText = inputText;
        this.regex = regex;
        this.pattern = Pattern.compile(regex);
        this.replacement = replacement;
    }

    public Matcher matcher(){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(inputText);
    }
    public String replaceRegex(){
        return inputText.replaceAll(regex, replacement);
    }

}
