package sasf.net.aco.dao;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AgePersonaDao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private Integer ageLicencCodigo;
	private String primerNombre;
	private String segundoNombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private Integer edad;
	private String domicilio;
	private String correoElectronico;
	private String identificacion;
	private String estado;
	private LocalDate fechaEstado;
	private LocalDate fechaIngreso;
	private LocalDate fechaModificacion;
	private String observacionEstado;

}
