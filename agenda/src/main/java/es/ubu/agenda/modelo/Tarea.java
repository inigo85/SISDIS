package es.ubu.agenda.modelo;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.NamingException;

import es.ubu.agenda.persistencia.Fachada;

public class Tarea {

	private String id;
	private String nombreUsuario;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
	
	private String fecha_formateada;
	
	private String fecha_formateada_fin;
	
	private String idUsuario;

	public String getFecha_formateada() {
		return fecha_formateada;
	}

	public void setFecha_formateada(String fecha_formateada) {
		this.fecha_formateada = fecha_formateada;
	}

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
		DateFormat df= new SimpleDateFormat("dd/MM/yy HH:mm");
		setFecha_formateada(df.format(fecha_inicio));
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
		DateFormat df= new SimpleDateFormat("dd/MM/yy HH:mm");
		setFecha_formateada_fin(df.format(fecha_fin));
	}


	public boolean isTodo_el_día() {
		return todo_el_día;
	}

	public void setTodo_el_día(boolean todo_el_día) {
		this.todo_el_día = todo_el_día;
	}

	public Timestamp obtenerFechaFormateadaInicio(){
		return new Timestamp(fecha_inicio.getTime());
	}


    public Timestamp obtenerFechaFormateadaFin(){
    	return new Timestamp(fecha_fin.getTime());
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public void rellenarIDUsuario() throws NamingException {
		Fachada fachada;
		fachada=Fachada.getInstance();
		setIdUsuario(fachada.obtenerIDUsuarioPorNombre(nombreUsuario));
	}

	public String getFecha_formateada_fin() {
		return fecha_formateada_fin;
	}

	public void setFecha_formateada_fin(String fecha_formateada_fin) {
		this.fecha_formateada_fin = fecha_formateada_fin;
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	
	
    
    
}
