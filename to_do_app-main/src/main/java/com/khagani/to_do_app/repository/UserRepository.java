package com.khagani.to_do_app.repository;


import com.khagani.to_do_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //    @Query(value = "select * from users left join tasks t on users.id = t.user_id where users.id=:userId;", nativeQuery = true)
//    List<User> findUsersById(@Param("userId") Long id);
    boolean existsByUsername(String username);

    Optional<User> findByUuid(UUID uuid);

    Optional<User> findByUsername(String email);
    Optional<User>  findByOtpCode(String token);
}
