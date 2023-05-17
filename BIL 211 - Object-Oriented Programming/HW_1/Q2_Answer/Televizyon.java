public class Televizyon extends Envanter{
    private String marka;
    private String ekran_boyutu;
    public Televizyon(double a, double b, int c, String d, String e, int miktar){
        super(a,b,c,miktar,"Televizyon");
        marka=d;
        ekran_boyutu=e;
    }

    public String getMarka() {
        return marka;
    }

    public String getEkran_boyutu() {
        return ekran_boyutu;
    }
    public static Televizyon clone(Televizyon a){
        return new Televizyon(a.getAgirlik(),a.getHacim(),a.getFiyat(),a.marka,a.ekran_boyutu,a.getMiktar());
    }
    public String toString() {
        return getMiktar()+" adet Televizyon: "+marka+", "+ekran_boyutu;
    }
}
