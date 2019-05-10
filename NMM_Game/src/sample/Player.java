package sample;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {
    private String name;
    private int playerId;
    private int playerType;
    private int playerPhase;
    private int amountOfPawns;
    private int algorithm;
    private int counter_of_moves;
    private ArrayList<Integer> heuristics;
    // ---- OPENING PHASE ----
    // closed morris, morris number, number of block pieces, pieces number, two conf, three conf
    // ---- MID PHASE ----
    // closed morris, morris number, number of block pieces, pieces number, open morris, double morris, win
    // ---- MID PHASE ----
    // two conf, three conf, closed morris, win
    // -> if 1 then chosen heuristic else not chosen
    private ArrayList<String> history_of_moves;
    private ArrayList<Integer> pawn_behind_board;

    public Player(int playerId, String name, int playerType, int playerPhase, int algorithm, ArrayList<Integer> heuristics) {
        this.playerId = playerId;
        this.name = name;
        this.playerType = playerType;
        this.playerPhase = playerPhase;
        this.amountOfPawns = 0;
        this.counter_of_moves = 0;
        this.algorithm = algorithm;
        this.heuristics = heuristics;
        history_of_moves = new ArrayList<>();
        pawn_behind_board = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
    }

    public int getAlgorithm() {
        return algorithm;
    }

    public int getPlayerType() {
        return playerType;
    }

    public int getPlayerPhase() {
        return playerPhase;
    }

    public void setPlayerPhase(int playerPhase) {
        this.playerPhase = playerPhase;
        counter_of_moves = 0;
    }

    public int getAmountOfPawns() {
        return amountOfPawns;
    }

    public void addHistory(String h)
    {
        history_of_moves.add(h);
        counter_of_moves++;
    }

    public String getName() {
        return name;
    }

    public void setPawnOnBoard()
    {
        pawn_behind_board.remove(0);
        amountOfPawns++;
    }

    public int getCounter_of_moves() {
        return counter_of_moves;
    }

//    public void setPawnBehindBoard()
//    {
//        pawn_behind_board.add(amountOfPawns--);
//    }

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

    public int getLastMove(int field) {
        String last_move = history_of_moves.get(history_of_moves.size()-1);
        String fields[] = last_move.split(" ");
        return Controller.list_of_fields_in_words.indexOf(fields[field]);
    }
}
