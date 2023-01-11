package sasf.net.aco.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sasf.net.aco.model.AgePersona;
import sasf.net.aco.repository.AgePersonaRepository;


@Service
public class AgePersonaService {
	
	@Autowired
	private AgePersonaRepository agePersonaRepository;
	
	public AgePersona create(AgePersona agePersona) {
		return agePersonaRepository.save(agePersona);
	}
	
	public List<AgePersona> getAllAgePersonas(){
		return agePersonaRepository.findAll();
	}
	
	public void deleteById(Long codigo) {
		agePersonaRepository.deleteById(codigo);
	}
	
	public AgePersona findById(Long codigo){
		return agePersonaRepository.findById(codigo).get();
	}
	
}
