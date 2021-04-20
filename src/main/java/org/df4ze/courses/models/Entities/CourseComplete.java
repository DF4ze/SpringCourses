package org.df4ze.courses.models.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.df4ze.courses.models.AbstractCourseEntity;

@Entity
public class CourseComplete extends AbstractCourseEntity{

	@Id
	private Long 		courseID;
	private Integer 	nombrePartant;
	private Integer 	nombreChevauxInfCinq;
	private Double 		rapGagnant;
	private Double 		rapPlacePremier;
	private Double 		rapPlaceDeuxieme;
	private Double 		rapPlaceTroisieme;
	private Integer 	numeroChvlPremier;
	private Integer 	numeroChvlDeuxieme;
	private Integer 	numeroChvlTroisieme;
	private Float 		totalPourcent;
	private Float 		pourcentPremierFavori;
	private Integer 	numeroPremierFavori;
	private Float 		pourcentDeuxiemeFavori;
	private Integer 	numeroDeuxiemeFavori;
	private Float 		pourcentTroisiemeFavori;
	private Integer 	numeroTroisiemeFavori;
	private Integer 	nombreChvlFavoriPlace;
	private String 		dateCourse;
	private Integer 	numeroCourse;
	private Integer 	numeroReunion;
	private String 		hippodrome;
	private String 		prime;
	private String 		typeCourse;
	private String 		ageSexChvlPremier;
	private String 		autoStart;
	private String 		musiquePremier;
	private String 		nomChvlPremier;
	private Integer		gainChvlPremier;
	private String 		musiqueMeilleurGains;
	private Integer		numeroMeilleurGains;
	private String		p1er;
	private String		p2eme;
	private String		p3eme;
	private String		p4eme;
	private String		p5eme;
	private String		p6eme;
	private String		p7eme;
	private String		p8eme;
	private String		p9eme;
	private String		p10eme;
	private String		p11eme;
	private String		p12eme;
	private String		p13eme;
	private String		p14eme;
	private String		p15eme;
	
	public CourseComplete() {
	}

	public Integer getNombrePartant() {
		return nombrePartant;
	}
	public void setNombrePartant(Integer nombrePartant) {
		this.nombrePartant = nombrePartant;
	}

	public Integer getNombreChevauxInfCinq() {
		return nombreChevauxInfCinq;
	}
	public void setNombreChevauxInfCinq(Integer nombreChevauxInfCinq) {
		this.nombreChevauxInfCinq = nombreChevauxInfCinq;
	}

	public Double getRapGagnant() {
		return rapGagnant;
	}
	public void setRapGagnant(Double rapGagnant) {
		this.rapGagnant = rapGagnant;
	}

	public Double getRapPlaceDeuxieme() {
		return rapPlaceDeuxieme;
	}
	public void setRapPlaceDeuxieme(Double rapPlaceDeuxieme) {
		this.rapPlaceDeuxieme = rapPlaceDeuxieme;
	}

	public Double getRapPlaceTroisieme() {
		return rapPlaceTroisieme;
	}
	public void setRapPlaceTroisieme(Double rapPlaceTroisieme) {
		this.rapPlaceTroisieme = rapPlaceTroisieme;
	}

	public Integer getNumeroChvlPremier() {
		return numeroChvlPremier;
	}
	public void setNumeroChvlPremier(Integer numeroChvlPremier) {
		this.numeroChvlPremier = numeroChvlPremier;
	}

	public Integer getNumeroChvlDeuxieme() {
		return numeroChvlDeuxieme;
	}
	public void setNumeroChvlDeuxieme(Integer numeroChvlDeuxieme) {
		this.numeroChvlDeuxieme = numeroChvlDeuxieme;
	}

	public Integer getNumeroChvlTroisieme() {
		return numeroChvlTroisieme;
	}
	public void setNumeroChvlTroisieme(Integer numeroChvlTroisieme) {
		this.numeroChvlTroisieme = numeroChvlTroisieme;
	}

	public Float getTotalPourcent() {
		return totalPourcent;
	}
	public void setTotalPourcent(Float totalPourcent) {
		this.totalPourcent = totalPourcent;
	}

	public Float getPourcentPremierFavori() {
		return pourcentPremierFavori;
	}
	public void setPourcentPremierFavori(Float pourcentPremierFavori) {
		this.pourcentPremierFavori = pourcentPremierFavori;
	}

	public Integer getNumeroPremierFavori() {
		return numeroPremierFavori;
	}
	public void setNumeroPremierFavori(Integer numeroPremierFavori) {
		this.numeroPremierFavori = numeroPremierFavori;
	}

	public Float getPourcentDeuxiemeFavori() {
		return pourcentDeuxiemeFavori;
	}
	public void setPourcentDeuxiemeFavori(Float pourcentDeuxiemeFavori) {
		this.pourcentDeuxiemeFavori = pourcentDeuxiemeFavori;
	}

	public Integer getNumeroDeuxiemeFavori() {
		return numeroDeuxiemeFavori;
	}
	public void setNumeroDeuxiemeFavori(Integer numeroDeuxiemeFavori) {
		this.numeroDeuxiemeFavori = numeroDeuxiemeFavori;
	}

	public Float getPourcentTroisiemeFavori() {
		return pourcentTroisiemeFavori;
	}
	public void setPourcentTroisiemeFavori(Float pourcentTroisiemeFavori) {
		this.pourcentTroisiemeFavori = pourcentTroisiemeFavori;
	}

