import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Assignment2_Ditherning2 {
    public static void main(String[] args) {
        try{
            Scanner OBJECT = new Scanner(System.in);
            String img;
            System.out.println("Enter Image Name : "); 
            img = OBJECT.nextLine();
            FileInputStream InputFile = new FileInputStream("image/"+img+".raw");
            FileOutputStream OutputFile = new FileOutputStream("image/"+img+"_D2.raw");
            
            int value;
            int i=0;
            
            Scanner WIDTH = new Scanner(System.in);
            System.out.println("Enter Width : "); 
            int width = WIDTH.nextInt();
            
            Scanner HEIGHT = new Scanner(System.in);
            System.out.println("Enter Height : "); 
            int height = HEIGHT.nextInt();
            
            int[][] data = new int[height][width];
            int [][] data2 = { { 0, 128, 32, 160 }, { 192, 64, 224, 96 },{ 48, 176, 16, 144 },{240,112,208,80} };
              
            int start_Width = 0;
            int start_Height = 0;
              
            while((value = InputFile.read())!=-1){
                if((i%width==0)&&(i!=0)){
                    start_Height++;
                    start_Width = 0;
                }
                data[start_Height][start_Width]  = value;
                start_Width++;
                i++;
            }
            
            int W=0;
            int H=0;
            
            for(int count = 0; count < height; count++){
                for(int num = 0; num < width; num++){
                    if(data[count][num]>data2[H][W]){
                        data[count][num]=255;
                    }else{
                        data[count][num]=0;
                    }
                    if(W!=3){
                        W++;
                    }else{
                        W=0;
                    }
                }
                if(H!=3){
                    H++;
                }else{
                    H=0;
                }

            }
            
            for(int count = 0; count < height; count++){
                for(int num = 0; num < width; num++){

                      OutputFile.write(data[count][num]);
                }

            }
            InputFile.close();
            OutputFile.close();
            
        }catch(IOException ex){
            System.out.println("File error, please check the file of image!!!");
        }
    }
}


