package br.com.bbm.framework.richlets;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.zkoss.image.AImage;
import org.zkoss.zhtml.Form;
import org.zkoss.zhtml.Input;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.bbm.framework.dao.ibatis.WebusuDAO;
import br.com.bbm.framework.domain.WebsisVO;
import br.com.bbm.framework.domain.WebusuVO;

public class Selsys extends GenericRichlet {

	@Override
	public void service(Page page) {
		new PageSelSys(page);
	}
}

class PageSelSys {

	private final iWebsis iWsis = new iWebsis();
	private final Button btn = new Button();
	private final Listbox lbox = new Listbox();
	private HttpSession session;
	private List<WebsisVO> lisSistemas = null;
	private Page pg;

	public PageSelSys(Page page) {
		pg = page;
		session = (HttpSession) Executions.getCurrent().getDesktop().getSession().getNativeSession();

		if (!chkLogin())
			Executions.sendRedirect("index");
		else {
			page.removeComponents();

			page.setTitle("Dteam");
			page.setAttribute("cacheable", false);
			org.zkoss.zul.Window w = new org.zkoss.zul.Window();
			w.setTitle("Seleção de Módulo");
			w.setBorder("normal");
			w.setWidth("85%");
			w.setHeight("85%");
			w.doOverlapped();

			lbox.setModel(new ListModelList(lisSistemas));

			Listhead cls = new Listhead();
			Listheader hLabel = new Listheader();
			Listheader hImage = new Listheader();
			hImage.setWidth("57px");
			hImage.setAlign("center");
			hLabel.setValign("middle");
			cls.appendChild(hImage);
			cls.appendChild(hLabel);
			lbox.appendChild(cls);
			lbox.setItemRenderer(iWsis);
			lbox.setVflex(true);
//			lbox.setHeight("95%");

			lbox.addEventListener(Events.ON_SELECT, new EventListener() {
				@SuppressWarnings("unchecked")
				@Override
				public void onEvent(Event event) throws Exception {
					HashMap h = (HashMap) session.getAttribute("usumnu");
					Listbox box = (Listbox) event.getTarget();
					h.put("codsis", ((WebsisVO) box.getSelectedItem().getValue()).getCodsis());
					h.put("nomsis", ((WebsisVO) box.getSelectedItem().getValue()).getNomsis());
					session.setAttribute("usumnu", h);

					String url = ((WebsisVO) box.getSelectedItem().getValue()).getUrlsis();
					if (url.indexOf("/zk") > -1)
						url = url.substring(0, url.indexOf("/zk")) + "/zk/index";

					Form form = new Form();
					form.setDynamicProperty("action", url);
					form.setDynamicProperty("method", "post");
					form.setPage(pg);
					Input inCodAudit = new Input();
					inCodAudit.setParent(form);
					inCodAudit.setDynamicProperty("type", "hidden");
					inCodAudit.setDynamicProperty("name", "codAudit");
					inCodAudit.setValue("" + h.get("codAudit"));

					Input inCodSis = new Input();
					inCodSis.setParent(form);
					inCodSis.setDynamicProperty("type", "hidden");
					inCodSis.setDynamicProperty("name", "sistema");
					inCodSis.setValue("" + h.get("codsis"));

					Clients.submitForm(form);
					// Executions.sendRedirect("menuGeral");
				}
			});

			btn.setLabel("Voltar");
			btn.setId("voltar");
			btn.setWidth("100%");
			btn.addEventListener(Events.ON_CLICK, new EventListener() {
				@Override
				public void onEvent(Event event) throws Exception {
					if (Events.ON_CLICK.equals(event.getName())) {
						String id = event.getTarget().getId().toLowerCase();
						if ("voltar".equals(id)) {
							Executions.sendRedirect("index");
						}

					}
				}

			});
			w.appendChild(btn);
			w.appendChild(lbox);
			w.setPage(page);
			w.setPosition("center");
		}
	}

	@SuppressWarnings("unchecked")
	public boolean chkLogin() {
		WebusuDAO usuDAO;
		HashMap h;
		session = (HttpSession) Executions.getCurrent().getDesktop().getSession().getNativeSession();
		try {
			if (session.getAttribute("usuario") == null)
				return false;

			WebusuVO u = (WebusuVO) session.getAttribute("usuario");
			usuDAO = new WebusuDAO();

			if (session.getAttribute("usumnu") != null)
				h = (HashMap) session.getAttribute("usumnu");
			else {
				h = new HashMap();
				h.put("codusu", u.getCodusu());
			}

			lisSistemas = usuDAO.getUsuSistema(h);

			if (lisSistemas.size() == 1) {
				h.put("codsis", lisSistemas.get(0).getCodsis());
				h.put("nomsis", lisSistemas.get(0).getNomsis());
				session.setAttribute("usumnu", h);
				Executions.getCurrent().sendRedirect("menuGeral");
				return true;
			} else if (lisSistemas.size() == 0) {
				h.put("codsis", null);
				lisSistemas = usuDAO.getUsuSistema(h);
				if (lisSistemas.size() == 0)
					return false;

			}

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

}

class iWebsis implements ListitemRenderer {
	@Override
	public void render(Listitem item, Object data, int index) throws Exception {
		String urlImg = "http://172.17.0.18:8080/weblogin/imagens/";
		if (data instanceof WebsisVO) {
			WebsisVO s = (WebsisVO) data;
			Listcell[] cell = new Listcell[] { new Listcell(), new Listcell() };
			Image i = new Image();

			if (s.getImgsis() != null)
				i.setSrc(urlImg + s.getImgsis());
			else {
				java.io.InputStream is = getClass().getResourceAsStream("/web/images/system.png");
				org.zkoss.image.Image ig = new AImage("Icon", is);
				i.setContent(ig);
			}
			item.setHeight("55px");
			cell[0].appendChild(i);
			cell[1].appendChild(new Label(s.getNomsis()));
			item.appendChild(cell[0]);
			item.appendChild(cell[1]);
			item.setValue(data);
		}
	}
}
