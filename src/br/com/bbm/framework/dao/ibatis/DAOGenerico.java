package br.com.bbm.framework.dao.ibatis;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author Virmerson Bento
 * @param <T>
 *            O Tipo do Objeto Repositorio (Values Object) que contem os dados a
 *            serem persistidos
 */
public class DAOGenerico<T, PK> {

	protected SqlMapClient sqlMap; // A propriedade referencia ao SqlMapClient
	private String nameSpaceMap;
	// private final String _SRC_XML_DEFAULT =
	// "pmcg/imti/dao/ibatis/sqlmap/SqlMapConfig.xml";
	private String srcXml;

	static {

	}

	public DAOGenerico() {
		new SqlMapConfig();
		sqlMap = SqlMapConfig.getSqlMapClient();
	}

	public DAOGenerico(String nameSpaceMap) {
		this.nameSpaceMap = nameSpaceMap;
		// if (!_SRC_XML_DEFAULT.equals(SqlMapConfig.getSrc())){
		// SqlMapConfig.setSrc(_SRC_XML_DEFAULT);
		// SqlMapConfig.initSqlMapClient();
		// }
		sqlMap = SqlMapConfig.getSqlMapClient();
	}

	public DAOGenerico(String nameSpaceMap, SqlMapClient sm) {
		this.nameSpaceMap = nameSpaceMap;
		sqlMap = sm;
	}

	public DAOGenerico(String nameSpaceMap, String srcXml) {
		try {
			this.nameSpaceMap = nameSpaceMap;
			this.srcXml = srcXml;
			// SqlMapConfig.setSrc(srcXml);
			// SqlMapConfig.initSqlMapClient();
			sqlMap = SqlMapConfig.getSqlMapClient(this.srcXml);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public SqlMapClient getSqlMap(){
		return this.sqlMap;
	}
	
	public String getNameSpaceMap() {
		return nameSpaceMap;
	}

	public void setNameSpaceMap(String nameSpaceMap) {
		this.nameSpaceMap = nameSpaceMap;
	}

	/**
	 * Monta dinamicamente a String com o name space de acordo com o Objecto obj
	 * passados pelo metodos publicos
	 * 
	 * @param obj
	 * @return String com o nomespace montado com seu prefixo
	 */
	protected String getNameSpaceMap(Object obj) {
		if (obj != null) {
			return obj.getClass().getSimpleName();
		} else {
			return null;
		}
	}

	/**
	 * Inserir registro na tabela
	 * 
	 * @param obj
	 *            Instancia do Objeto Repositorio (Value Object)
	 * @throws SQLException
	 * @throws SQLException
	 */
	public Object insReg(T obj) throws SQLException {
		String nameSpace = getNameSpaceMap();
		if (nameSpace == null)
			nameSpace = getNameSpaceMap(obj);

		return sqlMap.insert(nameSpace + ".insReg", obj);
	}

	/**
	 * Deletar registro da tabela
	 * 
	 * @param obj
	 *            Instancia do Objeto Repositorio (Value Object)
	 * @return codigo do registro deletado
	 * @throws SQLException
	 */
	public Integer delReg(PK obj) throws SQLException {
		String nameSpace = getNameSpaceMap();
		if (nameSpace == null)
			nameSpace = getNameSpaceMap(obj);

		return (Integer) sqlMap.delete(nameSpace + ".delByCod", obj);
	}

	/**
	 * Atualizar registro da tabela
	 * 
	 * @param obj
	 *            Instancia do Objeto Repositorio (Value Object)
	 * @return quantidade de registros atualizados
	 * @throws SQLException
	 */
	public Integer updRegByCod(T obj) throws SQLException {
		String nameSpace = getNameSpaceMap();
		if (nameSpace == null)
			nameSpace = getNameSpaceMap(obj);

		return (Integer) sqlMap.update(nameSpace + ".updRegByCod", obj);
	}

	/**
	 * Buscar por registros da tabela
	 * 
	 * @param obj
	 *            Instancia do Objeto Repositorio (Value Object)
	 * @return Lista contendo os registros
	 * @throws SQLException
	 */
	public T getRegByCod(PK obj) throws SQLException {
		String nameSpace = getNameSpaceMap();
		if (nameSpace == null)
			nameSpace = this.getClass().getName();

		return (T) sqlMap.queryForObject(nameSpace + ".getRegByCod", obj);
	}

	/**
	 * Buscar por todos registros da tabela
	 * 
	 * @param obj
	 *            Instancia do Objeto Repositorio (Value Object)
	 * @return Lista contendo os registros
	 * @throws SQLException
	 */
	public List<T> getTodos(T obj) throws SQLException {
		String nameSpace = getNameSpaceMap();
		if (nameSpace == null)
			nameSpace = getNameSpaceMap(obj);

		return sqlMap.queryForList(nameSpace + ".getTodos", obj);
	}

	/**
	 * Buscar por todos registros da tabela conforme o atributo setado no objeto
	 * 
	 * @param obj
	 *            Instancia do Objeto Repositorio (Value Object)
	 * @return Lista contendo os registros
	 * @throws SQLException
	 */
	public List<T> getRegByCriterio(T obj) throws SQLException {
		String nameSpace = getNameSpaceMap();
		if (nameSpace == null)
			nameSpace = getNameSpaceMap(obj);

		return sqlMap.queryForList(nameSpace + ".getRegByCriterio", obj);
	}
}