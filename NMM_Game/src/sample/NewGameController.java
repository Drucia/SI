package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.Arrays;

public class NewGameController {
    private final static int I_CLOSE_MORRIS_O = 18;
    private final static int I_MORRIS_NUMBER_O = 26;
    private final static int I_BLOCK_OPP_O = 1;
    private final static int I_NUMBER_OF_PAWNS_O = 6;
    private final static int I_TWO_CONF_O = 12;
    private final static int I_THREE_CONF_O = 7;
    private final static int I_CLOSE_MORRIS_M = 14;
    private final static int I_MORRIS_NUMBER_M = 43;
    private final static int I_BLOCK_OPP_M = 10;
    private final static int I_NUMBER_OF_PAWNS_M = 8;
    private final static int I_OPEN_MORRIS_M = 7;
    private final static int I_DOUBLE_MORRIS_M = 42;
    private final static int I_WIN_CONF_M = 1086;
    private final static int I_CLOSE_MORRIS_E = 10;
    private final static int I_TWO_CONF_E = 1;
    private final static int I_THREE_CONF_E = 16;
    private final static int I_WIN_CONF_E = 1190;
    public static boolean isClose = true;

    @FXML
    private ChoiceBox<String> white_player;

    @FXML
    private CheckBox w_close_morris_o;

    @FXML
    private CheckBox w_number_of_morris_o;

    @FXML
    private CheckBox w_number_of_blocked_o;

    @FXML
    private CheckBox w_number_of_pawns_o;

    @FXML
    private ChoiceBox<String> w_alg;

    @FXML
    private ChoiceBox<String> b_alg;

    @FXML
    private CheckBox w_number_of_two_o;

    @FXML
    private CheckBox w_number_of_three_o;

    @FXML
    private CheckBox w_close_morris_m;

    @FXML
    private CheckBox w_number_of_morris_m;

    @FXML
    private CheckBox w_number_of_blocked_m;

    @FXML
    private CheckBox w_number_of_pawns_m;

    @FXML
    private CheckBox w_open_morris_m;

    @FXML
    private CheckBox w_double_morris_m;

    @FXML
    private CheckBox w_win_m;

    @FXML
    private CheckBox w_close_morris_e;

    @FXML
    private CheckBox w_number_of_two_e;

    @FXML
    private CheckBox w_number_of_three_e;

    @FXML
    private CheckBox w_win_e;

    @FXML
    private ChoiceBox<String> black_player;

    @FXML
    private CheckBox b_close_morris_o;

    @FXML
    private CheckBox b_number_of_morris_o;

    @FXML
    private CheckBox b_number_of_blocked_o;

    @FXML
    private CheckBox b_number_of_pawns_o;

    @FXML
    private CheckBox b_number_of_two_o;

    @FXML
    private CheckBox b_number_of_three_o;

    @FXML
    private CheckBox b_close_morris_m;

    @FXML
    private CheckBox b_number_of_morris_m;

    @FXML
    private CheckBox b_number_of_blocked_m;

    @FXML
    private CheckBox b_number_of_pawns_m;

    @FXML
    private CheckBox b_open_morris_m;

    @FXML
    private CheckBox b_double_morris_m;

    @FXML
    private CheckBox b_win_m;

    @FXML
    private CheckBox b_close_morris_e;

    @FXML
    private CheckBox b_number_of_two_e;

    @FXML
    private CheckBox b_number_of_three_e;

    @FXML
    private CheckBox b_win_e;

    @FXML
    private Label w_open_label;

    @FXML
    private Label w_mid_label;

    @FXML
    private Label w_end_label;

    @FXML
    private Label b_open_label;

    @FXML
    private Label b_mid_label;

    @FXML
    private Label b_end_label;

    @FXML
    private Label w_alg_label;

    @FXML
    private Label b_alg_label;


    public static Stage stage;

    @FXML
    public void initialize()
    {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Machine", "Manual"));
        ArrayList<String> list_a = new ArrayList<>(Arrays.asList("Alpha Beta", "Mini Maxi"));
        ObservableList<String> olist = FXCollections.observableList(list);
        white_player.setItems(olist);
        white_player.getSelectionModel().selectFirst();
        white_player.setOnAction(event -> {
            if (white_player.getValue().equals("Manual"))
                setDisableAllWhiteFieldsTo(true);
            else
                setDisableAllWhiteFieldsTo(false);
        });
        black_player.setItems(olist);
        black_player.getSelectionModel().selectFirst();
        black_player.setOnAction(event -> {
            if (white_player.getValue().equals("Manual"))
                setDisableAllBlackFieldsTo(true);
            else
                setDisableAllBlackFieldsTo(false);
        });
        olist = FXCollections.observableList(list_a);
        w_alg.setItems(olist);
        w_alg.getSelectionModel().selectFirst();
        b_alg.setItems(olist);
        b_alg.getSelectionModel().selectFirst();
    }

