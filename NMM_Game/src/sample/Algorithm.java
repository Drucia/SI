package sample;

import java.util.ArrayList;
import java.util.Arrays;

public class Algorithm
{
    private static final int I_MIN_TURN = 0;
    public static final int I_MAX_TURN = 1;
    private static long time_minimax;
    private static long time_alphabeta;

    public static void setConst(long t_a, long t_m)
    {
        time_minimax = t_a;
        time_alphabeta = t_m;
    }

    public static void addTime(long t_a, long t_m){
        time_minimax += t_a;
        time_alphabeta += t_m;
    }

    public static Node miniMaxiOpenPhase(int playerId, int flag, int depth, Node board)
    {
        if (isEndOfOpenPhase(depth) || depth == 0)
            return new Node(evaluateOpenPhaseFunction(playerId, board), board.getMill(), board.getBoard());

        ArrayList<Node> children;

        if (flag == I_MAX_TURN)
        {
            Node max = new Node(Double.NEGATIVE_INFINITY, 0, null);
            children = getPossMovesForPlayerOpenPhase(playerId, board.getBoard());

            for (Node child:children) {
                Node val = miniMaxiOpenPhase(getSecondPlayerId(playerId), I_MIN_TURN, depth-1, child);
                max = max.getValue() < val.getValue() ? new Node(val.getValue(), 0, child.getBoard()) : max;
            }

            return max;
        }
        else { //if (flag == I_MIN_TURN)
            Node min = new Node(Double.POSITIVE_INFINITY, 0, null);
            children = getPossMovesForPlayerOpenPhase(playerId, board.getBoard());

            for (Node child:children) {
                Node val = miniMaxiOpenPhase(getSecondPlayerId(playerId), I_MIN_TURN, depth-1, child);
                min = min.getValue() > val.getValue() ? new Node(val.getValue(), 0, child.getBoard()) : min;
            }

            return min;
        }
    }

    public static Node miniMaxiMidEndPhase(int playerId, int flag, int depth, Node board)
    {
        if (isGameOver(board.getBoard()) || depth == 0) {
            if (getPhaseForPlayer(playerId, board.getBoard()) == NMM.I_MID_GAME_PHASE)
                return new Node(evaluateMidPhaseFunction(playerId, board), board.getMill(), board.getBoard());
            else
                return new Node(evaluateEndPhaseFunction(playerId, board), board.getMill(), board.getBoard());
        }

        ArrayList<Node> children;

        if (flag == I_MAX_TURN)
        {
            Node max = new Node(Double.NEGATIVE_INFINITY, 0, null);

            if (getPhaseForPlayer(playerId, board.getBoard()) == NMM.I_MID_GAME_PHASE)
                children = getPossMovesForPlayerMidPhase(playerId, board.getBoard());
            else
                children = getPossMovesForPlayerEndPhase(playerId, board.getBoard());

            for (Node child:children) {
                Node val = miniMaxiMidEndPhase(getSecondPlayerId(playerId), I_MIN_TURN, depth-1, child);
                max = max.getValue() < val.getValue() ? new Node(val.getValue(), 0, child.getBoard()) : max;
            }

            return max;
        }
        else { //if (flag == I_MIN_TURN)
            Node min = new Node(Double.POSITIVE_INFINITY, 0, null);

            if (getPhaseForPlayer(playerId, board.getBoard()) == NMM.I_MID_GAME_PHASE)
                children = getPossMovesForPlayerMidPhase(playerId, board.getBoard());
            else
                children = getPossMovesForPlayerEndPhase(playerId, board.getBoard());

            for (Node child:children) {
                Node val = miniMaxiMidEndPhase(getSecondPlayerId(playerId), I_MIN_TURN, depth-1, child);
                min = min.getValue() > val.getValue() ? new Node(val.getValue(), 0, child.getBoard()) : min;
            }

            return min;
        }
    }

    private static ArrayList<Node> getPossMovesForPlayerEndPhase(int playerId, ArrayList<Integer> board) {
        Player player = NMM.getPlayer(playerId);
        ArrayList<Node> moves = new ArrayList<>();
        ArrayList<Integer> move;

        for(int i=0; i<board.size(); i++)
        {
            if (board.get(i) == playerId)
            {
                for (int n=0; n<board.size(); n++)
                {
                    if (board.get(n) == NMM.I_BLANK_FIELD && n != player.getLastMove(NMM.I_FIRST_FIELD)) // different shift than last
                    {
                        move = new ArrayList<>(board);
                        move.set(n,playerId);
                        move.set(i, NMM.I_BLANK_FIELD);

                        if (isCloseMill(move, n))
                            moves.add(new Node(0, Node.I_CLOSED_MILL, beatPawnMove(move, getSecondPlayerId(playerId), 1)));
                        else
                            moves.add(new Node(0, Node.I_NO_MILL, move));
                    }
                }
            }
        }

        return moves;
    }

