package br.com.bbm.framework.ui;

import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Menuseparator;

import br.com.bbm.framework.domain.VwebmnuVO;
import br.com.bbm.framework.domain.WebfavVO;
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
public class MnuGeral extends Menubar {
	/**
	 * 
	 */
	private Webmenu wm = new Webmenu();
	private HashMap<String, HashMap<String, Object>> permissoes;
	private SessionManager sessionManager = new SessionManager();
	private boolean menuPermissao = false;

	protected String menuIcon = "fa fa-folder-open";
	protected String frmIcon = "fa fa-wpforms";
	protected String impIcon = "fa fa-print";
	protected String edtIcon = "fa fa-pencil";
	protected String cstIcon = "fa fa-search";
	
	public MnuGeral() {
		// Verifica se o usuario tem acesso a mais de uma unidade pelo
		// sistema/perfil

		HashMap<String, Object> usumnu = (HashMap<String, Object>) sessionManager
				.getSession().getAttribute("usumnu");
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

			sessionManager.getSession().setAttribute("usumnu", usumnu);
			// Monta o Menu do sistema
			this.criaMenu(usumnu, this);
		} else if (uni.size() > 1) {
			this.menuPermissao = true;
			WinUtils.abreSetor();
		} else {
			this.menuPermissao = false;
			return;
		}

		try {
			usumnu = (HashMap) sessionManager.getSession().getAttribute("usumnu");
			usumnu.put("codprf", uni.get(0).get("codprf"));
			sessionManager.getSession().setAttribute("usumnu", usumnu);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	
	/**
	 * Metodo recursivo para criacao do menu
	 * 
	 * @param HashMap
	 *            - paremetros do usuario gravados na secao
	 * @param Object
	 *            - Objeto a qual o menu ou item sera acoplado
	 */
	private void criaMenu(HashMap m, Object mnu) {
		try {
			Webmenu wm = new Webmenu();
			List<VwebmnuVO> mItens = wm.getMenu(m);
			List<Integer> prfs = wm.getPerfUniSis(mItens.get(0).getCodsis(),
					mItens.get(0).getCodusu(), mItens.get(0).getCoduni());
			Integer[] perfis = prfs.toArray(new Integer[prfs.size()]);
			this.setPermissoes(perfis, mItens.get(0).getCodsis());
			for (VwebmnuVO mnuVO : mItens) {
				this.menuPermissao = true;
				switch (mnuVO.getTipfrm()) {
				case 1:
					Menupopup p = new Menupopup();
					switch (mnuVO.getNivfrm()) {
					case 1:
						Menu a = new Menu(mnuVO.getNomfrm());
						a.setDroppable("true");
						a.appendChild(p);
						a.setIconSclass(this.menuIcon);
						this.appendChild(a);
						break;
					default:
						if (mnu instanceof Menupopup) {
							Menu b = new Menu(mnuVO.getNomfrm());
							b.appendChild(p);
							b.setIconSclass(this.menuIcon);
							((Menupopup) mnu).appendChild(b);
						} else if (mnu instanceof Menu)
							((Menu) mnu).appendChild(p);
						else
							return;
					}
					HashMap r = new HashMap();
					r.put("codsis", mnuVO.getCodsis());
					r.put("codusu", mnuVO.getCodusu());
					r.put("coduni", mnuVO.getCoduni());
					r.put("hiefrm", mnuVO.getHiefrm());
					r.put("nivfrm", mnuVO.getNivfrm() + 1);
					this.criaMenu(r, p);
					break;
				case 2:

					Menuitem mi = new Menuitem(mnuVO.getNomfrm());

					// Verificando o tipo de formulario para setar o Icone
					String[] aux = mnuVO.getUrlfrm().toString().split("/");
					String key = aux[aux.length - 1];
					HashMap<String, Object> per = permissoes.get(key);

					if (per == null)
						break;

					mi.setValue(per.toString());
					mi.setDraggable("true");
					mi.setDroppable("true");

					final String icoimg;
					
					if ("F".equals(String.valueOf(per.get("mnefrm"))))
						icoimg = frmIcon;
					else if ("I".equals(String.valueOf(per.get("mnefrm"))))
						icoimg = impIcon;
					else if ("C".equals(String.valueOf(per.get("mnefrm"))))
						icoimg = cstIcon;
					else if ("E".equals(String.valueOf(per.get("mnefrm"))))
						icoimg = edtIcon;
					else
						icoimg = frmIcon;
					
					mi.setIconSclass(icoimg);
					
					// Fim do icone do menu
					final String form = mnuVO.getUrlfrm();

					mi.addEventListener("onClick", new EventListener() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							openFrm(form);
						}
					});

					final WebfavVO fav = new WebfavVO(null, mnuVO.getNomfrm(),
							mnuVO.getUrlfrm(), mnuVO.getCodusu(),
							mnuVO.getCodsis(), mnuVO.getCodprf(),
							mnuVO.getCoduni());

					// Evento de arrastar para a criação de botões favoritos.
					/*
					mi.addEventListener(Events.ON_MOVE, new EventListener() {
						@Override
						public void onEvent(Event dragged) throws Exception {
							try {
								new WebfavDAO().insReg(fav);
								Button b = new Button();
								b.setLabel(fav.getDesfav());
								b.setImageContent(icoimg);
								b.setOrient("vertical");
								b.setId("idFav_" + fav.getCodfav());
								b.addEventListener(Events.ON_CLICK,
										new EventListener() {
											@Override
											public void onEvent(Event arg0)
													throws Exception {
												openFrm(form);
											}
										});
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					});
					*/
					if (mnu instanceof Menupopup)
						((Menupopup) mnu).appendChild(mi);
					else if (mnu instanceof Menu)
						((Menu) mnu).appendChild(mi);
					break;

				case -1:
					Menuseparator se = new Menuseparator();
					if (mnu instanceof Menupopup)
						((Menupopup) mnu).appendChild(se);
					else if (mnu instanceof Menu)
						((Menu) mnu).appendChild(se);
					break;
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
