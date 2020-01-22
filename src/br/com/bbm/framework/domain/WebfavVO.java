package br.com.bbm.framework.domain; 

public class WebfavVO{
	private Integer codfav;

 	private String desfav;

 	private String urlfr;

 	private Integer codusu;

 	private Integer codsis;

 	private Integer codprf;

 	private String coduni;

    

	public WebfavVO(Integer codfav, String desfav, String urlfr,
			Integer codusu, Integer codsis, Integer codprf, String coduni) {
		super();
		this.codfav = codfav;
		this.desfav = desfav;
		this.urlfr = urlfr;
		this.codusu = codusu;
		this.codsis = codsis;
		this.codprf = codprf;
		this.coduni = coduni;
	}

	/**
	* null
	**/
	public Integer getCodfav(){
		return this.codfav; 
	}
 
	/**
	* null
	**/
	public String getDesfav(){
		return this.desfav; 
	}
 
	/**
	* null
	**/
	public String getUrlfr(){
		return this.urlfr; 
	}
 
	/**
	* null
	**/
	public Integer getCodusu(){
		return this.codusu; 
	}
 
	/**
	* null
	**/
	public Integer getCodsis(){
		return this.codsis; 
	}
 
	/**
	* null
	**/
	public Integer getCodprf(){
		return this.codprf; 
	}
 
	/**
	* null
	**/
	public String getCoduni(){
		return this.coduni; 
	}


	/**
	* null
	**/
	public void setCodfav(Integer codfav){
		this.codfav = codfav; 
	}
 
	/**
	* null
	**/
	public void setDesfav(String desfav){
		this.desfav = desfav; 
	}
 
	/**
	* null
	**/
	public void setUrlfr(String urlfr){
		this.urlfr = urlfr; 
	}
 
	/**
	* null
	**/
	public void setCodusu(Integer codusu){
		this.codusu = codusu; 
	}
 
	/**
	* null
	**/
	public void setCodsis(Integer codsis){
		this.codsis = codsis; 
	}
 
	/**
	* null
	**/
	public void setCodprf(Integer codprf){
		this.codprf = codprf; 
	}
 
	/**
	* null
	**/
	public void setCoduni(String coduni){
		this.coduni = coduni; 
	}

}