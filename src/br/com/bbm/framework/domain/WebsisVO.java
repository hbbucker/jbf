package br.com.bbm.framework.domain;

import java.io.Serializable;

public class WebsisVO implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3146327828258785052L;
	private Integer codsis;
	private String  nomsis;
	private String  urlsis;
	private String  imgsis;
	private String  tunsis;
	private String  icosis;

	public Integer getCodsis() {
		return codsis;
	}
	public void setCodsis(Integer codsis) {
		this.codsis = codsis;
	}
	public String getNomsis() {
		return nomsis;
	}
	public void setNomsis(String nomsis) {
		this.nomsis = nomsis;
	}
	public String getUrlsis() {
		return urlsis;
	}
	public void setUrlsis(String urlsis) {
		this.urlsis = urlsis;
	}
	public String getImgsis() {
		return imgsis;
	}
	public void setImgsis(String imgsis) {
		this.imgsis = imgsis;
	}
	public String getTunsis() {
		return tunsis;
	}
	public void setTunsis(String tunsis) {
		this.tunsis = tunsis;
	}
	public String getIcosis() {
		return icosis;
	}
	public void setIcosis(String icosis) {
		this.icosis = icosis;
	}
}
