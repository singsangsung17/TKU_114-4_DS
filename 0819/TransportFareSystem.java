abstract class Transport {
    private final String routeName;

    Transport(String routeName) {
        this.routeName = routeName == null || routeName.isBlank()
                ? "Unknown" : routeName.trim();
    }

    String getRouteName() {
        return routeName;
    }

    abstract int calculateFare(int distance);
}

class Bus extends Transport {
    private final int baseFare;

    Bus(String routeName, int baseFare) {
        super(routeName);
        this.baseFare = Math.max(0, baseFare);
    }

    @Override
    int calculateFare(int distance) {
        int actual = Math.max(0, distance);
        return baseFare + (actual / 5) * 5;
    }
}

class Taxi extends Transport {
    private final int startFare;
    private final int perKilometer;

    Taxi(String routeName, int startFare, int perKilometer) {
        super(routeName);
        this.startFare = Math.max(0, startFare);
        this.perKilometer = Math.max(0, perKilometer);
    }

    @Override
    int calculateFare(int distance) {
        int actual = Math.max(0, distance);
        return startFare + actual * perKilometer;
    }
}

public class TransportFareSystem {
    public static void main(String[] args) {
        Transport[] transports = {
            new Bus("紅 32", 15),
            new Bus("藍 5", 20),
            new Taxi("市區叫車", 85, 20),
            new Taxi("機場接送", 120, 25)
        };

        System.out.println("距離 12 公里：");
        for (Transport transport : transports) {
            System.out.println(transport.getRouteName()
                    + " 票價=" + transport.calculateFare(12));
        }

        System.out.println("距離 -3 公里：");
        for (Transport transport : transports) {
            System.out.println(transport.getRouteName()
                    + " 票價=" + transport.calculateFare(-3));
        }
    }
}
