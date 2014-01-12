package com.example.TicTacToe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

public class MyActivity extends ActionBarActivity {
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_chooser);
    }

    public void invokeSinglePlayer(View view) {
        Intent intent = new Intent("com.example.TicTacToe.SinglePlayerActivity");
        startActivity(intent);
    }

    public void invokeMultiPlayer(View view) {
        Intent intent = new Intent("com.example.TicTacToe.MultiPlayerActivity");
        startActivity(intent);
    }
}