package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import javafx.scene.control.Label;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    private static Player actualPlayer;
    private static int actual_move;
    private static final int I_CHOOSE_PAWN_TO_PLACE_ON_BOARD = 0;
    private static final int I_CHOOSE_PAWN_TO_SHIFT_FROM = 1;
    private static final int I_CHOOSE_PAWN_TO_SHIFT_TO = 2;
    private static final int I_CHOOSE_PAWN_TO_DELETE = 3;
    private static final int I_NO_MOVE = 4;
    private static int id_shift_from;
    private static int to_delete;

    @FXML
    private Button next;

    @FXML
    private ImageView b2;

    @FXML
    private ImageView b3;

    @FXML
    private ImageView b4;

    @FXML
    private ImageView b5;

    @FXML
    private ImageView b6;

    @FXML
    private ImageView b7;

    @FXML
    private ImageView b8;

    @FXML
    private ImageView b0;

    @FXML
    private ImageView b1;

    @FXML
    private ImageView w2;

    @FXML
    private ImageView w3;

    @FXML
    private ImageView w4;

    @FXML
    private ImageView w5;

    @FXML
    private ImageView w6;

    @FXML
    private ImageView w7;

    @FXML
    private ImageView w8;

    @FXML
    private ImageView w0;

    @FXML
    private ImageView w1;

    @FXML
    private ImageView p2;

    @FXML
    private ImageView p14;

    @FXML
    private ImageView p23;

    @FXML
    private ImageView p5;

    @FXML
    private ImageView p13;

    @FXML
    private ImageView p20;

    @FXML
    private ImageView p12;

    @FXML
    private ImageView p8;

    @FXML
    private ImageView p17;

    @FXML
    private ImageView p11;

    @FXML
    private ImageView p6;

    @FXML
    private ImageView p15;

    @FXML
    private ImageView p4;

    @FXML
    private ImageView p1;

    @FXML
    private ImageView p7;

    @FXML
    private ImageView p19;

    @FXML
    private ImageView p16;

    @FXML
    private ImageView p22;

    @FXML
    private ImageView p3;

    @FXML
    private ImageView p10;

    @FXML
    private ImageView p18;

    @FXML
    private ImageView p0;

    @FXML
    private ImageView p9;

    @FXML
    private ImageView p21;

    @FXML
    private Label player;

    @FXML
    private Label comm;

    private ArrayList<ImageView> list_of_fields;
    private ArrayList<ImageView> list_of_blacks;
    private ArrayList<ImageView> list_of_whites;
    public static Stage primaryStage;
    public static ArrayList<String> list_of_fields_in_words;

    @FXML
    public void initialize()
    {
        list_of_fields = new ArrayList<>(Arrays.asList(
                p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10,
                p11, p12, p13, p14, p15, p16, p17, p18, p19,
                p20, p21, p22, p23
        ));

        list_of_fields_in_words = new ArrayList<>(Arrays.asList(
                "a1", "a4", "a7", "b2", "b4", "b6", "c3", "c4", "c5",
                "d1", "d2", "d3", "d5", "d6", "d7", "e3", "e4", "e5",
                "f2", "f4", "f6", "g1", "g4", "g7"
        ));

        list_of_blacks = new ArrayList<>(Arrays.asList(
                b0, b1, b2, b3, b4, b5, b6, b7, b8
        ));

        list_of_whites = new ArrayList<>(Arrays.asList(
                w0, w1, w2, w3, w4, w5, w6, w7, w8
        ));

        NMM.newGame();

        showPopup("Nowa Gra", "NewGame.fxml");

        if (NewGameController.isClose)
            primaryStage.close();
        else {
            actualPlayer = NMM.getPlayer(NMM.I_BLACK_PLAYER);
            actual_move = I_NO_MOVE;
            nextPlayerPressed();
        }
    }

    private void showPopup(String title, String fxml)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxml));
        // initializing the controller
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            // this is the popup stage
            Stage popupStage = new Stage();
            popupStage.setTitle(title);
            // Giving the popup controller access to the popup stage (to allow the controller to close the stage)

            if (title.equals("Nowa Gra"))
                NewGameController.stage = popupStage;
            else
                GameOverController.stage = popupStage;
            popupStage.initOwner(primaryStage);
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.setScene(scene);
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPlaceClicked(MouseEvent mouseEvent)
    {
        if (actual_move != I_NO_MOVE)
        {
            String id_of_clicked_place = mouseEvent.getPickResult().getIntersectedNode().getId();

            switch (actualPlayer.getPlayerPhase()) {
                case NMM.I_OPEN_GAME_PHASE:
                    if (actual_move == I_CHOOSE_PAWN_TO_PLACE_ON_BOARD)
                        actual_move = setPawnOnBoard(id_of_clicked_place) ? (to_delete = NMM.checkHowMuchOpponentCanDelete(actualPlayer)) != 0 ? I_CHOOSE_PAWN_TO_DELETE : I_NO_MOVE : actual_move;
                    else
                        actual_move = deletePawn(id_of_clicked_place) && to_delete == 0 ? I_NO_MOVE : actual_move;
                    break;
                case NMM.I_MID_GAME_PHASE:
                case NMM.I_END_GAME_PHASE:
                    if (actual_move == I_CHOOSE_PAWN_TO_SHIFT_FROM)
                        actual_move = shiftPawnFrom(id_of_clicked_place) ? I_CHOOSE_PAWN_TO_SHIFT_TO : actual_move;
                    else if (actual_move == I_CHOOSE_PAWN_TO_SHIFT_TO)
                        actual_move = shiftPawnTo(id_of_clicked_place) ? (to_delete = NMM.checkHowMuchOpponentCanDelete(actualPlayer)) != 0 ? I_CHOOSE_PAWN_TO_DELETE : I_NO_MOVE : actual_move;
                    else
                        actual_move = deletePawn(id_of_clicked_place) && to_delete == 0 ? I_NO_MOVE : actual_move;
                    break;
            }

        } else {
            comm.setText("Wykonano ruch. Wcisnij przycisk, żeby zakończyć rundę.");
            comm.setVisible(true);
        }
    }

    private boolean deletePawn(String id_of_clicked_place) {
        int id = Integer.parseInt(id_of_clicked_place.substring(1));

        if (NMM.getActualBoard().get(id) != Algorithm.getSecondPlayerId(actualPlayer.getPlayerId()))
            return false;

        ArrayList<Pair<Pair<Integer, Integer>, Integer>> shifts = new ArrayList<>();
        Pair<Pair<Integer, Integer>, Integer> shift = new Pair<>(new Pair<>(id, NMM.I_BLANK_FIELD), NMM.I_BLANK_FIELD);
        shifts.add(shift);

        makeShifts(shifts);
        NMM.updateFieldOfBoard(id, NMM.I_BLANK_FIELD);
        to_delete--;

        return true;
    }

    private boolean shiftPawnTo(String id_of_clicked_place) {
        int id = Integer.parseInt(id_of_clicked_place.substring(1));

        if (NMM.getActualBoard().get(id) == actualPlayer.getPlayerId())
        {
            id_shift_from = id;
            return false;
        }
        else if ((!Algorithm.getNeighbours(id_shift_from).contains(id) && actualPlayer.getPlayerPhase() == NMM.I_MID_GAME_PHASE) || (actualPlayer.getLastMove(NMM.I_FIELD_FROM) == id && actualPlayer.getLastMove(NMM.I_FIELD_TO) == id_shift_from))
            return false;

        ArrayList<Pair<Pair<Integer, Integer>, Integer>> shifts = new ArrayList<>();
        Pair<Pair<Integer, Integer>, Integer> shift = new Pair<>(new Pair<>(id_shift_from, id), actualPlayer.getPlayerId());
        shifts.add(shift);

        makeShifts(shifts);
        NMM.updateFieldOfBoard(id_shift_from, NMM.I_BLANK_FIELD);
        NMM.updateFieldOfBoard(id, actualPlayer.getPlayerId());
        id_shift_from = I_NO_MOVE;

        return true;
    }

    private boolean shiftPawnFrom(String id_of_clicked_place) {
        int id = Integer.parseInt(id_of_clicked_place.substring(1));

        if (NMM.getActualBoard().get(id) != actualPlayer.getPlayerId())
            return false;

        id_shift_from = id;

        return true;
    }

    private boolean makeShifts(ArrayList<Pair<Pair<Integer, Integer>, Integer>> shifts) // ((from, to), who) -> shift
    {
        if (shifts == null)
            return false;

        for (int i=0; i<shifts.size(); i++)
        {
            actualPlayer.increment_moves();
            NMM.incrementGameCounterAfterBeat();
            Pair<Pair<Integer, Integer>, Integer> tmp = shifts.get(i);
            int idx_from = tmp.getKey().getKey();
            int idx_to = tmp.getKey().getValue();
            int val = tmp.getValue();

            ImageView im;

            if (idx_from != NMM.I_BLANK_FIELD) {
                im = list_of_fields.get(idx_from);
                im.setImage(null);

                if (idx_to != NMM.I_BLANK_FIELD) {
                    String play = NMM.getNameOfPlayer(val);
                    im = list_of_fields.get(idx_to);
                    im.setImage(new Image("/images/" + play + ".png"));
                    actualPlayer.addHistory(list_of_fields_in_words.get(idx_from) + " -> " + list_of_fields_in_words.get(idx_to));
                    NMM.addHistory( "Gracz " + actualPlayer.getName() + ": " + list_of_fields_in_words.get(idx_from) + " -> " + list_of_fields_in_words.get(idx_to));
                }
                else { // beat pawn
                    actualPlayer.addHistory(list_of_fields_in_words.get(idx_from) + " -> " + NMM.I_BLANK_FIELD);
                    NMM.addHistory("Gracz " + actualPlayer.getName() + ": " + list_of_fields_in_words.get(idx_from) + " -> " + NMM.I_BLANK_FIELD);
                    actualPlayer.setCounter_of_moves(0);
                    NMM.setGame_counter_of_moves_after_beat(0);
                }
            } else
                setPawnOnBoard("p"+idx_to);
        }

        return true;
    }

    private void updateGUIBoard(ArrayList<Integer> board, int playerId)
    {
        ArrayList<Integer> old_board = NMM.getActualBoard();
        ArrayList<Pair<Pair<Integer, Integer>, Integer>> shifts = new ArrayList<>();
        ArrayList<Integer> to_del = new ArrayList<>();

        int from, to, new_val;
        from = to = new_val = NMM.I_BLANK_FIELD;

        for (int i=0; i<board.size(); i++)
        {
            int old_val = old_board.get(i);
            int curr_new_val = board.get(i);

            if (old_val != curr_new_val) // if is different
            {
                if (old_val == NMM.I_BLANK_FIELD) // it was set pawn behind board
                {
                    to = i;
                    new_val = curr_new_val;
                }
                else if (old_val == playerId)
                    from = i;
                else {
                    to_del.add(i);
                }
            }
        }

        Pair<Pair<Integer, Integer>, Integer> shift = new Pair<>(new Pair<>(from, to), new_val);
        shifts.add(shift);

        for (Integer delete : to_del)
        {
            shift = new Pair<>(new Pair<>(delete, NMM.I_BLANK_FIELD), NMM.I_BLANK_FIELD);
            shifts.add(shift);
        }

        makeShifts(shifts);
        NMM.updateBoard(board);

        if (NMM.isGameOver() && to_delete == 0 && actualPlayer.getPlayerPhase() != NMM.I_OPEN_GAME_PHASE) {
            GameOverController.n_game = false;
            showPopup("Koniec Gry", "GameOver.fxml");

            if (GameOverController.n_game)
                newGameClicked();
        }
    }

    private boolean setPawnOnBoard(String id_of_clicked_place) {
        ArrayList<ImageView> pawns;
        String play = actualPlayer.getName();

        if (actualPlayer.getPlayerId() == NMM.I_WHITE_PLAYER)
            pawns = list_of_whites;
        else
            pawns = list_of_blacks;

        int id = Integer.parseInt(id_of_clicked_place.substring(1));
        ImageView field = list_of_fields.get(id);

        if (field.getImage() != null)
            return false;

        ImageView pawn = pawns.get(actualPlayer.getFirstPawnBehindBoard());
        pawn.setVisible(false);
        field.setImage(new Image("/images/" + play + ".png"));
        actualPlayer.setPawnOnBoard();
        NMM.updateFieldOfBoard(id, actualPlayer.getPlayerId());
        actualPlayer.addHistory("-1 -> " + list_of_fields_in_words.get(id));
        NMM.addHistory("Gracz " + actualPlayer.getName() + ": " + "-1 -> " + list_of_fields_in_words.get(id));

        return true;
    }

    public void newGameClicked() {
        comm.setVisible(false);
        for (ImageView i : list_of_fields)
            i.setImage(null);

        for (ImageView i : list_of_whites)
            i.setVisible(true);

        for (ImageView i : list_of_blacks)
            i.setVisible(true);

        NMM.newGame();

        showPopup("Nowa Gra", "NewGame.fxml");

        actualPlayer = NMM.getPlayer(NMM.I_BLACK_PLAYER);
        actual_move = I_NO_MOVE;
        nextPlayerPressed();
    }

    public void optionClicked() {
    }

    public void closeClicked() {
        primaryStage.close();
    }

    public void nextPlayerPressed() {
        if (actual_move == I_NO_MOVE) {
            comm.setVisible(false);
            switchPlayers();

            switch (actualPlayer.getPlayerType())
            {
                case NMM.I_MAN_PLAYER:
                    if (actualPlayer.getPlayerPhase() == NMM.I_OPEN_GAME_PHASE)
                        actual_move = I_CHOOSE_PAWN_TO_PLACE_ON_BOARD;
                    else
                        actual_move = I_CHOOSE_PAWN_TO_SHIFT_FROM;
                    break;
                case NMM.I_AI_PLAYER:
                    updateGUIBoard(NMM.makeMove(actualPlayer), actualPlayer.getPlayerId());
                    actual_move = I_NO_MOVE;
                    break;
            }

            if (NMM.isGameOver() && actualPlayer.getPlayerPhase() != NMM.I_OPEN_GAME_PHASE) {
                GameOverController.n_game = false;
                showPopup("Koniec Gry", "GameOver.fxml");

                if (GameOverController.n_game)
                    newGameClicked();
            }
        }
        else {
            comm.setText("Musisz wykonac ruch.");
            comm.setVisible(true);
        }
    }

    private void switchPlayers()
    {
        actualPlayer = NMM.getPlayer((actualPlayer.getPlayerId() + 1) % 2);
        player.setText("GRACZ: " + (actualPlayer.getPlayerId() == NMM.I_BLACK_PLAYER ? "CZARNY" : "BIAŁY"));
        NMM.incrementGameCounter();
        NMM.updateActualPhase(actualPlayer);
    }
}
