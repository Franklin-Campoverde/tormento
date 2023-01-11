package sasf.net.aco.utils;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class CamposGenerales {

	public interface CamposGeneralesCreation {}
	public interface CamposGeneralesUpdate {}
	
	@Column(name = "estado", nullable = false)
	@NotNull(groups = CamposGeneralesCreation.class, message = "El estado no puede ser nulo")
	@Pattern(groups = { CamposGeneralesCreation.class,
			CamposGeneralesUpdate.class }, regexp = "["+Globales.ESTADO_ACTIVO+Globales.ESTADO_INACTIVO+Globales.ESTADO_ANULADO+"]",
			message = "El estado debe ser "+Globales.ESTADO_ACTIVO+", "+Globales.ESTADO_INACTIVO+" o "+Globales.ESTADO_ANULADO)
	protected String estado;
	
	@NotNull(groups = CamposGeneralesCreation.class, message = "La Fecha estado no puede ser nulo")
	@Column(name = "fecha_estado", nullable = false)
	@Pattern(groups = { CamposGeneralesCreation.class,
		CamposGeneralesUpdate.class }, regexp ="", message = "bobo o que?")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected LocalDate fechaEstado;

	@Column(name = "fecha_ingreso", updatable = false, nullable = false)
	@NotNull(groups = CamposGeneralesCreation.class, message = "La Fecha ingreso no puede ser nulo")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected LocalDate fechaIngreso;

	@Column(name = "fecha_modificacion", nullable = false)
	@NotNull(groups = CamposGeneralesCreation.class, message = "La Fecha modificacion no puede ser nulo")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	protected LocalDate fechaModificacion;

	@Column(name = "observacion_estado", nullable = false)
	@Size(max=2000)
	@NotNull(groups = CamposGeneralesCreation.class, message = "La Observacion estado no puede ser nulo")
	protected String observacionEstado;
	
}
