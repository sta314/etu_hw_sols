public class Su extends Envanter{
    private String uretildigi_yer;
    public Su(double a,double b,int c,String d,int miktar){
        super(a,b,c,miktar,"Su");
        uretildigi_yer=d;
    }

    public String getUretildigi_yer() {
        return uretildigi_yer;
    }
    public static Su clone(Su a){
        return new Su(a.getAgirlik(),a.getHacim(),a.getFiyat(),a.uretildigi_yer,a.getMiktar());
    }
    public String toString() {
        return getMiktar()+" adet Su: "+uretildigi_yer;
    }
}
