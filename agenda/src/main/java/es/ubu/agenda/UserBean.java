package es.ubu.agenda;

import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;


/**
 * Bean implementation class ValidateBean.
 */
@ManagedBean
public class UserBean {

	/**
	 * Login user.
	 */
	private String login;
	
	
	/**
	 * Password user.
	 */
	private String password;
	
	
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


	
	public String obtainLogin(){
		System.out.print(getLogin()+getPassword());
		return "";
	}
	
}
