package br.com.bbm.framework.dao.ibatis;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


import br.com.bbm.framework.domain.VwebmnuVO;
import br.com.bbm.framework.domain.WebsisVO;
import br.com.bbm.framework.domain.WebusuVO;
import br.com.bbm.framework.util.StringUtils;

import com.ibatis.sqlmap.client.SqlMapClient;
@SuppressWarnings("unchecked")
public class WebmnuDAO extends DAOGenericoLimit<VwebmnuVO, Integer> {
	public WebmnuDAO() {
		super("Webmnu", "br/com/bbm/framework/dao/ibatis/sqlmap/acessoSMCfg.xml");
	}
	
	/**
	 * Menu Responsivo
	 * @param m
	 * @return
	 */
	public List<VwebmnuVO> getMenuNew(HashMap<String,Object> m) {
		List<VwebmnuVO> lis = null;
		try {
			lis = sqlMap.queryForList(this.getNameSpaceMap() + ".getMenuNew", m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lis;
	}
	
	public List<VwebmnuVO> getMenu(HashMap<String,Object> m) {
		List<VwebmnuVO> lis = null;
		try {
			lis = sqlMap.queryForList(this.getNameSpaceMap() + ".getMenu", m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lis;
	}
	
	public List<Integer> getPerfUniSis(HashMap<String,Object> m) {
		List<Integer> lis = null;
		try {
			lis = sqlMap.queryForList(this.getNameSpaceMap() + ".getPerfUniSis", m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lis;
	}
	
	public WebusuVO login(WebusuVO usuario){
		try{
			usuario = (WebusuVO) sqlMap.queryForObject(this.getNameSpaceMap() + ".getUser", usuario);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return usuario;
	}
	
	public HashMap<String, HashMap<String,Object>> getFrmPermissoes(Integer codprf[], Integer codsis){
		List<HashMap<String,Object>> lis = null;
		HashMap<String,Object> par = new HashMap<String,Object>();
		par.put("codprf",StringUtils.join(codprf,","));
		par.put("codsis",codsis);
		
		HashMap<String, HashMap<String,Object>> ret = new HashMap<String,HashMap<String,Object>>();
		try {
			lis = sqlMap.queryForList(this.getNameSpaceMap() + ".getFrmPermissoes", par);
			for(HashMap<String,Object> i : lis){
				if (i.get("urlfrm") != null){
					 String[] aux = i.get("urlfrm").toString().split("/");				 
					 String key = aux[aux.length-1];
					 ret.put(key, i);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;		
	}
	
	public String getSistema(String codsis){
		Integer nomsis = Integer.parseInt(codsis);
		try{
			return (String) sqlMap.queryForObject(this.getNameSpaceMap() + ".getSistema", nomsis);
		}catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public WebsisVO getSistema(Integer codsis){
		try{
			return (WebsisVO) sqlMap.queryForObject(this.getNameSpaceMap() + ".getSistema1", codsis);
		}catch (SQLException e) {
			return null;
		}
	}
	
	public List<WebsisVO> getSistemaByContext(String nomectx){
		try{
			return sqlMap.queryForList(this.getNameSpaceMap() + ".getSistemaByContext", nomectx);
		}catch (SQLException e) {
			return null;
		}
	}
	
	public List<HashMap<String,Object>> getUnidades(HashMap<String,Object> m){
		List<HashMap<String,Object>> lis = null;
		try {
			lis = sqlMap.queryForList(this.getNameSpaceMap() + ".getUnidades", m);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lis;
	}
	
	public HashMap<String,Object> getRptDesing(HashMap<String,Object> param){
		try{
			return (HashMap<String,Object>) sqlMap.queryForObject(this.getNameSpaceMap() + ".getRptDesign", param);
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}