package co.edu.escuelaing.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Minimal HTTP request parser for a single request/connection.
 *
 * Autor: Alexandra Moreno Latorre
 */
public class HttpRequest {
    private final String method;
    private final String path;         // without the query string
    private final String version;
    private final Map<String,String> headers;
    private final Map<String,String> queryParams;
    private final String body;

    private HttpRequest(String method, String path, String version,
                        Map<String,String> headers, Map<String,String> queryParams, String body) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.headers = headers;
        this.queryParams = queryParams;
        this.body = body;
    }

    public String getMethod() { return method; }
    public String getPath() { return path; }
    public String getVersion() { return version; }
    public Map<String,String> getHeaders() { return Collections.unmodifiableMap(headers); }
    public String getHeader(String name) { return headers.getOrDefault(name, null); }
    public String getBody() { return body; }

    public String getParam(String name) { return queryParams.get(name); }
    public Map<String,String> getQueryParams() { return Collections.unmodifiableMap(queryParams); }

    public static HttpRequest parse(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

        // Request line
        String requestLine = reader.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            throw new IOException("Empty request");
        }
        String[] parts = requestLine.split(" ");
        if (parts.length < 3) throw new IOException("Malformed request line: " + requestLine);

        String method = parts[0];
        String fullPath = parts[1];
        String version = parts[2];

        String pathOnly = fullPath;
        Map<String,String> queryParams = new HashMap<>();
        int qIdx = fullPath.indexOf('?');
        if (qIdx >= 0) {
            pathOnly = fullPath.substring(0, qIdx);
            String qs = fullPath.substring(qIdx + 1);
            for (String pair : qs.split("&")) {
                if (pair.isEmpty()) continue;
                String[] kv = pair.split("=", 2);
                String k = urlDecode(kv[0]);
                String v = kv.length > 1 ? urlDecode(kv[1]) : "";
                queryParams.put(k, v);
            }
        }

        // Headers
        Map<String,String> headers = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            int colon = line.indexOf(':');
            if (colon > 0) {
                String name = line.substring(0, colon).trim();
                String value = line.substring(colon + 1).trim();
                headers.put(name, value);
            }
        }

        // Body (simple: read Content-Length bytes if present)
        String body = "";
        String cl = headers.get("Content-Length");
        if (cl != null) {
            try {
                int len = Integer.parseInt(cl);
                char[] buf = new char[len];
                int read = 0;
                while (read < len) {
                    int r = reader.read(buf, read, len - read);
                    if (r == -1) break;
                    read += r;
                }
                body = new String(buf, 0, read);
            } catch (NumberFormatException ignored) {}
        }

        return new HttpRequest(method, pathOnly, version, headers, queryParams, body);
    }

    private static String urlDecode(String s) {
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }
}
