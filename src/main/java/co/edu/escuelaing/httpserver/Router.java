package co.edu.escuelaing.httpserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple router with GET routes and static file serving.
 *
 * Autor: Alexandra Moreno Latorre
 */
public class Router {

    private static final Map<String, FunctionalHandler> getRoutes = new HashMap<>();
    private static String staticRoot = null;

    public static void staticfiles(String root) {
        staticRoot = root;
    }

    public static void get(String path, FunctionalHandler handler) {
        getRoutes.put(path, handler);
    }

    static boolean handle(HttpRequest req, HttpResponse res) throws IOException {
        // Exact GET routes
        if ("GET".equalsIgnoreCase(req.getMethod())) {
            FunctionalHandler handler = getRoutes.get(req.getPath());
            if (handler != null) {
                String out = handler.handle(req, res);
                if (out != null && !out.isEmpty()) {
                    res.send(out, "text/plain");
                }
                return true;
            }
        }

        // Static files fallback
        if (staticRoot != null && "GET".equalsIgnoreCase(req.getMethod())) {
            String p = req.getPath().equals("/") ? "/index.html" : req.getPath();
            Path file = Path.of(staticRoot).resolve("." + p).normalize();
            File f = file.toFile();
            System.out.println("Buscando archivo: " + file.toAbsolutePath());
            if (f.exists() && f.isFile()) {
                byte[] bytes = Files.readAllBytes(file);
                String ct = guessContentType(file.getFileName().toString());
                res.sendBytes(bytes, ct);
                return true;
            }
        }

        return false; // <--- si no se encuentra nada
    }

    private static String guessContentType(String filename) {
        String name = filename.toLowerCase();
        if (name.endsWith(".html") || name.endsWith(".htm")) {
            return "text/html; charset=utf-8";
        }
        if (name.endsWith(".css")) {
            return "text/css; charset=utf-8";
        }
        if (name.endsWith(".js")) {
            return "application/javascript; charset=utf-8";
        }
        if (name.endsWith(".json")) {
            return "application/json; charset=utf-8";
        }
        if (name.endsWith(".png")) {
            return "image/png";
        }
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (name.endsWith(".gif")) {
            return "image/gif";
        }
        if (name.endsWith(".svg")) {
            return "image/svg+xml";
        }
        return "application/octet-stream";
    }
}
