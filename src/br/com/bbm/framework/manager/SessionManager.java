package br.com.bbm.framework.manager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;

import br.com.bbm.framework.dao.ibatis.WebaufDAO;
import br.com.bbm.framework.dao.ibatis.WebmnuDAO;
import br.com.bbm.framework.dao.ibatis.WebusuDAO;
import br.com.bbm.framework.domain.AuditSession;
import br.com.bbm.framework.domain.WebaufVO;
import br.com.bbm.framework.domain.WebsisVO;
import br.com.bbm.framework.domain.WebusuVO;
import pmcg.imti.autenticador.service.UsuarioService;

public class SessionManager {

	private static WebmnuDAO mnuDAO;
	private static WebusuDAO usuDAO;
	private static WebaufDAO aufDAO;
	
	protected HttpSession session;
	
	private Integer sistema = null;

	static {
		mnuDAO = new WebmnuDAO();
		usuDAO = new WebusuDAO();
		aufDAO = new WebaufDAO();
	}

	public SessionManager(){
		this.session = (HttpSession) Executions.getCurrent().getDesktop().getSession().getNativeSession();
	}
	
	public HttpSession getSession() {
		return session;
	}

	private Integer getCodigoSistemaFromRequest() {
		HttpServletRequest request = (HttpServletRequest) 
				Executions.getCurrent().getDesktop().getExecution()
				.getNativeRequest();

		this.sistema = request.getParameter("sistema") != null ? Integer.parseInt(request.getParameter("sistema")) : null;
		return this.sistema;
	}

	private boolean getLogSisFromRequest() {
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getDesktop().getExecution()
				.getNativeRequest();
		return request.getParameter("logsis") != null ? Boolean.parseBoolean(request.getParameter("logsis")) : true;
	}

	private void registrarLoginSessao(WebusuVO usuVO, Integer sistema, boolean registrarLog){
		UsuarioService usuarioService = new UsuarioService();
		usuarioService.setIdUsuario(String.valueOf(usuVO.getCodusu()));
		usuarioService.setCodsis(sistema);
		Integer codaudit = usuarioService.getLogon();
		AuditSession audit = new AuditSession();
		HashMap<String, Object> hash = new HashMap<String, Object>();
		hash.put("codsis", sistema);
		hash.put("codusu", usuVO.getCodusu());
		hash.put("coduni", null);
		hash.put("codAudit", codaudit);
		audit.setCodAudit(codaudit);
		hash.put("logsis", registrarLog);
		this.session.setAttribute("usuario", usuVO);
		this.session.setAttribute("usumnu", hash);
		this.session.setAttribute("audit", audit);
	}

	private Integer buscarPermissaoSistemaURL(WebusuVO usuVO){
		Integer sistema = null;
		String contexto = Executions.getCurrent().getDesktop().getExecution().getContextPath();
		List<WebsisVO> lisSistemas = null;
		HashMap<String,Object> h = new HashMap<String, Object>();
		h.put("codusu", usuVO.getCodusu());
		lisSistemas = usuDAO.getUsuSistema(h);
		for (WebsisVO vo : lisSistemas) {
			if (vo.getUrlsis().endsWith(contexto)) {
				sistema = vo.getCodsis();
				break;
			}
		}
		this.sistema = sistema;
		return sistema;
	}
	
	public Integer getCodSistemaFromURL(){
		Integer sistema = null;
		String contexto = Executions.getCurrent().getDesktop().getExecution().getContextPath();
		List<WebsisVO> lisSistemas = null;
		lisSistemas = usuDAO.getUsuSistema(null);
		for (WebsisVO vo : lisSistemas) {
			if (vo.getUrlsis().endsWith(contexto)) {
				sistema = vo.getCodsis();
				break;
			}
		}
		return sistema;
	}
	
	public boolean validaLogin(Integer codusu){
		return validaLogin(usuDAO.getRegByCod(codusu));
	}
	
	public boolean validaLogin(String login, String pass){
		WebusuVO usuVO = new WebusuVO();
		usuVO.setLogusu(login);
		usuVO.setSenusu(pass);
		return validaLogin(mnuDAO.login(usuVO));
	}
	
