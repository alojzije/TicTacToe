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
    protected static Activity activity;

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

    public static void setActivity(Activity activity) {
        TicTacToe.activity = activity;
    }
    public static void setNameforPlayerX(String X_) {
        playerX = X_;
    }

    public static void setNameforPlayerO(String O_) {
        playerO = O_;
    }


    protected static void addButton ( int buttonId) {
        buttons.add((Button) activity.findViewById(buttonId));
    }

    private static void markButton( Button clickedButton_, String tag) {
        turn ++;
        winner = tag;
        clickedButton = clickedButton_;

        clickedButton.setTag(tag);
        clickedButton.setEnabled(false);

        int backgroundRes = tag == playerO ? R.drawable.player_o : R.drawable.player_x;
        setBackground(clickedButton, backgroundRes);

    }

    @SuppressWarnings("deprecation")
    private static void setBackground(View view, int backgroundRes) {
        Drawable backgroundPic = activity.getResources().getDrawable(backgroundRes);

        //set background pic
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
            view.setBackgroundDrawable(backgroundPic);
        else
            view.setBackground(backgroundPic);
    }

    protected static Boolean isGameOver() {
        boolean isWon;
        //the logic
        View grid = activity.findViewById(R.id.grid);
        //check horizontal
        if (buttons.get(0).getTag() != null &&
                buttons.get(0).getTag() == buttons.get(1).getTag() &&
                buttons.get(1).getTag() == buttons.get(2).getTag()) {
            isWon = true;
            setBackground(grid, R.drawable.grid_won123);

        } else if (buttons.get(3).getTag() != null &&
                buttons.get(3).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(5).getTag()) {
            isWon = true;
            setBackground(grid, R.drawable.grid_won456);

        } else if (buttons.get(6).getTag() != null &&
                buttons.get(6).getTag() == buttons.get(7).getTag() &&
                buttons.get(7).getTag() == buttons.get(8).getTag()) {
            isWon = true;
            setBackground(grid, R.drawable.grid_won789);

        }

        //check vertical
        else if (buttons.get(0).getTag() != null &&
                buttons.get(0).getTag() == buttons.get(3).getTag() &&
                buttons.get(3).getTag() == buttons.get(6).getTag()) {
            isWon = true;
            setBackground(grid, R.drawable.grid_won147);

        } else if (buttons.get(1).getTag() != null &&
                buttons.get(1).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(7).getTag()) {
            isWon = true;
            setBackground(grid, R.drawable.grid_won258);

        } else if (buttons.get(2).getTag() != null &&
                buttons.get(2).getTag() == buttons.get(5).getTag() &&
                buttons.get(5).getTag() == buttons.get(8).getTag()) {
            isWon = true;
            setBackground(grid, R.drawable.grid_won369);

        }

        //check diagonal
        else if (buttons.get(0).getTag() != null &&
                buttons.get(0).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(8).getTag()) {
            isWon = true;
            setBackground(grid, R.drawable.grid_won159);

        } else if (buttons.get(2).getTag() != null &&
                buttons.get(2).getTag() == buttons.get(4).getTag() &&
                buttons.get(4).getTag() == buttons.get(6).getTag()) {
            isWon = true;
            setBackground(grid, R.drawable.grid_won357);

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

    public static void playO(Button clickedButton_) {

        markButton((Button)clickedButton_, playerO);
    }

    public static void playX( Button clickedButton_) {

        markButton((Button)clickedButton_, playerX);
    }

    public static void playPC( Button view) {

        int nextMove = findTwoConsecutive(playerX);
        if (nextMove != -1) {
            clickedButton = (Button) buttons.get(nextMove);
            markButton((Button)clickedButton, playerX);
            return;
        }

        // if player_x can't win, check if player_o can and try to stop it
        nextMove = findTwoConsecutive(playerO);
        if (nextMove != -1) {
            clickedButton = (Button) buttons.get(nextMove);
            markButton((Button)clickedButton, playerX);
            return;
        }

        //otherwise make player_x play something semi-intelligent
        nextMove = pcChooseSpace();
        if (nextMove != -1) {
            clickedButton = (Button) buttons.get(nextMove);
            markButton( (Button)clickedButton, playerX);
            return;
        }
    }

    private static int pcChooseSpace() {
        int nextMove = -1;

        //check for empty corners whose one opposite and one neighbour corner are both marked by player_x
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

        //check for empty corners whose opposite corner are marked by player_x
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
