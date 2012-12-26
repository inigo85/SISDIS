package es.ubu.agenda.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ContextException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import es.ubu.agenda.beans.UserBean;
import es.ubu.agenda.modelo.Tarea;
import javax.servlet.http.HttpServletRequest;

public class Fachada {

	private static Fachada DBInstancia;
	private DataSource ds;

	private Fachada() throws NamingException {
		Context contextoInicial = new InitialContext();
		ds = (DataSource) contextoInicial.lookup("jdbc/sisdis");
	}

	public static Fachada getInstance() throws NamingException {
		if (DBInstancia == null)
			DBInstancia = new Fachada();
		return DBInstancia;
	}

	public int login(String usuario, String contrasena) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		int id = -1;
		try {
			conn = ds.getConnection();
			String sql;
			sql = "SELECT * FROM usuario WHERE nombre='" + usuario
					+ "' AND contraseña='" + contrasena + "';";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next())
				id = rs.getInt("id");
			// que hacemos con la columna conectado??
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st, rs);
		}
		return id;

	}
	
	public List<Tarea> obenerTareas(int idUsuario, int días){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		List<Tarea> listaTareas = null;
		boolean ok = false;
		try {
			conn = ds.getConnection();
			listaTareas=new ArrayList<Tarea>();
			String sql;
			sql = "SELECT * FROM tarea WHERE idUsuario="+idUsuario+" ORDER BY fecha_inicio";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()){
				Tarea tarea=new Tarea();
				tarea.setId(rs.getString("id"));
				tarea.setDescripción(rs.getString("descripcion"));
				tarea.setNombre(rs.getString("nombre"));
				tarea.setFecha_inicio(rs.getDate("fecha_inicio"));
				tarea.setFecha_fin(rs.getDate("fecha_fin"));
				tarea.setTodo_el_día(rs.getBoolean("todo_el_dia"));
				listaTareas.add(tarea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st, rs);
		}
		return listaTareas;
	}
	
	public List<Tarea> obenerTareas(int idUsuario){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		List<Tarea> listaTareas = null;
		boolean ok = false;
		try {
			conn = ds.getConnection();
			listaTareas=new ArrayList<Tarea>();
			String sql;
			sql = "SELECT * FROM tarea WHERE idUsuario="+idUsuario+" ORDER BY fecha_inicio";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()){
				Tarea tarea=new Tarea();
				tarea.setId(rs.getString("id"));
				tarea.setDescripción(rs.getString("descripcion"));
				tarea.setNombre(rs.getString("nombre"));
				tarea.setFecha_inicio(rs.getTimestamp("fecha_inicio"));
				tarea.setFecha_fin(rs.getTimestamp("fecha_fin"));
				tarea.setTodo_el_día(rs.getBoolean("todo_el_dia"));
				listaTareas.add(tarea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st, rs);
		}
		return listaTareas;
	}
	
	public void actualizarTarea(Tarea tarea, Tarea tarea2){
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ds.getConnection();
			String sql;
			sql = "UPDATE tarea SET nombre='"+tarea.getNombre()+"', descripcion='"+tarea.getDescripción()+"', " +
					"fecha_inicio='"+tarea.obtenerFechaFormateadaInicio()+"', fecha_fin='"+tarea.obtenerFechaFormateadaFin()+"', " +
							"todo_el_dia=1 WHERE nombre='"+tarea2.getNombre()+"' AND fecha_inicio='"+tarea2.obtenerFechaFormateadaInicio()+"' " +
									"AND fecha_fin='"+tarea2.obtenerFechaFormateadaFin()+"';";
			st = conn.prepareStatement(sql);
			st.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st);
		}
	}
	
	
	public int insertarTarea(Tarea tarea){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs=null;
		boolean ok = false;
		int idUsuario;
		int id=-1;
		try {
			conn = ds.getConnection();
			String sql;
			idUsuario=obtenerIdUsuario();
			sql = "INSERT INTO tarea(nombre,descripcion,fecha_inicio,fecha_fin,todo_el_dia,idUsuario) VALUES('"+tarea.getNombre()+"','"+tarea.getDescripción()+"','"+
					tarea.obtenerFechaFormateadaInicio()+"','"+tarea.obtenerFechaFormateadaFin()+"',1,"+idUsuario+");";
			st = conn.prepareStatement(sql);
			ok=st.execute(sql);
			sql="SELECT Auto_increment FROM information_schema.tables WHERE table_name='tarea';";
			rs=st.executeQuery(sql);
			if(rs.next())
				id=rs.getInt("Auto_increment");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st);
		}
		return id-1;
	}
	
	public void eliminarEvento(Tarea tarea){
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ds.getConnection();
			String sql;
			sql = "DELETE FROM tarea WHERE nombre='"+tarea.getNombre()+"' AND fecha_inicio='"+tarea.obtenerFechaFormateadaInicio()+"' " +
									"AND fecha_fin='"+tarea.obtenerFechaFormateadaFin()+"';";
			st = conn.prepareStatement(sql);
			st.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st);
		}
	}
	
	
	public int obtenerIdUsuario(){
		ExternalContext tmpEC;
	    Map sMap;
	    tmpEC = FacesContext.getCurrentInstance().getExternalContext();
	    sMap = tmpEC.getSessionMap();
	    UserBean user = (UserBean) sMap.get("UserBean");
	    return user.getId();
	}

}
