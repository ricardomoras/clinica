package med.voll.api.controllers;


import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.DatosActualizarMedico;
import med.voll.api.domain.medico.DatosListadoMedico;
import med.voll.api.domain.medico.DatosRegistroMedico;
import med.voll.api.domain.medico.DatosRespuestaMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
	
	@Autowired
	private MedicoRepository medicoRepository;

	@PostMapping
	public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
			UriComponentsBuilder uriComponentsBuilder) {
		Medico medico  = medicoRepository.save(new Medico(datosRegistroMedico));
		DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(),medico.getNombre(),
				medico.getEmail(),medico.getTelefono(),medico.getEspecialidad().toString(),
				new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
						medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
		URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
		return ResponseEntity.created(url).body(datosRespuestaMedico);
		
	}
	
	@GetMapping
	public ResponseEntity<Page<DatosListadoMedico>>		 listadoDeMedicos(@PageableDefault(size = 2) Pageable pageable){
//		return medicoRepository.findAll(pageable).map(DatosListadoMedico::new);
		return ResponseEntity.ok(medicoRepository.findByActivoTrue(pageable).map(DatosListadoMedico::new));
		
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody DatosActualizarMedico  datosActualizarMedico) {
		Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
		medico.actualizarMedico(datosActualizarMedico);
		return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(),medico.getNombre(),
				medico.getEmail(),medico.getTelefono(),medico.getEspecialidad().toString(),
				new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
						medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento())));
	}
	
	// DELETE Logico
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity eliminarMedico(@PathVariable Long id) {
		Medico medico = medicoRepository.getReferenceById(id);
		medico.desactivarMedico();
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DatosRespuestaMedico> retornarDatoMedico(@PathVariable Long id) {
		Medico medico = medicoRepository.getReferenceById(id);
		DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(),medico.getNombre(),
				medico.getEmail(),medico.getTelefono(),medico.getEspecialidad().toString(),
				new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
						medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
		return ResponseEntity.ok(datosRespuestaMedico);
	}
	

}
