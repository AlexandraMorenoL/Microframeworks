package co.edu.escuelaing.httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Minimal HTTP response builder.
 *
 * Autor: Alexandra Moreno Latorre
 */
public class HttpResponse {
    private final OutputStream outputStream;
    private int status = 200;
    private String reason = "OK";
    private final Map<String,String> headers = new HashMap<>();
    private boolean sent = false;

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
        headers.put("Server", "MiniHttp/1.0");
        headers.put("Connection", "close");
    }

    public void setStatus(int status, String reason) {
        this.status = status;
        this.reason = reason;
    }

    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void send(String body, String contentType) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        setHeader("Content-Type", contentType + "; charset=utf-8");
        setHeader("Content-Length", Integer.toString(bytes.length));
        writeHead();
        outputStream.write(bytes);
        outputStream.flush();
        sent = true;
    }

    public void sendBytes(byte[] bytes, String contentType) throws IOException {
        setHeader("Content-Type", contentType);
        setHeader("Content-Length", Integer.toString(bytes.length));
        writeHead();
        outputStream.write(bytes);
        outputStream.flush();
        sent = true;
    }

    public void sendNotFound() throws IOException {
        setStatus(404, "Not Found");
        send("<h1>404 Not Found</h1>", "text/html");
    }

    public void sendServerError(Exception e) throws IOException {
        setStatus(500, "Internal Server Error");
        send("<h1>500 Internal Server Error</h1><pre>" + escape(e.toString()) + "</pre>", "text/html");
    }

    private void writeHead() throws IOException {
        if (sent) return;
        PrintWriter pw = new PrintWriter(outputStream, false, StandardCharsets.UTF_8);
        pw.printf("HTTP/1.1 %d %s\r\n", status, reason);
        for (Map.Entry<String,String> h : headers.entrySet()) {
            pw.printf("%s: %s\r\n", h.getKey(), h.getValue());
        }
        pw.print("\r\n");
        pw.flush();
    }

    private static String escape(String s) {
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
    }
}
