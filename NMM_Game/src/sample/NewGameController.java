package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NewGameController {
    @FXML
    private ChoiceBox<String> white_player;

    @FXML
    private ChoiceBox<String> black_player;

    private HashMap<String, Integer> result = new HashMap<>();

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
        result.put("w", white_player.getValue().equals("Manual") ? NMM.I_MAN_PLAYER : NMM.I_AI_PLAYER);
        result.put("b", black_player.getValue().equals("Manual") ? NMM.I_MAN_PLAYER : NMM.I_AI_PLAYER);
        Player p1 = new Player(NMM.I_WHITE_PLAYER, "white", result.get("w"), NMM.I_OPEN_GAME_PHASE);
        Player p2 = new Player(NMM.I_BLACK_PLAYER, "black", result.get("b"), NMM.I_OPEN_GAME_PHASE);
        NMM.setPlayers(new ArrayList<>(Arrays.asList(p1, p2)));
        stage.close();
    }
}
