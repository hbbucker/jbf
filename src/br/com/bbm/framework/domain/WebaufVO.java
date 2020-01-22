package br.com.bbm.framework.domain; 

public class WebaufVO{
	
	public static final String FORMULARIO_ABERTURA   = "ABERTURA";
	public static final String FORMULARIO_FECHAMENTO = "FECHAMENTO";
	public static final String FORMULARIO_IMPRESSAO  = "IMPRESSAO";
	public static final String REGISTRO_ALTERACAO    = "ALTERACAO";
	public static final String REGISTRO_INCLUSAO     = "INSCLUSAO";
	public static final String REGISTRO_EXCLUSAO     = "EXCLUSAO";
	public static final String REGISTRO_CONSULTA     = "CONSULTA";
	
	private Long codauf;

 	private Integer codusu;

 	private String logusu;

 	private Integer codsis;

 	private String nomsis;

 	private String frmauf;

 	private String opeauf;

 	private String nipauf;

 	private java.util.Date dtaauf;



	/**
	* codigo da auditoria
	**/
	public Long getCodauf(){
		return this.codauf; 
	}
 
	/**
	* codigo do usuario
	**/
	public Integer getCodusu(){
		return this.codusu; 
	}
 
	/**
	* login do usuario
	**/
	public String getLogusu(){
		return this.logusu; 
	}
 
	/**
	* codigo do sistema
	**/
	public Integer getCodsis(){
		return this.codsis; 
	}
 
	/**
	* nome do sistema
	**/
	public String getNomsis(){
		return this.nomsis; 
	}
 
	/**
	* formulario acessado
	**/
	public String getFrmauf(){
		return this.frmauf; 
	}
 
	/**
	* operacao feita: ABERTURA, CONSULTA, INCLUSAO, ALTERACAO, EXCLUSAO, IMPRESSAO
	**/
	public String getOpeauf(){
		return this.opeauf; 
	}
 
	/**
	* Numero do IP da maquida do usuario que acessou o formulario
	**/
	public String getNipauf(){
		return this.nipauf; 
	}
 
	/**
	* data do acesso/operacao
	**/
	public java.util.Date getDtaauf(){
		return this.dtaauf; 
	}


	/**
	* codigo da auditoria
	**/
	public void setCodauf(Long codauf){
		this.codauf = codauf; 
	}
 
	/**
	* codigo do usuario
	**/
	public void setCodusu(Integer codusu){
		this.codusu = codusu; 
	}
 
	/**
	* login do usuario
	**/
	public void setLogusu(String logusu){
		this.logusu = logusu; 
	}
 
	/**
	* codigo do sistema
	**/
	public void setCodsis(Integer codsis){
		this.codsis = codsis; 
	}
 
	/**
	* nome do sistema
	**/
	public void setNomsis(String nomsis){
		this.nomsis = nomsis; 
	}
 
	/**
	* formulario acessado
	**/
	public void setFrmauf(String frmauf){
		this.frmauf = frmauf; 
	}
 
	/**
	* operacao feita: ABERTURA, CONSULTA, INCLUSAO, ALTERACAO, EXCLUSAO, IMPRESSAO
	**/
	public void setOpeauf(String opeauf){
		this.opeauf = opeauf; 
	}
 
	/**
	* Numero do IP da maquida do usuario que acessou o formulario
	**/
	public void setNipauf(String nipauf){
		this.nipauf = nipauf; 
	}
 
	/**
	* data do acesso/operacao
	**/
	public void setDtaauf(java.util.Date dtaauf){
		this.dtaauf = dtaauf; 
	}

	@Override
	public String toString() {
		return "Auditoria(" + (dtaauf != null ? "dtaauf=" + dtaauf : "") +  
				"[" + (codusu != null ? "codusu=" + codusu + ", " : "")
				+ (logusu != null ? "logusu=" + logusu + ", " : "")
				+ (codsis != null ? "codsis=" + codsis + ", " : "")
				+ (nomsis != null ? "nomsis=" + nomsis + ", " : "")
				+ (frmauf != null ? "frmauf=" + frmauf + ", " : "")
				+ (opeauf != null ? "opeauf=" + opeauf + ", " : "")
				+ (nipauf != null ? "nipauf=" + nipauf + ", " : "") + "]";
	}

	
	
}