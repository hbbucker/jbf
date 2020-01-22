package br.com.bbm.framework.util;

public enum BirtServlet {
	FRAMESET ("frameset"), RUN ("run"), PREVIEW ("preview"), OUTPUT ("output");
	
	
	public String runtime;
	
	BirtServlet(String runtime){
		this.runtime = runtime;
	}
}
