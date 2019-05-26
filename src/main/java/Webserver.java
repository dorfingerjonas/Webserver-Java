import java.io.*;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;
import java.nio.file.*;

public class Webserver {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new RootHandler());
        server.createContext("/getimage", new ImageHandler());
        server.createContext("/plane.png", new PlaneHandler());
        server.createContext("/style.css", new StyleHandler());
        server.createContext("/favicon.ico", new FavIconHandler());
        server.start();
        System.out.println("Started");
    }

    static class RootHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) {
            try {
                sendResponse(200, Files.readAllBytes(Paths.get("public/html/index.html")), "text/html", t);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    static class ImageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) {
            try {
                sendResponse(200, Files.readAllBytes(Paths.get("public/img/img.gif")), "image/gif", t);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    static class PlaneHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) {
            try {
                sendResponse(200, Files.readAllBytes(
                        Paths.get("public/img/plane.png")), "image/png", t);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    static class StyleHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) {
            try {
                sendResponse(200, Files.readAllBytes(
                        Paths.get("public/style/style.css")), "text/css", t);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    static class FavIconHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) {
            try {
                sendResponse(200, Files.readAllBytes(Paths.get("public/img/favicon.ico")), "image/x-icon", t);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private static void sendResponse(int status, byte[] response, String contentType, HttpExchange t) throws IOException {
        Headers responseHeader = t.getResponseHeaders();
        responseHeader.add("Content-Type", contentType);
        t.sendResponseHeaders(status, response.length);

        OutputStream os = t.getResponseBody();
        os.write(response);
        os.close();
    }
}