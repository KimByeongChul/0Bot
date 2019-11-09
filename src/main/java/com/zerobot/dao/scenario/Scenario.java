package com.zerobot.dao.scenario;

import org.springframework.data.annotation.Id;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
public class Scenario implements RowMapper<Scenario> {
    @Id
    String scenario_id;
    String comment;

    @Override
    public Scenario mapRow(ResultSet resultSet, int i) throws SQLException {
        Scenario scenario = new Scenario();
        scenario.setScenario_id(resultSet.getString("SCENARIO_ID"));
        scenario.setComment(resultSet.getString("COMMENT"));
        return scenario;
    }

    public String getScenario_id() {
        return scenario_id;
    }

    public void setScenario_id(String scenario_id) {
        this.scenario_id = scenario_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
