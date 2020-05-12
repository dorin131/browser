package org.fodor.browser.networking;

import javax.swing.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class Request {
    public enum Method {
        GET,
        POST,
    }

    public CompletableFuture<HttpResponse<String>> http(Method method, String address) throws URISyntaxException {
        URI uri = new URI(address);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(uri)
                .GET()
                .headers("Foo", "foovalue", "Bar", "barvalue")
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    }

    public CompletableFuture<HttpResponse<String>> makeGetRequest(String address) {
        CompletableFuture<HttpResponse<String>> response = new CompletableFuture<>();
        try {
            response = http(Request.Method.GET, address);
        } catch (URISyntaxException ex) {
            JOptionPane.showMessageDialog(null, "Invalid address: " + address);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getLocalizedMessage());
        }
        return response;
    }
}
