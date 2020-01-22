package br.com.bbm.framework.ui;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlNativeComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Script;
import org.zkoss.zul.Span;

import br.com.bbm.framework.domain.VwebmnuVO;
import br.com.bbm.framework.manager.SessionManager;
import br.com.bbm.framework.util.WinUtils;

/**
 * Monta a barra de menu do sistema com as permissoes do usuario
 * 
 * @author Hugo B. Bucker, Joacir Ilha
 * @since 03 fev 2009
 * @version 0.0.2
 * 
 */
@SuppressWarnings({ "unchecked", "serial" })
public class MnuGeral2 extends HtmlNativeComponent {
	/**
	 * 
	 */
	private Webmenu wm = new Webmenu();
	private HashMap<String, HashMap<String, Object>> permissoes;
	private SessionManager sessionManager;
	private boolean menuPermissao = false;

	protected String menuIcon = "fa fa-folder-open";
	protected String frmIcon = "fa fa-wpforms";
	protected String impIcon = "fa fa-print";
	protected String edtIcon = "fa fa-pencil";
	protected String cstIcon = "fa fa-search";

	public MnuGeral2(SessionManager session) {
		// Verifica se o usuario tem acesso a mais de uma unidade pelo
		// sistema/perfil

		this.sessionManager = session;
		this.setTag("nav");
		this.setDynamicProperty("class", "main-nav");
		this.setDynamicProperty("role", "navigation");
		
		HashMap<String, Object> usumnu = (HashMap<String, Object>) sessionManager.getSession().getAttribute("usumnu");
		int uni = checkUnidadeSessao(usumnu);
		
		//Informações Usuario e Sistema
		if (Executions.getCurrent().getBrowser("mobile") != null)
			criarBarraInformacaoMobile();
		else	
			criarBarraInformacao();
		
		//Barra de navegação Elemento Raiz
		HtmlNativeComponent rootUl = new HtmlNativeComponent("ul");
		rootUl.setId("main-menu");
		rootUl.setDynamicProperty("class", Labels.getRequiredLabel("smartmenu.theme"));
		this.appendChild(rootUl);
		
		if (uni == 1) {
			// Monta o Menu do sistema
			this.criaMenu(usumnu, rootUl);
		} else if (uni > 1) {
			this.menuPermissao = true;
			WinUtils.abreSetor();
		} else {
			this.menuPermissao = false;
			return;
		}
		
		Script script1 = new Script();
		script1.setSrc("/assets/smartmenus/js/jquery.smartmenus.js");

		Script script2 = new Script();
		script2.setSrc("/assets/smartmenus/js/jquery.smartmenus.bootstrap-4.js");
		
		Script script3 = new Script();
		script3.setSrc("/assets/smartmenus/js/custom.smartmenus.js");
		
		this.appendChild(script1);
		this.appendChild(script2);
		this.appendChild(script3);		
		
//		Clients.evalJavaScript(scriptMenu());
	}

	private int checkUnidadeSessao(HashMap<String, Object> usumnu){
		List<HashMap<String, Object>> uni = wm.getUnidades(usumnu);
		if (uni.size() == 1) {
			if (usumnu.get("coduni") == null) {
				usumnu.put("coduni", uni.get(0).get("coduni"));
				usumnu.put("desuni", uni.get(0).get("desuni"));
				usumnu.put("siguni", uni.get(0).get("siguni"));
			}
			
			usumnu.put("tipfrm", 1);
			usumnu.put("nivfrm", 1);
			usumnu.put("hiefrm", "");
			
		}
		
		try {
			usumnu.put("codprf", uni.get(0).get("codprf"));
			sessionManager.getSession().setAttribute("usumnu", usumnu);
			
			return uni.size();
		}catch (NullPointerException e) {
			return 0;
		}
	}
	
