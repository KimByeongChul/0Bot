package com.zerobot.dao.scenario_step;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

public class Scenario_stepDaoImpl implements Scenario_stepDao {
    JdbcTemplate jdbc;
    NamedParameterJdbcTemplate namedJdbc;

    @Override
    public String findObject_IdByIdStep(String Scenario_id, int order_step) {
        String sql =
                "SELECT OBJECT_ID FROM ZEROBOT.SCENARIO_STEP WHERE SCENARIO_ID=:SCENARIO_ID AND ORDER_STEP=:ORDER_STEP";
        Map<String, Object> map = new HashMap<>();
        map.put("SCENARIO_ID", Scenario_id);
        map.put("ORDER_STEP", order_step);


        try {
            return namedJdbc.queryForObject(sql, map, String.class);
        } catch (EmptyResultDataAccessException e) {
            return "[시나리오 스텝 에서 OBJECT_ID를 조회하지 못했습니다. 추가해주세요.]";
        }
    }

    public JdbcTemplate getJdbc() {
        return jdbc;
    }

    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public NamedParameterJdbcTemplate getNamedJdbc() {
        return namedJdbc;
    }

    public void setNamedJdbc(NamedParameterJdbcTemplate namedJdbc) {
        this.namedJdbc = namedJdbc;
    }
}
