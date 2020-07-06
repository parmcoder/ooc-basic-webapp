package ooc.webapp.possawat.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import ooc.webapp.possawat.model.Users;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<Users> {

    public static final String BASE_SQL
            = "SELECT u.User_Id, u.User_Name, u.Encryted_Password From APP_USER u ";

    @Override
    public Users mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long userId = rs.getLong("USER_ID");
        String userName = rs.getString("USER_NAME");
        String encrytedPassword = rs.getString("ENCRYTED_PASSWORD");

        return new Users(userId, userName, encrytedPassword);
    }

}
