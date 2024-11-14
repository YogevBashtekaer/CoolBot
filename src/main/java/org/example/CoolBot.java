package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.apache.commons.io.FileUtils.getFile;

public class CoolBot extends TelegramLongPollingBot {

    private HashMap<Long,User> userHashMap;
    final String GOOGLE_SEARCH = "google search";
    final String RIVER = "river";
    final String POKEMON = "pokemon";
    final String JOKE = "joke";
    final String PHOTO = "photo";
    final String BLACK_AND_WHITE_PHOTO = "black And White";
    final String MIRROR_PHOTO = "mirror";
    final String GRAY_SCALE_PHOTO = "gray scale";
    final String SHUFFLE_COLORS_PHOTO = "shuffle colors";
    final String OPPOSITE_COLORS_PHOTO = "opposite colors";
    final String DRAW_EDGES_PHOTO = "draw edges";
    public CoolBot() {
        this.userHashMap = new HashMap<>();
    }

    @Override
    public String getBotToken() {
        return "6510623166:AAEo6mzDGa0eyYJI2fE0iYkCs73z12FVK1w";
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        long chatID = getChatId(update);

        User user = this.userHashMap.get(chatID);
        message.setChatId(chatID);
        if(user== null){
            String name = update.getMessage().getFrom().getFirstName();
            this.userHashMap.put(chatID,new User(chatID,name));
            message.setText("hello "+name+"\nhere the options:");
            message.setReplyMarkup(mainMenu());
        }else if(update.hasCallbackQuery()){
            System.out.println("enter else");
            if (update.hasCallbackQuery()){
                System.out.println("enter has call back query");
                if(update.getCallbackQuery().getData().equals(GOOGLE_SEARCH)){
                    System.out.println(GOOGLE_SEARCH);
                    user.setUserPress(UserPress.GOOGLE_SEARCH);
                    message.setText("enter word for search");
                } else if (update.getCallbackQuery().getData().equals(RIVER)) {
                    System.out.println(RIVER);
                    user.setUserPress(UserPress.RIVER);
                    message.setReplyMarkup(riverMenu());
                    message.setText("choose how many rivers you want");
                } else if (update.getCallbackQuery().getData().equals("1") || update.getCallbackQuery().getData().equals("3")
                        || update.getCallbackQuery().getData().equals("10") || update.getCallbackQuery().getData().equals("15")) {
                    RiverScraping riverScraping = new RiverScraping();
                    message.setText(riverScraping.getData(Integer.parseInt(update.getCallbackQuery().getData())));
                    user.setUserPress(UserPress.DEFAULT);
                    message.setReplyMarkup(mainMenu());
                } else if (update.getCallbackQuery().getData().equals(POKEMON)) {
                    message.setText("enter a "+POKEMON +" name or id");
                    user.setUserPress(UserPress.POKEMON);
                } else if (update.getCallbackQuery().getData().equals(JOKE)) {
                    JokeAPI jokeAPI = new JokeAPI();
                    System.out.println(JOKE);
                    message.setText(jokeAPI.getText());
                    message.setReplyMarkup(mainMenu());
                } else if (update.getCallbackQuery().getData().equals(PHOTO)) {
                    System.out.println("photo");
                    message.setReplyMarkup(photoMenu());
                    message.setText("choose the effect");

                } else if (update.getCallbackQuery().getData().equals(MIRROR_PHOTO) || update.getCallbackQuery().getData().equals(BLACK_AND_WHITE_PHOTO)
                        || update.getCallbackQuery().getData().equals(GRAY_SCALE_PHOTO) || update.getCallbackQuery().getData().equals(SHUFFLE_COLORS_PHOTO)
                        || update.getCallbackQuery().getData().equals(OPPOSITE_COLORS_PHOTO) || update.getCallbackQuery().getData().equals(DRAW_EDGES_PHOTO)) {
                    String userChoice = update.getCallbackQuery().getData();
                    switch (userChoice) {
                        case MIRROR_PHOTO :
                            user.setUserPhotoOption(PhotoOptions.MIRROR);
                            System.out.println(MIRROR_PHOTO);
                            break;
                        case BLACK_AND_WHITE_PHOTO:
                            user.setUserPhotoOption(PhotoOptions.BLACK_AND_WHITE);
                            System.out.println(BLACK_AND_WHITE_PHOTO);
                            break;
                        case GRAY_SCALE_PHOTO:
                            user.setUserPhotoOption(PhotoOptions.GRAY_SCALE);
                            System.out.println(GRAY_SCALE_PHOTO);
                            break;
                        case SHUFFLE_COLORS_PHOTO:
                            user.setUserPhotoOption(PhotoOptions.SHUFFLE_COLORS);
                            System.out.println(SHUFFLE_COLORS_PHOTO);
                            break;
                        case OPPOSITE_COLORS_PHOTO:
                            user.setUserPhotoOption(PhotoOptions.OPPOSITE_COLORS);
                            System.out.println(OPPOSITE_COLORS_PHOTO);
                            break;
                        case DRAW_EDGES_PHOTO:
                            user.setUserPhotoOption(PhotoOptions.DRAW_EDGES);
                            System.out.println(DRAW_EDGES_PHOTO);
                            break;
                        default :
                            user.setUserPhotoOption(PhotoOptions.DEFAULT);
                            System.out.println("default");;
                            break;
                    }
                    message.setText("please send the photo");

                }
            }
        } else if (update.getMessage().hasPhoto()) {


            List<PhotoSize> photos = update.getMessage().getPhoto();
            PhotoSize largestPhoto = photos.get(photos.size() - 1); // The largest photo is usually at the end of the list

            String fileId = largestPhoto.getFileId();

            GetFile getFile = new GetFile();
            getFile.setFileId(fileId);

            try {
                org.telegram.telegrambots.meta.api.objects.File file = execute(getFile);
                InputStream inputStream = new URL(file.getFileUrl(getBotToken())).openStream();

                BufferedImage bufferedImage = ImageIO.read(inputStream);
                ImageProcessing imageProcessing;
                switch (user.getUserPhotoOption()){
                    case MIRROR :
                        imageProcessing = new ImageProcessing(bufferedImage,PhotoOptions.MIRROR);
                        break;
                    case BLACK_AND_WHITE:
                        imageProcessing = new ImageProcessing(bufferedImage,PhotoOptions.BLACK_AND_WHITE);
                        break;
                    case GRAY_SCALE:
                        imageProcessing = new ImageProcessing(bufferedImage,PhotoOptions.GRAY_SCALE);
                        break;
                    case SHUFFLE_COLORS:
                        imageProcessing = new ImageProcessing(bufferedImage,PhotoOptions.SHUFFLE_COLORS);
                        break;
                    case OPPOSITE_COLORS:
                        imageProcessing = new ImageProcessing(bufferedImage,PhotoOptions.OPPOSITE_COLORS);
                        break;
                    case DRAW_EDGES:
                        imageProcessing = new ImageProcessing(bufferedImage,PhotoOptions.DRAW_EDGES);
                        break;
                    default:
                        imageProcessing = new ImageProcessing(bufferedImage,PhotoOptions.DEFAULT);
                        break;
                }
                user.setUserPhotoOption(PhotoOptions.DEFAULT);

                bufferedImage = imageProcessing.getBufferedImage();

                // Convert the bufferedImage to InputFile
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", outputStream);
                InputStream imageInputStream = new ByteArrayInputStream(outputStream.toByteArray());
                InputFile processedImageInputFile = new InputFile(imageInputStream, "processed_image.png");

                // Prepare the response message
                SendPhoto sendPhotoResponse = new SendPhoto();
                sendPhotoResponse.setChatId(update.getMessage().getChatId());
                sendPhotoResponse.setPhoto(processedImageInputFile);
                sendPhotoResponse.setReplyMarkup(mainMenu());

                // Send the processed image to the user
                execute(sendPhotoResponse);
            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
            }


        } else {
            if (user.getUserPress().equals(UserPress.GOOGLE_SEARCH)) {
                GoogleSearch googleSearch = new GoogleSearch(""+update.getMessage().getText());
                message.setText(googleSearch.getOutPut());
                user.setUserPress(UserPress.DEFAULT);
                message.setReplyMarkup(mainMenu());
            } else if (user.getUserPress().equals(UserPress.POKEMON)) {
                PokemonAPI pokemonAPI = new PokemonAPI(update.getMessage().getText());
                message.setText(pokemonAPI.getPokemonData());
                user.setUserPress(UserPress.DEFAULT);
                message.setReplyMarkup(mainMenu());
            }

        }


        send(message);
    }



