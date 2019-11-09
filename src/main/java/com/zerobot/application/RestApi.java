package com.zerobot.application;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.zerobot.dao.transaction.Transaction;
import com.zerobot.dao.transaction.TransactionDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileReader;


@RestController
public class RestApi {
    Gson gson = new Gson();
    JsonParser parser = new JsonParser();
    ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");


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




        // Todo : 트랜잭션 서비스를 이용해서 저장 로직 실행 필요
        Transaction transaction = new Transaction();
        transaction.setTransaction_id(userId);

        TransactionDao transactionService = ctx.getBean(TransactionDao.class);

        transaction.setTransaction_id(userId);
        // Todo-End

        JsonObject returnJson = getSimpleTextJson("임시");

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
