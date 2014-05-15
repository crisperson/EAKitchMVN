package mhayes.interviews.ea.EAKitchMVN.models;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

import mhayes.interviews.ea.EAKitchMVN.exceptions.InvalidPokerHandException;
import mhayes.interviews.ea.EAKitchMVN.models.Card.Suit;
import mhayes.interviews.ea.EAKitchMVN.models.Card.Value;
import mhayes.interviews.ea.EAKitchMVN.models.PokerHand.HandValue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PokerHandTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();
  
	PokerHand firstHand;
	PokerHand secondHand;
	
	@Before
	public void setUp() throws Exception {
		firstHand = new PokerHand();
		secondHand = new PokerHand();
		for(int i=0; i<5; i++)
		{
			firstHand.add((new Card()).makeRandomCard());
			secondHand.add((new Card()).makeRandomCard());
		}
	}

	@After
	public void tearDown() throws Exception {
		firstHand = new PokerHand();
		secondHand = new PokerHand();
	}

	@Test
	public void testEmptyConstructor() 
	{
		PokerHand testHand = new PokerHand();
		
		assertFalse(testHand.isValidHand());
		assertEquals(testHand.getHand().size(), 0);
	}
	
	@Test
	public void testConstructorWithIntParameter()
	{
		PokerHand testHand = new PokerHand(5);
		assertEquals(testHand.getHand().size(), testHand.getHandLimit());
		
		testHand = new PokerHand(3);
		assertFalse(testHand.isValidHand());
	}
	
	@Test
	public void testConstructorWithVariableArgsCards()
	{
		PokerHand testHand = new PokerHand(new Card(Suit.CLUBS, Value.TWO), new Card(Suit.CLUBS, Value.ACE));
		assertFalse(testHand.isValidHand());
		
		testHand = new PokerHand(new Card(Suit.CLUBS, Value.TWO), new Card(Suit.HEARTS, Value.ACE), 
									new Card(Suit.CLUBS, Value.FOUR), new Card(Suit.CLUBS, Value.SEVEN), 
									new Card(Suit.DIAMONDS, Value.ACE));
		
		assertTrue(testHand.isValidHand());
		
		
		
	}
	
	@Test
	public void testConstructorWithArrayListArg()
	{
		ArrayList<Card> list_one = new ArrayList<Card>();
		list_one.add(new Card(Suit.CLUBS, Value.TWO));
		list_one.add(new Card(Suit.CLUBS, Value.JACK));
		list_one.add(new Card(Suit.CLUBS, Value.ACE));
		list_one.add(new Card(Suit.CLUBS, Value.SIX));
		list_one.add(new Card(Suit.CLUBS, Value.EIGHT));
		
		PokerHand testHand = new PokerHand(list_one);
		
		assertTrue(testHand.isValidHand());
		assertEquals(testHand.getHand().size(), 5);
		
		list_one = new ArrayList<Card>();
		list_one.add(new Card(Suit.CLUBS, Value.TWO));
		list_one.add(new Card(Suit.CLUBS, Value.JACK));
		list_one.add(new Card(Suit.CLUBS, Value.EIGHT));
		
		testHand = new PokerHand(list_one);
		
		assertFalse(testHand.isValidHand());
		assertEquals(testHand.getHand().size(), 3);
	}
	
	@Test
	public void testSortAndEvaluate()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		
		assertFalse(hand_one.isValidHand());
		
		hand_one.sortAndEvaluate();
		
		assertFalse(hand_one.isValidHand());
		
	}
	
	@Test
	public void testSortHand() {
		
		//Check if sort works.
		//Check with a poker hand less than maximum as the add function also integrates searching
		
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		
		
		boolean isOrdered = 
				hand_one._hand.get(0).get_value().getValue() >= hand_one._hand.get(1).get_value().getValue() &&
				hand_one._hand.get(1).get_value().getValue() >= hand_one._hand.get(2).get_value().getValue() &&
				hand_one._hand.get(2).get_value().getValue() >= hand_one._hand.get(3).get_value().getValue() //&&
				//hand_one._hand.get(3).get_value().getValue() >= hand_one._hand.get(4).get_value().getValue()
				;
		
		assertFalse(isOrdered);
		
		hand_one.sortHand();
		
		isOrdered = hand_one._hand.get(0).get_value().getValue() >= hand_one._hand.get(1).get_value().getValue() &&
				hand_one._hand.get(1).get_value().getValue() >= hand_one._hand.get(2).get_value().getValue() &&
						hand_one._hand.get(2).get_value().getValue() >= hand_one._hand.get(3).get_value().getValue()// &&
						//hand_one._hand.get(3).get_value().getValue() >= hand_one._hand.get(4).get_value().getValue()
						;
		
		assertTrue(isOrdered);
	}
	
	/* Testing the EvaluateHand() method */
	
	@Test
	public void testIsRoyalFlush()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		
		hand_one.sortAndEvaluate();
		
		assertEquals(hand_one.get_handValue(), HandValue.ROYAL_FLUSH);
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.ACE));
		
		assertThat(hand_one.get_handValue(), not(HandValue.ROYAL_FLUSH));
	}
	
	@Test
	public void testIsStraightFlush()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		
		hand_one.sortAndEvaluate();
		
		assertEquals(hand_one.get_handValue(), HandValue.STRAIGHT_FLUSH);
		
		
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));

		hand_one.sortAndEvaluate();
		
		assertThat(hand_one.get_handValue(), not(HandValue.STRAIGHT_FLUSH));
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.SIX));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		
		hand_one.sortAndEvaluate();
		
		assertEquals(hand_one.get_handValue(), HandValue.STRAIGHT_FLUSH);
	}
	
	@Test
	public void testIsFourOfAKind()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		
		hand_one.sortAndEvaluate();
		
		assertEquals(hand_one.get_handValue(), HandValue.FOUR_OF_A_KIND);
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));

		hand_one.sortAndEvaluate();
		
		assertThat(hand_one.get_handValue(), not(HandValue.FOUR_OF_A_KIND));
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.SIX));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		
		hand_one.sortAndEvaluate();
		
		assertEquals(hand_one.get_handValue(), HandValue.FOUR_OF_A_KIND);
	}
	
	@Test
	public void testIsStraight()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.FIVE));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.SIX));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		
		hand_one.sortAndEvaluate();
		
		assertEquals(hand_one.get_handValue(), HandValue.STRAIGHT);
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));

		hand_one.sortAndEvaluate();
		
		assertThat(hand_one.get_handValue(), not(HandValue.STRAIGHT));
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.SIX));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		
		hand_one.sortAndEvaluate();
		
		//This would be a straight flush, not a straight
		assertThat(hand_one.get_handValue(), not(HandValue.STRAIGHT));
		
		/* Fringe cases where the check fails as we move down through A-5-4-3-2 */
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.FIVE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		
		hand_one.sortAndEvaluate();
		assertThat(hand_one.get_handValue(), not(HandValue.STRAIGHT));
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		
		hand_one.sortAndEvaluate();
		assertThat(hand_one.get_handValue(), not(HandValue.STRAIGHT));
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.THREE));
		
		hand_one.sortAndEvaluate();
		assertThat(hand_one.get_handValue(), not(HandValue.STRAIGHT));
	}

	@Test
	public void testIsFlush()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.SIX));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		
		hand_one.sortAndEvaluate();
		
		assertEquals(hand_one.get_handValue(), HandValue.FLUSH);
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));

		hand_one.sortAndEvaluate();
		
		assertThat(hand_one.get_handValue(), not(HandValue.FLUSH));
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		
		hand_one.sortAndEvaluate();
		
		//This would be a royal flush, not just a flush
		assertThat(hand_one.get_handValue(), not(HandValue.FLUSH));		
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		
		hand_one.sortAndEvaluate();
		
		assertThat(hand_one.get_handValue(), not(HandValue.FLUSH));
	}
	
	@Test
	public void testIsFullHouse()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		
		hand_one.sortAndEvaluate();
		
		assertEquals(hand_one.get_handValue(), HandValue.FULL_HOUSE);
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.KING));

		hand_one.sortAndEvaluate();
		
		assertThat(hand_one.get_handValue(), not(HandValue.FULL_HOUSE));
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TEN));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.TEN));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.KING));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		
		hand_one.sortAndEvaluate();
		
		//This would be a royal flush, not just a flush
		assertEquals(hand_one.get_handValue(), (HandValue.FULL_HOUSE));			
	}
	
	@Test 
	public void testIsTriples()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		
		hand_one.sortAndEvaluate();
		
		assertEquals(hand_one.get_handValue(), HandValue.THREE_OF_A_KIND);

		hand_one.sortAndEvaluate();
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.KING));
		
		assertThat(hand_one.get_handValue(), not(HandValue.THREE_OF_A_KIND));		
	}
	
	@Test
	public void testIsTwoPair()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		
		hand_one.sortAndEvaluate();
		
		assertEquals(hand_one.get_handValue(), HandValue.TWO_PAIR);
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));

		hand_one.sortAndEvaluate();
		
		assertThat(hand_one.get_handValue(), not(HandValue.TWO_PAIR));		
		
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.KING));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		
		//It is two pair, but it is also a full house.
		assertThat(hand_one.get_handValue(), not(HandValue.TWO_PAIR));	
	}
	
	@Test 
	public void testIsPair()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		
		hand_one.sortAndEvaluate();
		
		assertEquals(hand_one.get_handValue(), HandValue.PAIR);
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.EIGHT));

		hand_one.sortAndEvaluate();
		
		assertThat(hand_one.get_handValue(), not(HandValue.PAIR));			
	
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));

		hand_one.sortAndEvaluate();
		
		//Two pair, not one pair.
		assertThat(hand_one.get_handValue(), not(HandValue.PAIR));	
	}
	
	@Test
	public void testIsHighCard()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		
		hand_one.sortAndEvaluate();
		
		assertEquals(hand_one.get_handValue(), HandValue.HIGH_CARD);

		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		
		hand_one.sortAndEvaluate();
		
		assertEquals(hand_one._hand.get(0).get_value(), Card.Value.ACE);

		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		
		hand_one.sortAndEvaluate();
		
		//pair
		assertThat(hand_one.get_handValue(), not(HandValue.HIGH_CARD));	
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		
		hand_one.sortAndEvaluate();
		
		//triples
		assertThat(hand_one.get_handValue(), not(HandValue.HIGH_CARD));

		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		
		hand_one.sortAndEvaluate();
		
		//four of a kind
		assertThat(hand_one.get_handValue(), not(HandValue.HIGH_CARD));

		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		
		hand_one.sortAndEvaluate();
		
		//two pair
		assertThat(hand_one.get_handValue(), not(HandValue.HIGH_CARD));
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.THREE));
		
		hand_one.sortAndEvaluate();
		
		//full house
		assertThat(hand_one.get_handValue(), not(HandValue.HIGH_CARD));		

		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		
		hand_one.sortAndEvaluate();
		
		//straight
		assertThat(hand_one.get_handValue(), not(HandValue.HIGH_CARD));
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));
		
		hand_one.sortAndEvaluate();
		
		//flush
		assertThat(hand_one.get_handValue(), not(HandValue.HIGH_CARD));

		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.FIVE));
		
		hand_one.sortAndEvaluate();
		
		//straight flush
		assertThat(hand_one.get_handValue(), not(HandValue.HIGH_CARD));
		

		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.TEN));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));
		
		hand_one.sortAndEvaluate();
		
		//royal flush
		assertThat(hand_one.get_handValue(), not(HandValue.HIGH_CARD));
	}

	@Test
	public void testValidPokerHand()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		
		assertTrue(hand_one.isValidPokerHand());
	
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		
		assertFalse(hand_one.isValidPokerHand());
		
		hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		
		assertFalse(hand_one.isValidPokerHand());
	}

	//Evaluating the compareTo method

	@Test
	public void testRoyalFlushWin()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.TEN));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));
		hand_one.sortAndEvaluate();
		//make sure we have a royal flush
		assertEquals(hand_one.get_handValue(), HandValue.ROYAL_FLUSH);

		//royal flush beats everything, and ties another royal flush of a different type
		PokerHand hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();
		
		//tie
		assertEquals(hand_two.get_handValue(), HandValue.ROYAL_FLUSH);
		assertEquals(hand_one.compareTo(hand_two), 0);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs flush
		int result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.FLUSH);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs straight flush
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.STRAIGHT_FLUSH);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs straight
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.STRAIGHT);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs four of a kind
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.FOUR_OF_A_KIND);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs three of a kind
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.THREE_OF_A_KIND);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs full house
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.FULL_HOUSE);
		

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs two pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.TWO_PAIR);
	

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs high
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);

	}

	@Test
	public void testStraightFlushWin()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.NINE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.TEN));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_one.sortAndEvaluate();
		//make sure we have a straight flush
		assertEquals(hand_one.get_handValue(), HandValue.STRAIGHT_FLUSH);

		//straight flush beats everything but a royal flush, so check all those
		//since we have already checked the loss condition above.
		//first check that it ties to another identical straight flush
		PokerHand hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.EIGHT));
		hand_two.sortAndEvaluate();
		
		//tie
		assertEquals(hand_two.get_handValue(), HandValue.STRAIGHT_FLUSH);
		assertEquals(hand_one.compareTo(hand_two), 0);
		
		//next check that it beats a worse straight flush
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.EIGHT));
		hand_two.sortAndEvaluate();
		
		//win vs inferior straight flush
		int result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.STRAIGHT_FLUSH);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();
		
		//loss vs better straight flush
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.STRAIGHT_FLUSH);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs flush
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.FLUSH);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs straight
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.STRAIGHT);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs four of a kind
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.FOUR_OF_A_KIND);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs three of a kind
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.THREE_OF_A_KIND);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs full house
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.FULL_HOUSE);
		

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs two pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.TWO_PAIR);
	

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs high
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);

	}

	@Test
	public void testFourOfAKindWin() 
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_one.sortAndEvaluate();
		//make sure we have a straight flush
		assertEquals(hand_one.get_handValue(), HandValue.FOUR_OF_A_KIND);

		//four of a kind beats everything but a royal flush and straight flush, so check all those
		//since we have already checked the loss condition above.
		//first check that it ties to another identical four of a kind
		PokerHand hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_two.sortAndEvaluate();
		
		//tie
		assertEquals(hand_two.get_handValue(), HandValue.FOUR_OF_A_KIND);
		assertEquals(hand_one.compareTo(hand_two), 0);
		
		//next check that it beats a worse straight flush
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//win vs inferior straight flush
		int result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.FOUR_OF_A_KIND);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.ACE));
		hand_two.sortAndEvaluate();
		
		//loss vs better straight flush
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.FOUR_OF_A_KIND);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs flush
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.FLUSH);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs straight
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.STRAIGHT);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs three of a kind
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.THREE_OF_A_KIND);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs full house
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.FULL_HOUSE);
		

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs two pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.TWO_PAIR);
	

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs high
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
	}

	@Test
	public void testFullHouseWin() 
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.SPADES, Card.Value.EIGHT));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_one.sortAndEvaluate();
		//make sure we have a straight flush
		assertEquals(hand_one.get_handValue(), HandValue.FULL_HOUSE);

		//four of a kind beats everything but a royal flush and straight flush, so check all those
		//since we have already checked the loss condition above.
		//first check that it ties to another identical four of a kind
		PokerHand hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.EIGHT));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_two.sortAndEvaluate();
		
		//tie
		assertEquals(hand_two.get_handValue(), HandValue.FULL_HOUSE);
		assertEquals(hand_one.compareTo(hand_two), 0);
		
		//next check that it beats a worse straight flush
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SIX));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//win vs inferior straight flush (by the pair)
		int result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.FULL_HOUSE);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.ACE));
		hand_two.sortAndEvaluate();
		
		//loss vs better straight flush (by the pair)
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.FULL_HOUSE);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.ACE));
		hand_two.sortAndEvaluate();
		
		//loss vs better straight flush (by the triplet)
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.FULL_HOUSE);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.FIVE));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.FIVE));
		hand_two.sortAndEvaluate();
		
		//win vs lesser straight flush (by the triplet)
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.FULL_HOUSE);	
		
		
		
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs flush
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.FLUSH);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs straight
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.STRAIGHT);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs three of a kind
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.THREE_OF_A_KIND);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs two pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.TWO_PAIR);
	

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs high
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
	}
	
	@Test
	public void testFlushWin() 
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_one.sortAndEvaluate();
		//make sure we have a straight flush
		assertEquals(hand_one.get_handValue(), HandValue.FLUSH);

		//flush beats everything but a royal flush, straight flush, four of a kind, and full house, so check all those
		//since we have already checked the loss condition above.
		//first check that it ties to another identical flush
		PokerHand hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_two.sortAndEvaluate();
		
		//tie
		assertEquals(hand_two.get_handValue(), HandValue.FLUSH);
		assertEquals(hand_one.compareTo(hand_two), 0);
		
		//next check that it beats a worse flush
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_two.sortAndEvaluate();
		
		//win vs inferior  flush 
		int result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.FLUSH);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_two.sortAndEvaluate();
		
		//loss vs better  flush 
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.FLUSH);
			
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs straight
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.STRAIGHT);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs three of a kind
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.THREE_OF_A_KIND);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs two pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.TWO_PAIR);
	

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs high
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
	}
	
	@Test
	public void testStraightWin() 
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_one.sortAndEvaluate();
		//make sure we have a straight flush
		assertEquals(hand_one.get_handValue(), HandValue.STRAIGHT);

		//straight beats everything but a flush, royal flush, straight flush, four of a kind, and full house, so check all those
		//since we have already checked the loss condition above.
		//first check that it ties to another identical flush
		PokerHand hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//tie
		assertEquals(hand_two.get_handValue(), HandValue.STRAIGHT);
		assertEquals(hand_one.compareTo(hand_two), 0);
		
		//next check that it beats a worse straight
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//win vs inferior  straight 
		int result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.STRAIGHT);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//loss vs better  straight 
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.STRAIGHT);
	
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.KING));
		hand_two.sortAndEvaluate();

		//win vs three of a kind
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.THREE_OF_A_KIND);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs two pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.TWO_PAIR);
	

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs high
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
	}
	
	@Test
	public void testThreeOfAKindWin() 
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_one.sortAndEvaluate();
		//make sure we have a straight flush
		assertEquals(hand_one.get_handValue(), HandValue.THREE_OF_A_KIND);

		//straight beats everything but a flush, royal flush, straight flush, four of a kind, and full house, so check all those
		//since we have already checked the loss condition above.
		//first check that it ties to another identical flush
		PokerHand hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//tie
		assertEquals(hand_two.get_handValue(), HandValue.THREE_OF_A_KIND);
		assertEquals(hand_one.compareTo(hand_two), 0);
		
		//next check that it beats a worse triple (by the triple)
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//win vs inferior  straight 
		int result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.THREE_OF_A_KIND);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.FIVE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.FIVE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//loss vs better  triple (by the triple) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.THREE_OF_A_KIND);
	
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//loss vs better  triple (by the highest number) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.THREE_OF_A_KIND);
		
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TEN));		//beats the six
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));	//ties the other jack
		hand_two.sortAndEvaluate();
		
		//loss vs better  triple (by the second highest number) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.THREE_OF_A_KIND);	
		
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));		//loses to jack
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//win vs inferior  three, by the highest number 
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.THREE_OF_A_KIND);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));		//ties to jack
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));			//loses to six
		hand_two.sortAndEvaluate();
		
		//win vs inferior  three, by the highest number 
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.THREE_OF_A_KIND);
		
				
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs two pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.TWO_PAIR);
	

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs high
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
	}
	
	@Test
	public void testTwoPairWin() 
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_one.sortAndEvaluate();
		//make sure we have a straight flush
		assertEquals(hand_one.get_handValue(), HandValue.TWO_PAIR);

		//first check that it ties to another identical two pair
		PokerHand hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//tie
		assertEquals(hand_two.get_handValue(), HandValue.TWO_PAIR);
		assertEquals(hand_one.compareTo(hand_two), 0);
		
		//next check that it beats a worse two pair (by the singlet)
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));
		hand_two.sortAndEvaluate();
		
		//win vs inferior two pair, by the singlet 
		int result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.TWO_PAIR);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));
		hand_two.sortAndEvaluate();
		
		//loss vs better  two pair (by the singlet) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.TWO_PAIR);
	
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//loss vs better  two pair (by the higher pair) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.TWO_PAIR);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//loss vs better  two pair (by the lower pair) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.TWO_PAIR);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//win  vs lesser  two pair (by the higher pair) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.TWO_PAIR);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//win  vs lesser  two pair (by the lower pair) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.TWO_PAIR);	

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs pair
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs high
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
	}
	
	@Test
	public void testSinglePairWin() 
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_one.sortAndEvaluate();
		//make sure we have a straight flush
		assertEquals(hand_one.get_handValue(), HandValue.PAIR);

		//first check that it ties to another identical two pair
		PokerHand hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//tie
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);
		assertEquals(hand_one.compareTo(hand_two), 0);
		
		//next check that it beats a worse pair (by the pair)
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//win vs inferior two pair, by the pair 
		int result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.NINE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.NINE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//loss vs better  two pair (by the singlet) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);
	
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.QUEEN));			//beats jack
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//loss vs better  two pair (by the highest value) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TEN));			//beats 8
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));			
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
		//loss vs better  two pair (by the second value) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));	
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));			
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));			//beats 6
		hand_two.sortAndEvaluate();
		
		//loss vs better  two pair (by the third value) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));	
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.TEN));				//loses to jack
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));			
		hand_two.sortAndEvaluate();
		
		//beats vs lesser  two pair (by the highest value) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));		//loses to 8	
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));				
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));			
		hand_two.sortAndEvaluate();
		
		//beats vs lesser  two pair (by the highest value) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.EIGHT));		
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));				
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));			//loses to 6			
		hand_two.sortAndEvaluate();
		
		//beats vs lesser  two pair (by the highest value) 
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.PAIR);

		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.ACE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.TWO));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SEVEN));
		hand_two.sortAndEvaluate();

		//win vs high
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
	}
	
	@Test
	public void testHighCardWin() 
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));
		hand_one.sortAndEvaluate();
		//make sure we have a high card
		assertEquals(hand_one.get_handValue(), HandValue.HIGH_CARD);

		//first check that it ties to another identical two pair
		PokerHand hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));
		hand_two.sortAndEvaluate();
		
		//tie
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
		assertEquals(hand_one.compareTo(hand_two), 0);
		
		//next check that it beats a high card, by the highest card
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.QUEEN));		//loses to king
		hand_two.sortAndEvaluate();
		
		//win vs inferior two pair, by the pair 
		int result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
		
		//next check that it beats a high card, by the second highest card
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.TEN));				//loses to ten
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));		
		hand_two.sortAndEvaluate();
		
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
		
		//next check that it beats a high card, by the third highest card
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SIX));			//loses to seven
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));				
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));		
		hand_two.sortAndEvaluate();
		
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
		
		//next check that it beats a high card, by the fourth highest card
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FOUR));			//loses to five
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));			
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));				
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));		
		hand_two.sortAndEvaluate();
		
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
		
		//next check that it beats a high card, by the fourth highest card
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TWO));			//loses to three
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));		
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));			
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));				
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));		
		hand_two.sortAndEvaluate();
		
		result = hand_one.compareTo(hand_two);
		assertTrue(result>1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
		
		//next check that it loses by high card, versus a highest card
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));			
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));		
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));			
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));				
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.ACE));				//beats king
		hand_two.sortAndEvaluate();
		
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
		
		//next check that it loses by high card, versus a second card
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));			
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));		
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));			
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.QUEEN));				//beats jack				
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));				
		hand_two.sortAndEvaluate();
		
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
		
		//next check that it loses by high card, versus a highest card
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));			
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));		
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.TEN));				//beats seven			
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));							
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));				
		hand_two.sortAndEvaluate();
		
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
		
		//next check that it loses by high card, versus a fourth card
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.THREE));			
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.SIX));					//beats five
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));						
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));							
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));				
		hand_two.sortAndEvaluate();
		
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
			
		//next check that it loses by high card, versus a fifth card
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));				//beats four
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));					
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));						
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));							
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));				
		hand_two.sortAndEvaluate();
		
		result = hand_one.compareTo(hand_two);
		assertTrue(result<=-1);
		assertEquals(hand_two.get_handValue(), HandValue.HIGH_CARD);
	}
	
	@Test
	public void testAdd()
	{
		PokerHand hand_one = new PokerHand();
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.FOUR));				
		hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.FIVE));					
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.SEVEN));						
		hand_one.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));							
		hand_one.add(new Card(Card.Suit.DIAMONDS, Card.Value.KING));		
		
		assertFalse(hand_one.add(new Card(Card.Suit.HEARTS, Card.Value.KING)));
		
	}
}
