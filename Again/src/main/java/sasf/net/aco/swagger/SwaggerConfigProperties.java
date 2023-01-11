package sasf.net.aco.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration("swaggerConfigProperties")
public class SwaggerConfigProperties {

	@Value("${swagger.package}")
    private String swaggerPackage;
	
	@Value("${swagger.titulo}")
    private String titulo;
	
	@Value("${swagger.descripcion}")
    private String descripcion;

	@Value("${swagger.contacto}")
    private String contacto;
	
	@Value("${swagger.pagina.web}")
    private String paginaWeb;
	
	@Value("${swagger.email}")
    private String email;
	
	@Value("${swagger.version}")
    private String version;
	
	public String getSwaggerPackage() {
		return swaggerPackage;
	}

	public void setSwaggerPackage(String swaggerPackage) {
		this.swaggerPackage = swaggerPackage;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getPaginaWeb() {
		return paginaWeb;
	}

	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
