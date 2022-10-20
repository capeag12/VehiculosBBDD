package com.mycompany.vehiculosbbdd;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;



public class GestionVehiculos {
	
	private ArrayList<Vehiculo> listaVehiculos;
	private String cadena="";
	private Random random;
	
	public GestionVehiculos() throws SQLException
	{   
            listaVehiculos = (ArrayList<Vehiculo>) DAOVehiculoImpl.getInstance().getVehiculos();
            random = new Random();
	}
        
        public boolean insertarVehiculo(Vehiculo vehiculo) {
	boolean añadido=false;
        
            if (buscarMatricula(vehiculo.getMatricula())==null) {
                listaVehiculos.add(vehiculo);
                añadido=true;
            }
            else añadido = false;
       
		
		return añadido;
	}

	

	
	public Vehiculo eliminarVehiculo(String matricula) {
            Vehiculo v = buscarMatricula(matricula);
            if (v.equals(null)==false) {
               this.listaVehiculos.remove(v); 
            }
            
            return v ;
	}
        
        public void editarVehiculo(int indice,String matricula,String modelo,String marca)
        {
            listaVehiculos.get(indice).setModelo(modelo);
            listaVehiculos.get(indice).setMarca(marca);
            listaVehiculos.get(indice).setMatricula(matricula);
        
        }
        
        public Vehiculo buscarMatricula(String matricula){
            boolean encontrado=false;
            Vehiculo v = null;
            for (int i = 0; i < listaVehiculos.size() && encontrado == false; i++) {
                if (listaVehiculos.get(i).getMatricula().equals(matricula)) {
                    v=listaVehiculos.get(i);
                    encontrado=true;
                }
            }
            return v;
        }

	
	public List<Vehiculo> eliminarVehiculos(List<Vehiculo> lstVehiculos) {
            this.listaVehiculos.removeAll(lstVehiculos);
            
            return lstVehiculos;
	}

	
	public List<Vehiculo> getVehiculos() {
		// TODO Auto-generated method stub
		return this.listaVehiculos;
	}

	
	

	
	public boolean eliminarVehiculo(Vehiculo vehiculo) {
            boolean borrado = false;
            if (this.listaVehiculos.contains(vehiculo)) {
                listaVehiculos.remove(vehiculo);
                borrado=true;
                 
            }
            return borrado;
	}
	
	public String getVehiculo()
	{  String cadenaEspacios="";
		Collections.shuffle(listaVehiculos);
	   cadena = listaVehiculos.get(0).getMarca() + " " + listaVehiculos.get(0).getModelo();
		for (int cont=0; cont<cadena.length(); cont++)
		{ if (random.nextBoolean())  cadenaEspacios = cadenaEspacios + cadena.charAt(cont);
		else  cadenaEspacios = cadenaEspacios +  "_";
		}
		
		
		return cadenaEspacios;
	}
	
	public boolean compara(String cadenaJugador)
	{return cadena.equalsIgnoreCase(cadenaJugador);
		}

    public ArrayList<Vehiculo> getListaVehiculos() {
        return listaVehiculos;
    }
        
        

}
