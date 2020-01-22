package br.com.bbm.framework.domain;

import java.io.Serializable;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import pmcg.imti.autenticador.service.UsuarioService;

public class AuditSession extends AuditSessionHttpBinding implements Serializable {
	private static final long serialVersionUID = 5572413757812335897L;
	private Integer codAudit;

	public Integer getCodAudit() {
		return codAudit;
	}

	public void setCodAudit(Integer codAudit) {
		this.codAudit = codAudit;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codAudit == null) ? 0 : codAudit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditSession other = (AuditSession) obj;
		if (codAudit == null) {
			if (other.codAudit != null)
				return false;
		} else if (!codAudit.equals(other.codAudit))
			return false;
		return true;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent sess) {
//		AuditSession m = (AuditSession) sess.getValue();
//		System.out.println("Audit Registrado: " + m.getCodAudit());
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent sess) {
		try {
		UsuarioService usuarioService = new UsuarioService();
		AuditSession m = (AuditSession) sess.getValue();
		usuarioService.setIdLogon(m == null || m.getCodAudit() == null ? null : m.getCodAudit().toString());
		usuarioService.getLogoff();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


abstract class AuditSessionHttpBinding implements HttpSessionBindingListener {
	
}