package br.com.bbm.framework.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;


/**
 * 
 * @author Hugo B. Bucker
 * @since 17/06/2010
 */

public class DateUtils {
	
	private static Map<Integer, String> months = new Hashtable<Integer, String>();

	static {
		months.put(new Integer(1), "Janeiro");
		months.put(new Integer(2), "Fevereiro");
		months.put(new Integer(3), "Março");
		months.put(new Integer(4), "Abril");
		months.put(new Integer(5), "Maio");
		months.put(new Integer(6), "Junho");
		months.put(new Integer(7), "Julho");
		months.put(new Integer(8), "Agosto");
		months.put(new Integer(9), "Setembro");
		months.put(new Integer(10), "Outubro");
		months.put(new Integer(11), "Novembro");
		months.put(new Integer(12), "Dezembro");
	}

	/**
	 * 
	 * @param date
	 *            - data a partir da qual sera extraido o nome do mes
	 * @return - nome do mes em que a data passada como parametro foi criada
	 */
	public static String getMonthName(java.util.Date date) {
		return (String) months.get(Integer.valueOf(getMonth(date)));
	}

	/**
	 * Incrementa o numero de dias para uma data
	 * @param data
	 * @param numeroDias
	 * @return Date
	 */
	public static Date incrementarDiaData(Date data, int numeroDias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.add(Calendar.DAY_OF_MONTH, numeroDias);
		data = calendar.getTime();
		return data;
	}

	
	/**
	 * Retorna o dia da data passada como parametro para o método
	 * @param value
	 *            o dia extraido do Timestamp passado como parametro
	 * @return String que representa o dia passado como parametro.
	 */
	public static String getDay(java.util.Date value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(value);

		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return StringUtils.fillLeft(String.valueOf(day), '0', 2);
	}

	/**
	 * metodo que extrai o mes de uma data Timestamp passada como parametro
	 * 
	 * @param value
	 *            a data no formato Timestamp de onde se pretende extrair o mes
	 * @return String que representa o mes extraido da data passada como
	 *         parametro.
	 */
	public static String getMonth(java.util.Date value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(value);
		int month = calendar.get(Calendar.MONTH);
		return StringUtils.fillLeft(String.valueOf(month + 1), '0', 2);
	}

	/**
	 * metodo que extrai o ano de uma data Timestamp passada como parï¿½metro.
	 * 
	 * @param value
	 *            a data no formato Timestamp de onde se pretende extrair o ano
	 * @return String que representa o ano extraido da data Timestamp passada
	 *         como parametro.
	 */
	public static String getYear(java.util.Date value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(value);
		int year = calendar.get(Calendar.YEAR);
		return StringUtils.fillLeft(String.valueOf(year), '0', 2);
	}


	/**
	 * Converte um Date para uma String dd/mm/yyyy
	 * @param date
	 *            - data a ser convertida para string
	 * @return - uma string com formato de data
	 */
	public static String dateToString(java.util.Date date) {
		String dt = null;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"dd/MM/yyyy");
			dt = simpleDateFormat.format(date);
		} catch (Exception ex) {
		}
		return dt;
	}

	/**
	 * Converte uma String para um Date "dd-MM-yyyy"
	 * @param string
	 *            - a ser convertida para data
	 * @return - objeto Date com a data presente na String ou null caso a data
	 *         nao seja valida
	 * @throws ParseException
	 */
	public static java.util.Date stringToDate(String string) {

		SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
		try {
			string = string.replace('/', '-');
			return s.parse(string);
		} catch (ParseException e) {
			// data invalida
			return null;
		}
	}


	/**
	 * Pega o ano corrento do servidor de aplicação/JVM
	 * @return
	 */
	public int getAnoCorrente() {
		return anoCorrente();
	}

	/**
	 * Pega o ano corrento do servidor de aplicação/
	 * @return o ano Corrente
	 */

	public static int anoCorrente() {
		String ano = "";
		try {
			Date data = new Date();
			String dataS = DateUtils.dateToString(data);
			ano = dataS.split("/")[2];
		} catch (RuntimeException e) {
			return 0;
		}
		return Integer.valueOf(ano).intValue();
	}

	/**
	 * Encontra o útimo dia util para o mês da data informada
	 * @param data
	 * @return Date
	 */
	public static Date getUltimoDiaUtilMes(Date data) {
		Calendar d = Calendar.getInstance();
		d.setTime(data);

		while (d.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| d.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			d.set(Calendar.DAY_OF_MONTH, d.get(Calendar.DAY_OF_MONTH) - 1);

		return d.getTime();
	}
}
