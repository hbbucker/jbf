package br.com.bbm.framework.richlets;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Div;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

import br.com.bbm.framework.dao.ibatis.WebmnuDAO;
import br.com.bbm.framework.domain.WebsisVO;
import br.com.bbm.framework.manager.SessionManager;
import pmcg.imti.autenticador.service.UsuarioService;

public class Index extends GenericRichlet {

	@Override
	public void service(Page page) {
		new MyPage2(page);
	}
}

class MyPage2 {

	private Integer sistema = null;
	private final Textbox[] tx = new Textbox[] { new Textbox(), new Textbox() };
//	private final Label[] lb = new Label[] { new Label(Labels.getRequiredLabel("field.username"))
//										   , new Label(Labels.getRequiredLabel("field.password"))
//										   , new Label(Labels.getRequiredLabel("field.system")) };
	private final Button btn = new Button();
	private HttpServletRequest request;
	UsuarioService usuarioService;
	
	final SessionManager sessionManager = new SessionManager();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MyPage2(Page page) {
		
		WebmnuDAO mnuDAO = new WebmnuDAO();
		request = (HttpServletRequest) page.getDesktop().getExecution().getNativeRequest();

//		contexto = page.getDesktop().getExecution().getContextPath();
		usuarioService = new UsuarioService();
		
//		logsis = request.getParameter("logsis") != null ? Boolean.parseBoolean(request.getParameter("logsis")) : true; 
		
		if (request.getParameter("codAudit") != null) {
			usuarioService.setIdLogon(request.getParameter("codAudit"));
			usuarioService.setIdUsuario(request.getParameter("usuario"));
			if (usuarioService.isLogado()) {
				if(sessionManager.validaLogin(Integer.parseInt(usuarioService.getIdUsuario())))
					return;
			}
		}
		
		sistema = request.getParameter("sistema") != null ? Integer
				.parseInt(request.getParameter("sistema")) : null;
		
		String caption = "";
		page.removeComponents();
		initComponentes();
		
		if (sistema != null) {
			WebsisVO sis = mnuDAO.getSistema(sistema);
			caption = sis.getNomsis();
		} else {
			caption = Labels.getRequiredLabel("system.institute.title");
		}
		
		page.setTitle(Labels.getRequiredLabel("system.institute.title"));
		page.setAttribute("cacheable", false);
		org.zkoss.zul.Div body = new org.zkoss.zul.Div();
		org.zkoss.zul.Window w = new org.zkoss.zul.Window();
		
		body.setZclass("window_body");
		body.setWidth("100%");
		body.setHeight("100%");
		
		w.setTitle(caption);
		w.setBorder("none");
		w.setShadow(false);
		w.doOverlapped();
		w.setZclass("window_login");
				
		btn.setLabel(Labels.getRequiredLabel("field.enter"));
		btn.setId("entrar");
		btn.setHflex("1");
		btn.setZclass(Labels.getRequiredLabel("button.enter.css"));
		btn.addEventListener(Events.ON_CLICK, new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				if (Events.ON_CLICK.equals(event.getName())) {
					String id = event.getTarget().getId().toLowerCase();
					if ("entrar".equals(id)) {
						btn.setFocus(true);
						sessionManager.validaLogin(tx[0].getText(), tx[1].getText());
					}
				}
			}

		});
		
		tx[1].setType("password");
		tx[1].addEventListener(Events.ON_OK, new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				btn.setFocus(true);
				sessionManager.validaLogin(tx[0].getText(), tx[1].getText());
			}
		});

		tx[0].setPlaceholder(Labels.getRequiredLabel("field.username"));
		tx[1].setPlaceholder(Labels.getRequiredLabel("field.password"));
		
		tx[0].setStyle("margin-bottom: 15px");
		tx[1].setStyle("margin-bottom: 15px");
		
		tx[0].setHflex("1");
		tx[1].setHflex("1");
		
		Vbox hb = new Vbox();
		hb.setWidth("100%");
		hb.appendChild(tx[0]);
		hb.appendChild(tx[1]);
		w.appendChild(hb);

		Separator sp = new Separator();
		sp.setHeight("10px");
		w.appendChild(sp);
		
		Div dv = new Div();
		dv.setStyle("text-align:right");
		dv.appendChild(btn);
		
		w.appendChild(dv);
		w.setParent(body);
		w.setPosition("center");
		body.setPage(page);

		tx[0].focus();
	}

	private void initComponentes() {
		sessionManager.logout();
	}
}