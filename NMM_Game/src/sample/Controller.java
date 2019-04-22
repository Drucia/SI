package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    public static int game_phase; // opening phase
    public static int player; // id of actual player 0 - white, 1 - black

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
    private ArrayList<Integer> list_of_black_behind_board;
    private ArrayList<Integer> list_of_white_behind_board;
    public static ArrayList<Integer> board;

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

        list_of_black_behind_board = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
        list_of_white_behind_board = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));

        Integer[] data = new Integer[24];
        Arrays.fill(data,-1);
        board = new ArrayList<>(Arrays.asList(data));

        game_phase = NMM.I_OPEN_GAME_PHASE;
        player = NMM.I_WHITE_PLAYER;
    }

        public void onPlaceClicked(MouseEvent mouseEvent)
    {
        if (game_phase == NMM.I_OPEN_GAME_PHASE)
        {
            MouseButton mb = mouseEvent.getButton();
            String id_of_clicked_place = mouseEvent.getPickResult().getIntersectedNode().getId();

            if (mb.name().equals("PRIMARY"))
            {
                boolean isSet = setPawnOnBoard(id_of_clicked_place); // communicate

                if (isSet)
                    player = (player + 1) % 2;

                if (list_of_black_behind_board.isEmpty() && list_of_white_behind_board.isEmpty())
                    game_phase = NMM.I_MID_GAME_PHASE;
            }
        } else if (game_phase == NMM.I_MID_GAME_PHASE)
        {
            System.out.println("Jestesmy w czesci glownej");
        }
    }

    private boolean setPawnOnBoard(String id_of_clicked_place) {
        ArrayList<Integer> behind_board;
        ArrayList<ImageView> pawns;
        String play;

        if (player == NMM.I_WHITE_PLAYER) {
            pawns = list_of_whites;
            behind_board = list_of_white_behind_board;
            play = "white";
        }
        else {
            pawns = list_of_blacks;
            behind_board = list_of_black_behind_board;
            play = "black";
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
        board.set(id, player);
        return true;
    }
}
