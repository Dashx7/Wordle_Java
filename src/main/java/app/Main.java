// Launches JavaFX app.
package wordle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Wordle Clone");

        GridPane gameGrid = new GridPane();
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                Text tile = new Text("_");
                tile.setStyle("-fx-font-size: 24; -fx-padding: 10;");
                gameGrid.add(tile, col, row);
            }
        }

        TextField guessInput = new TextField();
        guessInput.setPromptText("Enter guess");
        Button submitButton = new Button("Submit");

        VBox inputBox = new VBox(5, guessInput, submitButton);
        BorderPane root = new BorderPane();
        root.setCenter(gameGrid);
        root.setBottom(inputBox);

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
