package com.crud.Proyecto.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    private Long idpersona;

    @OneToOne
    @MapsId
    @JoinColumn(name = "idpersona")
    @JsonIgnore
    private Persona persona;

    @Column(nullable = false, unique = true)
    private String login;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String apikey;

    // Método para generar el login
    public void generarLogin() {
        this.login = this.persona.getPnombre() + this.persona.getPapellido().charAt(0) + this.persona.getId();
    }

    // Método para generar el password
    public void generarPassword() {
        this.password = java.util.UUID.randomUUID().toString();
    }
    public void generarApiKey() {
    	this.apikey= java.util.UUID.randomUUID().toString();
    }
    //Getters y setters
	public Long getIdpersona() {
		return idpersona;
	}

	public void setIdpersona(Long idpersona) {
		this.idpersona = idpersona;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

}
