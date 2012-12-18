package es.ubu.agenda.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.enterprise.context.ContextException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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

	public boolean login(String usuario, String contrasena) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		boolean ok = false;
		try {
			conn = ds.getConnection();
			String sql;
			sql = "SELECT * FROM usuario WHERE nombre='" + usuario
					+ "' AND contraseña='" + contrasena + "';";
			st = conn.prepareStatement(sql);
			rs = st.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			// que hacemos con la columna conectado??
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.close(conn, st, rs);
		}
		return ok;

	}

}
