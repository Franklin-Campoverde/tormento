package sasf.net.aco.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import sasf.net.aco.utils.CamposGenerales;

@Entity
@Getter
@Setter
@Table (name="age_persona")
public class AgePersona extends CamposGenerales implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public interface AgePersonaCreation{}
	public interface AgePersonaUpdate{}
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotNull(groups = {AgePersonaCreation.class}, 
			message = "El LicenCodigo no puede ser nulo")
	private Integer ageLicencCodigo=1;
	
	@NotNull(groups = {AgePersonaCreation.class, AgePersonaUpdate.class}, 
			message = "El Primer Nombre no puede ser nulo")
	
    @Pattern(groups = {AgePersonaCreation.class, AgePersonaUpdate.class}, regexp = "[A-Za-z]+", message="Solo letras.")
	@Size(groups = {AgePersonaCreation.class, AgePersonaUpdate.class}, min=3, max=50, message = "Que no tienes nombre o que?")
	@Column (name="primer_nombre")
	private String primerNombre;
	
	@NotNull(groups = {AgePersonaCreation.class, AgePersonaUpdate.class}, 
			message = "El Segundo Nombre no puede ser nulo")
	@NotBlank
	@Column (name="segundo_nombre")
	private String segundoNombre;
	
	@NotNull(groups = {AgePersonaCreation.class, AgePersonaUpdate.class}, 
			message = "El Apellido Paterno no puede ser nulo")
	@NotBlank(message="fue por cigarros")
	@Column (name="apellido_paterno")
	private String apellidoPaterno;
	
	@NotNull(groups = {AgePersonaCreation.class, AgePersonaUpdate.class}, 
			message = "El Apellido Materno no puede ser nulo")
	@NotBlank
	@Column (name="apellido_materno")
	private String apellidoMaterno;
	
	@Min(value=18, message="Edad minima 18")
    @Max(value=100, message="Edad maxima 100")	
	private Integer edad;
	
	@NotBlank
	private String domicilio;
	
	@NotNull(groups = {AgePersonaCreation.class, AgePersonaUpdate.class}, 
			message = "El Correo Electronico no puede ser nulo")
	@NotBlank
	@Email
	@Column (name="correo_electronico")
	private String correoElectronico;
	
	@NotNull(groups = {AgePersonaCreation.class, AgePersonaUpdate.class}, 
			message = "La Identificación no puede ser nulo")
	@NotBlank
	@Size(min=10, max=13)
	private String identificacion;
	
	
}
