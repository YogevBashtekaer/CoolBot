package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public class JokeAPI {
    private String text;

    public JokeAPI() {
        this.text = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String response = (Unirest.get("https://official-joke-api.appspot.com/random_joke").asString().getBody());
            Joke joke = objectMapper.readValue(response, Joke.class);
            this.text = joke.getData();
        }catch (UnirestException e){
            System.out.println("problem with joke");
        }catch (Exception e){
            System.out.println("problem with joke");
        }
    }

    public String getText() {
        return text;
    }

    public static void main(String[] args) {
        JokeAPI jokeAPI = new JokeAPI();
    }
}
