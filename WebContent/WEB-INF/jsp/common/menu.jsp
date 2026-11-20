<%@page import="it.giustizia.smp.model.TAnMenu"%>
<%@page import="java.util.ArrayList"%>

<script language="javascript" type="text/javascript">
function controllaMenu(){
	if(<%=request.getSession().getAttribute("clickmenu1")%> != null){
		openCloseMenu("menu_" + <%=request.getSession().getAttribute("clickmenu1")%>);
		<%request.getSession().removeAttribute("clickmenu1");%>
	}
	if(<%=request.getSession().getAttribute("clickmenu2")%> != null){
		openCloseMenu("menu_" + <%=request.getSession().getAttribute("clickmenu2")%>);
		<%request.getSession().removeAttribute("clickmenu2");%>
	}
}

function openCloseMenu(elem){
                var menu_elem = document.getElementById(elem);
    
    if (menu_elem.style.display == "none"){
                    menu_elem.style.display = "block";
    }else{
                    menu_elem.style.display = "none";
    }
}
function closeAllMenu(){
                var inputs = document.getElementsByTagName("div"); 
                for (var i = 0; i < inputs.length; i++) { 
                  if(inputs[i].id.indexOf("menu_") == 0) 
                    inputs[i].style.display = "none";
                } 
}
function openAllMenu(){
                var inputs = document.getElementsByTagName("div"); 
                for (var i = 0; i < inputs.length; i++) { 
                  if(inputs[i].id.indexOf("menu_") == 0) 
                    inputs[i].style.display = "block";
                } 
}

</script>


<%
@SuppressWarnings("unchecked")
ArrayList<TAnMenu> menuSinistra1Liv = (ArrayList<TAnMenu>)request.getSession().getAttribute("menuSinistra1Liv");
@SuppressWarnings("unchecked")
ArrayList<TAnMenu> menuSinistra2Liv = (ArrayList<TAnMenu>)request.getSession().getAttribute("menuSinistra2Liv");
@SuppressWarnings("unchecked")
ArrayList<TAnMenu> menuSinistra3Liv = (ArrayList<TAnMenu>)request.getSession().getAttribute("menuSinistra3Liv");
/*String idPadre = null;
if(request.getParameter("idPadreMenu") != null){
	idPadre = request.getParameter("idPadreMenu");
	request.getSession().setAttribute("idPadre",idPadre);
}
else{
	if(request.getSession().getAttribute("idPadre") != null)
		idPadre = request.getSession().getAttribute("idPadre").toString();
}*/
	%>
