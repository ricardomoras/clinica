package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

public record DatosListarConsulta(

		Long id,

		String medico,

		String paciente,

		LocalDateTime fecha)
{
	 public DatosListarConsulta(Consulta consulta) {
	        this(consulta.getId(), consulta.getMedico().getNombre(), consulta.getPaciente().getNombre(), consulta.getFecha());
	    }


}
