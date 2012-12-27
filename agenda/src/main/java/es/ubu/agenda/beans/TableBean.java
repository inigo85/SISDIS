package es.ubu.agenda.beans;

import java.util.ArrayList;
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
public class TableBean {

	private Tarea tareaSeleccionada;
	
	public Tarea getTareaSeleccionada() {
		return tareaSeleccionada;
	}

	public void setTareaSeleccionada(Tarea tareaSeleccionada) {
		this.tareaSeleccionada = tareaSeleccionada;
		ExternalContext tmpEC;
	    Map sMap;
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
	private Fachada fachada;
	
	public TableBean() throws NamingException{
		fachada=Fachada.getInstance();
		actualizarLista();
		
	}

	public void actualizarLista() throws NamingException {
		listaTareas=fachada.obenerTareas(Fachada.getInstance().obtenerIdUsuario());
	}
	
	public List<Tarea> getListaTareas() {
		return listaTareas;
	}

	public void setListaTareas(List<Tarea> listaTareas) {
		this.listaTareas = listaTareas;
	}


}
