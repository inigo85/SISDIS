package es.ubu.agenda.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import es.ubu.agenda.modelo.Usuario;

import javax.servlet.http.HttpServletRequest;

public class Fachada {

	private static Fachada DBInstancia;
	private DataSource ds;

	private Fachada() throws NamingException {
		Context contextoInicial = new InitialContext();
		ds = (DataSource) contextoInicial.lookup("java:/comp/env/jdbc/sisdis");
	}

	public static Fachada getInstance() throws NamingException {
		if (DBInstancia == null)
			DBInstancia = new Fachada();
		return DBInstancia;
	}

	public Usuario login(String usuario, String contrasena) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		Usuario usu=null;
		try {
			conn = ds.getConnection();
			String sql;			
			sql = "SELECT * FROM usuario WHERE nombre='" + usuario
					+ "' AND contraseña='" + contrasena + "';";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()){
				usu=new Usuario();
				usu.setId(rs.getString("id"));
				usu.setTipo(rs.getString("tipo").charAt(0));
				usu.setNombre(rs.getString("nombre"));
				usu.setEmail(rs.getString("email"));
				usu.setContrasena(rs.getString("contraseña"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st, rs);
		}
		return usu;

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
	
	public List<Usuario> obtenerListaUsuarios(){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		List<Usuario> listaUsuarios = null;
		try {
			conn = ds.getConnection();
			listaUsuarios=new ArrayList<Usuario>();
			String sql;
			sql = "SELECT * FROM usuario";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			while (rs.next()){
				Usuario usu=new Usuario();
				usu.setId(rs.getString("id"));
				usu.setEmail(rs.getString("email"));
				usu.setNombre(rs.getString("nombre"));
				usu.setTipo(rs.getString("tipo").charAt(0));
				usu.setContrasena(rs.getString("contraseña"));
				listaUsuarios.add(usu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st, rs);
		}
		return listaUsuarios;
	}
	
	
	public List<Tarea> obenerTareas(int idUsuario, boolean próximas){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		List<Tarea> listaTareas = null;
		String operador=">=";
		boolean ok = false;
		try {
			conn = ds.getConnection();
			listaTareas=new ArrayList<Tarea>();
			String sql;
			if(!próximas)
				operador="<";
			sql = "SELECT * FROM tarea WHERE idUsuario="+idUsuario+" AND fecha_inicio"+operador+"'"+new Timestamp(new Date().getTime())+"' ORDER BY fecha_inicio";
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
	
	public List<Tarea> obenerTareas(){
		Connection conn = null;
		ResultSet rs = null, rs2=null;
		PreparedStatement st = null;
		List<Tarea> listaTareas = null;
		boolean ok = false;
		try {
			conn = ds.getConnection();
			listaTareas=new ArrayList<Tarea>();
			String sql;
			sql = "SELECT * FROM tarea";
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
				sql="SELECT nombre from usuario where id="+rs.getString("idUsuario")+";";
				st = conn.prepareStatement(sql);
				rs2=st.executeQuery();
				if(rs2.next())
					tarea.setNombreUsuario(rs2.getString("nombre"));
				listaTareas.add(tarea);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st, rs);
		}
		return listaTareas;
	}
	
	
	public String obtenerIDUsuarioPorNombre(String nombre){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		String id="";
		try {
			conn = ds.getConnection();
			String sql;
			sql = "SELECT id FROM usuario WHERE nombre='"+nombre+"';";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next()){
				id=rs.getString("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st, rs);
		}
		return id;
	}
	
	
	
	public void actualizarTarea(Tarea tarea, Tarea tarea2){
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ds.getConnection();
			String sql;
			sql = "UPDATE tarea SET nombre='"+tarea.getNombre()+"', descripcion='"+tarea.getDescripción()+"', " +
					"fecha_inicio='"+tarea.obtenerFechaFormateadaInicio()+"', fecha_fin='"+tarea.obtenerFechaFormateadaFin()+"', " +
							"todo_el_dia="+tarea2.isTodo_el_día()+" WHERE nombre='"+tarea2.getNombre()+"' AND fecha_inicio='"+tarea2.obtenerFechaFormateadaInicio()+"' " +
									"AND fecha_fin='"+tarea2.obtenerFechaFormateadaFin()+"';";
			st = conn.prepareStatement(sql);
			st.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st);
		}
	}
	
	public void actualizarUsuario(Usuario usuario){
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ds.getConnection();
			String sql;
			sql = "UPDATE usuario SET nombre='"+usuario.getNombre()+"', email='"+usuario.getEmail()+"', " +
					"tipo='"+usuario.getTipo()+"', contraseña='"+usuario.getContrasena()+"'" +
							" WHERE id="+usuario.getId()+";";
			st = conn.prepareStatement(sql);
			st.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st);
		}
	}
	
