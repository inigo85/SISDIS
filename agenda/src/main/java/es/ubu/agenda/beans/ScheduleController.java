package es.ubu.agenda.beans;



import java.io.Serializable;
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

import org.primefaces.context.RequestContext;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;

import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import es.ubu.agenda.modelo.Tarea;
import es.ubu.agenda.persistencia.Fachada;

@ManagedBean
public class ScheduleController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5133150658694289644L;


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
	
	
	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	

	private ScheduleEvent event = new DefaultScheduleEvent();
	
	
	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	
	private String descripci�n;

	
	public String getDescripci�n() {
		return descripci�n;
	}

	public void setDescripci�n(String descripci�n) {
		this.descripci�n = descripci�n;
	}


	public ScheduleController() throws NamingException {
		eventModel = new DefaultScheduleModel() ;
		fachada=Fachada.getInstance();
		List<Tarea> lista=fachada.obenerTareas(fachada.obtenerIdUsuario(),true);
		lista.addAll(fachada.obenerTareas(fachada.obtenerIdUsuario(),false));
		this.setVistaCalendario("month");
		this.setFechaSeleccionada(new Date());
		this.setHora(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		for(Tarea tarea:lista){
			DefaultScheduleEvent de=new DefaultScheduleEvent(tarea.getNombre(), tarea.getFecha_inicio(), tarea.getFecha_fin());		
			eventModel.addEvent(de);
			de.setId(tarea.getId());
			de.setAllDay(tarea.isTodo_el_d�a());
		}

		
	}
	
	public void addEvent(ActionEvent actionEvent) throws NamingException {
		if(event.getStartDate().compareTo(event.getEndDate())>0){
			FacesMessage msg=new FacesMessage(FacesMessage.SEVERITY_ERROR, "Desde es posterior a Hasta", null);
		    FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
		if(event.getId() == null){
			String eventoId;
			eventModel.addEvent(event);
			fachada=Fachada.getInstance();
			Tarea t = obtenerTarea(event);
			t.setIdUsuario(String.valueOf(fachada.obtenerIdUsuario()));
			eventoId=fachada.insertarTarea(t);
			event.setId(eventoId);	
		}
		else
		{
			eventModel.updateEvent(event);
			fachada=Fachada.getInstance();
			Tarea t = obtenerTarea(event);
			fachada.actualizarTarea(t, event.getId());
		}
		actualizarListaTareas();
		RequestContext.getCurrentInstance().execute("eventDialog.hide();myschedule.update();");
		//oncomplete="myschedule.update();"
		event = new DefaultScheduleEvent();
		this.setDescripci�n("");
	}

	private void actualizarListaTareas() throws NamingException {
		ExternalContext tmpEC;
		Map<?, ?> sMap;
		tmpEC = FacesContext.getCurrentInstance().getExternalContext();
		sMap = tmpEC.getSessionMap();
		TableBean tb = (TableBean) sMap.get("tableBean");
		tb.actualizarLista();
		tb.actualizarListaAntiguas();
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
		t.setDescripci�n(getDescripci�n());
		t.setTodo_el_d�a(event.isAllDay());
		return t;
	}
	
	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		descripci�n="";
		event = selectEvent.getScheduleEvent();
		descripci�n=fachada.obtenerDescripcion(event.getId());
	}
	
	public void onDateSelect(DateSelectEvent selectEvent) {
		descripci�n="";
		event = new DefaultScheduleEvent("", selectEvent.getDate(), selectEvent.getDate());
	}
	
	public void onEventMove(ScheduleEntryMoveEvent event) throws NamingException {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento movido", "D�as:" + event.getDayDelta() + ", Minutos:" + event.getMinuteDelta());
		addMessage(message);
		this.event=event.getScheduleEvent();
		this.addEvent(null);
	}
	
	public void onEventResize(ScheduleEntryResizeEvent event) throws NamingException {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Evento redimensionado", "D�as:" + event.getDayDelta() + ", Minutos:" + event.getMinuteDelta());
		addMessage(message);
		this.event=event.getScheduleEvent();
		this.addEvent(null);
	}
	
	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	

	//Getters and Setters
}

