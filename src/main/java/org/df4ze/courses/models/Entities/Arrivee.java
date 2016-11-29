package org.df4ze.courses.models.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.df4ze.courses.models.AbstractCourseEntity;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"courseID", "numChv"}))
public class Arrivee extends AbstractCourseEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long courseID;

	private Integer numArrivee;
	private Integer numChv;
	private String nomChv;
	
	public Arrivee(){
		super();
	}
	
	public Arrivee(Long courseId, Integer numArrivee, Integer numChv, String nomChv) {
		super();
		this.courseID = courseId;
		this.numArrivee = numArrivee;
		this.numChv = numChv;
		this.nomChv = nomChv;
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
	public void setCourseID(Long courseID) {
		this.courseID = courseID;
	} 
	
	public Integer getNumArrivee() {
		return numArrivee;
	}
	public void setNumArrivee(Integer numArrivee) {
		this.numArrivee = numArrivee;
	}
	
	public Integer getNumChv() {
		return numChv;
	}
	public void setNumChv(Integer numChv) {
		this.numChv = numChv;
	}
	
	public String getNomChv() {
		return nomChv;
	}
	public void setNomChv(String nomChv) {
		this.nomChv = nomChv;
	}
	


	@Override
	public String toString() {
		return "Arrivee [id=" + id + ", CourseId=" + courseID + ", numArrivee=" + numArrivee + ", numChv=" + numChv
				+ ", nomChv=" + nomChv + "]";
	}


	
	

}
