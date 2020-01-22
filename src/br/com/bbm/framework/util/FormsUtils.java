package br.com.bbm.framework.util;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.impl.InputElement;

public class FormsUtils {

	/**
	 * Faz a validação de um formulário, de acordo com as constraints definidas no ZUL
	 * @param component
	 * @return
	 */
	public static Boolean validarForm(Component component) {
		for (Object c : component.getSpaceOwner().getFellows()) {
			if (c instanceof InputElement) {
				InputElement e = (InputElement) c;
				Object pai = e.getParent();
				if (!e.isValid()) {
					for (;;) {
						if (pai instanceof Tabpanel) {
							((Tabpanel) pai).getLinkedTab().setSelected(true);
							break;
						} else if (pai instanceof org.zkoss.zul.Window) {
							((org.zkoss.zul.Window) pai).focus();
							break;
						}
						pai = ((HtmlBasedComponent) pai).getParent();
					}
					e.focus();
					e.getConstraint().validate(e, e.getText());
					return false;
				}
			}
		}
		return true;
	}
	
}
