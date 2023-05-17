import java.awt.*;

public class Pacman{
    private int x;
    private int y;
    private int puan;

    protected final int speed = 50; // Oyuncu hizi.
    private final int saniyeBasiPuan = 1;
    protected final int pacmanWidth =50;
    protected final int pacmanHeight =50;

    public void printPacman(Graphics g){
        g.setColor(Color.yellow);
        g.fillOval(x-(pacmanWidth /2),y-(pacmanHeight /2), pacmanWidth, pacmanHeight);
    }

    public int getPuan() {
        return puan;
    }

    public void setPuan(int puan) {
        this.puan = puan;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed(){return speed;}

    public Pacman() {
        x=mainpanel.getPanelWidth()- pacmanWidth /2;
        y=mainpanel.getPanelHeight()- pacmanHeight /2;
        puan=0;
        Thread autoPoint = new Thread(()->{
            while(mainpanel.isRunning){
                try{
                    Thread.sleep(1000);
                }
                catch(InterruptedException e){
                    System.out.println("Thread interrupted.");
                }
                if(mainpanel.isRunning) {
                    synchronized (mainpanel.lock) {
                        puan+=saniyeBasiPuan;
                    }
                }
            }
        });
        autoPoint.setDaemon(true);
        autoPoint.start();
    }

    public void checkPacman(){
        for (int i = 0; i < Oyun.oduls.length; i++)
            if(Oyun.oduls[i].isExist)
                Oyun.oduls[i].isEaten(this);
        for (int i = 0; i < Oyun.canavars.length; i++)
            if(Oyun.canavars[i].isIntersects(x,y, pacmanWidth, pacmanHeight)){
                mainpanel.endGame(false);
                return;
            }
        if(this.getX()==this.pacmanWidth /2&&this.getY()==this.pacmanHeight /2) {
            mainpanel.endGame(true);
        }
    }

}
