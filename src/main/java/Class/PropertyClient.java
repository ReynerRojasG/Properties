package Class;

/**
 *
 * @author Reyner
 */
public class PropertyClient {
    private String type;
    private String status;
    private String direction;
    private String size;
    private int clientPropertyId;
    private int propertyId;
    private int value;

    public PropertyClient(String type, String status, String direction, String size, int clientPropertyId, int propertyId, int value) {
        this.type = type;
        this.status = status;
        this.direction = direction;
        this.size = size;
        this.clientPropertyId = clientPropertyId;
        this.propertyId = propertyId;
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setClientPropertyId(int clientPropertyId) {
        this.clientPropertyId = clientPropertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getDirection() {
        return direction;
    }

    public String getSize() {
        return size;
    }

    public int getClientPropertyId() {
        return clientPropertyId;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    
}
