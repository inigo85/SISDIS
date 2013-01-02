package es.ubu.agenda.beans;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import org.primefaces.context.RequestContext;

import es.ubu.agenda.modelo.Usuario;
import es.ubu.agenda.persistencia.Fachada;


@ManagedBean
public class RegistroBean implements Serializable{

	private String nombre;
	private String contrase�a;
	private String email;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getContrase�a() {
		return contrase�a;
	}
	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String registrarUsuario() throws NamingException{
		Fachada fachada;
		Usuario usuario=new Usuario();
		usuario.setContrasena(contrase�a);
		usuario.setNombre(nombre);
		usuario.setEmail(email);
		usuario.setTipo('N');
		fachada=Fachada.getInstance();
		fachada.insertarUsuario(usuario);
		mostrarMensaje();
		return "loginPage";
	}
	
	
	private void mostrarMensaje() {
		ExternalContext tmpEC;
	    Map sMap;
	    tmpEC = FacesContext.getCurrentInstance().getExternalContext();
	    sMap = tmpEC.getSessionMap();
	    UserBean user = (UserBean) sMap.get("UserBean");
	    user.mostrarMensaje();
	}
	
}
