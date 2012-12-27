package es.ubu.agenda.beans;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.naming.NamingException;

import es.ubu.agenda.modelo.Tarea;
import es.ubu.agenda.persistencia.Fachada;

@ManagedBean
public class AdminEventoBean {
	private List<Tarea> listaTareas;
	private Fachada fachada;
	
	public AdminEventoBean() throws NamingException{
		fachada=Fachada.getInstance();
		listaTareas=fachada.obenerTareas();
		
	}

	public List<Tarea> getListaTareas() {
		return listaTareas;
	}

	public void setListaTareas(List<Tarea> listaTareas) {
		this.listaTareas = listaTareas;
	}
	
	
	
	
}
