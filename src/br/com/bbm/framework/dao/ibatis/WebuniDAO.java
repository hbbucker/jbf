package br.com.bbm.framework.dao.ibatis;

import java.sql.SQLException;
import java.util.List;

import br.com.bbm.framework.domain.WebuniVO;


public class WebuniDAO extends DAOGenerico<WebuniVO, WebuniVO>{

	public WebuniDAO() {
		super("Webuni", "br/com/bbm/framework/dao/ibatis/sqlmap/acessoSMCfg.xml");
	}
	
	/**
	 * Retorna lista de setores filtrando pela descricao
	 * @param desuni
	 * @param codsis
	 * @return List<WebuniVO>
	 */
	public List<WebuniVO> getSetorByDes(String desuni, Integer codsis) {
		try {
			WebuniVO uVO = new WebuniVO();
			uVO.setDesuni("%" + desuni + "%");
			uVO.setCodsis(codsis);
			return sqlMap.queryForList("Webuni.getSetorByDes", uVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Retorna lista de setores Ativos filtrando pela descricao
	 * @param desuni
	 * @param codsis
	 * @return List<WebuniVO>
	 */
	public List<WebuniVO> getSetoresAtivos(String desuni, Integer codsis) {
		try {
			WebuniVO uVO = new WebuniVO();
			uVO.setDesuni("%" + desuni + "%");
			uVO.setCodsis(codsis);
			return sqlMap.queryForList("Webuni.getSetoresAtivos", uVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Retorna o setor ativo pelo codido do organograma (siguni) e codigo do sistema
	 * @param uVO
	 * @return WebuniVO
	 */
	public WebuniVO getSetorByCod(WebuniVO uVO) {
		try {
			return (WebuniVO) sqlMap.queryForObject("Webuni.getSetorByCod", uVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Ver {@link #getSetorByCod(WebuniVO)}
	 */
	public List<WebuniVO> getSetorBySig(WebuniVO uVO) {
		try {
			return sqlMap.queryForList("Webuni.getSetorByCod", uVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	@Override
	public Integer insReg(WebuniVO obj) {
			return null;
	}

	@Override
	public Integer delReg(WebuniVO obj) {
		return  null;
	}

	@Override
	public Integer updRegByCod(WebuniVO obj) {
		return null;
	}

	
	
}
