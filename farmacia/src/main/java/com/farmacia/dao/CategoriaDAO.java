package com.farmacia.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.util.ArrayList;

import com.farmacia.entidad.Categoria;
import com.farmacia.interfaces.ICategoriaDAO;
import com.farmacia.util.MySqlConexion;

public class CategoriaDAO implements ICategoriaDAO {

	@Override
	public int registrarCategoria(Categoria c) {
		//DECLARAR VARIABLE PARA EL RESULTADO
		int r=-1;
		
		//DECLARAR UN OBJETO PARA LA CONEXIÓN
		Connection cone=null;
		
		//DECLARAR OBJETO PARA MANIPULAR EL PROCEDIMIENTO ALMACENADO
		CallableStatement cstm=null;
		
		try {
			//PASO 01 - INVOCAR LA CONEXIÓN
			cone=MySqlConexion.miConexion();
			
			//PASO 02 - PREPARAR CALLABLESTATEMENT
			cstm=cone.prepareCall("{CALL SP_REGISTRAR_CATEGORIA(null, ?)}");
			
			//PASO 03 - ENVIAR LOS DATOS A CSTM OBTENIDO DE LA MEMORIA RAM
			cstm.setString(1, c.getNom_cate());
	
			//COMPROBANDO LO Q TIENE CSTM
			System.out.println("==>"+cstm);
			
			//PASO 04 - EJECUTAMOS CSTM
			r=cstm.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				if(cone!=null)cone.close();
				if(cstm!=null)cstm.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return r;
	}

	@Override
	public int modificarCategoria(Categoria c) {
		//DECLARAR VARIABLE PARA EL RESULTADO
		int r=-1;
		
		//DECLARAR UN OBJETO PARA LA CONEXIÓN
		Connection cone=null;
		
		//DECLARAR OBJETO PARA MANIPULAR EL PROCEDIMIENTO ALMACENADO
		CallableStatement cstm=null;
		
		try {
			//PASO 01 - INVOCAR LA CONEXIÓN
			cone=MySqlConexion.miConexion();
			
			//PASO 02 - PREPARAR CALLABLESTATEMENT
			cstm=cone.prepareCall("{CALL SP_MODIFICAR_CATEGORIA(?, ?)}");
			
			//PASO 03 - ENVIAR LOS DATOS A CSTM OBTENIDO DE LA MEMORIA RAM
			cstm.setInt(1, c.getNum_cate());
			cstm.setString(2, c.getNom_cate());

			//COMPROBANDO LO Q TIENE CSTM
			System.out.println("==>"+cstm);
			
			//PASO 04 - EJECUTAMOS CSTM
			r=cstm.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				if(cone!=null)cone.close();
				if(cstm!=null)cstm.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

		return r;
	}

	@Override
	public int eliminarCategoria(int num_cate) {
		//DECLARAR VARIABLE PARA EL RESULTADO
		int r=-1;
		
		//DECLARAR UN OBJETO PARA LA CONEXIÓN
		Connection cone=null;
		
		//DECLARAR OBJETO PARA MANIPULAR EL PROCEDIMIENTO ALMACENADO
		CallableStatement cstm=null;
		
		try {
			//PASO 01 - INVOCAR LA CONEXIÓN
			cone=MySqlConexion.miConexion();
			
			//PASO 02 - PREPARAR CALLABLESTATEMENT
			cstm=cone.prepareCall("{CALL SP_ELIMINAR_CATEGORIA(?)}");
			
			//PASO 03 - ENVIAR LOS DATOS A CSTM OBTENIDO DE LA MEMORIA RAM
			cstm.setInt(1, num_cate);
			//COMPROBANDO LO Q TIENE CSTM
			System.out.println("==>"+cstm);
			
			//PASO 04 - EJECUTAMOS CSTM
			r=cstm.executeUpdate();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		finally {
			try {
				if(cone!=null)cone.close();
				if(cstm!=null)cstm.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		return r;
	}

	@Override
	public Categoria buscarCategoria(int num_cate) {
		Connection cone=null;
		CallableStatement cstm=null;
		ResultSet rs=null;
		Categoria cate=new Categoria();
		try {
			cone=MySqlConexion.miConexion();
			cstm=cone.prepareCall("{CALL SP_BUSCAR_CATEGORIA(?)}");
			cstm.setInt(1, num_cate);
			rs=cstm.executeQuery();
			while(rs.next()) {
				cate.setNom_cate(rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(cone!=null) cone.close();
				if(cstm!=null) cstm.close(); //PROECEDIMIENTOS ALMACENADOS
				if(rs!=null) rs.close(); //PARA REGISTROS
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return cate;
	}

	@Override
	public ArrayList<Categoria> listadoCategoria() {
		Connection cone=null;
		CallableStatement cstm=null;
		ResultSet rs=null;
		ArrayList<Categoria>listame=new ArrayList<Categoria>();
		
		try {
			//Invocar conexion
			cone=MySqlConexion.miConexion();
			//Preparar el cstm
			cstm=cone.prepareCall("{CALL SP_LISTAR_CATEGORIA()}");
			//Enviar lo q tiene cstm a rs
			rs=cstm.executeQuery();
			//Haciendo el recorrido
			while(rs.next()) {
				//Declarar un objeto basado a cliente
				Categoria cate=new Categoria();
				cate.setNum_cate((rs.getInt(1)));
				cate.setNom_cate(rs.getString(2));

				//Enviando cli a listame
				listame.add(cate);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		finally {
			try {
				if(cone!=null)cone.close();
				if(cstm!=null)cstm.close();
				if(rs!=null)rs.close();
				
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
		return listame;
	}

}
