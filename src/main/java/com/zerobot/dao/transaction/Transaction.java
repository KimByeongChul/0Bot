package com.zerobot.dao.transaction;

import org.hibernate.annotations.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Entity
public class Transaction implements RowMapper<Transaction> {
    @Id
    String transaction_id;
    String con_scenario;
    String con_scenario_step;

    @Override
    public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setTransaction_id(resultSet.getString("TRANSACTION_ID"));
        transaction.setCon_scenario(resultSet.getString("CON_SCENARIO"));
        transaction.setCon_scenario_step(resultSet.getString("CON_SCENARIO_STEP"));
        return transaction;
    }


    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getCon_scenario() {
        return con_scenario;
    }

    public void setCon_scenario(String con_scenario) {
        this.con_scenario = con_scenario;
    }

    public String getCon_scenario_step() {
        return con_scenario_step;
    }

    public void setCon_scenario_step(String con_scenario_step) {
        this.con_scenario_step = con_scenario_step;
    }
}
