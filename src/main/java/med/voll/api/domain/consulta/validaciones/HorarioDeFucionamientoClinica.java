package med.voll.api.domain.consulta.validaciones;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

@Component
public class HorarioDeFucionamientoClinica implements ValidadorDeConsultas{

	public void validar(DatosAgendarConsulta datos) {
		
		var domingo = DayOfWeek.SUNDAY.equals(datos.fecha().getDayOfWeek());
		
		var antesDeApertura = datos.fecha().getHour()<7;
		
		var despuesDeCierre = datos.fecha().getHour()>19;
		
		if(domingo || antesDeApertura || despuesDeCierre) {
			throw new ValidationException("El horario de atencion de la consulta es de lunes a sabado de 7:00 a 10:00 horas")	;
		}
		
	}
}
