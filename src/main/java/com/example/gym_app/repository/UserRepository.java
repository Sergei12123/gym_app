package com.example.gym_app.repository;

import com.example.gym_app.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<T extends User> extends BaseRepository<T> {

    boolean existsByUserName(String userName);

    Optional<T> findByUserName(String userName);

    boolean existsByUserNameAndPassword(String userName, String password);

    @Transactional
    boolean existsByFirstNameAndLastNameAndIsActiveTrue(String firstName, String lastName);

    @Modifying
    @Query("DELETE FROM #{#entityName} t WHERE t.userName = ?1")
    void removeByUserName(String userName);

    @Query(value = "SELECT COUNT(u) FROM #{#entityName} u WHERE u.userName LIKE CONCAT(:userName,'%')")
    Long countByUserNameStartsWith(@Param("userName") String userName);

}
