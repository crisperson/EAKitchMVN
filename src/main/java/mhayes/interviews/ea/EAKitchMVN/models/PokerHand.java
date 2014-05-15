package mhayes.interviews.ea.EAKitchMVN.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import mhayes.interviews.ea.EAKitchMVN.comparators.CardComparator;
import mhayes.interviews.ea.EAKitchMVN.exceptions.InvalidPokerHandException;

/**
 * The Class PokerHand. The class represents a hand of Card objects, with the default hand size of 5; the standard 
 * hand size in poker.
 */
public class PokerHand implements Comparable<PokerHand> {

	/** The Constant HAND_LIMIT, representing the standard hand size in poker. */
	static final int HAND_LIMIT = 5;

	/**
	 * The Enum HandValue.
	 * HandValue represents the overall "rank" of the poker hand, from 0-9, from High Card to a Royal Flush.
	 * Used to compare two poker hands together
	 */
	public enum HandValue {

		HIGH_CARD(0), 
 PAIR(1), 
 TWO_PAIR(2), 
 THREE_OF_A_KIND(3), 
 STRAIGHT(4), 
 FLUSH(5), 
 FULL_HOUSE(6), 
 FOUR_OF_A_KIND(7), 
 STRAIGHT_FLUSH(8), 
 ROYAL_FLUSH(9);

		/** The value of the rank, i.e. a royal flush is worth the most value at 9. */
		private int value;

		/**
		 * Instantiates a new hand value.
		 *
		 * @param v the value for the new hand
		 */
		HandValue(int v) {
			this.value = v;
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public int getValue() {
			return value;
		}
	}

	/** The evaluate indices. Used in determining the order in which to break ties. */
	private int[] evaluateIndices = { 0, 1, 2, 3, 4 };

	/** Boolean to determine whether the current hand is valid or not. */
	private boolean isValidHand;
	
	/** Private member for describing the value of the object */
	private HandValue _handValue;
	
	/** The _hand member variable, defines the hand of cards used in comparison and ranking. */
	ArrayList<Card> _hand;

	/* Constructors */
	
	/**
	 * Instantiates a new poker hand that is invalid; but has an initial capacity of HAND_LIMIT.
	 */
	public PokerHand() 
	{
		_hand = new ArrayList<Card>(HAND_LIMIT);
		isValidHand = false;
	}

	/**
	 * Instantiates a new poker hand with a given hand limit.
	 *
	 * @param hand_limit the hand limit to instantiate the poker hand object.
	 */
	public PokerHand(int hand_limit) {
		_hand = new ArrayList<Card>(hand_limit);
		for(int i=0; i<hand_limit; i++)
			_hand.add(new Card());

		try
		{
			//check if the hand is indeed valid (may not be as the cards are instantiate randomly).
			if(!isValidPokerHand())
			{
				isValidHand = false;
				throw new InvalidPokerHandException();
			}
			//if the hand is indeed valid, we can sort and evaluate it for comparison vs. other hands.
			else {
				isValidHand = true;
				sortAndEvaluate();
			}
		}
		catch(InvalidPokerHandException ex)
		{
			System.out.println("Invalid poker hand.");
		}
	}

	/**
	 * Instantiates a new poker hand with a set of card objects.
	 *
	 * @param cards the cards being placed into the poker hand.
	 */
	public PokerHand(Card ... cards)
	{
		//creates a hand based on the size of the input
		_hand = new ArrayList<Card>(cards.length);
		for(Card a : cards)
		{
			_hand.add(a);
		}
		
		try
		{
			//check if the hand is valid; may not be as this allows for less than, or more than, five cards
			//to be input to the constructor.
			if(!isValidPokerHand())
			{
				isValidHand = false;
				throw new InvalidPokerHandException();
			}
			else {
				isValidHand = true;
				sortAndEvaluate();
			}

		}
		catch(InvalidPokerHandException ex)
		{
			System.out.println("Invalid poker hand.");
		}
	}

