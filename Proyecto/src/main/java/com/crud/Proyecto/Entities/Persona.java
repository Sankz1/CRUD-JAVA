			package com.crud.Proyecto.Entities;
import java.time.LocalDate;
import java.time.Period;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Persona {

    @Id	
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

    @Column(nullable = false)
    private int identificacion;

    @Column(nullable = false)
    private String pnombre;

    private String snombre;
    @Column(nullable = false)
    private String papellido;

    private String sapellido;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate fechanacimiento;

    @Column(nullable = false)
    private int edad;

    @Column(nullable = false)
    private String edadclinica;
    
    @Column (nullable = false)
    private String ubicacion;
    
    @JsonIgnore
    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL)
    private Usuario usuario;

    // Métodos para calcular la edad y la edad clínica
    public void calcularEdad() {
        this.edad = Period.between(this.fechanacimiento, LocalDate.now()).getYears();
    }

    public void calcularEdadClinica() {
        Period period = Period.between(this.fechanacimiento, LocalDate.now());
        this.edadclinica = period.getYears() + " años " + period.getMonths() + " meses " + period.getDays() + " días";
    }

    
    // Getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(int identificacion) {
		this.identificacion = identificacion;
	}

	public String getPnombre() {
		return pnombre;
	}

	public void setPnombre(String pnombre) {
		this.pnombre = pnombre;
	}

	public String getSnombre() {
		return snombre;
	}

	public void setSnombre(String snombre) {
		this.snombre = snombre;
	}

	public String getPapellido() {
		return papellido;
	}

	public void setPapellido(String papellido) {
		this.papellido = papellido;
	}

	public String getSapellido() {
		return sapellido;
	}

	public void setSapellido(String sapellido) {
		this.sapellido = sapellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getFechanacimiento() {
		return fechanacimiento;
	}

	public void setFechanacimiento(LocalDate fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getEdadclinica() {
		return edadclinica;
	}

	public void setEdadclinica(String edadclinica) {
		this.edadclinica = edadclinica;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}


   
}
