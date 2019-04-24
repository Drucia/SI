package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import javafx.scene.control.Label;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    private static Player actualPlayer;
    private static int amount_of_moves = 1;
    private static int actual_nb_of_move = 1;
    private static final int I_CHOOSE_PAWN_TO_PLACE_ON_BOARD = 0;
    private static final int I_CHOOSE_PAWN_TO_SHIFT_FROM = 1;
    private static final int I_CHOOSE_PAWN_TO_SHIFT_FOR = 2;
    private static final int I_CHOOSE_PAWN_TO_DELETE = 3;

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

    @FXML
    public void initialize()
    {
        list_of_fields = new ArrayList<>(Arrays.asList(
                p0, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10,
                p11, p12, p13, p14, p15, p16, p17, p18, p19,
                p20, p21, p22, p23
        ));

        list_of_blacks = new ArrayList<>(Arrays.asList(
                b0, b1, b2, b3, b4, b5, b6, b7, b8
        ));

        list_of_whites = new ArrayList<>(Arrays.asList(
                w0, w1, w2, w3, w4, w5, w6, w7, w8
        ));

        Integer[] data = new Integer[24];
        Arrays.fill(data, NMM.I_BLANK_FIELD);
        NMM.updateBoard(new ArrayList<>(Arrays.asList(data)));

        showPopup("Nowa Gra", "NewGame.fxml");

        actualPlayer = NMM.getPlayer(NMM.I_BLACK_PLAYER);
        amount_of_moves = actual_nb_of_move = 1;
        nextPlayerPressed();
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
            NewGameController.stage = popupStage;
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
        if (actual_nb_of_move < amount_of_moves) {

            switch (actualPlayer.getPlayerPhase()) {
                case NMM.I_OPEN_GAME_PHASE:
                    String id_of_clicked_place = mouseEvent.getPickResult().getIntersectedNode().getId();
                    setPawnOnBoard(id_of_clicked_place);
                    actual_nb_of_move++;
                    break;
                case NMM.I_MID_GAME_PHASE:
                    System.out.println("Jestesmy w czesci glownej!!!");
                    NMM.checkIfCanDeleteOpponent();
                    break;
                case NMM.I_END_GAME_PHASE:
                    break;
            }
        } else {
            comm.setText("Wykonano ruch. Wcisnij przycisk, żeby zakończyć rundę.");
            comm.setVisible(true);
        }
    }

    private boolean makeShifts(ArrayList<Pair<Pair<Integer, Integer>, Integer>> shifts)
    {
        if (shifts == null)
            return false;

        for (int i=0; i<shifts.size(); i++)
        {
            Pair<Pair<Integer, Integer>, Integer> tmp = shifts.get(i);
            int idx_from = tmp.getKey().getKey();
            int idx_for = tmp.getKey().getValue();
            int val = tmp.getValue();

            ImageView im;

            if (idx_from != NMM.I_BLANK_FIELD) {
                im = list_of_fields.get(idx_from);
                im.setImage(null);

                if (val != NMM.I_BLANK_FIELD) {
                    String play = NMM.getNameOfPlayer(val);
                    im = list_of_fields.get(idx_for);
                    im.setImage(new Image("/images/" + play + ".png"));
                }
            } else
                setPawnOnBoard("p"+idx_for);
        }

        return true;
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
        actualPlayer.updateAmountOfPawns(1);
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

        for (int i=0; i<NMM.getBoardSize(); i++)
            NMM.updateFieldOfBoard(i, NMM.I_BLANK_FIELD);

        showPopup("Nowa Gra", "NewGame.fxml");

        actualPlayer = NMM.getPlayer(NMM.I_BLACK_PLAYER);
        amount_of_moves = actual_nb_of_move = 1;
        nextPlayerPressed();
    }

    public void optionClicked() {
    }

    public void closeClicked() {
        primaryStage.close();
    }

    public void nextPlayerPressed() {
        if (amount_of_moves == actual_nb_of_move) {
            comm.setVisible(false);
            switchPlayers();
            amount_of_moves = 1;
            actual_nb_of_move = 0;
            NMM.updateActualPhase(actualPlayer);

            if (actualPlayer.getPlayerType() == NMM.I_AI_PLAYER)
                makeShifts(NMM.playerMove(actualPlayer));
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
    }
}
