package br.com.bbm.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import br.com.bbm.framework.ui.annotations.PrimaryKey;

public class VoUtils {
	
	public static final Double RAIO_DE_DENUNCIA = 2500D;
	
	/**
	 * Encotra a chave primaria do VO pela anotação PrimaryKey
	 * @param vo
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("unused")
	public static <E> Object getPrimaryKey(E vo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (vo != null) {
            Field[] fields = vo.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].isAnnotationPresent(PrimaryKey.class)) {
                    String mname = getGetMethodName(fields[i].getName());
                    Method m = vo.getClass().getDeclaredMethod(mname);
                    Object retval = m.invoke(vo);
                    return retval;
                }else{
                	return vo;
                }
            }
        }
        return null;
    }

	/**
	 * Converte todos os campos string de um VO para UpperCase
	 * @param vo
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <E> Object toUpperCaseStringFields(E vo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (vo != null) {
			Field[] fields = vo.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if ("String".equalsIgnoreCase(fields[i].getType().getSimpleName())) {
					String mname = getGetMethodName(fields[i].getName());
					Method m = vo.getClass().getDeclaredMethod(mname);
					Object setval = m.invoke(vo);

					if (setval != null) {

						mname = getSetMethodName(fields[i].getName());
						m = vo.getClass().getDeclaredMethod(mname, fields[i].getType());
						m.invoke(vo, ("" + setval).toUpperCase());
					}
				}
			}
		}

		return vo;
	}

	/**
	 * Faz um trim em todos os campos String do VO
	 * @param vo
	 * @return
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static <E> Object trimStringFields(E vo) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (vo != null) {
			Field[] fields = vo.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if ("String".equalsIgnoreCase(fields[i].getType().getSimpleName())) {
					String mname = getGetMethodName(fields[i].getName());
					Method m = vo.getClass().getDeclaredMethod(mname);
					Object setval = m.invoke(vo);

					if (setval != null) {
						mname = getSetMethodName(fields[i].getName());
						m = vo.getClass().getDeclaredMethod(mname, fields[i].getType());
						String val = ("" + setval).trim();
						if (val == null || val.length() == 0)
							val = null;
						m.invoke(vo, val);
					}
				}
			}
		}

		return vo;
	}

	public static String getSHA1(String msg){
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(msg.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
	
	private static String getGetMethodName(String fieldname) {
		return "get" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
	}

	private static String getSetMethodName(String fieldname) {
		return "set" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1);
	}
	
	/**
	 * Calcula distancia entre dois pontos geográficos
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return o valor em metros
	 */
	public static double getDistanciaEntrePontos(double lat1, double lon1, double lat2, double lon2){
		return distance(lat1, lon1, lat2, lon2, "K") * 1000;
	}
	
	private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit.equals("K")) {
			dist = dist * 1.609344;
		} else if (unit.equals("N")) {
			dist = dist * 0.8684;
		}
		
		return (dist);
	}
	
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	private static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}
}