	/**
	 * Instantiates a new poker hand based on an existing array list of cards.
	 *
	 * @param a the arraylist for the current hand.
	 */
	public PokerHand(ArrayList<Card> a) {
		_hand = new ArrayList<Card>(a);

		try
		{
			//checks if the new poker hand is valid
			if(!isValidPokerHand())
			{
				isValidHand = false;
				throw new InvalidPokerHandException();
			}
			else {
				isValidHand = true;
				sortAndEvaluate();
			}

		}
		catch(InvalidPokerHandException ex)
		{
			System.out.println("Invalid poker hand.");
		}
	}

	/* Methods */

	/**
	 * Gets the hand member variable.
	 *
	 * @return the hand member variable
	 */
	public ArrayList<Card> getHand()
	{
		return _hand;
	}

	/**
	 * Gets the hand limit.
	 *
	 * @return the hand limit
	 */
	public int getHandLimit()
	{
		return HAND_LIMIT;
	}
	
	/**
	 * Checks if it is a valid hand.
	 *
	 * @return true, if it is a valid hand
	 */
	public boolean isValidHand()
	{
		return isValidHand;
	}

	/**
	 * Sort and evaluate.
	 * Takes the current hand, checks if it is valid, and if it is, sorts and evaluates the hand.
	 * This generates a ranking value for the hand which can be used for comparison.
	 */
	public void sortAndEvaluate()
	{
		try {
			if(isValidHand) {
				//Print out the initial structure of the hand
				System.out.println("Pre-sort: " + this.toString());
				this.sortHand();
				//Sanity print that the sort worked
				System.out.println("Post-sort: " + this.toString());
				System.out.println(this.evaluateHand());
				//Final print for the structure of the hand, post evaluation (some of the evaluation methods move 
				//values around to make it easier to index the tie breakers
				System.out.println("Post-evaluate: " + this.toString());
			}
			else throw new InvalidPokerHandException("Invalid at sorting");
		}
		catch(InvalidPokerHandException ex)
		{
			System.out.println("Invalid poker hand when sorting and evaluating. ");
			isValidHand=false;
		}
		
	}

	/**
	 * Adds a new card to the hand.
	 *
	 * @param a the new card to be inserted into the hand
	 * @return true, if successful
	 */
	public boolean add(Card a) {
		//check that we are below the current hand limit
		if (_hand.size() < HAND_LIMIT) {
			_hand.add(a);
			try
			{
				//if we are back to the hand limit, 
				//we should check if the hand is valid
				//and sort and evaluate once again
				if(_hand.size() == HAND_LIMIT) {
					if(!isValidPokerHand())
					{
						throw new InvalidPokerHandException();
					}
					else {
						isValidHand=true;
						sortAndEvaluate();
					}
				}
			}
			catch(InvalidPokerHandException ex)
			{
				System.out.println("Invalid poker hand when adding card: " + a.get_value());
				isValidHand=false;

				return false;
			}

			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	 * Returns a human readable version of the poker hand
	 * Also uses the card.toString() method
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < HAND_LIMIT; i++) {
			builder.append(_hand.get(i).toString() + " ");
		}

		return builder.toString();
	}

	/**
	 * Sort hand using the Collections sort method.
	 * 
	 * Also uses the custom CardComparator class to define relationships between card objects.
	 */
	public void sortHand() {
		Collections.sort(_hand, new CardComparator());
	}

	/*
	 * Two trains of thought: evaluate from winning hands down (i.e. even if
	 * there are pairs, who cares, if there are also triples) or: evaluate
	 * pairs, and if they aren't pairs, we can skip directly to
	 * flushes/straights (don't need too evaluate triples and quadruples)
	 * 
	 * Taking the first option here; evaluate from winning hands down. returning once we find a good hand.
	 * Worst case: hand has nothing except a high card
	 * 
	 */
	
