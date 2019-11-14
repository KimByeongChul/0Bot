package com.zerobot.dao.transaction;


import com.zerobot.dao.history.History;
import com.zerobot.dao.history.HistoryDao;
import com.zerobot.dao.message.MessageDao;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TransactionDaoImpl implements TransactionDao {
    JdbcTemplate jdbc;
    NamedParameterJdbcTemplate namedJdbc;
    MessageDao messageDao;
    HistoryDao historyDao;

    @Override
    public void insert(Transaction transaction) {
        String sql =
                "INSERT INTO ZEROBOT.TRANSACTION(TRANSACTION_ID, CON_SCENARIO, CON_SCENARIO_STEP) "
                        + "VALUES(:TRANSCATION_ID, :CON_SCENARIO, :CON_SCENARIO_STEP)";

        String class_id = getRandomString();
        while(!historyDao.isEmpty(class_id)){
            class_id = getRandomString();
        }

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

    public String getRandomString() {
        byte[] b = new byte[10];
        new Random().nextBytes(b);
        String MD5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(b);
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            MD5 = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            MD5 = null;
        }
        return MD5;
    }

    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public void setHistoryDao(HistoryDao historyDao) {
        this.historyDao = historyDao;
    }
}
