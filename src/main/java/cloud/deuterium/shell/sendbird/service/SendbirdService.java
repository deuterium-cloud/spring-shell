package cloud.deuterium.shell.sendbird.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Created by Milan Stojkovic 12-Mar-2022
 */

@Service
public class SendbirdService {

    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private final HttpClient client = HttpClient.newHttpClient();
    private static final String API_TOKEN_KEY = "Api-Token";
    private String token;
    private String baseUrl;
    private boolean tokenReady = false;
    private boolean urlReady = false;

    @Value("${sendbird.api.url.users}")
    private String usersUrl;

    public void setApiToken(String token) {
        this.token = token;
        this.tokenReady = true;
        System.out.println("Api-Token added: " + token);
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        this.urlReady = true;
        System.out.println("Sendbird Base url added: " + baseUrl);
    }

    public void call(String url) {
        System.out.println("Call url: " + baseUrl + url);
        HttpRequest request = HttpRequest.newBuilder()
                .header(API_TOKEN_KEY, token)
                .uri(URI.create(baseUrl + url))
                .build();
        try {
            String response = client.send(request, HttpResponse.BodyHandlers.ofString())
                    .body();
            Object jsonObject = mapper.readValue(response, Object.class);
            String json = mapper.writeValueAsString(jsonObject);
            System.out.println(json);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void getUsers() {
        this.call(usersUrl);
    }

    public void getUser(String userId) {
        this.call(usersUrl + "/" + userId);
    }

    public boolean isReady() {
        return tokenReady && urlReady;
    }
}
