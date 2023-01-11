package sasf.net.aco.swagger;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class SwaggerConfig {
	
	@Bean
	  public GroupedOpenApi publicApi(SwaggerConfigProperties swaggerConfigProperties) {
	      return GroupedOpenApi.builder()
	              .group(swaggerConfigProperties.getSwaggerPackage())
	              .pathsToMatch("/**")
	              .build();
	  }
	

	
	@Bean
	  public OpenAPI apiEndPointsInfo(SwaggerConfigProperties swaggerConfigProperties) {
		
	      return new OpenAPI()
	              .info(new Info().title(swaggerConfigProperties.getTitulo())
	              .description(swaggerConfigProperties.getDescripcion())
	              .version(swaggerConfigProperties.getVersion())
	              .license(new License().name("Apache 2.0").url("http://springdoc.org")))
	              .externalDocs(new ExternalDocumentation()
	              .description(swaggerConfigProperties.getVersion())
	              .url(swaggerConfigProperties.getPaginaWeb()))
	              .addSecurityItem(new SecurityRequirement().addList("Autorización token"))
	              .components(new Components()
	              .addSecuritySchemes("Autorización token", new SecurityScheme()
	              .name("Authorization").type(Type.APIKEY).in(In.HEADER)));
	  }
}
