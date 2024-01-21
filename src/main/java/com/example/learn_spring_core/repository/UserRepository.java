package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface UserRepository<T extends User> extends BaseRepository<T> {

    boolean existsByUserName(String userName);

    T findByUserName(String userName);

    boolean existsByUserNameAndPassword(String userName, String password);

    boolean existsByFirstNameAndLastNameAndIsActiveTrue(String firstName, String lastName);

    @Modifying
    @Query("DELETE FROM #{#entityName} t WHERE t.userName = ?1")
    void removeByUserName(String userName);

    @Query(value = "SELECT COUNT(u) FROM #{#entityName} u WHERE u.userName LIKE CONCAT(:userName,'%')")
    Long countByUserNameStartsWith(@Param("userName") String userName);

}