	private void criarBarraInformacao(){
		HtmlNativeComponent toogleButton = new HtmlNativeComponent("input");
		toogleButton.setId("main-menu-state");
		toogleButton.setDynamicProperty("type", "checkbox");
		this.appendChild(toogleButton);
		
		HtmlNativeComponent label = new HtmlNativeComponent("label");
		label.setDynamicProperty("class", "main-menu-btn");
		label.setDynamicProperty("for", "main-menu-state");
		
		HtmlNativeComponent span = new HtmlNativeComponent("span");
		span.setDynamicProperty("class", "main-menu-btn-icon");
		span.setPrologContent("Toggle main menu visibility");
		
		label.appendChild(span);
		this.appendChild(label);
		
		HtmlNativeComponent h6 = new HtmlNativeComponent("h6");
		h6.setDynamicProperty("class", "nav-brand");
		
		org.zkoss.zul.Div linha01 = new org.zkoss.zul.Div();
		linha01.setZclass("row align-items-center");

		/** Botões de sair e home */
		org.zkoss.zul.Div col01 = new org.zkoss.zul.Div();
		col01.setZclass("col-3 col-md-auto custom-line-buttons");

		Span btnHome = new Span();
		btnHome.setSclass("fa fa-home custom-button");
		btnHome.setTooltiptext(Labels.getRequiredLabel("system.unit.tooltip"));

		Span btnSair = new Span();
		btnSair.setSclass("fa fa-power-off custom-button");
		btnSair.setTooltiptext(Labels.getRequiredLabel("system.exit.tooltip"));

		// Adicona o evento ao botao de sair do sistema
		btnSair.addEventListener(Events.ON_CLICK, new EventListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onEvent(Event event) throws Exception {
				final Integer sis = sessionManager.getCodSis();
				if (Messagebox.show(Labels.getRequiredLabel("system.exit.question"), Labels.getRequiredLabel("system.warning") + "!!!", Messagebox.YES | Messagebox.NO,
						Messagebox.QUESTION) == Messagebox.YES) {
					sessionManager.limpaSessao();
					Executions.getCurrent().sendRedirect("/zk/index?sistema=" + sis);
				}
			}

		});

