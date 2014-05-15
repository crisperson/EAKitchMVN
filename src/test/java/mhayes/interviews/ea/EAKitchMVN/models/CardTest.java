package mhayes.interviews.ea.EAKitchMVN.models;

import static org.junit.Assert.*;

import mhayes.interviews.ea.EAKitchMVN.models.Card.Suit;
import mhayes.interviews.ea.EAKitchMVN.models.Card.Value;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class CardTest.
 */
public class CardTest {

	/** The card1. */
	Card card1;
	
	/**
	 * Setup, initialize a single card object.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
	
		card1 = new Card();
	}

	/**
	 * Tear down.
	 *
	 * @throws Exception the exception
	 */
	@After
	public void tearDown() throws Exception {
		
	}

	/**
	 * Test is greater.
	 */
	@Test
	public void testIsGreater() {

		Card card2 = new Card();
		card1.set_value(Value.KING);
		card2.set_value(Value.QUEEN);
		
		assertTrue(card1.isGreater(card2));
		assertFalse(card2.isGreater(card1));
		
		card2.set_value(Value.ACE);
		assertTrue(card2.isGreater(card1));
		assertFalse(card1.isGreater(card2));
	}

	/**
	 * Test is greater or equal.
	 */
	@Test
	public void testIsGreaterOrEqual() {

		Card card2 = new Card();
		card1.set_value(Value.KING);
		card2.set_value(Value.QUEEN);
		
		assertTrue(card1.isGreaterOrEqual(card2));
		assertFalse(card2.isGreaterOrEqual(card1));
		
		card2.set_value(Value.ACE);
		assertTrue(card2.isGreaterOrEqual(card1));
		assertFalse(card1.isGreaterOrEqual(card2));
		
		card1.set_value(Value.ACE);
		assertTrue(card2.isGreaterOrEqual(card1));
		assertTrue(card1.isGreaterOrEqual(card2));
		
		card1.set_value(Value.TWO);
		assertFalse(card1.isGreaterOrEqual(card2));
		assertTrue(card2.isGreaterOrEqual(card1));
	}

	/**
	 * Test is equal.
	 */
	@Test
	public void testIsEqual() {

		Card card2 = new Card();
		
		card1.set_value(Value.KING);
		card2.set_value(Value.QUEEN);
		assertFalse(card1.equals(card2));
		assertFalse(card2.equals(card1));
		
		card2.set_value(Value.ACE);
		assertFalse(card2.equals(card1));
		assertFalse(card1.equals(card2));
		
		card1.set_value(Value.ACE);
		card1.set_suit(Suit.DIAMONDS);
		card2.set_suit(Suit.DIAMONDS);
		assertTrue(card2.equals(card1));
		assertTrue(card1.equals(card2));
		
		card1.set_value(Value.TWO);
		assertFalse(card1.equals(card2));
		assertFalse(card2.equals(card1));
		
		card1 = card2;
		assertTrue(card1.equals(card2));
		
		card2 = null;
		assertFalse(card1.equals(card2));
		
		Object card3 = new Object();
		assertFalse(card1.equals(card3));
	}

	/**
	 * Test is equal suit.
	 */
	@Test
	public void testIsEqualSuit() {

		Card card2 = new Card();
		card1.set_suit(Suit.CLUBS);
		card2.set_suit(Suit.DIAMONDS);
		
		assertFalse(card1.isEqualSuit(card2.get_suit()));
		assertFalse(card2.isEqualSuit(card1.get_suit()));
		
		card2.set_suit(Suit.CLUBS);
		assertTrue(card2.isEqualSuit(card1.get_suit()));
		assertTrue(card1.isEqualSuit(card2.get_suit()));
		
	}

	/**
	 * Test is equal value.
	 */
	@Test
	public void testIsEqualValue() {
		Card card2 = new Card();
		card1.set_value(Value.QUEEN);
		card2.set_value(Value.KING);
		
		assertFalse(card1.isEqualValue(card2.get_value()));
		assertFalse(card2.isEqualValue(card1.get_value()));
		
		card2.set_value(Value.QUEEN);
		assertTrue(card2.isEqualValue(card1.get_value()));
		assertTrue(card1.isEqualValue(card2.get_value()));
		
		assertTrue(card2.isEqualValue(Value.QUEEN));
		assertFalse(card1.isEqualValue(Value.FOUR));
	}

	/**
	 * Test compare to method; looking for a different response for each potential outcome (0, 1, <0, >1)
	 */
	@Test
	public void testCompareTo() {
		
		card1 = new Card();
		Card card2 = new Card();
		
		card1.set_value(Value.JACK);
		
		card2.set_value(Value.EIGHT);
		int result = card1.compareTo(card2);
		assertTrue(result > 0);
	

		card2.set_value(Value.ACE);

		result = card1.compareTo(card2);
		assertTrue(result < 0);
		
		card2.set_suit(Suit.DIAMONDS);

		result = card1.compareTo(card2);
		assertTrue(result < 0);
	}

}
