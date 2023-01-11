package sasf.net.aco.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.exception.DataException;
import org.springframework.stereotype.Service;

@Service
public class FuncionesGenerales {
	
	public void validar(Object objeto, Class<?> group) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<Object>> violations = validator.validate(objeto, group);
		if (!violations.isEmpty()) {
			System.out.println("TIENES OTRA VEZ MUCHOS PROBLEMAS.");
			List<String> errores = new ArrayList<>();
			for (ConstraintViolation<Object> violation : violations) {
				errores.add(violation.getMessage());
			}
			throw new DataException(errores.toString(), null);
		}
	}
	
	
	
}
