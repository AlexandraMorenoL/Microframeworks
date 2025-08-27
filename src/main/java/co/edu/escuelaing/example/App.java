package co.edu.escuelaing.example;

/**
 *
 * Autor: Alexandra Moreno Latorre
 */

import co.edu.escuelaing.httpserver.Router;
import co.edu.escuelaing.httpserver.Server;

public class App {
    public static void main(String[] args) throws Exception {
        Router.staticfiles("./webroot");

        Router.get("/hello", (req, res) -> "Hellooooo! " + (req.getParam("name") != null ? req.getParam("name") : "World"));
        Router.get("/pi", (req, res) -> String.valueOf(Math.PI));

        Server server = new Server(35000);
        server.start();
    }
}
