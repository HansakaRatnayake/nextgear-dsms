package model;

import util.SupportiveCM;

public class VehicleCategory extends SupportiveCM {

    private VehicleType vehicleType;


    public VehicleCategory() {
    }

    public VehicleCategory(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public VehicleCategory(Integer id, String name, VehicleType vehicleType) {
        super(id, name);
        this.vehicleType = vehicleType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}
