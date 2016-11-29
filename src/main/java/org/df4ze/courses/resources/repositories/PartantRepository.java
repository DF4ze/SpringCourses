package org.df4ze.courses.resources.repositories;

import java.util.Set;

import org.df4ze.courses.models.Entities.Partant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartantRepository extends JpaRepository<Partant, Long> {

	Set<Partant> findByCourseID( Long courseID );
	
}
