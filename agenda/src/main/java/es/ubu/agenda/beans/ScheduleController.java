package es.ubu.agenda.beans;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.NamingException;

import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import es.ubu.agenda.modelo.Tarea;
import es.ubu.agenda.persistencia.Fachada;

@ManagedBean
public class ScheduleController implements Serializable{

	private ScheduleModel eventModel;
	
	private String vistaCalendario;
	
	private Integer hora;

	public Integer getHora() {
		return hora;
	}

	public void setHora(Integer hora) {
		this.hora = hora;
	}

	private Date fechaSeleccionada;
	
	public Date getFechaSeleccionada() {
		return fechaSeleccionada;
	}

	public void setFechaSeleccionada(Date fechaSeleccionada) {
		this.fechaSeleccionada = fechaSeleccionada;
	}

	public String getVistaCalendario() {
		return vistaCalendario;
	}

	public void setVistaCalendario(String vistaCalendario) {
		this.vistaCalendario = vistaCalendario;
	}

	private Fachada fachada;
	
	private Tarea tareaAntigua;
	
	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	private ScheduleModel lazyEventModel;

	private ScheduleEvent event = new DefaultScheduleEvent();
	
	private ScheduleEvent oldEvent = new DefaultScheduleEvent();
	
	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	private String theme;
	
	private String descripción;

	
	public String getDescripción() {
		return descripción;
	}

	public void setDescripción(String descripción) {
		this.descripción = descripción;
	}

	public ScheduleController() throws NamingException {
		eventModel = new DefaultScheduleModel() ;
		fachada=Fachada.getInstance();
		List<Tarea> lista=fachada.obenerTareas(fachada.obtenerIdUsuario());
		this.setVistaCalendario("month");
		this.setFechaSeleccionada(new Date());
		this.setHora(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		for(Tarea tarea:lista){
			DefaultScheduleEvent de=new DefaultScheduleEvent(tarea.getNombre(), tarea.getFecha_inicio(), tarea.getFecha_fin());		
			eventModel.addEvent(de);
			de.setId(tarea.getId());
			de.setAllDay(tarea.isTodo_el_día());
		}

		
		lazyEventModel = new LazyScheduleModel() {
			
			public void fetchEvents(Date start, Date end) {
				clear();
			}	
		};
	}
	
	public void addEvent(ActionEvent actionEvent) throws NamingException {
		if(event.getId() == null){
			String eventoId;
			eventModel.addEvent(event);
			fachada=Fachada.getInstance();
			Tarea t = obtenerTarea(event);
			t.setIdUsuario(String.valueOf(fachada.obtenerIdUsuario()));
			eventoId=fachada.insertarTarea(t);
			event.setId(eventoId);
			actualizarListaTareas();
		}
		else
		{
			eventModel.updateEvent(event);
			fachada=Fachada.getInstance();
			Tarea t = obtenerTarea(event);
			//Tarea t2 = obtenerTarea(oldEvent);
			fachada.actualizarTarea(t, event.getId());
			actualizarListaTareas();
		}
		event = new DefaultScheduleEvent();
		this.setDescripción("");
	}

	private void actualizarListaTareas() throws NamingException {
		ExternalContext tmpEC;
		Map sMap;
		tmpEC = FacesContext.getCurrentInstance().getExternalContext();
		sMap = tmpEC.getSessionMap();
		TableBean tb = (TableBean) sMap.get("tableBean");
		tb.actualizarLista();
	}
	
	public void deleteEvent(ActionEvent actionEvent) throws NamingException{
		fachada=Fachada.getInstance();
		fachada.eliminarEvento(event.getId());
		eventModel.deleteEvent(event);
		this.actualizarListaTareas();
		
	}

	private Tarea obtenerTarea(ScheduleEvent event) {
		Tarea t=new Tarea();
		t.setNombre(event.getTitle());
		t.setFecha_inicio(event.getStartDate());
		t.setFecha_fin(event.getEndDate());
		t.setDescripción(getDescripción());
		t.setTodo_el_día(event.isAllDay());
		return t;
	}
	
	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		descripción="";
		event = selectEvent.getScheduleEvent();
		tareaAntigua = obtenerTarea(event);
		descripción=fachada.obtenerDescripcion(event.getId());
	}
	
	public void onDateSelect(DateSelectEvent selectEvent) {
		descripción="";
		event = new DefaultScheduleEvent("", selectEvent.getDate(), selectEvent.getDate());
	}
	
	public void onEventMove(ScheduleEntryMoveEvent event) throws NamingException {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento movido", "Días:" + event.getDayDelta() + ", Minutos:" + event.getMinuteDelta());
		addMessage(message);
		this.event=event.getScheduleEvent();
		this.addEvent(null);
	}
	
	public void onEventResize(ScheduleEntryResizeEvent event) throws NamingException {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento redimensionado", "Días:" + event.getDayDelta() + ", Minutos:" + event.getMinuteDelta());
		addMessage(message);
		this.event=event.getScheduleEvent();
		this.addEvent(null);
	}
	
	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	

	//Getters and Setters
}

