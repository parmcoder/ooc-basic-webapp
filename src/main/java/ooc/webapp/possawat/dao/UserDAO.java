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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository //accessing database
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
        String sql = UserMapper.BASE_SQL;
        List<AppUser> resultList;
        //https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html
        try{
            resultList = getJdbcTemplate().query(sql, mapper);

        }catch(EmptyResultDataAccessException e){
            System.out.println("Null!");
            resultList = null;
        }
        return resultList;
    }

    public void insertUser(AppUser user, int authority){
        /*
        ? find total count and then insert to every table
         */
        String sqlForCount = "select max(USER_ID) from APP_USER";
        String sqlForCount2 = "select max(ID) from USER_ROLE";
        String sqlForInsert1 = "insert into APP_USER (USER_ID, USER_NAME, ENCRYPTED_PASSWORD, ENABLED)\n" +
                "values (?\n" +
                "        , ?\n" +
                "        , ?\n" +
                "        , 1)";
        String sqlForInsert2 = "insert into USER_ROLE (ID, USER_ID, ROLE_ID)\n" +
                "values (?, ?, ?)";
        try{
            Long lastId = getJdbcTemplate().queryForObject(sqlForCount, Long.class)+1;
            Long lastRole = getJdbcTemplate().queryForObject(sqlForCount2, Long.class)+1;
            Object[] params = new Object[]{ lastId, user.getUserName(), user.getEncryptedPassword() };
            getJdbcTemplate().update(sqlForInsert1, params);

            if(authority>0){
                params = new Object[]{ lastRole, lastId, 1};
                getJdbcTemplate().update(sqlForInsert2, params);
            }
            params = new Object[]{ lastRole, lastId, 2};
            getJdbcTemplate().update(sqlForInsert2, params);
        }catch(EmptyResultDataAccessException e){
            System.out.println("Null!");
        }
    }
}