    private void setDisableAllBlackFieldsTo(boolean b) {
        b_close_morris_o.setDisable(b);
        b_close_morris_e.setDisable(b);
        b_close_morris_m.setDisable(b);
        b_double_morris_m.setDisable(b);
        b_end_label.setDisable(b);
        b_mid_label.setDisable(b);
        b_number_of_blocked_m.setDisable(b);
        b_number_of_blocked_o.setDisable(b);
        b_number_of_morris_m.setDisable(b);
        b_number_of_morris_o.setDisable(b);
        b_number_of_pawns_m.setDisable(b);
        b_number_of_pawns_o.setDisable(b);
        b_number_of_three_e.setDisable(b);
        b_number_of_three_o.setDisable(b);
        b_number_of_two_e.setDisable(b);
        b_number_of_two_o.setDisable(b);
        b_open_label.setDisable(b);
        b_open_morris_m.setDisable(b);
        b_win_e.setDisable(b);
        b_win_m.setDisable(b);
        b_alg_label.setDisable(b);
        b_alg.setDisable(b);
    }

    private void setDisableAllWhiteFieldsTo(boolean b) {
        w_close_morris_o.setDisable(b);
        w_close_morris_e.setDisable(b);
        w_close_morris_m.setDisable(b);
        w_double_morris_m.setDisable(b);
        w_end_label.setDisable(b);
        w_mid_label.setDisable(b);
        w_number_of_blocked_m.setDisable(b);
        w_number_of_blocked_o.setDisable(b);
        w_number_of_morris_m.setDisable(b);
        w_number_of_morris_o.setDisable(b);
        w_number_of_pawns_m.setDisable(b);
        w_number_of_pawns_o.setDisable(b);
        w_number_of_three_e.setDisable(b);
        w_number_of_three_o.setDisable(b);
        w_number_of_two_e.setDisable(b);
        w_number_of_two_o.setDisable(b);
        w_open_label.setDisable(b);
        w_open_morris_m.setDisable(b);
        w_win_e.setDisable(b);
        w_win_m.setDisable(b);
        w_alg_label.setDisable(b);
        w_alg.setDisable(b);
    }

    public void startClicked(ActionEvent actionEvent) {
        ArrayList<Integer> w_heu = setWhiteHeuristic();
        ArrayList<Integer> b_heu = setBlackHeuristic();

        Player p1 = new Player(NMM.I_WHITE_PLAYER, "white", white_player.getValue().equals("Manual") ? NMM.I_MAN_PLAYER : NMM.I_AI_PLAYER, NMM.I_OPEN_GAME_PHASE,
                w_alg.getValue().equals("Mini Maxi") ? NMM.I_MINI_MAXI_ALG : NMM.I_ALPHA_BETA_ALG, w_heu);
        Player p2 = new Player(NMM.I_BLACK_PLAYER, "black", black_player.getValue().equals("Manual") ? NMM.I_MAN_PLAYER : NMM.I_AI_PLAYER, NMM.I_OPEN_GAME_PHASE,
                b_alg.getValue().equals("Mini Maxi") ? NMM.I_MINI_MAXI_ALG : NMM.I_ALPHA_BETA_ALG, b_heu);
        NMM.setPlayers(new ArrayList<>(Arrays.asList(p1, p2)));
        isClose = false;
        stage.close();
    }

