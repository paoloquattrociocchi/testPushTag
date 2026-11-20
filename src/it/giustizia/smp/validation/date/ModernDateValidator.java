package it.giustizia.smp.validation.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ModernDateValidator implements ConstraintValidator<ModernDate, Date> {

	private String	threshold;
	private String	format;
	private String	message;

	public void initialize(ModernDate annotation) {

		this.threshold = annotation.threshold();
		this.format = annotation.format();
		this.message = annotation.message() + threshold;
	}

	public boolean isValid(Date data, ConstraintValidatorContext context) {

		if (data == null) {
			return true;
		}
		SimpleDateFormat frmt = new SimpleDateFormat(format);
		Date thresholdDate = null;
		try {
			thresholdDate = frmt.parse(threshold);
		} catch (ParseException exc) {
			exc.printStackTrace();
		}
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		return data.after(thresholdDate);
	}
}
