package org.df4ze.courses.resources;

public class Debug {

	private static boolean enable = true;
	
	private Debug() {
		// TODO Auto-generated constructor stub
	}

	public static boolean isEnable() {
		return enable;
	}

	public static void setEnable(boolean enable) {
		Debug.enable = enable;
	}

}
