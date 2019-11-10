package com.zerobot.dao.transaction;


import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.Map;

public class TransactionDaoImpl implements TransactionDao {
    JdbcTemplate jdbc;
    NamedParameterJdbcTemplate namedJdbc;

    @Override
    public void insert(Transaction transaction) {
        String sql =
                "INSERT INTO ZEROBOT.TRANSACTION(TRANSACTION_ID, CON_SCENARIO, CON_SCENARIO_STEP) "
                        + "VALUES(:TRANSCATION_ID, :CON_SCENARIO, :CON_SCENARIO_STEP)";

        Map<String, Object> map = new HashMap<>();
        map.put("TRANSCATION_ID", transaction.getTransaction_id());
        map.put("CON_SCENARIO", transaction.getCon_scenario());
        map.put("CON_SCENARIO_STEP", transaction.getCon_scenario_step());


        try {
            namedJdbc.update(sql, map);
        } catch (DuplicateKeyException e) {
            deleteByID(transaction.getTransaction_id());
            namedJdbc.update(sql, map);
        }

    }

    @Override
    public Transaction findByID(String id) {
        String sql =
                "SELECT * FROM ZEROBOT.TRANSACTION WHERE TRANSACTION_ID=:TRANSACTION_ID ";

        Map<String, Object> map = new HashMap<>();
        map.put("TRANSACTION_ID", id);

        return namedJdbc.queryForObject(sql, map, new Transaction());
    }

    @Override
    public void deleteByID(String id) {
        String sql =
                "DELETE FROM ZEROBOT.TRANSACTION WHERE TRANSACTION_ID=:TRANSACTION_ID ";

        Map<String, Object> map = new HashMap<>();
        map.put("TRANSACTION_ID", id);

        namedJdbc.update(sql, map);
    }

    @Override
    public void update(Transaction transaction) {
        String sql =
                "UPDATE ZEROBOT.TRANSACTION SET CON_SCENARIO_STEP=? WHERE TRANSACTION_ID=?";

        jdbc.update(sql, transaction.con_scenario_step, transaction.transaction_id);
    }

    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void setNamedJdbc(NamedParameterJdbcTemplate namedJdbc) {
        this.namedJdbc = namedJdbc;
    }
}
