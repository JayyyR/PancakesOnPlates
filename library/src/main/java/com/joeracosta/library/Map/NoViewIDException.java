package com.joeracosta.library.Map;

/**
 * Created by Joe on 1/9/2016.
 */
public class NoViewIDException extends Exception {

    //Parameterless Constructor
    public NoViewIDException() {}

    //Constructor that accepts a message
    public NoViewIDException(String message)
    {
        super(message);
    }
}
