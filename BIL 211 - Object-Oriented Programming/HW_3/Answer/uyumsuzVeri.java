public class uyumsuzVeri extends Exception {
    Class type;
    uyumsuzVeri(Class type){
        this.type=type;
    }
    public String getMessage(){
        return "Yanlis veri girdiniz lutfen verilerin "+type.getName().replace("java.lang.","")+" turunde olup olmadigindan emin olun.";
    }
}
