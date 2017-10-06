package com.tiago.cooHomeless.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import com.tiago.cooHomeless.model.Colaborador;
import com.tiago.cooHomeless.repository.ColaboradorRepository;

@Controller
@RequestMapping("/colaborador")
public class ColaboradorController {
    
	final static Logger logger = Logger.getLogger(ColaboradorController.class);
	
    private final ColaboradorRepository colaboradorRepository;
 
	@Autowired
    public ColaboradorController(ColaboradorRepository colaboradorRepository) {
		this.colaboradorRepository = colaboradorRepository;
	}
	
	// -------------------Retrieve All Users---------------------------------------------
 
    @RequestMapping(value = "/all/", method = RequestMethod.GET)
    public ResponseEntity<List<Colaborador>> listAllUsers() {
        List<Colaborador> colaborador = this.colaboradorRepository.findAll();
        if (colaborador.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Colaborador>>(colaborador, HttpStatus.OK);
    }
 
    // -------------------Retrieve Single User------------------------------------------
 
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        Colaborador colaborador = this.colaboradorRepository.findById(id);
        if (colaborador == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Colaborador>(colaborador, HttpStatus.OK);
    }
 
    // -------------------Create a User-------------------------------------------
 
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody Colaborador colaborador, UriComponentsBuilder ucBuilder) {
        this.colaboradorRepository.save(colaborador);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(colaborador.getIdColaborador()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    // ------------------- Update a User ------------------------------------------------
 
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestBody Colaborador colaborador) {
 
        Colaborador currentColaborador = this.colaboradorRepository.findById(id);
 
        if (currentColaborador == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
 
        currentColaborador.setNome(colaborador.getNome());
        currentColaborador.setCpf(colaborador.getCpf());
 
        this.colaboradorRepository.save(currentColaborador);
        return new ResponseEntity<Colaborador>(currentColaborador, HttpStatus.OK);
    }
 
    // ------------------- Delete a User-----------------------------------------
 
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        Colaborador colaborador = this.colaboradorRepository.findById(id);
        if (colaborador == null) {
            logger.debug("Unable to delete. User with id "+id+ " not found.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        this.colaboradorRepository.delete(id);
        return new ResponseEntity<Colaborador>(HttpStatus.NO_CONTENT);
    }
 
    // ------------------- Delete All Users-----------------------------
 
    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    public ResponseEntity<Colaborador> deleteAllUsers() {
        logger.info("Deleting All Users");
 
        this.colaboradorRepository.deleteAll();
        return new ResponseEntity<Colaborador>(HttpStatus.NO_CONTENT);
    }
}
