package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class RiverScraping {
    private ArrayList<River> rivers;
    public RiverScraping() {
        rivers = new ArrayList<>();
        String url = "https://en.wikipedia.org/wiki/List_of_river_systems_by_length";
        try {
            Document document = Jsoup.connect(url).get();
            Elements riverRows = document.select("table.wikitable tbody tr");

            for (Element row : riverRows) {
                Elements columns = row.select("td");
                if (columns.size() >= 3) {
                    Element nameCell = row.selectFirst("td:nth-child(2)");
                    String riverName = nameCell.text();

                    String riverLengthKm = columns.get(1).text();
                    if (checkData(riverLengthKm)) {
                        riverLengthKm = extractInformation(riverLengthKm);
                    }

                    String riverLengthMiles = columns.get(2).text();
                    if (checkData(riverLengthMiles)) {
                        riverLengthMiles = extractInformation(riverLengthMiles);
                    }

                    String drainageArea = columns.get(3).text();
                    if (checkData(drainageArea)) {
                        drainageArea = extractInformation(drainageArea);
                    }

                    River river = new River();
                    river.setName(riverName);
                    river.setLengthKm(Integer.parseInt(riverLengthKm));
                    river.setLengthMiles(Integer.parseInt(riverLengthMiles));

                    if (!drainageArea.equals("")) {
                        river.setDrainageArea(Integer.parseInt(drainageArea));
                    }

                    this.rivers.add(river);

                }
            }
        }catch (Exception e){
            System.out.println("problem with river Scraping");
        }
    }
    public String getData(int num){
        String result = "";
        for (int i = 0; i < num; i++) {
            result+= i+1+") "+this.rivers.get(i)+"\n";
        }
        return result;
    }

    private boolean checkData(String data){
        return data.contains("[") || data.contains(",") || data.contains("*")
                || data.contains("*") || data.contains("(") || data.contains(".");
    }

    private String extractInformation(String data) {
        String[] temp = data.split("\\(");
        String[] temp2 =temp[0].split("\\.");
        String[] parts = temp2[0].split("\\[");
        String result = parts[0].replace(",", "");
        result = result.replace("*","");
        result = result.replace(" ","");

        return result;
    }
}