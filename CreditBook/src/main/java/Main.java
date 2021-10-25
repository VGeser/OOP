import ru.nsu.fit.lab4.Subject;

public class Main {
    public static void main(String[] args){
        Subject history = new Subject("History", new byte[]{1});
        history.setGrades(new byte[]{5,0,0,0,0,0,0,0});
        System.out.println(history.getName());
        for (byte g: history.getGrades()) {
            if(g!=0)System.out.println(g);
        }
    }
}
