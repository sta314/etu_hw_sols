public class Sut extends Envanter {
    private String marka;
    private String skt;
    public Sut(double a,double b,int c,String d,String e,int miktar){
        super(a,b,c,miktar,"Sut");
        marka=d;
        skt=e;
    }

    public String getMarka() {
        return marka;
    }

    public String getSkt() {
        return skt;
    }
    public static Sut clone(Sut a){
        return new Sut(a.getAgirlik(),a.getHacim(),a.getFiyat(),a.marka,a.skt,a.getMiktar());
    }
    public String toString() {
        return getMiktar()+" adet Sut: "+marka+", "+skt;
    }
}