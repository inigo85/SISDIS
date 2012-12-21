package es.ubu.agenda.beans;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
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

public class ScheduleController {

	private ScheduleModel eventModel;
	
	private Fachada fachada;
	
	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	private ScheduleModel lazyEventModel;

	private ScheduleEvent event = new DefaultScheduleEvent();
	
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
		for(Tarea tarea:lista){
			eventModel.addEvent(new DefaultScheduleEvent(tarea.getNombre(), tarea.getFecha_inicio(), tarea.getFecha_fin()));
		}

		
		lazyEventModel = new LazyScheduleModel() {
			
			public void fetchEvents(Date start, Date end) {
				clear();
				
//				Date random = getRandomDate(start);
//				addEvent(new DefaultScheduleEvent("Lazy Event 1", random, random));
//				
//				random = getRandomDate(start);
//				addEvent(new DefaultScheduleEvent("Lazy Event 2", random, random));
			}	
		};
	}
	
	public void addEvent(ActionEvent actionEvent) throws NamingException {
		if(event.getId() == null){
			eventModel.addEvent(event);
			fachada=Fachada.getInstance();
			Tarea t=new Tarea();
			t.setNombre(event.getTitle());
			t.setFecha_inicio(event.getStartDate());
			t.setFecha_fin(event.getEndDate());
			t.setDescripción(getDescripción());
			t.setTodo_el_día(event.isAllDay());
			fachada.insertarTarea(t);
		}
		else
		{
			eventModel.updateEvent(event);
		}
		event = new DefaultScheduleEvent();
	}
	
	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
		event = selectEvent.getScheduleEvent();
	}
	
	public void onDateSelect(DateSelectEvent selectEvent) {
		event = new DefaultScheduleEvent(Math.random() + "", selectEvent.getDate(), selectEvent.getDate());
	}
	
	public void onEventMove(ScheduleEntryMoveEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
		
		addMessage(message);
	}
	
	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
		
		addMessage(message);
	}
	
	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	//Getters and Setters
}

