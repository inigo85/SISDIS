package es.ubu.agenda.beans;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.naming.NamingException;

import es.ubu.agenda.modelo.Usuario;
import es.ubu.agenda.persistencia.Fachada;


@ManagedBean
public class AdminUserBean {
	private List<Usuario> listaUsuarios;
	private Fachada fachada;
	
	public AdminUserBean() throws NamingException{
		fachada=Fachada.getInstance();
		listaUsuarios=fachada.obtenerListaUsuarios();
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	
	 
	
}
