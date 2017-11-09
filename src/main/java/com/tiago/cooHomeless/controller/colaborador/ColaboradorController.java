package com.tiago.cooHomeless.controller.colaborador;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
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
 
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Colaborador>> listAllColaboradores() {
        List<Colaborador> colaborador = this.colaboradorRepository.findAll();
        if (colaborador.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Colaborador>>(colaborador, HttpStatus.OK);
    }
 
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView listar() {
        List<Colaborador> colaboradores = this.colaboradorRepository.findAll();
    	return new ModelAndView("/template/colaborador/lista", "colaboradores", colaboradores);
    }
    
    // -------------------Retrieve Single User By Id------------------------------------------
 
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getColaboradorById(@PathVariable("id") Long id) {
        Colaborador colaborador = this.colaboradorRepository.findById(id);
        if (colaborador == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Colaborador>(colaborador, HttpStatus.OK);
    }
    
    // -------------------Retrieve Single User By Cpf------------------------------------------
        
    @RequestMapping(value = "/cpf/{cpf}", method = RequestMethod.GET)
    public ResponseEntity<?> getColaboradorByCpf(@PathVariable("cpf") String cpf) {
    	logger.info("OKAY getByCpf");
        Optional<Colaborador> colaborador = this.colaboradorRepository.findByCpf(cpf);
        
        if (colaborador.get() == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Colaborador>(colaborador.get(), HttpStatus.OK);
    }
 
    // -------------------Create a User-------------------------------------------
 
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createColaborador(@RequestBody Colaborador colaborador, UriComponentsBuilder ucBuilder) {
        this.colaboradorRepository.save(colaborador);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(colaborador.getIdColaborador()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    // ------------------- Update a User ------------------------------------------------
 
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateColaborador(@PathVariable("id") Long id, @RequestBody Colaborador colaborador) {
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
    public ResponseEntity<?> deleteColaborador(@PathVariable("id") Long id) {
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
    public ResponseEntity<Colaborador> deleteAllColaboradores() {
        logger.info("Deleting All Users");
 
        this.colaboradorRepository.deleteAll();
        return new ResponseEntity<Colaborador>(HttpStatus.NO_CONTENT);
    }
}
