/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * imgcrop.java
 *
 * Created on Jul 27, 2012, 2:54:26 PM
 */
package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Timer;

/**
 *
 * @author Marcellin
 */
public class imgcrop extends javax.swing.JFrame {

    private final Timer imgtimer;
    private Image myim = null;

    /** Creates new form imgcrop */
    public imgcrop() {
        initComponents();
        aimg_pan.setBackground(Color.red);
myim = loadimg();
        

        imgtimer = new Timer(40, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Graphics g = aimg_pan.getGraphics();
                g.setColor(Color.blue);
                g.drawRect(0, 0, 50, 50);
                g.drawImage(myim, 0, 0, rootPane);
                //gc.fillArc(10, 10, 75, 50, 0, 50);
                //pief.validate();
            }
        });
        imgtimer.start();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        aimg_pan = new javax.swing.JPanel();
        cimg_pan = new javax.swing.JPanel();
        crop_but = new javax.swing.JButton();
        xpos_field = new javax.swing.JTextField();
        ypos_field = new javax.swing.JTextField();
        w_field = new javax.swing.JTextField();
        h_field = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(livefacenew.LivefacenewApp.class).getContext().getResourceMap(imgcrop.class);
        aimg_pan.setBackground(resourceMap.getColor("aimg_pan.background")); // NOI18N
        aimg_pan.setName("aimg_pan"); // NOI18N

        javax.swing.GroupLayout aimg_panLayout = new javax.swing.GroupLayout(aimg_pan);
        aimg_pan.setLayout(aimg_panLayout);
        aimg_panLayout.setHorizontalGroup(
            aimg_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 225, Short.MAX_VALUE)
        );
        aimg_panLayout.setVerticalGroup(
            aimg_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 278, Short.MAX_VALUE)
        );

        cimg_pan.setBackground(resourceMap.getColor("cimg_pan.background")); // NOI18N
        cimg_pan.setName("cimg_pan"); // NOI18N

        javax.swing.GroupLayout cimg_panLayout = new javax.swing.GroupLayout(cimg_pan);
        cimg_pan.setLayout(cimg_panLayout);
        cimg_panLayout.setHorizontalGroup(
            cimg_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 171, Short.MAX_VALUE)
        );
        cimg_panLayout.setVerticalGroup(
            cimg_panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 149, Short.MAX_VALUE)
        );

        crop_but.setText(resourceMap.getString("crop_but.text")); // NOI18N
        crop_but.setName("crop_but"); // NOI18N

        xpos_field.setText(resourceMap.getString("xpos_field.text")); // NOI18N
        xpos_field.setName("xpos_field"); // NOI18N

        ypos_field.setText(resourceMap.getString("ypos_field.text")); // NOI18N
        ypos_field.setName("ypos_field"); // NOI18N

        w_field.setText(resourceMap.getString("w_field.text")); // NOI18N
        w_field.setName("w_field"); // NOI18N

        h_field.setText(resourceMap.getString("h_field.text")); // NOI18N
        h_field.setName("h_field"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(aimg_pan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cimg_pan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(w_field)
                                    .addComponent(xpos_field, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(h_field)
                                    .addComponent(ypos_field, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE))))
                        .addContainerGap(62, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(crop_but, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cimg_pan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(xpos_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ypos_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(w_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(h_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(crop_but, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                    .addComponent(aimg_pan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(imgcrop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(imgcrop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(imgcrop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(imgcrop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                imgcrop imc = new imgcrop();
                imc.loadimg();
                imc.setVisible(true);
            }
        });
    }

//    load image to be cropped and displa it in a panel
    public Image loadimg() {
        Graphics g1 = aimg_pan.getGraphics();
        g1.setColor(Color.blue);
        String temp;
        File wd = new File(""), inPath;

        temp = wd.getAbsolutePath();
        inPath = new File(temp + "\\img_temp\\nma1.txt");
        Image myimg = null;
        try {
            //create FileInputStream object
            FileInputStream fin = new FileInputStream(inPath);

            /*
             * Create byte array large enough to hold the content of the file.
             * Use File.length to determine size of the file in bytes.
             */
            byte fileContent[] = new byte[(int) inPath.length()];

            /*
             * To read content of the file in byte array, use
             * int read(byte[] byteArray) method of java FileInputStream class.
             *
             */
            fin.read(fileContent);

            //jTextArea1.append("\n" + String.valueOf(people.elementAt(0).get(1)));
            System.out.println("File content : " + fileContent[0]);

            myimg = Toolkit.getDefaultToolkit().createImage(fileContent);

            Graphics g = aimg_pan.getGraphics();
            g.setColor(Color.blue);
            g.drawImage(myimg, 0, 0, null);
  
            myimg = byte_img(fileContent);

        } catch (FileNotFoundException ex) {
            System.out.println("File not found" + ex);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the file " + ioe);
        }

        return myimg;
    }

    //----------------------------------
    public Image byte_img(byte [] bytes) throws IOException{
     /*
         * The second part shows how to convert byte array back to an image file  
         */
 
        //Before is how to change ByteArray back to Image
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");
        //ImageIO is a class containing static convenience methods for locating ImageReaders
        //and ImageWriters, and performing simple encoding and decoding. 
 
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; // File or InputStream, it seems file is OK
 
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        //Returns an ImageInputStream that will take its input from the given Object
 
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
 
        Image image = reader.read(0, param);
        //got an image file
 
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
        //bufferedImage is the RenderedImage to be written
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, null, null);
        File imageFile = new File("C:\\newrose2.jpg");
        ImageIO.write(bufferedImage, "jpg", imageFile);
        //"jpg" is the format of the image
        //imageFile is the file to be written to.
 
        System.out.println(imageFile.getPath());
        return image;
}
    
    //----------------------------------
    public Image cropimg(Image img, int x, int y, int w, int h) {
        return createImage(new FilteredImageSource(img.getSource(),
                new CropImageFilter(x, y, w, h)));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel aimg_pan;
    private javax.swing.JPanel cimg_pan;
    private javax.swing.JButton crop_but;
    private javax.swing.JTextField h_field;
    private javax.swing.JTextField w_field;
    private javax.swing.JTextField xpos_field;
    private javax.swing.JTextField ypos_field;
    // End of variables declaration//GEN-END:variables
}
