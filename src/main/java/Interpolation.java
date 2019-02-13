import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

@Data
public class Interpolation{
    private int power=4;
    private double x[]; //Значение аргументов функции f()
    private double y[]; //Значение функции в этих точках
    private double xx;  //Значение аргумента при котором вычисляется интерполяционное значение функции
    private double yy;  //Полученное интерполяционное значение
    private double yy_3; //Интерполяционное значение для полинома 3 степени
    Vector<Double> NearX, NearY; //Ближайшие значения X и f(X) к XX

    Interpolation() throws IOException {
        x = new double[power];
        y = new double[power];
        NearX = new Vector<Double>();
        NearY = new Vector<Double>();
        loadData();
    }

    //Чтение из файла
    private boolean loadData() throws IOException {
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

        for (int i = 0; i < power; ++i)
            for (int j = i+1; j < power; ++j)
                if (x[i] == x[j])
                    return false;
        return true;
    }

    //Очистка вектора близких точек
    private void setNearNull(){
        if(NearX.isEmpty() == false)
         NearX.removeAllElements();
        if(NearY.isEmpty() == false)
        NearY.removeAllElements();
    }

    //Поиск ближайшех к XX точек
    private void findNear(int p){
        setNearNull();
        int start = 0;

        if(xx >= x[power-2]) { //Правая граница
            start = power - p;
        }
        else{
            if(xx > x[1]){ //Если не левая
                for(int i=0; i< power-1;i++) //Случай если мы за 2(или больше) точками от границ
                {
                    if(x[i] <= xx && x[i+1] >= xx) //Находим точки между которыми расположен XX
                        if(xx-x[i] <= x[i+1] - xx || p==4) //Проверяем X ближе к левой, иначе к правой
                        {
                            start = i-1;
                        }
                    else{
                        start = i;
                        }

                }
            }
        }
        for(int j=start; j<=start + p -1;j++){
            NearX.add(x[j]);
            NearY.add(y[j]);
        }
    }

    private double A(int i){
        double result = 1;
        for (int j=0; j<NearX.size();j++ ){
            if(i!=j){
                result *= x[i]-x[j];
            }
        }
        return 1/result;
    }

    private double Lagrange(){
        double res_chisl =0, res_zn=0,P;
        for(int i=0; i<NearX.size();i++){
            P=A(i);
            res_chisl +=NearY.get(i)*P / (xx-x[i]);
            res_zn+=P/(xx-x[i]);
        }
        return res_chisl/res_zn;
    }

    public void calculation() throws IOException {
        if(loadData() == true)
        {
            findNear(3);
            yy = Lagrange();

            findNear(4);
            yy_3 = Lagrange();

            System.out.println("Для 3 точек: " + yy);
            System.out.println("Для 4 точек: " + yy_3);
        }
        else
        {
            System.out.println("Ошибка! Значения повторяются");
        }

    }
}
