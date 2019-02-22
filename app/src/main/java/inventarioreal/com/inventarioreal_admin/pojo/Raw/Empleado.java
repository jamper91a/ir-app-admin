package inventarioreal.com.inventarioreal_admin.pojo.Raw;

public class Empleado {

    private String createdAt;
    private String updatedAt;
    private String id;
    private long companias_id;
    private long users_id;
    private long locales_id;

    public Empleado() {
    }

    public Empleado(String createdAt, String updatedAt, String id, long companias_id, long user_id, long locales_id) {

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

    public long getCompanias_id() {
        return companias_id;
    }

    public void setCompanias_id(long companias_id) {
        this.companias_id = companias_id;
    }

    public long getUser_id() {
        return users_id;
    }

    public void setUser_id(long user_id) {
        this.users_id = user_id;
    }

    public long getLocales_id() {
        return locales_id;
    }

    public void setLocales_id(long locales_id) {
        this.locales_id = locales_id;
    }
}
