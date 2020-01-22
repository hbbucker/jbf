package br.com.bbm.framework.util;

public class StringUtils {

	/**
	 * Método para retornar uma String formada pela String fornecida e
	 * preenchida à esquerda pelo caracter até completar a quantidade possíveis
	 * fornecida. <br>
	 * Ex. fillLeft("casa",'0',10) = "000000casa"
	 * 
	 * @param string
	 *            String a ser preenchida
	 * @param chr
	 *            a ser utilizado no preenchimento
	 * @param length
	 *            comprimento final da string preenchida
	 * @return Str
	 */
	public static String fillLeft(String string, char chr, int length) {

		StringBuffer sb = new StringBuffer(string);
		if (string.length() < length) {
			while (sb.length() < length) {
				sb.insert(0, chr);
			}
		}
		return sb.toString();
	}

	/**
	 * Formata um CPF ou CNPJ
	 * @param val
	 *            - String com CPF ou CNPJ
	 * @return String formatada
	 */
	public static String stringToCpfCnpj(String val) {
		switch (val.length()) {
		case 11:
			val = val.substring(0, 3) + "." + val.substring(3, 6) + "."
					+ val.substring(6, 9) + "-" + val.substring(9, 11);
			break;
		case 14:
			val = val.substring(0, 2) + "." + val.substring(2, 5) + "."
					+ val.substring(5, 8) + "/" + val.substring(8, 12) + "-"
					+ val.substring(12, 14);
			break;
		case 15:
			val = val.substring(0, 3) + "." + val.substring(3, 6) + "."
					+ val.substring(6, 9) + "/" + val.substring(9, 13) + "-"
					+ val.substring(13, 15);
			break;
		}

		return val;
	}

	/**
	 * Valida se o CPF ou CNPJ são corretos
	 * @param cpfcnpj
	 * 			-CPF: tamanho 11<br>
	 * 			-CNPJ: tamanho 14
	 * @return boolean
	 */
	public static boolean validaCpfCnpj(String cpfcnpj) {
		/**
		 * Rotina para CPF
		 */
		if (cpfcnpj.length() == 11) {
			int d1, d2;
			int digito1, digito2, resto;
			int digitoCPF;
			String nDigResult;
			d1 = d2 = 0;
			digito1 = digito2 = resto = 0;
			for (int n_Count = 1; n_Count < cpfcnpj.length() - 1; n_Count++) {
				digitoCPF = Integer.valueOf(
						cpfcnpj.substring(n_Count - 1, n_Count)).intValue();
				/**
				 *  Multiplique a ultima casa por 2 a seguinte por 3 a
				 *  seguinte por 4 e assim por diante.
				 */
				d1 = d1 + (11 - n_Count) * digitoCPF;
				/**
				 *  Para o segundo digito repita o procedimento
				 *  incluindo o primeiro digito calculado no passo anterior.
				 */
				d2 = d2 + (12 - n_Count) * digitoCPF;
			}
			;
			/**
			 *  Primeiro resto da divisão por 11.
			 */
			resto = (d1 % 11);
			/**
			 *  Se o resultado for 0 ou 1 o digito é 0 caso contrário o
			 *  digito é 11 menos o resultado anterior.
			 */
			if (resto < 2)
				digito1 = 0;
			else
				digito1 = 11 - resto;
			
			d2 += 2 * digito1;
			/**
			 * Segundo resto da divisão por 11.
			 */
			resto = (d2 % 11);
			
			/**
			 *  Se o resultado for 0 ou 1 o digito é 0 caso contrário o
			 *  digito é 11 menos o resultado anterior.
			 */
			if (resto < 2)
				digito2 = 0;
			else
				digito2 = 11 - resto;
			
			/**
			 *  Digito verificador do CPF que está sendo validado.
			 */
			String nDigVerific = cpfcnpj.substring(cpfcnpj.length() - 2, cpfcnpj.length());
			
			/**
			 *  Concatenando o primeiro resto com o segundo.
			 */
			nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
			
			/**
			 *  Comparar o digito verificador do cpf com o primeiro
			 *  resto + o segundo resto.
			 */
			return nDigVerific.equals(nDigResult);
		}
		/**
		 *  Rotina para CNPJ
		 */
		else if (cpfcnpj.length() == 14) {
			int soma = 0, dig;
			String cnpj_calc = cpfcnpj.substring(0, 12);
			char[] chr_cnpj = cpfcnpj.toCharArray();
			
			/**
			 *  Primeira parte
			 */
			for (int i = 0; i < 4; i++)
				if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
					soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
			for (int i = 0; i < 8; i++)
				if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)
					soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
			dig = 11 - (soma % 11);
			cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
			
			/**
			 *  Segunda parte
			 */
			soma = 0;
			for (int i = 0; i < 5; i++)
				if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
					soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
			for (int i = 0; i < 8; i++)
				if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)
					soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
			dig = 11 - (soma % 11);
			cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);
			return cpfcnpj.equals(cnpj_calc);
		} else
			return false;
	}
	
	/**
	 * Faz a junção de um array em string pelo separador
	 * @param texto
	 * @param separador
	 * @return
	 */
	public static String join(Object[] texto, String separador){
		String res = "";
		for (Object o : texto)
			res += separador + o.toString();
		
		return res.substring(separador.length());
	}
}
