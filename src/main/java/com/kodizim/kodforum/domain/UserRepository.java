package com.kodizim.kodforum.domain;

import com.kodizim.kodforum.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

}
