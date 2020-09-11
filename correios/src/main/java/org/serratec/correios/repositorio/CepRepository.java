package org.serratec.correios.repositorio;

import java.util.List;

import org.serratec.correios.dominio.Cep;
import org.springframework.data.repository.CrudRepository;

public interface CepRepository extends CrudRepository<Cep, String> {
	
	Cep findByNumero(String numero);
	List<Cep> findAllByUf(String uf);
}
