package com.example.TicTacToe;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by alojzije on 1/12/14.
 */
public class TicTacToe {
    protected static int turn;
    protected static String winner;
    protected static Button clickedButton;
    //android.View arrayList
    protected static ArrayList<Button> buttons;

    //constants player playerX, player 0

    protected static String playerX;
    protected static String playerO;
    protected static final String DRAW;
    public static final String NOBODY;

    static {
        turn = 0;
        buttons = new ArrayList<Button>();
        playerX = "X";
        playerO = "O";
        DRAW = "Draw";
        NOBODY = "Nobody";
        winner = NOBODY;

    }
    public static void setNameforPlayerX(String X_) {
        playerX = X_;
    }

    public static void setNameforPlayerO(String O_) {
        playerO = O_;
    }


    protected static void addButton (Activity activity, int buttonId) {
        buttons.add((Button) activity.findViewById(buttonId));
    }

    private static void markButton(Activity activity, Button clickedButton_, String tag) {
        turn ++;
        winner = tag;
        clickedButton = clickedButton_;

        clickedButton.setTag(tag);
        clickedButton.setEnabled(false);

        int backgroundRes = tag == playerO ? R.drawable.user: R.drawable.pc;
        Drawable backgroundPic = activity.getResources().getDrawable(backgroundRes);

        //set background pic
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
            clickedButton.setBackgroundDrawable(backgroundPic);
        else
            clickedButton.setBackground(backgroundPic);

    }

    protected static Boolean isGameOver() {
        boolean isWon;
        //the logic

        //check horizontal
        if (buttons.get(0).getTag() != null &&
                buttons.get(0).getTag() == buttons.get(1).getTag() &&
                buttons.get(1).getTag() == buttons.get(2).getTag()) {
            isWon = true;
        } else if (buttons.get(3).getTag() != null &&
                buttons.get(3).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(5).getTag()) {
            isWon = true;
        } else if (buttons.get(6).getTag() != null &&
                buttons.get(6).getTag() == buttons.get(7).getTag() &&
                buttons.get(7).getTag() == buttons.get(8).getTag()) {
            isWon = true;
        }

        //check vertical
        else if (buttons.get(0).getTag() != null &&
                buttons.get(0).getTag() == buttons.get(3).getTag() &&
                buttons.get(3).getTag() == buttons.get(6).getTag()) {
            isWon = true;
        } else if (buttons.get(1).getTag() != null &&
                buttons.get(1).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(7).getTag()) {
            isWon = true;
        } else if (buttons.get(2).getTag() != null &&
                buttons.get(2).getTag() == buttons.get(5).getTag() &&
                buttons.get(5).getTag() == buttons.get(8).getTag()) {
            isWon = true;
        }

        //check diagonal
        else if (buttons.get(0).getTag() != null &&
                buttons.get(0).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(8).getTag()) {
            isWon = true;
        } else if (buttons.get(2).getTag() != null &&
                buttons.get(2).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(6).getTag()) {
            isWon = true;

        } else if (turn == 9) {
            isWon = true;
            winner = DRAW;
        } else{
            isWon = false;
            winner = NOBODY;
        }

        return isWon;
    }


    public static void setTurn(int turn_) {
        turn = turn_;

    }

    public static void playO(Activity activity, Button clickedButton_) {

        markButton(activity, (Button)clickedButton_, playerO);
    }

    public static void playX(Activity activity, Button clickedButton_) {

        markButton(activity, (Button)clickedButton_, playerX);
    }

    public static void playPC(Activity activity, Button view) {

        int nextMove = findTwoConsecutive(playerX);
        if (nextMove != -1) {
            clickedButton = (Button) buttons.get(nextMove);
            markButton(activity, (Button)clickedButton, playerX);
            return;
        }

        // if pc can't win, check if user can and try to stop it
        nextMove = findTwoConsecutive(playerO);
        if (nextMove != -1) {
            clickedButton = (Button) buttons.get(nextMove);
            markButton(activity, (Button)clickedButton, playerX);
            return;
        }

        //otherwise make pc play something semi-intelligent
        nextMove = pcChooseSpace();
        if (nextMove != -1) {
            clickedButton = (Button) buttons.get(nextMove);
            markButton(activity, (Button)clickedButton, playerX);
            return;
        }
    }

    private static int pcChooseSpace() {
        int nextMove = -1;

        //check for empty corners whose one opposite and one neighbour corner are both marked by pc
        if (buttons.get(0).getTag() == null
                && buttons.get(8).getTag() == playerX
                && (buttons.get(2).getTag() == playerX || buttons.get(2).getTag() == playerX)) {
            nextMove = 0;

        } else if (buttons.get(8).getTag() == null
                && buttons.get(0).getTag() == playerX
                && (buttons.get(0).getTag() == playerX || buttons.get(8).getTag() == playerX)) {
            nextMove = 8;

        } else if (buttons.get(2).getTag() == null
                && buttons.get(6).getTag() == playerX
                && (buttons.get(2).getTag() == playerX || buttons.get(2).getTag() == playerX)) {
            nextMove = 2;
        } else if (buttons.get(6).getTag() == null
                && buttons.get(2).getTag() == playerX
                && (buttons.get(0).getTag() == playerX || buttons.get(8).getTag() == playerX)) {
            nextMove = 6;
        }

        //check for empty corners whose opposite corner are marked by pc
        else if (buttons.get(0).getTag() == null && buttons.get(8).getTag() == playerX) {
            nextMove = 0;
        } else if (buttons.get(8).getTag() == null && buttons.get(0).getTag() == playerX) {
            nextMove = 8;
        } else if (buttons.get(2).getTag() == null && buttons.get(6).getTag() == playerX) {
            nextMove = 2;
        } else if (buttons.get(6).getTag() == null && buttons.get(2).getTag() == playerX) {
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

    private static int findTwoConsecutive(String tag) {
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

    public static void disableAllButtons() {
        for(Button btn: buttons)
            btn.setEnabled(false);
    }

    public static void clearButtons() {
        buttons.clear();
    }
}