    private static ArrayList<Node> getPossMovesForPlayerMidPhase(int playerId, ArrayList<Integer> board) {
        Player player = NMM.getPlayer(playerId);
        ArrayList<Node> moves = new ArrayList<>();
        ArrayList<Integer> move;

        for(int i=0; i<board.size(); i++)
        {
            if (board.get(i) == playerId)
            {
                ArrayList<Integer> neigh = getNeighbours(i);

                for (Integer n : neigh)
                {
                    int nei = board.get(n);
                    if (nei == NMM.I_BLANK_FIELD && n != player.getLastMove(NMM.I_FIRST_FIELD)) // different shift than last
                    {
                        move = new ArrayList<>(board);
                        move.set(n,playerId);
                        move.set(i, NMM.I_BLANK_FIELD);

                        if (isDoubleMill(move, n))
                            moves.add(new Node(0, Node.I_DOUBLE_MILL, beatPawnMove(move, getSecondPlayerId(playerId), 2)));
                        else if (isCloseMill(move, n))
                            moves.add(new Node(0, Node.I_CLOSED_MILL, beatPawnMove(move, getSecondPlayerId(playerId), 1)));
                        else
                            moves.add(new Node(0, Node.I_NO_MILL, move));
                    }
                }
            }
        }

        return moves;
    }

    private static double evaluateEndPhaseFunction(int player, Node board) {
        ArrayList<Integer> heu = NMM.getPlayer(player).getHeuristics(); // for end phase 13, 14, 15, 16
        int closed_morris = board.getMill() == Node.I_CLOSED_MILL ? 1 : 0;
        int two_conf = countTwoConfigurations(player, board.getBoard());
        int three_conf = 0; //TODO
        int win = isGameOver(getSecondPlayerId(player), board.getBoard()) ? 1 : 0; // ??

        return heu.get(13) * closed_morris + heu.get(14) * two_conf + heu.get(15) * three_conf + heu.get(16) * win;
    }

    private static double evaluateMidPhaseFunction(int player, Node board) {
        ArrayList<Integer> heu = NMM.getPlayer(player).getHeuristics(); // for mid phase 6, 7, 8, 9, 10, 11, 12
        int closed_morris = board.getMill() == Node.I_CLOSED_MILL ? 1 : 0;
        int morris_number = countMorris(player, board.getBoard());
        int block_opp_pieces = countOpponentBlockPawns(player, board.getBoard());
        int pieces_number = countPawns(player, board.getBoard());
        int opened_morris = 0; //TODO
        int double_morris = board.getMill() == Node.I_DOUBLE_MILL ? 1 : 0;
        int win = isGameOver(getSecondPlayerId(player), board.getBoard()) ? 1 : 0; // ??

        return heu.get(6) * closed_morris + heu.get(7) * morris_number + heu.get(8) * block_opp_pieces + heu.get(9) *
                pieces_number + heu.get(10) * opened_morris + heu.get(11) * double_morris + heu.get(12) * win;
    }

    private static boolean isEndOfOpenPhase(int act_depth) {
        return NMM.getGame_counter() + (NMM.I_DEPTH_FOR_ALG - act_depth) > 18; // each of players has 9 pawns on board
    }

    private static int evaluateOpenPhaseFunction(int player, Node board) {
        ArrayList<Integer> heu = NMM.getPlayer(player).getHeuristics(); // for open phase 0, 1, 2, 3, 4, 5
        int closed_morris = board.getMill() == Node.I_CLOSED_MILL ? 1 : 0;
        int morris_number = countMorris(player, board.getBoard());
        int block_opp_pieces = countOpponentBlockPawns(player, board.getBoard());
        int pieces_number = countPawns(player, board.getBoard());
        int two_conf = countTwoConfigurations(player, board.getBoard());
        int three_conf = 0; //TODO

        return heu.get(0) * closed_morris + heu.get(1) * morris_number + heu.get(2) * block_opp_pieces + heu.get(3) * pieces_number + heu.get(4) * two_conf + heu.get(5) * three_conf;
    }

    private static ArrayList<Node> getPossMovesForPlayerOpenPhase(int playerId, ArrayList<Integer> board) {
        ArrayList<Node> moves = new ArrayList<>();
        ArrayList<Integer> move;

        for (int i=0; i<board.size(); i++)
        {
            if (board.get(i) == NMM.I_BLANK_FIELD) {
                move = new ArrayList<>(board);
                move.set(i, playerId);

                if (isDoubleMill(move, i))
                    moves.add(new Node(0, Node.I_DOUBLE_MILL, beatPawnMove(move, getSecondPlayerId(playerId), 2)));
                else if (isCloseMill(move, i))
                    moves.add(new Node(0, Node.I_CLOSED_MILL, beatPawnMove(move, getSecondPlayerId(playerId), 1)));
                else
                    moves.add(new Node(0, Node.I_NO_MILL, move));
            }
        }

        return moves;
    }

