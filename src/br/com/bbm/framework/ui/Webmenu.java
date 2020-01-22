package br.com.bbm.framework.ui;

import java.util.HashMap;
import java.util.List;

import br.com.bbm.framework.dao.ibatis.WebmnuDAO;
import br.com.bbm.framework.domain.VwebmnuVO;


@SuppressWarnings("unchecked")
public class Webmenu {
	private int usuario;
	private int sistema;

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

	public int getSistema() {
		return sistema;
	}

	public void setSistema(int sistema) {
		this.sistema = sistema;
	}

	/**
	 * Menu responsivo
	 * @param m
	 * @return
	 */
	public List<VwebmnuVO> getMenuNew(HashMap m) {
		WebmnuDAO mDao = new WebmnuDAO();
		return (mDao.getMenuNew(m));
	}
	
	public List<VwebmnuVO> getMenu(HashMap m) {
		WebmnuDAO mDao = new WebmnuDAO();
		return (mDao.getMenu(m));
	}

	public List<HashMap<String, Object>> getUnidades(HashMap<String, Object> m) {
		WebmnuDAO mDao = new WebmnuDAO();
		return (mDao.getUnidades(m));
	}

	public HashMap<String, HashMap<String, Object>> getFrmPermissoes(
			Integer m[], Integer s) {
		WebmnuDAO mDao = new WebmnuDAO();
		return mDao.getFrmPermissoes(m, s);
	}

	public List<Integer> getPerfUniSis(Integer codsis, Integer codusu,
			String coduni) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("codsis", codsis);
		param.put("coduni", coduni);
		param.put("codusu", codusu);
		WebmnuDAO mDao = new WebmnuDAO();
		return mDao.getPerfUniSis(param);
	}
}