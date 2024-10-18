package dev.niss.app.models.cards.hand.ruleset;

import dev.niss.app.models.cards.Card;
import dev.niss.app.models.cards.hand.Hand;

public class BlackjackRuleSet extends Hand {

    @Override
    protected int calculate() {
        int value = stream().mapToInt(card -> {
            int v = card.getValue().toInt();
            return v > 10 ? 10 : v;
        }).sum();

        boolean hasAce = stream().anyMatch(card -> card.getValue() == Card.Value.ACE);

        if (hasAce && ((value + 10) <= 21))
            value += 10;
        return value;
    }

}
