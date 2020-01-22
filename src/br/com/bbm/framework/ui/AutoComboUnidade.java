package br.com.bbm.framework.ui;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import br.com.bbm.framework.util.WinUtils;


public class AutoComboUnidade extends Combobox implements EventListener {
	private static final long serialVersionUID = -6459928485572486838L;
	private List<HashMap<String, Object>> _uItens;

	public AutoComboUnidade(List<HashMap<String, Object>> list,HttpSession httpSession) {
		setUItens(list);
		this.addEventListener(Events.ON_CHANGING, this);
		this.addEventListener(Events.ON_CHANGE, this);
		if(httpSession.getAttribute("usumnu")!=null &&  ((HashMap<String, Object>)httpSession.getAttribute("usumnu")).get("coduni")!=null){
			
			selecionaItem(((HashMap<String, Object>)httpSession.getAttribute("usumnu")).get("coduni").toString());
		}
	}

	public AutoComboUnidade() {
		//refresh(""); // init the child comboitems
	}

	public AutoComboUnidade(String value) {
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
	private void selecionaItem(String coduni) {
		 
		//super.getItems().clear();
		for (HashMap<String, Object> i : _uItens) {
			if (i.get("coduni").toString().toUpperCase().equalsIgnoreCase(coduni.toUpperCase())) {
				for(Comboitem it:(List<Comboitem>)this.getItems()){					
					if(it.getValue().equals(i)){
						this.setSelectedItem(it);
						break;
					}
				}
				 
			}
		}

	}

	public List<HashMap<String, Object>> getUItens() {
		return _uItens;
	}

	public void setUItens(List<HashMap<String, Object>> list) {
		_uItens = list;
		getItems().clear();
		if(list!=null){
			for(HashMap<String, Object> map:list){
				Comboitem it=new Comboitem(map.get("desuni").toString());
				it.setValue(map);
				it.setParent(this);
				//this.setParent(it);
			}
		}
	}

	@Override
	public void onEvent(Event event) throws Exception {
		try {
			 
			if (Events.ON_CHANGE.equals(event.getName())) {
				HashMap<String, Object> sel = (HashMap<String, Object>) this.getSelectedItem().getValue();
				WinUtils.retSetorList(sel);				 
			}
		} catch (NullPointerException e) {
//			e.printStackTrace();
		}
	}
}
