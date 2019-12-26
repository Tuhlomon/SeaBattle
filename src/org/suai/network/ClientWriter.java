package org.suai.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public class ClientWriter extends Thread {

    BufferedReader in;
    BufferedWriter out;

    ClientWriter(BufferedReader br, BufferedWriter bw) {
        in = br;
        out = bw;
    }

    public void run() {
        try {
            String input;
            while(true) {
                input = in.readLine();
                out.write(input);
            }
        }
        catch(Exception e) {}
    }
}
