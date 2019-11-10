package com.zerobot.dao.message;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class MessageDaoimpl implements MessageDao {
    JdbcTemplate jdbc;
    NamedParameterJdbcTemplate namedJdbc;

    @Override
    public String findMessageByOBJID(String object_id) {
        String sql =
                "SELECT MESSAGE FROM ZEROBOT.MESSAGE WHERE OBJECT_ID=?";


        try {
            return jdbc.queryForObject(sql,String.class,object_id);
        } catch (EmptyResultDataAccessException e) {
            return "[End of scenario]";
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
