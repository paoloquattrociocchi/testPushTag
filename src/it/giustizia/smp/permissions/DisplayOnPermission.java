package it.giustizia.smp.permissions;

import it.giustizia.smp.form.ProcedimentoForm;
import it.giustizia.smp.form.ProfiloForm;
import it.giustizia.smp.model.TUtente;
import it.giustizia.smp.utility.Constants;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

public class DisplayOnPermission extends BodyTagSupport implements TryCatchFinally {

	private static final String	PERMISSION_SEPARATOR	= ";";
	private static final long	serialVersionUID		= 1L;
	private String				requiredPermissions;
	private String				useContext;
	private HttpServletRequest	request;
	private HttpSession			session;
	private ProcedimentoForm	procedimento;
	private TUtente				utente;
	private ProfiloForm			profilo;
	private long				idTipoProcedimento;
	private int					idUfficioUtente;
	private String				tipoUtente;
	private long				idTipoUfficio;

	@Override
	public final int doStartTag() throws JspException {

		inizializzaContesto();
		Set<Permission> permessiRichiesti = parseStringForPermissions(requiredPermissions);
		if (contestoConsenteOperazione(permessiRichiesti, useContext) && utentePossiedePermessi(permessiRichiesti, useContext)) {
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;
	}

	private final boolean utentePossiedePermessi(final Set<Permission> permessiRichiesti, final String useContext) {

		return true;
	}

	private final void inizializzaContesto() {

		request = (HttpServletRequest) pageContext.getRequest();
		session = pageContext.getSession();
		utente = (TUtente) session.getAttribute(Constants.ID_UTENTE);
		procedimento = (ProcedimentoForm) session.getAttribute(Constants.ID_PROCEDIMENTO);
		idUfficioUtente = Integer.valueOf((Integer) request.getSession().getAttribute(Constants.ID_UFFICIO_UTENTE));
		profilo = (ProfiloForm) session.getAttribute(Constants.PROFILO);
		tipoUtente = (String) session.getAttribute(Constants.TIPO_UTENTE);
		idTipoUfficio = Long.valueOf((Integer) request.getSession().getAttribute(Constants.ID_TIPO_UFFICIO));
	}

	private final boolean contestoConsenteOperazione(final Set<Permission> permessiRichiesti, final String useContext) {

		return false;
	}

	private static final Set<Permission> parseStringForPermissions(final String permessiString) {

		String[] permessiStringArray = permessiString.split(PERMISSION_SEPARATOR);
		if (permessiStringArray == null) {
			return Collections.EMPTY_SET;
		}
		Set<Permission> permessi = new HashSet<Permission>();
		for (String permessoString : permessiStringArray) {
			permessi.add(Permission.forName(permessoString));
		}
		return permessi;
	}

	public final void setRequiredPermissions(final String requiredPermissions) {

		this.requiredPermissions = requiredPermissions;
	}

	public final void setUseContext(final String useContext) {

		this.useContext = useContext;
	}

	public final void doCatch(final Throwable t) throws Throwable {

		throw new JspException(t);
	}

	public final void doFinally() {

		// Non fa nulla perché non devo rilasciare alcuna risorsa allocata.
	}
}
