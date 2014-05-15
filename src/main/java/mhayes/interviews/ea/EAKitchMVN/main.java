package mhayes.interviews.ea.EAKitchMVN;

import mhayes.interviews.ea.EAKitchMVN.models.Card;
import mhayes.interviews.ea.EAKitchMVN.models.PokerHand;

public class main {

	//Enumeration for the types of comparison outcomes
	public enum Outcome { HAND_ONE, HAND_TWO, DRAW, INVALID_INPUT, MISSED }
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//Assumption: no check made whether the two hands can exist within the same game of poker
		// i.e. didn't check whether both hands have the same card
		
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.EIGHT));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
			
		PokerHand hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.ACE));
		
		System.out.println(compare(hand_one, hand_two));	
	}
	
	/**
	 * Compares two poker hands and returns the winning hand, or draw.
	 *
	 * @param one the first poker hand to be compared
	 * @param two the second poker hand to be compared
	 * @return the Outcome, an enumerated value: HAND_ONE, HAND_TWO, DRAW, INVALID_INPUT
	 */
	public static Outcome compare(PokerHand one, PokerHand two)
	{
		int comp = one.compareTo(two);
		if(comp > 1)
			return Outcome.HAND_ONE;
		else if(comp < 0)
			return Outcome.HAND_TWO;
		else if(comp == 1)
			return Outcome.INVALID_INPUT;
		else return Outcome.DRAW;
	}
}
