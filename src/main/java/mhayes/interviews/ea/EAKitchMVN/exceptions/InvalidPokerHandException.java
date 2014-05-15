package mhayes.interviews.ea.EAKitchMVN.exceptions;

/**
 * The Class InvalidPokerHandException.
 */
public class InvalidPokerHandException extends Exception {

    //Parameterless Constructor
    /**
     * Instantiates a new invalid poker hand exception.
     */
    public InvalidPokerHandException() {}

    //Constructor that accepts a message
    /**
     * Instantiates a new invalid poker hand exception.
     *
     * @param message the message to be displayed to the user when the exception is thrown
     */
    public InvalidPokerHandException(String message)
    {
       super("Invalid poker hand: " + message);
    }
}
