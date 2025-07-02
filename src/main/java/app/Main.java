package wordle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main extends Application {

    private static final int ROWS = 6;
    private static final int COLS = 5;
    private GridPane grid = new GridPane();
    private int currentRow = 0;
    private String answer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("Debugging start: JavaFX app has launched");

        // Load words
        List<String> words = loadAnswerList();
        answer = words.get(new Random().nextInt(words.size())).toUpperCase();
        System.out.println("Answer (for debugging): " + answer);

        // Build board
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Text cell = new Text("_");
                cell.setStyle("-fx-font-size: 24; -fx-padding: 10;");
                grid.add(cell, col, row);
            }
        }

        // User input
        TextField input = new TextField();
        input.setPromptText("Enter 5-letter word");
        Button submit = new Button("Submit");

        submit.setOnAction(e -> {
            String guess = input.getText().trim().toUpperCase();
            if (guess.length() != 5 || currentRow >= ROWS)
                return;

            List<WordChecker.LetterResult> results = WordChecker.checkGuess(guess, answer);

            for (int i = 0; i < COLS; i++) {
                char letter = guess.charAt(i);
                WordChecker.LetterResult res = results.get(i);

                String color;
                switch (res) {
                    case CORRECT:
                        color = "#6aaa64";
                        break; // green
                    case PRESENT:
                        color = "#c9b458";
                        break; // yellow
                    case ABSENT:
                        color = "#787c7e";
                        break; // gray
                    default:
                        color = "white";
                        break;
                }

                Label cell = new Label(String.valueOf(letter));
                cell.setStyle(String.format(
                        "-fx-font-size: 24; -fx-padding: 10; -fx-background-color: %s; -fx-fill: white;", color));
                grid.add(cell, i, currentRow);
            }

            currentRow++;
            input.clear();
        });

        VBox controls = new VBox(5, input, submit);
        BorderPane layout = new BorderPane();
        layout.setCenter(grid);
        layout.setBottom(controls);

        primaryStage.setTitle("Wordle Clone");
        primaryStage.setScene(new Scene(layout, 400, 500));
        primaryStage.show();
    }

    private List<String> loadAnswerList() throws Exception {
        var stream = getClass().getResourceAsStream("/answerlist.txt");
        if (stream == null) {
            throw new RuntimeException("Missing resource: answerlist.txt");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            return reader.lines().collect(Collectors.toList());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
