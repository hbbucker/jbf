<%@ page contentType="text/css;charset=UTF-8" %> 
<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %> 
<c:set var="project" value="bbm/img"/> 

	.z-tabpanel, .z-tabbox-ver .z-tabpanels-ver {
		border:0px;
		padding:0px;
	}

	.z-window-modal-cm, .z-window-highlighted-cm, .z-window-overlapped-cm {
		border:0px;
		margin:0;
		padding:0;
	}
	.z-window-overlapped-cnt, .z-window-highlighted-cnt, .z-window-popup-cnt {
	    padding: 0 !important;
	}


	div.z-grid {
		background:#FFFFFF none repeat scroll 0 0;
		border:0px;
		overflow:hidden;
	}
	tr.z-row td.z-row-inner {
		background: #FFFFFF;
		border:0px;
	}
	tr.z-grid-odd td.z-row-inner, tr.z-grid-odd {
		background: #FFFFFF;
	}
	body {
			padding:0px;
			margin:0px;	
	}
	
	.window_body{
		background: white url(${c:encodeURL(c:cat3('~./',project,'/bbm/login/bg_login.jpg'))}) no-repeat bottom right;
	}
	
	.window_login .z-window-overlapped-tl {
		/*background-image: url(${c:encodeURL(c:cat3('~./',project,'/bbm/login/wnd-ol-corner.png'))});*/
		background-image: none;
		background-color: transparent;
	}
	.window_login .z-window-overlapped-tr {
		/*background-image: url(${c:encodeURL(c:cat3('~./',project,'/bbm/login/wnd-ol-corner.png'))});
		background-position: right -56px;*/
		background-image: none;
		background-color: transparent;
	}
	
	.window_login .z-window-overlapped-bl {
		/*background-image: url(${c:encodeURL(c:cat3('~./',project,'/bbm/login/wnd-ol-corner.png'))});*/
		background-image: none;
		background-color: transparent;
	}
	.window_login .z-window-overlapped-br {
		/*background-image: url(${c:encodeURL(c:cat3('~./',project,'/bbm/login/wnd-ol-corner.png'))});
		background-position: right -93px;*/
		background-image: none;
		background-color: transparent;
	}
	
	.window_login .z-window-overlapped-hl {
		/*background-image: url(${c:encodeURL(c:cat3('~./',project,'/bbm/login/wnd-ol-hl.png'))});*/
		background-image: none;
		background-color: transparent;
	}
	.window_login .z-window-overlapped-hr {
		/*background-image: url(${c:encodeURL(c:cat3('~./',project,'/bbm/login/wnd-ol-hr.png'))});*/
		background-image: none;
		background-color: transparent;
	}
	
	.window_login .z-window-overlapped-cl {
		background-color:none;
		/*background-image: url(${c:encodeURL(c:cat3('~./',project,'/bbm/login/wnd-ol-hl.png'))});*/
		background-image: none;
		background-color: transparent;
	}
	.window_login .z-window-overlapped-cr {
		/*background:url(${c:encodeURL(c:cat3('~./',project,'/bbm/login/wnd-ol-hr.png'))}) repeat-y top right;*/
		background-image: none;
		background-color: transparent;
	}
	
	.window_login .z-window-overlapped-header {
		background:none;
		background-color: transparent;
		
		font-family:Arial;
		font-size:14px;
		font-weight:bold;
		
		padding: 0 !important;
		margin: 0 !important;
		
		color:#5D5D5D;
		
		text-align:center;
	}
	
	.window_login .z-window-overlapped-cnt {
		background:none;
		background-color: transparent;
	}
	
	.window_login .z-window-overlapped-cm {
		background:none;
		background-color: transparent;
	}
	
	.window_login .z-window-overlapped-hm {
		background:none;
		background-color: transparent;
	}
	
	.window_login .z-window-overlapped-cnt-noborder {
		background:none;
		background-color: transparent;
	}
	
	.window_login .z-label {
		font-family:Arial;
		font-size:14px;
		font-weight:bold;
		
		color:#135985;
	}
	
	.window_login .z-textbox {
		font-family:Arial;
		font-size:14px;
		font-weight:bold;
		
		background: #0086bd;
		
		border:2px solid #59b600;
		border-top:0px;
		border-right:0px;
		
		padding:5px;
		
		color: white;
		
		width:240px;
	}
	
/*RULE:TOP-LEFT*/

