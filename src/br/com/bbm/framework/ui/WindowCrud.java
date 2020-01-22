package br.com.bbm.framework.ui;

import java.util.HashMap;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;

import br.com.bbm.framework.domain.WebaufVO;

/**
 * Esta classe implementa uma Window com uma Toolbar, devem ser implementados os
 * metodos de chamada de cada botao. <br>
 * Incluir(), Salvar(), Apagar(), Pesquisar(), Limpar(), Imprimir(), Sair() <br>
 * <b>A classe java deve estender esta classe.</b>
 * 
 * @author Hugo Bastos Bucker
 * 
 */
@SuppressWarnings("unchecked")
public abstract class WindowCrud extends Window implements EventListener {

	private static final long serialVersionUID = -2726993296221714093L;

	/**
	 * {@link CrudBar#btnIncluir()}
	 */
	@Command("incluir")
	public abstract void incluir() throws InterruptedException;

	/**
	 * {@link CrudBar#btnSalvar()}
	 */
	@Command("salvar")
	public abstract void salvar() throws InterruptedException;

	/**
	 * {@link CrudBar#btnApagar()}
	 */
	@Command("apagar")
	public abstract void apagar() throws InterruptedException;

	/**
	 * {@link CrudBar#btnPesquisar()}
	 */
	@Command("pesquisar")
	public abstract void pesquisar() throws InterruptedException;

	/**
	 * {@link CrudBar#btnLimpar()}
	 */
	@Command("limpar")
	public abstract void limpar() throws InterruptedException;

	/**
	 * {@link CrudBar#btnImprimir()}
	 */
	@Command("imprimir")
	public abstract void imprimir() throws InterruptedException;

	/**
	 * {@link CrudBar#btnSair()}
	 */
	@Command("sair")
	public abstract void sair() throws InterruptedException;

	private CrudBar crdBar = null;

	private Boolean[] estadoBotoes = { true, true, true, true, true, true };

	private String idWindow;

	private boolean removeListenerButtons;

	public CrudBar getCrdBar() {
		return crdBar;
	}

	public void setCrdBar(CrudBar crdBar) {
		this.crdBar = crdBar;
	}

	/**
	 * Não checa as permissões
	 */
	public WindowCrud() {
		this.addCrudBar(false);
	}

	/**
	 * Recebe o nome do arquivo ZUL par fazer as checagens de permissões
	 * 
	 * @param zulPage
	 */
	public WindowCrud(String zulPage) {
		this.zulPage = zulPage;
		this.initConfigBar(zulPage, false);
	}

	/**
	 * Recebe o nome do arquivo ZUL par fazer as checagens de permissões
	 * 
	 * @param zulPage
	 *            - Nome do Aquivo ZUL
	 * @param removeListenerButtons
	 *            - Habilita ou desabilita as teclas de atalho
	 */
	public WindowCrud(String zulPage, boolean removeListenerButtons) {
		this.zulPage = zulPage;
		this.removeListenerButtons = removeListenerButtons;
		this.initConfigBar(zulPage, removeListenerButtons);
	}
	
	public WindowCrud(String zulPage, String idWindow, boolean removeListenerButtons) {
		this.idWindow = idWindow;
		this.zulPage = zulPage;
		this.removeListenerButtons = removeListenerButtons;
		this.initConfigBar(zulPage, removeListenerButtons);
	}

