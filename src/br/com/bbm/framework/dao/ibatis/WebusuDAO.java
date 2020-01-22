package br.com.bbm.framework.dao.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.bbm.framework.domain.WebsisVO;
import br.com.bbm.framework.domain.WebusuVO;


@SuppressWarnings("unchecked")
public class WebusuDAO extends DAOGenerico<WebusuVO, Integer> {

	public WebusuDAO() {
		super("webusu", "br/com/bbm/framework/dao/ibatis/sqlmap/acessoSMCfg.xml");
	}

	public int getCodByLogSen(WebusuVO usu) {
		int konta = 0;
		try {
			konta = (Integer) sqlMap.queryForObject(this.getNameSpaceMap() + ".getCodByLogSen",
					usu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return konta;
	}

	public int getQtdByLog(String login) {
		int konta = 0;
		try {
			konta = (Integer) sqlMap
					.queryForObject(this.getNameSpaceMap() + ".getQtdByLog", login);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return konta;
	}

	public WebusuVO getLogNomByCod(int cod) {
		WebusuVO us = null;
		try {
			us = (WebusuVO) sqlMap.queryForObject(this.getNameSpaceMap() + ".getLogNomByCod",
					new Integer(cod));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return us;
	}

	public WebusuVO getRegByCod(Integer cod) {
		WebusuVO us = null;
		if (cod == null)
			return null;
		try {
			us = (WebusuVO) sqlMap.queryForObject(this.getNameSpaceMap() + ".getRegByCod", cod);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return us;
	}

	public Date getDataBanco() {
		try {
			return (Date) sqlMap.queryForObject(this.getNameSpaceMap() + ".getDataBanco");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<WebsisVO> getSistema(Integer cod) {
		try {
			return sqlMap.queryForList(this.getNameSpaceMap() + ".getSistemas", cod);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<WebsisVO> getUsuSistema(HashMap<String, Object> hash) {
		try {
			return sqlMap.queryForList(this.getNameSpaceMap() + ".getUsuSistemas", hash);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}