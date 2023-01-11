package sasf.net.aco.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import sasf.net.aco.utils.CamposGenerales;
import sasf.net.aco.utils.FuncionesGenerales;
import sasf.net.aco.utils.Globales;
import sasf.net.aco.dao.AgePersonaDao;
import sasf.net.aco.model.AgePersona;
import sasf.net.aco.repository.AgePersonaRepository;

@RestController
@RequestMapping (path ="/api/age_persona/")
public class AgePersonaController {
	
	@Autowired
	AgePersonaRepository agePersonaRepository;
	
	@Autowired
	public TransactionTemplate transactionTemplate;
	
	@Autowired
	public FuncionesGenerales fg;
	
	@Operation(summary = "consultarGeneral")
	@GetMapping
	public Page<?> listarTodasLasPersonas (
			@RequestParam(name = "codigo", required = false) Long codigo,
			@RequestParam(name = "ageLicencCodigo", required = false) Integer ageLicencCodigo,
			@RequestParam(name = "estado", required = false) String estado,
			@PageableDefault(page = 0, size = Globales.CANTIDAD_ELEMENTOS_PAGINA) Pageable pageable){
			
		Page<AgePersona> agePersona = agePersonaRepository.buscarPorParametros(codigo,
				ageLicencCodigo, estado, Globales.ESTADO_ANULADO, pageable);

		List<AgePersonaDao> agePersonaDao = fromEntityToDaoList(agePersona);
		return new PageImpl<>(agePersonaDao, pageable, agePersona.getTotalElements());
	}
	
	@Operation(summary = "consultarPorCodigo")
	@GetMapping (path= "/{codigo}")
	public AgePersonaDao listarPersonasPorId (@PathVariable (name="codigo") Long codigo ){
		Optional<AgePersona>agePersona = agePersonaRepository.findById(codigo);
		if (!agePersona.isPresent() || agePersona.get().getEstado().equals(Globales.ESTADO_ANULADO)) {
			throw new NoResultException("La Persona con codigo " + codigo + " no existe.");
		}
		return fromEntityToDao(agePersona.get());
	}
	
	@Operation(summary = "guardar")
	@PostMapping
	public ResponseEntity<AgePersona> crear (@Valid @RequestBody  AgePersona agePersona, HttpServletRequest request){
		fg.validar(agePersona, AgePersona.AgePersonaCreation.class);
		fg.validar(agePersona, CamposGenerales.CamposGeneralesCreation.class);
		validacionesPost(agePersona);
		LocalDate fechaActual = LocalDate.now();
		agePersona.setFechaModificacion(fechaActual);
		agePersonaRepository.save(agePersona);
		return new ResponseEntity<>(agePersona, HttpStatus.OK);
		
	}

