public class Kitap extends Envanter{
    private String yazar_adi;
    private String basim_yili;
    private String baslik;
    public Kitap(double a, double b, int c,String d, String e, String f, int miktar){
        super(a,b,c,miktar,"Kitap");
        yazar_adi=d;
        basim_yili=e;
        baslik=f;
    }

    public static Kitap clone(Kitap a){
        return new Kitap(a.getAgirlik(),a.getHacim(),a.getFiyat(),a.yazar_adi,a.basim_yili,a.baslik,a.getMiktar());
    }
    public String getYazar_adi() {
        return yazar_adi;
    }

    public String getBasim_yili() {
        return basim_yili;
    }

    public String getBaslik() {
        return baslik;
    }

    public String toString() {
        return getMiktar()+" adet Kitap: "+yazar_adi+", "+basim_yili+", "+baslik;
    }
}
