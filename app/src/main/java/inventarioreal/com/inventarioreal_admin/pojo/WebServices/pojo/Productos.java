package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.HashMap;

import inventarioreal.com.inventarioreal_admin.pojo.InventarioRealPojo;
import inventarioreal.com.inventarioreal_admin.util.Constants;

public class Productos extends InventarioRealPojo {

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
        private Companias companias_id;

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

    public Companias getCompanias_id() {
        return companias_id;
    }

    public void setCompanias_id(Companias companias_id) {
        this.companias_id = companias_id;
    }

    @Override
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
        values.put(Constants.companias_id,companias_id.id);

        return values;
    }

    @Override
    public void fromHashMap(Class myClass, HashMap<String, String> data) {

    }

    @Override
    public void toHashMap(Class myClass) {

    }

    @Override
    public void fromCursor(Cursor c) {

    }
}
