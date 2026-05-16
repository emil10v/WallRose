package logica;

public class Linea {
    private Producto producto;
    private Double cantidad;

    public Linea(Producto producto, Double cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getCosto() {
        return producto.getPrecio() * cantidad;
    }
}