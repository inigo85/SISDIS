package es.ubu.agenda.beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import es.ubu.agenda.persistencia.Fachada;
import es.ubu.agenda.modelo.Tarea;

@ManagedBean
public class TableBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8997868904134418756L;
	private Tarea tareaSeleccionada;
	
	public Tarea getTareaSeleccionada() {
		return tareaSeleccionada;
	}

	public void setTareaSeleccionada(Tarea tareaSeleccionada) {
		this.tareaSeleccionada = tareaSeleccionada;
		ExternalContext tmpEC;
	    Map<?, ?> sMap;
	    tmpEC = FacesContext.getCurrentInstance().getExternalContext();
	    sMap = tmpEC.getSessionMap();
	    ScheduleController schedule = (ScheduleController) sMap.get("scheduleController");
	    schedule.setVistaCalendario("agendaDay");
	    schedule.setFechaSeleccionada(tareaSeleccionada.getFecha_inicio());
	    Calendar calendar = GregorianCalendar.getInstance(); 
	    calendar.setTime(tareaSeleccionada.getFecha_inicio());
	    schedule.setHora(calendar.get(Calendar.HOUR_OF_DAY) );
	}

	private List<Tarea> listaTareas;
	private List<Tarea> listaTareasAntiguas;
	private Fachada fachada;
	
	public TableBean() throws NamingException{
		fachada=Fachada.getInstance();
		actualizarLista();
		actualizarListaAntiguas();
		
	}

	public void actualizarLista() throws NamingException {
		listaTareas=fachada.obenerTareas(Fachada.getInstance().obtenerIdUsuario(),true);
	}
	
	public void actualizarListaAntiguas() throws NamingException {
		listaTareasAntiguas=fachada.obenerTareas(Fachada.getInstance().obtenerIdUsuario(),false);
	}
	
	public List<Tarea> getListaTareas() {
		return listaTareas;
	}

	public void setListaTareas(List<Tarea> listaTareas) {
		this.listaTareas = listaTareas;
	}

	public List<Tarea> getListaTareasAntiguas() {
		return listaTareasAntiguas;
	}

	public void setListaTareasAntiguas(List<Tarea> listaTareasAntiguas) {
		this.listaTareasAntiguas = listaTareasAntiguas;
	}

	

}