	public Integer getNumeroTroisiemeFavori() {
		return numeroTroisiemeFavori;
	}
	public void setNumeroTroisiemeFavori(Integer numeroTroisiemeFavori) {
		this.numeroTroisiemeFavori = numeroTroisiemeFavori;
	}

	public Integer getNombreChvlFavoriPlace() {
		return nombreChvlFavoriPlace;
	}
	public void setNombreChvlFavoriPlace(Integer nombreChvlFavoriPlace) {
		this.nombreChvlFavoriPlace = nombreChvlFavoriPlace;
	}

	public String getDateCourse() {
		return dateCourse;
	}
	public void setDateCourse(String dateCourse) {
		this.dateCourse = dateCourse;
	}

	public Integer getNumeroCourse() {
		return numeroCourse;
	}
	public void setNumeroCourse(Integer numeroCourse) {
		this.numeroCourse = numeroCourse;
	}

	public Integer getNumeroReunion() {
		return numeroReunion;
	}
	public void setNumeroReunion(Integer numeroReunion) {
		this.numeroReunion = numeroReunion;
	}

	public String getHippodrome() {
		return hippodrome;
	}
	public void setHippodrome(String hippodrome) {
		this.hippodrome = hippodrome;
	}

	public String getTypeCourse() {
		return typeCourse;
	}
	public void setTypeCourse(String typeCourse) {
		this.typeCourse = typeCourse;
	}

	public String getAgeSexChvlPremier() {
		return ageSexChvlPremier;
	}
	public void setAgeSexChvlPremier(String ageSexChvl) {
		this.ageSexChvlPremier = ageSexChvl;
	}

	public String getAutoStart() {
		return autoStart;
	}
	public void setAutoStart(String autoStart) {
		this.autoStart = autoStart;
	}

	public String getMusiquePremier() {
		return musiquePremier;
	}
	public void setMusiquePremier(String musiquePremier) {
		this.musiquePremier = musiquePremier;
	}

	public String getPrime() {
		return prime;
	}
	public void setPrime(String prime) {
		this.prime = prime;
	}


	public Double getRapPlacePremier() {
		return rapPlacePremier;
	}
	public void setRapPlacePremier(Double rapPlacePremier) {
		this.rapPlacePremier = rapPlacePremier;
	}

	public String getNomChvlPremier() {
		return nomChvlPremier;
	}
	public void setNomChvlPremier(String nomChvlPremier) {
		this.nomChvlPremier = nomChvlPremier;
	}


	public String getMusiqueMeilleurGains() {
		return musiqueMeilleurGains;
	}
	public void setMusiqueMeilleurGains(String musiqueMeilleurGains) {
		this.musiqueMeilleurGains = musiqueMeilleurGains;
	}

	public Integer getNumeroMeilleurGains() {
		return numeroMeilleurGains;
	}
	public void setNumeroMeilleurGains(int numeroMeilleurGains) {
		this.numeroMeilleurGains = numeroMeilleurGains;
	}

	
	public Integer getGainChvlPremier() {
		return gainChvlPremier;
	}
	public void setGainChvlPremier(Integer integer) {
		this.gainChvlPremier = integer;
	}

	public String getP1er() {
		return p1er;
	}
	public void setP1er(String p1er) {
		this.p1er = p1er;
	}

	public String getP2eme() {
		return p2eme;
	}
	public void setP2eme(String p2eme) {
		this.p2eme = p2eme;
	}

	public String getP3eme() {
		return p3eme;
	}
	public void setP3eme(String p3eme) {
		this.p3eme = p3eme;
	}

	public String getP4eme() {
		return p4eme;
	}
	public void setP4eme(String p4eme) {
		this.p4eme = p4eme;
	}

	public String getP5eme() {
		return p5eme;
	}
	public void setP5eme(String p5eme) {
		this.p5eme = p5eme;
	}

	public String getP6eme() {
		return p6eme;
	}
	public void setP6eme(String p6eme) {
		this.p6eme = p6eme;
	}

	public String getP7eme() {
		return p7eme;
	}
	public void setP7eme(String p7eme) {
		this.p7eme = p7eme;
	}

	public String getP8eme() {
		return p8eme;
	}
	public void setP8eme(String p8eme) {
		this.p8eme = p8eme;
	}

	public String getP9eme() {
		return p9eme;
	}
	public void setP9eme(String p9eme) {
		this.p9eme = p9eme;
	}

	public String getP10eme() {
		return p10eme;
	}
	public void setP10eme(String p10eme) {
		this.p10eme = p10eme;
	}

	public String getP11eme() {
		return p11eme;
	}
	public void setP11eme(String p11eme) {
		this.p11eme = p11eme;
	}

	public String getP12eme() {
		return p12eme;
	}
	public void setP12eme(String p12eme) {
		this.p12eme = p12eme;
	}

	public String getP13eme() {
		return p13eme;
	}
	public void setP13eme(String p13eme) {
		this.p13eme = p13eme;
	}

	public String getP14eme() {
		return p14eme;
	}
	public void setP14eme(String p14eme) {
		this.p14eme = p14eme;
	}

	public String getP15eme() {
		return p15eme;
	}
	public void setP15eme(String p15eme) {
		this.p15eme = p15eme;
	}

	@Override
	public Long getCourseID() {
		return courseID;
	}

	@Override
	public void setCourseID(Long courseID) {
		this.courseID = courseID;
	}

	@Override
	public Long getId() {
		return courseID;
	}

	@Override
	public String toString() {
		return "CourseComplete [courseID=" + courseID + ", numeroCourse=" + numeroCourse + ", numeroReunion="
				+ numeroReunion + ", hippodrome=" + hippodrome + "]";
	}

}