    private static boolean isDoubleMill(ArrayList<Integer> board, int field) {
        int value = board.get(field);

        switch(field)
        {
            case 0:
                if ((board.get(1) == value && board.get(2) == value) && (board.get(9) == value &&
                        board.get(21) == value))
                    return true;
                break;
            case 1:
                if ((board.get(0) == value && board.get(2) == value) && (board.get(4) == value &&
                        board.get(7) == value))
                    return true;
                break;
            case 2:
                if ((board.get(1) == value && board.get(0) == value) && (board.get(14) == value &&
                        board.get(23) == value))
                    return true;
                break;
            case 3:
                if ((board.get(4) == value && board.get(5) == value) && (board.get(10) == value &&
                        board.get(18) == value))
                    return true;
                break;
            case 4:
                if ((board.get(1) == value && board.get(7) == value) && (board.get(3) == value &&
                        board.get(5) == value))
                    return true;
                break;
            case 5:
                if ((board.get(3) == value && board.get(4) == value) && (board.get(13) == value &&
                        board.get(20) == value))
                    return true;
                break;
            case 6:
                if ((board.get(7) == value && board.get(8) == value) && (board.get(11) == value &&
                        board.get(15) == value))
                    return true;
                break;
            case 7:
                if ((board.get(6) == value && board.get(8) == value) && (board.get(1) == value &&
                        board.get(4) == value))
                    return true;
                break;
            case 8:
                if ((board.get(6) == value && board.get(7) == value) && (board.get(12) == value &&
                        board.get(17) == value))
                    return true;
                break;
            case 9:
                if ((board.get(0) == value && board.get(21) == value) && (board.get(10) == value &&
                        board.get(11) == value))
                    return true;
                break;
            case 10:
                if ((board.get(9) == value && board.get(11) == value) && (board.get(3) == value &&
                        board.get(18) == value))
                    return true;
                break;
            case 11:
                if ((board.get(9) == value && board.get(10) == value) && (board.get(6) == value &&
                        board.get(15) == value))
                    return true;
                break;
            case 12:
                if ((board.get(8) == value && board.get(17) == value) && (board.get(13) == value &&
                        board.get(14) == value))
                    return true;
                break;
            case 13:
                if ((board.get(12) == value && board.get(14) == value) && (board.get(5) == value &&
                        board.get(20) == value))
                    return true;
                break;
            case 14:
                if ((board.get(12) == value && board.get(13) == value) && (board.get(2) == value &&
                        board.get(23) == value))
                    return true;
                break;
            case 15:
                if ((board.get(6) == value && board.get(11) == value) && (board.get(16) == value &&
                        board.get(17) == value))
                    return true;
                break;
            case 16:
                if ((board.get(19) == value && board.get(22) == value) && (board.get(15) == value &&
                        board.get(17) == value))
                    return true;
                break;
            case 17:
                if ((board.get(8) == value && board.get(12) == value) && (board.get(16) == value &&
                        board.get(15) == value))
                    return true;
                break;
            case 18:
                if ((board.get(19) == value && board.get(20) == value) && (board.get(3) == value &&
                        board.get(10) == value))
                    return true;
                break;
            case 19:
                if ((board.get(16) == value && board.get(22) == value) && (board.get(20) == value &&
                        board.get(18) == value))
                    return true;
                break;
            case 20:
                if ((board.get(18) == value && board.get(19) == value) && (board.get(5) == value &&
                        board.get(13) == value))
                    return true;
                break;
            case 21:
                if ((board.get(9) == value && board.get(0) == value) && (board.get(22) == value &&
                        board.get(23) == value))
                    return true;
                break;
            case 22:
                if ((board.get(21) == value && board.get(23) == value) && (board.get(16) == value &&
                        board.get(19) == value))
                    return true;
                break;
            case 23:
                if ((board.get(21) == value && board.get(22) == value) && (board.get(2) == value &&
                        board.get(14) == value))
                    return true;
                break;
        }
        return false;
    }
    public static Node alphaBetaMidEndPhase(int playerId, int flag, int depth, Node board, Node alpha, Node beta) {
        if (isGameOver(board.getBoard()) || depth == 0) {
            if (getPhaseForPlayer(playerId, board.getBoard()) == NMM.I_MID_GAME_PHASE)
                return new Node(evaluateMidPhaseFunction(playerId, board), board.getMill(), board.getBoard());
            else
                return new Node(evaluateEndPhaseFunction(playerId, board), board.getMill(), board.getBoard());
        }

        ArrayList<Node> children;

        if (flag == I_MAX_TURN)
        {
            if (getPhaseForPlayer(playerId, board.getBoard()) == NMM.I_MID_GAME_PHASE)
                children = getPossMovesForPlayerMidPhase(playerId, board.getBoard());
            else
                children = getPossMovesForPlayerEndPhase(playerId, board.getBoard());

            for (Node child:children) {
                Node val = alphaBetaMidEndPhase(getSecondPlayerId(playerId), I_MIN_TURN, depth-1, child, alpha, beta);
                if (val.getValue() > alpha.getValue())
                    alpha = new Node(val.getValue(), Node.I_NO_MILL, child.getBoard()); // found a better best move
                if (alpha.getValue() >= beta.getValue())
                    return alpha; // cut off
            }

            return alpha; // this is best move
        }
        else { //if (flag == I_MIN_TURN)

            if (getPhaseForPlayer(playerId, board.getBoard()) == NMM.I_MID_GAME_PHASE)
                children = getPossMovesForPlayerMidPhase(playerId, board.getBoard());
            else
                children = getPossMovesForPlayerEndPhase(playerId, board.getBoard());

            for (Node child:children) {
                Node val = alphaBetaMidEndPhase(getSecondPlayerId(playerId), I_MIN_TURN, depth-1, child, alpha, beta);
                if (val.getValue() < beta.getValue())
                    beta = new Node(val.getValue(), Node.I_NO_MILL, child.getBoard()); // opponent has found a better worse move
                if (alpha.getValue() >= beta.getValue())
                    return beta; // cut off
            }

            return beta;
        }
    }

