package com.example.demo.controllers;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.cliente.AlumnosService;
import com.example.demo.entity.Alumnos;



@RestController
@RequestMapping("/api")
public class AlumnosControllers {
	
	@Autowired
	private AlumnosService alumnoService;
	
	
	@GetMapping("/alumnos")
	public List<Alumnos> index(){
		return alumnoService.findAll();
	}
	
	@GetMapping("/alumnos/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Alumnos alumnos = null;
		Map<String,Object> response = new HashMap<>();
		
		try {
			alumnos = alumnoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje","Error al realizar consulta en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(alumnos == null) {
			response.put("mensaje", "El cliente ID: ".concat(id.toString().concat("no existe en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Alumnos>(alumnos,HttpStatus.OK);
	}
	
	
	@PostMapping("/alumnos")
	public ResponseEntity<?> create(@RequestBody Alumnos alumno){
		Alumnos alumnoNuevo = null;
		Map<String,Object> response = new HashMap<>();
		
		try {
			alumnoNuevo = alumnoService.save(alumno);
		} catch (DataAccessException e) {
			response.put("mensaje","Error al insertar en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El alumno ha sido creado con éxito!");
		response.put("alumno", alumnoNuevo);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/alumnos/{id}")
	public ResponseEntity<?> update(@RequestBody Alumnos alumno, @PathVariable Long id) {
		Alumnos alumnoActual= alumnoService.findById(id);
		
		Alumnos alumnoActualizado = null;
		Map<String,Object> response = new HashMap<>();
		
		if(alumnoActual == null){
			response.put("mensaje", "Error: no se pudo editar, el alumno ID: ".concat(id.toString().concat("no existe el id en la base de datos")));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		try {
			alumnoActual.setApellido(alumno.getApellido());
			alumnoActual.setNombre(alumno.getNombre());
			alumnoActual.setEmail(alumno.getEmail());
			alumnoActual.setTelefono(alumno.getTelefono());
			alumnoActual.setCreatedAt(alumno.getCreatedAt());
			
			
			 alumnoActualizado= alumnoService.save(alumnoActual);
		} catch (DataAccessException e) {
			response.put("mensaje","Error al actualizar el alumno en base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje","El alumno ha sido actualizado con éxito!");
		response.put("alumno", alumnoActualizado);
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping("alumnos/{id}")
	public void delete(@PathVariable Long id) {
		alumnoService.delete(id);
	}

}
