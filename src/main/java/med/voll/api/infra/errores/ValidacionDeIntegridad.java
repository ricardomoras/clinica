package med.voll.api.infra.errores;

public class ValidacionDeIntegridad extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidacionDeIntegridad(String s) {
		super(s);
	}

}
