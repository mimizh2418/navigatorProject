package navigator.utils;

import java.util.HashMap;

public class RoadNameShortener {
    public static HashMap<String, String> directions;
    public static HashMap<String, String> roadTypes;

    static {
        directions = new HashMap<>();
        directions.put("North", "N");
        directions.put("Northwest", "NW");
        directions.put("Northeast", "NE");
        directions.put("South", "S");
        directions.put("Southwest", "SW");
        directions.put("Southeast", "SE");
        directions.put("East", "E");
        directions.put("West", "W");

        roadTypes = new HashMap<>();
        roadTypes.put("Alley", "Aly");
        roadTypes.put("Avenue", "Ave");
        roadTypes.put("Boulevard", "Blvd");
        roadTypes.put("Causeway", "Cswy");
        roadTypes.put("Center", "Ctr");
        roadTypes.put("Circle", "Cir");
        roadTypes.put("Court", "Ct");
        roadTypes.put("Cove", "Cv");
        roadTypes.put("Crossing", "Xing");
        roadTypes.put("Drive", "Dr");
        roadTypes.put("Expressway", "Expy");
        roadTypes.put("Extension", "Ext");
        roadTypes.put("Freeway", "Fwy");
        roadTypes.put("Grove", "Grv");
        roadTypes.put("Highway", "Hwy");
        roadTypes.put("Hollow", "Holw");
        roadTypes.put("Junction", "Jct");
        roadTypes.put("Lane", "Ln");
        roadTypes.put("Motorway", "Mtwy");
        roadTypes.put("Overpass", "Opas");
        roadTypes.put("Parkway", "Pkwy");
        roadTypes.put("Place", "Pl");
        roadTypes.put("Plaza", "Plz");
        roadTypes.put("Point", "Pt");
        roadTypes.put("Road", "Rd");
        roadTypes.put("Route", "Rt");
        roadTypes.put("Skyway", "Skwy");
        roadTypes.put("Square", "Sq");
        roadTypes.put("Street", "St");
        roadTypes.put("Terrace", "Ter");
        roadTypes.put("Trail", "Trl");
    }

    private RoadNameShortener() {}

    public static String shorten(String streetName) {
        if (streetName.isEmpty()) return "Unnamed Rd";
        StringBuilder shortened = new StringBuilder();
        String[] words = streetName.split(" ");

        String direction = words[0];
        String shortDirect = directions.getOrDefault(direction, "");
        shortened.append(shortDirect);
        if (!shortDirect.isEmpty()) shortened.append(" ");

        for (int i = shortDirect.isEmpty() ? 0 : 1; i < words.length - 1; i++) {
            shortened.append(words[i]).append(" ");
        }

        String roadType = words[words.length - 1];
        String shortStreet = roadTypes.getOrDefault(roadType, roadType);
        shortened.append(shortStreet);

        return shortened.toString();
    }
}
