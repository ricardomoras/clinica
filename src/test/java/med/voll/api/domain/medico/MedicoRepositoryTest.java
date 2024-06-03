package med.voll.api.domain.medico;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private TestEntityManager em;
	
    @Test
    @DisplayName("deberia retornar nulo cuando el medico se encuentre en consulta con otro paciente en ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario1() {
    	
    	//given
    	var proximoLunes10H = LocalDate.now()
    			.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
    			.atTime(10,0);
    	
    	var medico = registarMedico("Ricardo","rmora@mail.com","123456",Especialidad.ORTOPEDIA);
    	var paciente = registarPaciente("Juan","j@mail.com","145659");
    	registrarConsulta(medico,paciente,proximoLunes10H);
    	
    	//when
    	var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.ORTOPEDIA, proximoLunes10H);
    	
    	//then
    	assertThat(medicoLibre).isNull();
    }
    
    @Test
    @DisplayName("deberia retornar un medico cuando realize la consulta en el repositorio")
    void seleccionarMedicoConEspecialidadEnFechaEscenario2() {
    	//given
    	var proximoLunes10H = LocalDate.now()
    			.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
    			.atTime(10,0);
    	
    	var medico = registarMedico("Ricardo","rmora@mail.com","123456",Especialidad.ORTOPEDIA);
    	
    	//when
    	var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.ORTOPEDIA, proximoLunes10H);
    	
    	//then
    	assertThat(medicoLibre).isEqualTo(medico);
    }

	private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
		em.persist(new Consulta(null,medico, paciente, fecha, null));
	}
	
	private Medico registarMedico(String nombre,String email,String documento, Especialidad especialidad) {
		var medico = new Medico(datosMedico(nombre,email,documento,especialidad));
		em.persist(medico);
		return medico;
	}
	
	private Paciente registarPaciente(String nombre,String email,String documento) {
		var paciente = new Paciente(datosPaciente(nombre,email,documento));
		em.persist(paciente);
		return paciente;
	}
	
	private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad) {
		return new DatosRegistroMedico(
				nombre,
				email,
				"45557878",
				documento,
				especialidad,
				datosDireccion());
	}

	private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento) {
		return new DatosRegistroPaciente(
				nombre,
				email,
				"45557878",
				documento,
				datosDireccion());
	}

	private  DatosDireccion datosDireccion() {
		return new DatosDireccion(
				"loma",
				"blanca",
				"cancun",
				"654",
				"90");
	}
	
}