package logica;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class OrdenCompra {
    private final int numero;
    private final LocalDateTime fecha;
    private String estado;
    private final static Double impuestoVenta = 0.13;
    private Cliente cliente;
    private List<Linea> lineas;

    public OrdenCompra(int numero, Cliente cliente) {
        this.numero = numero;
        this.cliente = cliente;
        this.fecha = LocalDateTime.now();
        this.estado = "Iniciada";
        this.lineas = new ArrayList<Linea>();
    }

    public int getNumero() {
        return numero;
    }

    public String getFecha() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	return fecha.format(formatter);

    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Linea> getLineas() {
        return lineas;
    }

    public void agregarLinea(Linea linea) {
        lineas.add(linea);
    }

    public void borrarLinea(int numLinea) throws Exception {
    	if (numLinea < 0 || numLinea >= lineas.size())
    		throw new Exception("Numero de linea invalido");
        lineas.remove(numLinea);
    }

    public Double getMontoTotal() {
        Double total = 0.0;
        for (Linea l : lineas) {
            total += l.getCosto();
        }
        total += total * impuestoVenta;
        return total;
    }
    public void actualizarLinea(int numeroLinea, Producto prod, double cantidad) throws Exception {
	if (numeroLinea < 0 || numeroLinea >= lineas.size()) 
		throw new Exception("Numero de linea invalido.");
	Linea l = lineas.get(numeroLinea);
	l.setProducto(prod);
	l.setCantidad(cantidad);
    }
}