package es.ubu.agenda.beans;

import javax.annotation.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import es.ubu.agenda.modelo.Usuario;
import es.ubu.agenda.persistencia.Fachada;


@ManagedBean
public class RegistroBean {

	private String nombre;
	private String contraseña;
	private String email;
	private boolean visible;
	
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void registrarUsuario() throws NamingException{
		Fachada fachada;
		Usuario usuario=new Usuario();
		usuario.setContrasena(contraseña);
		usuario.setNombre(nombre);
		usuario.setEmail(email);
		usuario.setTipo('N');
		fachada=Fachada.getInstance();
		fachada.insertarUsuario(usuario);
	}
	
}
