package edu.virginia.cs.gui;

import edu.virginia.cs.wordle.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class WordleController {
    @FXML
    private Label welcomeText;

    @FXML
    private Wordle wordle = new WordleImplementation();
    @FXML
    private GridPane grid = new GridPane();

    @FXML
    Label errorLabel = new Label();
    @FXML
    private TextField enteredWord;
    @FXML
    private TextField letterEntered;
    @FXML
    private Label gameResult;

    protected LetterResult[] guessResult;
    Label l1 = new Label(), l2 = new Label(), l3 = new Label(), l4 = new Label(), l5 = new Label();
    int r = 0;
    public void initialize(){
        grid.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setHgap(10.0);
        grid.setVgap(5.0);
        gameResult = new Label();
        gameResult.setText("");
        //enteredWord.setVisible(false);
    }

    @FXML
    protected void onTextEntryEnter(KeyEvent event) {
        int colind = 0;
        Label newChar = new Label();
        newChar.setText(enteredWord.getText());
        grid.add(newChar, colind, r);

        if(wordle.getRemainingGuesses()<1) {
            gameResult.setText("no guesses remaining. Answer was: " + wordle.getAnswer());
        }
        else if(event.getCode().equals(KeyCode.ENTER)){
            try {
                enteredWord.setText(enteredWord.getText().strip());
                guessResult = wordle.submitGuess(enteredWord.getText());
                errorLabel.setText("");

                for(int i = 0; i < 5; i++) {
                    Label letterToAdd = new Label();
                    letterToAdd.setText(String.valueOf(enteredWord.getText().charAt(i)));
                    if(guessResult.equals(LetterResult.YELLOW)){
                        grid.setBackground();
                    }
                    grid.add(letterToAdd, i+1, r);
                }

                enteredWord.setText("");
                r++;
                /*
                l1.setText(String.valueOf(enteredWord.getText().charAt(0)));
                l2.setText(String.valueOf(enteredWord.getText().charAt(1)));
                l3.setText(String.valueOf(enteredWord.getText().charAt(2)));
                l4.setText(String.valueOf(enteredWord.getText().charAt(3)));
                l5.setText(String.valueOf(enteredWord.getText().charAt(4)));
                 */

            }catch(IllegalWordException e) {
                errorLabel.setText("word is invalid: enter a valid 5 letter word");
                enteredWord.setText("");
            }
        }
    }




    /*
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("change y/n");
    }
     */

}