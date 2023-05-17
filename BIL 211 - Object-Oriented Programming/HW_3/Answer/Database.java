import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Database<T>{
    public static void main(String[] args) {
        try{
            Database<String> test = new Database<>(String.class,"test");
            Database<String> deneme1 = new Database<>(String.class,"deneme1");
            Database<String> deneme2 = new Database<>(String.class,"deneme2");
            System.out.println("test veritabani:");
            test.display();
            System.out.println("where metot test -- test.where(“POSITION”,”C”)");
            test.where("POSITION","C").display();
            System.out.println("select metot test -- test.select({“POSITION”, “TEAM”})");
            test.select(new String[]{"POSITION", "TEAM"}).display();
            System.out.println("Buraya kadar olan testlerin ana veritabanini etkilemedigini gostermek amaciyla test veritabani:");
            test.display();
            System.out.println("add_column metot test -- test.add_column(“BirthState”, {“OH”, “NY”, “CA”, “CA”, “IL”})");
            test.add_column("BirthState",new String[]{"OH","NY","CA","CA","IL"});
            test.display();
            System.out.println("remove metot test -- test.remove(“POSITION”,”C”)");
            test.remove("POSITION","C");
            test.display();
            System.out.println("deneme1 veritabani:");
            deneme1.display();
            System.out.println("deneme2 veritabani:");
            deneme2.display();
            System.out.println("join metot test --  deneme1.join(deneme2, “ID”)");
            deneme1.join(deneme2,"ID").display();
            System.out.println("Islem sonunda ana veritabanlarinin etkilenmedigini gosterelim: ");
            System.out.println("deneme1 veritabani:");
            deneme1.display();
            System.out.println("deneme2 veritabani:");
            deneme2.display();
            System.out.println("Bir integer databasesi deneyelim:");
            Database<Integer> sayitablo = new Database<Integer>(Integer.class,"sayitablo");
            sayitablo.display();
            System.out.println("Son olarak da bir string veritabanini integermis gibi acmaya calisip exceptionu tutalim.");
            Database<Integer> hataligirdi = new Database<Integer>(Integer.class,"test");
        }
        catch(Exception e){
            System.out.println("--------------------------------------------");
            System.out.println(e.getMessage());
            System.out.println("--------------------------------------------");
        }
    }
    Class type;
    ArrayList<String> columns = new ArrayList<>();
    ArrayList<ArrayList<T>> db = new ArrayList<>();
    Database (Class veri_turunun_sinifi, String dosya_ismi) throws uyumsuzVeri, IOException{ //Uyumsuz veri varsa ayrintili bilgi iceren bir exception atar.
        String temp;
        int index=0;
        type = veri_turunun_sinifi;
        BufferedReader reader = new BufferedReader(new FileReader(dosya_ismi));
        columns=new ArrayList<>(Arrays.asList(reader.readLine().split(",")));
        while(true){
            String readedline = reader.readLine();
            if(readedline==null)
                break;
            String[] tempValues = readedline.split(",");
            db.add(new ArrayList<T>());
            for (int i = 0; i < tempValues.length; i++) {
                try {
                    if (getType() == 0)
                        db.get(index).add((T) tempValues[i]);
                    else if (getType() == 1)
                        db.get(index).add((T) Integer.valueOf(Integer.parseInt(tempValues[i])));
                    else if (getType() == 2)
                        db.get(index).add((T) Double.valueOf(Double.parseDouble(tempValues[i])));
                }
                catch(NumberFormatException e){
                    throw new uyumsuzVeri(type);
                }
            }
            index++;
        }
        reader.close();
    }
    Database (ArrayList<String> columns, ArrayList<ArrayList<T>> db){
        this.columns=columns;
        this.db=db;
    }
    int getType(){
        if(type== Double.class){
            return 2;
        }

        else if(type == Integer.class){
            return 1;
        }

        return 0;//if it is String
    }
    Database where(String column_name, T value){
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<ArrayList<T>> db = new ArrayList<>();
        this.columns.stream().forEach(columns::add);
        for (int i = 0; i < this.db.size(); i++)
            if(this.db.get(i).get(this.columns.indexOf(column_name)).equals(value))
                db.add(deepCopy(this.db.get(i)));
        return new Database(columns,db);
    }
    Database select(String[] column_names){
        ArrayList<String> columns = new ArrayList<>(Arrays.asList(column_names));
        ArrayList<ArrayList<T>> db = new ArrayList<>();
        for (int i = 0; i < this.db.size(); i++) {
            db.add(new ArrayList<>());
            for (int j = 0; j < columns.size(); j++)
                db.get(db.size()-1).add(this.db.get(i).get(this.columns.indexOf(columns.get(j))));
        }
        return new Database(columns,db);
    }
    void add_column(String label, T[] data){
        if(db.size()!=data.length){
            System.out.println("Bu data bu databaseye eklenemez.");
            return;
        }
        columns.add(label);
        int index=0;
        for (int i = 0; i < data.length; i++)
            db.get(index++).add(data[i]);
    }
    void remove(String column_name, T value){
        for (int i = 0; i < db.size(); i++)
            if(db.get(i).get(columns.indexOf(column_name)).equals(value))
                db.remove(i--);
    }
    Database join(Database<T> second, String column_name){
        ArrayList<String> columns = new ArrayList<>();
        ArrayList<ArrayList<T>> db = new ArrayList<>();
        if(this.columns.indexOf(column_name)==-1||second.columns.indexOf(column_name)==-1) {
            System.out.println("Bu iki database birlestirilemez!");
            return this;
        }
        for (int i = 0; i < this.columns.size(); i++)
            columns.add(this.columns.get(i));
        for (int i = 0; i < second.columns.size(); i++) {
            if (second.columns.get(i).equals(column_name))
                continue;
            if (columns.contains(second.columns.get(i)))
                columns.add(second.columns.get(i) + "2");
            else
                columns.add(second.columns.get(i));
        }
        for (int i = 0; i < this.db.size(); i++) {
            for (int j = 0; j < second.db.size(); j++) {
                if(this.db.get(i).get(this.columns.indexOf(column_name)).equals(second.db.get(j).get(second.columns.indexOf(column_name)))){
                    db.add(deepCopy(this.db.get(i)));
                    ArrayList<T> temp;
                    temp=deepCopy(second.db.get(j));
                    temp.remove(second.columns.indexOf(column_name));
                    for (int k = 0; k < temp.size(); k++) {
                        db.get(db.size()-1).add(temp.get(k));
                    }
                }
            }
        }
        return new Database(columns,db);
    }
    void display(){
        System.out.println("--------------------------------------------");
        columns.stream().forEach(s->System.out.print(s+"\t"));
        System.out.println();
        db.stream().forEach(s->{s.stream().forEach(t -> System.out.print(t+"\t"));System.out.println();});
        System.out.println("--------------------------------------------");
    }
    ArrayList<T> deepCopy(ArrayList<T> toCopy){
        ArrayList<T> temp = new ArrayList<>();
        toCopy.stream().forEach(temp::add);
        return temp;
    }
}