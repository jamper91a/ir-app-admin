package inventarioreal.com.inventarioreal_admin.pojo;

public class Zona {

    private long id;
    private String name;
    private Local locales_id;
    private String createdAt;
    private String UpdatedAt;

    public Zona() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Local getLocales_id() {
        return locales_id;
    }

    public void setLocales_id(Local locales_id) {
        this.locales_id = locales_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }
}
