package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Scanner;

public class GoogleSearch {
    private String outPut;
    private String NO_FOUND = "did not match any documents.";
    public GoogleSearch(String searchWord) {
        try {
            String START_OF_STRING = "result for " + searchWord +"\n";
            this.outPut = START_OF_STRING;
            Document page = Jsoup.connect("https://www.google.com/search?q=" +
                    URLEncoder.encode(searchWord, "UTF-8")).get();

            Elements searchResults = page.select("div.tF2Cxc");


            for (Element element : searchResults) {
                Element elementTitle = element.selectFirst("h3");
                String title = elementTitle != null ? elementTitle.text() : "no title found";

                Element urlElement = element.selectFirst("a");
                String url = urlElement != null ? urlElement.attr("href") : "no url found";

                this.outPut += title + "  ---->  " + url +"\n\n";
            }
            if (this.outPut.equals(START_OF_STRING)){
                this.outPut = NO_FOUND;
            }
        }catch (IOException e){
            System.out.println("problem with google search");
        }

    }

    public String getOutPut() {
        return outPut;
    }

    public static void main(String[] args) {
        GoogleSearch googleSearch = new GoogleSearch("hero");
        System.out.println(googleSearch.outPut);
    }

}
