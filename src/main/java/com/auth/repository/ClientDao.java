package com.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auth.dto.Client;

@Repository
public interface ClientDao extends JpaRepository<Client, String>{

}