	public void actualizarTarea(Tarea tarea, String id){
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ds.getConnection();
			String sql;
			sql = "UPDATE tarea SET nombre='"+tarea.getNombre()+"', descripcion='"+tarea.getDescripción()+"', " +
					"fecha_inicio='"+tarea.obtenerFechaFormateadaInicio()+"', fecha_fin='"+tarea.obtenerFechaFormateadaFin()+"', " +
							"todo_el_dia="+tarea.isTodo_el_día()+" WHERE id="+id+";";
			st = conn.prepareStatement(sql);
			st.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st);
		}
	}
	
	
	public String insertarTarea(Tarea tarea){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs=null;
		int id=-1;
		try {
			conn = ds.getConnection();
			String sql;
			sql = "INSERT INTO tarea(nombre,descripcion,fecha_inicio,fecha_fin,todo_el_dia,idUsuario) VALUES('"+tarea.getNombre()+"','"+tarea.getDescripción()+"','"+
					tarea.obtenerFechaFormateadaInicio()+"','"+tarea.obtenerFechaFormateadaFin()+"',"+tarea.isTodo_el_día()+","+tarea.getIdUsuario()+");";
			st = conn.prepareStatement(sql);
			st.execute(sql);
			sql="SELECT Auto_increment FROM information_schema.tables WHERE table_name='tarea';";
			rs=st.executeQuery(sql);
			if(rs.next()){
				id=rs.getInt("Auto_increment");
				id=id-1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st);
		}
		return String.valueOf(id);
	}
	
	public String insertarUsuario(Usuario usuario){
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs=null;
		int id=-1;
		try {
			conn = ds.getConnection();
			String sql;
			sql = "INSERT INTO usuario(nombre,email,tipo,contraseña) VALUES('"+usuario.getNombre()+"','"+usuario.getEmail()+"','"+
					usuario.getTipo()+"','"+usuario.getContrasena()+"');";
			st = conn.prepareStatement(sql);
			st.execute(sql);
			sql="SELECT Auto_increment FROM information_schema.tables WHERE table_name='usuario';";
			rs=st.executeQuery(sql);
			if(rs.next()){
				id=rs.getInt("Auto_increment");
				id=id-1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st);
		}
		return String.valueOf(id);
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
	
	public void eliminarEvento(String id){
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ds.getConnection();
			String sql;
			sql = "DELETE FROM tarea WHERE id="+id+";";
			st = conn.prepareStatement(sql);
			st.execute(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st);
		}
	}
	
	public String obtenerDescripcion(String id){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		String descripcion="";
		try {
			conn = ds.getConnection();
			String sql;
			sql = "SELECT descripcion FROM tarea WHERE id="+id+";";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if(rs.next())
				descripcion=rs.getString("descripcion");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st, rs);
		}
		return descripcion;
	}
	
	public void eliminarUsuario(String id){
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = ds.getConnection();
			String sql;
			sql = "DELETE FROM usuario WHERE id="+id+";";
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
