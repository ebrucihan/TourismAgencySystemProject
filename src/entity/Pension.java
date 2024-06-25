package entity;

public class Pension {
    private int pension_type_id; // Pansiyon tipi ID'si
    private int pension_hotel_id; // Pansiyon tipine ait otel ID'si
    private boolean pension_type_ultra; // Ultra Herşey Dahil
    private boolean pension_type_hsd; // Herşey Dahil
    private boolean pension_type_breakfast; // Yatak ve Kahvaltı
    private boolean pension_type_tam; // Tam Pansiyon
    private boolean pension_type_yarim; // Yarım Pansiyon
    private boolean pension_type_just_bed; // Sadece Yatak
    private boolean pension_type_ahfc; // Alkol Hariç Full Kredi

    public Pension() {
    }

    public Pension(int pension_type_id) {
        this.pension_type_id = pension_type_id;
    }

    public int getPension_type_id() {
        return pension_type_id;
    }

    public void setPension_type_id(int pension_type_id) {
        this.pension_type_id = pension_type_id;
    }

    public int getPension_hotel_id() {
        return pension_hotel_id;
    }

    public void setPension_hotel_id(int pension_hotel_id) {
        this.pension_hotel_id = pension_hotel_id;
    }

    public boolean isPension_type_ultra() {
        return pension_type_ultra;
    }

    public void setPension_type_ultra(boolean pension_type_ultra) {
        this.pension_type_ultra = pension_type_ultra;
    }

    public boolean isPension_type_hsd() {
        return pension_type_hsd;
    }

    public void setPension_type_hsd(boolean pension_type_hsd) {
        this.pension_type_hsd = pension_type_hsd;
    }

    public boolean isPension_type_breakfast() {
        return pension_type_breakfast;
    }

    public void setPension_type_breakfast(boolean pension_type_breakfast) {
        this.pension_type_breakfast = pension_type_breakfast;
    }

    public boolean isPension_type_tam() {
        return pension_type_tam;
    }

    public void setPension_type_tam(boolean pension_type_tam) {
        this.pension_type_tam = pension_type_tam;
    }

    public boolean isPension_type_yarim() {
        return pension_type_yarim;
    }

    public void setPension_type_yarim(boolean pension_type_yarim) {
        this.pension_type_yarim = pension_type_yarim;
    }

    public boolean isPension_type_just_bed() {
        return pension_type_just_bed;
    }

    public void setPension_type_just_bed(boolean pension_type_just_bed) {
        this.pension_type_just_bed = pension_type_just_bed;
    }

    public boolean isPension_type_ahfc() {
        return pension_type_ahfc;
    }

    public void setPension_type_ahfc(boolean pension_type_ahfc) {
        this.pension_type_ahfc = pension_type_ahfc;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (pension_type_ultra) sb.append("Ultra All Inclusive, ");
        if (pension_type_hsd) sb.append("All Inclusive, ");
        if (pension_type_breakfast) sb.append("Bed and Breakfast, ");
        if (pension_type_tam) sb.append("Full Board, ");
        if (pension_type_yarim) sb.append("Half Board, ");
        if (pension_type_just_bed) sb.append("Bed Only, ");
        if (pension_type_ahfc) sb.append("Alcohol-Free Full Credit, ");

        if (sb.length() > 0) {
            sb.setLength(sb.length() - 2); // Remove the trailing comma and space
        }

        return sb.toString();
    }
}