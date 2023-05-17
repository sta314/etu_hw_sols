import java.util.Scanner;
public class Envanter {
    private double agirlik;
    private double hacim;
    private int fiyat;
    private int miktar;
    private String tur;
    public static Envanter[]  liste;
    public Envanter(){    Envanter[]  liste=new Envanter[0];}
    public Envanter(double agirlik, double hacim, int fiyat, int miktar, String tur){
        setAgirlik(agirlik);
        setFiyat(fiyat);
        setHacim(hacim);
        setMiktar(miktar);
        setTur(tur);
    }
    public int toplam_fiyat(){
        int toplamfiyat=0;
        for (int i = 0; i < liste.length ;i++) {
            toplamfiyat+=liste[i].getFiyat()*liste[i].getMiktar();
        }
        return toplamfiyat;
    }
    public void envanteri_doldur(){
        Scanner oku = new Scanner(System.in);
        while (true){
            System.out.println("Envantere hangi ürünü eklemek istiyorsanız ilgili rakamı giriniz.");
            System.out.println("1) Kitap, 2) Televizyon, 3) Bilgisayar, 4) Resmi Evrak, 5) Su, 6) Süt 7) Çıkış");
            int check=oku.nextInt();
            if(check==7)
                return;

            arrangeInput(check);
        }
    }
    public void arrangeInput(int a){
        String inp;
        String[] arrinp;
        Scanner oku = new Scanner(System.in);
        switch (a){
            case 1:
                System.out.println("Kitap için ağırlığını, hacmini ve fiyatını, yazar adını, basım yılını, başlığını, giriniz. Her bilgi arasına virgül karakteri koyunuz.");
                arrinp = extractInput(oku.nextLine());
                System.out.println("Ürün adedini giriniz.");
                envanterEkle(new Kitap(Double.parseDouble(arrinp[0]),Double.parseDouble(arrinp[1]), Integer.parseInt(arrinp[2]), arrinp[3], arrinp[4], arrinp[5], oku.nextInt()));
                break;
            case 2:
                System.out.println("Televizyon için ağırlığını, hacmini ve fiyatını, markasını ve ekran boyutunu giriniz. Her bilgi arasına virgül karakteri koyunuz.");
                arrinp = extractInput(oku.nextLine());
                System.out.println("Ürün adedini giriniz.");
                envanterEkle(new Televizyon(Double.parseDouble(arrinp[0]),Double.parseDouble(arrinp[1]), Integer.parseInt(arrinp[2]), arrinp[3], arrinp[4], oku.nextInt()));
                break;
            case 3:
                System.out.println("Bilgisayar için ağırlığını, hacmini ve fiyatını, işlemci sayısını, hafıza boyutunu, markasını ve modelini giriniz. Her bilgi arasına virgül karakteri koyunuz.");
                arrinp = extractInput(oku.nextLine());
                System.out.println("Ürün adedini giriniz.");
                envanterEkle(new Bilgisayar(Double.parseDouble(arrinp[0]),Double.parseDouble(arrinp[1]), Integer.parseInt(arrinp[2]), arrinp[3], arrinp[4], arrinp[5], arrinp[4], oku.nextInt()));
                break;
            case 4:
                System.out.println("Evrak için ağırlığını, hacmini ve fiyatını, evrak türünü giriniz.");
                arrinp = extractInput(oku.nextLine());
                System.out.println("Ürün adedini giriniz.");
                envanterEkle(new Evrak(Double.parseDouble(arrinp[0]),Double.parseDouble(arrinp[1]), Integer.parseInt(arrinp[2]), arrinp[3], oku.nextInt()));
                break;
            case 5:
                System.out.println("Su için ağırlığını, hacmini ve fiyatını, üretildiği yeri giriniz.");
                arrinp = extractInput(oku.nextLine());
                System.out.println("Ürün adedini giriniz.");
                envanterEkle(new Su(Double.parseDouble(arrinp[0]),Double.parseDouble(arrinp[1]), Integer.parseInt(arrinp[2]), arrinp[3], oku.nextInt()));
                break;
            case 6:
                System.out.println("Süt için ağırlığını, hacmini ve fiyatını, markasını ve son kullanma tarihini giriniz. Her bilgi arasına virgül karakteri koyunuz.");
                arrinp = extractInput(oku.nextLine());
                System.out.println("Ürün adedini giriniz.");
                envanterEkle(new Sut(Double.parseDouble(arrinp[0]),Double.parseDouble(arrinp[1]), Integer.parseInt(arrinp[2]), arrinp[3], arrinp[4], oku.nextInt()));
                break;
        }
    }
    public String[] extractInput(String inp){
        String[] arrinp;
        inp=inp.replace(", ",",");
        inp=inp.replace(" ,",",");
        arrinp=inp.split(",");
        for (int i = 0; i < arrinp.length; i++) {
            if(arrinp[i].charAt(0)==' ')
                arrinp[i]=arrinp[i].substring(1);
        }
        return arrinp;
    }
    public void envanterEkle(Envanter a){
        if(liste==null)
            liste = new Envanter[0];
        Envanter[] temp = new Envanter[liste.length+1];
        for (int i = 0; i < liste.length; i++) {
            temp[i]=liste[i];
        }
        temp[liste.length]=a;
        liste=temp;
    }
    public void envanteri_listele(){
        int countKitap=0,countTelevizyon=0,countBilgisayar=0,countEvrak=0,countSu=0,countSut=0;
        for (int i = 0; i < liste.length; i++) {
            if(liste[i].tur.equals("Kitap"))
                countKitap+=liste[i].miktar;
            if(liste[i].tur.equals("Televizyon"))
                countTelevizyon+=liste[i].miktar;
            if(liste[i].tur.equals("Bilgisayar"))
                countBilgisayar+=liste[i].miktar;
            if(liste[i].tur.equals("Evrak"))
                countEvrak+=liste[i].miktar;
            if(liste[i].tur.equals("Su"))
                countSu+=liste[i].miktar;
            if(liste[i].tur.equals("Sut"))
                countSut+=liste[i].miktar;
        }
        if(countKitap>0) {
            System.out.println("Toplamda " + countKitap + " adet kitap var.");
            for (int i = 0; i < liste.length; i++) {
                if (liste[i].tur.equals("Kitap")) {
                    System.out.println(liste[i].toString());
                }
            }
            System.out.println("-----------------------------------------");
        }
        if(countTelevizyon>0) {
            System.out.println("Toplamda " + countTelevizyon + " adet televizyon var.");
            for (int i = 0; i < liste.length; i++) {
                if (liste[i].tur.equals("Televizyon")) {
                    System.out.println(liste[i].toString());
                }
            }
            System.out.println("-----------------------------------------");
        }
        if(countBilgisayar>0) {
            System.out.println("Toplamda " + countBilgisayar + " adet bilgisayar var.");
            for (int i = 0; i < liste.length; i++) {
                if (liste[i].tur.equals("Bilgisayar")) {
                    System.out.println(liste[i].toString());
                }
            }
            System.out.println("-----------------------------------------");
        }
        if(countEvrak>0) {
            System.out.println("Toplamda " + countEvrak + " adet evrak var.");
            for (int i = 0; i < liste.length; i++) {
                if (liste[i].tur.equals("Evrak")) {
                    System.out.println(liste[i].toString());
                }
            }
            System.out.println("-----------------------------------------");
        }
        if(countSu>0) {
            System.out.println("Toplamda " + countSu + " adet su var.");
            for (int i = 0; i < liste.length; i++) {
                if (liste[i].tur.equals("Su")) {
                    System.out.println(liste[i].toString());
                }
            }
            System.out.println("-----------------------------------------");
        }
        if(countSut>0) {
            System.out.println("Toplamda " + countSut + " adet sut var.");
            for (int i = 0; i < liste.length; i++) {
                if (liste[i].tur.equals("Sut")) {
                    System.out.println(liste[i].toString());
                }
            }
            System.out.println("-----------------------------------------");
        }
    }
    public static void araci_listele(Envanter[] a){
        a = stackYuk(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i].toString());
        }
    }
    public static Envanter[] araca_ekle(Envanter[] arac, Envanter yuk){
        Envanter[] temp = new Envanter[arac.length+1];
        for (int i = 0; i < arac.length; i++) {
            temp[i]=arac[i];
        }
        temp[temp.length-1]=yuk;
        return temp;
    }
    public static Envanter[] stackYuk(Envanter[] arac){
        for (int i = 0; i < arac.length; i++) {
            for (int j = i+1; j < arac.length; j++) {
                if(arac[i].toString().substring(arac[i].toString().indexOf(':')).equals(arac[j].toString().substring(arac[j].toString().indexOf(':')))){
                    arac[i].setMiktar(arac[i].getMiktar()+arac[j].getMiktar());
                    arac = silYuk(arac,j);
                    j--;
                }
            }
        }
        return arac;
    }
    public static Envanter[] silYuk(Envanter[]arac,int index){
        Envanter[]temp=new Envanter[arac.length-1];
        for (int i = 0,j=0; i < arac.length; i++,j++) {
            if(index==i){
                j--;
                continue;
            }
            temp[j]=arac[i];
        }
        return temp;
    }
    public Envanter[] subEnvanter(Envanter[] inp, int a){
        Envanter[] temp = new Envanter[inp.length-a];
        for (int i = a; i <inp.length; i++) {
            temp[i-a]=inp[i];
        }
        return temp;
    }
    public String toString() {
        return "Tanimlanmamis madde.";
    }
    public static void araci_sirala(Envanter[] arac){
        araci_qsort(arac,0,arac.length-1);
    }
    public static void araci_qsort(Envanter[] arac, int d, int y){ //quicksort
        if(d<y){
            int pivot = araci_qsort_parca(arac,d,y);

            araci_qsort(arac, d, pivot-1);
            araci_qsort(arac, pivot+1, y);
        }
    }

    public static int araci_qsort_parca(Envanter[] arac, int d, int y){
        double pivotAgirlik = arac[y].getAgirlik();
        int i=d;
        for (int j = d; j < y; j++) {
            if(arac[j].getAgirlik()<=pivotAgirlik){
                Envanter temp = arac[i];
                arac[i]=arac[j];
                arac[j]=temp;
                i++;
            }
        }
        Envanter temp = arac[y];
        arac[y]=arac[i];
        arac[i]=temp;
        return i;
    }


    //
    //getters-setters
    //
    public double getAgirlik() {
        return agirlik;
    }

    public void setAgirlik(double agirlik) {
        this.agirlik = agirlik;
    }

    public double getHacim() {
        return hacim;
    }

    public void setHacim(double hacim) {
        this.hacim = hacim;
    }

    public int getFiyat() {
        return fiyat;
    }

    public void setFiyat(int fiyat) {
        this.fiyat = fiyat;
    }

    public int getMiktar() {
        return miktar;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }
}
