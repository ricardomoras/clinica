package med.voll.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosListarConsulta;

@Controller
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
	
	@Autowired
	private  AgendaDeConsultaService service;
	
	@Autowired 
	private ConsultaRepository consultaRepository;

	@PostMapping
	@Transactional	
	public ResponseEntity<?> agendar(@RequestBody @Valid DatosAgendarConsulta datos){
		
		var response = service.agendar(datos);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<?> consultar(){
		
		return ResponseEntity.ok(consultaRepository.findAll().stream().map(DatosListarConsulta::new));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> eliminarConsula(@PathVariable Long id) {
		Consulta consulta = consultaRepository.getReferenceById(id);
		consultaRepository.delete(consulta);
		
		return ResponseEntity.noContent().build();
	}
	
}
