package com.example.demo.cliente;

import java.util.List;

import com.example.demo.entity.Alumnos;



public interface AlumnosService {
	
public List<Alumnos> findAll();
	
	public Alumnos findById(Long id);
	
	public Alumnos save(Alumnos alumnos);
	
	public void delete(Long id);
	
}