.window_login .z-button .z-button-tl{
	background-image : none; /*PRO*/
	background-position : top left;
	background-color : #FFA000; /*PRO*/
	height: 1px;
}
/*RULE_E*//*RULE:TOP-MIDDLE*/
.window_login .z-button .z-button-tm{
	background-image : none; /*PRO*/
	background-position : top center;
	background-color : #FFBB00; /*PRO*/
	height: 1px;
}
/*RULE_E*//*RULE:TOP-RIGHT*/
.window_login .z-button .z-button-tr{
	background-image : none; /*PRO*/
	background-position : top right;
	background-color : #FFA000; /*PRO*/
	height: 1px;
}
/*RULE_E*//*RULE:CENTER-MIDDLE*/
.window_login .z-button .z-button-cm{
	background-color : #FF8400; /*PRO*/
	background-image : none; /*PRO*/
	background-position : center center;
	color : #FFFFFF; /*PRO*/
	padding:5px;
}
/*RULE_E*//*RULE:CENTER-RIGHT*/
.window_login .z-button .z-button-cr{
	background-color : #F87F00; /*PRO*/
	background-image : none; /*PRO*/
	background-position : center right;
	width:1px;
}
/*RULE_E*//*RULE:CENTER-LEFT*/
.window_login .z-button .z-button-cl{
	background-color : #F87F00; /*PRO*/
	background-image : none; /*PRO*/
	background-position : center left;
	width: 1px;
}
/*RULE_E*//*RULE:BOTTOM-LEFT*/
.window_login .z-button .z-button-bl{
	background-color : #D76A00; /*PRO*/
	background-image : none; /*PRO*/
	background-position : bottom left;
	height:1px;
}
/*RULE_E*//*RULE:BOTTOM-MIDDLE*/
.window_login .z-button .z-button-bm{
	background-color : #C65E00; /*PRO*/
	background-image : none; /*PRO*/
	background-position : bottom center;
	height:1px;
}
/*RULE_E*//*RULE:BOTTOM-RIGHT*/
.window_login .z-button .z-button-br{
	background-color : #D76A00; /*PRO*/
	background-image : none; /*PRO*/
	background-position : bottom right;
	height:1px;
}


/* combospotlight */
.spotlight-combo {
	vertical-align:middle;
	margin:0;
	padding:0;
}

.spotlight-combo .spotlight-combo-img{

	background: url(${c:encodeURL(c:cat3('~./',project,'/bbm/spotlight/spotlight_button.gif'))}) no-repeat 0 0;
	
	width: 30px;
	height:26px;
	border-bottom: 0px;
	
}
.spotlight-combo-pp {
	background-color:#F4F4F4;
	border:2px solid #2e5498;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	overflow:auto;
}

.spotlight-combo-pp .z-combo-item{
	color:#2e5498;
	font-weight:bold;
	padding:2px;	
}

.spotlight-combo-pp .z-combo-item-over, .spotlight-combo-pp .z-combo-item-seld{
	background-color:none;
	background:url(${c:encodeURL(c:cat3('~./',project,'/bbm/spotlight/spotlight_over_bg.gif'))}) repeat-x top left;
	color:#FFF;
	font-weight:bold;
	
	font-family:Arial;
	font-size:11px;
}

.spotlight-combo-pp .z-combo-item-text{
	padding:3px;
	font-family:Arial;
	font-size:11px;
	cursor:pointer;
}


.spotlight-combo-inp {
	border:0px;
	background:url(${c:encodeURL(c:cat3('~./',project,'/bbm/spotlight/spotlight_input_bg.gif'))}) no-repeat top left;
	
	padding-left:10px;
	padding-top:5px;
	*padding-top:2px;
	
	font-family:Arial;
	font-size:11px;
	color:#2e5498;
	
	height:18px;
	line-height:18px;
}

