package org.df4ze.courses.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UrlGenerator {

	public static void main(String[] args) {
		String sDate1 = "01/01/2021";
		
		
		Date date1;
		try {
			date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		System.out.println(
				"https://www.geny.com/reunions-courses-pmu?date=" + new SimpleDateFormat("yyyy-MM-dd").format(date1));

		Calendar c = Calendar.getInstance();
		c.setTime(date1);

		for (int i = 0; i < 30; i++) {
			// manipulate date
			c.add(Calendar.DATE, 1);
			// convert calendar to date
			Date currentDatePlusOne = c.getTime();
			// "https://www.geny.com/reunions-courses-pmu?date=2021-03-28"
			try {
				System.out.println("https://www.geny.com/reunions-courses-pmu?date="
						+ new SimpleDateFormat("yyyy-MM-dd").format(currentDatePlusOne));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