    public static Node alphaBetaOpenPhase(int playerId, int flag, int depth, Node board, Node alpha, Node beta) {
        if (isEndOfOpenPhase(depth) || depth == 0)
            return new Node(evaluateOpenPhaseFunction(playerId, board), board.getMill(), board.getBoard());

        ArrayList<Node> children;

        if (flag == I_MAX_TURN)
        {
            children = getPossMovesForPlayerOpenPhase(playerId, board.getBoard());

            for (Node child:children) {
                Node val = alphaBetaOpenPhase(getSecondPlayerId(playerId), I_MIN_TURN, depth-1, child, alpha, beta);

                if (val.getValue() > alpha.getValue())
                    alpha = new Node(val.getValue(), Node.I_NO_MILL, child.getBoard()); // found a better best move
                if (alpha.getValue() >= beta.getValue())
                    return alpha; // cut off
            }

            return alpha; // this is best move
        }
        else { //if (flag == I_MIN_TURN)
            children = getPossMovesForPlayerOpenPhase(playerId, board.getBoard());

            for (Node child:children) {
                Node val = alphaBetaOpenPhase(getSecondPlayerId(playerId), I_MIN_TURN, depth-1, child, alpha, beta);
                if (val.getValue() < beta.getValue())
                    beta = new Node(val.getValue(), Node.I_NO_MILL, child.getBoard()); // opponent has found a better worse move
                if (alpha.getValue() >= beta.getValue())
                    return beta; // cut off
            }

            return beta;
        }
    }

//    public static Pair<ArrayList<Integer>, Double> alphabeta(int playerId, int flag, int depth, ArrayList<Integer> board, Pair<ArrayList<Integer>, Double> alpha, Pair<ArrayList<Integer>, Double> beta)
//    {
//        if (isGameOver(board) || depth == 0)
//            return new Pair<>(board, evaluateFunction(playerId, board));
//
//        ArrayList<ArrayList<Integer>> children;
//
//        if (flag == I_MAX_TURN)
//        {
//            children = getPossMovesForPlayer(playerId, board);
//
//            for (ArrayList<Integer> child:children) {
//                Pair<ArrayList<Integer>, Double> val = alphabeta(getSecondPlayerId(playerId), I_MIN_TURN, depth-1, child, alpha, beta);
//                if (val.getValue() > alpha.getValue())
//                        alpha = new Pair<>(child, val.getValue()); // found a better best move
//                if (alpha.getValue() >= beta.getValue())
//                    return alpha; // cut off
//            }
//
//            return alpha; // this is best move
//        }
//        else { //if (flag == I_MIN_TURN)
//            children = getPossMovesForPlayer(playerId, board);
//
//            for (ArrayList<Integer> child:children) {
//                Pair<ArrayList<Integer>, Double> val = alphabeta(getSecondPlayerId(playerId), I_MAX_TURN, depth-1, child, alpha, beta);
//                if (val.getValue() < beta.getValue())
//                    beta = new Pair<>(child, val.getValue()); // opponent has found a better worse move
//                if (alpha.getValue() >= beta.getValue())
//                    return beta; // cut off
//            }
//
//            return beta;
//        }
//    }

