package com.retoalura.connectionAPI;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConnetionAPI {
    private static ConnetionAPI instance;
    private HttpClient cliente;
    private String api;
    private String idConsumer;

    private String response;

    private ConnetionAPI() {
        api = "https://v6.exchangerate-api.com/v6/";
        idConsumer = "fd692f429c860d57c4486985";
        cliente = HttpClient.newHttpClient();
        response = "";
    }

    public static ConnetionAPI getInstance(){
        if(instance == null){
            return new ConnetionAPI();
        }
        return instance;
    }

    private String consultaApi(String endpoint) throws IOException, InterruptedException {
        URI url = URI.create(api + idConsumer + "/%s".formatted(endpoint));
        HttpRequest request = HttpRequest.newBuilder().uri(url).build();
        HttpResponse<String> respuesta = cliente.send(request,HttpResponse.BodyHandlers.ofString());
        return respuesta.body();
    }

    public String consulta (String moneda, String cambio) throws IOException, InterruptedException {
        response = consultaApi("pair/%s/%s".formatted(moneda, cambio));
        return response;
    }

    public String consulta (String moneda) throws IOException, InterruptedException {
        response = consultaApi("latest/%s".formatted(moneda));
        return response;
    }

    public String consulta (String moneda, String cambio, double cantidad) throws IOException, InterruptedException {
        response = consultaApi("pair/%s/%s/%f".formatted(moneda, cambio,cantidad));
        return response;
    }
    public String consultaMonedas() throws IOException, InterruptedException {
        response = consultaApi("codes");
        return response;
    }
    public String getResponse() {
        return response;
    }
}
