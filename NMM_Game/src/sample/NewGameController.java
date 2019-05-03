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


    public static Stage stage;

    @FXML
    public void initialize()
    {
        ArrayList<String> list = new ArrayList<>(Arrays.asList("Manual", "Machine"));
        ObservableList<String> olist = FXCollections.observableList(list);
        white_player.setItems(olist);
        white_player.getSelectionModel().selectFirst();
        black_player.setItems(olist);
        black_player.getSelectionModel().selectFirst();
    }

    public void startClicked(ActionEvent actionEvent) {
        ArrayList<Integer> w_heu = new ArrayList<>(Arrays.asList(w_h_pawns.isSelected() ? 8:0, w_h_two.isSelected() ? 12:0, w_h_moves.isSelected() ? 7:0, w_h_blocks.isSelected() ? 10:0));
        ArrayList<Integer> b_heu = new ArrayList<>(Arrays.asList(b_h_pawns.isSelected() ? 8:0, b_h_two.isSelected() ? 12:0, b_h_moves.isSelected() ? 7:0, b_h_blocks.isSelected() ? 10:0));
        Player p1 = new Player(NMM.I_WHITE_PLAYER, "white", white_player.getValue().equals("Manual") ? NMM.I_MAN_PLAYER : NMM.I_AI_PLAYER, NMM.I_OPEN_GAME_PHASE, w_heu);
        Player p2 = new Player(NMM.I_BLACK_PLAYER, "black", black_player.getValue().equals("Manual") ? NMM.I_MAN_PLAYER : NMM.I_AI_PLAYER, NMM.I_OPEN_GAME_PHASE, b_heu);
        NMM.setPlayers(new ArrayList<>(Arrays.asList(p1, p2)));
        stage.close();
    }
}
