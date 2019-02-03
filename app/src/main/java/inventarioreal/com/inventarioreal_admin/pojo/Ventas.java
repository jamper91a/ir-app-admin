package inventarioreal.com.inventarioreal_admin.pojo;

public class Ventas {
    private long id;
    private User users_id;
    private String createdAt;
    private String modifiedAt;
    private ProductosZona productos_ona_id;

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

    public ProductosZona getProductos_ona_id() {
        return productos_ona_id;
    }

    public void setProductos_ona_id(ProductosZona productos_ona_id) {
        this.productos_ona_id = productos_ona_id;
    }
}
