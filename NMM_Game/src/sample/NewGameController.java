package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class NewGameController {
    public static boolean isClose = true;
    public CheckBox w_h_pawns;
    public CheckBox w_h_two;
    public CheckBox w_h_moves;
    public CheckBox w_h_blocks;
    public CheckBox b_h_pawns;
    public CheckBox b_h_two;
    public CheckBox b_h_moves;
    public CheckBox b_h_blocks;
    @FXML
    private ChoiceBox<String> white_player;

    @FXML
    private ChoiceBox<String> black_player;

    @FXML
    private ChoiceBox<String> w_alg;

    @FXML
    private ChoiceBox<String> b_alg;


    public static Stage stage;

    @FXML
    public void initialize()
    {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Machine", "Manual"));
        ArrayList<String> list_a = new ArrayList<>(Arrays.asList("Alpha Beta", "Mini Maxi"));
        ObservableList<String> olist = FXCollections.observableList(list);
        white_player.setItems(olist);
        white_player.getSelectionModel().selectFirst();
        black_player.setItems(olist);
        black_player.getSelectionModel().selectFirst();
        olist = FXCollections.observableList(list_a);
        w_alg.setItems(olist);
        w_alg.getSelectionModel().selectFirst();
        b_alg.setItems(olist);
        b_alg.getSelectionModel().selectFirst();
    }

    public void startClicked(ActionEvent actionEvent) {
        ArrayList<Integer> w_heu = new ArrayList<>(Arrays.asList(w_h_pawns.isSelected() ? 118:0, w_h_two.isSelected() ? 26:0, w_h_moves.isSelected() ? 1:0, w_h_blocks.isSelected() ? 6:0,
                312,7,641,43,10,8,507,42,1086,10,1,16, 1190));
        ArrayList<Integer> b_heu = new ArrayList<>(Arrays.asList(b_h_pawns.isSelected() ? 118:0, b_h_two.isSelected() ? 26:0, b_h_moves.isSelected() ? 1:0, b_h_blocks.isSelected() ? 6:0,
                312,7,641,43,10,8,507,42,1086,10,1,16, 1190));
        Player p1 = new Player(NMM.I_WHITE_PLAYER, "white", white_player.getValue().equals("Manual") ? NMM.I_MAN_PLAYER : NMM.I_AI_PLAYER, NMM.I_OPEN_GAME_PHASE,
                w_alg.getValue().equals("Mini Maxi") ? NMM.I_MINI_MAXI_ALG : NMM.I_ALPHA_BETA_ALG, w_heu);
        Player p2 = new Player(NMM.I_BLACK_PLAYER, "black", black_player.getValue().equals("Manual") ? NMM.I_MAN_PLAYER : NMM.I_AI_PLAYER, NMM.I_OPEN_GAME_PHASE,
                b_alg.getValue().equals("Mini Maxi") ? NMM.I_MINI_MAXI_ALG : NMM.I_ALPHA_BETA_ALG, b_heu);
        NMM.setPlayers(new ArrayList<>(Arrays.asList(p1, p2)));
        isClose = false;
        stage.close();
    }
}
