package com.zerobot.application;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.zerobot.dao.message.MessageDao;
import com.zerobot.dao.scenario.Scenario;
import com.zerobot.dao.scenario.ScenarioDao;
import com.zerobot.dao.scenario_step.Scenario_step;
import com.zerobot.dao.scenario_step.Scenario_stepDao;
import com.zerobot.dao.transaction.Transaction;
import com.zerobot.dao.transaction.TransactionDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
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
    TransactionDao transactionDao = ctx.getBean(TransactionDao.class);
    ScenarioDao scenarioDao = ctx.getBean(ScenarioDao.class);
    Scenario_stepDao scenario_stepDao = ctx.getBean(Scenario_stepDao.class);
    MessageDao messageDao = ctx.getBean(MessageDao.class);

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

        String userId = getUserID(receiveJson);

        Scenario scenario = scenarioDao.getRandomScenario();

        transactionDao.insert(new Transaction(userId, scenario.getScenario_id(), 1));

        String object_id = scenario_stepDao.findObject_IdByIdStep(scenario.getScenario_id(), 1);
        String message = messageDao.findMessageByOBJID(object_id);

//        String anwser = "Hi ~, shall we start today's lesson? Start study by typing \"LEGO\"";
        String botAnswer = "Now, let's try a role-play!\n" +
                "please use the expressions in the card as much as you can during the role-play\n" +
                "We Shall do an exercise that will involve you to translate korean sentences into English.\n" +
                "Okay! Let's hop right in! *('V')*\n\n" + scenario.getComment() + "\n\n" +
                "[question]\n" + message;

        JsonObject returnJson = getSimpleTextJson(botAnswer);

        return returnJson.toString();
    }

    @RequestMapping(value = "/bot", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public String bot(@RequestBody String jsonObject) throws FileNotFoundException {
        JsonObject receiveJson = getJsonObject(jsonObject);
        String userId = getUserID(receiveJson);


        Transaction trx = null;
        try {
            trx = transactionDao.findByID(userId);
        } catch (EmptyResultDataAccessException e) {
            return getSimpleTextJson("진행중인 대화가 없습니다 'LEGO'를 입력해주세요.").toString();
        }

        trx.setCon_scenario_step(trx.getCon_scenario_step() + 1);
        transactionDao.update(trx);

        String object_id = scenario_stepDao.findObject_IdByIdStep(trx.getCon_scenario(), trx.getCon_scenario_step());
        String message = messageDao.findMessageByOBJID(object_id);

        if(message.equals("[End of scenario]")){
            transactionDao.deleteByID(userId);
        }

        JsonObject returnJson = getSimpleTextJson(message);
        return returnJson.toString();
    }

    private String getUserID(JsonObject receiveJson) {
        return receiveJson.getAsJsonObject("userRequest")
                .getAsJsonObject("user")
                .get("id").getAsString();
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
