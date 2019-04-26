package manu.internet;

import android.media.Image;
import android.media.ImageReader;

import java.io.*;
import java.nio.ByteBuffer;


public class TestThread extends Thread {
    private ImageReader reader;
    public static int tmp = 0;


    public TestThread(ImageReader reader) {
        this.reader = reader;
    }
    public void run(){
        saveImage();
    }

    private void saveImage(){
        Image image = null;
        try {
            image = reader.acquireLatestImage();
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.capacity()];
            buffer.get(bytes);
            save(bytes);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            {
                if (image != null)
                    image.close();
            }
        }
    }

    private void save(byte[] bytes) throws IOException {
        File file = new File("/data/user/0/manu.internet/files", tmp++ + ".jpg");
        try {
            //Create the file
            if (file.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }
            //                FileWriter writer = new FileWriter(file);
            //                writer.write("Test data");
            //                writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            outputStream.write(bytes);
        } finally {
            if (outputStream != null)
                outputStream.close();
        }
    }


}
