package org.df4ze.courses.resourcesImpl.send2File;

import java.util.ArrayList;
import java.util.List;

import org.df4ze.courses.models.Entities.Course;
import org.df4ze.courses.resources.send2File.EntityWrapper;

public class CourseWrapper implements EntityWrapper {

	private Course course;
	
	public CourseWrapper(Course course) {
		this.course = course;
	}

	@Override
	public Integer getNumberOfAttributes() {
		return Course.class.getDeclaredFields().length;
	}

	@Override
	public Object getAttribute(int number) {
		switch( number ){
		case 0:
			return course.getCourseID();
		case 1:
			return course.getDate();
		case 2:
			return course.getHippodrome();
		case 3:
			return course.getReunion();
		case 4:
			return course.getCourse();
		case 5:
			return course.getPrix();
		case 6:
			return course.getType();
		case 7:
			return course.getPrime();
		case 8:
			return course.getDepart();

		}
		return null;
	}

	@Override
	public List<Object> getAttributesList() {
		List<Object> list = new ArrayList<>();
		for (int i = 0; i< getNumberOfAttributes(); i++) {
			list.add(getAttribute(i));
		}
		return list;
	}

}