	@Operation(summary = "crearVarios")
	@PostMapping("/varios")
	public ResponseEntity<List<AgePersona>> crearVarios(@RequestBody @Valid List<AgePersona> transacciones,
																						HttpServletRequest request) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				for (AgePersona transaccion : transacciones) {
					fg.validar(transaccion, AgePersona.AgePersonaCreation.class);
					fg.validar(transaccion, CamposGenerales.CamposGeneralesCreation.class);
					LocalDate fechaActual = LocalDate.now();
					transaccion.setFechaModificacion(fechaActual);
					validacionesPost(transaccion);
					agePersonaRepository.save(transaccion);
				}
			}
		});
		return new ResponseEntity<>(transacciones, HttpStatus.CREATED);
	}
	
	@Operation(summary = "actualizar")
	@PutMapping 
	public ResponseEntity<AgePersona> actualizar (@RequestBody  AgePersona agePersona, 
			HttpServletRequest request){
		fg.validar(agePersona, AgePersona.AgePersonaUpdate.class);
		fg.validar(agePersona, CamposGenerales.CamposGeneralesUpdate.class);
		validacionesPut(agePersona);
		LocalDate fechaActual = LocalDate.now();
		agePersona.setFechaModificacion(fechaActual);
		agePersonaRepository.save(agePersona);
		return new ResponseEntity<>(agePersona, HttpStatus.OK);		
	}
	
	@Operation(summary = "actualizarVarios")
	@PutMapping("/varios")
	public ResponseEntity<List<AgePersona>> actualizarVarios(@RequestBody List<AgePersona> transacciones, 
																		HttpServletRequest request) {
			
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				for (AgePersona transaccion : transacciones) {
					fg.validar(transaccion, AgePersona.AgePersonaUpdate.class);
					fg.validar(transaccion, CamposGenerales.CamposGeneralesUpdate.class);
					validacionesPut(transaccion);
					LocalDate fechaActual = LocalDate.now();
					transaccion.setFechaModificacion(fechaActual);
					agePersonaRepository.save(transaccion);
				}
			}
		});
		return new ResponseEntity<>(transacciones, HttpStatus.OK);
	}

	/*
	@Operation(summary = "eliminar")
	@DeleteMapping (value = "{codigo}")
	public ResponseEntity<?> eliminarPersona (@PathVariable Long codigo){
		Map<String, Object> responseEliminar = new HashMap<>();
		try {
			agePersonaService.deleteById(codigo);
			responseEliminar.put("MENSAJE:", "Se elimino el codigo: ".concat(codigo.toString().concat(" con exito.")));
		} catch (DataAccessException e) {
			responseEliminar.put("MENSAJE:", "El Código seleccionado: ".concat(codigo.toString().concat(" tiene un error.")));
			responseEliminar.put("ERROR:",e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(responseEliminar, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(responseEliminar, HttpStatus.OK);
	}
	*/
	
	private void validacionesPost(AgePersona transaccion) {
		
		Long codigo = transaccion.getCodigo();
		
		if(codigo==0) {
			throw new DataIntegrityViolationException("Secuencia " + Globales.CODIGO_SECUENCIA_CSN_CLIENTE_TIPOS_TARIFAS +
					" El codigo " + transaccion.getCodigo() + " para la apliacion " + Globales.CODIGO_APLICACION + " no se puede crear.");
		}
		
		transaccion.getCodigo();
		if(agePersonaRepository.existsById(transaccion.getCodigo())) {
			throw new DataIntegrityViolationException("Persona con codigo " + codigo + " ya existe.");
		}
	}
	
	
	private void validacionesPut(AgePersona transaccion) {
		if (!agePersonaRepository.existsById(transaccion.getCodigo())) {
			throw new NoResultException("Persona con codigo " + transaccion.getCodigo()
					+ " no encontrado");
		}
		AgePersona agePersona = agePersonaRepository.findById(transaccion.getCodigo()).orElse(transaccion);

		
		if (transaccion.getCodigo() == null) {
			transaccion.setCodigo(agePersona.getCodigo());
		}
		if (transaccion.getAgeLicencCodigo() == null) {
			transaccion.setAgeLicencCodigo(agePersona.getAgeLicencCodigo());
		}
		if (transaccion.getPrimerNombre() == null) {
			transaccion.setPrimerNombre(agePersona.getPrimerNombre());
		}
		if (transaccion.getSegundoNombre()==null) {
			transaccion.setSegundoNombre(agePersona.getSegundoNombre());
		}
		if (transaccion.getApellidoPaterno()==null) {
			transaccion.setApellidoPaterno(agePersona.getApellidoPaterno());
		}
		if (transaccion.getApellidoMaterno() == null) {
			transaccion.setApellidoMaterno(agePersona.getApellidoMaterno());
		}
		if (transaccion.getEdad() == null) {
			transaccion.setEdad(agePersona.getEdad());
		}
		if (transaccion.getDomicilio() == null) {
			transaccion.setDomicilio(agePersona.getDomicilio());
		}
		if (transaccion.getCorreoElectronico() == null) {
			transaccion.setCorreoElectronico(agePersona.getCorreoElectronico());
		}
		if (transaccion.getIdentificacion() == null) {
			transaccion.setIdentificacion(agePersona.getIdentificacion());
		}
		if (transaccion.getEstado() == null) {
			transaccion.setEstado(agePersona.getEstado());
		}
		if (transaccion.getFechaEstado() == null) {
			transaccion.setFechaEstado(agePersona.getFechaEstado());
		}
		if (transaccion.getFechaIngreso() == null) {
			transaccion.setFechaIngreso(agePersona.getFechaIngreso());
		}
		if (transaccion.getFechaModificacion() == null) {
			transaccion.setFechaModificacion(agePersona.getFechaModificacion());
		}
		if (transaccion.getObservacionEstado() == null) {
			transaccion.setObservacionEstado(agePersona.getObservacionEstado());
		}
		
	}
	
	private static List<AgePersonaDao> fromEntityToDaoList(Page<AgePersona> responseList) {
		List<AgePersonaDao> pageDao = new ArrayList<>();

		for (int i = 0; i < responseList.getNumberOfElements(); i++) {
			pageDao.add(fromEntityToDao(responseList.getContent().get(i)));
		}

		return pageDao;
	}

	private static AgePersonaDao fromEntityToDao(AgePersona response) {
		
		return new AgePersonaDao(
				response.getCodigo(),
				response.getAgeLicencCodigo(), 
				response.getPrimerNombre(),
				response.getSegundoNombre(), 
				response.getApellidoPaterno(), 
				response.getApellidoMaterno(),
				response.getEdad(),
				response.getDomicilio(),
				response.getCorreoElectronico(),
				response.getIdentificacion(),
				response.getEstado(), 
				response.getFechaEstado(), 
				response.getFechaIngreso(),
				response.getFechaModificacion(),
				response.getObservacionEstado()
				);
	}
	
}
