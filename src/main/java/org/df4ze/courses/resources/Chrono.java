package org.df4ze.courses.resources;

import java.util.Date;

public class Chrono {

	
	private long time1;
	private long time2;
	private boolean print;

	public Chrono( boolean print ) {
		this.print = print;
	}

	public void pick() {
		if( Debug.isEnable() )
			time1 = new Date().getTime();
	}
	public long compare( String txt ) {
		long delta = 0;
		if( print ) {
			time2 = new Date().getTime();
			delta = time2-time1;
			System.out.println(txt+" : "+delta);
		}
		return delta;
	}
}
