package br.com.bbm.framework.ui;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.impl.BinderUtil;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Toolbar;

/**
 * Toolbar padrao para ser utilizadas com as telas CRUD do modelo
 * 
 * @author Hugo B. Bucker
 * @since 03 fev 2009
 * @version 0.0.1b
 * 
 */
public abstract class CrudBar extends Toolbar implements EventListener {
	private static final long serialVersionUID = -8793631862368577896L;

	private boolean removeListener = false;
	
	/**
	 * Botão de incluir
	 */
	public static final int BTN_INCLUIR = 0;
	/**
	 * Botão de salvar
	 */
	public static final int BTN_SALVAR = 1;
	/**
	 * Botão de apagar
	 */
	public static final int BTN_APAGAR = 2;
	/**
	 * Botão de limpar
	 */
	public static final int BTN_LIMPAR = 3;
	/**
	 * Botão de pesquisar
	 */
	public static final int BTN_PESQUISAR = 4;
	/**
	 * Botão de imprimir
	 */
	public static final int BTN_IMPRIMIR = 5;

	/**
	 * Botão de sair
	 */
	public static final int BTN_SAIR = 6;

	/**
	 * Metodo chamado para inclusao de registro
	 */
	public abstract void btnIncluir();

	/**
	 * Metodo chamado para salvar/alterar um registro
	 */
	public abstract void btnSalvar();

	/**
	 * Metodo chamada na exclusao de um registro
	 */
	public abstract void btnApagar();

	/**
	 * Metodo chamado para pesquisa de registro
	 */
	public abstract void btnPesquisar();

	/**
	 * Metodo chamado para limpar a tela
	 */
	public abstract void btnLimpar();

	/**
	 * Metodo chamado para imprimir relatorio
	 */
	public abstract void btnImprimir();

	/**
	 * Metodo chamado para fechar a janela
	 */
	public abstract void btnSair();

	private Button[] btn = new Button[10];
	private String dirImg = "/images/toolbar/";
	private String[] tip = { Labels.getRequiredLabel("button.add")
						   , Labels.getRequiredLabel("button.save")
						   , Labels.getRequiredLabel("button.delete")
						   , Labels.getRequiredLabel("button.clean")
						   , Labels.getRequiredLabel("button.search")
						   , Labels.getRequiredLabel("button.print")
						   , Labels.getRequiredLabel("button.close") };
	
	private String[] img = { "incluir", "salvar", "apagar", "limpar", "pesquisar", "imprimir", "sair" };

	private String[] iCls = { Labels.getRequiredLabel("button.img.add")
						    , Labels.getRequiredLabel("button.img.save")
						    , Labels.getRequiredLabel("button.img.delete")
						    , Labels.getRequiredLabel("button.img.clean")
						    , Labels.getRequiredLabel("button.img.search")
						    , Labels.getRequiredLabel("button.img.print")
						    , Labels.getRequiredLabel("button.img.close") };

	
	private int nBtn = 0;
	
	private String toolbarStyle = "btn-group btn-group-justified";
	private String buttonStyle = Labels.getRequiredLabel("button.css");
	
	public CrudBar(boolean removeListenerButtons) {
		super();
		initConfiguracoes(removeListenerButtons);		
	}
	
	public CrudBar() {
		super();
		initConfiguracoes(false);
	}
	
	public void initConfiguracoes(boolean removeListenerButtons){
		this.removeListener = removeListenerButtons; 
		initButtons();
		this.setSclass(toolbarStyle);
		this.setWidth("100%");
		
	}
	
	public String getToolbarStyle() {
		return toolbarStyle;
	}
	
	public void setToolbarStyle(String toolbar){
		this.toolbarStyle = toolbar;
		this.setStyle((this.toolbarStyle != null ? this.toolbarStyle : "") + " btn-group btn-group-justified");
	}
	
	public String getButtonStyle() {
		return buttonStyle;
	}

	public void setButtonStyle(String buttonStyle) {
		this.buttonStyle = buttonStyle;
	}

