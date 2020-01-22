package br.com.bbm.framework.domain;

import java.io.Serializable;

public class UsumnuVO implements Serializable {

	private static final long serialVersionUID = -7014016317639501662L;
	private Integer codsis;
	private Integer codusu;
	private String coduni;
	private Integer codprf;
	private Integer codAudit;
	private Boolean logsis;

	public Integer getCodsis() {
		return codsis;
	}

	public void setCodsis(Integer codsis) {
		this.codsis = codsis;
	}

	public Integer getCodusu() {
		return codusu;
	}

	public void setCodusu(Integer codusu) {
		this.codusu = codusu;
	}

	public String getCoduni() {
		return coduni;
	}

	public void setCoduni(String coduni) {
		this.coduni = coduni;
	}

	public Integer getCodAudit() {
		return codAudit;
	}

	public void setCodAudit(Integer codAudit) {
		this.codAudit = codAudit;
	}

	public Boolean getLogsis() {
		return logsis;
	}

	public void setLogsis(Boolean logsis) {
		this.logsis = logsis;
	}

	public Integer getCodprf() {
		return codprf;
	}

	public void setCodprf(Integer codprf) {
		this.codprf = codprf;
	}

}
