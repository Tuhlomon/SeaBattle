package org.suai.network;

import java.io.*;

public class ClientReader extends Thread {

    BufferedReader in;
    PrintWriter out;
    String name;

    ClientReader(BufferedReader br, PrintWriter pw) {
        in = br;
        out = pw;
    }

    public void run() {
        try {
            String input;
            while (true) {
                input = in.readLine();
                out.println(input);
            }
        }
        catch(Exception e) {}
    }
}
