module niss.blackjackfx {
    requires transitive javafx.controls;

    requires javafx.base;
    requires javafx.fxml;
    requires core.fx;
    requires javafx.graphics;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign;
    requires org.kordamp.ikonli.materialdesign2;
    requires org.kordamp.ikonli.core;

    requires atlantafx.base;

    opens dev.niss to javafx.fxml;
    opens dev.niss.app.root to javafx.fxml;
    opens dev.niss.app.board to javafx.fxml;

    exports dev.niss;
    exports dev.niss.app.root;
    exports dev.niss.app.board;
}
