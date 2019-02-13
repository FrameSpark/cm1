import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@Data
public class Interpolation{
    private int power=4;
    private double x[];
    private double y[];
    private double xx;
    private double yy;
    private double yy_3;

    Interpolation(){
        x = new double[power];
        y = new double[power];
    }

    public void loadData() throws IOException {
        Scanner in = new Scanner(new File("src/main/Data.txt"));
        String line = in.nextLine();
        String words[] = line.split(" ");
        for(int i=0; i<words.length;i++){
            x[i]=Double.parseDouble(words[i]);
        }
        line = in.nextLine();
        words = line.split(" ");
        for(int i=0; i<words.length;i++){
            y[i]=Double.parseDouble(words[i]);
        }
        line = in.nextLine();
        xx = Double.parseDouble(line);

    }



}
