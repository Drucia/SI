package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class NewGameController {
    public static Stage stage;
    private static ArrayList<String> players;
    @FXML
    private ChoiceBox<String> white_player;

    @FXML
    private ChoiceBox<String> black_player;

    @FXML
    public void initialize()
    {
        players = new ArrayList<>(Arrays.asList("Manual", "Machine"));
        ObservableList<String> list = FXCollections.observableList(players);
        black_player.setItems(list);
        black_player.getSelectionModel().selectFirst();
        white_player.setItems(list);
        white_player.getSelectionModel().selectFirst();
    }

    public void startPressed()
    {
        NMM.setPlayers(white_player.getValue().equals("Manual") ? NMM.I_MAN_PLAYER : NMM.I_AI_PLAYER,
                black_player.getValue().equals("Manual") ? NMM.I_MAN_PLAYER : NMM.I_AI_PLAYER);
        stage.close();
    }
}
