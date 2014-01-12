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
        setContentView(R.layout.main_chooser);
        buttons = new ArrayList<View>();

    }

    public void invokeSinglePlayer(View view) {
    }

    public void invokeMultiPlayer(View view) {
        Intent intent = new Intent(this, MultiPlayerActivity.class);
        startActivity(intent);
    }
}