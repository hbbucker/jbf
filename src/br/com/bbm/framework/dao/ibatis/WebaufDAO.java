package br.com.bbm.framework.dao.ibatis;

import br.com.bbm.framework.domain.WebaufVO;

public class WebaufDAO extends DAOGenerico<WebaufVO, Long>{

	public WebaufDAO() {
		super("Webauf", "br/com/bbm/framework/dao/ibatis/sqlmap/acessoSMCfg.xml");
	}
	
	

}
