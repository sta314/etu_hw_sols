public class Evrak extends Envanter {
    private String evraktur;
    public Evrak(double a,double b,int c,String d,int miktar){
        super(a,b,c,miktar,"Evrak");
        evraktur=d;
    }

    public String getevraktur() {
        return evraktur;
    }
    public static Evrak clone(Evrak a){
        return new Evrak(a.getAgirlik(),a.getHacim(),a.getFiyat(),a.evraktur,a.getMiktar());
    }
    public String toString() {
        return getMiktar()+" adet Evrak: "+evraktur;
    }
}
