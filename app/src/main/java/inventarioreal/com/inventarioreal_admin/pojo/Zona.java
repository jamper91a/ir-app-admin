package inventarioreal.com.inventarioreal_admin.pojo;

public class Zona {

    private long id;
    private String name;
    private long locales_id;
    private String createdAt;
    private String updatedAt;

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

    public long getLocales_id() {
        return locales_id;
    }

    public void setLocales_id(long locales_id) {
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
        updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
