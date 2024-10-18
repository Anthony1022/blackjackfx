package dev.niss.app.board;

import dev.niss.app.models.cards.Card;
import dev.niss.app.models.cards.Deck;
import dev.niss.app.models.cards.Card.Face;
import dev.niss.app.models.cards.hand.Hand;
import dev.niss.app.models.cards.hand.ruleset.BlackjackRuleSet;
import dev.sol.base.collections.FXObservableMappedList;
import dev.sol.core.controller.FXController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class BoardController extends FXController {

    @FXML
    private AnchorPane board;

    @FXML
    private StackPane deckContainer;

    @FXML
    private Button startButton;
    @FXML
    private VBox buttonContainer;

    @FXML
    private VBox playerHandleInfoContainer;

    @FXML
    private VBox bankerHandleInfoContainer;

    @FXML
    private void handleStart() {
        shuffleDeck();

        player.add(deck.deal(Face.UP));
        player.add(deck.deal(Face.DOWN));

        banker.add(deck.deal(Face.UP));
        banker.add(deck.deal());

        started.set(true);
    };

    @FXML
    private void handleHit() {
    };

    @FXML
    private void handleStand() {
    };

    private Deck deck;
    private Hand player;
    private Hand banker;
    private BooleanProperty started;

    FXObservableMappedList<Card, Node> playerHandleInfoDisplay;

    FXObservableMappedList<Card, Node> bankerHandleInfoDisplay;

    @Override
    protected void load_fields() {
        deck = new Deck(135);
        deck.shuffle();

        player = new BlackjackRuleSet();
        player.addAll(deck.deal(Face.UP));

        banker = new BlackjackRuleSet();
        banker.addAll(deck.deal(Face.DOWN));

        playerHandleInfoDisplay = new FXObservableMappedList<>(player, card -> {
            Label label = card.labelDisplay();

            StackPane pane = new StackPane(label);
            StackPane.setAlignment(label, Pos.CENTER_RIGHT);

            return pane;
        });

        bankerHandleInfoDisplay = new FXObservableMappedList<>(banker, card -> {
            Label label = card.labelDisplay();

            StackPane pane = new StackPane(label);
            StackPane.setAlignment(label, Pos.CENTER_RIGHT);

            return pane;
        });

        started = new SimpleBooleanProperty();
    }

    @Override
    protected void load_bindings() {
        Bindings.bindContent(playerHandleInfoContainer.getChildren(), playerHandleInfoDisplay);
        Bindings.bindContent(bankerHandleInfoContainer.getChildren(), bankerHandleInfoDisplay);

        playerHandleInfoContainer.visibleProperty().bind(started);
        bankerHandleInfoContainer.visibleProperty().bind(started);

        startButton.disableProperty().bind(started);
        buttonContainer.disableProperty().bind(started.not());
    }

    @Override
    protected void load_listeners() {
        player.addListener((ListChangeListener<Card>) change -> {
            while (change.next()) {
                Card card = change.getList().get(change.getFrom());
                System.out.println("Current: " + card.display());
                System.out.println("Index" + player.indexOf(card));
                if (change.wasPermutated()) {

                } else {
                    if (change.wasRemoved()) {
                    }

                    if (change.wasAdded()) {
                    }
                }

            }
        });
    }

    public void shuffleDeck() {
        Bindings.unbindContent(deckContainer.getChildren(), deck);
        deck.shuffle();
        Bindings.bindContent(deckContainer.getChildren(), deck);
    }
}