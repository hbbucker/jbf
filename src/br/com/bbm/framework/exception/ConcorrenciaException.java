package br.com.bbm.framework.exception;

public class ConcorrenciaException extends Exception {

	private static final long serialVersionUID = -4181121510313104602L;

	public ConcorrenciaException(String erro){
		super(erro);
	}
	
}
