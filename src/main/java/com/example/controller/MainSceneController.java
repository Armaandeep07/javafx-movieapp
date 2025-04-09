package com.example.controller;

import com.example.model.Movie;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.net.URI;
import java.net.http.*;
import java.util.ArrayList;
import java.util.List;

public class MainSceneController {

    @FXML private ListView<Movie> movieListView;
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @FXML
    public void initialize() {
        fetchMovies("Avengers");
    }

    private void fetchMovies(String query) {
        String url = "https://www.omdbapi.com/?apikey=Dpr7dIfnNJyh8Wc4n6nJ5A==2y6GUJ2n11adNV4z&s=" + query;

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
              .thenApply(HttpResponse::body)
              .thenAccept(this::parseResults);
    }

    private void parseResults(String json) {
        JsonObject obj = gson.fromJson(json, JsonObject.class);
        JsonArray array = obj.getAsJsonArray("Search");

        List<Movie> movies = new ArrayList<>();
        array.forEach(e -> movies.add(gson.fromJson(e, Movie.class)));

        javafx.application.Platform.runLater(() -> {
            movieListView.getItems().setAll(movies);
        });
    }

    @FXML
    public void onMovieClicked() throws Exception {
        Movie selected = movieListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/detail_scene.fxml"));
        Scene scene = new Scene(loader.load(), 500, 400);

        DetailSceneController controller = loader.getController();
        controller.loadDetails(selected.getImdbID());

        Stage stage = (Stage) movieListView.getScene().getWindow();
        stage.setScene(scene);
    }
}
