package dev.niss.app.models.cards;

import java.util.LinkedList;
import java.util.List;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignE;

import atlantafx.base.theme.Styles;
import dev.niss.App;
import dev.sol.ui.scene.control.FXWritableImage;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;

public class Card extends StackPane {
    public static final Image ATLAS = new Image(
            App.class.getResource("/dev/niss/assets/img/atlas.png").toExternalForm());

    public static final Integer WIDTH = (int) (ATLAS.getWidth() / 13);
    public static final Integer HEIGHT = (int) (ATLAS.getHeight() / 5);

    public static enum Suit {
        CLUBS("Clubs", MaterialDesignC.CARDS_CLUB),
        DIAMONDS("Diamonds", MaterialDesignC.CARDS_DIAMOND),
        HEARTS("Hearts", MaterialDesignC.CARDS_HEART),
        SPADES("Spades", MaterialDesignC.CARDS_SPADE),
        JOKER("Joker", MaterialDesignE.EMOTICON_HAPPY),
        BACK("Back Card", MaterialDesignC.CARDS);

        private String display;
        private Ikon ikon;

        private Suit(String display, Ikon ikon) {
            this.display = display;
            this.ikon = ikon;
        }

        public Ikon getIkon() {
            return this.ikon;
        }

        @Override
        public String toString() {
            return display;
        }

        public static List<Suit> LIST() {
            List<Suit> list = new LinkedList<>(List.of(values()));
            list.removeLast();
            return list;
        }

    }

    public static enum Value {

        ACE("Ace", 1),
        TWO("Two", 2),
        THREE("Three", 3),
        FOUR("Four", 4),
        FIVE("Five", 5),
        SIX("Six", 6),
        SEVEN("Seven", 7),
        EIGHT("Eight", 8),
        NINE("Nine", 9),
        TEN("Ten", 10),
        JACK("Jack", 11),
        QUEEN("Queen", 12),
        KING("King", 13),
        RED("Joker (Red)", -1),
        BLACK("Joker (Black)", -1),
        NONE("", -1);

        private String display;
        private int value;

        private Value(String display, int value) {
            this.display = display;
            this.value = value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public void setDisplay(String display) {
            this.display = display;
        };

        public Integer toInt() {
            return value;
        }

        @Override
        public String toString() {
            return display;
        }

        public static List<Value> LIST() {
            List<Value> list = new LinkedList<>(List.of(values()));

            list.removeLast();
            list.removeLast();
            return list;
        }
    }

    public static enum Face {
        UP,
        DOWN;
    }

    private ObjectProperty<Suit> suite;
    private ObjectProperty<Value> value;

    private ObjectProperty<Face> face;
    private ImageView back;
    private ImageView front;

    public Card() {
        this(Suit.BACK, Value.NONE, Card.WIDTH);
    }

    public Card(Suit suite, Value value, int custom_witdh) {
        this.suite = new SimpleObjectProperty<>(suite);
        this.value = new SimpleObjectProperty<>(value);
        this.face = new SimpleObjectProperty<>(Face.DOWN);

        back = new ImageView(new FXWritableImage(Card.ATLAS, Card.WIDTH * 2, Card.HEIGHT * 4, Card.WIDTH, Card.HEIGHT));
        back.setFitWidth(custom_witdh);
        back.setPreserveRatio(true);
        front = new ImageView(_render_frontcard());
        front.setFitWidth(custom_witdh);
        front.setPreserveRatio(true);
        getChildren().addAll(front, back);
    }

    public FXWritableImage _render_frontcard() {
        int suit_coordinate = switch (getSuit()) {
            case JOKER -> 4;
            case BACK -> 4;
            default ->
                getFace() == Face.DOWN ? HEIGHT * 4 : getSuit().ordinal();
        };

        int value_coordinate = switch (getValue()) {
            case BLACK -> 0;
            case RED -> 1;
            case NONE -> 2;
            default ->
                getFace() == Face.DOWN ? WIDTH * 2 : getSuit().ordinal();
        };

        return new FXWritableImage(Card.ATLAS, suit_coordinate, value_coordinate, Card.WIDTH, Card.HEIGHT);
    }

    public ObjectProperty<Suit> getSuitProperty() {
        return this.suite;
    }

    public Suit getSuit() {
        return this.suite.get();
    }

    public ObjectProperty<Value> getValueProperty() {
        return this.value;
    }

    public Value getValue() {
        return this.value.get();
    }

    public ObjectProperty<Face> getFaceProperty() {
        return this.face;
    }

    public Face getFace() {
        return this.face.get();
    }

    public void setFace(Face face) {
        this.face.set(face);
    }

    public String display() {
        if (getSuit() == Suit.JOKER) {
            return suite + " " + value;
        }
        return value + " of " + suite;
    }

    public Label labelDisplay() {
        FontIcon ikon = new FontIcon();
        ikon.setIconSize(20);
        ikon.setWrappingWidth(30);
        ikon.setTextAlignment(TextAlignment.RIGHT);

        Label display = new Label();
        display.setContentDisplay(ContentDisplay.RIGHT);
        display.getStyleClass().add(Styles.TEXT_BOLD);
        display.setGraphic(ikon);

        if (getFace() == Face.DOWN)
            ikon.setIconCode(MaterialDesignC.CARDS);

        else {
            ikon.setIconCode(getSuit().getIkon());
            display.setText(getValue().toString());
        }
        return display;
    }
}
