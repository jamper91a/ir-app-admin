package inventarioreal.com.inventarioreal_admin.pojo;

public class Empleado {

    private String createdAt;
    private String updatedAt;
    private String id;
    private Compania companias_id;
    private User users_id;
    private Local locales_id;

    public Empleado() {
    }

    public Empleado(String createdAt, String updatedAt, String id, Compania companias_id, User user_id, Local locales_id) {

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.id = id;
        this.companias_id = companias_id;
        this.users_id = user_id;
        this.locales_id = locales_id;
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

    public Compania getCompanias_id() {
        return companias_id;
    }

    public void setCompanias_id(Compania companias_id) {
        this.companias_id = companias_id;
    }

    public User getUser_id() {
        return users_id;
    }

    public void setUser_id(User user_id) {
        this.users_id = user_id;
    }

    public Local getLocales_id() {
        return locales_id;
    }

    public void setLocales_id(Local locales_id) {
        this.locales_id = locales_id;
    }
}
