package inventarioreal.com.inventarioreal_admin.pojo;

public class Local {
    private String createdAt;
    private String updatedAt;
    private String id;
    private Compania companias_id;

    public Local() {
    }

    public Local(String createdAt, String updatedAt, String id, Compania companias_id) {

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.id = id;
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

    public Compania getCompanias_id() {
        return companias_id;
    }

    public void setCompanias_id(Compania companias_id) {
        this.companias_id = companias_id;
    }
}
