package com.bfwg.repository;

import com.bfwg.model.UserRoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bfwg.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
  Authority findByName(UserRoleName name);
}