    private static int getSecondPlayerId(int playerId) {
        return playerId == NMM.I_WHITE_PLAYER ? NMM.I_BLACK_PLAYER : NMM.I_WHITE_PLAYER;
    }

//    private static ArrayList<ArrayList<Integer>> getPossMovesForPlayer(int playerId, ArrayList<Integer> board) {
//        Player player = NMM.getPlayer(playerId);
//        ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
//        ArrayList<Integer> move;
//
//        if (player.getPlayerPhase() == NMM.I_OPEN_GAME_PHASE) {
//            for (int i=0; i<board.size(); i++)
//            {
//                if (board.get(i) == NMM.I_BLANK_FIELD) {
//                    move = new ArrayList<>(board);
//                    move.set(i, playerId);
//
//                    if (isMill(move, i))
//                        moves.addAll(getAllMovesToBeatPawn(move, getSecondPlayerId(playerId)));
//                    else
//                        moves.add(move);
//                }
//            }
//        }
//        else
//        {
//            for(Integer field : board)
//            {
//                if (field == playerId)
//                {
//                    switch (player.getPlayerPhase())
//                    {
//                        case NMM.I_MID_GAME_PHASE:
//
//                            for(int i=0; i<board.size(); i++)
//                            {
//                                if (board.get(i) == playerId)
//                                {
//                                    ArrayList<Integer> neigh = getNeighbours(i);
//
//                                    for (int n=0; n<neigh.size(); n++)
//                                    {
//                                        if (board.get(n) == NMM.I_BLANK_FIELD && n != player.getLastMove(NMM.I_FIRST_FIELD)) // different shift than last
//                                        {
//                                            move = new ArrayList<>(board);
//                                            move.set(n,playerId);
//
//                                            if (isMill(move, i))
//                                                moves.addAll(getAllMovesToBeatPawn(move, getSecondPlayerId(playerId)));
//                                            else
//                                                moves.add(move);
//                                        }
//                                    }
//                                }
//                            }
//
//                            break;
//                        case NMM.I_END_GAME_PHASE:
//                            for(int i=0; i<board.size(); i++)
//                            {
//                                if (board.get(i) == playerId)
//                                {
//                                    for (int n=0; n<board.size(); n++)
//                                    {
//                                        if (board.get(n) == NMM.I_BLANK_FIELD && n != player.getLastMove(NMM.I_FIRST_FIELD)) // different shift than last
//                                        {
//                                            move = new ArrayList<>(board);
//                                            move.set(n,playerId);
//
//                                            if (isMill(move, i))
//                                                moves.addAll(getAllMovesToBeatPawn(move, getSecondPlayerId(playerId)));
//                                            else
//                                                moves.add(move);
//                                        }
//                                    }
//                                }
//                            }
//                            break;
//                    }
//                }
//            }
//        }
//        return moves;
//    }

    private static ArrayList<ArrayList<Integer>> getAllMovesToBeatPawn(ArrayList<Integer> move, int playerId) {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
        for (int j=0; j<move.size(); j++)
        {
            if (move.get(j) == playerId)
            {
                ArrayList<Integer> new_move = new ArrayList<>(move);
                new_move.set(j, NMM.I_BLANK_FIELD);
                moves.add(new_move);
            }
        }
        return moves;
    }

    private static ArrayList<Integer> beatPawnMove(ArrayList<Integer> move, int playerId, int how_much) {
        ArrayList<Integer> new_move = new ArrayList<>(move);

        for (int j=0; j<new_move.size() && how_much>0; j++)
        {
            if (new_move.get(j) == playerId) {
                new_move.set(j, NMM.I_BLANK_FIELD);
                how_much--;
            }
        }
        return new_move;
    }

    private static Double evaluateFunction(int player, ArrayList<Integer> board) {
        ArrayList<Integer> heu = NMM.getPlayer(player).getHeuristics();
        int moves = countMoves(player, board);
        int block = countOpponentBlockPawns(player, board);
        int two = countTwoConfigurations(player, board);
        int pawns = countPawns(player, board);
        int win = isGameOver(getSecondPlayerId(player), board) ? 1000 : isGameOver(player, board) ? -1000 : 0;
        int three = 413 * countMorris(player,board);

        return (double) heu.get(0) * pawns + heu.get(1) * two + heu.get(2) * moves + heu.get(3) * block + win + three;
    }

    private static int countMorris(int player, ArrayList<Integer> board) {
        final int amount_of_lines = 16;
        int counter_f = 0;
        int counter_s = 0;

        for (int i=0; i<amount_of_lines; i++)
            if (isMorrisInLine(i, player, board))
                counter_f++;
            else if (isMorrisInLine(i, getSecondPlayerId(player), board))
                counter_s++;

        return counter_f-counter_s;
    }

