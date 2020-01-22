package br.com.bbm.framework.richlets;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.HtmlNativeComponent;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Div;
import org.zkoss.zul.East;
import org.zkoss.zul.Include;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.South;
import org.zkoss.zul.Span;
import org.zkoss.zul.Vbox;

import br.com.bbm.framework.config.Config;
import br.com.bbm.framework.manager.SessionManager;
import br.com.bbm.framework.ui.MnuGeral2;
import br.com.bbm.framework.ui.Webmenu;
import br.com.bbm.framework.ui.Window;
import br.com.bbm.framework.util.WinUtils;

public class MenuGeral extends GenericRichlet {

	@Override
	public void service(Page page) {
		try {
			new PageMnuGeral(page);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}

class PageMnuGeral {

	private Div header = new Div();
	private Div body = new Div();
	private MnuGeral2 mnuGeral = null;
	private Include include = new Include();
	private Include includeCen = new Include();

	private Window window = new Window();
	
	@SuppressWarnings("unchecked")
	public PageMnuGeral(Page page) throws InstantiationException, IllegalAccessException {
		try {
			page.removeComponents();
			page.setTitle(Labels.getRequiredLabel("system.institute.title"));
			page.setAttribute("cacheable", false);
			if (initComponentes())
				return;

			header.setWidth("100%");
			body.setWidth("100%");
			body.setVflex("1");
			

			final HashMap<String, Integer> m = (HashMap<String, Integer>) 
					window.getSessionManager().getSession().getAttribute("usumnu");
			
			if (m == null) {
				Executions.sendRedirect("/index.jsp");
				return;
			}

			m.put("tipfrm", 2);
			m.put("nivfrm", null);

			
			include.setId("ConteudoCentral");
			include.setSrc("blank.zul");
			include.setMode("instant");

			include.setParent(body);
			
			includeCen.setWidth("100%");
			includeCen.setVflex("1");
			
			includeCen.setSrc("pgCentral.zul");
			includeCen.setParent(body);
			
			header.appendChild(mnuGeral);
			
			header.setPage(page);
			body.setPage(page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean initComponentes() {
		mnuGeral = new MnuGeral2(window.getSessionManager());
		if (!mnuGeral.isMenuPermissao()) {
			Executions.getCurrent().sendRedirect("/zk/selsys");
			return true;
		}
		return false;
	}
}