	/**
	 * Evaluate hand. Saves the current hand value as a HandValue enum object.
	 *
	 * @return a string indicating a human readable form of the hand
	 */
	public String evaluateHand() {
		//Check if we have a flush first and foremost
		if (isFlush()) {
			//If we have a flush, check if we have a straight
			if (isStraight()) {
				//if we do, then we have at least a straight flush.
				//but check if we have a royal flush
				//this would mean the ace is in the first position (based on the sorting order)
				if (getHighestValue().isEqualValue(Card.Value.ACE)) {
					set_handValue(HandValue.ROYAL_FLUSH);
					return "Royal Flush";
				} else {
					set_handValue(HandValue.STRAIGHT_FLUSH);
					return getHighestValue().get_value().getName()
							+ "-high Straight flush";
				}
			}
			//if we don't have a straight, then we must only have a flush
			else {
				set_handValue(HandValue.FLUSH);
				return "Flush";
			}
		}

		//if it is not a flush, lets check if it is a straight
		if (isStraight()) {
			set_handValue(HandValue.STRAIGHT);

			return getHighestValue().get_value().getName() + "-high Straight (no flush)";
		}

		//if it is not a flush, or straight, check for four of a kind
		if (isFourOfAKind()) {
			set_handValue(HandValue.FOUR_OF_A_KIND);

			evaluateIndices = new int[]{ 4, -1, -1, -1, -1 };

			return "Four of a kind with " + getHighestValue().get_value().getName() + " and a " + getLowestValue().get_value().getName();
		}

		//then check for three of a kind
		if (isTriples()) {
			//and a full house (i.e. this would mean that the last two values not including in the three of a kind
			//are a pair itself
			if (isFullHouse()) {
				set_handValue(HandValue.FULL_HOUSE);

				evaluateIndices = new int[]{ 1, 0, -1, -1, -1 };

				return "Full house with " + getMiddleValue().get_value().getName()
						+ "s and " + getLowestValue().get_value().getName() + "s";
			}

			//if not, we must have something like 8-8-8-A-J, so only a three of a kind
			else {
				set_handValue(HandValue.THREE_OF_A_KIND);
				evaluateIndices = new int[]{ 1, 0, 4, -1, -1 };

				return "Three of a kind with " + getMiddleValue().get_value().getName()
						+ "s";
			}
		}

		//if we do not have any of the above, check for a pair
		if (isPair()) {

			//if we have a pair, maybe we have a two pair
			if (isTwoPair()) {

				set_handValue(HandValue.TWO_PAIR);
				evaluateIndices = new int[]{ 0, 4, 2, -1, -1 };

				return "Two pair " + getHighestValue().get_value().getName()
						+ "s and " + getLowestValue().get_value().getName() + "s and a " + getMiddleValue().get_value().getName();
			}

			//otherwise we only have a pair
			else {
				set_handValue(HandValue.PAIR);
				evaluateIndices = new int[]{ 4, 0, 1, 2, -1 };

				return "One pair " + getLowestValue().get_value().getName()
						+ "s with high card " + getHighestValue().get_value().getName();
			}
		}

		//finally, if there is nothing else, then we only have a high card hand.
		set_handValue(HandValue.HIGH_CARD);
		return "High card with " + getHighestValue().get_value().getName();
	}

	/**
	 * Gets the middle value, used to break some tie breakers and to display some human readable text.
	 *
	 * @return the middle value
	 */
	private Card getMiddleValue(){
		return _hand.get(2);
	}

	/**
	 * Gets the highest value, used to break some tie breakers, and to display some human readable text.
	 *
	 * @return the highest value
	 */
	private Card getHighestValue() {
		return _hand.get(0);
	}

	/**
	 * Gets the lowest value, used to break some tie breakers, and to display some human readable text.
	 *
	 * @return the lowest value
	 */
	private Card getLowestValue() {
		return _hand.get(4);
	}

