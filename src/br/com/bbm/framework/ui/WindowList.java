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
public abstract class WindowList<T> extends Window implements EventListener {

	/**
	 * Vinculado ao Item selecionado
	 */
	protected T item;
	
	/**
	 * ListModel
	 * Recebe os dados apartir de List fornecido por um DAO
	 */
	protected ListModel listmodel;
	
	private CrudBar crdBar = null;
	public CrudBar getCrdBar() {
		return crdBar;
	}
	
	public void setCrdBar(CrudBar crdBar) {
		this.crdBar = crdBar;
	}
	
	public WindowList(){
		super();
		
		this.setCtrlKeys("^i^d^s^l^r^p^q");
		this.addEventListener(Events.ON_CTRL_KEY, this);
		
		this.crdBar = new CrudBar(){
			@Override
			public void btnApagar() {}
			@Override
			public void btnImprimir() {}
			@Override
			public void btnIncluir() {}
			@Override
			public void btnLimpar() {limpar();}
			@Override
			public void btnPesquisar() {pesquisar();}
			@Override
			public void btnSair() {sair();}
			@Override
			public void btnSalvar() {}
		};
		//Ocutando botões de CRUD
		for (int i = 0; i < 3; i++)
			this.crdBar.getBotao(i).setVisible(false);
		//Ocultado botão de Imprimir
		this.crdBar.getBotao(5).setVisible(false);
		
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
	
	/**
	 * Metodo utilizado para retornar o Objeto vincluado a Linha
	 */
	public void selecionar(){
	    Events.postEvent("onClose", this, item);
//	    this.detach();
	}
	
	/**
	 * Meto abstrado para implementacao da pesquisa<br>
	 * Por definicao use o metodo <b>setListmodel</b> para popular a Lista dentro do <b>pesquisar</b>
	 */
	public abstract void pesquisar();
	/**LIMPAR
	 * limpar campos de pesquisa<br>
	 * </b>
	 */
	public void limpar(){
		System.out.println("Metodo limpar nao foi implementado");
	}
	/**
	 * Objeto da Linha do ListBox selecionada
	 * @return T
	 */
	public T getItem() {
		return item;
	}

	/**
	 * Objeto da Linha do ListBox selecionada
	 * @param item
	 */
	public void setItem(T item) {
		this.item = item;
	}

	public ListModel getListmodel() {
		return listmodel;
	}

	public void setListmodel(ListModel listmodel) {
		this.listmodel = listmodel;
	}	
	
	public void onEvent(Event event) throws UiException {
		if (event.getName().equals(Events.ON_CTRL_KEY)) {
			KeyEvent key = (KeyEvent) event;
			switch (key.getKeyCode()) {
			// CTRL+Q = SAIR
			case 81:
				this.sair();
				break;
			
			// CTRL+R = LIMPAR
		    case 82:
				this.limpar();
			break;
		   // CTRL+L = PESQUISAR
		    case 76:
				this.pesquisar();
			break;
			}
		
	}
}
}