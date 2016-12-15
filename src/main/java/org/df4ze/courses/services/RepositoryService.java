package org.df4ze.courses.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.df4ze.courses.models.AbstractEntity;
import org.df4ze.courses.models.EntitiesList;
import org.df4ze.courses.models.Entities.Arrivee;
import org.df4ze.courses.models.Entities.Cote;
import org.df4ze.courses.models.Entities.Course;
import org.df4ze.courses.models.Entities.CourseComplete;
import org.df4ze.courses.models.Entities.Partant;
import org.df4ze.courses.models.Entities.Rapport;
import org.df4ze.courses.resources.repositories.ArriveeRepository;
import org.df4ze.courses.resources.repositories.CoteRepository;
import org.df4ze.courses.resources.repositories.CourseCompleteRepository;
import org.df4ze.courses.resources.repositories.CourseRepository;
import org.df4ze.courses.resources.repositories.PartantRepository;
import org.df4ze.courses.resources.repositories.RapportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RepositoryService {

	@Autowired
	private ArriveeRepository arriveeRepository;
	@Autowired
	private CoteRepository coteRepository;
	@Autowired
	private CourseCompleteRepository courseCompleteRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private PartantRepository partantRepository;
	@Autowired
	private RapportRepository rapportRepository;

	@Transactional
	public void add(AbstractEntity entity) {
		if (entity instanceof Arrivee) {
			Arrivee a = (Arrivee) entity;
			arriveeRepository.saveAndFlush(a);

		} else if (entity instanceof Cote) {
			Cote new_name = (Cote) entity;
			coteRepository.saveAndFlush(new_name);

		} else if (entity instanceof CourseComplete) {
			CourseComplete new_name = (CourseComplete) entity;
			courseCompleteRepository.saveAndFlush(new_name);

		} else if (entity instanceof Course) {
			Course new_name = (Course) entity;
			courseRepository.saveAndFlush(new_name);

		} else if (entity instanceof Partant) {
			Partant new_name = (Partant) entity;
			partantRepository.saveAndFlush(new_name);

		} else if (entity instanceof Rapport) {
			Rapport new_name = (Rapport) entity;
			rapportRepository.saveAndFlush(new_name);
		}
	}

	public void addAll(Collection<AbstractEntity> entities) {
		// for (AbstractEntity entity : entities) {
		// add(entity);
		// }
		Collection<Arrivee> arrivees = new ArrayList<>();
		Collection<Cote> cotes = new ArrayList<>();
		Collection<CourseComplete> coursesCompletes = new ArrayList<>();
		Collection<Course> courses = new ArrayList<>();
		Collection<Partant> partants = new ArrayList<>();
		Collection<Rapport> rapports = new ArrayList<>();
		for (AbstractEntity entity : entities) {
			if (entity instanceof Arrivee) {
				Arrivee a = (Arrivee) entity;
				arrivees.add(a);

			} else if (entity instanceof Cote) {
				Cote new_name = (Cote) entity;
				cotes.add(new_name);

			} else if (entity instanceof CourseComplete) {
				CourseComplete new_name = (CourseComplete) entity;
				coursesCompletes.add(new_name);

			} else if (entity instanceof Course) {
				Course new_name = (Course) entity;
				courses.add(new_name);

			} else if (entity instanceof Partant) {
				Partant new_name = (Partant) entity;
				partants.add(new_name);

			} else if (entity instanceof Rapport) {
				Rapport new_name = (Rapport) entity;
				rapports.add(new_name);

			}
		}
		try {
			arriveeRepository.save(arrivees);
		} catch (DataIntegrityViolationException e) {
			System.err.println(e.getMessage());
		}
		try {
			coteRepository.save(cotes);
		} catch (DataIntegrityViolationException e) {
			System.err.println(e.getMessage());
		}
		try {
			courseCompleteRepository.save(coursesCompletes);
		} catch (DataIntegrityViolationException e) {
			System.err.println(e.getMessage());
		}
		try {
			courseRepository.save(courses);
		} catch (DataIntegrityViolationException e) {
			System.err.println(e.getMessage());
		}
		try {
			partantRepository.save(partants);
		} catch (DataIntegrityViolationException e) {
			System.err.println(e.getMessage());
		}
		try {
			rapportRepository.save(rapports);
		} catch (DataIntegrityViolationException e) {
			System.err.println(e.getMessage());
		}
	}

	public void addAll(EntitiesList list) {
		addAll(list.get());
	}

	@Transactional
	public List<?> listAll(Class<?> clazz) {
		List<?> list = new ArrayList<>();

		if (clazz.isAssignableFrom(Arrivee.class)) {
			list = arriveeRepository.findAll();

		} else if (clazz.isAssignableFrom(Cote.class)) {
			list = coteRepository.findAll();

		} else if (clazz.isAssignableFrom(CourseComplete.class)) {
			list = courseCompleteRepository.findAll();

		} else if (clazz.isAssignableFrom(Partant.class)) {
			list = partantRepository.findAll();

		} else if (clazz.isAssignableFrom(Course.class)) {
			list = courseRepository.findAll();

		} else if (clazz.isAssignableFrom(Rapport.class)) {
			list = rapportRepository.findAll();

		}

		return list;
	}

}
