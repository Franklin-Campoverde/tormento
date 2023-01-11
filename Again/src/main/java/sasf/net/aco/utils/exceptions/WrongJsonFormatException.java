package sasf.net.aco.utils.exceptions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class WrongJsonFormatException extends InvalidFormatException{

	private static final long serialVersionUID = 1L;

	public WrongJsonFormatException(JsonParser p, String msg, Object value, Class<?> targetType) {
		super(p, msg, value, targetType);
		// TODO Auto-generated constructor stub
	}
}
