import java.util.Random;
public class Oyun {

    private static final int amountOfCanavars = 10;
    private static final int amountOfOduls = 5;

    static Canavar[] canavars = new Canavar[amountOfCanavars];
    static Odul[] oduls = new Odul[amountOfOduls];
    static Pacman pacman;

    public static void masaOlustur(){ //Default hali random sekilde olusturan metod.
        pacman = new Pacman();
        Random r = new Random();
        for (int i = 0; i < amountOfCanavars; i++) {
            int x=0;
            int y=0;
            boolean found = false;
            while(!found){
                x = r.nextInt((mainpanel.getPanelWidth()-Canavar.canavarWidth/2)-(Canavar.canavarWidth/2))+Canavar.canavarWidth/2;
                y = r.nextInt((mainpanel.getPanelHeight()-Canavar.canavarHeight/2)-(Canavar.canavarHeight/2))+Canavar.canavarHeight/2;
                if(isSuitable(x,y))
                    found=true;
            }
            canavars[i]=new Canavar(i,x,y,r.nextInt(3));
            canavars[i].setDaemon(true);
            canavars[i].start();
        }
        for (int i = 0; i < amountOfOduls; i++) {
            int x=0;
            int y=0;
            x = pacman.pacmanWidth /2 + r.nextInt(mainpanel.getPanelWidth()/(pacman.speed)-1)*(pacman.speed);
            y = pacman.pacmanHeight /2 + r.nextInt(mainpanel.getPanelHeight()/(pacman.speed)-1)*(pacman.speed);
            if(x==mainpanel.getPanelWidth()- pacman.pacmanWidth /2&&y==mainpanel.getPanelHeight()- pacman.pacmanHeight /2)
                y = pacman.pacmanHeight /2 + r.nextInt(mainpanel.getPanelHeight()/(pacman.speed)-2)*(pacman.speed);
            oduls[i]=new Odul(x,y);
        }
    }

    public static boolean isSuitable(int x,int y){ // Canavarlarin ust uste veya oyuncuya cok yakin olmasini engelleyen metod.
        if(Math.pow(pacman.getX()-x,2)+Math.pow(pacman.getY()-y,2)<Math.pow(Math.max(Canavar.canavarHeight,Canavar.canavarWidth)+Math.max(Math.max(Canavar.speeds[0],Canavar.speeds[1]),Canavar.speeds[2]),2))
            return false; //Bu iki satir oyuncunun oyun basinda direkt kaybetmesini engeller, ona bir tepki sansi sunar.
        for (int i = 0; i < amountOfCanavars; i++) {
            if(canavars[i]==null)
                continue; // can use break
            if(Math.pow(canavars[i].getX()-x,2)/Math.pow(canavars[i].getCanavarWidth(),2)+Math.pow(canavars[i].getY()-y,2)/Math.pow(canavars[i].getCanavarHeight(),2)<=1) //Elips denklemi
                return false;
        }
        return true;
    }
}