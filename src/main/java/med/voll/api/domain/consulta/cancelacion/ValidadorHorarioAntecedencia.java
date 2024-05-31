package med.voll.api.domain.consulta.cancelacion;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;

@Component("ValidadorHorarioAntecedenciaCancelamiento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamientoConsulta {

	@Autowired
	private ConsultaRepository repository;
	
	public void validar(DatosCancelamientoConsulta datos) {
			var consulta = repository.getReferenceById(datos.idConsulta());
			var ahora = LocalDateTime.now();
			var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();
			
			if ( diferenciaEnHoras < 24) {
				throw new ValidationException("Consulta solo puede ser cancelada con minimo 24 horas de antelacion");
			}
	}
}
