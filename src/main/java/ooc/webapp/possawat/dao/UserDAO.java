package ooc.webapp.possawat.dao;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


import ooc.webapp.possawat.mapper.UserMapper;
import ooc.webapp.possawat.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository //accessing database
@Transactional
public class UserDAO extends JdbcDaoSupport {
    private UserMapper mapper = new UserMapper();

    @Autowired
    public DataSource dataSource;

    @PostConstruct
    public void init(){
        setDataSource(dataSource);
    }

    public AppUser findUserAccount(String userName) {
        // Select .. from App_User u Where u.User_Name = ?
        String sql = UserMapper.BASE_SQL + " where u.User_Name = ? ";

        Object[] params = new Object[] { userName };
        try {
            //https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html
            AppUser appUserInfo = getJdbcTemplate().queryForObject(sql, params, mapper);
//            this.createJdbcTemplate()
            return appUserInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<AppUser> getAllUsers() {
        // Select .. from App_User u Where u.User_Name = ?
        String sql = "SELECT * from APP_USER";
        List<AppUser> appUserList;
        //https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html
        try{
            appUserList = getJdbcTemplate().query(sql, mapper);
        }catch(EmptyResultDataAccessException e){
            System.out.println("Null!");
            appUserList = null;
        }
        return appUserList;
    }
}