    private ArrayList<Integer> setBlackHeuristic() {
        ArrayList<Integer> heu = new ArrayList<>();

        int val = 0;

        if (w_close_morris_o.isSelected())
            val = 1;

        heu.add(val*I_CLOSE_MORRIS_O);
        val = 0;

        if (w_number_of_morris_o.isSelected())
            val = 1;

        heu.add(val*I_MORRIS_NUMBER_O);
        val = 0;

        if (w_number_of_blocked_o.isSelected())
            val = 1;

        heu.add(val*I_BLOCK_OPP_O);
        val = 0;

        if (w_number_of_pawns_o.isSelected())
            val = 1;

        heu.add(val*I_NUMBER_OF_PAWNS_O);
        val = 0;

        if (w_number_of_two_o.isSelected())
            val = 1;

        heu.add(val*I_TWO_CONF_O);
        val = 0;

        if (w_number_of_three_o.isSelected())
            val = 1;

        heu.add(val*I_THREE_CONF_O);
        val = 0;

        if (w_close_morris_m.isSelected())
            val = 1;

        heu.add(val*I_CLOSE_MORRIS_M);
        val = 0;

        if (w_number_of_morris_m.isSelected())
            val = 1;

        heu.add(val*I_MORRIS_NUMBER_M);
        val = 0;

        if (w_number_of_blocked_m.isSelected())
            val = 1;

        heu.add(val*I_BLOCK_OPP_M);
        val = 0;

        if (w_number_of_pawns_m.isSelected())
            val = 1;

        heu.add(val*I_NUMBER_OF_PAWNS_M);
        val = 0;

        if (w_open_morris_m.isSelected())
            val = 1;

        heu.add(val*I_OPEN_MORRIS_M);
        val = 0;

        if (w_double_morris_m.isSelected())
            val = 1;

        heu.add(val*I_DOUBLE_MORRIS_M);
        val = 0;

        if (w_win_m.isSelected())
            val = 1;

        heu.add(val*I_WIN_CONF_M);
        val = 0;

        if (w_close_morris_e.isSelected())
            val = 1;

        heu.add(val*I_CLOSE_MORRIS_E);
        val = 0;

        if (w_number_of_two_e.isSelected())
            val = 1;

        heu.add(val*I_TWO_CONF_E);
        val = 0;

        if (w_number_of_three_e.isSelected())
            val = 1;

        heu.add(val*I_THREE_CONF_E);
        val = 0;

        if (w_win_e.isSelected())
            val = 1;

        heu.add(val*I_WIN_CONF_E);

        return heu;
    }

    private ArrayList<Integer> setWhiteHeuristic() {
        ArrayList<Integer> heu = new ArrayList<>();

        int val = 0;

        if (b_close_morris_o.isSelected())
            val = 1;

        heu.add(val*I_CLOSE_MORRIS_O);
        val = 0;

        if (b_number_of_morris_o.isSelected())
            val = 1;

        heu.add(val*I_MORRIS_NUMBER_O);
        val = 0;

        if (b_number_of_blocked_o.isSelected())
            val = 1;

        heu.add(val*I_BLOCK_OPP_O);
        val = 0;

        if (b_number_of_pawns_o.isSelected())
            val = 1;

        heu.add(val*I_NUMBER_OF_PAWNS_O);
        val = 0;

        if (b_number_of_two_o.isSelected())
            val = 1;

        heu.add(val*I_TWO_CONF_O);
        val = 0;

        if (b_number_of_three_o.isSelected())
            val = 1;

        heu.add(val*I_THREE_CONF_O);
        val = 0;

        if (b_close_morris_m.isSelected())
            val = 1;

        heu.add(val*I_CLOSE_MORRIS_M);
        val = 0;

        if (b_number_of_morris_m.isSelected())
            val = 1;

        heu.add(val*I_MORRIS_NUMBER_M);
        val = 0;

        if (b_number_of_blocked_m.isSelected())
            val = 1;

        heu.add(val*I_BLOCK_OPP_M);
        val = 0;

        if (b_number_of_pawns_m.isSelected())
            val = 1;

        heu.add(val*I_NUMBER_OF_PAWNS_M);
        val = 0;

        if (b_open_morris_m.isSelected())
            val = 1;

        heu.add(val*I_OPEN_MORRIS_M);
        val = 0;

        if (b_double_morris_m.isSelected())
            val = 1;

        heu.add(val*I_DOUBLE_MORRIS_M);
        val = 0;

        if (b_win_m.isSelected())
            val = 1;

        heu.add(val*I_WIN_CONF_M);
        val = 0;

        if (b_close_morris_e.isSelected())
            val = 1;

        heu.add(val*I_CLOSE_MORRIS_E);
        val = 0;

        if (b_number_of_two_e.isSelected())
            val = 1;

        heu.add(val*I_TWO_CONF_E);
        val = 0;

        if (b_number_of_three_e.isSelected())
            val = 1;

        heu.add(val*I_THREE_CONF_E);
        val = 0;

        if (b_win_e.isSelected())
            val = 1;

        heu.add(val*I_WIN_CONF_E);

        return heu;
    }
}
