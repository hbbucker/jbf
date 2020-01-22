package br.com.bbm.framework.util;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.http.ExecutionImpl;

import br.com.bbm.framework.dao.ibatis.WebmnuDAO;
import br.com.bbm.framework.dao.ibatis.WebusuDAO;
import br.com.bbm.framework.domain.WebaufVO;
import br.com.bbm.framework.manager.SessionManager;
import br.com.bbm.framework.ui.Window;

@SuppressWarnings("unchecked")
public class WinUtils {

	public final static String dirImg = "/web/images";
	private static String imgnames[] = new String[] { "iconsForm/frmEdit.png", "iconsForm/frmImp.png", "iconsForm/frmConsulta.png", "toolbar/btnIncluir.png", "toolbar/btnSalvar.png",
			"toolbar/btnApagar.png", "toolbar/btnLimpar.png", "toolbar/btnPesquisar.png", "toolbar/btnImprimir.png", "toolbar/btnSair.png" };

	private static HashMap<String, Image> img = new HashMap<String, Image>();

	static {
		try {
			// Carrega os icones de forma estática
			// para resolver o problema de que as imagens não eram carregas em
			// tempo real.
			for (int i = 0; i < imgnames.length; i++) {
				String key = imgnames[i].split("/")[1].split("\\.")[0].substring(3).toLowerCase();
				img.put(key, new AImage(key, new WinUtils().getClass().getResourceAsStream(dirImg + "/" + imgnames[i])));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Abre uma janela de Busca (lis) e chama a funcao de callback, importante
	 * que a funcao de callback possua um parametro, pois ele sera retornado
	 * pelo lis com o dado selecionado
	 * 
	 * @param url
	 *            - Endereco da pagina de busca (.zul)
	 * @param param
	 *            - Paramametros que podem ser enviados a tela de busca
	 * @param win
	 *            - Janela que sera retornado os dados (classe ou window que
	 *            possui a funcao de callback)
	 * @param callback
	 *            - Nome da funcao que sera chamada quando houver a selecao na
	 *            tela busca
	 */
	public void abrirLis(String url, Map<String, String> param, final Object win, final String callback) {
		final Window cmp = (Window) Executions.createComponents(url, null, param);
		cmp.doHighlighted();
		cmp.addEventListener("onClose", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				Method m = win.getClass().getDeclaredMethod(callback, Object.class);
				try {
					if (event.getData() != null)
						m.invoke(win, event.getData());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Abre uma janela e chama a funcao de callback, importante que a funcao de
	 * callback possua um parametro, pois ele sera retornado pelo lis com o dado
	 * selecionado
	 * 
	 * @param url
	 *            - Endereco da pagina de busca (.zul)
	 * @param param
	 *            - Paramametros que podem ser enviados a tela de busca
	 * @param win
	 *            - Janela que sera retornado os dados (classe ou window que
	 *            possui a funcao de callback)
	 * @param callback
	 *            - Nome da funcao que sera chamada quando houver a selecao na
	 *            tela busca
	 */
	public void abrirWin(String url, Map<String, Object> param, final Object win, final String callback) {
		final Window cmp = (Window) Executions.createComponents(url, null, param);
		cmp.doHighlighted();
		cmp.addEventListener("onClose", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				Method m = win.getClass().getDeclaredMethod(callback, Object.class);
				try {
					if (event.getData() != null)
						m.invoke(win, event.getData());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Abre uma janela de Relatorio <br>
	 * <b>Aponta diretamente para o um birt2.3</b>
	 * 
	 * @param relat
	 *            - caminho e nome do relatorio no birtviewer
	 * @param param
	 *            - Paramametros que podem ser enviados a tela de busca
	 */
	@Deprecated
	public void abrirBirt(String relat, Map<String, String> param) {
		Map<String, String> url = new HashMap<String, String>();
		String s = "/../birt2.3/frameset?__report=" + relat + "&__format=pdf";
		String par = "";
		for (Object key : param.keySet()) {
			par += "&" + (String) key + "=";
			par += (String) param.get(key);
		}
		url.put("url", s + par);
		final Window cmp = (Window) Executions.createComponents("/forms/birt.zul", null, url);

		// Auditoria de impressão de Relatório
		WebaufVO auf = cmp.getInfo(WebaufVO.FORMULARIO_IMPRESSAO);
		auf.setFrmauf(relat);
		cmp.setAuditInfo(auf);

		cmp.setMaximized(true);
		cmp.setSizable(true);
		cmp.setMaximizable(true);
		cmp.setClosable(true);
		cmp.doOverlapped();
	}

	/**
	 * Abre uma janela de Relatorio baseado no gerenciamento pelo banco de dados
	 * 
	 * @since 11 ferv 2011 - Alteração - Auditoria na impressão de relatórios. 
	 * @since 27 fev 2015 - Alteração para seleção do runtime do birt
	 * @author Hugo B. Bucker
	 * 
	 * @param codsis
	 *            - Código do sistema em que o relatório está
	 * @param nomctx
	 *            - Nome do subdiretorio do diretorio reports do birt em que o
	 *            relatorio esta armazenado
	 * @param bvsrpt
	 *            - Versão do birt em que o relatório foi desenvolvido
	 * @param urlhst
	 *            - Url do servidor de birt
	 * @param tiprpt
	 *            - Tipo do relatório: Tipos usados pelo birt para geração do
	 *            relatório
	 * @param desrpt
	 *            - Descrição para o relatório
	 * @param nomrpt
	 *            - Nome do arquivo rpdesign
	 * @param rtmrpt
	 * 			  - Servlet/Runtime responsável pela visualização
	 * 			    valores possíveis frameset|run|preview         
	 * @param parametros
	 *            - Parametros extra a serem passados ao relatório
	 */
	public void abrirBirt(Integer codsis, String nomctx, String bvsrpt, String urlhst, String tiprpt, String desrpt, String nomrpt, BirtServlet rtmrpt, HashMap<String, String> parametros) {
		if (codsis == null)
			codsis = new SessionManager().getCodSis();

		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("nomrpt", nomrpt);
		param.put("codsis", codsis);

		HashMap rpt = new WebmnuDAO().getRptDesing(param);

		// Nome do Contexto
		if (nomctx == null)
			nomctx = ((ExecutionImpl) Executions.getCurrent()).getContextPath();

		// Host do Birt
		if (urlhst == null)
			urlhst = String.valueOf(rpt.get("urlhst")).split("/")[0].trim() + ":" + rpt.get("pjvhst");

		// Versao do Birt
		if (bvsrpt == null) {
			bvsrpt = (String) rpt.get("bvsrpt");

			// String[] vb = rpt.get("bvshst").toString().split(",");
			// Float maiorBirt = new Float(0);
			// for (String v : vb) {
			// Float ver = new Float(v);
			// if (maiorBirt < ver)
			// maiorBirt = ver;
			// }
			// bvsrpt = maiorBirt.toString();
		}

		// Tipo da saida
		if (tiprpt == null)
			tiprpt = String.valueOf(rpt.get("tiprpt")).trim();

		// Descrição do relatorio
		if (desrpt == null)
			desrpt = String.valueOf(rpt.get("desrpt")).trim();

		// Outros parametros
		String npar = "";
		Iterator keysIter = parametros.entrySet().iterator();
		while (keysIter.hasNext()) {
			Map.Entry obj = (Map.Entry) keysIter.next();
			npar += "&" + obj.getKey() + "=" + (String) obj.getValue();
		}

		String rtm = "";
		// Runtime do birt
		if (rtmrpt == null) {
			rtm = (String) rpt.get("rtmrpt");
			if (rtm == null)		
				rtm = BirtServlet.FRAMESET.runtime;
			else {
				rtm = (String) rpt.get("rtmrpt");
			}
		} else
			rtm = rtmrpt.runtime;
		
		Map<String, String> url = new HashMap<String, String>();
		url.put("url", "http://" + urlhst.trim() + "/birt" + bvsrpt.trim() + "/" + rtm + "?__report=reports" + nomctx.trim() + "/" + nomrpt.trim() + "&__showtitle=true" + "&__title=" + desrpt
				+ "&__toolbar=true" + "&__navigationbar=true" + "&__format=" + tiprpt.toLowerCase() + npar);

		final Window cmp = (Window) Executions.createComponents("/forms/birt.zul", null, url);

		// Auditoria de impressão de Relatório
		WebaufVO auf = cmp.getInfo(WebaufVO.FORMULARIO_IMPRESSAO + ":BIRT");
		auf.setFrmauf(nomrpt.trim() + "?" + npar);
		cmp.setAuditInfo(auf);

		cmp.setMaximized(true);
		cmp.setSizable(true);
		cmp.setMaximizable(true);
		cmp.setClosable(true);
		cmp.doOverlapped();
		
	}

	/**
	 * Abre uma janela de Relatorio baseado no gerenciamento pelo banco de dados
	 * 
	 * @since 11 fev 2011 - Alteração - Auditoria na impressão de relatórios.
	 * @since 27 fev 2015 - Alteração para seleção do runtime do birt 
	 * @author Hugo B. Bucker
	 * 
	 * @param codsis
	 *            - Código do sistema em que o relatório está
	 * @param nomctx
	 *            - Nome do subdiretorio do diretorio reports do birt em que o
	 *            relatorio esta armazenado
	 * @param bvsrpt
	 *            - Versão do birt em que o relatório foi desenvolvido
	 * @param urlhst
	 *            - Url do servidor de birt
	 * @param tiprpt
	 *            - Tipo do relatório: Tipos usados pelo birt para geração do
	 *            relatório
	 * @param desrpt
	 *            - Descrição para o relatório
	 * @param nomrpt
	 *            - Nome do arquivo rpdesign
	 * @param parametros
	 *            - Parametros extra a serem passados ao relatório
	 */
	public void abrirBirt(Integer codsis, String nomctx, String bvsrpt, String urlhst, String tiprpt, String desrpt, String nomrpt, HashMap<String, String> parametros) {
		this.abrirBirt(codsis, nomctx, bvsrpt, urlhst, tiprpt, desrpt, nomrpt, null, parametros);
	}

	/**
	 * Abre uma janela de Relatorio baseado no gerenciamento pelo banco de dados
	 * 
	 * @param nomrpt
	 *            - Nome do arquivo rptdesing
	 * @param parametros
	 *            - parametros a serem passados para o relatório
	 */
	public void abrirBirt(String nomrpt, HashMap<String, String> parametros) {
		this.abrirBirt(null, null, null, null, null, null, nomrpt, parametros);
	}

	/**
	 * Pega a data do Servidor de Banco de Dados
	 * 
	 * @return java.util.Date
	 */
	public static Date getDataBanco() {
		WebusuDAO usuDAO = new WebusuDAO();
		return usuDAO.getDataBanco();
	}

	/**
	 * Retorna uma imagem dentro do pacote do framework
	 * 
	 * @param name
	 *            - nome do arquivo da imagem
	 * @return
	 */
	public static Image getImageIcons(String name) {
		return img.get(name);
	}

	/**
	 * Abre a janela de selecao de unidade/setor
	 * 
	 */
	public static void abreSetor() {
		java.util.Map<String, String> map = new HashMap<String, String>();
		try {
			new WinUtils().abrirLis("~./zul/unilis.zul", map, new WinUtils(), "retSetorList");
		} catch (Exception e) {
			Executions.getCurrent().sendRedirect("/index.jsp");
		}
	}

	/**
	 * Metodo de callback para o retorno da janela de abertura do sistema
	 * 
	 * @param r
	 *            - Objeto com os dados selecionados na janela do setor
	 */
	public static void retSetorList(Object r) {
		HashMap<String, Object> m = (HashMap<String, Object>) r;
		SessionManager sessionManager = new SessionManager();
		try {
			if (r != null) {
				sessionManager.registarSelecaoUnidade(m.get("coduni").toString(), 
													  m.get("desuni").toString(), 
													  String.valueOf(m.get("siguni")));
			}
		} catch (Exception e) {
			Integer sistema = sessionManager.getCodSistemaFromURL();
			if (sistema == null)
				Executions.getCurrent().sendRedirect("/index.jsp");
			else {
				Executions.getCurrent().sendRedirect("/zk/index?sistema=" + sistema);
			}
		}
	}

}
