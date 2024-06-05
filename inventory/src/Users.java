public class Users {
    private String role;
    private int ID;

    public Users(String role, int ID) {
        this.role = role;
        this.ID = ID;
    }

    public Users() {
        this.role = "";
        this.ID = -1;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


}
