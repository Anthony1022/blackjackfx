package dev.niss.app.root;

import java.io.IOException;

import dev.niss.App;
import dev.niss.app.board.BoardLoader;
import dev.sol.core.view.FXLoader;
import dev.sol.core.view.FXView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class RootLoader extends FXView {
    public void load(Scene scene) {
        try {
            Parent node = loader.load();
            scene.setRoot(node);

            BoardLoader boardloader = (BoardLoader) FXLoader.createInstance(BoardLoader.class,
                    App.class.getResource("/dev/niss/app/board/BOARD.FXML"));
            boardloader.initialize();
            boardloader.load((BorderPane) node);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
