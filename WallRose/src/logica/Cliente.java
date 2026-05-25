package logica;

import java.util.Map;
import java.util.TreeMap;

public class Cliente {
    private final String id;
    private String nombre;
    private String email;
    private Map<Integer, OrdenCompra> ordenes;
    
    public Cliente(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.ordenes = new TreeMap<Integer, OrdenCompra>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Map<Integer, OrdenCompra> getOrdenesCompra() {
    	return ordenes;
    }
    public void agregarOrdenCompra(OrdenCompra orden) {
    	int numero = orden.getNumero();
    	ordenes.put(numero, orden);
    }
   public void borrarOrdenCompra(OrdenCompra orden) {
	   int numero = orden.getNumero();
	   ordenes.remove(numero);
   }
   public Double getMontoPendiente() {
	   Double monto = 0.0;
		for (OrdenCompra orden : ordenes.values()) {
			if (orden.getEstado() == "Pendiente") {
				monto += orden.getMontoTotal();
			}
		}
		return monto;
   }
 }