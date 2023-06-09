package com.example.telegrambot.model;

import lombok.Data;
import java.util.Date;

@Data
public class CurrencyModel {
    Integer cur_ID;
    String curName;
    Double curRate;
    String curAbbreviation;
    Date date;

}
