package com.zerobot.dao.scenario_step;

import org.hibernate.annotations.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
public class Scenario_step implements RowMapper<Scenario_step> {
    @Id
    String scenario_id;
    String object_id;
    int order_step;

    @Override
    public Scenario_step mapRow(ResultSet resultSet, int i) throws SQLException {

        Scenario_step scenario_step = new Scenario_step();
        scenario_step.setScenario_id(resultSet.getString("SCENARIO_ID"));
        scenario_step.setObject_id(resultSet.getString("OBJECT_ID"));
        scenario_step.setOrder_step(resultSet.getInt("ORDER_STEP"));
        return scenario_step;
    }

    public String getScenario_id() {
        return scenario_id;
    }

    public void setScenario_id(String scenario_id) {
        this.scenario_id = scenario_id;
    }

    public String getObject_id() {
        return object_id;
    }

    public void setObject_id(String object_id) {
        this.object_id = object_id;
    }

    public int getOrder_step() {
        return order_step;
    }

    public void setOrder_step(int order_step) {
        this.order_step = order_step;
    }
}