		// Adiciona o evento ao botao de selecao de unidade
		btnHome.addEventListener(Events.ON_CLICK, new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				WinUtils.abreSetor();
			}

		});

		btnHome.setParent(col01);
		btnSair.setParent(col01);
		/** */

		/** Nome do sistema */
		org.zkoss.zul.Div col02 = new org.zkoss.zul.Div();
		col02.setZclass("col-9 col-md-auto custom-line-systemname");

		HtmlNativeComponent nomeSistema = new HtmlNativeComponent("p");
		nomeSistema.setDynamicProperty("class", "h4-responsive custom-systemname ");
		nomeSistema.setPrologContent(sessionManager.getNomsis());
		nomeSistema.setParent(col02);
		/** */

		/** Usuário */
		org.zkoss.zul.Div col04 = new org.zkoss.zul.Div();
		col04.setZclass("col-12 col-md-auto custom-line-username");

		HtmlNativeComponent usuarioSistema = new HtmlNativeComponent("p");
		usuarioSistema.setDynamicProperty("class", "custom-username");
		HtmlNativeComponent labelusuario = new HtmlNativeComponent("small");
		labelusuario.setDynamicProperty("class", "text-muted custom-lb-username");
		labelusuario.setPrologContent("<i class='fa fa-user' aria-hidden='true' title='" + Labels.getLabel("field.userame") + "'></i>");
		labelusuario.setParent(usuarioSistema);
		usuarioSistema.setEpilogContent(sessionManager.getLogin() + " - " + sessionManager.getUsuario());
		usuarioSistema.setParent(col04);
		/** */
		
		/** Unidade */
		HtmlNativeComponent unidadeSistema = new HtmlNativeComponent("p");
		unidadeSistema.setDynamicProperty("class", "custom-username");
		HtmlNativeComponent labelUnidade = new HtmlNativeComponent("small");
		labelUnidade.setDynamicProperty("class", "text-muted custom-lb-username");
		labelUnidade.setPrologContent("<i class='fa fa-tags' aria-hidden='true' title='" + Labels.getLabel("field.unit") + "'></i>");
		labelUnidade.setParent(unidadeSistema);
		unidadeSistema.setEpilogContent(sessionManager.getUnidade());
		unidadeSistema.setParent(col04);
		/** */
		
		col01.setParent(linha01);
		col02.setParent(linha01);
		col04.setParent(linha01);
		
		h6.appendChild(linha01);
		this.appendChild(h6);
		
	}
	
	private void criarBarraInformacaoMobile(){
		HtmlNativeComponent toogleButton = new HtmlNativeComponent("input");
		toogleButton.setId("main-menu-state");
		toogleButton.setDynamicProperty("type", "checkbox");
		this.appendChild(toogleButton);
		
		HtmlNativeComponent label = new HtmlNativeComponent("label");
		label.setDynamicProperty("class", "main-menu-btn");
		label.setDynamicProperty("for", "main-menu-state");
		
		HtmlNativeComponent span = new HtmlNativeComponent("span");
		span.setDynamicProperty("class", "main-menu-btn-icon");
		span.setPrologContent("Toggle main menu visibility");
		
		label.appendChild(span);
		this.appendChild(label);
		
		HtmlNativeComponent h6 = new HtmlNativeComponent("div");
		h6.setDynamicProperty("class", "nav-brand container-fluid");
//		h6.setDynamicProperty("style", "color:white; margin-top: 1px; position: absolute; z-index: 1;");
		
		org.zkoss.zul.Div linha01 = new org.zkoss.zul.Div();
		linha01.setZclass("row align-items-center");

		/** Botões de sair e home */
		org.zkoss.zul.Div col01 = new org.zkoss.zul.Div();
		col01.setZclass("col-2 col-md-auto custom-line-buttons");
//		col01.setStyle("padding-left: 5px; padding-bottom: 5px;padding-right: 5px;");

		Span btnHome = new Span();
		btnHome.setSclass("fa fa-home custom-button");
//		btnHome.setStyle("font-size:25px; color: #4dbd74; vertical-align: middle; margin-right: 6px;");
		btnHome.setTooltiptext("Alterar Unidade");

		Span btnSair = new Span();
		btnSair.setSclass("fa fa-power-off custom-button");
//		btnSair.setStyle("font-size:25px; color: red; vertical-align: middle;");
		btnSair.setTooltiptext("Sair do Sistema");

		// Adicona o evento ao botao de sair do sistema

		btnSair.addEventListener(Events.ON_CLICK, new EventListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onEvent(Event event) throws Exception {
				final Integer sis = sessionManager.getCodSis();
				if (Messagebox.show("Deseja realmente sair do sistema?", "Aviso!!!", Messagebox.YES | Messagebox.NO,
						Messagebox.QUESTION) == Messagebox.YES) {
					sessionManager.limpaSessao();
					Executions.getCurrent().sendRedirect("/zk/index?sistema=" + sis);
				}
			}

		});

		// Adiciona o evento ao botao de selecao de unidade
		btnHome.addEventListener(Events.ON_CLICK, new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				WinUtils.abreSetor();
			}
		});

		btnHome.setParent(col01);
		btnSair.setParent(col01);
		/** */

		/** Nome do sistema */
		org.zkoss.zul.Div col02 = new org.zkoss.zul.Div();
		col02.setZclass("col-8 col-md-auto custom-line-systemname");

		HtmlNativeComponent nomeSistema = new HtmlNativeComponent("p");
		nomeSistema.setDynamicProperty("class", "h6 custom-systemname");
		nomeSistema.setPrologContent(sessionManager.getNomsis());
		nomeSistema.setParent(col02);
		/** */

		/** Usuário */
		HtmlNativeComponent labelusuario = new HtmlNativeComponent("small");
		labelusuario.setDynamicProperty("class", "text-muted custom-username");
		labelusuario.setPrologContent("<i class='fa fa-user' aria-hidden='true' title='" + Labels.getLabel("field.userame") + "'></i> " + sessionManager.getUsuario());
		labelusuario.setParent(col02);
		/** */
		
		HtmlNativeComponent br = new HtmlNativeComponent("br");
		br.setParent(col02);
		
		/** Unidade */
		HtmlNativeComponent labelUnidade = new HtmlNativeComponent("small");
		labelUnidade.setDynamicProperty("class", "text-muted");
		labelUnidade.setPrologContent("<i class='fa fa-tags' aria-hidden='true' title='" + Labels.getLabel("field.unit") + "'></i> " + sessionManager.getUnidade());
		labelUnidade.setParent(col02);
		/** */
		
		col01.setParent(linha01);
		col02.setParent(linha01);		
		linha01.setParent(h6);
		
		this.appendChild(h6);
		
	}
	
	
	/**
	 * Metodo recursivo para criacao do menu
	 * 
	 * @param HashMap
	 *            - paremetros do usuario gravados na secao
	 * @param Object
	 *            - Objeto a qual o menu ou item sera acoplado
	 */
	private void criaMenu(HashMap filtro, HtmlNativeComponent parent) {
		try {
			Webmenu wm = new Webmenu();
			List<VwebmnuVO> mItens = wm.getMenu(filtro);

			List<Integer> prfs = wm.getPerfUniSis(mItens.get(0).getCodsis(), mItens.get(0).getCodusu(),
					mItens.get(0).getCoduni());
			Integer[] perfis = prfs.toArray(new Integer[prfs.size()]);
			this.setPermissoes(perfis, mItens.get(0).getCodsis());

			for (VwebmnuVO mnuVO : mItens) {
				this.menuPermissao = true;

				switch (mnuVO.getTipfrm()) {
				case 1: {
					HtmlNativeComponent li = new HtmlNativeComponent("li");
					A link = new A();
					HtmlNativeComponent ul = new HtmlNativeComponent("ul");

					li.appendChild(link);
					li.appendChild(ul);
					parent.appendChild(li);

					link.setIconSclass(this.menuIcon);
					link.setLabel(" " + mnuVO.getDesfrm());
					link.setHref("#");

					HashMap r = new HashMap();
					r.put("codsis", mnuVO.getCodsis());
					r.put("codusu", mnuVO.getCodusu());
					r.put("coduni", mnuVO.getCoduni());
					r.put("hiefrm", mnuVO.getHiefrm());
					r.put("nivfrm", mnuVO.getNivfrm() + 1);
					
					criaMenu(r, ul);
				} break;
				case 2: {
					HtmlNativeComponent li = new HtmlNativeComponent("li");
					A link = new A();
					link.setHref("#");
					
					li.appendChild(link);
					parent.appendChild(li);

					final String form = mnuVO.getUrlfrm();
					final String icoimg;
					
					if ("F".equals(mnuVO.getMnefrm()))
						icoimg = frmIcon;
					else if ("I".equals(mnuVO.getMnefrm()))
						icoimg = impIcon;
					else if ("C".equals(mnuVO.getMnefrm()))
						icoimg = cstIcon;
					else if ("E".equals(mnuVO.getMnefrm()))
						icoimg = edtIcon;
					else
						icoimg = frmIcon;
					
					link.setIconSclass(icoimg);
					
					link.setLabel(" " + mnuVO.getDesfrm());
					link.addEventListener("onClick", new EventListener() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							openFrm(form);
						}
					});
					
					
					
					
				} break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void openFrm(String form) {
		Include inc = (Include) getSpaceOwner().getFellow("ConteudoCentral");
		inc.setSrc("blank.zul");
		if (form.indexOf('?') >= 0) {
			String queryString = form.substring(form.indexOf('?') + 1);
			StringTokenizer st = new StringTokenizer(queryString);
			while (st.hasMoreTokens()) {
				String[] arg = st.nextToken("&").split("[=]");
				inc.setDynamicProperty(arg[0], arg[1]);
			}
		}
		inc.setSrc(form);
	}

	/**
	 * Grava na sessao as permissoes do usario
	 * 
	 * @param codprf
	 */
	private void setPermissoes(Integer codprf[], Integer codsis) {
		permissoes = wm.getFrmPermissoes(codprf, codsis);
		sessionManager.getSession().setAttribute("permissoes", permissoes);
	}

	/**
	 * Retorna true se o usuário tiver algum tipo de permissão no sistema
	 * 
	 * @return true | false
	 */
	public boolean isMenuPermissao() {
		return menuPermissao;
	}
}
