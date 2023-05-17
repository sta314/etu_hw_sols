import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class mainpanel extends JPanel implements KeyListener {
    static JFrame window = new JFrame();
    static boolean isRunning = true;
    protected static final Object lock = new Object();

    private static final int panelWidth = 1000; //1920x1080 cozunurlukteki bir ekranda sorun oldugundan yuksekligi 800 yaptim.
    private static final int panelHeight = 800; //Eger cok buyutulurse program calisirken otomatik olarak kuculebileceginden istenmeyen sonuclar dogurabilir.


    public mainpanel(){
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addKeyListener(this);
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        window.add(this);
        window.pack();
        window.setResizable(false);
        this.setBackground(Color.black);
        window.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setFont(g.getFont().deriveFont(40F));
        g.drawString("F", 10, 40);

        for (int i = 0; i < Oyun.oduls.length; i++) {
            Oyun.oduls[i].printOdul(g);
        }

        Oyun.pacman.printPacman(g);

        for (int i = 0; i < Oyun.canavars.length; i++) {
            Oyun.canavars[i].printCanavar(g);
        }

    }

    public static void endGame(boolean isWin){
        isRunning=false;
        UIManager.put("OptionPane.okButtonText","Tamam");
        if(isWin)
            JOptionPane.showConfirmDialog(null, "Oyunu Kazandiniz.\nPuaniniz : " + Oyun.pacman.getPuan(), "Oyun Bitti!", JOptionPane.DEFAULT_OPTION);
        else
            JOptionPane.showConfirmDialog(null, "Oyunu Kaybettiniz..\nPuaniniz : " + Oyun.pacman.getPuan(), "Oyun Bitti!", JOptionPane.DEFAULT_OPTION);
        System.exit(0);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_UP)
        {
            if(Oyun.pacman.getY()-Oyun.pacman.getSpeed()>=Oyun.pacman.pacmanHeight /2)
                Oyun.pacman.setY(Oyun.pacman.getY()-Oyun.pacman.getSpeed());
            repaint();
            Oyun.pacman.checkPacman();
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN)
        {
            if(Oyun.pacman.getY()+ Oyun.pacman.getSpeed()<=getPanelHeight()-Oyun.pacman.pacmanHeight /2)
                Oyun.pacman.setY(Oyun.pacman.getY()+Oyun.pacman.getSpeed());
            repaint();
            Oyun.pacman.checkPacman();
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if(Oyun.pacman.getX()+Oyun.pacman.getSpeed()<=getPanelWidth()-Oyun.pacman.pacmanWidth /2)
                Oyun.pacman.setX(Oyun.pacman.getX()+Oyun.pacman.getSpeed());
            repaint();
            Oyun.pacman.checkPacman();
        }
        else if(keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if(Oyun.pacman.getX()-Oyun.pacman.getSpeed()>=Oyun.pacman.pacmanWidth /2)
                Oyun.pacman.setX(Oyun.pacman.getX()-Oyun.pacman.getSpeed());
            repaint();
            Oyun.pacman.checkPacman();
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    public static void main(String[] args) {
        Oyun.masaOlustur();
        new mainpanel();
    }


    public static int getPanelWidth(){
        return panelWidth;
    }

    public static int getPanelHeight(){
        return panelHeight;
    }
}
