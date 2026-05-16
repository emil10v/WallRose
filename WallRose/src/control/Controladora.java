package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import logica.Cliente;
import logica.Linea;
import logica.Producto;
import logica.OrdenCompra;

public class Controladora {
    private static Controladora instance = null;
    private Map<String, Cliente> clientes;
    private Map<Integer, Producto> productos;
    private Map<Integer, OrdenCompra> ordenesCompra;
    private int consecutivoOrden;
    private int consecutivoProducto;
    
    private Controladora() {
        this.clientes = new TreeMap<String, Cliente>();
        this.productos = new TreeMap<Integer, Producto>();
        this.ordenesCompra = new TreeMap<Integer, OrdenCompra>();
        this.consecutivoProducto = 1;
        this.consecutivoOrden = 1;
    }

    private void revisarClienteExiste(String id) throws Exception {
        if (!clientes.containsKey(id))
            throw new Exception("Cliente no existe.");
    }
    private void revisarProductoExiste(int codigoProducto) throws Exception {
        if (!productos.containsKey(codigoProducto))
            throw new Exception("Producto no existe.");
    }
    private void revisarOrdenCompraExiste(int numeroOrdenCompra) throws Exception {
        if (!ordenesCompra.containsKey(numeroOrdenCompra)) 
            throw new Exception("Orden de Compra no existe.");
    }
    
    private void revisarEmailValido(String email) throws Exception {
        Pattern p = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        Matcher m = p.matcher(email);
        if(!m.matches())
        	throw new Exception("Email invalido");
    }  
    
    public static Controladora getInstance() {
        if (instance == null) 
            instance = new Controladora();
        return instance;
    }
    
    public List<Cliente> getClientes() {
    	List<Cliente> listaClientes = new ArrayList<Cliente>();
    	for (Map.Entry<String, Cliente> e : clientes.entrySet()) {
    	listaClientes.add(e.getValue());
    	}
    	return listaClientes;
    }
    public Cliente getCliente(String id) throws Exception {
        revisarClienteExiste(id);
        return clientes.get(id);
    }
    public List<OrdenCompra> getOrdenesCliente(String id) throws Exception {
    	revisarClienteExiste(id);
    	List<OrdenCompra> listaOrdenes = new ArrayList<OrdenCompra>();
    	Cliente c = clientes.get(id);
    	Map<Integer, OrdenCompra> ordenes = c.getOrdenesCompra();
    	for (OrdenCompra orden : ordenes.values()) {
    		listaOrdenes.add(orden);
    	}
    	return listaOrdenes;
    }
    public List<OrdenCompra> getOrdenesPorEstadoCliente(String id, String estado) throws Exception {
    	revisarClienteExiste(id);
    	List<OrdenCompra> listaOrdenes = new ArrayList<OrdenCompra>();
    	Cliente c = clientes.get(id);
    	Map<Integer, OrdenCompra> ordenes = c.getOrdenesCompra();
    	for (OrdenCompra orden : ordenes.values()) {
    		if (orden.getEstado().equals(estado))
    			listaOrdenes.add(orden);
    	}
    	return listaOrdenes;
    } 
    public void crearCliente(String id, String nombre, String email) throws Exception {
	if (clientes.containsKey(id))
		throw new Exception("Cliente ya existe.");
	revisarEmailValido(email);
	Cliente c = new Cliente(id, nombre, email);
	clientes.put(id, c);
	}
    
    public void actualizarCliente(String id, String nombre, String email) throws Exception {
	revisarClienteExiste(id);
	revisarEmailValido(email);
	Cliente c = clientes.get(id);
	c.setNombre(nombre);
	c.setEmail(email);
	}
    
    public void borrarCliente(String id) throws Exception {
        revisarClienteExiste(id);
        clientes.remove(id);
    }
    
    public List<Producto> getProductos() {
    	List<Producto> listaProductos = new ArrayList<Producto>();
    	for (Map.Entry<Integer, Producto> p : productos.entrySet()) {
    		listaProductos.add(p.getValue());
    	}
    	return listaProductos;
    }
    
