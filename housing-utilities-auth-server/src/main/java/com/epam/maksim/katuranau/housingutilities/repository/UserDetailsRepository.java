package com.epam.maksim.katuranau.housingutilities.repository;

import com.epam.maksim.katuranau.housingutilities.model.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserDetailsRepository extends CrudRepository<UserInfo, String> {
    UserInfo findByUserName(String userName);

    List<UserInfo> findAll();

    UserInfo findByUserId(Integer id);

    void deleteByUserId(Integer id);
}
