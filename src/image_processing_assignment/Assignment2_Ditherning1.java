import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Assignment2_Ditherning1 {
    public static void main(String[] args) {
        try{
            Scanner OBJECT = new Scanner(System.in);
            String img;
            System.out.println("Enter Image Name : "); 
            img = OBJECT.nextLine();
            FileInputStream myInputFile = new FileInputStream("image/"+img+".raw");
            FileOutputStream myOutputFile = new FileOutputStream("image/"+img+"_.raw");
            
            int value;
            int i=0;
            
            Scanner input_width = new Scanner(System.in);
            System.out.println("Enter Width : "); 
            int width = input_width.nextInt();
            
            Scanner input_height = new Scanner(System.in);
            System.out.println("Enter Width : "); 
            int height = input_height.nextInt();
            
            int[][] data = new int[width][height];
            int WIDTH = 0;
            int HEIGHT = 0;
            
            while((value = myInputFile.read())!=-1){
                if((i%width==0)&&(i!=0)){
                    WIDTH = 0;
                    HEIGHT++;                    
                }
                data[WIDTH][HEIGHT]  = value;
                WIDTH++;
                i++;
            }
            int j=0;
            for(int count = 0; count < height; count++){
                for(int num = 0; num < width; num++){
                    if(count%2==0){
                        if(j==0){
                           if(data[count][num]>=0){
                               data[count][num]=255;
                           }else{
                               data[count][num]=0;
                           }
                           j++;
                        }else{
                           j=0;
                           if(data[count][num]>128){
                               data[count][num]=255;
                           }else{
                               data[count][num]=0;
                           } 
                        }
                    }else{
                        if(j==0){
                           if(data[count][num]>192){
                               data[count][num]=255;
                           }else{
                               data[count][num]=0;
                           } 
                           j++;
                        }else{
                           if(data[count][num]>64){
                               data[count][num]=255;
                           }else{
                               data[count][num]=0;
                           } 
                           j=0;
                        }
                    } 

                }

            }
            
            for(int count = 0; count < height; count++){
                for(int num = 0; num < width; num++){

                      myOutputFile.write(data[count][num]);
                }
            }
            
            myInputFile.close();
            myOutputFile.close();
        }catch(IOException ex){
            System.out.println("File error, please check the file of image!!!");
        }
    }
}