    public void crearProducto(String nombre, double existencias, String unidad, double precio) {
	Producto p = new Producto(consecutivoProducto, nombre, existencias, unidad, precio);
	productos.put(consecutivoProducto, p);
	consecutivoProducto++;
	}
    public Producto getProducto(int codigo) throws Exception {
        revisarProductoExiste(codigo);
        return productos.get(codigo);
    }
    
    public void actualizarProducto(int codigo, String nombre, double existencias, String unidad, double precio) throws Exception {
	revisarProductoExiste(codigo);
	Producto p = productos.get(codigo);
	p.setNombre(nombre);
	p.setExistencias(existencias);
	p.setUnidad(unidad);
	p.setPrecio(precio);
	}
    
    public void borrarProducto(int codigo) throws Exception {
        revisarProductoExiste(codigo);
        productos.remove(codigo);
    }
    
    public List<OrdenCompra> getOrdenesCompra() {
    	List<OrdenCompra> listaOrdenCompra = new ArrayList<OrdenCompra>();
    	for (Map.Entry<Integer, OrdenCompra> o : ordenesCompra.entrySet()) {
    		listaOrdenCompra.add(o.getValue());
    	}
    	return listaOrdenCompra;
    }
    public double getMontoTotalPendiente() {
    	double montoTotal = 0.0;
    	for (Map.Entry<Integer, OrdenCompra> o : ordenesCompra.entrySet()) {
    		OrdenCompra orden = o.getValue();
    		if (orden.getEstado().equals("Pendiente"))
    			montoTotal += orden.getMontoTotal();
    	}
    	return montoTotal;
    }
    public int crearOrdenCompraVacia(String idCliente) throws Exception {
    	revisarClienteExiste(idCliente);
        Cliente c = clientes.get(idCliente);
        OrdenCompra o = new OrdenCompra(consecutivoOrden, c);
        ordenesCompra.put(consecutivoOrden, o);
        consecutivoOrden++;
        return o.getNumero();
    }
    public OrdenCompra getOrdenCompra(int numero) throws Exception {
        revisarOrdenCompraExiste(numero);
        return ordenesCompra.get(numero);
    }
    public List<Linea> getLineasOrdenCompra(int numeroOrden) throws Exception {
        revisarOrdenCompraExiste(numeroOrden);
        OrdenCompra o = ordenesCompra.get(numeroOrden);
        return o.getLineas();
    }
    public void setOrdenCompraPendiente(int numeroOrden) throws Exception {
        revisarOrdenCompraExiste(numeroOrden);
        OrdenCompra o = ordenesCompra.get(numeroOrden);
        o.setEstado("Pendiente");
    }
    public void setOrdenCompraTerminada(int numeroOrden) throws Exception {
        revisarOrdenCompraExiste(numeroOrden);
        OrdenCompra o = ordenesCompra.get(numeroOrden);
        o.setEstado("Terminada");
    }
    public void agregarLineaOrdenCompra(int numeroOrden, int codigoProducto, double cantidad) throws Exception {
	revisarOrdenCompraExiste(numeroOrden);
	revisarProductoExiste(codigoProducto);
	OrdenCompra orden = ordenesCompra.get(numeroOrden);
	Producto prod = productos.get(codigoProducto);
	Linea nuevaLinea = new Linea(prod, cantidad);
	orden.agregarLinea(nuevaLinea);
	}
    
    public void actualizarLineaOrdenCompra(int numeroOrden, int numeroLinea, int codigoProducto, double cantidad) throws Exception {
	revisarOrdenCompraExiste(numeroOrden);
	revisarProductoExiste(codigoProducto);
	OrdenCompra o = ordenesCompra.get(numeroOrden);
	Producto p = productos.get(codigoProducto);
	o.actualizarLinea(numeroLinea, p, cantidad);
	}
    
    public void borrarLineaOrdenCompra(int numeroOrden, int numeroLinea) throws Exception {
	revisarOrdenCompraExiste(numeroOrden);
	OrdenCompra o = ordenesCompra.get(numeroOrden);
	o.borrarLinea(numeroLinea);
	}
    public void borrarOrdenCompra(int numeroOrden) throws Exception {
        revisarOrdenCompraExiste(numeroOrden);
        ordenesCompra.remove(numeroOrden);
    }
}