	/**
	 * Retorna o diretorio em que as imagens padroes estao armazenas no pacote
	 * 
	 * @return String
	 */
	public String getDirImg() {
		return dirImg;
	}

	/**
	 * Configura o diretorio de imagens
	 * 
	 * @param dirImg
	 */
	public void setDirImg(String dirImg) {
		this.dirImg = dirImg;
	}

	/**
	 * Retorna as dicas configuradas para os botoes
	 * 
	 * @return String[]
	 */
	public String[] getTip() {
		return tip;
	}

	/**
	 * Configura as dicas dos botoes
	 * 
	 * @param tip
	 */
	public void setTip(String[] tip) {
		this.tip = tip;
	}

	/**
	 * Retorna os nomes dos arquivos das imagens dos botoes
	 * 
	 * @return String[]
	 */
	public String[] getImg() {
		return img;
	}

	/**
	 * Configura os nomes dos arquivos das imagens dos botoes
	 * 
	 * @param img
	 */
	public void setImg(String[] img) {
		this.img = img;
	}

	/**
	 * Seta as configuracoes dos botoes, preparando-os para exibixao
	 */
	protected void initButtons() {
		try {
			for (int i = 0; i < img.length; i++) {
				btn[i] = new Button();
				btn[i].setId(img[i].split("\\.")[0].replaceAll("btn", ""));
				btn[i].setTooltiptext(tip[i]);
				btn[i].setTooltip(tip[i]);

				btn[i].addEventListener(Events.ON_CLICK, this);
				Map<String, String[]> par = new HashMap<String, String[]>();
				par.put("value", new String[]{"'" + img[i] + "'"});
				
				btn[i].addAnnotation("onClick", "command", par);
				btn[i].setOrient("vertical");
				btn[i].setIconSclass(iCls[i]);
				btn[i].setZclass(this.buttonStyle);
				this.appendChild(btn[i]);
				this.nBtn++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onEvent(Event event) throws UiException {
		if (Events.ON_CLICK.equals(event.getName()) || Events.ON_CTRL_KEY.equals(event.getName())) {
			String id = event.getTarget().getId().toLowerCase();
			if ("incluir".equals(id))
				this.btnIncluir();
			else if ("salvar".equals(id))
				this.btnSalvar();
			else if ("apagar".equals(id))
				this.btnApagar();
			else if ("pesquisar".equals(id))
				this.btnPesquisar();
			else if ("limpar".equals(id))
				this.btnLimpar();
			else if ("imprimir".equals(id))
				this.btnImprimir();
			else if ("sair".equals(id))
				this.btnSair();
		}
	}

	/**
	 * Adiciona um botao a barra
	 * 
	 * @param b
	 * @return boolean
	 */
	public boolean adicionarBotao(Button b) {
		try {
			btn[this.nBtn++] = b;
		} catch (Exception ex) {
			return false;
		}
		return true;

	}

	/**
	 * Retorna um botao da barra 0 = Incluir, 1 = Salvar, 2 = Apagar, 3 =
	 * Limpar, 4 = Pesquisar, 5 = Imprimir, 6 = Sair
	 * 
	 * @param i
	 *            indice do botao a ser retornado
	 * @return Button
	 */
	public Button getBotao(int i) {
		return btn[i];
	}

	/**
	 * Modifica os parametros do botao
	 * 
	 * @param btn
	 *            int - Referencia do botao: 0 = Incluir, 1 = Salvar, 2 =
	 *            Apagar, 3 = Limpar, 4 = Pesquisar, 5 = Imprimir, 6 = Sair
	 * @param imagem
	 *            String - Caminho da imagem do botao
	 * @param tip
	 *            String - Dica do botao
	 */
	public void setBotao(int btn, String imagem, String tip) {
		this.getBotao(btn).setImage(imagem);
		this.getBotao(btn).setTooltiptext(tip);
		this.getBotao(btn).setTooltip(tip);
	}
}