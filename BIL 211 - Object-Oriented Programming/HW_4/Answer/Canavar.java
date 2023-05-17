import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Canavar extends Thread{
    private int num;
    private int direction;
    private int x;
    private int y;
    private int tur;
    private int x_speed;
    private int y_speed;
    private int cooldown;

    protected static final int canavarWidth=50;
    protected static final int canavarHeight=50;

    //Asagidaki tanimlar {yavas,orta,hizli} sirasi kullanir.

    protected static final int[] speeds = {5,5,10};//turlerin hizlari hizlari (px/cd).
    private static final int[] cooldowns = {400,200,100};//turlerinin cooldownlari.
    private static final Color[] horizontalColors = {new Color(0,0,153),Color.blue,new Color(51,204,255)};//yatay hareket edenlerin renkleri.
    private static final Color[] verticalColors = {new Color(102,0,153),Color.red,Color.pink};//dikey hareket edenlerin renkleri


    @Override
    public void run() {
        while (mainpanel.isRunning) {
            try {
                Thread.sleep(cooldown);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
            if(mainpanel.isRunning) {
                while(!isMovable(x_speed*direction,y_speed*direction)||(!((this.x + x_speed * direction >= this.getCanavarWidth() / 2) && (this.x + x_speed * direction <= mainpanel.getPanelWidth() - this.getCanavarWidth() / 2) && (this.y + y_speed * direction >= this.getCanavarHeight() / 2) && (this.y + y_speed * direction <= mainpanel.getPanelHeight() - this.getCanavarHeight() / 2))))
                    direction *= -1; //Duvara girecekse veya bir canavarla carpisacaksa geri donder.
                this.x += x_speed * direction;
                this.y += y_speed * direction;
                mainpanel.window.repaint();
                if(isIntersects(Oyun.pacman.getX(), Oyun.pacman.getY(), Oyun.pacman.pacmanWidth, Oyun.pacman.pacmanHeight))
                    mainpanel.endGame(false);
            }
        }
    }

    public Canavar(int num, int x, int y, int tur){
        this.num=num;
        this.x=x;
        this.y=y;
        this.tur=tur;
        this.direction=1;
        cooldown = cooldowns[tur];
        if(num%2==0){
            x_speed = speeds[tur];
            y_speed = 0;
        }
        else{
            x_speed = 0;
            y_speed = speeds[tur];
        }
    }
    public void printCanavar (Graphics g){
        if(num%2==0)
            g.setColor(horizontalColors[tur]);
        else
            g.setColor(verticalColors[tur]);
        g.fillOval(x-(canavarWidth/2),y-(canavarHeight/2),canavarWidth,canavarHeight);
    }
    public boolean isMovable(int plusX, int plusY){
        for (int i = 0; i < Oyun.canavars.length; i++)
            if(Oyun.canavars[i]!=this)
                if(isIntersects(Oyun.canavars[i].getX()-plusX, Oyun.canavars[i].getY()-plusY, Oyun.canavars[i].getCanavarWidth(), Oyun.canavars[i].getCanavarHeight()))
                    return false;
        return true;
    }
    public boolean isIntersects(int x,int y,int width, int height){
        Shape s1 = new Ellipse2D.Double(this.x,this.y,this.getCanavarWidth(),this.getCanavarHeight());
        Shape s2 = new Ellipse2D.Double(x,y,width,height);
        if(s1.getBounds().intersects(s2.getBounds())) { //Performans acisindan eger cevreleyen dikdortgen degiyorsa alanlari incele
            Area a1 = new Area(s1);
            a1.intersect(new Area(s2)); //Kaynak : https://stackoverflow.com/questions/15690846/java-collision-detection-between-two-shape-objects
            if(!a1.isEmpty())
                return true;
        }
        return false;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
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
    public int getCanavarWidth() {
        return canavarWidth;
    }

    public int getCanavarHeight() {
        return canavarHeight;
    }
}
