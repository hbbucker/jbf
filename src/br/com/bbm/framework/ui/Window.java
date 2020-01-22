package br.com.bbm.framework.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.http.ExecutionImpl;
import org.zkoss.zkplus.databind.AnnotateDataBinder;

import br.com.bbm.framework.dao.ibatis.WebaufDAO;
import br.com.bbm.framework.domain.WebaufVO;
import br.com.bbm.framework.domain.WebusuVO;
import br.com.bbm.framework.manager.SessionManager;
import br.com.bbm.framework.util.FormsUtils;

/**
 * Implementacoes da classe base Window, para facilitar e atender as
 * necessidades do Modelo.
 * 
 * @author Hugo B. Bucker
 * @since 03 fev 2009
 * @version 1.0
 */
@SuppressWarnings({ "serial", "unchecked" })
public class Window extends org.zkoss.zul.Window {

	/**
	 * Habilita ou Desabilita a auditoria em operações de CRUD
	 * 
	 * @since 11 fev 2011
	 */
	protected Boolean auditoria = true;

	protected Logger log = Logger.getLogger(this.getClass());

	protected String zulPage;

	protected static WebaufDAO aufDAO;
	/**
	 * Requisicoes enviados para o Formulario
	 */
	protected Map request;

	/**
	 * Sessao
	 */
	protected SessionManager sessionManager;

	protected String contentStyle = "padding:0px; margin:0px; border:none;";

	private AnnotateDataBinder binder = new AnnotateDataBinder(this);

	/**
	 * Componentes do Formulario
	 */
	private Map<String, HtmlBasedComponent> componentes = new HashMap<String, HtmlBasedComponent>();

	static {
		aufDAO = new WebaufDAO();
	}

