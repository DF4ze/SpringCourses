package org.df4ze.courses.resources.repositories;

import java.util.List;

import org.df4ze.courses.models.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Long> {

	List<Course> findByCourseID( Long courseID );
	
	@Query("from Course where ?1 <= courseID ")
	List<Course> findAllFrom( Long courseID );
}
