package sample;

import javafx.util.Pair;

import java.util.ArrayList;

public class NMM {
    public static final int I_WHITE_PLAYER = 0;
    public static final int I_BLACK_PLAYER = 1;
    public static final int I_BLANK_FIELD = -1;
    public static final int I_OPEN_GAME_PHASE = 0;
    public static final int I_MID_GAME_PHASE = 1;
    public static final int I_END_GAME_PHASE = 2;
    public static final int I_AMOUNT_OF_PAWN = 9;
    public static final int I_AI_PLAYER = 0;
    public static final int I_MAN_PLAYER = 1;
    private static final int I_AMOUNT_OF_END_PHASE_PAWN = 3;

    private static ArrayList<Player> players;
    private static ArrayList<Integer> board = new ArrayList<>();

    private static ArrayList<String> white_moves = new ArrayList<>();
    private static ArrayList<String> black_moves = new ArrayList<>();


    public static void setPlayers(ArrayList<Player> pl)
    {
        players = pl;
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

    public static String getNameOfPlayer(int i)
    {
        return players.get(i).getName();
    }

    public static Player getPlayer(int i)
    {
        return players.get(i);
    }

    public static ArrayList<Pair<Pair<Integer, Integer>, Integer>> playerMove(Player actualPlayer) {
        return null;
    }

    public static void updateActualPhase(Player actualPlayer) {
        switch (actualPlayer.getPlayerPhase())
        {
            case I_OPEN_GAME_PHASE:
                if (actualPlayer.getAmountOfPawns() == I_AMOUNT_OF_PAWN)
                    actualPlayer.setPlayerPhase(I_MID_GAME_PHASE);
                break;
            case I_MID_GAME_PHASE:
                if (actualPlayer.getAmountOfPawns() == I_AMOUNT_OF_END_PHASE_PAWN)
                    actualPlayer.setPlayerPhase(I_END_GAME_PHASE);
                break;
        }
    }

    public static void checkIfCanDeleteOpponent() {
    }
}