	/**
	 * Checks if the hand has a pair.
	 *
	 * @return true, if there is a pair
	 */
	private boolean isPair() {
		boolean isPair = false;

		//we check from the right to the left as the right has a lower initial value
		//this is helpful when we are presenting human readable text, as well as determining which are the tie breakers
		//if there is another pair, then we know it must be a higher pair than the first pair determined.
		for(int i=HAND_LIMIT-1; i>0; i--)
		{
			if(_hand.get(i).isEqualValue(_hand.get(i-1).get_value()))
			{
				isPair=true;

				_hand.add(_hand.remove(i));
				_hand.add(_hand.remove(i-1));

				return isPair;
			}
		}

		return isPair;
	}

	/**
	 * Checks if the hand is two pair, only used when isPair() is true.
	 *
	 * @return true, if the hand has two pair
	 */
	private boolean isTwoPair() {
		boolean isPair = false;
		//we only need to check the first three values in the hand as we know the cards in position 3 and 4 are the other pair.
		for(int i=0; i<HAND_LIMIT-3; i++)
		{
			if(_hand.get(i).isEqualValue(_hand.get(i+1).get_value()))
			{
				isPair=true;

				_hand.add(0, _hand.remove(i));				
				_hand.add(0, _hand.remove(i+1));

				return isPair;
			}
		}

		return isPair;
	}

	/**
	 * Checks if is full house.
	 *
	 * @return true, if is full house
	 */
	private boolean isFullHouse() {
		//isTriples moves the other two points to point 0 and point 4 (the corners of the array)
		if (_hand.get(0).isEqualValue(_hand.get(4).get_value()))
			return true;
		return false;
	}

	/**
	 * Checks if is triples.
	 *
	 * @return true, if is triples
	 */
	private boolean isTriples() {
		boolean isTriples = false;

		// Check in positions 1-3
		if (_hand.get(0).isEqualValue(_hand.get(1).get_value())
				&& _hand.get(1).isEqualValue(_hand.get(2).get_value())) {

			isTriples = true;

			//move the fourth position up to the beginning
			_hand.add(0, _hand.remove(3));
		}
		// Check in positions 2-4
		if (!isTriples) {
			if (_hand.get(1).isEqualValue(_hand.get(2).get_value())
					&& _hand.get(2).isEqualValue(_hand.get(3).get_value())) {
				isTriples = true;
				//_hand.add(_hand.remove(0));
			}
		}

		// Check in positions 3-5
		if (!isTriples) {
			if (_hand.get(2).isEqualValue(_hand.get(3).get_value())
					&& _hand.get(3).isEqualValue(_hand.get(4).get_value())) {
				isTriples = true;
				//_hand.add(_hand.remove(0));
				//_hand.add(_hand.remove(0));
				//move the second point back to the end (for tiebreaking purposes)
				_hand.add(_hand.remove(1));
			}
		}

		return isTriples;
	}

	/**
	 * Checks if is four of a kind.
	 *
	 * @return true, if is four of a kind
	 */
	private boolean isFourOfAKind() {
		boolean isFour = false;

		// found at positions 1-4
		isFour = _hand.get(0).isEqualValue(_hand.get(1).get_value())
				&& _hand.get(1).isEqualValue(_hand.get(2).get_value())
				&& _hand.get(2).isEqualValue(_hand.get(3).get_value());

		// Found at positions 2-5
		if (!isFour) {
			isFour = _hand.get(1).isEqualValue(_hand.get(2).get_value())
					&& _hand.get(2).isEqualValue(_hand.get(3).get_value())
					&& _hand.get(3).isEqualValue(_hand.get(4).get_value());

			if (isFour)
				_hand.add(_hand.remove(0));
		}

		return isFour;
	}

