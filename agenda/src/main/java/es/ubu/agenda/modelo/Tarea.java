package es.ubu.agenda.modelo;

import java.util.Date;

public class Tarea {

	private String descripci�n;
	private String nombre;
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private Date fecha_inicio;
	private Date fecha_fin;
	private boolean todo_el_d�a;
	private String lugar;

	public String getDescripci�n() {
		return descripci�n;
	}

	public void setDescripci�n(String descripci�n) {
		this.descripci�n = descripci�n;
	}

	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public boolean isTodo_el_d�a() {
		return todo_el_d�a;
	}

	public void setTodo_el_d�a(boolean todo_el_d�a) {
		this.todo_el_d�a = todo_el_d�a;
	}


}
