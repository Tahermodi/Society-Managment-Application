package sgp.project.society;

import androidx.annotation.NonNull;

public class DummyClassParkingActivity {

    private String id;
    private String name;
    private String vehicle_number;
    private String vehicle_type;

    public DummyClassParkingActivity() {
    }

    public DummyClassParkingActivity(String id, String name, String vehicle_number, String vehicle_type) {
        this.id = id;
        this.name = name;
        this.vehicle_number = vehicle_number;
        this.vehicle_type = vehicle_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }
}
