package mhayes.interviews.ea.EAKitchMVN.models;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;

import mhayes.interviews.ea.EAKitchMVN.models.Card.Suit;
import mhayes.interviews.ea.EAKitchMVN.models.Card.Value;
import mhayes.interviews.ea.EAKitchMVN.models.PokerHand.HandValue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * The Class PokerHandTest.
 */
public class PokerHandTest {

  /** The potential exception to catch. */
  @Rule
  public ExpectedException exception = ExpectedException.none();
  
	/** The first hand. */
	PokerHand firstHand;
	
	/** The second hand. */
	PokerHand secondHand;
	
	/**
	 * Setup method, initialize a couple poker hands to potentially use.
	 *
	 * @throws Exception the exception
	 */
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

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
		firstHand = new PokerHand();
		secondHand = new PokerHand();
	}

	/**
	 * Test empty constructor.
	 */
	@Test
	public void testEmptyConstructor() 
	{
		PokerHand testHand = new PokerHand();
		
		assertFalse(testHand.isValidHand());
		assertEquals(testHand.getHand().size(), 0);
	}
	
	/**
	 * Test constructor with int parameter.
	 */
	@Test
	public void testConstructorWithIntParameter()
	{
		PokerHand testHand = new PokerHand(5);
		assertEquals(testHand.getHand().size(), testHand.getHandLimit());
		
		testHand = new PokerHand(3);
		assertFalse(testHand.isValidHand());
	}
	
	/**
	 * Test constructor with variable args cards.
	 */
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
	
	/**
	 * Test constructor with array list arg.
	 */
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
	
	/**
	 * Test sort and evaluate method. checking for the branch where we have an invalid hand making it to the
	 * sortAndEvaluate method
	 */
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
	
	/**
	 * Test sort hand method. Checking that an unsorted hand becomes sorted once passing through the sort method.
	 */
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
	
	/**
	 * Test is royal flush. Sanity checking that when we evaluate the hand, we can see that the HandValue variable is set
	 * to the royal flush.
	 */
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
	
	/**
	 * Test is straight flush. Sanity checking that when we evaluate the hand, we can see that the HandValue variable is set
	 * to the straight flush.
	 */
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
	
	/**
	 * Test is four of a kind. Sanity checking that when we evaluate the hand, we can see that the HandValue variable is set
	 * to the four of a kind.
	 */
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
	
	/**
	 * Test is straight. Sanity checking that when we evaluate the hand, we can see that the HandValue variable is set
	 * to the straight.
	 */
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

	/**
	 * Test is flush. Sanity checking that when we evaluate the hand, we can see that the HandValue variable is set
	 * to the flush.
	 */
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
	
	/**
	 * Test is full house. Sanity checking that when we evaluate the hand, we can see that the HandValue variable is set
	 * to the full house.
	 */
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
	
	/**
	 * Test is triples. Sanity checking that when we evaluate the hand, we can see that the HandValue variable is set
	 * to the three of a kind.
	 */
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
	
	/**
	 * Test is two pair. Sanity checking that when we evaluate the hand, we can see that the HandValue variable is set
	 * to the two pair.
	 */
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
	
	/**
	 * Test is pair. Sanity checking that when we evaluate the hand, we can see that the HandValue variable is set
	 * to the pair.
	 */
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
	
	/**
	 * Test is high card. Sanity checking that when we evaluate the hand, we can see that the HandValue variable is set
	 * to the high card.
	 */
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

	/**
	 * Test valid poker hand. Making sure that invalid poker hands are not included. Most importantly, this includes hands
	 * which have identical cards in them (impossible in the assumed 52 card standard deck).
	 */
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
	//Essentially comparing all the win conditions for each hand
	//This implicitly also compares all the loss conditions as well.

	/**
	 * Test royal flush win. make sure we win against everything else; and tie against an equivalent royal flush
	 */
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

	/**
	 * Test straight flush win. make sure we win against everything except a royal flush. and that we tie an identical
	 * straight flush. also that we lose to a better straight flush, and win vs a lesser straight flush.
	 */
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

	/**
	 * Test four of a kind win. make sure we win against everything except a royal flush and straight flush. and that we tie an identical
	 * four of a kind (impossible). also that we lose to a better four of a kind, and win vs a lesser four of a kind.
	 */
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
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
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

	/**
	 * Test full house win. check that we win against everything except a royal flush, straight flush, and four of a kind.
	 * also check that we tie against an equivalent full house; beat a lesser full house by the triples, beat a lesser full
	 * house by the doubles; lose to a better full house by the triples; and lose to a better full house by the doubles.
	 */
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
		assertEquals(hand_one.get_handValue(), HandValue.FULL_HOUSE);

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
		
		hand_two = new PokerHand();
		hand_two.add(new Card(Card.Suit.DIAMONDS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.HEARTS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.JACK));
		hand_two.add(new Card(Card.Suit.CLUBS, Card.Value.SIX));
		hand_two.add(new Card(Card.Suit.SPADES, Card.Value.SIX));
		hand_two.sortAndEvaluate();
		
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
	
	/**
	 * Test flush win. test that we win against everything except a royal flush, straight flush, four of a kind, and full house.
	 * also check that we beat a lesser flush (lesser high value), lose to a better flush (better high value), and tie an equivalent
	 * flush but in a different suit.
	 */
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
	
	/**
	 * Test straight win. test that we win only against the three of a kind, two pair, pair, and high card. also test that we
	 * tie an equivalent straight, i.e. same high card. we lose to a better straight, better high card. and win vs a lesser
	 * straight, i.e. worse high card.
	 */
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
		assertEquals(hand_one.get_handValue(), HandValue.STRAIGHT);

		//straight beats everything but a flush, royal flush, straight flush, four of a kind, and full house, so check all those
		//since we have already checked the loss condition above.
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
	
	/**
	 * Test three of a kind win. check that we only beat a two pair, single pair, and high card. also check that we tie equivalent
	 * triples (impossible, but checked for sanity). check we beat a worse triple, check we beat a better triple. also check we 
	 * lose to a better triple by the high card, or a better triple by the second highest card. also check we beat a lesser
	 * triple by the high card, or beat a lesser triple by the second highest card.
	 */
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
		assertEquals(hand_one.get_handValue(), HandValue.THREE_OF_A_KIND);

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
	
	/**
	 * Test two pair win. check that we only beat a pair and a high card. check that we tie an equivalent two pair, with the same
	 * pair and high card. check we lose against a two pair with a better high card. check that we win against a two pair with a
	 * worse high card. check we lose against a two pair with a better high pair. check we lose against a two pair with the same
	 * high pair but better low pair. equivalently check that we beat a two pair that has a lesser high pair, or that we beat
	 * a two pair that has a lesser low pair but equivalent high pair.
	 */
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
		assertEquals(hand_one.get_handValue(), HandValue.TWO_PAIR);

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
	
	/**
	 * Test single pair win. check that we only beat a high card. check that we tie an equivalent pair. check that we beat a pair
	 * of lesser value. check that we lose to a pair of higher value. check that we beat a pair that has a worse high card, or
	 * equivalent high card and worse second card, or equivalent highest and second highest but worst third highest card. also
	 * check that we lose to the opposite conditions: a pair that has a better high card, or better second card and same high card,
	 * or better third card and same first and second high card.
	 */
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
		assertEquals(hand_one.get_handValue(), HandValue.PAIR);

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
	
	/**
	 * Test high card win. only need to check if we beat other high cards. check that we tie identical hands. check that we lose to
	 * a better high card, or better second card with same high card, or better third card with same first and second, and so on.
	 * also check we win in the opposite conditions: i.e. we have a better high card, or we have a better second card but same 
	 * high card, and so on.
	 */
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
	
	/**
	 * Test add. Check that we can only add up to the standard hand limit constant.
	 */
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