<%if(request.getSession().getAttribute("menuSinistra1Liv") != null ){ %>
  &nbsp;&nbsp;&nbsp;&nbsp;<a href="#" class="pagelinks" onClick="openAllMenu()"><img src="${pageContext.request.contextPath}/images/icoOpen.png" border="0" alt="Espandi Menu"></a>
  / <a href="#" class="pagelinks" onClick="closeAllMenu()"><img src="${pageContext.request.contextPath}/images/icoClose.png" border="0" alt="Riduci Menu"> </a>

	<img src="${pageContext.request.contextPath}/images/filo.jpg"
		class="filoMenu" style="margin-top: 40px" />
		
	<%for(int i=0; i<menuSinistra1Liv.size(); i++){
			
	%>
	<ul class="menu1liv">
		<%
			
			if(menuSinistra1Liv.get(i).getDescPercorso() != null && menuSinistra1Liv.get(i).getDescPercorso().equals("#")){
				%>
		<li>
			<a href="#" onClick="openCloseMenu('menu_<%=menuSinistra1Liv.get(i).getCodiKIdMenu()%>')"><%=menuSinistra1Liv.get(i).getDescVoce()%></a>
		</li>
		<%}
				else{%>
		<li>
		<a href="${pageContext.request.contextPath}/menu.htm?path=<%=menuSinistra1Liv.get(i).getDescPercorso()%>&descr=<%=menuSinistra1Liv.get(i).getDescVoce()%>&idPadre=<%=menuSinistra1Liv.get(i).getNumePadre()%>&idMenuAttuale=<%=menuSinistra1Liv.get(i).getCodiKIdMenu()%>&livello=2"><%=menuSinistra1Liv.get(i).getDescVoce()%></a>
		</li>
		<%}%>
		<div id="menu_<%=menuSinistra1Liv.get(i).getCodiKIdMenu()%>" style="display: none;">
		<%
			if(request.getSession().getAttribute("menuSinistra2Liv") != null ){
				for(int j=0; j<menuSinistra2Liv.size(); j++){
							if(menuSinistra2Liv.get(j).getNumePadre().intValue()==(menuSinistra1Liv.get(i).getCodiKIdMenu())){%>
		
			<ul class="menu2liv">
				<%
					if(menuSinistra2Liv.get(j).getDescPercorso() != null && menuSinistra2Liv.get(j).getDescPercorso().equals("#")){
				%>
			<li>
				<a href="#" onClick="openCloseMenu('menu_<%=menuSinistra2Liv.get(j).getCodiKIdMenu()%>')"><%=menuSinistra2Liv.get(j).getDescVoce()%></a>
			</li>
				<%}
					else{%>
		
			<li>
				<a href="${pageContext.request.contextPath}/menu.htm?path=<%=menuSinistra2Liv.get(j).getDescPercorso()%>&descr=<%=menuSinistra2Liv.get(j).getDescVoce()%>&idPadre=<%=menuSinistra2Liv.get(j).getNumePadre()%>&idMenuAttuale=<%=menuSinistra2Liv.get(j).getCodiKIdMenu()%>&livello=3" onClick="openCloseMenu('menu_<%=menuSinistra2Liv.get(j).getCodiKIdMenu()%>')"><%=menuSinistra2Liv.get(j).getDescVoce()%></a>
			</li>
					<%} %>
				<%if(request.getSession().getAttribute("menu2")!=null && request.getSession().getAttribute("menu2").equals("menu_" + menuSinistra2Liv.get(j).getDescVoce().replaceAll(" ","_")+i)){ %>
					<div id="menu_<%=menuSinistra2Liv.get(j).getCodiKIdMenu()%>" style="display: block;">
				<%}
				 else{%>
					<div id="menu_<%=menuSinistra2Liv.get(j).getCodiKIdMenu()%>" style="display: none;">
				<%} %>
					<%if(request.getSession().getAttribute("menuSinistra3Liv") != null ){
						for(int k=0; k<menuSinistra3Liv.size(); k++){
									if(menuSinistra3Liv.get(k).getNumePadre().intValue() ==(menuSinistra2Liv.get(j).getCodiKIdMenu())){%>
			            <ul class="menu3liv">
			                <li>
			                	<a href="${pageContext.request.contextPath}/menu.htm?path=<%=menuSinistra3Liv.get(k).getDescPercorso()%>&descr=<%=menuSinistra3Liv.get(k).getDescVoce()%>&idPadre=<%=menuSinistra3Liv.get(k).getNumePadre()%>&idMenuAttuale=<%=menuSinistra3Liv.get(k).getCodiKIdMenu()%>&livello=4"><%=menuSinistra3Liv.get(k).getDescVoce()%></a>
							</li>
			            </ul>
			            <%			}
																	}
						}%>
				</div>
			</ul>
		
		<%}
					}%>
		</div>
	</ul>
	<img src="${pageContext.request.contextPath}/images/filo.jpg"
		class="filoMenu" />

	<% 
			}%>
	
			<%
		
	} %>
<div style="width:200px; height:115px"></div>
<div style="position: absolute; bottom: 0px">
	<img src="${pageContext.request.contextPath}/images/pagine_immagine.png" />
</div>
<%}
	else{%>
<div
	style="width:100%; height:500px; background-image: url('${pageContext.request.contextPath}/images/home_immagine.png'); background-repeat: no-repeat;">
</div>
<%} %>