    @Override
    public String getBotUsername() {
        return "yogev_bot_bot";
    }






    private InlineKeyboardMarkup mainMenu(){
        InlineKeyboardButton googleSearchButton = new InlineKeyboardButton(GOOGLE_SEARCH);
        googleSearchButton.setCallbackData(GOOGLE_SEARCH);
        InlineKeyboardButton riverButton = new InlineKeyboardButton(RIVER);
        riverButton.setCallbackData(RIVER);
        InlineKeyboardButton pokemonButton = new InlineKeyboardButton(POKEMON);
        pokemonButton.setCallbackData(POKEMON);
        InlineKeyboardButton photoButton = new InlineKeyboardButton(PHOTO);
        photoButton.setCallbackData(PHOTO);
        InlineKeyboardButton jokeButton = new InlineKeyboardButton(JOKE);
        jokeButton.setCallbackData(JOKE);
        List<InlineKeyboardButton> firstRow = Arrays.asList(googleSearchButton,
                riverButton,
                photoButton);
        List<InlineKeyboardButton> secondRow = Arrays.asList(pokemonButton,
                jokeButton);
        List<List<InlineKeyboardButton>> keyBoard = Arrays.asList(firstRow,secondRow);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyBoard);
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup riverMenu(){
        InlineKeyboardButton biggestRiverButton = new InlineKeyboardButton("1");
        biggestRiverButton.setCallbackData("1");
        InlineKeyboardButton treeRiverButton = new InlineKeyboardButton("3");
        treeRiverButton.setCallbackData("3");
        InlineKeyboardButton tenRiverButton = new InlineKeyboardButton("10");
        tenRiverButton.setCallbackData("10");
        InlineKeyboardButton fifteenRiverButton = new InlineKeyboardButton("15");
        fifteenRiverButton.setCallbackData("15");

        List<InlineKeyboardButton> topRow = Arrays.asList(biggestRiverButton,
                treeRiverButton,
                tenRiverButton,
                fifteenRiverButton);
        List<List<InlineKeyboardButton>> keyBoard = Arrays.asList(topRow);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyBoard);
        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup photoMenu(){
        InlineKeyboardButton blackAndWhitePhotoButton = new InlineKeyboardButton(BLACK_AND_WHITE_PHOTO);
        blackAndWhitePhotoButton.setCallbackData(BLACK_AND_WHITE_PHOTO);
        InlineKeyboardButton mirrorPhotoButton = new InlineKeyboardButton(MIRROR_PHOTO);
        mirrorPhotoButton.setCallbackData(MIRROR_PHOTO);
        InlineKeyboardButton grayScalePhotoButton = new InlineKeyboardButton(GRAY_SCALE_PHOTO);
        grayScalePhotoButton.setCallbackData(GRAY_SCALE_PHOTO);
        InlineKeyboardButton shuffleColorsButton = new InlineKeyboardButton(SHUFFLE_COLORS_PHOTO);
        shuffleColorsButton.setCallbackData(SHUFFLE_COLORS_PHOTO);
        InlineKeyboardButton oppositeColorsButton = new InlineKeyboardButton(OPPOSITE_COLORS_PHOTO);
        oppositeColorsButton.setCallbackData(OPPOSITE_COLORS_PHOTO);
        InlineKeyboardButton drawEdgesButton = new InlineKeyboardButton(DRAW_EDGES_PHOTO);
        drawEdgesButton.setCallbackData(DRAW_EDGES_PHOTO);
        List<InlineKeyboardButton> firstRow = Arrays.asList(blackAndWhitePhotoButton,
                mirrorPhotoButton,
                drawEdgesButton);

        List<InlineKeyboardButton> secondRow = Arrays.asList(grayScalePhotoButton,
                shuffleColorsButton,
                oppositeColorsButton);
        List<List<InlineKeyboardButton>> keyBoard = Arrays.asList(firstRow,secondRow);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyBoard);
        return inlineKeyboardMarkup;
    }

    private long getChatId(Update update){
        long chatId = 0;
        if (update.getMessage()!=null){
            chatId = update.getMessage().getChatId();
        }else {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        }
        return chatId;
    }

    private void send(SendMessage sendMessage){
        try {
            execute((sendMessage));
        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    private BufferedImage invertImage(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage invertedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = originalImage.getRGB(x, y);
                Color color = new Color(rgb, true);
                int invertedRgb = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), color.getAlpha()).getRGB();
                invertedImage.setRGB(x, y, invertedRgb);
            }
        }

        return invertedImage;
    }


}
