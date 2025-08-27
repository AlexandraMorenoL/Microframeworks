package co.edu.escuelaing.httpserver;

/**
 * Simple entry point that starts a default server if needed.
 *
 * Autor: Alexandra Moreno Latorre
 */
public class HttpServer {
    public static void main(String[] args) throws Exception {
        int port = 35000;
        if (args.length > 0) {
            try { port = Integer.parseInt(args[0]); } catch (NumberFormatException ignored) {}
        }
        Server server = new Server(port);
        Router.staticfiles("webroot");
        server.start();
    }
}
