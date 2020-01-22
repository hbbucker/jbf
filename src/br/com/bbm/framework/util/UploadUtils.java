package br.com.bbm.framework.util;

import java.io.IOException;
import java.io.Reader;

import org.zkoss.util.media.Media;

public class UploadUtils {

	/**
	 * Retorna o conteudo de um objeto do tipo Media em byte[]. <br>
	 * Metodo utilizado para facilitar as conversoes dos uploads.
	 * @author Hugo Batos Bucker
	 * @since 28 fev 2011
	 * @param media
	 * @param encoding - Codificacao a ser utilizada. Ex. "UTF-8", "ISO-8859-1"
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] getBinaryData(Media media, String encoding) throws IOException{
		if (media.inMemory()) {
			if (media.isBinary())
				return media.getByteData();
			else
				return media.getStringData().getBytes(encoding);
		} else {
			if (media.isBinary()) {
				byte[] b = new byte[media.getStreamData().available()];
				media.getStreamData().read(b);
				return b;
			} else {
				StringBuffer buffer = new StringBuffer();
				Reader in = media.getReaderData();
				int ch;
				while ((ch = in.read()) > -1) {
					buffer.append((char)ch);
				}
				in.close();
				return buffer.toString().getBytes(encoding);
			}
		}
	}
	
	/**
	 * Retorna o conteudo de um objeto do tipo Media em byte[]. <br>
	 * Metodo utilizado para facilitar as conversoes dos uploads.<br>
	 * Arquivos baseados em texto retornam com codificacao ISO-8859-1
	 * @author Hugo Batos Bucker
	 * @since 28 fev 2011
	 * @param media
	 * @return byte[]
	 * @throws IOException
	 */
	public static byte[] getBinaryData(Media media) throws IOException{	
		return getBinaryData(media, "ISO-8859-1");
	}
	
}
