package dev.niss.app.board;

import java.io.IOException;

import dev.sol.core.view.FXView;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class BoardLoader extends FXView {
    public void load(BorderPane borderpane) {
        try {
            Parent root = loader.load();
            borderpane.setCenter(root);

            BoardController controller = loader.getController();
            controller.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
