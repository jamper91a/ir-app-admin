package inventarioreal.com.inventarioreal_admin.pojo.WebServices.pojo;

public class User {

    private String createdAt;
    private String updatedAt;
    private String id;
    private String username;
    private String username_rfdi;
    private String password_rfdi;
    private Group groups_id;

    public User() {
    }

    public User(String createdAt, String updatedAt, String id, String username, String username_rfdi, String password_rfdi, Group groups_id) {

        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.id = id;
        this.username = username;
        this.username_rfdi = username_rfdi;
        this.password_rfdi = password_rfdi;
        this.groups_id = groups_id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername_rfdi() {
        return username_rfdi;
    }

    public void setUsername_rfdi(String username_rfdi) {
        this.username_rfdi = username_rfdi;
    }

    public String getPassword_rfdi() {
        return password_rfdi;
    }

    public void setPassword_rfdi(String password_rfdi) {
        this.password_rfdi = password_rfdi;
    }

    public Group getGroups_id() {
        return groups_id;
    }

    public void setGroups_id(Group groups_id) {
        this.groups_id = groups_id;
    }
}
