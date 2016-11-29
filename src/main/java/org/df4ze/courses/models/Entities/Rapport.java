package org.df4ze.courses.models.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.df4ze.courses.models.AbstractCourseEntity;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"courseId", "numCheval"}))
public class Rapport extends AbstractCourseEntity{


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long courseID;
	private Integer numCheval;
	private Integer arrivee;
	private Double place;
	private Double gagnant;
	
	
	public Rapport() {
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getCourseID() {
		return courseID;
	}
	@Override
	public void setCourseID(Long courseId) {
		this.courseID = courseId;
	}


	public Integer getNumCheval() {
		return numCheval;
	}
	public void setNumCheval(Integer numCheval) {
		this.numCheval = numCheval;
	}



	public Integer getArrivee() {
		return arrivee;
	}
	public void setArrivee(Integer arrivee) {
		this.arrivee = arrivee;
	}


	public Double getPlace() {
		return place;
	}
	public void setPlace(Double place) {
		this.place = place;
	}


	public Double getGagnant() {
		return gagnant;
	}
	public void setGagnant(Double gagnant) {
		this.gagnant = gagnant;
	}

}
