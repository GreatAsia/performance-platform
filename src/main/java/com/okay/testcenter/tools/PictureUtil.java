package com.okay.testcenter.tools;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 对图片的读取和写入
 */

public class PictureUtil {

    private static final Logger logger = LoggerFactory.getLogger(PictureUtil.class);

    /**
     * 读取图片
     * @param file
     * @return
     */
   public BufferedImage readImage(File file){
       BufferedImage bufferedImage = null;
       try {
            bufferedImage = ImageIO.read(file);
       } catch (IOException e) {
           logger.error("read image fail!!!");
           logger.error(ExceptionUtils.getStackTrace(e));
           e.printStackTrace();
       }
      return  bufferedImage;
   }

    /**
     * 写入图片
     * @param bufferedImage
     * @param file
     */
   public void writeImage(BufferedImage bufferedImage,File file){

       try {
           ImageIO.write(bufferedImage,"png",file);
       } catch (IOException e) {
           logger.error("write Image Fail!!!");
           logger.error(ExceptionUtils.getStackTrace(e));
           e.printStackTrace();
       }
   }



//    public static void main(String[] args) {
//        String imagePath = "E:\\javaproject\\paduidata\\夕阳.png";
//        String writePath = "E:\\javaproject\\paduidata\\xiyang.png";
//        BufferedImage bufferedImage = null;
//        File file = new File(imagePath);
//        File writeFile = new File(writePath);
//        System.out.println(file.getPath());
//
//        if(file.exists()){
//
//            try {
//                bufferedImage = ImageIO.read(file);
//                ImageIO.write(bufferedImage,"png",writeFile);
//            } catch (IOException e) {
//                System.out.println("read image fail!!!");
//                e.printStackTrace();
//            }
//        }
//
//    }

}
