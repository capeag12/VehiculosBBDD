/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.vehiculosbbdd;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author capea
 */
public class Conexion {
    private String BBDD;
    private String url = "jdbc:mysql://localhost/";
    private String usuario;
    private String contraseña;
    private String driver= "com.mysql.cj.jdbc.Driver";
    
    Connection conexion;

    public Conexion(String BBDD, String usuario, String contraseña) {
        this.BBDD = BBDD;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

       
    
    public Connection conectar(){
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url+BBDD, usuario, contraseña);
            System.out.println("Se conectó a la BBDD:"+BBDD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("No se conecto a la BBDD:"+BBDD);
        }
        return conexion;
        
    }
    
    public ResultSet realizarConsulta(String consulta) throws SQLException{
        //Preparo la consulta
        try {
            PreparedStatement sentencia = conexion.prepareStatement(consulta);
        
        //Establezco los parametros de consulta en caso de necesitar
        //sentencia.setString(1, "texto");
        
        //Realizo la consulta
        return sentencia.executeQuery();
        } catch (NullPointerException e) {
            System.out.println("Algo ha fallado");
        }
        return null;
    }
    
    public boolean realizarInsercionEliminacion(String query) {
        try {
            PreparedStatement sentencia = conexion.prepareStatement(query);
        
            sentencia.execute();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Algo ha fallado");
        } catch(NullPointerException a){
            System.out.println("No existe la conexion");
        }
        
        return false;
    }
    
    public int realizarUpdate(String query){
        int resultado = 0;
        
        try {
            PreparedStatement sentencia = conexion.prepareStatement(query);
        
            resultado= sentencia.executeUpdate();
            System.out.println("Realizado correctamente");
        } catch (SQLException e) {
            System.out.println("Algo ha fallado");
        } catch(NullPointerException a){
            System.out.println("No existe la conexion");
        }
        
        return resultado;
    }
    
    public void desconectar() throws SQLException{
        try {
            conexion.close();
            System.out.println("Se desconectó correctamente");
            
        } catch (SQLException e) {
            System.out.println("Algo falló al desconectar");
        } catch (NullPointerException p){ System.out.println("No se puede desconectar al no existir la conexion");}
        ;
    }
    
}