    private static boolean isMorrisInLine(int line, int value, ArrayList<Integer> board)
    {
        switch(line)
        {
            case 0:
                if ((board.get(0) == value && board.get(9) == value && board.get(21) == value))
                    return true;
                break;
            case 1:
                if ((board.get(3) == value && board.get(10) == value && board.get(18) == value))
                    return true;
                break;
            case 2:
                if ((board.get(6) == value && board.get(11) == value && board.get(15) == value))
                    return true;
                break;
            case 3:
                if ((board.get(1) == value && board.get(4) == value && board.get(7) == value))
                    return true;
                break;
            case 4:
                if ((board.get(16) == value && board.get(19) == value && board.get(22) == value))
                    return true;
                break;
            case 5:
                if ((board.get(8) == value && board.get(12) == value && board.get(17) == value))
                    return true;
                break;
            case 6:
                if ((board.get(5) == value && board.get(13) == value && board.get(20) == value))
                    return true;
                break;
            case 7:
                if ((board.get(2) == value && board.get(14) == value && board.get(23) == value))
                    return true;
                break;
            case 8:
                if ((board.get(0) == value && board.get(1) == value && board.get(2) == value))
                    return true;
                break;
            case 9:
                if ((board.get(3) == value && board.get(4) == value && board.get(5) == value))
                    return true;
                break;
            case 10:
                if ((board.get(6) == value && board.get(7) == value && board.get(8) == value))
                    return true;
                break;
            case 11:
                if ((board.get(9) == value && board.get(10) == value && board.get(11) == value))
                    return true;
                break;
            case 12:
                if ((board.get(12) == value && board.get(13) == value && board.get(14) == value))
                    return true;
                break;
            case 13:
                if ((board.get(15) == value && board.get(16) == value && board.get(17) == value))
                    return true;
                break;
            case 14:
                if ((board.get(18) == value && board.get(19) == value && board.get(20) == value))
                    return true;
                break;
            case 15:
                if ((board.get(21) == value && board.get(22) == value && board.get(23) == value))
                    return true;
                break;
        }
        return false;
    }

    private static int countTwoConfigurations(int player, ArrayList<Integer> board) {
        final int amount_of_lines = 16;
        int counter_f = 0;
        int counter_s = 0;

        for (int i=0; i<amount_of_lines; i++)
            if (isTwoConfigurationsInLine(i, player, board))
                counter_f++;
        else if (isTwoConfigurationsInLine(i, getSecondPlayerId(player), board))
                counter_s++;

        return counter_f - counter_s;
    }

    private static boolean isTwoConfigurationsInLine(int line, int value, ArrayList<Integer> board) {

        switch(line)
        {
            case 0:
                if ((board.get(0) == value && board.get(9) == value && board.get(21) == NMM.I_BLANK_FIELD) || (board.get(0) == value &&
                        board.get(21) == value && board.get(9) == NMM.I_BLANK_FIELD) || (board.get(21) == value && board.get(9) == value && board.get(0) == NMM.I_BLANK_FIELD))
                    return true;
                break;
            case 1:
                if ((board.get(3) == value && board.get(10) == value && board.get(18) == NMM.I_BLANK_FIELD) || (board.get(3) == value &&
                        board.get(18) == value && board.get(10) == NMM.I_BLANK_FIELD) || (board.get(10) == value && board.get(18) == value && board.get(3) == NMM.I_BLANK_FIELD))
                    return true;
                break;
            case 2:
                if ((board.get(6) == value && board.get(11) == value && board.get(15) == NMM.I_BLANK_FIELD) || (board.get(6) == value &&
                        board.get(15) == value && board.get(11) == NMM.I_BLANK_FIELD) || (board.get(11) == value && board.get(15) == value && board.get(6) == NMM.I_BLANK_FIELD))
                    return true;
                break;
            case 3:
                if ((board.get(1) == value && board.get(4) == value) || (board.get(1) == value &&
                        board.get(7) == value) || (board.get(4) == value && board.get(7) == value))
                    return true;
                break;
            case 4:
                if ((board.get(16) == value && board.get(19) == value) || (board.get(16) == value &&
                        board.get(22) == value) || (board.get(19) == value && board.get(22) == value))
                    return true;
                break;
            case 5:
                if ((board.get(8) == value && board.get(12) == value) || (board.get(8) == value &&
                        board.get(17) == value) || (board.get(12) == value && board.get(17) == value))
                    return true;
                break;
            case 6:
                if ((board.get(5) == value && board.get(13) == value) || (board.get(5) == value &&
                        board.get(20) == value) || (board.get(13) == value && board.get(20) == value))
                    return true;
                break;
            case 7:
                if ((board.get(2) == value && board.get(14) == value) || (board.get(2) == value &&
                        board.get(23) == value) || (board.get(14) == value && board.get(23) == value))
                    return true;
                break;
            case 8:
                if ((board.get(0) == value && board.get(1) == value) || (board.get(0) == value &&
                        board.get(2) == value) || (board.get(1) == value && board.get(2) == value))
                    return true;
                break;
            case 9:
                if ((board.get(3) == value && board.get(4) == value) || (board.get(3) == value &&
                        board.get(5) == value) || (board.get(4) == value && board.get(5) == value))
                    return true;
                break;
            case 10:
                if ((board.get(6) == value && board.get(7) == value) || (board.get(6) == value &&
                        board.get(8) == value) || (board.get(7) == value && board.get(8) == value))
                    return true;
                break;
            case 11:
                if ((board.get(9) == value && board.get(10) == value) || (board.get(9) == value &&
                        board.get(11) == value) || (board.get(10) == value && board.get(11) == value))
                    return true;
                break;
            case 12:
                if ((board.get(12) == value && board.get(13) == value) || (board.get(12) == value &&
                        board.get(14) == value) || (board.get(13) == value && board.get(14) == value))
                    return true;
                break;
            case 13:
                if ((board.get(15) == value && board.get(16) == value) || (board.get(16) == value &&
                        board.get(17) == value) || (board.get(15) == value && board.get(17) == value))
                    return true;
                break;
            case 14:
                if ((board.get(18) == value && board.get(19) == value) || (board.get(19) == value &&
                        board.get(20) == value) || (board.get(18) == value && board.get(20) == value))
                    return true;
                break;
            case 15:
                if ((board.get(21) == value && board.get(22) == value) || (board.get(21) == value &&
                        board.get(23) == value) || (board.get(22) == value && board.get(23) == value))
                    return true;
                break;
        }
        return false;
    }

