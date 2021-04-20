package org.df4ze.courses.models.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.df4ze.courses.models.AbstractCourseEntity;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"courseID", "numCheval"}))
public class Cote extends AbstractCourseEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long courseID;
	private Integer numCheval;
	private Float cote;
	private Float enjeux;

	public Cote() {
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

	public Float getCote() {
		return cote;
	}
	public void setCote(Float cote) {
		this.cote = cote;
	}

	public Float getEnjeux() {
		return enjeux;
	}
	public void setEnjeux(Float enjeux) {
		this.enjeux = enjeux;
	}

	@Override
	public String toString() {
		return "Cote [id=" + id + ", courseID=" + courseID + ", numCheval=" + numCheval + ", cote=" + cote + ", enjeux="
				+ enjeux + "]";
	}

}
