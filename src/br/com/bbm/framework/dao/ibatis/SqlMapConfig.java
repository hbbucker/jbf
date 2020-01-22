package br.com.bbm.framework.dao.ibatis;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * Singleton responsavel pela instanciacao de um (1) SqlMapClient<br>
 * Caminho padrao configurado:
 * <b>pmcg/imti/dao/ibatis/sqlmap/SqlMapConfig.xml<b>
 * 
 * @author Joacir Ilha, Hugo B. Bucker
 * @since 12 fev 2008
 * 
 */
public class SqlMapConfig {
	// private static SqlMapClient sqlMapClient ;
	private static final String _SRC_XML_DEFAULT = "br/com/bbm/sistema/dao/ibatis/sqlmap/SqlMapConfig.xml";
	private static HashMap<String, SqlMapClient> sqlMapClient;
	private static String src;

	static {
		try {
			// src = "pmcg/imti/dao/ibatis/sqlmap/SqlMapConfig.xml";
			Reader reaxml = Resources.getResourceAsReader(_SRC_XML_DEFAULT);
			sqlMapClient = new HashMap<String, SqlMapClient>();
			sqlMapClient.put(_SRC_XML_DEFAULT, SqlMapClientBuilder
					.buildSqlMapClient(reaxml));
			// sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reaxml);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna o SqlMapClient instanciado
	 * 
	 * @return SqlMapClient
	 */
	public static SqlMapClient getSqlMapClient() {
		return sqlMapClient.get(_SRC_XML_DEFAULT);
	}

	public static SqlMapClient getSqlMapClient(String xmlConfig) throws IOException {
		if (!sqlMapClient.containsKey(xmlConfig)) {
			Reader reaxml = Resources.getResourceAsReader(xmlConfig);
			sqlMapClient.put(xmlConfig, SqlMapClientBuilder
					.buildSqlMapClient(reaxml));
		}
		return sqlMapClient.get(xmlConfig);
	}

	/**
	 * Retorna o caminho do arquivo xml de configuracao
	 * 
	 * @return String
	 */
	public static String getSrc() {
		return src;
	}

	/**
	 * Seta o caminho do arquivo xml de configuracao
	 * 
	 * @param src
	 */
	public static void setSrc(String src) {
		SqlMapConfig.src = src;
	}

}