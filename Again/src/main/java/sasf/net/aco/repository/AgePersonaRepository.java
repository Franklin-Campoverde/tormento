package sasf.net.aco.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sasf.net.aco.model.AgePersona;

public interface AgePersonaRepository extends JpaRepository<AgePersona, Long>{
	
	// public Optional<AgePersona> findById(Long codigo);
	
	//public List<AgePersona> findByPrimerNombre(String primerNombre);
	
	//public List<AgePersona> consultarPorIdYNombre(String apellidoPaterno, String primerNombre);
	
	//public List<AgePersona> findByApellidoPaternoAndNombres(String apellidoPaterno, String primerNombre);

	@Query("SELECT h FROM AgePersona h "
			 + "WHERE (h.estado<>:estadoAnulado) "
			 + "AND (:codigo is null or h.id.codigo=:codigo) "
			 + "AND (:ageLicencCodigo is null or h.id.ageLicencCodigo=:ageLicencCodigo) "
			 + "AND (:estado is null or h.estado=:estado) ")
			 public Page<AgePersona> buscarPorParametros(
					 @Param("codigo")Long codigo,
					 @Param("ageLicencCodigo")Integer ageLicencCodigo,
					 @Param("estado")String estado,
					 @Param("estadoAnulado")String estadoAnulado,
					 Pageable pageable);

}
