package br.com.bbm.framework.ui;

import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.ListModel;


/**
 * Esta classe implementa uma Window de Listagem,
 * <br> - Pesquisar()
 * <br><b>A clase java deve estender esta classe.</b>
 * @author Hugo Bastos Bucker
 *
 */
public abstract class WindowPrint extends Window implements EventListener {

	private CrudBar crdBar = null;
	public abstract void imprimir();
	
	public CrudBar getCrdBar() {
		return crdBar;
	}

	public void setCrdBar(CrudBar crdBar) {
		this.crdBar = crdBar;
	}
	
	public WindowPrint(){
		super();
		
		this.setCtrlKeys("^i^d^s^l^r^p^q");
		this.addEventListener(Events.ON_CTRL_KEY, this);
		
		this.crdBar = new CrudBar(){
			@Override
			public void btnApagar() {}
			@Override
			public void btnImprimir() {imprimir();}
			@Override
			public void btnIncluir() {}
			@Override
			public void btnLimpar() {}
			@Override
			public void btnPesquisar() {}
			@Override
			public void btnSair() {sair();}
			@Override
			public void btnSalvar() {}
		};
		for (int i = 0; i < 5; i++)
			this.getCrdBar().getBotao(i).setVisible(false);
		
		this.appendChild(this.crdBar);
		atualizar();
	}
	
	private void sair(){
		this.detach();
	}

	/**
	 * Atualiza o vinculo dos atributos da classe com o formulario
	 */
	public void atualizar(){
		new AnnotateDataBinder(this).loadAll();
	}
	
	public void onEvent(Event event) throws UiException {
		if (event.getName().equals(Events.ON_CTRL_KEY)) {
			KeyEvent key = (KeyEvent) event;
			switch (key.getKeyCode()) {
			// CTRL+P = IMPRIMIR
			case 80:
				this.imprimir();
				break;
			// CTRL+Q = SAIR
			case 81:
				this.sair();
				break;
			}
		}
	}	
}