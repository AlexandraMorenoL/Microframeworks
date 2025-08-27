package co.edu.escuelaing.httpserver;

/**
 * Functional handler for HTTP routes.
 * The handler may return a String as the response body. If it returns null,
 * the handler must write to the HttpResponse directly.
 *
 * Autor: Alexandra Moreno Latorre
 */
@FunctionalInterface
public interface FunctionalHandler {
    String handle(HttpRequest request, HttpResponse response);
}
