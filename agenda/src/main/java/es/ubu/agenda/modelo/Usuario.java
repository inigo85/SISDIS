package es.ubu.agenda.modelo;

/* Comentario */
public class Usuario {
	
	private String id;
	private String nombre;
	private String email;
	private char tipo;
	private String contrasena;
	private boolean esAdmin;
	
	
	
	public boolean isEsAdmin() {
		return esAdmin;
	}
	public void setEsAdmin(boolean esAdmin) {
		this.esAdmin = esAdmin;
		if(esAdmin)
			setTipo('A');
		else
			setTipo('N');
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public char getTipo() {
		return tipo;
	}
	public void setTipo(char tipo) {
		this.tipo = tipo;
		if(tipo=='A' || tipo=='a')
			esAdmin=true;
		else
			esAdmin=false;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString(){
		return nombre;	
	}

}
