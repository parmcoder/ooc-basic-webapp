package ooc.webapp.possawat.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import ooc.webapp.possawat.model.AppUser;
import org.springframework.jdbc.core.RowMapper;

//better collect data and store it in
public class UserMapper implements RowMapper<AppUser> {

    public static final String BASE_SQL
            = "SELECT u.User_Id, u.User_Name, u.Encrypted_Password From APP_USER u ";

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppUser appUser = new AppUser();
        appUser.setUserId( rs.getLong("USER_ID"));
        appUser.setUserName(rs.getString("USER_NAME"));
        appUser.setEncryptedPassword(rs.getString("ENCRYPTED_PASSWORD"));

        return appUser;
    }

}
