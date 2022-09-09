package com.kodizim.kodforum.repository;

import com.kodizim.kodforum.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

}
