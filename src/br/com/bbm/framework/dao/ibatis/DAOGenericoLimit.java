package br.com.bbm.framework.dao.ibatis;

import java.sql.SQLException;
import java.util.List;


import br.com.bbm.framework.dao.ibatis.DAOGenerico;

import com.ibatis.sqlmap.client.SqlMapClient;

public class DAOGenericoLimit<T,PK> extends DAOGenerico<T, PK> {
	
	public DAOGenericoLimit(String nameSpaceMap) {
		super(nameSpaceMap);
	}

	public DAOGenericoLimit(String nameSpaceMap, SqlMapClient sm) {
		super(nameSpaceMap,sm);
	}

	public DAOGenericoLimit(String nameSpaceMap, String srcXml) {
		super(nameSpaceMap, srcXml);
	}

	public Integer getCountByCriteria(T obj) throws SQLException {
		return (Integer) sqlMap.queryForObject(this.getNameSpaceMap()+ ".countByCriterio", obj);
	}
	
	public List<T> getByCriterioLimit(T obj, int inicial, int page_size) throws SQLException {
		return sqlMap.queryForList(this.getNameSpaceMap() + ".getRegByCriterio", obj, inicial, page_size);
	}
	
	public T getRegByCodComplex(PK pkey) throws SQLException{
		return (T) sqlMap.queryForObject(this.getNameSpaceMap() + ".getRegByCodComplex", pkey);
	}
}
