package modelo;

public class DetalleVenta extends Producto {

    private int cantidad;

    public double Total() {
        return getCantidad() * getPrecio();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
