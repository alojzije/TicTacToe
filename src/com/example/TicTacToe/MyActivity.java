package com.example.TicTacToe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_chooser);
    }

    public void invokeSinglePlayer(View view) {
    }

    public void invokeMultiPlayer(View view) {
        Intent intent = new Intent("com.example.TicTacToe.MultiPlayerActivity");
        startActivity(intent);
    }
}