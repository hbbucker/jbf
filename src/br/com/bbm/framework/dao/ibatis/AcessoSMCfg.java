package br.com.bbm.framework.dao.ibatis;

import java.io.Reader;
import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class AcessoSMCfg {
	private static SqlMapClient aceSqlMap = null;  
	static {
		try {
			Reader rea = Resources.getResourceAsReader("br/com/bbm/framework/dao/ibatis/sqlmap/acessoSMCfg.xml");
			aceSqlMap = SqlMapClientBuilder.buildSqlMapClient(rea);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static SqlMapClient getSqlMapInstance() {
		return aceSqlMap;
	}
}
