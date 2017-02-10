package javanet;

import javanet.client.ClientUtils;

public class UnusedFunctions{
    
    int begin_index;
    int end_index;
    String fs_name;
    String fs;
    
    public String[] getDir(String dir_name){
        int temp_hold = begin_index;
        int layer_in = 1;
        int dir_begin = 0;
        int dir_end = 0;
        String dir_current = fs_name;
        if(dir_current.equals(dir_name)){
            return getContents(begin_index, end_index);
        }
        for(int i = begin_index; i <= end_index; i++){
            if(fs.charAt(i) == '['){
                if((dir_current + fs.substring(temp_hold, i) + "/").equals(dir_name)){
                    dir_begin = i + 1;
                }
                dir_current += fs.substring(temp_hold, i) + "/";
                layer_in += 1;
                temp_hold = i + 1;
                continue;
            }
            if(fs.charAt(i) == ']'){
                if(dir_current.equals(dir_name)){
                    dir_end = i - 1;
                    break;
                }
                dir_current = dir_current.substring(0, Utils.secondToLastIndexOf(dir_current, '/') + 1);
                layer_in -= 1;
                temp_hold = i + 1;
                continue;
            }
            if(fs.charAt(i) == ','){
                temp_hold = i + 1;
            }
        }
        return getContents(dir_begin, dir_end);
    }
    public String[] getContents(int dir_begin, int dir_end){
        int ai = 0;
        String[] contents = new String[10000];
        int temp_hold = dir_begin;
        int layer_in = 1;
        int layer_num = 1;
        for(int i = dir_begin; i <= dir_end; i++){
            if(fs.charAt(i) == '['){
                if(layer_in == layer_num){
                    contents[ai] = "<dir>" + fs.substring(temp_hold, i);
                    ai += 1;
                }
                layer_in += 1;
                temp_hold = i + 1;
                continue;
            }
            if(fs.charAt(i) == ']'){
                layer_in -= 1;
                temp_hold = i + 1;
                continue;
            }
            if(fs.charAt(i) == ','){
                if(layer_in == layer_num && fs.charAt(i - 1) != ']'){
                    contents[ai] = fs.substring(temp_hold, i);
                    ai += 1;
                }
                temp_hold = i + 1;
            }
        }
        return contents;
    }
    public void initFs(){
        for(int i = 0; i < fs.length(); i++){
            if(fs.charAt(i) == '{'){
                begin_index = i + 1;
                fs_name = fs.substring(0, i);
                continue;
            }
            if(fs.charAt(i) == '}'){
                end_index = i - 1;
                break;
            }
        }
    }
}
