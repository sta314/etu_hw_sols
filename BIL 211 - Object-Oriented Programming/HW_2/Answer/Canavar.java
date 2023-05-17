import java.io.Serializable;

public class Canavar implements Serializable {
    private int num;
    private int direction;
    private int x;
    private int y;
    public Canavar(){}
    public Canavar(int num, int x, int y){
        this.num=num;
        this.x=x;
        this.y=y;
        if(num%2==0)
            direction=1;
        else
            direction=-1;
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
}