/* menu bar 
.z-menubar-hor {
	background: transparent url(${c:encodeURL(c:cat3('~./',project,'/zul/menubar/menubar_bg.gif'))}) repeat-x top left;
	border:0px;
	height:32px;
}

.z-menu-body {
	height:24px;
}

.z-menu-body .z-menu-inner-m .z-menu-btn {
	font-family:Arial;
	font-size:12px;
	font-weight:bold;
	color:#003965;
}

.z-menu-body-over .z-menu-inner-l {
	background: transparent url(${c:encodeURL(c:cat3('~./',project,'/zul/menubar/menubutton_left.gif'))}) no-repeat top left;
}

.z-menu-body-over .z-menu-inner-m {
	background: transparent url(${c:encodeURL(c:cat3('~./',project,'/zul/menubar/menubutton_back.gif'))}) repeat-x top left;
}

.z-menu-body-over .z-menu-inner-r {
	background: transparent url(${c:encodeURL(c:cat3('~./',project,'/zul/menubar/menubutton_right.gif'))}) no-repeat top left;
}

.z-menu-body-seld .z-menu-inner-l {
	background: transparent url(${c:encodeURL(c:cat3('~./',project,'/zul/menubar/menubutton_left_seld.gif'))}) no-repeat top left;
}

.z-menu-body-seld .z-menu-inner-m {
	background: transparent url(${c:encodeURL(c:cat3('~./',project,'/zul/menubar/menubutton_back_seld.gif'))}) repeat-x top left;
}

.z-menu-body-seld .z-menu-inner-r {
	background: transparent url(${c:encodeURL(c:cat3('~./',project,'/zul/menubar/menubutton_right_seld.gif'))}) no-repeat top left;
}

.z-menu-body-seld .z-menu-inner-m .z-menu-btn,
.z-menu-body-over .z-menu-inner-m .z-menu-btn {
	color:#FFF;
}

.z-menu-body-over .z-menu-inner-m div {
	background-color : transparent;
	background-image : url(${c:encodeURL(c:cat3('~./',project,'/zul/menubar/menubutton_arrow.png'))});
	background-position : right 10px;
}

.z-menu-popup {
	background-color:#ededed;
	background-image : url(${c:encodeURL(c:cat3('~./',project,'/zul/menubar/submenu_bg.gif'))});
	border:1px solid #888;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
}

.z-menu-popup-cnt .z-menu-over,
.z-menu-popup-cnt .z-menu-item-over {
	background: transparent url(${c:encodeURL(c:cat3('~./',project,'/zul/menubar/menubutton_back.gif'))}) repeat-x top left;
}

.z-menu-popup-cnt .z-menu .z-menu-cnt,
.z-menu-popup-cnt .z-menu-item .z-menu-item-cnt {
	color:#003965 !important;
}

.z-menu-popup-cnt .z-menu-over .z-menu-cnt,
.z-menu-popup-cnt .z-menu-item-over .z-menu-item-cnt {
	color:#FFF !important;
}
*/


/* grid list 
div.z-listbox {
	border:1px solid #888;
	margin:2px;
	padding:0px;
}

div.z-listbox-header th.z-auxheader {
	padding:4px;
	border:0px;
}

div.z-listbox-header tr.z-auxhead {
	background-image:none;
	background-color:#2e5498;
}

div.z-listbox-header .z-auxheader-cnt {
	color:#FFFFFF !important;
	font-weight:bold !important;
}

div.z-listbox-header tr.z-list-head {
	background-image:url(${c:encodeURL(c:cat3('~./',project,'/zul/listbox/auxhead_bg.gif'))});;
	background-color:none;
}

div.z-list-header-cnt,
tr.z-list-item, tr.z-list-item a, tr.z-list-item a:visited {
	color:#2e5498;
}

tr.z-listbox-odd{
	background-color:#ebebeb;
}

tr.z-list-item-seld {
	background: #b3c8e8; border: 1px solid #6f97d2 !important;
}

tr.z-list-item-over {
	background: #dae7f6 !important;
}

tr.z-list-item-over-seld {
 	background: #6eadff !important;
}
*/


/* topo padrao com logomarca e subtitulo */
div#topo{
	height:65px;
	width:100%;
	background:url(${c:encodeURL(c:cat3('~./',project,'/bbm/topo/bg_topo.gif'))}) repeat-x top;
}

div#titulos {
	position:relative !important;
	height:65px;
}

div#titulos div#logomarca{
	float:left;
}

div#titulos div#spotlight{
	float:right;
	background:url(${c:encodeURL(c:cat3('~./',project,'/bbm/spotlight/spotlight_curve.gif'))}) no-repeat bottom left;
}

div#titulos div#spotlight div.input{
	margin-left:63px;
	background:url(${c:encodeURL(c:cat3('~./',project,'/bbm/spotlight/spotlight_bg.gif'))}) repeat-x bottom left;
	padding-bottom: 3px;
	margin-top: -6px;
	*margin-top: 0px;
}
div#titulos div#spotlight .buttons {
	vertical-align:middle;
}

div#titulos div#subtitulo{
	font-family:Arial;
	font-size:16px;
	font-style:italic;
	font-weight:bold;
	color:#FFF;
	position:absolute !important;
	right:10px;
	bottom:10px;
}

/* rodape */
div#rodape {
	height:26px;
	background:url(${c:encodeURL(c:cat3('~./',project,'/bbm/rodape/bg.gif'))}) repeat-x top;
}
div#rodape .mensagem {
	font-family: Arial;
	font-size: 12px;
	font-weight: bold;
	color: #555;
	line-height: 26px;
	vertical-align: middle;
}
div#rodape div#imti {
	float: right;
	height: 26px;
	/*background: url(${c:encodeURL(c:cat3('~./',project,'/bbm/rodape/logo_rodape.gif'))}) no-repeat;*/
	position:absolute !important;
	top:2px;
	/*bottom: 5px;*/
	right:10px;
}
.z-south, .z-north, .z-center {
	border:0px;
}