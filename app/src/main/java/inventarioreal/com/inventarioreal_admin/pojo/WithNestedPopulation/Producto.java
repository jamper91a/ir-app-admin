package inventarioreal.com.inventarioreal_admin.pojo.WithNestedPopulation;

import android.content.ContentValues;

import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Producto {

        private String createdAt;
        private String updatedAt;
        private String id;
        private String ean;
        private String plu;
        private String plu2;
        private String plu3;
        private String marca;
        private String genero;
        private String color;
        private String talla;
        private String categoria;
        private String descripcion;
        private String cantidad;
        private String imagen;
        private String precio_costo;
        private String precio_venta;
        private Compania companias_id;

    public Producto() {
    }

    public Producto(String createdAt, String updatedAt, String id, String ean, String plu, String plu2, String plu3, String marca, String genero, String color, String talla, String categoria, String descripcion, String cantidad, String imagen, String precio_costo, String precio_venta, Compania companias_id) {

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.id = id;
        this.ean = ean;
        this.plu = plu;
        this.plu2 = plu2;
        this.plu3 = plu3;
        this.marca = marca;
        this.genero = genero;
        this.color = color;
        this.talla = talla;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.imagen = imagen;
        this.precio_costo = precio_costo;
        this.precio_venta = precio_venta;
        this.companias_id = companias_id;
    }

    public String getCreatedAt() {

        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getPlu() {
        return plu;
    }

    public void setPlu(String plu) {
        this.plu = plu;
    }

    public String getPlu2() {
        return plu2;
    }

    public void setPlu2(String plu2) {
        this.plu2 = plu2;
    }

    public String getPlu3() {
        return plu3;
    }

    public void setPlu3(String plu3) {
        this.plu3 = plu3;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getPrecio_costo() {
        return precio_costo;
    }

    public void setPrecio_costo(String precio_costo) {
        this.precio_costo = precio_costo;
    }

    public String getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(String precio_venta) {
        this.precio_venta = precio_venta;
    }

    public Compania getCompanias_id() {
        return companias_id;
    }

    public void setCompanias_id(Compania companias_id) {
        this.companias_id = companias_id;
    }

    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(Constants.createdAt,createdAt);
        values.put(Constants.updatedAt,updatedAt);
        values.put(Constants.id,id);
        values.put(Constants.ean,ean);
        values.put(Constants.plu,plu);
        values.put(Constants.plu2,plu2);
        values.put(Constants.plu3,plu3);
        values.put(Constants.marca,marca);
        values.put(Constants.genero,genero);
        values.put(Constants.color,color);
        values.put(Constants.talla,talla);
        values.put(Constants.categoria,categoria);
        values.put(Constants.descripcion,descripcion);
        values.put(Constants.cantidad,cantidad);
        values.put(Constants.imagen,imagen);
        values.put(Constants.precio_costo,precio_costo);
        values.put(Constants.precio_venta,precio_venta);
        values.put(Constants.companias_id,getCompanias_id().getId());

        return values;
    }
}
