package br.com.bbm.framework.domain;

import java.io.Serializable;

public class WebusuVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2953647139187253609L;
	private Integer codusu;
	private String logusu;
	private String senusu;
	private String nomusu;
	private String emausu;
	private String urmusu;
	private String dtmusu;
	private String atvusu;
	private String obsusu;

	public Integer getCodusu() {
		return codusu;
	}

	public void setCodusu(Integer codusu) {
		this.codusu = codusu;
	}

	public String getLogusu() {
		return logusu;
	}

	public void setLogusu(String logusu) {
		this.logusu = logusu;
	}

	public String getSenusu() {
		return senusu;
	}

	public void setSenusu(String senusu) {
		this.senusu = senusu;
	}

	public String getNomusu() {
		return nomusu;
	}

	public void setNomusu(String nomusu) {
		this.nomusu = nomusu;
	}

	public String getEmausu() {
		return emausu;
	}

	public void setEmausu(String emausu) {
		this.emausu = emausu;
	}

	public String getUrmusu() {
		return urmusu;
	}

	public void setUrmusu(String urmusu) {
		this.urmusu = urmusu;
	}

	public String getDtmusu() {
		return dtmusu;
	}

	public void setDtmusu(String dtmusu) {
		this.dtmusu = dtmusu;
	}

	public String getAtvusu() {
		return atvusu;
	}

	public void setAtvusu(String atvusu) {
		this.atvusu = atvusu;
	}

	public String getObsusu() {
		return obsusu;
	}

	public void setObsusu(String obsusu) {
		this.obsusu = obsusu;
	}

	/**
	 * Retornar se o usuário esta ativo
	 * @return
	 */
	public boolean getAtivo(){
		return this.atvusu == null ? false : "S".equalsIgnoreCase(this.atvusu);
	}
	
	public void setAtivo(boolean atv){
		this.atvusu = atv ? "S" : "N";
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codusu == null) ? 0 : codusu.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebusuVO other = (WebusuVO) obj;
		if (codusu == null) {
			if (other.codusu != null)
				return false;
		} else if (!codusu.equals(other.codusu))
			return false;
		return true;
	}

}
