package sasf.net.aco.utils.exceptions;

import java.time.format.DateTimeParseException;

public class DateException extends DateTimeParseException{
	
private static final long serialVersionUID = 1L;
	
	public DateException(String message, CharSequence parsedData, int errorIndex) {
		super(message, parsedData, errorIndex);
	}
	
}
