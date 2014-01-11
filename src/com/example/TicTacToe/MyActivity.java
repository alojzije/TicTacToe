package com.example.TicTacToe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    int turn = 0;
    ArrayList<View> buttons;
    String winner;
    Button currentBtn ;
    Drawable backgroundPic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
        
        

    }

    public void newTurn(View view) {

        turn++;
        currentBtn = ((Button) view);
        currentBtn.setEnabled(false);


        if (turn%2 != 0){
            backgroundPic = getResources().getDrawable(R.drawable.purple);
            currentBtn.setTag("purple");
            winner = "purple";
        }else {
            backgroundPic = getResources().getDrawable(R.drawable.yellow);
            currentBtn.setTag("yellow");
            winner = "yellow";
        }

        //set background pic
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
            currentBtn.setBackgroundDrawable(backgroundPic);
        else
            currentBtn.setBackground(backgroundPic);

        //check if the current player just won the game
        checkIfWon();


    }

    private void checkIfWon() {
        //the logic

        //check horizontal
        if (buttons.get(0).getTag()!= null &&
            buttons.get(0).getTag() == buttons.get(1).getTag() &&
            buttons.get(1).getTag() == buttons.get(2).getTag()) {

            endGame(winner + " (horizontal 123)");
        }
        else if (buttons.get(3).getTag()!= null &&
                buttons.get(3).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(5).getTag()) {

            endGame(winner + " (horizontal 456)");
        }
        else if (buttons.get(6).getTag()!= null &&
                buttons.get(6).getTag() == buttons.get(7).getTag() &&
                buttons.get(7).getTag() == buttons.get(8).getTag()) {

            endGame(winner + " (horizontal 789)");
        }

        //check vertical
        else if (buttons.get(0).getTag()!= null &&
                buttons.get(0).getTag() == buttons.get(3).getTag() &&
                buttons.get(3).getTag() == buttons.get(6).getTag()) {

            endGame(winner + " (vertical 147)");
        }

        else if (buttons.get(1).getTag()!= null &&
                buttons.get(1).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(7).getTag()) {

            endGame(winner + " (vertical 258)");
        }

        else if (buttons.get(2).getTag()!= null &&
                buttons.get(2).getTag() == buttons.get(5).getTag() &&
                buttons.get(5).getTag() == buttons.get(8).getTag()) {

            endGame(winner + " (vertical 369)");
        }

        //check diagonal
        else if (buttons.get(0).getTag()!= null &&
                buttons.get(0).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(8).getTag()) {

            endGame(winner + " (diagonal 159)");
        }

        else if (buttons.get(2).getTag()!= null &&
                buttons.get(2).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(6).getTag()) {

            endGame(winner + " (diagonal 357)");
        }
        else if (turn == 9)
            endGame("Draw. Nobody");
    }

    private void endGame(String str) {
        //print the winning message
        Toast.makeText(getApplicationContext(), str + " wins", Toast.LENGTH_LONG).show();
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
