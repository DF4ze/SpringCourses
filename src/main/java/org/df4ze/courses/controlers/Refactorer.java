package org.df4ze.courses.controlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.df4ze.courses.models.Entities.Arrivee;
import org.df4ze.courses.models.Entities.Cote;
import org.df4ze.courses.models.Entities.Course;
import org.df4ze.courses.models.Entities.CourseComplete;
import org.df4ze.courses.models.Entities.Partant;
import org.df4ze.courses.models.Entities.Rapport;
import org.df4ze.courses.resources.repositories.ArriveeRepository;
import org.df4ze.courses.resources.repositories.CoteRepository;
import org.df4ze.courses.resources.repositories.CourseRepository;
import org.df4ze.courses.resources.repositories.PartantRepository;
import org.df4ze.courses.resources.repositories.RapportRepository;
import org.df4ze.courses.services.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Refactorer {

	@Autowired
	private transient ArriveeRepository arriveeRepository;
	@Autowired
	private transient CoteRepository coteRepository;
	// @Autowired
	// private transient CourseCompleteRepository courseCompleteRepository;
	@Autowired
	private transient PartantRepository partantRepository;
	@Autowired
	private transient RapportRepository rapportRepository;
	@Autowired
	private transient CourseRepository courseRepository;
	@Autowired
	private transient RepositoryService repository;

	private transient final int cycleStep = 100;
	// private Semaphore sema = new Semaphore(1);

	private static Long from = null;

	public Refactorer() {
	}

	public void makeCourseComplete(Long fromCourseId) {
		from = fromCourseId;
		makeCourseComplete();
	}

	public void makeCourseComplete() {

		List<Course> coursesList = null;

		int stepDone = 1;
		// long lastCourse = from;

		while (stepDone != 0) {
			stepDone = 0;
			// from = lastCourse;

			if (from != null) {
				String texte = "Starting from id " + from;
				System.out.println(texte);
				coursesList = courseRepository.findAllFrom(from);
			} else
				coursesList = courseRepository.findAll();

			for (Course course : coursesList) {

				CourseComplete cc = new CourseComplete();

				///////////////////////////////////
				// Infos Course
				cc.setCourseID(course.getId());
				cc.setDateCourse(course.getDate());
				cc.setNumeroReunion(course.getReunion());
				cc.setNumeroCourse(course.getCourse());
				cc.setHippodrome(course.getHippodrome());
				cc.setPrime(course.getPrime());
				cc.setTypeCourse(course.getType());
				cc.setAutoStart(course.getDepart());

				////////////////////////////////////
				// Infos Rapport

				Set<Rapport> rapportsList = rapportRepository.findByCourseID(course.getId());
				if (rapportsList.size() == 0)
					continue;

				for (Rapport unRap : rapportsList) {

					if (unRap.getArrivee() == 1) {
						cc.setNumeroChvlPremier(unRap.getNumCheval());
						cc.setRapGagnant(unRap.getGagnant());
						cc.setRapPlacePremier(unRap.getPlace());

					} else if (unRap.getArrivee() == 2) {
						cc.setNumeroChvlDeuxieme(unRap.getNumCheval());
						cc.setRapPlaceDeuxieme(unRap.getPlace());

					} else if (unRap.getArrivee() == 3) {
						cc.setNumeroChvlTroisieme(unRap.getNumCheval());
						cc.setRapPlaceTroisieme(unRap.getPlace());

					}

				}
				rapportsList.clear();
				rapportsList = null;

				////////////////////////////////////
				// Infos Cote
				Cote coteFavPreum = null;
				Cote coteFavDeuz = null;
				Cote coteFavTroiz = null;

				Cote cotePrCtPreum = null;
				Cote cotePrCtDeuz = null;
				Cote cotePrCtTroiz = null;

				Set<Cote> cotesList = coteRepository.findByCourseID(course.getId());
				if (cotesList.size() == 0)
					continue;

				cc.setNombrePartant(cotesList.size());

				// On recupere les 3 favoris : cote < 5
				// et les 3 meilleurs pourcentage
				int nbCoteInf5 = 0;
				for (Cote uneCote : cotesList) {

					if (uneCote.getCote() < 5) {
						nbCoteInf5++;
					}
					if (coteFavPreum == null)
						coteFavPreum = uneCote; // Cloner??????????????
					else {

						if (coteFavPreum.getCote() > uneCote.getCote()) {
							coteFavTroiz = coteFavDeuz;
							coteFavDeuz = coteFavPreum;
							coteFavPreum = uneCote;

						} else if (coteFavDeuz == null) {
							coteFavDeuz = uneCote;

						} else {
							if (coteFavDeuz.getCote() > uneCote.getCote()) {
								coteFavTroiz = coteFavDeuz;
								coteFavDeuz = uneCote;

							} else if (coteFavTroiz == null) {
								coteFavTroiz = uneCote;

							} else if (coteFavTroiz.getCote() > uneCote.getCote()) {
								coteFavTroiz = uneCote;
							}
						}
					}

					if (cotePrCtPreum == null)
						cotePrCtPreum = uneCote; // Cloner??????????????
					else {

						if (cotePrCtPreum.getEnjeux() < uneCote.getEnjeux()) {
							cotePrCtTroiz = cotePrCtDeuz;
							cotePrCtDeuz = cotePrCtPreum;
							cotePrCtPreum = uneCote;

						} else if (cotePrCtDeuz == null) {
							cotePrCtDeuz = uneCote;

						} else {
							if (cotePrCtDeuz.getEnjeux() < uneCote.getEnjeux()) {
								cotePrCtTroiz = cotePrCtDeuz;
								cotePrCtDeuz = uneCote;

							} else if (cotePrCtTroiz == null) {
								cotePrCtTroiz = uneCote;

							} else if (cotePrCtTroiz.getEnjeux() < uneCote.getEnjeux()) {
								cotePrCtTroiz = uneCote;
							}
						}
					}

				}
				cotesList.clear();
				cotesList = null;

				cc.setNombreChevauxInfCinq(nbCoteInf5);

				if (coteFavPreum != null) {
					cc.setNumeroPremierFavori(coteFavPreum.getNumCheval());

				}
				if (coteFavDeuz != null) {
					cc.setNumeroDeuxiemeFavori(coteFavDeuz.getNumCheval());

				}
				if (coteFavTroiz != null) {
					cc.setNumeroTroisiemeFavori(coteFavTroiz.getNumCheval());

				}

				cc.setPourcentPremierFavori((cotePrCtPreum == null ? null : cotePrCtPreum.getEnjeux()));
				cc.setPourcentDeuxiemeFavori((cotePrCtDeuz == null ? null : cotePrCtDeuz.getEnjeux()));
				cc.setPourcentTroisiemeFavori((cotePrCtTroiz == null ? null : cotePrCtTroiz.getEnjeux()));

				// calcul de la somme des enjeux/prcent
				Float somPrCent = (cotePrCtPreum == null ? 0f : cotePrCtPreum.getEnjeux())
						+ (cotePrCtDeuz == null ? 0f : cotePrCtDeuz.getEnjeux())
						+ (cotePrCtTroiz == null ? 0f : cotePrCtTroiz.getEnjeux());
				cc.setTotalPourcent(somPrCent);

				// nb chv cote < 5 arrivé placé => dans les 3 1er
				int nbInf5Place = 0;

				if (coteFavPreum != null) {
					if (coteFavPreum.getNumCheval() == cc.getNumeroChvlPremier()
							|| coteFavPreum.getNumCheval() == cc.getNumeroChvlDeuxieme()
							|| coteFavPreum.getNumCheval() == cc.getNumeroChvlTroisieme())
						nbInf5Place++;
				}

				if (coteFavDeuz != null) {
					if (coteFavDeuz.getNumCheval() == cc.getNumeroChvlPremier()
							|| coteFavDeuz.getNumCheval() == cc.getNumeroChvlDeuxieme()
							|| coteFavDeuz.getNumCheval() == cc.getNumeroChvlTroisieme())
						nbInf5Place++;
				}

				if (coteFavTroiz != null) {
					if (coteFavTroiz.getNumCheval() == cc.getNumeroChvlPremier()
							|| coteFavTroiz.getNumCheval() == cc.getNumeroChvlDeuxieme()
							|| coteFavTroiz.getNumCheval() == cc.getNumeroChvlTroisieme())
						nbInf5Place++;
				}

				// if( coteFavPreum.getNumCheval() == cc.getNumeroChvlPremier()
				// ||
				// coteFavDeuz.getNumCheval() == cc.getNumeroChvlPremier() ||
				// coteFavTroiz.getNumCheval() == cc.getNumeroChvlPremier() )
				// nbInf5Place ++;
				// if( coteFavPreum.getNumCheval() == cc.getNumeroChvlDeuxieme()
				// ||
				// coteFavDeuz.getNumCheval() == cc.getNumeroChvlDeuxieme() ||
				// coteFavTroiz.getNumCheval() == cc.getNumeroChvlDeuxieme() )
				// nbInf5Place ++;
				// if( coteFavPreum.getNumCheval() ==
				// cc.getNumeroChvlTroisieme() ||
				// coteFavDeuz.getNumCheval() == cc.getNumeroChvlTroisieme() ||
				// coteFavTroiz.getNumCheval() == cc.getNumeroChvlTroisieme() )
				// nbInf5Place ++;
				cc.setNombreChvlFavoriPlace(nbInf5Place);

				///////////////////////////
				// Arrivees
				Set<Arrivee> arrivees = arriveeRepository.findByCourseID(course.getId());

				Map<Integer, String> resultats = getNomFromPlace(arrivees);

				try {
					cc.setP1er(resultats.get(1));
					cc.setP2eme(resultats.get(2));
					cc.setP3eme(resultats.get(3));
					cc.setP4eme(resultats.get(4));
					cc.setP5eme(resultats.get(5));
					cc.setP6eme(resultats.get(6));
					cc.setP7eme(resultats.get(7));
					cc.setP8eme(resultats.get(8));
					cc.setP9eme(resultats.get(9));
					cc.setP10eme(resultats.get(10));
					cc.setP11eme(resultats.get(11));
					cc.setP12eme(resultats.get(12));
					cc.setP13eme(resultats.get(13));
					cc.setP14eme(resultats.get(14));
					cc.setP15eme(resultats.get(15));
				} catch (Exception e2) {

				}
				resultats.clear();
				resultats = null;

				//////////////////////////////
				// Info partant
				int ageMin = 30;
				int ageMax = 0;

				Set<Partant> partantsListe = partantRepository.findByCourseID(course.getId());
				Partant ChvBestGains = null;
				if (partantsListe.size() != 0) {
					// on fait le tour de tout les partant de cette course
					for (Partant unPart : partantsListe) {
						// si le numero du cheval en cours est celui du 1er
						// favoris
						if (unPart.getNumCheval() == cc.getNumeroPremierFavori()) {
							cc.setMusiquePremier(unPart.getMusique());
							// cc.setAgeSexChvlPremier(unPart.getAgeSexe());
							cc.setNomChvlPremier(unPart.getNomCheval());
							// cc.setGainsPremier(unPart.getGains());
							// break;
						}

						if (unPart.getNomCheval().equals(cc.getP1er())) {
							cc.setGainChvlPremier(unPart.getiGains());
						}

						// min et max age
						try {
							String text = unPart.getAgeSexe().replace("F", "");
							text = text.replace("H", "");
							text = text.replace("M", "");

							int age = Integer.parseInt(text);

							if (age > ageMax)
								ageMax = age;
							if (age < ageMin)
								ageMin = age;

						} catch (Exception e) {
							String texte = "Pb parse Age (" + unPart.getAgeSexe() + "), course " + course.getId()
									+ " : " + e.getMessage();
							System.err.println(texte);

						}

						// musique du cheval qui a le plus gros gain
						if (ChvBestGains == null && unPart.getiGains() != null) {

							ChvBestGains = unPart.clone();
						} else if (unPart.getiGains() != null)
							if (unPart.getiGains() > ChvBestGains.getiGains()) {
								ChvBestGains = unPart.clone();
							}

					}
					cc.setAgeSexChvlPremier(ageMin + "-" + ageMax);
					if (ChvBestGains != null) {
						cc.setMusiqueMeilleurGains(ChvBestGains.getMusique());
						cc.setNumeroMeilleurGains(ChvBestGains.getNumCheval());
					}
				}
				partantsListe.clear();
				partantsListe = null;

				///////////////////////////
				// envoi BDD
				repository.add(cc);
				System.out.println("Insert ok for courseID : " + course.getId());

				cc = null;

				stepDone++;
				if (stepDone > cycleStep) {
					from = course.getCourseID();
					break;
				}
			}
			coursesList.clear();
			coursesList = null;
			System.gc();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			
			if( stepDone < cycleStep )
				break;
			
		}

	}

	private static Map<Integer, String> getNomFromPlace(Set<Arrivee> arrivees) {
		Map<Integer, String> ret = new HashMap<Integer, String>();

		for (Arrivee arrivee : arrivees) {
			ret.put(arrivee.getNumArrivee(), arrivee.getNomChv());
		}

		return ret;
	}

}
