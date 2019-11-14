package com.zerobot.dao.history;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

public class HistoryDaoImpl implements HistoryDao {
    JdbcTemplate jdbc;
    NamedParameterJdbcTemplate namedJdbc;


    @Override
    public void insert(History history) {
        String sql =
                "INSERT INTO ZEROBOT.HISTORY(U_ID, CREATE_TIME, USER_INPUT, CORRECTED_OUTPUT, SIMILARITY, CLASS_ID ) "
                        + "VALUES(:U_ID, :CREATE_TIME, :USER_INPUT, :CORRECTED_OUTPUT, :SIMILARITY, :CLASS_ID)";

        Map<String, Object> map = new HashMap<>();
        map.put("U_ID",history.getU_id());
        map.put("CREATE_TIME",history.getCreate_time());
        map.put("USER_INPUT",history.getUser_input());
        map.put("CORRECTED_OUTPUT",history.getCorrected_output());
        map.put("SIMILARITY",history.getSimilarity());
        map.put("CLASS_ID",history.getClass());

        namedJdbc.update(sql, map);
    }

    @Override
    public boolean isEmpty(String ClassId) {
        return false;
    }
}
