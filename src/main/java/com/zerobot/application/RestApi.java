package com.zerobot.application;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.zerobot.dao.transaction.Transaction;
import com.zerobot.dao.transaction.TranscationDao;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;


@RestController
public class RestApi {
    Gson gson = new Gson();
    JsonParser parser = new JsonParser();

    @RequestMapping(value = "/ping", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public String isRunning(@RequestBody String jsonObject) {
        JsonObject json = new JsonObject();

        json.addProperty("text", "연동 됩니당 ㅎㅎ");

        return json.toString();
    }

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public String startBot(@RequestBody String jsonObject) throws FileNotFoundException {

        JsonObject receiveJson = getJsonObject(jsonObject);

        String userId = receiveJson.getAsJsonObject("userRequest")
                .getAsJsonObject("user")
                .get("id").getAsString();

        Transaction transaction = new Transaction();
        transaction.setTransaction_id(userId);

        //Todo : 트랜잭션 서비스를 이용해서 저장 시퀀스 실행 필

        JsonObject returnJson = getSimpleTextJson(text);

        return returnJson.toString();
    }

    JsonObject getSimpleTextJson(String Text) throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(ResourceUtils.getFile("classpath:json/simpleText.json")));
        JsonObject json = (JsonObject) parser.parse(reader);

        json.getAsJsonObject("template")
                .getAsJsonArray("outputs").get(0)
                .getAsJsonObject()
                .addProperty("simpleText", Text);

        return json;
    }

    JsonObject getJsonObject(String jsonReceive) {
        JsonObject json = (JsonObject) parser.parse(jsonReceive);
        return json;
    }


}
