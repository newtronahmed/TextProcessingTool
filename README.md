# Text Processing Tool - Project Requirements

## Overview

This document outlines the requirements for a Text Processing Tool application developed using JavaFX and Java. The primary goal of this project is to implement regex-based text processing features.

## Core Features

### 1. Main Interface

- **Text Area**: A large text input area for entering or displaying text to be processed.
- **Regex Field**: An input field for entering regular expressions.
- **Replace Field**: An input field for entering replacement text.

### 2. Regex Capabilities

The application supports various regex features, including:
- Sets
- Ranges
- Quantifiers
- Other standard regex operations

### 3. Text Processing Operations

- **Search**: Ability to search text using regex patterns.
- **Replace**: Capability to replace text based on regex matches.
- **Results Display**: Show match results and replaced text.

### 4. Real-time Statistics

- **Word Count**: Display and update the total number of words in the text in real-time.
- **Unique Word Count**: Display and update the count of unique words in the text in real-time.

### 5. File Handling

- **Text File Upload**:
    - Manual upload of text files
    - Drag-and-drop functionality for text files
- **File Content Display**: Automatically update the text area with the content of uploaded files.

### 6. Implementation Details

- Developed using JavaFX for the user interface
- Utilizes Java for backend logic
- Implements generics (e.g., `Set<String>`) for efficient data handling

## Planned Features (Not Yet Implemented)

1. **Text Highlighting**: Highlight matched text within the main text area.
2. **Predefined Validators**: Include built-in validators for common patterns such as:
    - Email addresses
    - Phone numbers
    - Other frequently used patterns

## Technical Requirements

1. **Programming Language**: Java
2. **GUI Framework**: JavaFX
3. **File Types Supported**: Text files (.txt)

## User Interface Components

1. Main text area
2. Regex input field
3. Replace text input field
4. Word count display
5. Unique word count display
6. File upload button
7. Drag-and-drop area for file upload

## Performance Considerations

- Real-time updating of word counts and unique word counts
- Efficient handling of large text files
- Responsive user interface during text processing operations

## Future Enhancements

- Implement text highlighting for regex matches
- Add predefined validators for common patterns
- Improve performance for very large text files