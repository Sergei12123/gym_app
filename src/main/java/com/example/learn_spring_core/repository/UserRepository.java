package com.example.learn_spring_core.repository;

import com.example.learn_spring_core.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository<T extends User> extends BaseRepository<T> {

    boolean existsByUserName(String userName);

    Long countByFirstNameAndLastName(String firstName, String lastName);

    T findByUserName(String userName);

    @Modifying
    @Query("DELETE FROM #{#entityName} t WHERE t.userName = ?1")
    void removeByUserName(String userName);

}
