package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GameOverController {
    public static boolean n_game = false;

    @FXML
    private Label winner;

    @FXML
    private Label w_time;

    @FXML
    private Label b_time;

    @FXML
    private Label w_counter;

    @FXML
    private Label b_counter;

    public static Stage stage;

    @FXML
    public void initialize()
    {
        int winn = NMM.getWinner();

        switch (winn)
        {
            case NMM.I_WHITE_PLAYER:
                winner.setText(" BIAŁY");
                break;
            case NMM.I_BLACK_PLAYER:
                winner.setText(" CZARNY");
                break;
            case NMM.I_DRAW:
                winner.setText(" REMIS");
                break;
        }

        Player w = NMM.getPlayer(NMM.I_WHITE_PLAYER);
        Player b = NMM.getPlayer(NMM.I_BLACK_PLAYER);

        w_time.setText(w.getTime() + "");
        b_time.setText(b.getTime() + "");
        w_counter.setText(w.getHistory_of_moves().size() + "");
        b_counter.setText(b.getHistory_of_moves().size() + "");

        writeToFile();
    }

    private void writeToFile() {
        final String path = "D:\\Users\\oladr\\Studia\\Term_VI\\Sztuczna\\WorkSpace\\NMM_Game\\score\\score.txt";

        try {

            File file = new File(path);
            file.createNewFile();

            FileWriter f_writer = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(f_writer);

            StringBuilder sb = new StringBuilder();
            ArrayList<String> history = NMM.getHistory_of_moves();

            for (int i=0; i<history.size(); i++) {
                sb.append(i+1);
                sb.append(". ");
                sb.append(history.get(i));
                sb.append("\n");
            }

            writer.write(sb.toString());
            writer.close();

            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
    }

    public void newGameClicked(ActionEvent actionEvent) {
        n_game = true;
        stage.close();
    }
}