	@Command
	@NotifyChange({"*","."})
	protected void initConfigBar(String zulPage, boolean removeListenerButtons) {
		this.addCrudBar(removeListenerButtons);
		this.zulPage = zulPage;
		this.chkButoes(zulPage);
		this.setAuditInfo(this.getInfo("ABERTURA"));
		this.addEventListener(Events.ON_CREATE, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				setChkPermissao(true);
			}
		});
	}

	@SuppressWarnings("serial")
	private void addCrudBar(boolean removeListenerButtons) {
		/**
		 * Listener para os eventos de cliques dos botoes da Toolbar
		 */

		if (!removeListenerButtons) {
			this.setCtrlKeys("^i^d^s^l@r@p@q");
			this.addEventListener(Events.ON_CTRL_KEY, this);
		}
		
		this.crdBar = new CrudBar(removeListenerButtons) {
			@Override
			public void btnApagar() {
				try {
					setAuditInfo(getInfo(WebaufVO.REGISTRO_EXCLUSAO));
					apagar();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void btnIncluir() {
				try {
					setAuditInfo(getInfo(WebaufVO.REGISTRO_INCLUSAO));
					incluir();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void btnPesquisar() {
				try {
					setAuditInfo(getInfo(WebaufVO.REGISTRO_CONSULTA));
					pesquisar();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void btnSalvar() {
				try {
					setAuditInfo(getInfo(WebaufVO.REGISTRO_ALTERACAO));
					salvar();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void btnLimpar() {
				try {
					limpar();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void btnImprimir() {
				try {
					// setAuditInfo(getInfo(WebaufVO.FORMULARIO_IMPRESSAO));
					imprimir();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void btnSair() {
				try {
					setAuditInfo(getInfo(WebaufVO.FORMULARIO_FECHAMENTO));
					sair();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		//Quando usando o mvvm precisa definir o ID da Window
		if (this.idWindow != null) {
			org.zkoss.zul.Window win = (org.zkoss.zul.Window) Executions.getCurrent().getDesktop().getFirstPage()
											.getFellow("ConteudoCentral").getFellow(idWindow);
			if (win != null)
				win.appendChild(this.crdBar);
		} else 
			this.appendChild(this.crdBar);
	}

	private boolean chkButtonAvailable(int i) {
		if (this.getCrdBar().getBotao(i).isVisible() && !this.getCrdBar().getBotao(i).isDisabled())
			return true;
		else
			return false;
	}

	public void onEvent(Event event) throws UiException {
		if (event.getName().equals(Events.ON_CTRL_KEY)) {
			KeyEvent key = (KeyEvent) event;
			try {
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
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Teste se o usuário tem permissão para abrir a janela
	 * 
	 * @param chk
	 */
	public void setChkPermissao(boolean chk) {
		if (!chk)
			return;
		HashMap<String, HashMap> per = null;
		HashMap frm = null;

		if (this.zulPage == null)
			return;
		try {
			per = (HashMap)	getSessionManager().getSession().getAttribute("permissoes");
			frm = per.get(this.zulPage);

			if (frm == null) {
				this.detach();
				// Messagebox.show("Usuário não possui permissão para abrir esta
				// tela.");
			}

		} catch (Exception e) {

		}
	}

	private void chkButoes(String key) {
		HashMap<String, HashMap> per = null;
		HashMap frm = null;
		String f[] = { "cadfrm", "altfrm", "excfrm", "", "busfrm", "impfrm" };
		String p[] = { "cadace", "altace", "excace", "", "busace", "impace" };

		try {
			per = (HashMap) getSessionManager().getSession().getAttribute("permissoes");
			frm = per.get(key);
			if ("1".equals(String.valueOf(((HashMap) getSessionManager().getSession().getAttribute("usumnu")).get("codprf"))))
				return;

			for (int i = 0; i < p.length; i++) {
				this.estadoBotoes[i] = true;
				if ("N".equals((String) frm.get(f[i]))) {
					this.crdBar.getBotao(i).setVisible(false);
					this.estadoBotoes[i] = false;
				} else if ("N".equals((String) frm.get(p[i]))) {
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
	 * Controla os Botões de Incluir, Alterar e Deletar
	 * 
	 * @since 19 abr 2011
	 * @param estado
	 *            - true | false <br>
	 *            <li><b>true</b> - Desabilita o Incluir e habilita Alterar e
	 *            Deletar</li>
	 *            <li><b>false</b> - Habilita o Incluir e Desabilita o Alterar e
	 *            Deletar</li>
	 */
	@Command
	@NotifyChange("*")
	public void ctrlBotoesIncAltDel(@BindingParam("estado") boolean estado) {
		if (this.estadoBotoes[0])
			this.crdBar.getBotao(0).setDisabled(estado);

		if (this.estadoBotoes[1])
			this.crdBar.getBotao(1).setDisabled(!estado);

		if (this.estadoBotoes[2])
			this.crdBar.getBotao(2).setDisabled(!estado);
	}

}