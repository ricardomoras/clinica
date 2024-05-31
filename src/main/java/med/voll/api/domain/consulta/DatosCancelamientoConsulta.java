package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DatosCancelamientoConsulta(
		Long idConsulta,

		String medico,

		String paciente,

		LocalDateTime fecha,
		MotivoCancelamiento motivo
		) {



}
