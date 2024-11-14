package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

public class PokemonAPI {
    private String pokemonData;
    public PokemonAPI(String name) {
        this.pokemonData = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String response = (Unirest.get("https://pokeapi.co/api/v2/pokemon/"+name).asString().getBody());
            Pokemon pokemon = objectMapper.readValue(response,Pokemon.class);
            this.pokemonData = pokemon.getData();

        } catch (UnirestException e) {
            System.out.println("problem with pokemon API1");
        } catch(IOException e){
            System.out.println("problem with pokemon API2");
            getPokemonData();
        }
    }

    public String getPokemonData() {
        if (this.pokemonData.equals("")){
            return "This Pokemon cannot be found";
        }
        return pokemonData;
    }

    public static void main(String[] args) {
        //PokemonAPI pokemonAPI = new PokemonAPI("6");
    }
}
