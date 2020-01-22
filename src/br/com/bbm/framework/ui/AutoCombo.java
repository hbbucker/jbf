package br.com.bbm.framework.ui;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Include;

import br.com.bbm.framework.domain.VwebmnuVO;


public class AutoCombo extends Combobox implements EventListener {
	private static final long serialVersionUID = -6459928485572486838L;
	private List<VwebmnuVO> _mItens;

	public AutoCombo(List<VwebmnuVO> itens) {
		setMItens(itens);
		this.addEventListener(Events.ON_CHANGING, this);
		this.addEventListener(Events.ON_CHANGE, this);
		refresh("");
	}

	public AutoCombo() {
		refresh(""); // init the child comboitems
	}

	public AutoCombo(String value) {
		super(value); // it invokes setValue(), which inits the child comboitems
	}

	public void setValue(String value) {
		super.setValue(value);
		// refresh(value); // refresh the child comboitems
	}

	/**
	 * Listens what an user is entering.
	 */
	// public void onChanging(Event evt) {
	// // if(!getText().toUpperCase().equals(evt.getValue().toUpperCase())) {
	// refresh(evt.getValue());
	// // }
	// }

	// public void onChange(){
	// VwebmnuVO sel = (VwebmnuVO) this.getSelectedItem().getValue();
	// Include inc = (Include) getSpaceOwner().getFellow("ConteudoCentral");
	// inc.setSrc(null);
	// inc.setSrc(sel.getUrlfrm());
	// }

	/**
	 * Refresh comboitem based on the specified value.
	 */
	private void refresh(String val) {
		super.getItems().clear();
		for (VwebmnuVO i : _mItens) {
			if (i.getDesfrm().toUpperCase().contains(val.toUpperCase())) {
				Comboitem cbi = new Comboitem(i.getDesfrm());
				cbi.setValue(i);
				cbi.setParent(this);
			}
		}

	}

	public List<VwebmnuVO> getMItens() {
		return _mItens;
	}

	public void setMItens(List<VwebmnuVO> itens) {
		_mItens = itens;
	}

	@Override
	public void onEvent(Event event) throws Exception {
		try {
			if (Events.ON_CHANGING.equals(event.getName())) {
				if (!((InputEvent) event).isChangingBySelectBack())
					refresh(((InputEvent) event).getValue());
			}

			if (Events.ON_CHANGE.equals(event.getName())) {
				VwebmnuVO sel = (VwebmnuVO) this.getSelectedItem().getValue();
				Include inc = (Include) getSpaceOwner().getFellow(
						"ConteudoCentral");
				inc.setSrc(null);
				inc.setSrc(sel.getUrlfrm());
				refresh("");
			}
		} catch (NullPointerException e) {
//			e.printStackTrace();
		}
	}
}
