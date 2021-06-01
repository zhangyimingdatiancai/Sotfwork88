package dao;

import java.io.*;
import java.util.List;

/**
 * @author YiMing Zhang
 * @date Created in 2021 2021/4/30
 * @description
 * @modified
 * @version:1.1
 */



public class VideoImage  {
    public static String ffmpeg_path=new File(VideoImage.class.getResource("").getPath()).getParent()+"\\tool\\ffmpeg\\bin\\ffmpeg.exe";
    public static String cmd_path=new File(VideoImage.class.getResource("").getPath()).getParent()+"\\tool\\ffmpeg\\bin";
    public static String picture_path=new File(VideoImage.class.getResource("").getPath()).getParent()+"\\data\\imageforvideo";

    static {
        try {
            ffmpeg_path = java.net.URLDecoder.decode(new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\tool\\ffmpeg\\bin\\ffmpeg.exe","utf-8");
            cmd_path = java.net.URLDecoder.decode(new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\tool\\ffmpeg\\bin","utf-8");
            picture_path = java.net.URLDecoder.decode(new File(WorkUtil.class.getResource("").getPath()).getParent()+"\\data\\imageforvideo","utf-8");


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    public static boolean processImg(String video_path,String name)  {
         File file = new File(video_path);
        if (!file.exists()) {
            System.out.println("do not have the video");
            return false;
        }
        File file2 = new File(picture_path+"\\"+name+".jpg");
        if (file2.exists()) {
            System.out.println(picture_path+"\\"+name);
            System.out.println("allready have the image");
            return false;
        }
        File imagefile=new File(picture_path);
        if (!imagefile.exists()){
            imagefile.mkdirs();
        }
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(ffmpeg_path);
        commands.add("-i");
        commands.add(video_path);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-ss");
        commands.add("1");
        commands.add("-s");
        commands.add("700x525");
        commands.add(picture_path+"//"+name+".jpg");
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder=builder.directory(new File(cmd_path));
            builder.command(commands);
            builder.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public static String execCMD(String command) throws IOException {
        StringBuilder sb =new StringBuilder();
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder=builder.directory(new File(cmd_path));
            builder.command(command);
            Process process=builder.start();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while((line=bufferedReader.readLine())!=null)
            {
                sb.append(line+"\n");
            }
        } catch (Exception e) {
            return e.toString();
        }
        return sb.toString();
    }





}
