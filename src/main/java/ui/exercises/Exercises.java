package ui.exercises;

public class Exercises {
    private final int id;
    private final String bai;
    private final String nhom;
    private final String dang;
    private final double diem;
    private final String acPercent;
    private final int acCount;
    private final String moTa;
    private final String dauVao;
    private final String gioiHan;
    private final String dauRa;
    private final String input;
    private final String output;

    public Exercises(int id, String bai, String nhom, String dang, double diem, 
                     String acPercent, int acCount, String moTa, String dauVao, 
                     String gioiHan, String dauRa, String input, String output) {
        this.id = id;
        this.bai = bai;
        this.nhom = nhom;
        this.dang = dang;
        this.diem = diem;
        this.acPercent = acPercent;
        this.acCount = acCount;
        this.moTa = moTa;
        this.dauVao = dauVao;
        this.gioiHan = gioiHan;
        this.dauRa = dauRa;
        this.input = input;
        this.output = output;
    }

    // Getters
    public int getId() { return id; }
    public String getBai() { return bai; }
    public String getNhom() { return nhom; }
    public String getDang() { return dang; }
    public double getDiem() { return diem; }
    public String getAcPercent() { return acPercent; }
    public int getAcCount() { return acCount; }
    public String getMoTa() { return moTa; }
    public String getDauVao() { return dauVao; }
    public String getGioiHan() { return gioiHan; }
    public String getDauRa() { return dauRa; }
    public String getInput() { return input; }
    public String getOutput() { return output; }
}