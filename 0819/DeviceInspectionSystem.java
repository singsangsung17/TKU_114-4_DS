abstract class Device {
    private final String deviceId;

    Device(String deviceId) {
        this.deviceId = deviceId == null || deviceId.isBlank()
                ? "UNKNOWN" : deviceId.trim();
    }

    String getDeviceId() {
        return deviceId;
    }

    abstract void runDiagnostic();
}

class Laptop extends Device {
    private final int batteryPercent;

    Laptop(String deviceId, int batteryPercent) {
        super(deviceId);
        this.batteryPercent = Math.min(100, Math.max(0, batteryPercent));
    }

    @Override
    void runDiagnostic() {
        System.out.println("Laptop " + getDeviceId()
                + " battery=" + batteryPercent + "%");
    }
}

class Printer extends Device {
    private int inkPercent;

    Printer(String deviceId, int inkPercent) {
        super(deviceId);
        this.inkPercent = Math.min(100, Math.max(0, inkPercent));
    }

    @Override
    void runDiagnostic() {
        System.out.println("Printer " + getDeviceId()
                + " ink=" + inkPercent + "%");
    }

    void cleanPrintHead() {
        System.out.println("Printer " + getDeviceId() + " 清潔噴頭完成");
    }
}

class Router extends Device {
    private final int connectedClients;

    Router(String deviceId, int connectedClients) {
        super(deviceId);
        this.connectedClients = Math.max(0, connectedClients);
    }

    @Override
    void runDiagnostic() {
        System.out.println("Router " + getDeviceId()
                + " clients=" + connectedClients);
    }
}

public class DeviceInspectionSystem {
    static void inspect(Device device) {
        device.runDiagnostic();
        if (device instanceof Printer printer) {
            printer.cleanPrintHead();
        }
    }

    public static void main(String[] args) {
        Device[] devices = {
            new Laptop("L01", 82),
            new Printer("P01", 35),
            new Router("R01", 12),
            new Printer("P02", -5),
            new Laptop("L02", 120)
        };

        for (Device device : devices) {
            inspect(device);
        }
    }
}
