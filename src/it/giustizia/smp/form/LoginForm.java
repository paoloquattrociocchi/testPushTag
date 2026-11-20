package it.giustizia.smp.form;

public class LoginForm {

	private ProfiloForm	profilo	= new ProfiloForm();

	public final ProfiloForm getProfilo() {

		return profilo;
	}

	public final void setProfilo(final ProfiloForm profilo) {

		this.profilo = profilo;
	}
}
