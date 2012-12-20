package es.ubu.agenda.beans;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import es.ubu.agenda.persistencia.Fachada;
import es.ubu.agenda.modelo.Tarea;


public class TableBean {

	private Tarea tarea;
	private List<Tarea> listaTareas;
	private Fachada fachada;
	
	public TableBean() throws NamingException{
		fachada=Fachada.getInstance();
		listaTareas=fachada.obenerTareas(1, 23);
		
	}
	
	public List<Tarea> getListaTareas() {
		return listaTareas;
	}

	public void setListaTareas(List<Tarea> listaTareas) {
		this.listaTareas = listaTareas;
	}

	public void tareaSeleccionada(Tarea tarea) {
		this.tarea=tarea;
	}
}
