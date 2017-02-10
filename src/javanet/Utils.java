package javanet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jackson McClintock
 */
public class Utils {
    
    final static int SLEEP_INTERVAL = 10;
    
    public static String getNameFromText(String text){
        if(text.length() >= 5 && text.substring(0,5).equals("<dir>")){
            return text.substring(5) + "/";
        }else{
            return text;
        }
    }
    public static String getNameFromFilePath(String file_path){
        if(file_path.substring(file_path.length() - 1).equals("/")){
            return file_path.substring(secondToLastIndexOf(file_path, '/') + 1, file_path.length() - 1);
        }else{
            return file_path.substring(file_path.lastIndexOf('/') + 1);
        }
    }
    public static String getPathFromFilePath(String file_path){
        return file_path.substring(0, file_path.lastIndexOf('/') + 1);
    }
    public static int secondToLastIndexOf(String s, char c){
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        sb = sb.reverse();
        String rs = sb.toString();
        int count = 0;
        for(int i = 0; i < rs.length(); i++){
            if(rs.charAt(i) == c){
                count += 1;
            }
            if(count == 2){
                return (s.length() - i - 1);
            }
        }
        return -1;
    }
    public static int numIndexOf(String string, String find_string, int num){
        for(int i = 0; i < (string.length() - find_string.length() + 1); i++){
            if(string.substring(i, i + find_string.length()).equals(find_string)){
                num--;
            }
            if(num == 0){
                return i;
            }
        }
        return -1;
    }
    public static int numberOf(String string, String find_string){
        for(int i = 0;; i++){
            if(numIndexOf(string, find_string, i + 1) == -1){
                return i;
            }
        }
    }
    public static int stringArrayUsedLength(String[] strings){
        int i = 0;
        for(String s : strings){
            if(s == null){
                break;
            }
            i++;
        }
        return i;
    }
    public static String[] splitToStringArray(String s){
        String[] result = new String[numberOf(s, ":_:_:") + 1];
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++){
            sb.append(s.charAt(i));
            if(sb.toString().contains(":_:_:") || sb.toString().contains(":^:^:")){
                result[count] = sb.substring(0, sb.length() - 5);
                count++;
                sb = new StringBuilder();
            }
        }
        return result;
    }
    public static String byteArrayToString(byte[] bytes){
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length).put(bytes);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bytes.length; i += 2){
            sb.append(buffer.getChar(i));
        }
        return sb.toString();
    }
    public static byte[] stringToByteArray(String string){
        ByteBuffer buffer = ByteBuffer.allocate(string.length() * 2);
        for(int i = 0; i < string.length(); i++){
            buffer.putChar(string.charAt(i));
        }
        return buffer.array();
    }
    public static String intArrayToString(int[] ints){
        ByteBuffer buffer = ByteBuffer.allocate(ints.length * 4);
        for(int i : ints){
            buffer.putInt(i);
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < buffer.array().length; i += 2){
            sb.append(buffer.getChar(i));
        }
        return sb.toString();
    }
    public static int[] stringToIntArray(String string){
        ByteBuffer buffer = ByteBuffer.allocate(string.length() * 2);
        IntBuffer ints = IntBuffer.allocate(string.length() / 2);
        for(int i = 0; i < string.length(); i++){
            buffer.putChar(string.charAt(i));
        }
        for(int i = 0; i < buffer.array().length; i += 4){
            ints.put(buffer.getInt(i));
        }
        return ints.array();
    }
    public static synchronized String commandListener(IOUtils io) throws IOException{
        StringBuilder sb = new StringBuilder();
        for(;;){
            if(io.available() > 1){
                sb.append(io.readChar());
            }
            if(sb.indexOf(":^:^:") != -1){
                return sb.toString();
            }
        }
    }
}
