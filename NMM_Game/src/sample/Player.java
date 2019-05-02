package sample;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private String name;
    private int playerId;
    private int playerType;
    private int playerPhase;
    private int amountOfPawns;
    private ArrayList<Integer> heuristics; // pawns, two, moves, blocks -> if 1 then chosen heuristic else not chosen
    private ArrayList<String> history_of_moves;
    private ArrayList<Integer> pawn_behind_board;

    public Player(int playerId, String name, int playerType, int playerPhase, ArrayList<Integer> heuristics) {
        this.playerId = playerId;
        this.name = name;
        this.playerType = playerType;
        this.playerPhase = playerPhase;
        this.amountOfPawns = 0;
        this.heuristics = heuristics;
        history_of_moves = new ArrayList<>();
        pawn_behind_board = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
    }

    public int getPlayerType() {
        return playerType;
    }

    public int getPlayerPhase() {
        return playerPhase;
    }

    public void setPlayerPhase(int playerPhase) {
        this.playerPhase = playerPhase;
    }

    public int getAmountOfPawns() {
        return amountOfPawns;
    }

    public void updateAmountOfPawns(int amountOdPawns) {
        this.amountOfPawns += amountOdPawns;
    }

    public void addHistory(String h)
    {
        history_of_moves.add(h);
    }

    public String getName() {
        return name;
    }

    public void setPawnOnBoard()
    {
        pawn_behind_board.remove(0);
    }

    public void setPawnBehindBoard()
    {
        pawn_behind_board.add(amountOfPawns);
    }

    public int getFirstPawnBehindBoard()
    {
        return pawn_behind_board.get(0);
    }

    public ArrayList<Integer> getPawn_behind_board() {
        return pawn_behind_board;
    }

    public int getPlayerId() {
        return playerId;
    }

    public ArrayList<Integer> getHeuristics() {
        return heuristics;
    }

    public ArrayList<String> getHistory_of_moves() {
        return history_of_moves;
    }
}
