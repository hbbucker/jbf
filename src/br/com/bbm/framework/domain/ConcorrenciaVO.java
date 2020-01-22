package br.com.bbm.framework.domain;

import java.util.Date;

public interface ConcorrenciaVO {

	/**
	 * Seta a idade do objeto
	 * @return
	 */
	Date getAge();
	
	/**
	 * Retorna a idade do Objeto
	 * @param age
	 */
	void setAge(Date age);
}
