package br.com.bbm.framework.dao.ibatis;

import java.sql.SQLException;


import br.com.bbm.framework.domain.ConcorrenciaVO;
import br.com.bbm.framework.exception.ConcorrenciaException;

import com.ibatis.sqlmap.client.SqlMapClient;

public class DAOConcorrencia<T,PK> extends DAOGenericoLimit<T,PK> {
	
	public DAOConcorrencia(String namespace){
		super(namespace);
	}
	
	public DAOConcorrencia(String nameSpaceMap, SqlMapClient sm) {
		super(nameSpaceMap,sm);
	}

	public DAOConcorrencia(String nameSpaceMap, String srcXml) {
		super(nameSpaceMap,srcXml);
	}

	/**
	 * Atualizar registro da tabela verificando se
	 * o mesmo não foi modificado por outro usuário
	 * 
	 * @param obj Instancia do Objeto Repositorio (Value Object)
	 * @param chave Instancia da chave primária do registro
	 * @return quantidade de registros atualizados
	 * @throws SQLException 
	 * @throws ConcorrenciaException
	 */
	public Integer updRegByCodConcorrencia(T obj, PK chave) throws ConcorrenciaException, SQLException {
		String nameSpace = getNameSpaceMap();
		if (nameSpace == null) nameSpace = getNameSpaceMap(obj);
		
		T old = this.getRegByCod(chave);
		if (((ConcorrenciaVO)old).getAge().after(((ConcorrenciaVO)obj).getAge()))
			throw new ConcorrenciaException("Erro - Registro modificado por outro usuário.");
		else	
			return (Integer) sqlMap.update(nameSpace + ".updRegByCod", obj);
	}
	
	/**
	 * Deletar registro da tabela verificando se
	 * o mesmo não foi modificado por outro usuário
	 * 
	 * @param obj Instancia do Objeto Repositorio (Value Object)
	 * @param chave Instancia da chave primária do registro
	 * @return quantidade de registros atualizados
	 * @throws SQLException 
	 * @throws ConcorrenciaException
	 */
	public Integer delRegConcorrencia(T obj, PK chave) throws ConcorrenciaException, SQLException {
		String nameSpace = getNameSpaceMap();
		if (nameSpace == null) nameSpace = getNameSpaceMap(obj);
		
		T old = this.getRegByCod(chave);
		if (((ConcorrenciaVO)old).getAge().after(((ConcorrenciaVO)obj).getAge()))
			throw new ConcorrenciaException("Erro - Registro modificado por outro usuário.");
		else	
			return (Integer) sqlMap.delete(nameSpace + ".delByCod", chave);
	}
}
