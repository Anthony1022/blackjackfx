package dev.niss.app.models.cards;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import dev.sol.base.collections.FXObservableList;

public class Deck extends FXObservableList<Card> {

    private Boolean includeJokers;

    public Deck() {
        this(false, Card.WIDTH);

    }

    public Deck(int custom_width) {
        this(false, custom_width);
    }

    public Deck(boolean includeJokers, int custom_width) {
        this.includeJokers = includeJokers;
        initialize(custom_width);
    }

    public void initialize(int custom_width) {
        Card.Suit.LIST().forEach(suit -> {
            Card.Value.LIST().forEach(value -> {
                if (suit == Card.Suit.JOKER)
                    return;

                if (value == Card.Value.RED)
                    return;
                add(new Card(suit, value, custom_width));
            });
        });

        if (includeJokers) {
            add(new Card(Card.Suit.JOKER, Card.Value.RED, custom_width));
            add(new Card(Card.Suit.JOKER, Card.Value.BLACK, custom_width));
        }

    }

    public void shuffle() {
        Collections.shuffle(this);
    }

    public void returnCards(Card card) {
        if (contains(card)) {
            add(card);
        }
    }

    public void returnCards(List<Card> card) {
        addAll(card);
    }

    public List<Card> deal(int amount) {
        List<Card> cards = new LinkedList<>();
        if (size() < amount) {
            cards.addAll(this);
        } else {
            for (int i = 0; i < amount; i++)
                cards.add(removeLast());

        }
        return cards;
    }

    public Card deal(Card.Face face) {
        if (isEmpty())
            return null;
        Card card = removeLast();
        card.setFace(face);
        return card;
    }

    public Card deal() {
        return isEmpty() ? null : removeLast();
    }

    public Integer cardsLeft() {
        return size();
    }

}