	/**
	 * Checks if is straight.
	 *
	 * @return true, if is straight
	 */
	private boolean isStraight() {
		boolean _return = false;

		// Fringe case: ace is high (A-5-4-3-2)

		if (_hand.get(0).isEqualValue(Card.Value.ACE) &&
				(_hand.get(1).isEqualValue(Card.Value.FIVE)
						&& _hand.get(2).isEqualValue(Card.Value.FOUR)
						&& _hand.get(3).isEqualValue(Card.Value.THREE)
						&& _hand.get(4).isEqualValue(Card.Value.TWO))) {
			_hand.add(_hand.remove(0));
			_return = true;
		}

		// check for regular straight
		else {
			_return = _hand.get(0).get_value().getValue() == _hand.get(1)
					.get_value().getValue() + 1
					&& _hand.get(1).get_value().getValue() == _hand.get(2)
					.get_value().getValue() + 1
					&& _hand.get(2).get_value().getValue() == _hand.get(3)
					.get_value().getValue() + 1
					&& _hand.get(3).get_value().getValue() == _hand.get(4)
					.get_value().getValue() + 1;
		}

		return _return;
	}

	/**
	 * Checks if is flush.
	 *
	 * @return true, if is flush
	 */
	private boolean isFlush() {
		return _hand.get(0).get_suit() == _hand.get(1).get_suit()
				&& _hand.get(0).get_suit() == _hand.get(2).get_suit()
				&& _hand.get(0).get_suit() == _hand.get(3).get_suit()
				&& _hand.get(0).get_suit() == _hand.get(4).get_suit();
	}

	/**
	 * Checks if is valid poker hand.
	 *
	 * @return true, if is valid poker hand
	 */
	public boolean isValidPokerHand() {

		//Create a hashMap using the cards and the cards hashCode
		HashMap<Integer, Card> hm = new HashMap<Integer, Card>();

		//it is not valid if we arent at the hand limit
		if(_hand.size() < HAND_LIMIT)
			return false;

		else {
			for(int i=0; i<_hand.size(); i++)
			{
				// it is also not valid if the hashmap contains an existing key
				//i.e. we are trying to put an identical card into the hashmap
				if(hm.containsKey(_hand.get(i).hashCode()))
					return false;
				else
					hm.put(_hand.get(i).hashCode(), _hand.get(i));
			}

			return true;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 * 0 == DRAW
	 * 1 == INVALID HAND
	 * >1 == HAND_ONE WINNER
	 * <0 == HAND_TWO WINNER
	 * 
	 * This comparison function does a few important things: first we check whether the two hands are valid, if not
	 * then we can stop immediately, and return that it is an invalid hand.
	 * 
	 * Next we determine the first comparison: that is, compare strictly the value of the hand itself (without considering the
	 * cards that make up that hand).
	 * 
	 * If we are still tied, then we need to loop through the tie breakers (i.e. check the cards themselves) to determine which
	 * hand is now the winner.
	 * 
	 * If we are still tied at the end of the loop (i.e. we made it to the end of the indices, or we made it to an index of -1,
	 * indicating that there are no further tie breakers, then we return that the hands are a tie.
	 * 
	 * Note that hands are assumed to be tied irrspective of their suits (i.e. there is not consideration for suits beating
	 * other suits.
	 */
	public int compareTo(PokerHand o) {

		if(this.isValidHand && o.isValidHand()) {
			int win = this.get_handValue().getValue() - o.get_handValue().getValue();
			int curInx = 0;

			while(win==0 && curInx < 5 && evaluateIndices[curInx] != -1) {

				win = this._hand.get(evaluateIndices[curInx]).get_value().getValue() - 
						o.getHand().get(evaluateIndices[curInx]).get_value().getValue();

				curInx ++;
			}
			//multiple by any number here to ensure that we don't return a 1, 
			//as we are saving the 1 for INVALID_HANDS
			return win*5;
		}

		else return 1;
	}

	/**
	 * Gets the _hand value.
	 *
	 * @return the _hand value
	 */
	public HandValue get_handValue() {
		return _handValue;
	}

	/**
	 * Sets the _hand value.
	 *
	 * @param _handValue the new _hand value
	 */
	public void set_handValue(HandValue _handValue) {
		this._handValue = _handValue;
	}

}
