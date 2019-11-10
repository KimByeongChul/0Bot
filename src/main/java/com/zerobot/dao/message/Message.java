package com.zerobot.dao.message;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Message implements RowMapper<Message> {
    String Object_id;
    String message;

    @Override
    public Message mapRow(ResultSet resultSet, int i) throws SQLException {
        Message message = new Message();
        message.setObject_id(resultSet.getString("OBJECT_ID"));
        message.setMessage(resultSet.getString("MESSAGE"));
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
}
