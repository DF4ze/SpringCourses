package org.df4ze.courses.resources.repositories;

import java.util.Set;

import org.df4ze.courses.models.Entities.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RapportRepository extends JpaRepository<Rapport, Long> {

	Set<Rapport> findByCourseID( Long courseID );
}
