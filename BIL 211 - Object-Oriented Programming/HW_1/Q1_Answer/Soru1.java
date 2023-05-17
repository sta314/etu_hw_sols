import java.util.Scanner;
public class Soru1 {
    public static void main(String[] args) {
        int[][][] arr = new int[5][5][5];
        arr[0]= new int[][]{
                {5, 0, 3, 0, 0},
                {4, 0, 0, 3, 0},
                {0, 3, 0, 0, 0},
                {0, 0, 4, 5, 2},
                {1, 5, 0, 0, 0},
        };
        arr[1]= new int[][]{
                {0, 0, 0, 4, 0},
                {3, 0, 5, 0, 2},
                {0, 0, 0, 0, 0},
                {0, 3, 1, 0, 4},
                {2, 0, 0, 3, 5},
        };
        arr[2]= new int[][]{
                {0, 3, 5, 1, 0},
                {2, 0, 0, 4, 0},
                {0, 1, 0, 0, 0},
                {0, 4, 2, 0, 5},
                {3, 0, 0, 0, 0},
        };
        arr[3]= new int[][]{
                {0, 1, 0, 3, 0},
                {0, 3, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {4, 0, 0, 0, 3},
                {0, 4, 0, 2, 0},
        };
        arr[4]= new int[][]{
            {3, 0, 1, 5, 4},
            {5, 0, 0, 0, 3},
            {1, 4, 2, 0, 5},
            {0, 0, 3, 0, 1},
            {4, 0, 0, 0, 2},
        };
        kupu_doldur(arr);
    }

    public static void kupu_doldur(int[][][] kup){
        if (fillCube(kup))
            printKup(kup);
        else
            System.out.println("Bu kup verilen girdiler ile doldurulamaz.");
    }

    public static boolean fillCube(int[][][] kup){
        int x = -1;
        int y = -1;
        int z = -1;
        //Base case
        boolean devam = false;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    if(kup[i][j][k]==0) {
                        devam = true;
                        x=i;
                        y=j;
                        z=k;
                    }
                }
            }
        }
        if(!devam)
            return true;
        //

        for (int i = 1; i <= 5; i++) {
            if(toAssign(kup,x,y,z,i)) {
                kup[x][y][z] = i;
                if (fillCube(kup))
                    return true;
                else {
                    kup[x][y][z] = 0;
                }
            }
        }
        return false;
    }
    public static void printKup(int[][][] kup){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    System.out.print(kup[i][j][k]+" ");
                }
                System.out.println();
            }
            System.out.print("---------------\n");
        }
    }
    public static boolean toAssign(int[][][] arr, int x, int y, int z,int num){
        if (!check_x_axis(arr,y,z,num)&&!check_y_axis(arr[x],z,num)&&!check_z_axis(arr[x][y],num))
            return true;
        return false;
    }
    public static boolean check_x_axis(int[][][] xaxis, int y, int z ,int num) {
        for (int i = 0; i < 5; i++) {
            if(xaxis[i][y][z]==num)
                return true;
        }
        return false;
    }

    public static boolean check_y_axis(int[][] yaxis, int z ,int num) {
        for (int i = 0; i < 5; i++) {
            if(yaxis[i][z]==num)
                return true;
        }
        return false;
    }
    public static boolean check_z_axis(int[] zaxis, int num) {
        for (int i = 0; i < 5; i++) {
            if(zaxis[i]==num)
                return true;
        }
        return false;
    }
}
