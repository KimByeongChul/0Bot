package com.zerobot.dao.history;

public interface HistoryDao {
    void insert(History history);

    boolean isEmpty(String ClassId);
}
