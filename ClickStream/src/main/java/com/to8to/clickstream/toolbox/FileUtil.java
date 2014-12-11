package com.to8to.clickstream.toolbox;

import com.to8to.clickstream.ClickStream;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by moon.zhong on 2014/12/2.
 */
public class FileUtil {
    /**
     * 向文件中追加事件
     * @param putEvent
     */

    public static void writeFile(String putEvent,String filePath){
        try {
            File file = new File(filePath) ;
            if(!file.exists()){
                try {
                    file.createNewFile() ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileOutputStream fos = new FileOutputStream(file) ;
            try {
                fos.write(putEvent.getBytes());
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件中的所有内容
     * @return
     */
    public static String readFile(String fileName){
        try {
            File file = new File(fileName) ;
            if(!file.exists()){
                try {
                    file.createNewFile() ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileInputStream fis = new FileInputStream(file) ;
            ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
            byte [] buffer = new byte[1024] ;
            int length = -1 ;
            try {
                while((length = fis.read(buffer)) != -1){
                    bos.write(buffer,0,length);
                }
                bos.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.delete() ;
            return bos.toString() ;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "" ;
        }
    }
}
