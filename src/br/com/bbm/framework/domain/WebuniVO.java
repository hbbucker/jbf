package br.com.bbm.framework.domain;

import java.io.Serializable;
import java.util.Date;

public class WebuniVO implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8505408743758543297L;
	private  Integer codsis; 
	/**
	 * Codigo sequencial
	 */
	private  String  coduni; 
	private  String  desuni; 
	private  Date    dteuni; 
	private  Date    dtsuni; 
	private  String  despai;
	private  String  sigsec;

	/**
	 * Codigo completo do setor conforme organograma
	 */
	private  String  siguni;
	/**
	 * Codigo do setor Pai
	 */
	private  String	 paiuni;
	/**
	 * Tipo da Unidade
	 */
	private  Integer tipuni;
	
	public Integer getCodsis() {
		return codsis;
	}
	
	public void setCodsis(Integer codsis) {
		this.codsis = codsis;
	}
	
	public String getCoduni() {
		return coduni;
	}
	
	public void setCoduni(String coduni) {
		this.coduni = coduni;
	}
	
	public String getDesuni() {
		return desuni;
	}
	
	public void setDesuni(String desuni) {
		this.desuni = desuni;
	}
	
	public Date getDteuni() {
		return dteuni;
	}
	
	public void setDteuni(Date dteuni) {
		this.dteuni = dteuni;
	}
	
	public Date getDtsuni() {
		return dtsuni;
	}
	
	public void setDtsuni(Date dtsuni) {
		this.dtsuni = dtsuni;
	}
	
	public String getSiguni() {
		return siguni;
	}
	
	public void setSiguni(String siguni) {
		this.siguni = siguni;
	}

	public String getPaiuni() {
		return paiuni;
	}

	public void setPaiuni(String paiuni) {
		this.paiuni = paiuni;
	}

	public Integer getTipuni() {
		return tipuni;
	}

	public void setTipuni(Integer tipuni) {
		this.tipuni = tipuni;
	}

	public String getDespai() {
		return despai;
	}

	public void setDespai(String despai) {
		this.despai = despai;
	}

	public String getSigsec() {
		return sigsec;
	}

	public void setSigsec(String sigsec) {
		this.sigsec = sigsec;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coduni == null) ? 0 : coduni.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebuniVO other = (WebuniVO) obj;
		if (coduni == null) {
			if (other.coduni != null)
				return false;
		} else if (!coduni.equals(other.coduni))
			return false;
		return true;
	}

	
}
