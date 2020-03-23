package com.epam.maksim.katuranau.housing_utilities.dao.impl;

import com.epam.maksim.katuranau.housing_utilities.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Primary
@Repository
public class UserDaoImpl implements UserDao {

    @Value("${users.id.get.sql}")
    private String USERS_ID_GET_SQL;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public UserDaoImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Integer> getUsersIdList() {
        return namedParameterJdbcTemplate.queryForList(USERS_ID_GET_SQL, new HashMap<>(), Integer.class);
    }
}
