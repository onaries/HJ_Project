package com.imagelab;

/**
 * Created by Nanodir on 2016-11-26.
 */
public interface MessageHandler {
    public void onReceive(Connection connection, String message);
}
