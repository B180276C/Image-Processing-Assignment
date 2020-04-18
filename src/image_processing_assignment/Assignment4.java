import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Assignment4 {
    public static void main(String[] args) {
        try{
            Scanner OBJECT = new Scanner(System.in);
            
            String file;
            System.out.println("Enter name of image : "); 
            file = OBJECT.nextLine();
            
            FileInputStream File_Input = new FileInputStream("image/"+file+".raw");
            FileOutputStream File_Output = new FileOutputStream("image/"+file+"_second.raw");
            
            int a=0;
            int value;
            
            Scanner input_width = new Scanner(System.in);
            System.out.println("Enter Width : "); 
            int width = input_width.nextInt();
            
            Scanner input_height = new Scanner(System.in);
            System.out.println("Enter Height : "); 
            int height = input_height.nextInt();
            
            int[][] data = new int[height][width];
            
            int HEIGHT = 0;
            int WIDTH  = 0;
            
            while((value = File_Input.read())!=-1){
                if((a%width==0)&&(a!=0)){
                    HEIGHT++;
                    WIDTH = 0;

                }
                data[HEIGHT][WIDTH]  = value;
                WIDTH++;
                a++;
            }
            
            for(int H=0;H<height;H++){
                for(int W=0;W<width;W++){
                    if((W==0)||(H==0)||(H==(height-1))||(W==(width-1))){
                        data[H][W]=255;
                    }
                }
            }
            
            int[][] convolution = { { -1, 0, 1 },{ -2, 0, 2 },{ -1, 0, 1 } }; 
            int[][] f_data = new int[height][width];
            int sum = 0;
            
            for(int y=1;y<(height-2);y++){
                for(int x=1;x<(width-2);x++){
                    sum =  ((convolution[0][0]*data[y+1][x+1])+
                            (convolution[1][0]*data[y+1][x])+
                            (convolution[2][0]*data[y+1][x-1])+
                            (convolution[0][1]*data[y][x+1])+
                            (convolution[1][1]*data[y][x])+
                            (convolution[2][1]*data[y][x-1])+
                            (convolution[0][2]*data[y-1][x+1])+
                            (convolution[1][2]*data[y-1][x])+
                            (convolution[2][2]*data[y-1][x-1]));

                    if(sum>255){
                        f_data[y][x] = 255;
                    }else if(sum<0){
                        f_data[y][x] = 0;
                    }else{
                        f_data[y][x] = sum;
                    }
                }

            }
            
            for(int H=0;H<height;H++){
                for(int W=0;W<width;W++){

                        File_Output.write(f_data[H][W]);
                }

            }
            
            File_Input.close();
            File_Output.close();
            
        }catch(IOException ex){
            System.out.println("File error, please check the file of image!!!");
        }
    }
}
