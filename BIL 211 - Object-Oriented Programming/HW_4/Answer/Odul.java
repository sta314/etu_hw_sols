import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Odul{ //Yenip yenmedigini kontrol ederken bu classa ait isEaten metodunda thread kullandım.
    private int x;
    private int y;
    private final int puan = 5; // Her odul kac puan?
    boolean isExist;

    public static final int odulWidth=25;
    public static final int odulHeight=25;

    public Odul(){}
    public Odul(int x,int y){
        this.x=x;
        this.y=y;
        isExist=true;
    }

    public void printOdul(Graphics g){
        if(isExist) {
            g.setColor(Color.green);
            g.fillRect(x - (odulWidth / 2), y - (odulHeight / 2), odulWidth, odulHeight);
        }
    }

    public void isEaten(Pacman o){
        int oyuncuX = o.getX();
        int oyuncuY = o.getY();
        //Optimizasyon icin thread kullandim.
        Thread temp = new Thread(()->{
            Ellipse2D oyuncuElips = new Ellipse2D.Double(o.getX()-o.pacmanWidth /2,o.getY()-o.pacmanHeight /2,o.pacmanWidth,o.pacmanHeight);
            if(oyuncuElips.contains(this.getX()-odulWidth/2,this.getY()-odulHeight/2,odulWidth,odulHeight)){
                this.isExist=false;
                synchronized (mainpanel.lock) {
                    o.setPuan(o.getPuan()+puan);
                }
                mainpanel.window.repaint();
            }
        });
        temp.setDaemon(true);
        temp.start();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }


}
