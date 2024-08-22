package Class;

/**
 *
 * @author Reyner
 */
public class Client {
    private int idClient;
    private String cedula;
    private String name;

    public Client(int idClient, String cedula, String name) {
        this.idClient = idClient;
        this.cedula = cedula;
        this.name = name;
    }

    public void setIdClient(int idCLient) {
        this.idClient = idClient;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getCedula() {
        return cedula;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Client{" + "idCLient=" + idClient + ", cedula=" + cedula + ", name=" + name + '}';
    }
    
}
