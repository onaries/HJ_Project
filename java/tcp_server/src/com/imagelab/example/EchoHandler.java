package com.imagelab.example;

import com.imagelab.Connection;
import com.imagelab.MessageHandler;

/**
 * Created by Nanodir on 2016-11-26.
 */
class EchoHandler implements MessageHandler {
    @Override
    public void onReceive(Connection connection, String message) {
        connection.println(message);
    }
}
