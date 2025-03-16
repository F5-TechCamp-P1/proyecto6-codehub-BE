package dev.proyect6.codehub.codehub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.proyect6.codehub.codehub.models.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

}
