public class FirmaYonetimi {
    public void uygun_maliyeti_hesapla(){
        int toplamMiktar=0;
        int toplamSiviMiktar=0;
        for (int i = 0; i <Envanter.liste.length; i++) {
            if(Envanter.liste[i].getTur().equals("Kitap")||Envanter.liste[i].getTur().equals("Televizyon")||Envanter.liste[i].getTur().equals("Bilgisayar")||Envanter.liste[i].getTur().equals("Evrak"))
                toplamMiktar += Envanter.liste[i].getMiktar();
            if(Envanter.liste[i].getTur().equals("Su")||Envanter.liste[i].getTur().equals("Sut"))
                toplamSiviMiktar += Envanter.liste[i].getMiktar();
        }
        Envanter[] maddeler = new Envanter[toplamMiktar];
        Envanter[] sivimaddeler = new Envanter[toplamSiviMiktar];
        int sth=0;
        int sivisth=0;
        for (int i = 0; i < Envanter.liste.length; i++) { //Tasinacaklari tek tek olacak bicimde baska bir arraye aktariyor.
            for (int j = 0; j < Envanter.liste[i].getMiktar(); j++) {
                if(Envanter.liste[i].getTur().equals("Su")) {
                    sivimaddeler[sivisth] = Su.clone((Su) Envanter.liste[i]);
                    sivimaddeler[sivisth].setMiktar(1);
                    sivisth++;
                    continue;
                }
                if(Envanter.liste[i].getTur().equals("Sut")) {
                    sivimaddeler[sivisth] = Sut.clone((Sut) Envanter.liste[i]);
                    sivimaddeler[sivisth].setMiktar(1);
                    sivisth++;
                    continue;
                }
                if(Envanter.liste[i].getTur().equals("Kitap"))
                    maddeler[sth]=Kitap.clone((Kitap)Envanter.liste[i]);
                if(Envanter.liste[i].getTur().equals("Televizyon"))
                    maddeler[sth]=Televizyon.clone((Televizyon)Envanter.liste[i]);
                if(Envanter.liste[i].getTur().equals("Bilgisayar"))
                    maddeler[sth]=Bilgisayar.clone((Bilgisayar)Envanter.liste[i]);
                if(Envanter.liste[i].getTur().equals("Evrak"))
                    maddeler[sth]=Evrak.clone((Evrak)Envanter.liste[i]);
               maddeler[sth].setMiktar(1);
               sth++;
            }
        }
        Envanter.araci_sirala(maddeler);;
        Envanter.araci_sirala(sivimaddeler);
        //Tasinacaklari kucukten buyuge agirliga gore siraladim.
        int minPrice=0;
        int rnAgirlik=0;
        int rnHacim=0;
        int TirCounter=1;
        int KamyonCounter=1;
        String rnArac="Kamyon";
        Envanter[] tempList=new Envanter[0];
            //////////////////
        System.out.println("-----Nakliye Basliyor-----");
        for (int i = 0; i < maddeler.length; i++) {
                if(rnArac.equals("Kamyon")){ //Kamyona yuklemeye calisiyorsam;
                    if(rnAgirlik+maddeler[i].getAgirlik()<=Kamyon.getMaxAgirlik()){//Kamyonun kasasi kaldirabiliyorsa;
                        if(rnHacim+maddeler[i].getHacim()<=Kamyon.getHacimKasa()){//Icine sigiyor ise icine koy.
                            rnAgirlik+=maddeler[i].getAgirlik();
                            rnHacim+=maddeler[i].getHacim();
                            tempList = Envanter.araca_ekle(tempList,maddeler[i]);
                        }
                        else{//Icine sigmiyor ise ilerideki elemanlari kontrol et.
                            for (int j = i; j < maddeler.length; j++) {
                                if(maddeler[j].getAgirlik()+rnAgirlik<=Kamyon.getMaxAgirlik()&&maddeler[j].getHacim()+rnHacim<=Kamyon.getHacimKasa()){//Ideal bir eslesme bulursa icine ekle.
                                    tempList = Envanter.araca_ekle(tempList,maddeler[j]);
                                    rnAgirlik+=maddeler[j].getAgirlik();
                                    rnHacim+=maddeler[j].getHacim();
                                    maddeler = Envanter.silYuk(maddeler,j);
                                    break;
                                }

                                if(maddeler.length-1==j){//Ileride de ideal eslesme bulamazsa tir yapmayi dusunup eslesme ara.
                                    for (int k = i; k < maddeler.length; k++) {
                                        if(maddeler[k].getAgirlik()+rnAgirlik<=Tir.getMaxAgirlik()&&maddeler[k].getHacim()+rnHacim<=Tir.getHacimKasa()){//Ideal bir eslesme bulursa icine ekle.
                                            rnArac="Tir";
                                            tempList = Envanter.araca_ekle(tempList,maddeler[k]);
                                            rnAgirlik+=maddeler[k].getAgirlik();
                                            rnHacim+=maddeler[k].getHacim();
                                            maddeler = Envanter.silYuk(maddeler,k);
                                            break;
                                        }
                                        if(maddeler.length-1==k){//Ileride de ideal eslesme bulamazsa kamyonu yolla.
                                            rnHacim=0;
                                            rnAgirlik=0;
                                            minPrice+=Kamyon.getFiyat();
                                            System.out.println("-----------------------------------------");
                                            System.out.println(KamyonCounter++ +". Kamyon:");
                                            Envanter.araci_listele(tempList);
                                            tempList=new Envanter[0];
                                        }
                                    }
                                }
                            }
                            i--;
                            continue;
                        }
                    }
                    else{//Kamyonun kasasi kaldiramiyorsa;
                        if(rnAgirlik+maddeler[i].getAgirlik()<=Tir.getMaxAgirlik()){//Tirin kasasi kaldirabiliyorsa;
                            if(rnHacim+maddeler[i].getHacim()<=Tir.getHacimKasa()){//Icine sigiyor ise tira buyutup icine koy.
                                rnArac="Tir";
                                rnAgirlik+=maddeler[i].getAgirlik();
                                rnHacim+=maddeler[i].getHacim();
                                tempList = Envanter.araca_ekle(tempList,maddeler[i]);
                            }
                            else{//Icine sigmiyor ise sonraki elemanlara bak.
                                for (int j = i; j < maddeler.length; j++) {
                                    if(maddeler[j].getAgirlik()+rnAgirlik<=Tir.getMaxAgirlik()&&maddeler[j].getHacim()+rnHacim<=Tir.getHacimKasa()){//Ideal bir eslesme bulursa icine ekle.
                                        rnArac="Tir";
                                        tempList = Envanter.araca_ekle(tempList,maddeler[j]);
                                        rnAgirlik+=maddeler[j].getAgirlik();
                                        rnHacim+=maddeler[j].getHacim();
                                        maddeler = Envanter.silYuk(maddeler,j);
                                        break;
                                    }
                                    if(maddeler.length-1==j){//Ileride de ideal eslesme bulamazsa
                                        rnHacim=0;
                                        rnAgirlik=0;
                                        minPrice+=Kamyon.getFiyat();
                                        System.out.println("-----------------------------------------");
                                        System.out.println(KamyonCounter++ +". Kamyon:");
                                        Envanter.araci_listele(tempList);
                                        tempList=new Envanter[0];
                                    }
                                }
                                i--;
                                continue;
                            }
                        }
                        else{//Tirin kasasi da kaldiramiyorsa kamyonu yolla ve yeni arac getir.
                            rnHacim=0;
                            rnAgirlik=0;
                            minPrice+=Kamyon.getFiyat();
                            System.out.println("-----------------------------------------");
                            System.out.println(KamyonCounter++ +". Kamyon:");
                            Envanter.araci_listele(tempList);
                            tempList=new Envanter[0];
                            i--;
                            continue;
                        }
                    }
                }
                else{//Tira yuklemeye calisiyorsam;
                    if(rnAgirlik+maddeler[i].getAgirlik()<=Tir.getMaxAgirlik()){//Tir tasiyabilip;
                        if(rnHacim+maddeler[i].getHacim()<=Tir.getHacimKasa()){//Icine sigiyorsa ekle.
                            rnAgirlik+=maddeler[i].getAgirlik();
                            rnHacim+=maddeler[i].getHacim();
                            tempList = Envanter.araca_ekle(tempList,maddeler[i]);
                        }
                        else{//Icine sigmiyorsa ileride eslesme ara.
                            for (int j = i; j < maddeler.length; j++) {
                                if (maddeler[j].getAgirlik() + rnAgirlik <= Tir.getMaxAgirlik() && maddeler[j].getHacim() + rnHacim <= Tir.getHacimKasa()) {//Ideal bir eslesme bulursa icine ekle.
                                    tempList=Envanter.araca_ekle(tempList, maddeler[j]);
                                    rnAgirlik += maddeler[j].getAgirlik();
                                    rnHacim += maddeler[j].getHacim();
                                    maddeler = Envanter.silYuk(maddeler, j);
                                    break;
                                }
                                if(maddeler.length-1==j){//Ileride de ideal eslesme bulamazsa tiri yolla.
                                    rnHacim=0;
                                    rnAgirlik=0;
                                    minPrice+=Tir.getFiyat();
                                    System.out.println("-----------------------------------------");
                                    System.out.println(TirCounter++ +". Tir:");
                                    Envanter.araci_listele(tempList);
                                    tempList=new Envanter[0];
                                    rnArac="Kamyon";
                                }
                            }
                            i--;
                            continue;
                        }
                    }
                    else {//Tir tasiyamiyorsa tiri yolla. Sistemi tekrar baslat.
                        rnHacim=0;
                        rnAgirlik=0;
                        minPrice+=Tir.getFiyat();
                        System.out.println("-----------------------------------------");
                        System.out.println(TirCounter++ +". Tir:");
                        Envanter.araci_listele(tempList);
                        tempList=new Envanter[0];
                        rnArac="Kamyon";
                        i--;
                        continue;
                    }
                }
                if(i==maddeler.length-1){//Baska eleman kalmadiysa araci yolla.
                    if(rnArac.equals("Kamyon")){
                        minPrice+=Kamyon.getFiyat();
                        System.out.println("-----------------------------------------");
                        System.out.println(KamyonCounter++ +". Kamyon:");
                        Envanter.araci_listele(tempList);
                        tempList=new Envanter[0];
                        rnHacim=0;
                        rnAgirlik=0;
                    }
                    if(rnArac.equals("Tir")){
                        minPrice+=Tir.getFiyat();
                        System.out.println("-----------------------------------------");
                        System.out.println(TirCounter++ +". Tir:");
                        Envanter.araci_listele(tempList);
                        tempList=new Envanter[0];
                        rnHacim=0;
                        rnAgirlik=0;
                        rnArac="Kamyon";
                    }
                }
        }
        for (int i = 0; i < sivimaddeler.length; i++) {
            if(rnArac.equals("Kamyon")){ //Kamyona yuklemeye calisiyorsam;
                if(rnAgirlik+sivimaddeler[i].getAgirlik()<=Kamyon.getMaxAgirlik()){//Kamyonun kasasi kaldirabiliyorsa;
                    if(rnHacim+sivimaddeler[i].getHacim()<=Kamyon.getHacimKasa()){//Icine sigiyor ise icine koy.
                        rnAgirlik+=sivimaddeler[i].getAgirlik();
                        rnHacim+=sivimaddeler[i].getHacim();
                        tempList = Envanter.araca_ekle(tempList,sivimaddeler[i]);
                    }
                    else{//Icine sigmiyor ise ilerideki elemanlari kontrol et.
                        for (int j = i; j < sivimaddeler.length; j++) {
                            if(sivimaddeler[j].getAgirlik()+rnAgirlik<=Kamyon.getMaxAgirlik()&&sivimaddeler[j].getHacim()+rnHacim<=Kamyon.getHacimKasa()){//Ideal bir eslesme bulursa icine ekle.
                                tempList = Envanter.araca_ekle(tempList,sivimaddeler[j]);
                                rnAgirlik+=sivimaddeler[j].getAgirlik();
                                rnHacim+=sivimaddeler[j].getHacim();
                                sivimaddeler = Envanter.silYuk(sivimaddeler,j);
                                break;
                            }

                            if(sivimaddeler.length-1==j){//Ileride de ideal eslesme bulamazsa tir yapmayi dusunup eslesme ara.
                                for (int k = i; k < sivimaddeler.length; k++) {
                                    if(sivimaddeler[k].getAgirlik()+rnAgirlik<=Tir.getMaxAgirlik()&&sivimaddeler[k].getHacim()+rnHacim<=Tir.getHacimKasa()){//Ideal bir eslesme bulursa icine ekle.
                                        rnArac="Tir";
                                        tempList = Envanter.araca_ekle(tempList,sivimaddeler[k]);
                                        rnAgirlik+=sivimaddeler[k].getAgirlik();
                                        rnHacim+=sivimaddeler[k].getHacim();
                                        sivimaddeler = Envanter.silYuk(sivimaddeler,k);
                                        break;
                                    }
                                    if(sivimaddeler.length-1==k){//Ileride de ideal eslesme bulamazsa kamyonu yolla.
                                        rnHacim=0;
                                        rnAgirlik=0;
                                        minPrice+=Kamyon.getFiyat();
                                        System.out.println("-----------------------------------------");
                                        System.out.println(KamyonCounter++ +". Kamyon:");
                                        Envanter.araci_listele(tempList);
                                        tempList=new Envanter[0];
                                    }
                                }
                            }
                        }
                        i--;
                        continue;
                    }
                }
                else{//Kamyonun kasasi kaldiramiyorsa;
                    if(rnAgirlik+sivimaddeler[i].getAgirlik()<=Tir.getMaxAgirlik()){//Tirin kasasi kaldirabiliyorsa;
                        if(rnHacim+sivimaddeler[i].getHacim()<=Tir.getHacimKasa()){//Icine sigiyor ise tira buyutup icine koy.
                            rnArac="Tir";
                            rnAgirlik+=sivimaddeler[i].getAgirlik();
                            rnHacim+=sivimaddeler[i].getHacim();
                            tempList = Envanter.araca_ekle(tempList,sivimaddeler[i]);
                        }
                        else{//Icine sigmiyor ise sonraki elemanlara bak.
                            for (int j = i; j < sivimaddeler.length; j++) {
                                if(sivimaddeler[j].getAgirlik()+rnAgirlik<=Tir.getMaxAgirlik()&&sivimaddeler[j].getHacim()+rnHacim<=Tir.getHacimKasa()){//Ideal bir eslesme bulursa icine ekle.
                                    rnArac="Tir";
                                    tempList = Envanter.araca_ekle(tempList,sivimaddeler[j]);
                                    rnAgirlik+=sivimaddeler[j].getAgirlik();
                                    rnHacim+=sivimaddeler[j].getHacim();
                                    sivimaddeler = Envanter.silYuk(sivimaddeler,j);
                                    break;
                                }
                                if(sivimaddeler.length-1==j){//Ileride de ideal eslesme bulamazsa
                                    rnHacim=0;
                                    rnAgirlik=0;
                                    minPrice+=Kamyon.getFiyat();
                                    System.out.println("-----------------------------------------");
                                    System.out.println(KamyonCounter++ +". Kamyon:");
                                    Envanter.araci_listele(tempList);
                                    tempList=new Envanter[0];
                                }
                            }
                            i--;
                            continue;
                        }
                    }
                    else{//Tirin kasasi da kaldiramiyorsa kamyonu yolla ve yeni arac getir.
                        rnHacim=0;
                        rnAgirlik=0;
                        minPrice+=Kamyon.getFiyat();
                        System.out.println("-----------------------------------------");
                        System.out.println(KamyonCounter++ +". Kamyon:");
                        Envanter.araci_listele(tempList);
                        tempList=new Envanter[0];
                        i--;
                        continue;
                    }
                }
            }
            else{//Tira yuklemeye calisiyorsam;
                if(rnAgirlik+sivimaddeler[i].getAgirlik()<=Tir.getMaxAgirlik()){//Tir tasiyabilip;
                    if(rnHacim+sivimaddeler[i].getHacim()<=Tir.getHacimKasa()){//Icine sigiyorsa ekle.
                        rnAgirlik+=sivimaddeler[i].getAgirlik();
                        rnHacim+=sivimaddeler[i].getHacim();
                        tempList = Envanter.araca_ekle(tempList,sivimaddeler[i]);
                    }
                    else{//Icine sigmiyorsa ileride eslesme ara.
                        for (int j = i; j < sivimaddeler.length; j++) {
                            if (sivimaddeler[j].getAgirlik() + rnAgirlik <= Tir.getMaxAgirlik() && sivimaddeler[j].getHacim() + rnHacim <= Tir.getHacimKasa()) {//Ideal bir eslesme bulursa icine ekle.
                                tempList = Envanter.araca_ekle(tempList, sivimaddeler[j]);
                                rnAgirlik += sivimaddeler[j].getAgirlik();
                                rnHacim += sivimaddeler[j].getHacim();
                                sivimaddeler = Envanter.silYuk(sivimaddeler, j);
                                break;
                            }
                            if(sivimaddeler.length-1==j){//Ileride de ideal eslesme bulamazsa tiri yolla.
                                rnHacim=0;
                                rnAgirlik=0;
                                minPrice+=Tir.getFiyat();
                                System.out.println("-----------------------------------------");
                                System.out.println(TirCounter++ +". Tir:");
                                Envanter.araci_listele(tempList);
                                tempList=new Envanter[0];
                                rnArac="Kamyon";
                            }
                        }
                        i--;
                        continue;
                    }
                }
                else {//Tir tasiyamiyorsa tiri yolla. Sistemi tekrar baslat.
                    rnHacim=0;
                    rnAgirlik=0;
                    minPrice+=Tir.getFiyat();
                    System.out.println("-----------------------------------------");
                    System.out.println(TirCounter++ +". Tir:");
                    Envanter.araci_listele(tempList);
                    tempList=new Envanter[0];
                    rnArac="Kamyon";
                    i--;
                    continue;
                }
            }
            if(i==sivimaddeler.length-1){//Baska eleman kalmadiysa araci yolla.
                if(rnArac.equals("Kamyon")){
                    minPrice+=Kamyon.getFiyat();
                    System.out.println("-----------------------------------------");
                    System.out.println(KamyonCounter++ +". Kamyon:");
                    Envanter.araci_listele(tempList);
                }
                if(rnArac.equals("Tir")){
                    minPrice+=Tir.getFiyat();
                    System.out.println("-----------------------------------------");
                    System.out.println(TirCounter++ +". Tir:");
                    Envanter.araci_listele(tempList);
                }
            }
        }
        System.out.println("-----------------------------------------\n");
        System.out.println("///////////////////////////////////////////");
        System.out.println("Bu is icin "+--TirCounter+" adet tir ve "+--KamyonCounter+" adet kamyon gerekiyor.");
        System.out.println("Toplam maliyet ise : "+minPrice+" Turk Lirasi.");
        System.out.println("///////////////////////////////////////////");
    }
}
