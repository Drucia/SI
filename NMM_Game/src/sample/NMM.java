package sample;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class NMM {
    public static final int I_WHITE_PLAYER = 0;
    public static final int I_BLACK_PLAYER = 1;
    public static final ArrayList<String> players = new ArrayList<>(Arrays.asList("white", "black"));
    public static final int I_BLANK_FIELD = -1;
    public static final int I_OPEN_GAME_PHASE = 0;
    public static final int I_MID_GAME_PHASE = 1;
    public static final int I_END_GAME_PHASE = 2;
    public static final int I_AMOUNT_OF_PAWN = 9;
    public static final int I_AI_PLAYER = 0;
    public static final int I_MAN_PLAYER = 1;
    public static int w_player;
    public static int b_player;
    public static ArrayList<Integer> board = new ArrayList<>();

    private static ArrayList<String> white_moves = new ArrayList<>();
    private static ArrayList<String> black_moves = new ArrayList<>();

    public static void setPlayers(int w_play, int b_play)
    {
        w_player = w_play;
        b_player = b_play;
    }

    public static int getPlayerType(int i)
    {
        return i == I_WHITE_PLAYER ? w_player : b_player;
    }

    public static void updateBoard(ArrayList<Integer> b)
    {
        board = b;
    }

    public static void updateFieldOfBoard(int idx, int val)
    {
        board.set(idx, val);
    }

    public static int getBoardSize()
    {
        return board.size();
    }

    public static ArrayList<Integer> getActualBoard()
    {
        return board;
    }

    public static ArrayList<Pair<Pair<Integer, Integer>, Integer>> makeMove(int player)
    {
        return null;
    }
}
