import javax.swing.*;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Assignment5_HighPass {
    
    static int width;
    static int height;
    final static String fileName = "shape";
    
    final static int[][] kernel = {

        {-1,-1,-1},
        {-1,8,-1},
        {-1,-1,-1}
    };
    
    static int[][] ArrayImage;
    static int[][] Result;
    
    public static void main(String[] args) {
    
        width = Integer.parseInt(JOptionPane.showInputDialog("Enter width of image(Image: "+fileName+")"));
        height = Integer.parseInt(JOptionPane.showInputDialog("Enter height of image(Image: "+fileName+")"));

        System.out.println("Width: " + width);
        System.out.println("Height: " + height);
        
        ArrayImage = new int[height][width];
        Result     = new int[height][width];
        
        if (readImage()) {

            doConvolution(); 
            save();
        }
    }
    
    public static void save() {
        try {
            File f = new File("image/" + fileName + "_highpass.raw");
            FileOutputStream myOutputFile = new FileOutputStream(f, false);

            for (int H = 0; H < height; H++) {
                for (int W = 0; W < width; W++) {
                    myOutputFile.write(Result[H][W]);
                }
            }
            myOutputFile.close();
            System.out.println("High pass filter is success to created..");
        } catch (IOException ex) {
            System.out.println("File image error, please try again.");
        }
    }
    
    public static boolean readImage() {
        boolean valid = true;
        try {
            FileInputStream myInputFile = new FileInputStream("image/"+fileName+".raw");
            int rawData;
            
            for (int H = 0; H < height; H++) {
                for (int W = 0; W < width; W++) {
                    rawData = myInputFile.read();
                    if (rawData == -1) {
                        break;
                    } else {
                        ArrayImage[H][W] = rawData;
                    }
                }
            }
            
            if (myInputFile.read() != -1) {
                System.out.println("Width or Length has wrong");
                valid = false;
            }
            
            myInputFile.close();
        } catch (IOException ex) {
            System.out.println("File cannot read, please try again");
        }
        
        return valid;
    }
    
    public static void doConvolution(){
        int indexNumber = kernel.length / 2;
        int startIndex = -kernel.length / 2;
        int endIndex = kernel.length / 2;

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                
                int NumberOfPixel = 0;
                int sum = 0;
               
                for (int HH = startIndex; HH <= endIndex; HH++) {
                    for (int WW = startIndex; WW <= endIndex; WW++) {
                        if( (h-HH) < 0 || (h-HH) >= height 
                                || (w-WW) < 0 || (w-WW) >= width )
                        {
                            continue;
                        }
                        
                        
                sum += ArrayImage[h-HH][w-WW] * kernel[HH+indexNumber][WW+indexNumber];
                NumberOfPixel++;

                    }
                }
                
                 if(sum > 255){
                    sum = 255;
                }else if(sum > 0){
                    sum = 0; 
            }
                
                Result[h][w] = sum ;
            }
        }
    }


}

