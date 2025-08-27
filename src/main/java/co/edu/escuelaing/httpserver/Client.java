package co.edu.escuelaing.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Minimal HTTP GET client for quick manual tests.
 *
 * Autor: Alexandra Moreno Latorre
 */
public class Client {
    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : "127.0.0.1";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 35000;
        String path = args.length > 2 ? args[2] : "/";

        try (Socket s = new Socket(host, port)) {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8));
            pw.print("GET " + path + " HTTP/1.1\r\n");
            pw.print("Host: " + host + "\r\n");
            pw.print("Connection: close\r\n");
            pw.print("\r\n");
            pw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
