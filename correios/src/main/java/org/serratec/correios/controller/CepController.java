package org.serratec.correios.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.serratec.correios.dominio.Cep;
import org.serratec.correios.repositorio.CepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cep")
public class CepController {
	
	@Autowired
	private CepRepository repository;
		
	/* Operação: GET (obter)
	 * Parâmetros de pesquisa vem pela URL como uma QueryString
	 * Exemplo: http://localhost:8080/cep?cidade=Teresopolis&uf=RJ
	 */
	@GetMapping("")
	public List<Cep> getTodos(@RequestParam Map<String,String> parametros) {				
			
		List<Cep> todos = (List<Cep>)repository.findAll(); 
		List<Cep> filtrada = new ArrayList<>();
		
		for (Cep cep : todos) {
					
			if (!parametros.getOrDefault("uf", cep.getUf()).equals(cep.getUf()))
				continue;
			
			if (!parametros.getOrDefault("cidade", cep.getCidade()).equals(cep.getCidade()))
				continue;
			
			if (!parametros.getOrDefault("numero", cep.getNumero()).equals(cep.getNumero()))
				continue;
			
			if (!parametros.getOrDefault("bairro", cep.getBairro()).equals(cep.getBairro()))
				continue;
			
			filtrada.add(cep);
		}
		
		return filtrada;
		
		/* Uso de generics
		 * 
		 return todos
			.stream()
			.filter(o -> 
				parametros.getOrDefault("uf", o.getUf()).equals(o.getUf()) &&
				parametros.getOrDefault("cidade", o.getCidade()).equals(o.getCidade()) &&
				parametros.getOrDefault("numero", o.getNumero()).equals(o.getNumero())
				parametros.getOrDefault("bairro", o.getBairro()).equals(o.getBairro())
			).collect(Collectors.toList());
		 */		
	}
	
	/* Operação: GET (obter)
	 * Parâmetro de pesquisa vem pela URL como uma variável de PATH
	 * Exemplo: http://localhost:8080/cep/25111000
	 */
	@GetMapping("/{numero}")
	public ResponseEntity<Cep> getCep(@PathVariable String numero) {
		
		Cep cep = repository.findByNumero(numero);
		
		if (cep != null)
			return new ResponseEntity<>(cep, HttpStatus.OK);
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	/* Operação: GET (obter)
	 * Parâmetro de pesquisa vem pela URL como uma variável de PATH
	 * Exemplo: http://localhost:8080/cep/uf/RJ
	 */
	@GetMapping("/uf/{uf}")
	public List<Cep> getPorUfCep(@PathVariable String uf) {
		
		List<Cep> todos = repository.findAllByUf(uf);		
				
		return todos;
	}
	
	/* Operação: POST (criar)
	 * Dados irão pelo Body em forma de JSON
	 * Exemplo: http://localhost:8080/cep/25111000
	 */
	@PostMapping
	public ResponseEntity<Cep> postCep(@RequestBody Cep novo) {
		
		Cep existente = repository.findByNumero(novo.getNumero());
		
		if (existente != null) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}		
		
		repository.save(novo);

		return new ResponseEntity<>(novo, HttpStatus.CREATED);
	}	
	
	/* Operação: PUT (atualizar)
	 * Dados irão pelo Body em forma de JSON
	 * Exemplo: http://localhost:8080/cep/25111000
	 */
	@PutMapping("/{numero}")
	public ResponseEntity<Cep> putCep(@PathVariable String numero, @RequestBody Cep modificado) {
		
		Cep existente = repository.findByNumero(numero);
		
		if (existente != null) {
			existente.setEndereco(modificado.getEndereco());
			existente.setUf(modificado.getUf());
			existente.setCidade(modificado.getCidade());
			existente.setBairro(modificado.getBairro());
			
			repository.save(existente);
			
			return new ResponseEntity<>(existente, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);		
	}
	
	/* Operação: DELETE (deletar)
	 * Parâmetro de pesquisa vem pela URL como uma variável de PATH
	 * Exemplo: http://localhost:8080/cep/25111000
	 */
	@DeleteMapping("/{numero}")
	public ResponseEntity<Cep> deleteCep(@PathVariable String numero) {
		//AUTORIZAÇÃO VIA HEADER - SEGREDO.
		//public ResponseEntity<?> deleteCep(@PathVariable String numero, @RequestHeader("segredo")String)
		//if (!"ABC113#!Brasil". equals(segredo))
		//  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errou!");*/
		
		Cep existente = repository.findByNumero(numero);
		
		if (existente != null) {
			repository.delete(existente);			
			return new ResponseEntity<>(existente, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	//AUTORIZAÇÃO VIA HEADER - SEGREDO.
	
	/*@DeleteMapping("/{numero}")
	public ResponseEntity<?> deleteCep(@PathVariable String numero, @RequestHeader("segredo")String)

	if (!"ABC113#!Brasil". equals(segredo))
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Errou!");

	    Cep existente = repository.findByNumero(numero);

	    if(existente !=null){
	        repository.delete(existente);
	        return new ResponseEntity<>(existente, HttpStatus.OK);
	    }
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }*/
	
}
