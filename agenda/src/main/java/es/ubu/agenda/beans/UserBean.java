package es.ubu.agenda.beans;

import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import es.ubu.agenda.modelo.Usuario;
import es.ubu.agenda.persistencia.Fachada;


/**
 * Bean implementation class ValidateBean.
 */
@ManagedBean
@SessionScoped
public class UserBean implements Serializable{

	/**
	 * Login user.
	 */
	private String login;
	
	
	/**
	 * Password user.
	 */
	private String password;
	
	private int id;
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	private HttpSession session = null;
	

	/**
	 * Empty constructor.
	 */
	public UserBean(){
		
	}

	
	/**
	 * Get the login user.
	 * 
	 * @return login login user.
	 */
	public String getLogin() {
		return login;
	}

	
	/**
	 * Set the login user.
	 * 
	 * @param login login user.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	
	/**
	 * Get the password user.
	 * 
	 * @return password password user.
	 */
	public String getPassword() {
		return password;
	}

	
	/**
	 * Set the password user.
	 * 
	 * @param password password user.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	public HttpSession getS() {
		return session;
	}


	public void setS(HttpSession session) {
		this.session = session;
	}


	public String obtainLogin() throws NamingException{
		Fachada fachada=Fachada.getInstance();
		Usuario usuario;
		usuario=fachada.login(login, password);
		if(usuario!=null){
		  setId(Integer.valueOf(usuario.getId()));
		  FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", getId());
		  if(usuario.getTipo()=='A' || usuario.getTipo()=='a')
		    return "admin";
		  else
			return "ok";
          
		}
		return "failed";
	}
	
	public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "logout";
    }
	
	
}
