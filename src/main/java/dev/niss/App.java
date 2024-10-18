package dev.niss;

import dev.niss.app.root.RootLoader;
import dev.sol.app.FXApplication;
import dev.sol.core.view.FXLoader;
import dev.sol.theme.FXSkin;

public class App extends FXApplication {

    @Override
    public void initialize() throws Exception {
        applicationStage.setTitle("Blackjack-FX");
        applicationScene.getStylesheets().add(FXSkin.PRIMER_DARK.getResource().toExternalForm());
        applicationStage.setResizable(false);

        _initialize_root();

    }

    private void _initialize_root() {
        RootLoader loader = (RootLoader) FXLoader
                .createInstance(RootLoader.class, App.class.getResource("/dev/niss/app/root/ROOT.fxml"))
                .initialize();
        loader.load(applicationScene);

    }

    public static void main(String[] args) {
        launch(args);
    }

}