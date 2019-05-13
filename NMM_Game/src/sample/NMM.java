package sample;

import java.util.ArrayList;
import java.util.Arrays;

public class NMM {
    public static final int I_WHITE_PLAYER = 0;
    public static final int I_BLACK_PLAYER = 1;
    public static final int I_BLANK_FIELD = -1;
    public static final int I_OPEN_GAME_PHASE = 0;
    public static final int I_MID_GAME_PHASE = 1;
    public static final int I_END_GAME_PHASE = 2;
    public static final int I_MINI_MAXI_ALG = 1;
    public static final int I_ALPHA_BETA_ALG = 2;
    public static final int I_AMOUNT_OF_PAWN = 9;
    public static final int I_AI_PLAYER = 0;
    public static final int I_MAN_PLAYER = 1;
    private static final int I_AMOUNT_OF_END_PHASE_PAWN = 3;
    public static final int I_SECOND_FIELD = 2;
    public static final int I_FIRST_FIELD = 0;
    public static final int I_DEPTH_FOR_ALG = 4;
    public static final int I_DRAW = 10;

    private static ArrayList<Player> players;
    private static ArrayList<Integer> board;
    private static ArrayList<String> history_of_moves;
    private static int game_counter;

    public static void newGame() {
        // clear all fields on board
        Integer[] data = new Integer[24];
        Arrays.fill(data, NMM.I_BLANK_FIELD);
        board = new ArrayList<>(Arrays.asList(data));

        // clear history of moves
        history_of_moves = new ArrayList<>();

        // clear players
        players = new ArrayList<>();

        // clear counter
        game_counter = 0;

        // clear time in algorithm
        //Algorithm.setConst(0, 0);
    }

    public static ArrayList<String> getHistory_of_moves() {
        return history_of_moves;
    }

    public static void incrementGameCounter() {
        NMM.game_counter++;
    }

    public static int getGame_counter() {
        return game_counter;
    }

    public static boolean isGameOver() {
        return Algorithm.isGameOver(board);
    }

    public static void addHistory(String h)
    {
        history_of_moves.add(h);
        System.out.println(h);
    }

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

    public static ArrayList<Integer> getActualBoard()
    {
        return board;
    }

    public static ArrayList<Integer> makeMove(Player player) {
        ArrayList<Integer> score;
        long startTime, endTime, totalTime;

        if (player.getAlgorithm() == I_MINI_MAXI_ALG) {
            startTime = System.nanoTime();

            if (player.getPlayerPhase() == I_OPEN_GAME_PHASE)
                score = Algorithm.miniMaxiOpenPhase(player.getPlayerId(), Algorithm.I_MAX_TURN, I_DEPTH_FOR_ALG, new Node(0, Node.I_NO_MILL, board)).getBoard();
            else // (p.getPlayerPhase() != I_OPEN_GAME_PHASE)
                score = Algorithm.miniMaxiMidEndPhase(player.getPlayerId(), Algorithm.I_MAX_TURN, I_DEPTH_FOR_ALG, new Node(0, Node.I_NO_MILL, board)).getBoard();
            endTime   = System.nanoTime();
            totalTime = endTime - startTime;
            player.addTime(totalTime);

        } else { // (p.getPlayerPhase() != I_OPEN_GAME_PHASE)
            startTime = System.nanoTime();
            if (player.getPlayerPhase() == I_OPEN_GAME_PHASE)
                score = Algorithm.alphaBetaOpenPhase(player.getPlayerId(), Algorithm.I_MAX_TURN, I_DEPTH_FOR_ALG, new Node(0, Node.I_NO_MILL, board), new Node(Double.NEGATIVE_INFINITY, Node.I_NO_MILL, board), new Node(Double.POSITIVE_INFINITY, Node.I_NO_MILL, board)).getBoard();
            else
                score = Algorithm.alphaBetaMidEndPhase(player.getPlayerId(), Algorithm.I_MAX_TURN, I_DEPTH_FOR_ALG, new Node(0, Node.I_NO_MILL, board), new Node(Double.NEGATIVE_INFINITY, Node.I_NO_MILL, board), new Node(Double.POSITIVE_INFINITY, Node.I_NO_MILL, board)).getBoard();
            endTime = System.nanoTime();
            totalTime = endTime - startTime;
            player.addTime(totalTime);
        }
        return score;
    }

    public static int getWinner()
    {
        int pawns = Algorithm.countPawns(I_WHITE_PLAYER, board);
        return pawns > 0 ? I_WHITE_PLAYER : (pawns < 0 ? I_BLACK_PLAYER : I_DRAW);
    }

    public static String getNameOfPlayer(int i)
    {
        return players.get(i).getName();
    }

    public static Player getPlayer(int i)
    {
        return players.get(i);
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

    public static boolean checkIfCanDeleteOpponent(Player actualPlayer) { // search mills
        return Algorithm.isCloseMill(board, actualPlayer.getLastMove(NMM.I_SECOND_FIELD));
    }
}
