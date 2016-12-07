package org.df4ze.courses.main;

import java.io.IOException;

import org.df4ze.courses.models.Entities.Course;
import org.df4ze.courses.resources.send2File.Send2File;
import org.df4ze.courses.resourcesImpl.send2File.GenericEntityWrapper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestLauncher {


	private static ClassPathXmlApplicationContext ctx;

	public static void main(String[] args) {
		ctx = new ClassPathXmlApplicationContext("classpath:*spring.xml");

		send2FileTest();
		
		System.out.println("tests ended");
		ctx.close();
	}
	
	
	
	private static void send2FileTest(){
		Send2File send2File = ctx.getBean(Send2File.class);
		
		try {
			send2File.createFile("/home/df4ze/Bureau/test.xlsx");
			
			Course course = new Course();
			course.setCourse(1);
			course.setCourseID(321654l);
			course.setDate("Date =)");
			course.setDepart("Depart...");
			course.setHippodrome("hippodrome");
			course.setPrime("Prime");
			course.setPrix("GP");
			course.setReunion(5);
			course.setType("type");
			
			GenericEntityWrapper<Course> wrapper = new GenericEntityWrapper<>(course, Course.class);
			
			send2File.addRow(wrapper);
			send2File.closeFile();
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
