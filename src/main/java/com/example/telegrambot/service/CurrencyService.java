package com.example.telegrambot.service;

import com.example.telegrambot.model.CurrencyModel;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class CurrencyService {
    public static String getCurrencyRate(String message, CurrencyModel model) throws IOException, ParseException {
        URL url = new URL("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchangenew?valcode=" + message + "&json");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder jsonString = new StringBuilder();
        while (scanner.hasNextLine()) {
            jsonString.append(scanner.nextLine());
        }
        scanner.close();

        String result = jsonString.toString();

        JSONArray jsonArray = new JSONArray(result);
        JSONObject object = jsonArray.getJSONObject(0);

        model.setCur_ID(object.getInt("r030"));
        model.setCurName(object.getString("txt"));
        model.setCurRate(object.getDouble("rate"));
        model.setCurAbbreviation(object.getString("cc"));
        model.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(object.getString("exchangedate")));

        return "Офіційний курс НБУ для " + model.getCurName() + ":\n" + model.getCurRate() + " UAH за 1 " + model.getCurAbbreviation() + "\n" + "Курс станом на: " + getFormatDate(model);
    }

    private static String getFormatDate(CurrencyModel model) {
        return new SimpleDateFormat("dd.MM.yyyy").format(model.getDate());
    }
}
