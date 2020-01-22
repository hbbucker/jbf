package br.com.bbm.framework.config;

import java.util.ResourceBundle;

public class Config {

	public static String INSTITUICAO;
	
	static {
		ResourceBundle myResources = ResourceBundle.getBundle("br.com.bbm.framework.config.Parameters");
		INSTITUICAO = myResources.getString("instituicao");
	}
}
