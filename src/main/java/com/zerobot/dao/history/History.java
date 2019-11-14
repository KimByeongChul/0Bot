package com.zerobot.dao.history;

import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Entity
public class History implements RowMapper<History> {
    @Id
    String u_id;
    Date create_time;
    String user_input;
    String corrected_output;
    int similarity;
    String classId;

    @Override
    public History mapRow(ResultSet resultSet, int i) throws SQLException {
        History history = new History();
        history.setU_id(resultSet.getString("U_ID"));
        history.setCreate_time(resultSet.getDate("CREATE_TIME"));
        history.setUser_input(resultSet.getString("USER_INPUT"));
        history.setCorrected_output(resultSet.getString("CORRECTED_OUTPUT"));
        history.setSimilarity(resultSet.getInt("SIMILARITY"));
        return history;
    }

    public History() {
    }

    public History(String u_id, Date create_time, String user_input, String corrected_output, String classId) {
        this.u_id = u_id;
        this.create_time = create_time;
        this.user_input = user_input;
        this.corrected_output = corrected_output;
        this.classId = classId;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getUser_input() {
        return user_input;
    }

    public void setUser_input(String user_input) {
        this.user_input = user_input;
    }

    public String getCorrected_output() {
        return corrected_output;
    }

    public void setCorrected_output(String corrected_output) {
        this.corrected_output = corrected_output;
    }

    public int getSimilarity() {
        return similarity;
    }

    public void setSimilarity(int similarity) {
        this.similarity = similarity;
    }
}
