package org.serratec.correios.dominio;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cep")
public class Cep implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String numero;	
	
	@Column(name= "endereco", length = 200)
	private String endereco;
	
	@Column(name= "uf", length = 2)
	private String uf;
	
	@Column(name= "cidade", length = 50)
	private String cidade;
	
	@Column(name= "bairro", length = 50)
	private String bairro;
	
	public Cep() {		
	}

	public Cep(String numero, String endereco, String cidade, String uf, String bairro) {
		super();
		
		this.numero = numero;
		this.endereco = endereco;
		this.cidade = cidade;
		this.bairro = bairro;
		this.uf = uf;
	}
	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	
	
}
