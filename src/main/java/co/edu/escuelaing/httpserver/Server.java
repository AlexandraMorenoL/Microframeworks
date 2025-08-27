package co.edu.escuelaing.httpserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Multithreaded HTTP server that delegates to Router.
 *
 * Autor: Alexandra Moreno Latorre
 */
public class Server {
    private final int port;
    private volatile boolean running = false;
    private ServerSocket serverSocket;
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public Server(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        System.out.println("HTTP Server listening on port " + port);
        while (running) {
            Socket client = serverSocket.accept();
            pool.submit(() -> handleClient(client));
        }
    }

    public void stop() throws IOException {
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) serverSocket.close();
        pool.shutdownNow();
    }

    private void handleClient(Socket client) {
        try (Socket c = client;
             InputStream in = c.getInputStream();
             OutputStream out = c.getOutputStream()) {

            HttpRequest req = HttpRequest.parse(in);
            HttpResponse res = new HttpResponse(out);

            boolean handled = Router.handle(req, res);
            if (!handled) {
                res.sendNotFound();
            }
        } catch (Exception e) {
            try {
                HttpResponse res = new HttpResponse(client.getOutputStream());
                res.sendServerError(e);
            } catch (IOException ignored) {}
        }
    }
}
