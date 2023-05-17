public class Bilgisayar extends Envanter {
    private String islemci_sayisi;
    private String hafiza_boyutu;
    private String marka;
    private String model;
    public Bilgisayar(double a,double b,int c,String d, String e, String f, String g,int miktar){
        super(a,b,c,miktar,"Bilgisayar");
        islemci_sayisi=d;
        hafiza_boyutu=e;
        marka=f;
        model=g;
    }

    public String getIslemci_sayisi() {
        return islemci_sayisi;
    }

    public String getHafiza_boyutu() {
        return hafiza_boyutu;
    }

    public String getMarka() {
        return marka;
    }
    public static Bilgisayar clone(Bilgisayar a){
        return new Bilgisayar(a.getAgirlik(),a.getHacim(),a.getFiyat(),a.islemci_sayisi,a.hafiza_boyutu,a.marka,a.model,a.getMiktar());
    }
    public String getModel() {
        return model;
    }

    public String toString() {
        return getMiktar()+" adet Bilgisayar: "+islemci_sayisi+", "+hafiza_boyutu+", "+marka+", "+model;
    }
}
