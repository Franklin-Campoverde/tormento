package sasf.net.aco.utils;

public class Globales {
	
	private Globales() {
		super();
	}
	
	public static final String ESTADO_ACTIVO = "A";
	public static final String ESTADO_INACTIVO = "I";
	public static final String ESTADO_ANULADO = "N";
	
	public static final String PERIODO_M = "M";
	public static final String PERIODO_A = "A";
	public static final String PERIODO_S = "S";
	public static final String PERIODO_T = "T";
	
	public static final String SI = "S";
	public static final String NO = "N";
	
	public static final Integer CODIGO_APLICACION = 700;
	
	public static final int CANTIDAD_ELEMENTOS_PAGINA = 10;

	//NUEVO
	//Secuencias  primarias
	public static final Long CODIGO_SECUENCIA_TAR_TARIFARIOS = 1l;
	public static final Long CODIGO_SECUENCIA_CSN_CLIENTE_TIPOS_TARIFAS_EXC = 2l;
	
	//Secuencias por licenciatarios
	public static final Integer CODIGO_SECUENCIA_TAR_TIPOS_TARIFARIOS = 1;
	public static final Integer CODIGO_SECUENCIA_CSN_CLIENTE_TIPOS_TARIFAS = 2;
	
}
