package com.chrissvg.miapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

@Service
public class SupabaseStorageService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.key}")
    private String supabaseKey;

    @Value("${supabase.bucket}")
    private String bucket;

    public String subirImagen(MultipartFile archivo) throws Exception {
        String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(supabaseUrl + "/storage/v1/object/" + bucket + "/" + nombreArchivo))
                .header("Authorization", "Bearer " + supabaseKey)
                .header("Content-Type", archivo.getContentType())
                .POST(HttpRequest.BodyPublishers.ofByteArray(archivo.getBytes()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200 || response.statusCode() == 201) {
            return supabaseUrl + "/storage/v1/object/public/" + bucket + "/" + nombreArchivo;
        } else {
            throw new Exception("Error al subir imagen: " + response.body());
        }
    }
}