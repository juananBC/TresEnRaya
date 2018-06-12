package Fichero;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Gestor.Estado;
import Juego.Ficha;

public class Log {

	private BufferedWriter fichero;
	
	public Log() {
		try {
			fichero = new BufferedWriter(
					new FileWriter("C:\\Users\\JNBN007\\Desktop\\workspace\\TresEnRaya\\TresEnRaya\\resources\\resultado.log"));
			
			fichero.write("");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void append(String msg) {
	
		try {						
			fichero.append(msg);
			
		} catch (Exception e) {
			System.out.println("Error escritura");
		}
	}
	
	public void cerrar() {
		try {
			fichero.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
