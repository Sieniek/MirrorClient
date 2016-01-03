package com.jappka.mirrorclient.networkChecking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

/**
 * Created by psienkiewicz on 02/01/16.
 */
public class NetworkPing implements Callable<Boolean> {

    private final int PORT = 4567;
    private String hostName;

    public NetworkPing(String hostName){
        this.hostName = hostName;
    }

    @Override
    public Boolean call() throws Exception {
        Boolean isAvailable = false;
        Socket testSocket;

        try{
            testSocket = new Socket(hostName, PORT);
            testSocket.close();
            isAvailable = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return isAvailable;
    }
}