    private static int countOpponentBlockPawns(int player, ArrayList<Integer> board) {
        int counter_f = 0;
        int counter_s = 0;

        int opposite_player = getSecondPlayerId(player);

        for (int i=0; i<board.size(); i++)
        {
                ArrayList<Integer> neigh = getNeighbours(i);
                int help_counter = 0;
                for (Integer n : neigh)
                    if (board.get(n) != NMM.I_BLANK_FIELD)
                        help_counter++;

                if (help_counter == neigh.size())
                    if (board.get(i) == opposite_player)
                        counter_f++;
                    else if (board.get(i) == player)
                        counter_s++;
        }

        return counter_f - counter_s;
    }

    public static boolean isGameOver(ArrayList<Integer> board) {
        return isGameOver(NMM.I_BLACK_PLAYER, board) || isGameOver(NMM.I_WHITE_PLAYER, board);
    }

    private static boolean isGameOver(int player, ArrayList<Integer> board) {
        Player p = NMM.getPlayer(player);

        switch (getPhaseForPlayer(player, board))
        {
            case NMM.I_MID_GAME_PHASE:
                if (countPlayerMoves(player, board) == 0)// || countPawnsOfPlayer(player, board) <= 2)
                    return true;
                break;
            case NMM.I_END_GAME_PHASE:
                if (countPawnsOfPlayer(player, board) <= 2 || p.getCounter_of_moves() > 40)
                    return true;
                break;
        }
        return false;
    }

    public static int getPhaseForPlayer(int player, ArrayList<Integer> board)
    {
        if (countPawnsOfPlayer(player, board) > 3)
            return NMM.I_MID_GAME_PHASE;
        else
            return NMM.I_END_GAME_PHASE;
    }

    private static int countPawns(int player, ArrayList<Integer> board) {
        int counter_f = 0;
        int counter_s = 0;

        for (Integer f : board)
            if (f == player)
                counter_f++;
            else if (f == getSecondPlayerId(player))
                counter_s++;

        return counter_f - counter_s;
    }

    private static int countPawnsOfPlayer(int player, ArrayList<Integer> board)
    {
        int counter = 0;

        for (Integer f : board)
            if (f == player)
                counter++;

        return counter;
    }

    private static int countMoves(int iPlayer, ArrayList<Integer> board)
    {
        int counter_f = 0;
        int counter_s = 0;

        for (int i=0; i<board.size(); i++) {
            int field = board.get(i);
            for (Integer neigh : getNeighbours(i))
                if (board.get(neigh) == NMM.I_BLANK_FIELD)
                    if (field == iPlayer)
                        counter_f++;
                    else if (field == getSecondPlayerId(iPlayer))
                        counter_s++;
        }

        return counter_f - counter_s;
    }

    private static int countPlayerMoves(int iPlayer, ArrayList<Integer> board) {
        int counter = 0;
        Player p = NMM.getPlayer(iPlayer);

        for (int i=0; i<board.size(); i++){
            if (board.get(i) == iPlayer) {
                for (Integer neigh : getNeighbours(i))
                    if (board.get(neigh) == NMM.I_BLANK_FIELD && p.getLastMove(NMM.I_FIRST_FIELD) != neigh)
                            counter++;
            }
        }

        return counter;
    }

    public static ArrayList<Integer> getNeighbours(int field)
    {
        ArrayList<Integer> neigh = new ArrayList<>();
        switch(field)
        {
            case 0:
                neigh.addAll(Arrays.asList(1,9));
                break;
            case 1:
                neigh.addAll(Arrays.asList(0,2,4));
                break;
            case 2:
                neigh.addAll(Arrays.asList(1,14));
                break;
            case 3:
                neigh.addAll(Arrays.asList(4,10));
                break;
            case 4:
                neigh.addAll(Arrays.asList(1,3,5,7));
                break;
            case 5:
                neigh.addAll(Arrays.asList(4,13));
                break;
            case 6:
                neigh.addAll(Arrays.asList(7,11));
                break;
            case 7:
                neigh.addAll(Arrays.asList(4,6,8));
                break;
            case 8:
                neigh.addAll(Arrays.asList(7,12));
                break;
            case 9:
                neigh.addAll(Arrays.asList(0,10,21));
                break;
            case 10:
                neigh.addAll(Arrays.asList(3,9,11,18));
                break;
            case 11:
                neigh.addAll(Arrays.asList(6,10,15));
                break;
            case 12:
                neigh.addAll(Arrays.asList(8,13,17));
                break;
            case 13:
                neigh.addAll(Arrays.asList(5,12,14,20));
                break;
            case 14:
                neigh.addAll(Arrays.asList(2,13,23));
                break;
            case 15:
                neigh.addAll(Arrays.asList(11,16));
                break;
            case 16:
                neigh.addAll(Arrays.asList(15,17,19));
                break;
            case 17:
                neigh.addAll(Arrays.asList(12,16));
                break;
            case 18:
                neigh.addAll(Arrays.asList(10,19));
                break;
            case 19:
                neigh.addAll(Arrays.asList(16,18,20,22));
                break;
            case 20:
                neigh.addAll(Arrays.asList(13,19));
                break;
            case 21:
                neigh.addAll(Arrays.asList(9,22));
                break;
            case 22:
                neigh.addAll(Arrays.asList(19,21,23));
                break;
            case 23:
                neigh.addAll(Arrays.asList(14,22));
                break;
        }

        return neigh;
    }

