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

	private DAOVehiculoImpl() throws SQLException{
            super();
            this.falsaBD = new ArrayList<Vehiculo>();
            conexion = new Conexion("vehiculos", "root", "");
            conexion.conectar();
                
                
            ResultSet resultados = conexion.realizarConsulta("SELECT * FROM `vehiculo`");
                
            if (resultados!=null) {
                while (resultados.next()) {
                this.falsaBD.add(new Vehiculo(resultados.getString(1),resultados.getString(2),resultados.getString(3))); 
            }
                
            } 
		
	}

	
	public boolean insertarVehiculo(Vehiculo vehiculo){
	
        
            String consulta = "INSERT INTO `vehiculo` (`Marca`, `Modelo`, `Matricula`) VALUES ('"+vehiculo.getMarca()+"', '"+vehiculo.getModelo()+"', '"+vehiculo.getMatricula()+"');";
            System.out.println(consulta);
           
        return conexion.realizarInsercionEliminacion(consulta);    
	}
        
        public void cerrarConexion() throws SQLException{
            conexion.desconectar();
            
            
        }

	

	
	public Vehiculo eliminarVehiculo(String matricula) throws SQLException {
            
            Vehiculo v = buscarMatricula(matricula);
            
            String consulta =  "DELETE FROM vehiculo WHERE `vehiculo`.`Matricula` = '"+matricula+"'";    
            conexion.realizarInsercionEliminacion(consulta);
            
            
            
            return v ;
	}
        
        public Vehiculo buscarMatricula(String matricula) throws SQLException{
           String consultaContar = "SELECT COUNT(Matricula) FROM `vehiculo` WHERE vehiculo.Matricula='"+matricula+"';";
            
           ResultSet resultadoCount = conexion.realizarConsulta(consultaContar);
           
           int num=0;
            while (resultadoCount.next()) {                
                num = Integer.parseInt(resultadoCount.getString(1));
            }
            System.out.println(num);
            if(num==1){
                String consulta = "SELECT * FROM `vehiculo` WHERE vehiculo.Matricula='"+matricula+"';";
           
           ResultSet resultado= conexion.realizarConsulta(consulta);
           
                while (resultado.next()) {       
                  
                  return new Vehiculo(resultado.getString(1),resultado.getString(2),resultado.getString(3));
                }
            } 
            
            return null;
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
        
        public boolean editarVehiculo(String mAntigua, String mNueva, String modelo, String marca ){
            String consulta = "UPDATE `vehiculo` SET `Marca` = '"+marca+"', `Modelo` = '"+modelo+"', `Matricula` = '"+mNueva+"' WHERE `vehiculo`.`Matricula` = '"+mAntigua+"'";
            
            int i=conexion.realizarUpdate(consulta);
            if (i==0) {
                System.out.println("Algo no fue bien");
                return false;
            } else{System.out.println("Todo fue bien"); return true;}
        }

}
