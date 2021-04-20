package org.df4ze.courses.resourcesImpl.warc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.df4ze.courses.models.EntitiesList;
import org.df4ze.courses.models.Entities.Arrivee;
import org.df4ze.courses.models.Entities.Cote;
import org.df4ze.courses.models.Entities.Course;
import org.df4ze.courses.models.Entities.Partant;
import org.df4ze.courses.models.Entities.Rapport;
import org.df4ze.courses.resources.Debug;
import org.df4ze.courses.resources.Logger;
import org.df4ze.courses.resources.warc.WarcParser;
import org.df4ze.courses.resourcesImpl.warc.tools.XPathTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class GenyParser implements WarcParser{

	private String url;
	private String body;
	private Document doc;
	private XPathTool xPathTool;
	
	GenyParser() {
	}
	
	

	public EntitiesList parse(String url, String body){
		this.url = url;
		this.body = body;
		
		EntitiesList beansList = new EntitiesList();
		init();
		
		EntitiesList beanListCourse = parse_course();
		if( beanListCourse != null)
			beansList.addAll( beanListCourse );
		
		EntitiesList beansListRapport = parse_rapport();
		if( beansListRapport != null )
			beansList.addAll( beansListRapport );
		
		EntitiesList beansListArrive = parse_arrivee();
		if( beansListArrive != null )
			beansList.addAll( beansListArrive );
		
		EntitiesList beansListPartant = parse_partant();
		if( beansListPartant != null )
			beansList.addAll( beansListPartant );
			
		EntitiesList beansListCote = parse_cote();
		if( beansListCote != null )
			beansList.addAll( beansListCote );
		

		
		try {
			Thread.sleep(10l);
		} catch (InterruptedException e) {}
		
		return beansList;
	}
	
	private void init(){
		doc = Jsoup.parse(body);
		xPathTool = new XPathTool();
	}
	
	public Long parse_numCourse(){
		// extraction de l'ID de la course
		Pattern p = Pattern.compile(".*_c([0-9]+)$");
		Matcher m = p.matcher(url);
		boolean b = m.matches();
		Long longCourse = null;
		if( b ){
			String numCourse = m.group(1);
			try{
				longCourse = Long.parseLong(numCourse);
			}catch(Exception e){
				if( Debug.isEnable() ){
					e.printStackTrace();
				}
			}
		}

		Logger.write("Num course : " + longCourse);

		if( Debug.isEnable() ){
			System.out.println("*************");
			System.out.println(url);
			System.out.println("Num course : "+longCourse);
		}
		return longCourse;
	}
	
	public EntitiesList parse_course(){
		Long longCourse = parse_numCourse();
		
		// extraction de l'ID de la date
		Pattern p = Pattern.compile(".*([0-9]{4}-[0-9]{2}-[0-9]{2})-.*");
		Matcher m = p.matcher(url);
		boolean b = m.matches();
		String date = null;
		if( b ){
			date = m.group(1);
		}
		if( Debug.isEnable() ){
			System.out.println("Date : "+date);
		}
		
		// extraction de la reunion
		String hippodrome = null;
		String reunion = null;
		Integer intReunion = null;
		Elements elements = xPathTool.getElements(doc, "/div[@class='nomReunion']");
		if( elements!=null && elements.size() > 0 ){
			String txt = elements.get(0).text();
			if( Debug.isEnable() ){
				System.out.println("nomReunion : "+txt);
			}
			
			// hippodrome
			p = Pattern.compile(".*:(.*)\\(R[0-9]+\\)");
			m = p.matcher(txt);
			b = m.matches();
			if( b ){
				hippodrome = m.group(1);
				hippodrome = hippodrome.trim();
			}
			if( Debug.isEnable() ){
				System.out.println("hippodrome : "+hippodrome);
			}
			
			// N� reunion
			p = Pattern.compile(".*:.*\\(R([0-9]+)\\)");
			m = p.matcher(txt);
			b = m.matches();
			if( b ){
				reunion = m.group(1);
				try{
					intReunion = Integer.parseInt(reunion);
				}catch(Exception e){
					if( Debug.isEnable() ){
						e.printStackTrace();
					}
				}
			}
			if( Debug.isEnable() ){
				System.out.println("reunion : "+intReunion);
			}
		}
		
		// Extraction de la course
		Integer numCourse = null;
		String prix = null;
		elements = xPathTool.getElements(doc, "/div[@class='nomCourse']");
		if( elements!=null && elements.size() > 0 ){
			String txt = elements.get(0).text();
			
			p = Pattern.compile("([0-9]+).*-.*");
			m = p.matcher(txt);
			b = m.matches();
			if( b ){
				String sNumCourse = m.group(1);
				try{
					numCourse = Integer.parseInt(sNumCourse);
				}catch(Exception e){
					if( Debug.isEnable() ){
						e.printStackTrace();
					}
				}
			}
			if( Debug.isEnable() ){
				System.out.println("numCourse : "+numCourse);
			}
		
			p = Pattern.compile("[0-9]+.*- (.*)");
			m = p.matcher(txt);
			b = m.matches();
			if( b ){
				prix = m.group(1);
				prix = prix.trim();
			}
			if( Debug.isEnable() ){
				System.out.println("prix : "+prix);
			}
		
		
		}
		
		// extract info course // Mont�|Steeple-chase|Attel�|Plat
		String type = null;
		String prime = null;
		String depart = "non";
		elements = xPathTool.getElements(doc, "/span[@class='infoCourse']");
		if( elements!=null && elements.size() > 0 ){
			String txt = elements.get(0).text();
			if( Debug.isEnable() ){
				System.out.println("infoCourse : "+txt);
			}
			p = Pattern.compile(".*(Monté|Steeple-chase|Attelé|Plat).*");
			m = p.matcher(txt);
			b = m.matches();
			if( b ){
				type = m.group(1);
				type = type.trim();
			}
			if( Debug.isEnable() ){
				System.out.println("type : "+type);
			}
			
			
			// somme des enjeux
			p = Pattern.compile(".*-.(([0-9]+.)*[0-9]{3})€.*");
			m = p.matcher(txt);
			b = m.matches();
			if( b ){
				prime = m.group(1);
				prime = prime.replaceAll( " ", "" );
//				try{
//					intReunion = Integer.parseInt(prime);
//				}catch(Exception e){}
			}else	{
				if( Debug.isEnable() ){
					System.out.println("prime no match ");
				}
			}
			if( Debug.isEnable() ){
				System.out.println("prime : "+prime);
			}

			
			// AutoStart
			p = Pattern.compile(".*autostart.*", Pattern.CASE_INSENSITIVE);
			m = p.matcher(txt);
			b = m.matches();
			if( b ){
				depart = "oui";
			}
			if( Debug.isEnable() ){
				System.out.println("Autostart : "+depart);
			}

		}
		
		Course course = null;
		if( longCourse 		!= null && 
				numCourse 	!= null &&
				date 		!= null &&
				hippodrome 	!= null &&
				prix 		!= null &&
				intReunion 	!= null &&
				depart	 	!= null &&
				type 		!= null ){
			
//			DAO<Course> DAOCourse = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getCourseDAO();
			course = new Course();
			course.setId( longCourse );
			course.setCourse(numCourse);
			course.setDate(date);
			course.setHippodrome(hippodrome);
			course.setPrix(prix);
			course.setReunion(intReunion);
			course.setType(type);
			course.setPrime(prime);
			course.setDepart(depart);
		}
		EntitiesList bl = null;
		if( course != null){
			bl = new EntitiesList();
			bl.add(course);
			Logger.write("Adding course : " + course);
		}
		
		return bl;
	}
	
	public EntitiesList parse_rapport(){
		Long longCourse = parse_numCourse();
		
		EntitiesList listeRapports = null;
		if( url.indexOf("arrivee-et-rapports") != -1 && longCourse != null){
			if( Debug.isEnable() ){
				System.out.println("===================================\nRapports");
			}
							
			Elements nbTableaux = xPathTool.getElements(doc, "/table[@id='lesSolos']/tbody table");
			boolean oldStyle = false;
			if( nbTableaux != null && nbTableaux.size() == 3)
				oldStyle = true;

			// ancien tableau
			Elements lignes = null;
			if( oldStyle ){
				lignes = xPathTool.getElements(doc, "/table[@id='lesSolos']/tbody/tr[0]/td[1]/table/tbody/tr");
				
				if( lignes == null || lignes.size() == 0 )
					lignes = xPathTool.getElements(doc, "/table[@id='lesSolos']/tbody/tr[0]/td[2]/table/tbody/tr");
			}else{
				// nouveau tableau
				lignes = xPathTool.getElements(doc, "/table[@id='lesSolos']/tbody/tr[0]/td[3]/table/tbody/tr");
				
			}
				
			if( lignes!=null && lignes.size() > 0 ){
				//System.out.println("Table finded ! "+lignes.size());
				Rapport rapport = new Rapport();
				listeRapports = new EntitiesList();
				
				for( int i=1; i< lignes.size(); i++ ){
					Element uneLigne = lignes.get(i);
					Elements cellules = uneLigne.select("td");
					
					Integer numCheval = null;
					Double gain = null;
					try{
					if( cellules!=null && cellules.size() > 0 ){
						for( int j=0; j < (oldStyle? cellules.size()/*-1*/ : cellules.size()); j++ ){
							Element uneCellule = cellules.get(j);
							
							if( j == 0 ){
//								Elements divs = uneCellule.select("div");
//								if( divs != null && divs.size() > 0 ){
//									Element div = divs.get(0);
//									numCheval = Integer.parseInt(div.text());
//								}
								String txt = uneCellule.text().trim();
								txt = txt.replaceAll("[^\\d\\.\\,\\-]", "");
								numCheval = Integer.parseInt( txt );
							
							}else if( j == 1 ){
								String txt = uneCellule.text().trim();
								txt = txt.replace(",", ".");
								txt = txt.replace("<b>", "");
								txt = txt.replace("</b>", "");
								txt = txt.replace(" ", "");
								txt = txt.replace("€", "");
								txt = txt.replaceAll("[^\\d\\.\\,\\-]", "");
								
								
								gain = Double.parseDouble(txt);
							}
						}// for cellules
						
						if( i == 1 ){
							rapport = new Rapport();
							rapport.setNumCheval(numCheval);
							rapport.setCourseID(longCourse);
							rapport.setGagnant(gain);
							rapport.setArrivee(1);
							
						}else if( i == 2 ){
							rapport.setPlace(gain);
							if( Debug.isEnable() ){
								System.out.println("1er => N"+rapport.getNumCheval()+" G:"+rapport.getGagnant()+" P:"+rapport.getPlace());
							}

							listeRapports.add(rapport);
						//	DAO<Rapport> DAORapport = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getRapportDAO();
						//	DAORapport.create(rapport);
						}else{
							rapport = new Rapport();
							
							rapport.setCourseID(longCourse);
							rapport.setNumCheval(numCheval);
							rapport.setArrivee(i-1);
							rapport.setPlace(gain);
							
							if( Debug.isEnable() ){
								System.out.println(rapport.getArrivee()+"eme => N"+rapport.getNumCheval()+" P:"+rapport.getPlace());
							}
					
							listeRapports.add(rapport);

//							DAO<Rapport> DAORapport = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getRapportDAO();
//							DAORapport.create(rapport);
						}
					}
					}catch( Exception e  ){ // pour les Parses Exceptions
						if( Debug.isEnable() ){
							System.err.println("Erreur RAPPORT : "+e.getMessage());
							e.printStackTrace();
						}
						
					}
				}// for lignes
			
				Logger.write("Adding rapport : " + rapport);
			// nouveau tableau
			}
			
		}
		
		return listeRapports;
	}
	
	public EntitiesList parse_arrivee(){
		Long longCourse = parse_numCourse();
		
		EntitiesList listeArrivees = null;
		if( url.indexOf("arrivee-et-rapports") != -1 && longCourse != null){
			Elements nbTableaux = xPathTool.getElements(doc, "/table[@id='arrivees']/tbody");
			
			Elements lignes = null;
			if( nbTableaux != null && nbTableaux.size() > 0 )
				lignes = xPathTool.getElements(doc, "/table[@id='arrivees']/tbody/tr");	
			
			if( lignes != null ){
			for( int i=1; i< lignes.size(); i++ ){
				Element uneLigne = lignes.get(i);
				Elements cellules = uneLigne.select("td");
				
				String nomCheval = null;
				Integer numCheval = null;
				Integer placeCheval = null;
				try{
					if( cellules!=null && cellules.size() > 0 ){
						for( int j=0; j < cellules.size(); j++ ){
							Element uneCellule = cellules.get(j);
							
							if( j == 0 ){
								String content = uneCellule.text();
								placeCheval = Integer.parseInt(content);
								
							}else if( j==1 ){
								String content = uneCellule.text();
								numCheval = Integer.parseInt(content);
								
							}else if( j==2 ){
								Elements justeNom = uneCellule.select("a");
								nomCheval = justeNom.text().trim();
								
							}else 
								break;
						}
						
						if( placeCheval != null && numCheval != null){
							if( listeArrivees == null )
								listeArrivees = new EntitiesList();
							if(Debug.isEnable())
								System.out.println("Course : "+longCourse+" Place : "+placeCheval+" Numero : "+numCheval+" Nom : "+nomCheval);
							listeArrivees.add(new Arrivee(longCourse, placeCheval, numCheval, nomCheval));
						}
					}
				}catch(Exception e){
					if( Debug.isEnable() ){
						System.err.println("Ligne N "+i +" : "+e.getMessage());
					}
				}
			}

			}
			Logger.write("Adding arrivees : " + listeArrivees);
		}
		
		return listeArrivees;
	}
	
	
	
	
	public EntitiesList parse_cote(){
		Long longCourse = parse_numCourse();
		
		EntitiesList cotesCourse = null;
		
		if(  url.indexOf("/cotes/") != -1 && longCourse != null){
			if( Debug.isEnable() ){
				System.out.println("===================================\nCote");
			}
			
			Elements lignes = xPathTool.getElements(doc, "/div[@id='div_tableau_cotes']/table/tbody/tr");
			if( lignes!=null && lignes.size() > 0 ){
				
				for( int i=0; i< lignes.size(); i++ ){
					Element uneLigne = lignes.get(i);
					Elements cellules = uneLigne.select("td");
					
					Integer numCheval = null;
					Float enjeux = null;
					Float cote = null;
					if( cellules!=null && cellules.size() > 0 ){
						for( int j=0; j < cellules.size(); j++ ){
							Element uneCellule = cellules.get(j);
							
							try{
								if( j == 0 ){
									numCheval = Integer.parseInt( uneCellule.text().trim() );
								
								}else if( j == 4 ){
									String txt = uneCellule.text().trim();
									txt = txt.replace(",", ".");
									cote = Float.parseFloat(txt);
									
								}else if( j == 7 ){
									String txt = uneCellule.text().trim();
									txt = txt.replace(",", ".");
									txt = txt.replace(" ", "");
									txt = txt.replace("%", "");
									txt = txt.replaceAll("[^\\d\\.\\,\\-]", "");
									
									enjeux = Float.parseFloat(txt);
								}
							}catch( Exception e ){
								if( Debug.isEnable() ){
									System.err.println("Erreur sur une ligne 'cote' : "+e.getMessage());
									//e.printStackTrace();
								}
								
							}
								
						}// fin for cellules
						
						if( Debug.isEnable() ){
							System.out.println("Cvl : "+numCheval+" cote : "+cote+" enjeux : "+enjeux);
						}
						if( numCheval != null && cote != null && enjeux != null ){
							//DAO<Rapport> DAORapport = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getRapportDAO();
							if( cotesCourse == null )
								cotesCourse = new EntitiesList();
							
							Cote coteBean = new Cote();
							coteBean.setCourseID(longCourse);
							coteBean.setNumCheval(numCheval);
							coteBean.setCote(cote);
							coteBean.setEnjeux(enjeux);
							
							cotesCourse.add(coteBean);
							//DAORapport.create(rapport);

							Logger.write("Adding cote : " + coteBean);
						}
					}
				}//fin for lignes
			}
		}
		
		return cotesCourse;
	}

	public EntitiesList parse_partant(){
		Long longCourse = parse_numCourse();
		
		EntitiesList partantsCourse = null;
		
		if(  url.indexOf("/partants-pmu/") != -1 && longCourse != null){
			if( Debug.isEnable() ){
				System.out.println("===================================\nPartants");
			}
			
			Elements celz = xPathTool.getElements(doc, "/div[@id='dt_partants']/table/thead/tr/th");
			int colMusique = 6;
			int colAgeSexe = 2;
			int colNom = 1;
			int colGains = 1;
			for(int i = 0; i< celz.size(); i++){
				Element oneCel = celz.get(i);
				if( oneCel.text().trim().toLowerCase().equals( "musique" ) ){
					colMusique = i;
				}
				if( oneCel.text().trim().toLowerCase().equals( "sa" ) ){
					colAgeSexe = i;
				}
				if( oneCel.text().trim().toLowerCase().equals( "cheval" ) ){
					colNom = i;
				}
				if( oneCel.text().trim().toLowerCase().equals( "gains" ) ){
					colGains = i;
				}
			}
			
			
			Elements lignes = xPathTool.getElements(doc, "/div[@id='dt_partants']/table/tbody/tr");//tbody[@class='yui-dt-data']
			if( lignes!=null && lignes.size() > 0 ){
				
				for( int i=0; i< lignes.size(); i++ ){
					Element uneLigne = lignes.get(i);
					Elements cellules = uneLigne.select("td");
					
					Integer numCheval = null;
					String ageSexe = null;
					String musique = null;
					String nom = null;
					String gains = null;
					if( cellules!=null && cellules.size() > 0 ){
						for( int j=0; j < cellules.size(); j++ ){
							Element uneCellule = cellules.get(j);
							
							try{
								if( j == 0 ){
									numCheval = Integer.parseInt( uneCellule.text().trim() );
								
								}else if( j == colNom ){
									nom = uneCellule.text().trim();
									
									
								}else if( j == colAgeSexe ){
									ageSexe = uneCellule.text().trim();
									
									
								}else if( j == colMusique ){
									musique = uneCellule.text().trim();
									
								}else if( j == colGains ){
									gains = uneCellule.text().trim();
									
								}
							}catch( Exception e ){
								if( Debug.isEnable() ){
									System.err.println("Erreur sur une ligne 'Partant' : "+e.getMessage());
									e.printStackTrace();
								}
								
							}
								
						}// fin for cellules
						
						if( Debug.isEnable() ){
							System.out.println("Cvl : "+nom+" Num : "+numCheval+" ageSexe : "+ageSexe+" musique : "+musique+" musique : "+musique+" gains : "+gains);
						}
						if( numCheval != null ){
							//DAO<Rapport> DAORapport = DAOFactory.getDAOFactory(DAOFactory.MYSQL).getRapportDAO();
							if( partantsCourse == null )
								partantsCourse = new EntitiesList();
							
							Partant partantsBean = new Partant();
							partantsBean.setCourseID(longCourse);
							partantsBean.setNumCheval(numCheval);
							partantsBean.setAgeSexe(ageSexe);
							partantsBean.setMusique(musique);
							partantsBean.setNomCheval(nom);
							partantsBean.setGains(gains);
							
							partantsCourse.add(partantsBean);
							//DAORapport.create(rapport);

							Logger.write("Adding cote : " + partantsCourse);
						}
					}
				}//fin for lignes
			}
		}
		
		return partantsCourse;		
	}


}
