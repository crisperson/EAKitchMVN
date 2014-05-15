package mhayes.interviews.ea.EAKitchMVN.comparators;

import java.util.Comparator;

import mhayes.interviews.ea.EAKitchMVN.models.Card;

/**
 * The Class CardComparator.
 */
public class CardComparator implements Comparator<Card> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 * 
	 * We multiple the output of the comparator to ensure the Cards are in reverse order. 
	 * i.e. we want A-K-Q-J-10, not 10-J-Q-K-A. This makes it better to pull the highest value.
	 */
	public int compare(Card o1, Card o2) {
        return o1.compareTo(o2) * - 1;
    }

}
