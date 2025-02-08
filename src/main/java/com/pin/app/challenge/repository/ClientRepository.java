package com.pin.app.challenge.repository;

import com.pin.app.challenge.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT AVG(c.age) FROM Client c")
    Double findAverageAge();
}
