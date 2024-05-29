package med.voll.api.domain.medico;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

	Page<Medico> findByActivoTrue(Pageable pageable);

	@Query("""
			select m from Medico m
			where m.activo=1 and
			m.especialidad=:especialidad and
			m.id not in(
			select c.medico.id from Consulta c
			c data=:fecha
			)
			order by rand()
			limit 1 
			""")
	Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, @NotNull @Future LocalDateTime fecha);

}