	public boolean validaLogin(WebusuVO usuVO) {
		boolean retval = true;
		try {
			if (usuVO == null) {
				Clients.showNotification("Usuário ou Senha incorretos!!!", Clients.NOTIFICATION_TYPE_ERROR, null,
						"middle_center", 2000);
				retval = false;
			} else {
				getCodigoSistemaFromRequest();
				registrarLoginSessao(usuVO, sistema == null ? buscarPermissaoSistemaURL(usuVO) : sistema, getLogSisFromRequest());
				
				if (sistema == null) {
					Executions.sendRedirect("selsys");
				} else {
					WebsisVO sis = mnuDAO.getSistema(sistema);
					if (sis.getUrlsis()
							.indexOf(Executions.getCurrent().getDesktop().getExecution().getContextPath()) > -1)
						Executions.sendRedirect("menuGeral");
					else
						Executions.sendRedirect(sis.getUrlsis() + "/zk/menuGeral");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return retval;
	}
	
	
	public void logout() {
		this.session.removeAttribute("usumnu");
		this.session.removeAttribute("audit");
		this.session.removeAttribute("usuario");
		this.session.removeAttribute("permissoes");
	}
	
	
	public void registarSelecaoUnidade(String coduni, String desuni, String siguni){
		try {
			if (coduni != null && desuni != null && siguni != null) {
				HashMap<String, Object> h = (HashMap<String, Object>) this.session.getAttribute("usumnu");
				h.put("coduni", coduni);
				h.put("desuni", desuni);
				h.put("siguni", siguni);
				h.put("logsis", h.get("logsis"));
				h.put("codusu", h.get("codusu"));
				h.put("codAudit", h.get("codAudit"));

				// Auditoria de impressão de Relatório
				WebaufVO auf = getInfo("SELECAO UNIDADE/SETOR", "unilis.zul");
				auf.setNomsis(desuni);
				setAuditInfo(auf);

				this.session.setAttribute("usumnu", h);

				Executions.sendRedirect("/zk/menuGeral");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	

	public WebaufVO getInfo(String operacao, String zulPage) {
		// Captura o IP da requisição
		String ip = Executions.getCurrent().getRemoteAddr();
	
		WebaufVO auf = new WebaufVO();
		auf.setCodsis(getCodSis());
		auf.setCodusu(getCodUsuario());
		auf.setLogusu(getLogin());
		auf.setNomsis(getNomsis());
		auf.setFrmauf(zulPage);
		auf.setNipauf(ip);
		auf.setOpeauf(operacao);
		auf.setDtaauf(Calendar.getInstance().getTime());
	
		return auf;
	}
	
	public boolean setAuditInfo(WebaufVO auf) {
			try {
				aufDAO.insReg(auf);
				return true;
			} catch (Exception e) {
				System.err.println("Não foi possível persistir auditoria no banco: " + auf.toString());
				return false;
			}
	}
	
	public String getUsuario() {
		if (this.session.getAttribute("usuario") != null)
			return ((WebusuVO) this.session.getAttribute("usuario")).getNomusu();

		return "Desconhecido";
	}

	/**
	 * Retorna o codigo do usuario registrado na sessao
	 * 
	 * @return Integer
	 */
	public Integer getCodUsuario() {
		if (this.session.getAttribute("usuario") != null)
			return ((WebusuVO) this.session.getAttribute("usuario")).getCodusu();

		return null;
	}

	/**
	 * Retorna o codigo do sistema logado
	 * 
	 * @return Integer
	 */
	public Integer getCodSis() {
		try {
			return (Integer) ((HashMap) this.session.getAttribute("usumnu")).get("codsis");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer getCodPerfil(){
		try {
			return (Integer) ((HashMap) this.session.getAttribute("usumnu")).get("codprf");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	/**
	 * Retorna o login do usuario registrado na sessao
	 * 
	 * @return Strng
	 */
	public String getLogin() {
		if (this.session.getAttribute("usuario") != null)
			return ((WebusuVO) this.session.getAttribute("usuario")).getLogusu();

		return "Desconhecido";
	}

	/**
	 * Retorna o objeto {@link WebusuVO} do usuário logado
	 * 
	 * @return retorn o usuário logado ou " new WebUsuVO()" caso não haja
	 *         usuário logado.
	 */
	public WebusuVO getWebusuVO() {
		if (this.session.getAttribute("usuario") != null)
			return (WebusuVO) this.session.getAttribute("usuario");

		return new WebusuVO();
	}

	/**
	 * Retorna o siguni da unidade selecionada
	 * 
	 * @return String
	 */
	public String getSiguni() {

		HashMap m = (HashMap) this.session.getAttribute("usumnu");

		if (m.get("siguni") != null)
			return (String) m.get("siguni");

		return "-";
	}

	/**
	 * Retorna a Unidade selecionada na sessao
	 * 
	 * @return Strng
	 */
	public String getUnidade() {
		try {
			HashMap m = (HashMap) this.session.getAttribute("usumnu");

			if (m.get("coduni") != null && m.get("desuni") != null) {
				String strUni = "" + m.get("coduni");
				if (m.get("siguni") != null)
					strUni += " - " + m.get("siguni");
				return strUni + " - " + m.get("desuni");
			}

			return "Não selecionada";
		} catch (Exception ex) {
			return "Não selecionada";
		}
	}

	/**
	 * Retorna o Codigo da Unidade selecionada na sessao
	 * 
	 * @return Strng
	 */
	public String getCodUnidade() {
		try {
			HashMap m = (HashMap) this.session.getAttribute("usumnu");

			if (m.get("coduni") != null)
				return (String) m.get("coduni");
			return "0";
		} catch (Exception ex) {
			return "0";
		}
	}

	/**
	 * Retorna o nome do sistema
	 * 
	 * @return Strng
	 */
	public String getNomsis() {
		try {
			WebmnuDAO mnu = new WebmnuDAO();
			String nomsis = String.valueOf(this.getCodSis());
			return mnu.getSistema(nomsis);
		} catch (Exception e) {
			return "Desconhecido";
		}
	}
	
	public void limpaSessao() {
		this.session.removeAttribute("usumnu");
		this.session.removeAttribute("audit");
		this.session.removeAttribute("usuario");
		this.session.removeAttribute("permissoes");
	}

}
