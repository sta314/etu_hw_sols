public class demo{
    public static void main(String[] args) {
        Envanter test = new Envanter();
        FirmaYonetimi firma = new FirmaYonetimi();
        // test.envanteri_doldur(); kullanilarak da ayni sekilde doldurulabilir. Test icin kolay olsun diye boyle yazdim.
        test.envanterEkle(new Evrak(3000000,40000,405,"Cok agir evrak",5));
        test.envanterEkle(new Kitap(300,10,35,"YAZAR1","1991","ya1",5));
        test.envanterEkle(new Kitap(3000,20,40,"YAZAR2","1992","ya2",2));
        test.envanterEkle(new Kitap(150,30,30,"YAZMAZ1","1993","ym1",4));
        test.envanterEkle(new Kitap(1000,20,20,"YAZMAZ2","1994","ym2",2));
        test.envanterEkle(new Televizyon(30000,2000,5000,"30","f",8));
        test.envanterEkle(new Sut (7000,2000,2,"Icim","13.12.2020",2));
        test.envanterEkle(new Sut (5000,3000,3,"Pinar","03.12.2020",5));
        test.envanterEkle(new Sut (3000,2000,2,"Sek","14.06.2020",5));
        test.envanterEkle(new Su (10000,10000,1,"Culudag",2));
        test.envanterEkle(new Su (1000,1000,5,"Suludag",2));
        test.envanterEkle(new Su (5000,5000,3,"Uludag",34));
        test.envanteri_listele();
        System.out.println("Envanterin toplam fiyati : " +test.toplam_fiyat());
        firma.uygun_maliyeti_hesapla();
    }
}