	public Window() {
		super();
		initComponentes();
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	/**
	 * Retorna true se a auditoria nas operações de CRUD estiverem habilitadas
	 * 
	 * @since 11 fev 2011
	 * @return true | false
	 */
	public Boolean isAuditoria() {
		return auditoria;
	}

	/**
	 * Habilita ou desabilita a auditoria nas operações de CRUD
	 * 
	 * @since 11 fev 2011
	 * @param auditoria
	 *            true| false
	 */
	public void setAuditoria(Boolean auditoria) {
		this.auditoria = auditoria;
	}

	/**
	 * Use o metodo {@link #setLogado}.
	 * 
	 * @param valida
	 */
	@Deprecated
	public void setValidaUser(boolean valida) {
		if (valida && sessionManager.getCodUsuario() == null) {
			Executions.sendRedirect("/index.jsp");
		}
	}

	/**
	 * Faz com que a pagina valide se o usuario esta logado, ou seja se sua
	 * session foi registrada, caso nao tenha sido registrada a sessao a pagina
	 * e redirecionada para <b>index</b>
	 * 
	 * @param b
	 */
	public void setLogado(Boolean b) {
		if (b && sessionManager.getCodUsuario() == null)
			Executions.sendRedirect("/index.jsp");
	}

	/**
	 * Retorna um objeto da tela, e' necessario que o elemento tenha o seu ID
	 * setado
	 * 
	 * @param id
	 *            - ID do elemento
	 * @return HtmlBasedComponent - pode ser convertido para qualquer tipo de
	 *         objeto da tela
	 */
	protected HtmlBasedComponent getComponente(String id) {
		if (componentes.size() == 0) {
			for (Object c : getSpaceOwner().getFellows()) {
				HtmlBasedComponent e = (HtmlBasedComponent) c;
				componentes.put(e.getId(), e);
			}
		}
		return componentes.get(id);
	}

	/**
	 * Retorna informações para auditoria
	 * 
	 * @param operacao
	 * @return String
	 */
	public WebaufVO getInfo(String operacao) {
		return this.sessionManager.getInfo(operacao, zulPage);
	}

	/**
	 * Persiste informações na tabela de auditoria ou quando a auditoria esta
	 * desabilitada no arquivo de Log do Tomcat
	 * 
	 * @param auf
	 * @return WebaufVO
	 */
	public boolean setAuditInfo(WebaufVO auf) {
		if (this.auditoria) {
			return this.sessionManager.setAuditInfo(auf);
		} else {
			log.info(auf.toString());
			return true;
		}

	}

	/**
	 * Retorna uma variavel configurada no arquivo ZUL pela TAG: <variable>
	 * 
	 * @param name
	 *            - String - nome da variavel
	 * @return Object
	 */
	public Object getVariavel(String name) {
		return getAttributeOrFellow(name, true);
	}

	/**
	 * Seta um valor para uma variavel configurada no arquivo ZUL pela TAG:
	 * <variable>
	 * 
	 * @param name
	 *            - nome da variavel
	 * @param val
	 *            - valor da variavel
	 */
	public void setVariavel(String name, Object val) {
		this.setAttribute(name, val, true);
	}

	/**
	 * Inicializa os componentes da classe: request e session
	 */
	private void initComponentes() {
		sessionManager = new SessionManager();
		request = ((ExecutionImpl) Executions.getCurrent()).getArg();

		this.setContentStyle(this.contentStyle);
	}

	/**
	 * Retorna verdadeiro quando todos os elementos da tela que possuem o
	 * atributo <b>constraint</b> forem satisfeitos, para a validacao destes
	 * elementos o ID do elemente tambem tem que estar setado
	 * 
	 * @return Boolean
	 */
	protected Boolean validarForm() {
		return FormsUtils.validarForm(this);
	}

	/**
	 * Retorna um Map com os valores passados na requisicao
	 * 
	 * @return Map
	 */
	public Map getRequest() {
		initComponentes();
		return request;
	}

	/**
	 * Retorna o nome do usuario registrado na sessao
	 * 
	 * @return String
	 */

	/**
	 * Método para Restaurar o vinculo do Bean com os campos da tela
	 */
	public void vincular() {
		// this.binder = new AnnotateDataBinder(this);
		binder.init(this, true);
		binder.loadAll();
	}

	public AnnotateDataBinder getBinder() {
		return binder;
	}

	public void setBinder(AnnotateDataBinder binder) {
		this.binder = binder;
	}

	public String getZulPage() {
		return zulPage;
	}

	/**
	 * 
	 * @return
	  * @deprecated Usar getSessionManager()
	 */
	@Deprecated
	public String getUsuario() {
		return sessionManager.getUsuario();
	}
	
	/**
	 * Retorna o codigo do usuario registrado na sessao
	 * 
	 * @return Integer
	 * @deprecated Usar getSessionManager()
	 */
	@Deprecated
	public Integer getCodUsuario() {
		return sessionManager.getCodUsuario();
	}

	/**
	 * Retorna o codigo do sistema logado
	 * 
	 * @return Integer
	 * @deprecated Usar getSessionManager()
	 */
	@Deprecated
	public Integer getCodSis() {
		return sessionManager.getCodSis();
	}

	/**
	 * 
	 * @deprecated Usar getSessionManager()
	 * @return String
	 */
	@Deprecated
	public Integer getCodPerfil() {
		return sessionManager.getCodPerfil();
	}

	/**
	 * Retorna o login do usuario registrado na sessao
	 * 
	 * @deprecated Usar getSessionManager()
	 * @return String
	 */
	@Deprecated
	public String getLogin() {
		return sessionManager.getLogin();
	}

	/**
	 * Retorna o objeto {@link WebusuVO} do usuário logado
	 * 
	 * @deprecated Usar getSessionManager()
	 * @return retorn o usuário logado ou " new WebUsuVO()" caso não haja
	 *         usuário logado.
	 */
	@Deprecated
	public WebusuVO getWebusuVO() {
		return sessionManager.getWebusuVO();
	}

	/**
	 * Retorna a sigla da unidade selecionada
	 * 
	 * @deprecated Usar getSessionManager()
	 * @return String
	 */
	@Deprecated
	public String getSiguni() {
		return sessionManager.getSiguni();
	}

	/**
	 * Retorna a Unidade selecionada na sessao
	 * 
	 * @deprecated Usar getSessionManager()
	 * @return String
	 */
	@Deprecated
	public String getUnidade() {
		return sessionManager.getUnidade();
	}

	/**
	 * Retorna o Codigo da Unidade selecionada na sessao
	 * 
	 * @deprecated Usar getSessionManager()
	 * @return String
	 */
	@Deprecated
	public String getCodUnidade() {
		return sessionManager.getCodUnidade();
	}

	/**
	 * Retorna o nome do sistema
	 * 
	 * @deprecated Usar getSessionManager()
	 * @return String
	 */
	@Deprecated
	public String getNomsis() {
		return sessionManager.getNomsis();
	}
}