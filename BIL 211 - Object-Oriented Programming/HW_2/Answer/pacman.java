import java.io.*;
import java.util.Scanner;
public class pacman{
    public static void main(String[] args) {
        try {
            if (args[0].equals("-l"))
                masaBinaryLoad(args[1]);
            else if (args[0].equals("-L"))
                masaTextLoad(args[1]);
        }
        catch(ArrayIndexOutOfBoundsException e){}
        if(oyuncu==null)
            masaOlustur();
        oyunBaslat();
    }
    static Canavar[] canavars = new Canavar[10];
    static Odul[] oduls = new Odul[10];
    static Oyuncu oyuncu;
    static boolean masadevam=true;
    static String errorMsg="";
    public static void oyunBaslat(){
        Scanner sc = new Scanner(System.in);
        while(masadevam){
            try{
                masaBas();
                char durum = sc.next().charAt(0);
                sc.nextLine();
                if(durum=='E'){
                    System.out.println("Oyundan cikiliyor...");
                    return;
                }
                if(durum=='K'){
                    System.out.println("Oyunu kaydedeceginiz metin dosyasinin adini yaziniz: ");
                    String kayit = sc.nextLine();
                    masaSave(kayit, "txt");
                    System.out.println("Oyun bir metin dosyasina basariyla kaydedildi. Kaldiginiz yerden devam edebilirsiniz.");
                    continue;
                }
                if(durum=='k'){
                    System.out.println("Oyunu kaydedeceginiz binary dosyasinin adini yaziniz: ");
                    String kayit = sc.nextLine();
                    masaSave(kayit,"binary");
                    System.out.println("Oyun bir binary dosyasina basariyla kaydedildi. Kaldiginiz yerden devam edebilirsiniz.");
                    continue;
                }
                masaTurnOyuncu(durum);
                masaTurn();
            }
            catch(Wrongmove a){
                errorMsg="Boyle bir harekette bulunamazsiniz. Baska yone gidiniz.";
            }
        }
    }
    public static void masaOlustur(){
        oyuncu = new Oyuncu();
        for (int i = 0; i < 10; i++) {
            int x=0;
            int y=0;
            boolean found = false;
            while(!found){
                x = (int)(Math.random()*10);
                y = (int)(Math.random()*10);
                if(isSuitable(x,y).equals("bos"))
                    found=true;
            }
            canavars[i]=new Canavar(i,x,y);
        }
        for (int i = 0; i < 10; i++) {
            int x=0;
            int y=0;
            boolean found = false;
            while(!found){
                x = (int)(Math.random()*10);
                y = (int)(Math.random()*10);
                if(isSuitable(x,y).equals("bos"))
                    found=true;
            }
            oduls[i]=new Odul(x,y);
        }
    }
    public static void masaBas(){
        System.out.println("-----------------------");
        String[][] masa = new String[10][10];
        masa[oyuncu.getX()][oyuncu.getY()]="P";
        for (int i = 0; i < 10; i++) {
            masa[canavars[i].getX()][canavars[i].getY()]=""+canavars[i].getNum();
        }
        for (int i = 0; i < 10; i++) {
            if(oduls[i]==null)
                continue;
            if(masa[oduls[i].getX()][oduls[i].getY()]==null)
                masa[oduls[i].getX()][oduls[i].getY()]="o";
        }
        if(masa[0][9]==null)
            masa[0][9]="E";
        for (int j = 9; j >= 0; j--) {
            for (int i = 0; i < 10; i++) {
                if(masa[i][j]==null) {
                    System.out.print("_" + "|");
                    continue;
                }
                System.out.print(masa[i][j]+"|");
            }
            if(j==5){
                System.out.print("         "+errorMsg);
            }
            System.out.println();
        }
        System.out.println("-----------------------");
    }
    public static void masaTurn(){
        for (int i = 0; i < 10; i++) {
            if(i%2==0){
                String durum = isSuitable(canavars[i].getX()+canavars[i].getDirection(),canavars[i].getY());
                if(durum.equals("bos")||durum.equals("odul")||durum.equals("end"))
                    canavars[i].setX(canavars[i].getX()+canavars[i].getDirection());
                else if(durum.equals("oyuncu"))
                    masaLose();
                else {
                    canavars[i].setDirection(canavars[i].getDirection() * -1);
                    durum = isSuitable(canavars[i].getX() + canavars[i].getDirection(), canavars[i].getY());
                    if(durum.equals("bos")||durum.equals("odul")||durum.equals("end"))
                        canavars[i].setX(canavars[i].getX()+canavars[i].getDirection());
                    else if(durum.equals("oyuncu"))
                        masaLose();
                    else
                        canavars[i].setDirection(canavars[i].getDirection() * -1);
                }
            }
            else{
                String durum = isSuitable(canavars[i].getX(),canavars[i].getY()+canavars[i].getDirection());
                if(durum.equals("bos")||durum.equals("odul"))
                    canavars[i].setY(canavars[i].getY()+canavars[i].getDirection());
                else if(durum.equals("oyuncu"))
                    masaLose();
                else {
                    canavars[i].setDirection(canavars[i].getDirection() * -1);
                    durum = isSuitable(canavars[i].getX(),canavars[i].getY()+canavars[i].getDirection());
                    if(durum.equals("bos")||durum.equals("odul"))
                        canavars[i].setY(canavars[i].getY()+canavars[i].getDirection());
                    else if(durum.equals("oyuncu"))
                        masaLose();
                    else
                        canavars[i].setDirection(canavars[i].getDirection() * -1);
                }
            }
        }
    }
    public static void masaTurnOyuncu(char a) throws Wrongmove {
        int x=0;
        int y=0;
        switch (a) {
            case ('a'):
                x=-1;
                break;
            case('d'):
                x=1;
                break;
            case ('s'):
                y=-1;
                break;
            case('w'):
                y=1;
                break;
        }
        String situation = isSuitable(oyuncu.getX()+x,oyuncu.getY()+y);
        if(situation.equals("end"))
            masaWin();
        if(situation.equals("canavar"))
            masaLose();
        if(situation.equals("odul")) {
            oyuncu.setPuan(oyuncu.getPuan() + 5);
            for (int i = 0; i < 10; i++) {
                if(oduls[i]==null)
                    continue;
                if(oduls[i].getX()==oyuncu.getX()+x&&oduls[i].getY()==oyuncu.getY()+y)
                    oduls[i]=null;
            }
        }
        if(situation.equals("bos"))
            oyuncu.setPuan(oyuncu.getPuan()+1);
        if(situation.equals("duvar"))
            throw new Wrongmove();
        oyuncu.setX(oyuncu.getX()+x);
        oyuncu.setY(oyuncu.getY()+y);
        errorMsg="";
    }
    public static void masaLose(){
        System.out.println("Oyunu kaybettiniz, puaniniz "+oyuncu.getPuan());
        masadevam = false;
    }
    public static void masaWin(){
        System.out.println("Oyunu kazandiniz, puaniniz "+oyuncu.getPuan());
        masadevam = false;
    }
    public static void masaSave(String konum, String tur){
        try{
            if(tur.equals("txt")) {
                PrintWriter out = new PrintWriter(konum);
                out.println("---Oyuncu info---");
                out.println(oyuncu.getX());
                out.println(oyuncu.getY());
                out.println(oyuncu.getPuan());
                out.println("---Canavarlar info---");
                for (int i = 0; i < canavars.length; i++) {
                    out.println(canavars[i].getNum()+" "+canavars[i].getDirection()+" "+canavars[i].getX()+" "+canavars[i].getY());
                }
                out.println("---Oduller info---");
                for (int i = 0; i < oduls.length; i++) {
                    if(oduls[i]==null)
                        out.println("null");
                    else
                        out.println(oduls[i].getX()+" "+oduls[i].getY());
                }
                out.println("---Son---");
                out.flush();
                out.close();
            }
            if(tur.equals("binary")) {
                ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream(konum)
                );
                out.writeObject(oyuncu);
                out.writeObject(canavars);
                out.writeObject(oduls);
                out.flush();
                out.close();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static void masaTextLoad(String konum) {
        try {
            Scanner sc = new Scanner(new File(konum));
            //Oyuncu okuma kismi
            sc.nextLine();
            oyuncu = new Oyuncu();
            oyuncu.setX(Integer.parseInt(sc.next()));
            oyuncu.setY(Integer.parseInt(sc.next()));
            oyuncu.setPuan(Integer.parseInt(sc.next()));
            sc.nextLine();
            //Canavar okuma kismi
            sc.nextLine();
            for (int i = 0; i < canavars.length; i++) {
                canavars[i]=new Canavar();
                canavars[i].setNum(Integer.parseInt(sc.next()));
                canavars[i].setDirection(Integer.parseInt(sc.next()));
                canavars[i].setX(Integer.parseInt(sc.next()));
                canavars[i].setY(Integer.parseInt(sc.next()));
                sc.nextLine();
            }
            //Odul okuma kismi
            sc.nextLine();
            for (int i = 0; i < oduls.length; i++) {
                String temp = sc.next();
                if (temp.equals("null"))
                    oduls[i] = null;
                else {
                    oduls[i]=new Odul();
                    oduls[i].setX(Integer.parseInt(temp));
                    oduls[i].setY(Integer.parseInt(sc.next()));
                }
                sc.nextLine();
        }
            sc.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Dosya Bulunamadi");
            System.exit(0);
        }
    }
    public static void masaBinaryLoad(String konum){
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(konum));
            oyuncu=(Oyuncu)in.readObject();
            canavars=(Canavar[])in.readObject();
            oduls=(Odul [])in.readObject();
            in.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static String isSuitable(int x, int y){
        if(x<0||x>9||y<0||y>9)
            return "duvar";
        if(oyuncu.getX()==x&&oyuncu.getY()==y)
            return "oyuncu";
        for (int i = 0; i < 10; i++) {
            if(canavars[i]==null)
                continue;
            if(canavars[i].getX()==x&&canavars[i].getY()==y)
                return "canavar";
        }
        for (int i = 0; i < 10; i++) {
            if(oduls[i]==null)
                continue;
            if(oduls[i].getX()==x&&oduls[i].getY()==y)
                return "odul";
        }
        if((x==0&&y==9))
            return "end";
        return "bos";
    }
}