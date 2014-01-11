package com.example.TicTacToe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class SinglePlayerActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    int turn;
    ArrayList<View> buttons;
    String[] buttonTags;
    String winner;
    Button currentBtn ;
    Drawable backgroundPic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        turn = 0;
        buttons = new ArrayList<View>();
        buttons.add(findViewById(R.id.button1));
        buttons.add(findViewById(R.id.button2));
        buttons.add(findViewById(R.id.button3));
        buttons.add(findViewById(R.id.button4));
        buttons.add(findViewById(R.id.button5));
        buttons.add(findViewById(R.id.button6));
        buttons.add(findViewById(R.id.button7));
        buttons.add(findViewById(R.id.button8));
        buttons.add(findViewById(R.id.button9));
        //initialize for saving state
        buttonTags = new String[]{"empty", "empty", "empty",
                "empty", "empty", "empty",
                "empty", "empty", "empty"};


        if (savedInstanceState != null) {
            turn = savedInstanceState.getInt("turn");
            buttonTags = savedInstanceState.getStringArray("buttonTags");
            configureDisplay();
        }
    }




    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("turn", turn);
        outState.putStringArray("buttonTags", buttonTags);


    }

    public void newTurn(View view) {

        turn++;
        //set the clicked button to currentBtn so it can be marked
        currentBtn = ((Button) view);
        //mark the button
        //user is user's tag, and pc computer's
        markButton("user");
        //check if user won the game;
        checkIfWon();

        //if user hasn't won, it's computer's turn
        if (winner == "nobody"){
            turn++;
            pcPlay();
        }
        //check if pc won the game
        checkIfWon();

    }

    private void markButton(String tag) {
        winner = tag;
        currentBtn.setTag(tag);
        currentBtn.setEnabled(false);

        int backgroundRes = tag == "user" ? R.drawable.user : R.drawable.pc;
        backgroundPic = getResources().getDrawable(backgroundRes);

        //set background pic
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
            currentBtn.setBackgroundDrawable(backgroundPic);
        else
            currentBtn.setBackground(backgroundPic);

        updateOnSavedStateResources(tag);
    }

    private void configureDisplay() {
        for( int i = 0; i<9; i++){
            if (buttonTags[i] != "empty")
                markButton(buttonTags[i]);
        }

    }
    private void updateOnSavedStateResources(String tag) {
        switch (currentBtn.getId()) {
            case R.id.button1:
                buttonTags[0] = tag;
                break;
            case R.id.button2:
                buttonTags[1] = tag;
                break;
            case R.id.button3:
                buttonTags[2] = tag;
                break;
            case R.id.button4:
                buttonTags[3] = tag;
                break;
            case R.id.button5:
                buttonTags[4] = tag;
                break;
            case R.id.button6:
                buttonTags[5] = tag;
                break;
            case R.id.button7:
                buttonTags[6] = tag;
                break;
            case R.id.button8:
                buttonTags[7] = tag;
                break;
            case R.id.button9:
                buttonTags[8] = tag;
                break;
        }
    }

    private void pcPlay() {

        //check if pc can win in this turn
        int nextMove = findTwoConsecutive("pc");
        if (nextMove != -1) {
            currentBtn = (Button) buttons.get(nextMove);
            markButton("pc");
            return;
        }

        // if pc can't win, check if user can and try to stop it
        nextMove = findTwoConsecutive("user");
        if (nextMove != -1) {
            currentBtn = (Button) buttons.get(nextMove);
            markButton("pc");
            return;
        }

        //otherwise make pc play something semi-intelligent
        nextMove = pcChooseSpace();
        if (nextMove != -1) {
            currentBtn = (Button) buttons.get(nextMove);
            markButton("pc");
            return;
        }


    }

    private int pcChooseSpace() {
        int nextMove = -1;

        //check for empty corners whose one opposite and one neighbour corner are both marked by pc
        if (buttons.get(0).getTag() == null
                && buttons.get(8).getTag() == "pc"
                && (buttons.get(2).getTag() == "pc" || buttons.get(2).getTag() == "pc")) {
            nextMove = 0;

        } else if (buttons.get(8).getTag() == null
                && buttons.get(0).getTag() == "pc"
                && (buttons.get(0).getTag() == "pc" || buttons.get(8).getTag() == "pc")) {
            nextMove = 8;

        } else if (buttons.get(2).getTag() == null
                && buttons.get(6).getTag() == "pc"
                && (buttons.get(2).getTag() == "pc" || buttons.get(2).getTag() == "pc")) {
            nextMove = 2;
        } else if (buttons.get(6).getTag() == null
                && buttons.get(2).getTag() == "pc"
                && (buttons.get(0).getTag() == "pc" || buttons.get(8).getTag() == "pc")) {
            nextMove = 6;
        }

        //check for empty corners whose opposite corner are marked by pc
        else if (buttons.get(0).getTag() == null && buttons.get(8).getTag() == "pc") {
            nextMove = 0;
        } else if (buttons.get(8).getTag() == null && buttons.get(0).getTag() == "pc") {
            nextMove = 8;
        } else if (buttons.get(2).getTag() == null && buttons.get(6).getTag() == "pc") {
            nextMove = 2;
        } else if (buttons.get(6).getTag() == null && buttons.get(2).getTag() == "pc") {
            nextMove = 6;
        }

        //check for corners
        else if (buttons.get(0).getTag() == null) {
            nextMove = 0;
        } else if (buttons.get(2).getTag() == null) {
            nextMove = 2;
        } else if (buttons.get(6).getTag() == null) {
            nextMove = 6;
        } else if (buttons.get(8).getTag() == null) {
            nextMove = 8;
        }

        //check if middle is empty
        else if (buttons.get(4).getTag() == null) {
            nextMove = 4;
        }

        //if everything else fails, check for any empty space
        else{
            for (int i = 0; i < 9; i++) {
                if (buttons.get(i).getTag() == null)
                    nextMove = i;
            }
        }
        return nextMove;
    }

    private int findTwoConsecutive(String tag) {
        int nextMove = -1;

        //check horizontal
        if (buttons.get(0).getTag() == tag && buttons.get(1).getTag() == tag && buttons.get(2).getTag() == null) {
            nextMove = 2;
        } else if (buttons.get(0).getTag() == tag && buttons.get(2).getTag() == tag && buttons.get(1).getTag() == null) {
            nextMove = 1;
        } else if (buttons.get(1).getTag() == tag && buttons.get(2).getTag() == tag && buttons.get(0).getTag() == null) {
            nextMove = 0;

        } else if (buttons.get(3).getTag() == tag && buttons.get(4).getTag() == tag && buttons.get(5).getTag() == null) {
            nextMove = 5;
        } else if (buttons.get(3).getTag() == tag && buttons.get(5).getTag() == tag && buttons.get(4).getTag() == null) {
            nextMove = 4;
        } else if (buttons.get(4).getTag() == tag && buttons.get(5).getTag() == tag && buttons.get(3).getTag() == null) {
            nextMove = 3;

        } else if (buttons.get(6).getTag() == tag && buttons.get(7).getTag() == tag && buttons.get(8).getTag() == null) {
            nextMove = 8;
        } else if (buttons.get(6).getTag() == tag && buttons.get(8).getTag() == tag && buttons.get(7).getTag() == null) {
            nextMove = 7;
        } else if (buttons.get(7).getTag() == tag && buttons.get(8).getTag() == tag && buttons.get(6).getTag() == null) {
            nextMove = 6;
        }


        //check vertical
        else if (buttons.get(0).getTag() == tag && buttons.get(3).getTag() == tag && buttons.get(6).getTag() == null) {
            nextMove = 6;
        } else if (buttons.get(0).getTag() == tag && buttons.get(6).getTag() == tag && buttons.get(3).getTag() == null) {
            nextMove = 3;
        } else if (buttons.get(3).getTag() == tag && buttons.get(6).getTag() == tag && buttons.get(0).getTag() == null) {
            nextMove = 0;

        } else if (buttons.get(1).getTag() == tag && buttons.get(4).getTag() == tag && buttons.get(7).getTag() == null) {
            nextMove = 7;
        } else if (buttons.get(1).getTag() == tag && buttons.get(7).getTag() == tag && buttons.get(4).getTag() == null) {
            nextMove = 4;
        } else if (buttons.get(4).getTag() == tag && buttons.get(7).getTag() == tag && buttons.get(1).getTag() == null) {
            nextMove = 1;

        } else if (buttons.get(2).getTag() == tag && buttons.get(5).getTag() == tag && buttons.get(8).getTag() == null) {
            nextMove = 8;
        } else if (buttons.get(2).getTag() == tag && buttons.get(8).getTag() == tag && buttons.get(5).getTag() == null) {
            nextMove = 5;
        } else if (buttons.get(5).getTag() == tag && buttons.get(8).getTag() == tag && buttons.get(2).getTag() == null) {
            nextMove = 2;
        }

        //check diagonal
        else if (buttons.get(0).getTag() == tag && buttons.get(4).getTag() == tag && buttons.get(8).getTag() == null) {
            nextMove = 8;
        } else if (buttons.get(0).getTag() == tag && buttons.get(8).getTag() == tag && buttons.get(4).getTag() == null) {
            nextMove = 4;
        } else if (buttons.get(4).getTag() == tag && buttons.get(8).getTag() == tag && buttons.get(0).getTag() == null) {
            nextMove = 0;

        } else if (buttons.get(2).getTag() == tag && buttons.get(4).getTag() == tag && buttons.get(6).getTag() == null) {
            nextMove = 6;
        } else if (buttons.get(2).getTag() == tag && buttons.get(6).getTag() == tag && buttons.get(4).getTag() == null) {
            nextMove = 4;
        } else if (buttons.get(6).getTag() == tag && buttons.get(4).getTag() == tag && buttons.get(2).getTag() == null) {
            nextMove = 2;
        }

    return nextMove;
}

    private void checkIfWon() {

        //the logic

        //check horizontal
        if (buttons.get(0).getTag() != null &&
                buttons.get(0).getTag() == buttons.get(1).getTag() &&
                buttons.get(1).getTag() == buttons.get(2).getTag()) {

            endGame(winner + " (horizontal 123)");
        } else if (buttons.get(3).getTag() != null &&
                buttons.get(3).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(5).getTag()) {

            endGame(winner + " (horizontal 456)");
        } else if (buttons.get(6).getTag() != null &&
                buttons.get(6).getTag() == buttons.get(7).getTag() &&
                buttons.get(7).getTag() == buttons.get(8).getTag()) {

            endGame(winner + " (horizontal 789)");
        }

        //check vertical
        else if (buttons.get(0).getTag() != null &&
                buttons.get(0).getTag() == buttons.get(3).getTag() &&
                buttons.get(3).getTag() == buttons.get(6).getTag()) {

            endGame(winner + " (vertical 147)");
        } else if (buttons.get(1).getTag() != null &&
                buttons.get(1).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(7).getTag()) {

            endGame(winner + " (vertical 258)");
        } else if (buttons.get(2).getTag() != null &&
                buttons.get(2).getTag() == buttons.get(5).getTag() &&
                buttons.get(5).getTag() == buttons.get(8).getTag()) {

            endGame(winner + " (vertical 369)");
        }

        //check diagonal
        else if (buttons.get(0).getTag() != null &&
                buttons.get(0).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(8).getTag()) {

            endGame(winner + " (diagonal 159)");
        } else if (buttons.get(2).getTag() != null &&
                buttons.get(2).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(6).getTag()) {

            endGame(winner + " (diagonal 357)");
        } else if (turn == 9) {
            winner = "Draw. Nobody";
            endGame("Draw. Nobody");
        } else
            winner = "nobody";
    }

    private void endGame(String str) {
        //print the winning message
        Toast toast = Toast.makeText(getApplicationContext(), winner + " wins", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
        //disable all the buttons
        for(View btn: buttons)
            btn.setEnabled(false);

    }


    public void newGame(View view) {

        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        } else {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
}
