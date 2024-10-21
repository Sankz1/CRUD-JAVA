package com.crud.Proyecto.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Coordenadas", schema = "PPOOII")
public class Coordenadas implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coordenada")
    public Long id;

    @Column(name = "persona")
    public Long persona;

    @Column(name = "marca")
    public String marca;

    @Column(name = "longitud")
    public double longitud;

    @Column(name = "latitud")
    public double latitud;

    public Coordenadas() {
    }

    public Coordenadas(Long persona, String marca, double longitud, double latitud) {
        this.persona = persona;
        this.marca = marca;
        this.longitud = longitud;
        this.latitud = latitud;
    }

    public Coordenadas(Long id, Long persona, String marca, double longitud, double latitud) {
        this.id = id;
        this.persona = persona;
        this.marca = marca;
        this.longitud = longitud;
        this.latitud = latitud;
    }
    // getters y setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPersona() {
		return persona;
	}

	public void setPersona(Long persona) {
		this.persona = persona;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
}
