package br.com.bbm.framework.ui;

import java.sql.SQLException;
import java.util.Map;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;

import br.com.bbm.framework.dao.ibatis.DAOGenerico;
import br.com.bbm.framework.ui.interfaces.CallbackCrud;
import br.com.bbm.framework.util.VoUtils;

@SuppressWarnings("rawtypes")
public abstract class GenericBaseCadMVVM<T, TDao extends DAOGenerico> extends WindowCrud {

	private static final long serialVersionUID = 1L;
	public T vo;
	protected TDao dao;
	protected Boolean abertoPeloMenu = Boolean.TRUE;

	protected abstract boolean trataVO();

	public abstract void initComponentes();

	protected abstract boolean validaFormExt();

	public GenericBaseCadMVVM(String urlform, T vo, TDao dao) {
		super(urlform);
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) Executions.getCurrent().getArg();
		if (map.get("pageName") != null) {
			abertoPeloMenu = Boolean.FALSE;
		}

		this.vo = vo;
		this.dao = dao;
		ctrlBotoesIncAltDel(false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void apagar() throws InterruptedException {
		try {
			Messagebox.show(Labels.getRequiredLabel("button.delete.question"), Labels.getRequiredLabel("system.warning") + "!!!", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(Event arg0) throws Exception {
					if (arg0.getName().equals("onYes")) {
						Integer qtdreg = 0;
						try {
							qtdreg = dao.delReg(VoUtils.getPrimaryKey(GenericBaseCadMVVM.this.vo));
						} catch (SQLException e) {
							e.printStackTrace();
						}
						if (qtdreg > 0) {
							if (testImplementsCallback()) {
								((CallbackCrud) GenericBaseCadMVVM.this).onApagarSuccess(GenericBaseCadMVVM.this.vo);
							} else {
								Messagebox.show(Labels.getRequiredLabel("register.success.delete"));
								limpar();
							}
						} else {
							if (testImplementsCallback()) {
								((CallbackCrud) GenericBaseCadMVVM.this).onApagarFailed(new Exception(Labels.getRequiredLabel("register.fail.delete")));
							} else
								Messagebox.show(Labels.getRequiredLabel("register.fail.delete"), Labels.getRequiredLabel("system.error") + ":", Messagebox.OK, Messagebox.ERROR);
						}
					}
				}
			});
		} catch (Exception e) {
			if (testImplementsCallback()) {
				((CallbackCrud) this).onApagarFailed(e);
			} else
				Messagebox.show(Labels.getRequiredLabel("register.fail.delete"), Labels.getRequiredLabel("system.error") + ":", Messagebox.OK, Messagebox.ERROR);

			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void incluir() throws InterruptedException {
		try {
			if (this.validarForm() && this.trataVO() && this.validaFormExt()) {
				Long cod = new Long(dao.insReg(this.vo).toString());
				if (cod > 0) {
					if (this.abertoPeloMenu) {
						if (testImplementsCallback()) {
							((CallbackCrud) this).onIncluirSuccess(this.vo);
						} else
							Messagebox.show(Labels.getRequiredLabel("register.success.add"));
					} else {
						this.sair();
					}
					this.ctrlBotoesIncAltDel(true);
					this.vincular();
				} else {
					if (testImplementsCallback()) {
						((CallbackCrud) this).onIncluirFailed(new Exception(Labels.getRequiredLabel("register.fail.add")));
					} else
						Messagebox.show(Labels.getRequiredLabel("register.fail.add"), Labels.getRequiredLabel("system.error") + ":", Messagebox.OK, Messagebox.ERROR);
				}
			}
		} catch (WrongValueException e) {
			if (testImplementsCallback()) {
				((CallbackCrud) this).onIncluirFailed(e);
			} else
				throw e;
		} catch (Exception e) {
			if (testImplementsCallback()) {
				((CallbackCrud) this).onIncluirFailed(e);
			} else
				Messagebox.show(Labels.getRequiredLabel("register.fail.add") + " " + e.getMessage(), "Erro:", Messagebox.OK, Messagebox.ERROR);

			e.printStackTrace();
		}
	}

	@Override
	public void sair() throws InterruptedException {
		if (this.abertoPeloMenu) {
			this.detach();
		} else {
			Events.postEvent("onClose", this, this.vo);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void salvar() throws InterruptedException {
		try {
			if (this.validarForm() && this.trataVO() && this.validaFormExt()) {
				if (this.dao.updRegByCod(this.vo) > 0) {
					if (this.abertoPeloMenu) {
						if (testImplementsCallback()) {
							((CallbackCrud) GenericBaseCadMVVM.this).onSalvarSuccess(this.vo);
						} else
							Messagebox.show(Labels.getRequiredLabel("register.success.save"));
						
					} else {
						this.sair();
					}
					
					this.vincular();
				} else {
					if (testImplementsCallback()) {
						((CallbackCrud) GenericBaseCadMVVM.this).onApagarFailed(new Exception(Labels.getRequiredLabel("register.fail.save")));
					} else
						Messagebox.show(Labels.getRequiredLabel("register.fail.save"), Labels.getRequiredLabel("system.error") + ":", Messagebox.OK, Messagebox.ERROR);
				}
			}
		} catch (WrongValueException e) {
			if (testImplementsCallback()) {
				((CallbackCrud) GenericBaseCadMVVM.this).onApagarFailed(e);
			} else
				throw e;
		} catch (Exception e) {
			if (testImplementsCallback()) {
				((CallbackCrud) GenericBaseCadMVVM.this).onApagarFailed(e);
			} else{
				Messagebox.show(Labels.getRequiredLabel("register.fail.save"), Labels.getRequiredLabel("system.error") + ":", Messagebox.OK, Messagebox.ERROR);
				e.printStackTrace();
			}
		}
	}

	/**
	 * Verifica se o callback foi implementado pela classe
	 * @return
	 */
	protected boolean testImplementsCallback() {
		boolean retval = false;
		Class<?>[] interfaces = this.getClass().getInterfaces();
		for (Class i : interfaces) {
			if (i.toString().equals(CallbackCrud.class.toString())) {
				retval = true;
				break;
			}
		}

		return retval;
	}
}
