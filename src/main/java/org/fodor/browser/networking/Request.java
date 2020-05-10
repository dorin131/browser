package org.fodor.browser.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
}
