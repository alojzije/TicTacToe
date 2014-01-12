package com.example.TicTacToe;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MultiPlayerActivity extends ActionBarActivity {
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_play);

        TicTacToe.clearButtons();
        TicTacToe.addButton(this,R.id.button1);
        TicTacToe.addButton(this,R.id.button2);
        TicTacToe.addButton(this,R.id.button3);
        TicTacToe.addButton(this,R.id.button4);
        TicTacToe.addButton(this,R.id.button5);
        TicTacToe.addButton(this,R.id.button6);
        TicTacToe.addButton(this,R.id.button7);
        TicTacToe.addButton(this,R.id.button8);
        TicTacToe.addButton(this,R.id.button9);
        TicTacToe.setNameforPlayerX("X");
        TicTacToe.setNameforPlayerO("O");
        TicTacToe.setTurn(0);


    }

    public void newTurn(View view) {
        // mark button either for playerO or for playerX
        if (TicTacToe.turn % 2 == 0)
            TicTacToe.playO(this, (Button) view);
        else
            TicTacToe.playX(this, (Button) view);

        //check if player_o won the game or if it's a DRAW;
        if (TicTacToe.isGameOver())
            endGame(TicTacToe.winner);

    }



    private void endGame(String winner) {
        Toast toast;
        //print the winning message
        if (winner == TicTacToe.DRAW)
            toast = Toast.makeText(getApplicationContext(), " Draw \n ", Toast.LENGTH_LONG);
        else
            toast = Toast.makeText(getApplicationContext(), winner + " won \n Up for a rematch? ", Toast.LENGTH_LONG);

        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
        //disable all the buttons
        TicTacToe.disableAllButtons();


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
