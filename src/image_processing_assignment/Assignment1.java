import java.io.FileInputStream;
import java.util.ArrayList; 
import java.io.IOException;

public class Assignment1 {
    public static void main(String[] args) {
        try{           
            FileInputStream myInputFile = new FileInputStream("image/Imgpro.tif");
            int value,first=0,sec=0;
            String H = "";
            int M=0;
            int I=1;
            
            System.out.println("-----------------------------Header Info-----------------------------");
            
            System.out.print("Byte Order : ");
            while((I<=2)&&((value = myInputFile.read())!=-1)){
                H = Integer.toHexString(value);
                System.out.print(H);
                I++;
            }
            
            System.out.println("");
            System.out.print("Version    : ");
            while((I<=4)&&((value = myInputFile.read())!=-1)){
                H = Integer.toHexString(value);
                if(value==0){
                    H = "";
                }
                System.out.print(H);
                I++;
            }
            
            System.out.println("");
            System.out.print("Offset     : ");
            while((I<=8)&&((value = myInputFile.read())!=-1)){
                H = Integer.toHexString(value);
                if(value==0){
                    H = "";
                }
                System.out.print(H);
                I++;
            }
            
            first = myInputFile.read();
            sec = myInputFile.read();
            M = getM(first,sec); 
            
            System.out.println("");
            System.out.println("-----------------------------Data  Entry-----------------------------");
            System.out.println("Tag					Type		Count	Value");
            System.out.println("---------------------------------------------------------------------"); 
            
            ArrayList<String> num = new ArrayList<String>(); 
            num.add("0");
            for(int array=1;array<=(12*M);array++){
                value = myInputFile.read();
                String data = String.format("%02X", value);
                num.add(data);
            }
            
            int Stripoff = 0;
            int TValue = 0;
            int Tag = 0;
            int Type = 0;
            int Length = 0;
            
            String name = " ",
            Ttype = " ";
            
            for(int j=0;j<M;j++){
                
                TValue = getValue(num.get(9+(12*j)),num.get(10+(12*j)),num.get(11+(12*j)),num.get(12+(12*j)));
                Tag    = getTag(num.get(1+(12*j)),num.get(2+(12*j)));
                Type   = getType(num.get(3+(12*j)),num.get(4+(12*j)));
                Length = getLength(num.get(5+(12*j)),num.get(6+(12*j)),num.get(7+(12*j)),num.get(8+(12*j)));             
                String decimal_tag = String.format("%02X", Tag);
                
                     if(Type == 1 ){
                        Ttype = "byte";
                }else if(Type == 2){
                        Ttype = "ASCII";
                }else if(Type == 3){
                        Ttype = "short";
                }else if(Type == 4){
                        Ttype = "long";
                }else if(Type == 5){
                        Ttype = "rational";
                }
                
                if(Tag==254){
                    name = "NewSubfileType";
                    System.out.println(decimal_tag + " (" +name + ")                   "  + Type + "(" + Ttype + ")" + "          " + 1 + "       " + TValue);
                }else if(Tag==256){
                    name = "Image width";
                    System.out.println(decimal_tag + "(" +name + ")                      " + Type + "(" + Ttype + ")" + "         " + 1 + "       " + TValue);
                }else if(Tag==257){
                    name = "Image height";
                    System.out.println(decimal_tag + "(" +name + ")                     " + Type + "(" + Ttype + ")" + "         " + 1 + "       " + TValue);
                }else if(Tag==258){
                    name = "Bits per sample";
                    System.out.println(decimal_tag + "(" +name + ")                  " + Type + "(" + Ttype + ")" + "         " + 1 + "       " + TValue);
                }else if(Tag==259){
                    name = "Compression";
                    System.out.println(decimal_tag + "(" +name + ")                      " + Type + "(" + Ttype + ")" + "         " + 1 + "       " + TValue);
                }else if(Tag==262){
                    name = "Photometric interpretation";
                    System.out.println(decimal_tag + "(" +name + ")       " + Type + "(" + Ttype + ")" + "         " + 1 + "       " + TValue);
                }else if(Tag==273){
                    name = "Strip offsets";
                    Stripoff = TValue;
                    System.out.println(decimal_tag + "(" +name + ")                    " + Type + "(" + Ttype + ")" + "          " + 1 + "       " + TValue);
                }else if(Tag==277){
                    name = "Samples per pixel";
                    System.out.println(decimal_tag + "(" +name + ")                " + Type + "(" + Ttype + ")" + "         " + 1 + "       " + TValue);
                }else if(Tag==278){
                    name = "Rows per strip";
                    System.out.println(decimal_tag + "(" +name + ")                   " + Type + "(" + Ttype + ")" + "          " + 1 + "       " + TValue);
                }else if(Tag==279){
                    name = "Strip byte counts";
                    System.out.println(decimal_tag + "(" +name + ")                " + Type + "(" + Ttype + ")" + "          " + 1 + "       " + TValue);
                }else if(Tag==282){
                    name = "X resolution";
                    System.out.println(decimal_tag + "(" +name + ")                     " + Type + "(" + Ttype + ")" + "      " + 1 + "       " + TValue);
                }else if(Tag==283){
                    name = "Y resolution";
                    System.out.println(decimal_tag + "(" +name + ")                     " + Type + "(" + Ttype + ")" + "      " + 1 + "       " + TValue);
                }
            }
            
            System.out.println("-----------------------------Image  Data-----------------------------");
            int ID = (M*12)+1;
            int SIXTEEN = 16;

            while((value = myInputFile.read())!=-1){
                
                if(ID>(Stripoff-10)){
                   if(SIXTEEN == 1){
                       System.out.println();
                       SIXTEEN = 16;
                   }
                   H = String.format("%02X", value);
                   System.out.print(H + " "); 
                   SIXTEEN--;
                }
                ID++;
            }
            myInputFile.close();
        }catch(IOException ex){
            System.out.println("File error, please check the image folder!!!");
        }
    }
    public static int getM(int i, int m){
        int tag = m + i;
        return tag;
    }
    public static int getTag(String i, String m){

        String tag = m + i;
        int value = Integer.parseInt(tag,16);
        String hex = String.format("%02X", value);
        return value;
    }
    public static int getType(String i, String m){

         String tag = m + i;
        int value = Integer.parseInt(tag,16);
        String hex = String.format("%02X", value);
        return value;
    }
    public static int getLength(String i, String m, String j, String k){
        String tag = k + j + m + i;
        int value = Integer.parseInt(tag,16);
        String hex = String.format("%02X", value);
        return value;
    }
    public static int getValue(String i, String m, String j, String k){
        String tag = k + j + m + i;
        int value = Integer.parseInt(tag,16);
        String hex = String.format("%02X", value);
        return value;
    }
}
