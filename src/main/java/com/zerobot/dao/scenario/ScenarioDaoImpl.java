package com.zerobot.dao.scenario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ScenarioDaoImpl implements ScenarioDao {
    JdbcTemplate jdbc;

    NamedParameterJdbcTemplate namedJdbc;
    @Override
    public Scenario getRandomScenario() {
        String sql = "SELECT * FROM ZEROBOT.SCENARIO";

        List<Scenario> scenarioList = jdbc.query(sql, new RowMapper<Scenario>() {
            @Override
            public Scenario mapRow(ResultSet resultSet, int i) throws SQLException {
                Scenario scenario = new Scenario();
                scenario.setScenario_id(resultSet.getString("SCENARIO_ID"));
                scenario.setComment(resultSet.getString("COMMENT"));
                return scenario;
            }
        });

        return scenarioList.get(new Random().nextInt(scenarioList.size()));
    }

    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void setNamedJdbc(NamedParameterJdbcTemplate namedJdbc) {
        this.namedJdbc = namedJdbc;
    }
}
