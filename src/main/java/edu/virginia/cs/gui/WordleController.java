package edu.virginia.cs.gui;

import edu.virginia.cs.wordle.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


public class WordleController {
    @FXML
    private Wordle wordle = new WordleImplementation();
    @FXML
    private GridPane grid = new GridPane();
    //@FXML
    //Label errorLabel = new Label();
    @FXML
    private TextField enteredWord;
    @FXML
    private Label displayMessage = new Label();
    @FXML
    private Label playAgain = new Label();
    @FXML
    private Label startText = new Label();
    @FXML
    private Button yesButton = new Button("Yes");
    @FXML
    private Button noButton = new Button("No");


    protected LetterResult[] guessResult;

    int r = 0;

    public void initialize(){
        playAgain.setVisible(false);
        yesButton.setVisible(false);
        noButton.setVisible(false);
        startText.setText("Welcome! Enter a 5 letter word.");
        createGrid();
    }

    private void reset(){
        displayMessage.setText("");
        grid.getChildren().clear();
        enteredWord.setText("");
        enteredWord.setVisible(true);
        playAgain.setVisible(false);
        wordle = new WordleImplementation();
        yesButton.setVisible(false);
        noButton.setVisible(false);
        startText.setVisible(true);
        r = 0;
    }
    @FXML
    EventHandler<ActionEvent> eventY = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            reset();
        }
    };
    @FXML
    EventHandler<ActionEvent> eventN = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            System.exit(0);
        }
    };

    int col = 1;
    @FXML
    TextField[][] squares = new TextField[6][5];
    @FXML
    private void createGrid(){
        for(int x = 0; x < 5; x++){
            ColumnConstraints cc = new ColumnConstraints();
            cc.setFillWidth(true);
            cc.setHgrow(Priority.ALWAYS);
            cc.setHalignment(HPos.CENTER);
            grid.getColumnConstraints().add(cc);
        }
        for(int y = 0; y<6; y++){
            RowConstraints rc = new RowConstraints();
            rc.setFillHeight(true);
            rc.setVgrow(Priority.ALWAYS);
            rc.setValignment(VPos.CENTER);
            grid.getRowConstraints().add(rc);
        }
        grid.setAlignment(Pos.CENTER);
        grid.setMinSize(100, 300);
        grid.setStyle("-fx-padding: 2; -fx-hgap: 2; fx-vgap:1 ;");
    }
    @FXML
    protected void onTextEntryEnter(KeyEvent event) {
        displayMessage.setText("");
        startText.setVisible(false);
        if(wordle.getRemainingGuesses()<1) {
            noGuesses();
        }
        else if(event.getCode().equals(KeyCode.ENTER)){
            try {
                enteredWord.setText(enteredWord.getText().strip());
                guessResult = wordle.submitGuess(enteredWord.getText());
                if(wordle.isWin()) {
                    gameWon();
                }
                displayGuess();
                enteredWord.setText("");
                r++;
            }catch(IllegalWordException e) {
                displayMessage.setText("word is invalid: enter a valid 5 letter word");
                enteredWord.setText("");
            }
        }
    }

    private void displayGuess() {
        for(int i = 0; i < 5; i++) {
            Label letterToAdd = new Label();
            letterToAdd.setPrefSize(40.0, 50.0);
            letterToAdd.setText(String.valueOf(enteredWord.getText().charAt(i)));
            if(guessResult[i].equals(LetterResult.YELLOW)){
                letterToAdd.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else if(guessResult[i].equals(LetterResult.GREEN)){
                letterToAdd.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            else if(guessResult[i].equals(LetterResult.GRAY)){
                letterToAdd.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
            }
            letterToAdd.setStyle("-fx-alignment: center;");
            grid.add(letterToAdd, i, r);
        }
    }

    private void gameWon() {
        playAgain.setVisible(true);
        enteredWord.setVisible(false);
        displayMessage.setText("Correct! Answer was : " + wordle.getAnswer());
        playAgain.setText("Play again?");
        yesButton.setVisible(true);
        noButton.setVisible(true);
        yesButton.setOnAction(eventY);
        noButton.setOnAction(eventN);
    }

    private void noGuesses() {
        enteredWord.setVisible(false);
        playAgain.setVisible(true);
        displayMessage.setText("No guesses remaining. Answer was: " + wordle.getAnswer());
        playAgain.setText("Play again?");
        yesButton.setVisible(true);
        noButton.setVisible(true);
        yesButton.setOnAction(eventY);
        noButton.setOnAction(eventN);
    }
}