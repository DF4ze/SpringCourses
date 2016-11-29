package org.df4ze.courses.resources.repositories;

import java.util.Set;

import org.df4ze.courses.models.Entities.Cote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoteRepository extends JpaRepository<Cote, Long>{

	Set<Cote> findByCourseID( Long courseID );

}
