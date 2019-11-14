package com.zerobot.dao.message;

import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
public class Message implements RowMapper<Message> {
    @Id
    String Object_id;
    String message;
    String correct_reply;

    @Override
    public Message mapRow(ResultSet resultSet, int i) throws SQLException {
        Message message = new Message();
        message.setObject_id(resultSet.getString("OBJECT_ID"));
        message.setMessage(resultSet.getString("MESSAGE"));
        message.setCorrect_reply(resultSet.getString("CORRECT_REPLY"));
        return message;
    }

    public String getObject_id() {
        return Object_id;
    }

    public void setObject_id(String object_id) {
        Object_id = object_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCorrect_reply() {
        return correct_reply;
    }

    public void setCorrect_reply(String correct_reply) {
        this.correct_reply = correct_reply;
    }
}
