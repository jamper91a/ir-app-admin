package inventarioreal.com.inventarioreal_admin.pojo;

public class Ventas {
    private long id;
    private User users_id;
    private String createdAt;
    private String modifiedAt;
    private ProductosZonas productos_zonas_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUsers_id() {
        return users_id;
    }

    public void setUsers_id(User users_id) {
        this.users_id = users_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public ProductosZonas getProductos_zonas_id() {
        return productos_zonas_id;
    }

    public void setProductos_zonas_id(ProductosZonas productos_zonas_id) {
        this.productos_zonas_id = productos_zonas_id;
    }
}
