package ph41045.fpoly.onthi.Model;

public class ThiSinh {
    private int id;
    private String hoTen;
    private int caThi;
    private String phongThi;

    public ThiSinh(int id, String hoTen, int caThi, String phongThi) {
        this.id = id;
        this.hoTen = hoTen;
        this.caThi = caThi;
        this.phongThi = phongThi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getCaThi() {
        return caThi;
    }

    public void setCaThi(int caThi) {
        this.caThi = caThi;
    }

    public String getPhongThi() {
        return phongThi;
    }

    public void setPhongThi(String phongThi) {
        this.phongThi = phongThi;
    }
}
