package entity;

public class Facility {
    private int facility_id; // Tesis özelliklerinin ID'si
    private int facility_hotel_id; // Tesis özelliklerinin ait olduğu otelin ID'si
    private boolean facility_free_park; // Ücretsiz otopark hizmeti
    private boolean facility_free_wifi; // Ücretsiz WiFi hizmeti
    private boolean facility_pool; // Yüzme havuzu mevcut mu?
    private boolean facility_gym; // Fitness merkezi bulunuyor mu?
    private boolean facility_concierge; // Otel görevlisi hizmeti mevcut mu?
    private boolean facility_SPA; // SPA hizmeti sunuluyor mu?
    private boolean facility_room_service; // 7/24 oda servisi mevcut mu?

    public Facility() {
    }

    public Facility(int facility_id) {
        this.facility_id = facility_id;
    }

    public int getFacility_id() {
        return facility_id;
    }

    public void setFacility_id(int facility_id) {
        this.facility_id = facility_id;
    }

    public int getFacility_hotel_id() {
        return facility_hotel_id;
    }

    public void setFacility_hotel_id(int facility_hotel_id) {
        this.facility_hotel_id = facility_hotel_id;
    }

    public boolean isFacility_free_park() {
        return facility_free_park;
    }

    public void setFacility_free_park(boolean facility_free_park) {
        this.facility_free_park = facility_free_park;
    }

    public boolean isFacility_free_wifi() {
        return facility_free_wifi;
    }

    public void setFacility_free_wifi(boolean facility_free_wifi) {
        this.facility_free_wifi = facility_free_wifi;
    }

    public boolean isFacility_pool() {
        return facility_pool;
    }

    public void setFacility_pool(boolean facility_pool) {
        this.facility_pool = facility_pool;
    }

    public boolean isFacility_gym() {
        return facility_gym;
    }

    public void setFacility_gym(boolean facility_gym) {
        this.facility_gym = facility_gym;
    }

    public boolean isFacility_concierge() {
        return facility_concierge;
    }

    public void setFacility_concierge(boolean facility_concierge) {
        this.facility_concierge = facility_concierge;
    }

    public boolean isFacility_SPA() {
        return facility_SPA;
    }

    public void setFacility_SPA(boolean facility_SPA) {
        this.facility_SPA = facility_SPA;
    }

    public boolean isFacility_room_service() {
        return facility_room_service;
    }

    public void setFacility_room_service(boolean facility_room_service) {
        this.facility_room_service = facility_room_service;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (facility_free_park) sb.append("Free Parking, ");
        if (facility_free_wifi) sb.append("Free WiFi, ");
        if (facility_pool) sb.append("Swimming Pool, ");
        if (facility_gym) sb.append("Fitness Center, ");
        if (facility_concierge) sb.append("Hotel Concierge, ");
        if (facility_SPA) sb.append("SPA, ");
        if (facility_room_service) sb.append("24/7 Room Service, ");

        // Remove the trailing newline character if sb is not empty
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1); // Remove the trailing newline character
        }

        return sb.toString();
    }
}


