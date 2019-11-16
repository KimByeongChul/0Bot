package com.zerobot.dao.message;

public interface MessageDao {
    String findMessageByOBJID(String Object_id);

    String findCorrectMSGByOBJID(String Object_id);
}
