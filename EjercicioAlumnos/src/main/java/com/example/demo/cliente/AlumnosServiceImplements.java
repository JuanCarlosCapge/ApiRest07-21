package com.example.demo.cliente;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.AlumnosDao;
import com.example.demo.entity.Alumnos;

@Service
public class AlumnosServiceImplements implements AlumnosService{

	@Autowired
	private AlumnosDao alumnoDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Alumnos> findAll() {
		
		return (List<Alumnos>) alumnoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Alumnos findById(Long id) {
		
		return alumnoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Alumnos save(Alumnos alumnos) {
		
		return alumnoDao.save(alumnos);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		alumnoDao.deleteById(id);
	}

}
