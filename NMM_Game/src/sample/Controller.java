package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    public static int game_phase; // first is opening phase
    public static int player; // id of actual player 0 - white, 1 - black
    public static int player_type; // type of player 0 - AI, 1 - manual

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

    private ArrayList<ImageView> list_of_fields;
    private ArrayList<ImageView> list_of_blacks;
    private ArrayList<ImageView> list_of_whites;
    //private ArrayList<Integer> list_of_black_behind_board;
    //private ArrayList<Integer> list_of_white_behind_board;
    //public static ArrayList<Integer> board;
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

        //list_of_black_behind_board = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        //list_of_white_behind_board = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));

        Integer[] data = new Integer[24];
        Arrays.fill(data, NMM.I_BLANK_FIELD);
        NMM.updateBoard(new ArrayList<>(Arrays.asList(data)));

        game_phase = NMM.I_OPEN_GAME_PHASE;
        player = NMM.I_WHITE_PLAYER;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("NewGame.fxml"));
        // initializing the controller
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            // this is the popup stage
            Stage popupStage = new Stage();
            popupStage.setTitle("Nowa Gra");
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
        player_type = NMM.getPlayerType(player);

        if (game_phase == NMM.I_OPEN_GAME_PHASE)
        {
            MouseButton mb = mouseEvent.getButton();
            String id_of_clicked_place = mouseEvent.getPickResult().getIntersectedNode().getId();

            if (player_type == NMM.I_MAN_PLAYER)
                setPawnOnBoard(id_of_clicked_place); // communicate

            
            //if (list_of_black_behind_board.isEmpty() && list_of_white_behind_board.isEmpty())
            //    game_phase = NMM.I_MID_GAME_PHASE;

        } else if (game_phase == NMM.I_MID_GAME_PHASE)
        {
            System.out.println("Jestesmy w czesci glownej");
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

        return true; // to change
    }

    private boolean setPawnOnBoard(String id_of_clicked_place) {
        ArrayList<Integer> behind_board;
        ArrayList<ImageView> pawns;
        String play = NMM.getNameOfPlayer(player);

        if (player == NMM.I_WHITE_PLAYER) {
            pawns = list_of_whites;
            behind_board = list_of_white_behind_board;
            //play = "white";
        }
        else {
            pawns = list_of_blacks;
            behind_board = list_of_black_behind_board;
            //play = "black";
        }

        if (behind_board.isEmpty())
            return false;

        int id = Integer.parseInt(id_of_clicked_place.substring(1));
        ImageView field = list_of_fields.get(id);

        if (field.getImage() != null)
            return false;

        ImageView pawn = pawns.get(behind_board.get(0));
        pawn.setVisible(false);
        field.setImage(new Image("/images/" + play + ".png"));
        behind_board.remove(0);
        //board.set(id, player);
        NMM.updateFieldOfBoard(id, player);
        return true;
    }

    public void newGameClicked() {
        for (ImageView i : list_of_fields)
            i.setImage(null);

        for (ImageView i : list_of_whites)
            i.setVisible(true);

        for (ImageView i : list_of_blacks)
            i.setVisible(true);

        for (int i=0; i<NMM.getBoardSize(); i++)
            NMM.updateFieldOfBoard(i, NMM.I_BLANK_FIELD);
            //board.set(i, -1);

        list_of_black_behind_board = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        list_of_white_behind_board = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));

        player = NMM.I_WHITE_PLAYER;

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("NewGame.fxml"));
        // initializing the controller
        Parent layout;
        try {
            layout = loader.load();
            Scene scene = new Scene(layout);
            // this is the popup stage
            Stage popupStage = new Stage();
            popupStage.setTitle("Nowa Gra");
            // Giving the popup controller access to the popup stage (to allow the controller to close the stage) 
            NewGameController.stage = popupStage;
            popupStage.initOwner(primaryStage);
            popupStage.initModality(Modality.WINDOW_MODAL);
            popupStage.setScene(scene);
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return popupController.getResult();
    }

    public void optionClicked() {
    }

    public void closeClicked() {
        primaryStage.close();
    }

    public void nextPlayerPressed() {
        //else {
        //    ArrayList<Pair<Pair<Integer, Integer>, Integer>> shifts = NMM.makeMove(player); // <(idx_from, idx_for), val>
        //    isSet = makeShifts(shifts);
        //}
        player = (player + 1) % 2;

        if ()
        if (list_of_black_behind_board.isEmpty() && list_of_white_behind_board.isEmpty())
            game_phase = NMM.I_MID_GAME_PHASE;




        if (list_of_black_behind_board.isEmpty() && list_of_white_behind_board.isEmpty())
            game_phase = NMM.I_MID_GAME_PHASE;
    }
}
