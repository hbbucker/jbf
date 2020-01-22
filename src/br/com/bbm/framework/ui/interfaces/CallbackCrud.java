package br.com.bbm.framework.ui.interfaces;

import org.zkoss.zk.ui.WrongValueException;

/**
 * Interface para implementa��o de callback para as classes de Crud Generico do sistema
 * @author bucker
 *
 * @param <T>
 */
public interface CallbackCrud<T> {

	public void onIncluirSuccess(T Object);
	public void onIncluirFailed(Exception e) throws WrongValueException;
	
	public void onSalvarSuccess(T Object);
	public void onSalvarFailed(Exception e) throws WrongValueException;
	
	public void onApagarSuccess(T Object);
	public void onApagarFailed(Exception e) throws WrongValueException;
}
