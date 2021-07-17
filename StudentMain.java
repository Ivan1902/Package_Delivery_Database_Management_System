package student;

//import rs.etf.sab.*;
import rs.etf.sab.operations.*;
import rs.etf.sab.tests.TestHandler;
import rs.etf.sab.tests.TestRunner;


public class StudentMain {

    public static void main(String[] args) {
        CityOperations cityOperations = new ni170615_CityOperations(); // Change this to your implementation.
        DistrictOperations districtOperations = new ni170615_DistrictOperations(); // Do it for all classes.
        CourierOperations courierOperations = new ni170615_CourierOperations(); // e.g. = new MyDistrictOperations();
        CourierRequestOperation courierRequestOperation = new ni170615_CourierRequestOperation();
        GeneralOperations generalOperations = new ni170615_GeneralOperations();
        UserOperations userOperations = new ni170615_UserOperations();
        VehicleOperations vehicleOperations = new ni170615_VehicleOperations();
        PackageOperations packageOperations = new ni170615_PackageOperations();

        TestHandler.createInstance(
                cityOperations,
                courierOperations,
                courierRequestOperation,
                districtOperations,
                generalOperations,
                userOperations,
                vehicleOperations,
                packageOperations);

        TestRunner.runTests();
    }
}
