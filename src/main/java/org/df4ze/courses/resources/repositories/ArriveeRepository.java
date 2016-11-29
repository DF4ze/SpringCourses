package org.df4ze.courses.resources.repositories;

import java.util.Set;

import org.df4ze.courses.models.Entities.Arrivee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArriveeRepository extends JpaRepository<Arrivee, Long>{

	Set<Arrivee> findByCourseID( Long courseID );
}
