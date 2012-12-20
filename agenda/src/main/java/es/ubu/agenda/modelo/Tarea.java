package es.ubu.agenda.modelo;

import java.util.Date;

public class Tarea {

	private String descripción;
	private String nombre;
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private Date fecha_inicio;
	private Date fecha_fin;
	private boolean todo_el_día;
	private String lugar;

	public String getDescripción() {
		return descripción;
	}

	public void setDescripción(String descripción) {
		this.descripción = descripción;
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

	public boolean isTodo_el_día() {
		return todo_el_día;
	}

	public void setTodo_el_día(boolean todo_el_día) {
		this.todo_el_día = todo_el_día;
	}


}
