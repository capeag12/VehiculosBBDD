package com.mycompany.vehiculosbbdd;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;






public class DAOVehiculoImpl  {
	
	private List<Vehiculo> falsaBD;
	private static DAOVehiculoImpl dao=null; 
        private Conexion conexion;

	private DAOVehiculoImpl() throws SQLException, NullPointerException {
		super();
		this.falsaBD = new ArrayList<Vehiculo>();
		conexion = new Conexion("vehiculos", "root", "");
                conexion.conectar();
                
                ResultSet resultados = conexion.realizarConsulta("SELECT * FROM `vehiculo`");
                
                while (resultados.next()) {
                this.falsaBD.add(new Vehiculo(resultados.getString(1),resultados.getString(2),resultados.getString(3)));
                
            }
                
		
	}

	
	public boolean insertarVehiculo(Vehiculo vehiculo){
	
        
            String consulta = "INSERT INTO `vehiculo` (`Marca`, `Modelo`, `Matricula`) VALUES ('"+vehiculo.getMarca()+"', '"+vehiculo.getModelo()+"', '"+vehiculo.getMatricula()+"');";
            System.out.println(consulta);
           
        return conexion.realizarInsercionEliminacion(consulta);    
	}
        
        public void cerrarConexion() throws SQLException{
            
            try {
                conexion.desconectar();
            } catch (SQLException e) {
                System.out.println("Algo ha fallado al desconectar");
            }
        }

	

	
	public Vehiculo eliminarVehiculo(String matricula) {
            
            Vehiculo v = buscarMatricula(matricula);
            System.out.println(v);
            String consulta =  "DELETE FROM vehiculo WHERE `vehiculo`.`Matricula` = '"+matricula+"'";    
            boolean resultado=conexion.realizarInsercionEliminacion(consulta);
            if (resultado==true) {
                System.out.println("Se ha realizado correctamente");
            } else{
                System.out.println("Algo ha fallado");
            }
            
            
            return v ;
	}
        
        public Vehiculo buscarMatricula(String matricula){
            String consulta ="SELECT * FROM `vehiculo` WHERE vehiculo.Matricula='"+matricula+"'";
            
            boolean encontrado=false;
            Vehiculo v = null;
            
            try {
                ResultSet resultado = conexion.realizarConsulta(consulta);
                int numRows = contadorFilas(resultado);
                
                if (numRows==0) {
                    System.out.println("No existen vehiculos con esa matricula");
                } else{
                    v = new Vehiculo(resultado.getString(1), resultado.getString(2), resultado.getString(3));
                }
                
            } catch (SQLException ex) {
                System.out.println("Algo ha fallado al realizar la consulta");
            }
            
            
            return v;
        }
        
        private int contadorFilas(ResultSet result) throws SQLException{
            int cont=0;
            
            while (result.next()) {
                cont++;
                
            }
            return cont;
            
        }

	
	public List<Vehiculo> eliminarVehiculos(List<Vehiculo> lstVehiculos) {
            this.falsaBD.removeAll(lstVehiculos);
            
            return lstVehiculos;
	}

	
	public List<Vehiculo> getVehiculos() {
		// TODO Auto-generated method stub
		return this.falsaBD;
	}

	
	public static DAOVehiculoImpl getInstance() throws SQLException {
	  if (dao== null) dao =new DAOVehiculoImpl();
	  
		return dao;
	}

	
	public boolean eliminarVehiculo(Vehiculo vehiculo) {
            boolean borrado = false;
            if (this.falsaBD.contains(vehiculo)) {
                falsaBD.remove(vehiculo);
                borrado=true;
                 
            }
            return borrado;
	}

}
