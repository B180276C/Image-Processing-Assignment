import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Assignment3 {
    public static void main(String[] args) {
        try{
            Scanner OBJECT = new Scanner(System.in);
            
            String file;
            System.out.println("Enter name of image: "); 
            file = OBJECT.nextLine();
            
            FileInputStream File_Input = new FileInputStream("image/"+file+".raw");
            FileOutputStream File_Output = new FileOutputStream("image/"+file+"_second.raw");
            
            Scanner WIDTH = new Scanner(System.in);
            System.out.println("Enter Width : "); 
            int width = WIDTH.nextInt();
            
            Scanner HEIGH = new Scanner(System.in);
            System.out.println("Enter Height : "); 
            int height = HEIGH.nextInt();
            
            int value;
            int A=0;
            int sum = 0;
            double nor  = 0.0;
            int mutiple = 0;
            int data[]  = new int [256];
            int image[] = new int [width*height];
            int number[]   = new int [256];
            int pixel[] = new int [256];
            
            
            while((value = File_Input.read())!=-1){
                image[A] = value;
                data[value] = data[value]+1;    
                A++;
            }

            for(int I=0;I<256;I++){
                sum  += data[I];
                nor = ((double)sum/A);
                mutiple = (int) Math.round(nor*255);
                number[I] = mutiple;
                
                if(I!=0){
                    if(number[I]==number[I-1]){
                        pixel[mutiple] = pixel[mutiple]+data[I];
                    }else{
                        pixel[mutiple] = data[I];
                    }
                }else{
                    pixel[mutiple] = data[I];
                }

            }
            for(int J=0;J<A;J++){
                  image[J] = number[image[J]];
                  File_Output.write(image[J]);
            }
            
            File_Input.close();
            File_Output.close();
            
        }catch(IOException ex){
            System.out.println("File error, please check the file of image!!!");
        }
    }
}
