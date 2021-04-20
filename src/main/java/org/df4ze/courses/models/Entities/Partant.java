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
public class Partant extends AbstractCourseEntity implements Cloneable{


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Long courseID;
	private Integer numCheval;
	private String nomCheval;
	private String ageSexe;
	private String musique;
	private String gains;
	private Integer iGains;
	
	
	public Partant() {
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

	public String getNomCheval() {
		return nomCheval;
	}
	public void setNomCheval(String nomCheval) {
		this.nomCheval = nomCheval;
	}


	public String getAgeSexe() {
		return ageSexe;
	}
	public void setAgeSexe(String ageSexe) {
		this.ageSexe = ageSexe;
	}


	public String getMusique() {
		return musique;
	}
	public void setMusique(String musique) {
		this.musique = musique;
	}

	public String getGains(){
		return gains;
	}
	public void setGains(String legains) {
		this.gains = legains;
		
		try{
			if( gains != null ){
				gains = gains.replace(" ", "");
	
				this.iGains = Integer.parseInt(gains);
			}
		}catch( Exception e ){
			;
		}

	}


	public Integer getiGains() {
		return iGains;
	}
	public void setiGains(Integer iGains) {
		this.iGains = iGains;
	}
	
	@Override
	public Partant clone() {
		Partant o = new Partant();

		o.ageSexe = ageSexe;
		o.courseID = courseID.longValue();
		o.gains = gains;
		o.id = id.longValue();
		o.iGains = iGains.intValue();
		o.musique = musique;
		o.nomCheval = nomCheval;
		o.numCheval = numCheval.intValue();
		
		
		// on renvoie le clone
		return o;
	}

	@Override
	public String toString() {
		return "Partant [id=" + id + ", courseID=" + courseID + ", numCheval=" + numCheval + ", nomCheval=" + nomCheval
				+ ", ageSexe=" + ageSexe + ", musique=" + musique + ", gains=" + gains + ", iGains=" + iGains + "]";
	}

}
