package logica;

public class Producto {
	private final int codigo;
	private String nombre;
	private Double existencias;
    private String unidad;
    private Double precio;
    
    public Producto(int codigo, String nombre, Double existencias,String unidad, Double precio) {
	this.codigo = codigo;
	this.nombre = nombre;
	this.existencias = existencias;
	this.unidad = unidad;
	this.precio = precio;
	}
    public int getCodigo() {
        return codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public Double getExistencias() {
        return existencias;
    }
    public String getUnidad() {
        return unidad;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setExistencias(Double existencias) {
        this.existencias = existencias;
    }
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}

