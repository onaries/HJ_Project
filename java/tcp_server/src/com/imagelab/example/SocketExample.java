package com.imagelab.example;

import com.imagelab.SocketServer;

import java.io.IOException;

/**
 * Created by Nanodir on 2016-11-26.
 */
class SocketExample {
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        SocketServer server = new SocketServer(5556, new EchoHandler());
        System.out.println("Server starts.");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


//        SocketClient client = new SocketClient(InetAddress.getLocalHost(), 5556);
//        client.println("Hello!");
//        System.out.println(client.readLine());
//
//        client.close();
        server.close();
    }
}