    public static boolean isCloseMill(ArrayList<Integer> board, int field)
    {
        int value = board.get(field);

        switch(field)
        {
            case 0:
                if ((board.get(1) == value && board.get(2) == value) || (board.get(9) == value &&
                        board.get(21) == value))
                    return true;
                break;
            case 1:
                if ((board.get(0) == value && board.get(2) == value) || (board.get(4) == value &&
                        board.get(7) == value))
                    return true;
                break;
            case 2:
                if ((board.get(1) == value && board.get(0) == value) || (board.get(14) == value &&
                        board.get(23) == value))
                    return true;
                break;
            case 3:
                if ((board.get(4) == value && board.get(5) == value) || (board.get(10) == value &&
                        board.get(18) == value))
                    return true;
                break;
            case 4:
                if ((board.get(1) == value && board.get(7) == value) || (board.get(3) == value &&
                        board.get(5) == value))
                    return true;
                break;
            case 5:
                if ((board.get(3) == value && board.get(4) == value) || (board.get(13) == value &&
                        board.get(20) == value))
                    return true;
                break;
            case 6:
                if ((board.get(7) == value && board.get(8) == value) || (board.get(11) == value &&
                        board.get(15) == value))
                    return true;
                break;
            case 7:
                if ((board.get(6) == value && board.get(8) == value) || (board.get(1) == value &&
                        board.get(4) == value))
                    return true;
                break;
            case 8:
                if ((board.get(6) == value && board.get(7) == value) || (board.get(12) == value &&
                        board.get(17) == value))
                    return true;
                break;
            case 9:
                if ((board.get(0) == value && board.get(21) == value) || (board.get(10) == value &&
                        board.get(11) == value))
                    return true;
                break;
            case 10:
                if ((board.get(9) == value && board.get(11) == value) || (board.get(3) == value &&
                        board.get(18) == value))
                    return true;
                break;
            case 11:
                if ((board.get(9) == value && board.get(10) == value) || (board.get(6) == value &&
                        board.get(15) == value))
                    return true;
                break;
            case 12:
                if ((board.get(8) == value && board.get(17) == value) || (board.get(13) == value &&
                        board.get(14) == value))
                    return true;
                break;
            case 13:
                if ((board.get(12) == value && board.get(14) == value) || (board.get(5) == value &&
                        board.get(20) == value))
                    return true;
                break;
            case 14:
                if ((board.get(12) == value && board.get(13) == value) || (board.get(2) == value &&
                        board.get(23) == value))
                    return true;
                break;
            case 15:
                if ((board.get(6) == value && board.get(11) == value) || (board.get(16) == value &&
                        board.get(17) == value))
                    return true;
                break;
            case 16:
                if ((board.get(19) == value && board.get(22) == value) || (board.get(15) == value &&
                        board.get(17) == value))
                    return true;
                break;
            case 17:
                if ((board.get(8) == value && board.get(12) == value) || (board.get(16) == value &&
                        board.get(15) == value))
                    return true;
                break;
            case 18:
                if ((board.get(19) == value && board.get(20) == value) || (board.get(3) == value &&
                        board.get(10) == value))
                    return true;
                break;
            case 19:
                if ((board.get(16) == value && board.get(22) == value) || (board.get(20) == value &&
                        board.get(18) == value))
                    return true;
                break;
            case 20:
                if ((board.get(18) == value && board.get(19) == value) || (board.get(5) == value &&
                        board.get(13) == value))
                    return true;
                break;
            case 21:
                if ((board.get(9) == value && board.get(0) == value) || (board.get(22) == value &&
                        board.get(23) == value))
                    return true;
                break;
            case 22:
                if ((board.get(21) == value && board.get(23) == value) || (board.get(16) == value &&
                        board.get(19) == value))
                    return true;
                break;
            case 23:
                if ((board.get(21) == value && board.get(22) == value) || (board.get(2) == value &&
                        board.get(14) == value))
                    return true;
                break;
        }
        return false;
    }
}
