package sample;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private String name;
    private int playerType;
    private int playerPhase;
    private int amountOdPawns;
    private ArrayList<String> history_of_moves;
    private ArrayList<Integer> pawn_behind_board;

    public Player(String name, int playerType, int playerPhase) {
        this.name = name;
        this.playerType = playerType;
        this.playerPhase = playerPhase;
        this.amountOdPawns = 0;
        history_of_moves = new ArrayList<>();
        pawn_behind_board = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
    }

    public int getPlayerType() {
        return playerType;
    }

    public void setPlayerType(int playerType) {
        this.playerType = playerType;
    }

    public int getPlayerPhase() {
        return playerPhase;
    }

    public void setPlayerPhase(int playerPhase) {
        this.playerPhase = playerPhase;
    }

    public int getAmountOdPawns() {
        return amountOdPawns;
    }

    public void updateAmountOdPawns(int amountOdPawns) {
        this.amountOdPawns += amountOdPawns;
    }

    public void addHistory(ArrayList<String> h)
    {
        history_of_moves.addAll(h);
    }

    public String getName() {
        return name;
    }

    public void setPawnOnBoard()
    {
        pawn_behind_board.remove(0);
    }
}
