/**
 * 
 */
package br.com.bbm.framework.ui;

import java.util.HashMap;

import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.ListModel;

import br.com.bbm.framework.domain.WebaufVO;

/**
 * Esta classe implementa uma Window com uma Toolbar e tabela, devem ser
 * implementados os métodos de chamada de cada botão. <br>
 * - Incluir() <br>
 * - Salvar() <br>
 * - Apagar() <br>
 * - Pesquisar() <br>
 * - Limpar() <br>
 * - Imprimir() <br>
 * - Sair() <br>
 * <b>A clase java deve estender esta classe.</b>
 * 
 * @author Hugo Bastos Bucker
 *
 */
public abstract class WindowCrudList<IT, DT> extends Window implements EventListener {

	/**
	 * Metodo Incluir para ser implementado para a Toolbar do CRUD
	 */
	public abstract void incluir();

	/**
	 * Metodo Salvar para ser implementado para a Toolbar do CRUD
	 */
	public abstract void salvar();

	/**
	 * Metodo Apagar para ser implementado para a Toolbar do CRUD
	 */
	public abstract void apagar();

	/**
	 * Metodo Pesquisar para ser implementado para a Toolbar do CRUD
	 */
	public abstract void pesquisar();

	/**
	 * Metodo Limpar para ser implementado para a Toolbar do CRUD
	 */
	public abstract void limpar();

	/**
	 * Metodo Imprimir para ser implementado para a Toolbar do CRUD
	 */
	public abstract void imprimir();

	/**
	 * Metodo Sair para ser implementado para a Toolbar do CRUD
	 */
	public abstract void sair();

	/**
	 * Vinculado ao Item selecionado
	 */
	protected IT item;

	/**
	 * ListModel Recebe os dados apartir de List fornecido por um DAO
	 */
	protected ListModel listmodel;

	/**
	 * HashMap Objeto VO vinculado ao Forumlario Detalhe
	 */
	protected DT detalheVO;

	private CrudBar crdBar = null;
	
	private Boolean[] estadoBotoes = {true,true,true,true,true,true};

	public CrudBar getCrdBar() {
		return crdBar;
	}

	public void setCrdBar(CrudBar crdBar) {
		this.crdBar = crdBar;
	}

	public WindowCrudList(String zulPage) {
		this.addCrudBar(false);
		this.zulPage = zulPage;
		this.chkButoes(zulPage);
		this.setAuditInfo(this.getInfo("ABERTURA"));
	}

	public WindowCrudList() {
		super();
		this.addCrudBar(false);
	}
	
	private void chkButoes(String key) {
		HashMap<String, HashMap> per = null;
		HashMap frm = null;
		String f[] = { "cadfrm", "altfrm", "excfrm", "", "busfrm", "impfrm" };
		String p[] = { "cadace", "altace", "excace", "", "busace", "impace" };

		try {
			per = (HashMap) getSessionManager().getSession().getAttribute("permissoes");
			frm = per.get(key);
			if ("1".equals(String.valueOf(((HashMap) getSessionManager().getSession().getAttribute(
					"usumnu")).get("codprf"))))
				return;

			for (int i = 0; i < p.length; i++) {
				this.estadoBotoes[i] = true;
				if ("N".equals((String) frm.get(f[i]))){
					this.crdBar.getBotao(i).setVisible(false);
					this.estadoBotoes[i] = false;
				} else if ("N".equals((String) frm.get(p[i]))){
					this.crdBar.getBotao(i).setDisabled(true);
					this.estadoBotoes[i] = false;
				}
			}
		} catch (Exception e) {
			for (int i = 0; i < this.estadoBotoes.length; i++)
				this.estadoBotoes[i] = true;
		}
	}

	/**
	 * Atualiza o vinculo dos atributos da classe com o formulario
	 */
	public void Vincular() {
		new AnnotateDataBinder(this).loadAll();
	}

	/**
	 * Método utilizado para retornar o Objeto vincluado a Linha
	 */
	public abstract void Selecionar();

	/**
	 * Objeto da Linha do ListBox selecionada
	 * 
	 * @return IT
	 */
	public IT getItem() {
		return item;
	}

	/**
	 * Objeto da Linha do ListBox selecionada
	 * 
	 * @param item
	 */
	public void setItem(IT item) {
		this.item = item;
	}

	/**
	 * ListModel do Listbox com a lista de dados pesquisados
	 * 
	 * @return ListModel
	 */
	public ListModel getListmodel() {
		return listmodel;
	}

	/**
	 * Configura o ListModel do Listbox com a lista de dados pesquisados
	 * 
	 * @param listmodel
	 */
	public void setListmodel(ListModel<?> listmodel) {
		this.listmodel = listmodel;
	}

	public DT getDetalheVO() {
		return detalheVO;
	}

	public void setDetalheVO(DT detalheVO) {
		this.detalheVO = detalheVO;
	}

	private boolean chkButtonAvailable(int i) {
		if (this.getCrdBar().getBotao(0).isVisible() && !this.getCrdBar().getBotao(0).isDisabled())
			return true;
		else
			return false;
	}

	@SuppressWarnings("serial")
	private void addCrudBar(boolean removeListenerButtons) {
		/**
		 * Listener para os eventos de cliques dos botoes da Toolbar
		 * this.setCtrlKeys("^i^d^s^l^r^p^q");
		 * this.addEventListener(Events.ON_CTRL_KEY, this);
		 */
		this.appendChild(this.crdBar = new CrudBar(removeListenerButtons) {
			@Override
			public void btnApagar() {
				try {
					setAuditInfo(getInfo(WebaufVO.REGISTRO_EXCLUSAO));
					apagar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void btnIncluir() {
				try {
					setAuditInfo(getInfo(WebaufVO.REGISTRO_INCLUSAO));
					incluir();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void btnPesquisar() {
				try {
					setAuditInfo(getInfo(WebaufVO.REGISTRO_CONSULTA));
					pesquisar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void btnSalvar() {
				try {
					setAuditInfo(getInfo(WebaufVO.REGISTRO_ALTERACAO));
					salvar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void btnLimpar() {
				try {
					limpar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void btnImprimir() {
				try {
					// setAuditInfo(getInfo(WebaufVO.FORMULARIO_IMPRESSAO));
					imprimir();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void btnSair() {
				try {
					setAuditInfo(getInfo(WebaufVO.FORMULARIO_FECHAMENTO));
					sair();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void onEvent(Event event) throws UiException {
		if (event.getName().equals(Events.ON_CTRL_KEY)) {
			KeyEvent key = (KeyEvent) event;
			switch (key.getKeyCode()) {
			// CTRL+I = INCLUIR
			case 73:
				if (this.chkButtonAvailable(0))
					this.incluir();
				break;
			// CTRL+S = SALVAR
			case 83:
				if (this.chkButtonAvailable(1))
					this.salvar();
				break;
			// CTRL+D = APAGAR
			case 68:
				if (this.chkButtonAvailable(2))
					this.apagar();
				break;
			// CTRL+R = LIMPAR
			case 82:
				if (this.chkButtonAvailable(3))
					this.limpar();
				break;
			// CTRL+L = PESQUISAR
			case 76:
				if (this.chkButtonAvailable(4))
					this.pesquisar();
				break;
			// CTRL+P = IMPRIMIR
			case 80:
				if (this.chkButtonAvailable(5))
					this.imprimir();
				break;
			// CTRL+Q = SAIR
			case 81:
				if (this.chkButtonAvailable(6))
					this.sair();
				break;
			}
		}
	}

}
