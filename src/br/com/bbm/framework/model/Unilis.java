package br.com.bbm.framework.model;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;

import br.com.bbm.framework.dao.ibatis.WebmnuDAO;
import br.com.bbm.framework.dao.ibatis.WebuniDAO;
import br.com.bbm.framework.domain.WebuniVO;
import br.com.bbm.framework.ui.WindowList;

@SuppressWarnings({ "serial", "unchecked" })
public class Unilis extends WindowList<HashMap> {

	/**
	 * List de Retorno do DAO
	 */
	private List<HashMap<String, Object>> hmSis;

	/**
	 * Vinculado a caixa de pesquisa no forumlario
	 */
	public String nome = new String();

	/**
	 * DAO
	 */
	private WebmnuDAO DAO = new WebmnuDAO();

	public Unilis() {
		super();
//		this.getCrdBar().getBotao(3).setVisible(true);
//		this.getCrdBar().getBotao(4).setVisible(true);
	}
	
	public void initComponentes() {
		if (Executions.getCurrent().getBrowser("mobile") != null)
			this.setMaximized(true);
		
//		this.getCrdBar().getBotao(3).setVisible(true);
//		this.getCrdBar().getBotao(4).setVisible(true);
		pesquisar();

	}
	
	public void limpar(){
		nome="";
		this.vincular();
	}

	public void check(String cod) {
		WebuniDAO uDAO = new WebuniDAO();
		WebuniVO vo = new WebuniVO();
		try {
			vo.setCoduni(cod);
			vo.setCodsis(this.sessionManager.getCodSis());
			WebuniVO m = uDAO.getRegByCod(vo);
			BeanUtils.copyProperties(item, m);
			this.selecionar();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pesquisar() {
		if (this.getSessionManager().getSession().getAttribute("usumnu") == null) return;
		HashMap<String, Object> vo = new HashMap<String, Object>();
		vo.put("codusu", this.sessionManager.getCodUsuario());
		vo.put("codsis", this.sessionManager.getCodSis());
		vo.put("coduni", this.sessionManager.getCodUnidade());
		vo.put("desuni", nome == null ? "" : nome);
		hmSis = DAO.getUnidades(vo);
		setListmodel(new ListModelList(hmSis));
		this.atualizar();
		((Textbox) this.getComponente("nome")).select();
	}
}