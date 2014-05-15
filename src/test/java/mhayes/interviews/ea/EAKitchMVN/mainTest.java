package mhayes.interviews.ea.EAKitchMVN;

import static org.junit.Assert.*;

import mhayes.interviews.ea.EAKitchMVN.main.Outcome;
import mhayes.interviews.ea.EAKitchMVN.models.Card;
import mhayes.interviews.ea.EAKitchMVN.models.PokerHand;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class mainTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCompare() {
		
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
		
		//HandTwo: FH Aces with Jacks, HandOne: FH Jacks with Eights
		//Winner: HandTwo
		assertEquals(main.compare(hand_one, hand_two), Outcome.HAND_TWO);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.ACE));

		//HandTwo: TP Jacks and Aces, HandOne: FH Jacks with Eights
		//Winner: HandOne
		assertEquals(main.compare(hand_one, hand_two), Outcome.HAND_ONE);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.EIGHT));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.EIGHT));

		//HandTwo: FH Jacks with Eights, HandOne: FH Jacks with Eights
		//Winner: Draw
		assertEquals(main.compare(hand_one, hand_two), Outcome.DRAW);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.EIGHT));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.EIGHT));
		
		//HandTwo: Invalid hand, HandOne: FH Jacks with Eights
		//Winner: Invalid hand
		assertEquals(main.compare(hand_one, hand_two), Outcome.INVALID_INPUT);

		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.EIGHT));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.EIGHT));

		//HandTwo: FH Jacks with Eights, HandOne: Invalid Hand
		//Winner: Invalid input
		assertEquals(main.compare(hand_one, hand_two), Outcome.INVALID_INPUT);

	}

}
