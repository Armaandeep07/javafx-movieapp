package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.net.http.*;
import java.net.URI;
import com.google.gson.*;

public class DetailSceneController {
    @FXML private ImageView posterView;
    @FXML private Label titleLabel, yearLabel, directorLabel, plotLabel;

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public void loadDetails(String imdbID) {
        String url = "https://www.omdbapi.com/?apikey=Dpr7dIfnNJyh8Wc4n6nJ5A==2y6GUJ2n11adNV4z&i=" + imdbID;

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
              .thenApply(HttpResponse::body)
              .thenAccept(this::showDetails);
    }

    private void showDetails(String json) {
        JsonObject obj = gson.fromJson(json, JsonObject.class);

        javafx.application.Platform.runLater(() -> {
            titleLabel.setText("Title: " + obj.get("Title").getAsString());
            yearLabel.setText("Year: " + obj.get("Year").getAsString());
            directorLabel.setText("Director: " + obj.get("Director").getAsString());
            plotLabel.setText("Plot: " + obj.get("Plot").getAsString());
            posterView.setImage(new Image(obj.get("Poster").getAsString()));
        });
    }

    @FXML
    public void onBackClicked() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main_scene.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        Stage stage = (Stage) posterView.getScene().getWindow();
        stage.setScene(scene);
    }
}
