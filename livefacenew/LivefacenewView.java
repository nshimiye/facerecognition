/*
 * LivefacenewView.java
 */
package livefacenew;

import Luxand.*;
import Luxand.FSDK.*;
import Luxand.FSDKCam.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * The application's main frame.
 */
public class LivefacenewView extends FrameView implements MouseListener {

    private int nn = 0, ii = 0;
    private boolean userav = false;
    private Point tlc, brc;
    private String temp;

    public LivefacenewView(SingleFrameApplication app) {
        super(app);


        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LivefacenewView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(LivefacenewView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(LivefacenewView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(LivefacenewView.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        // drawPie_obd(pie_panel);
        final JPanel pief = this.pie_panel;
        // Timer to draw and process image from camera
        pieTimer = new Timer(40, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Graphics gc = pief.getGraphics();

                drawPie((Graphics2D) gc, slices, items);
                //gc.setColor(Color.green);
                //gc.fillArc(10, 10, 75, 50, 0, 50);
                //pief.validate();
            }
        });

        //-----------------------face-------------------------------------------

        final JPanel mainFrame = this.camPanel;
        mainFrame_temp[0] = this.camPanel;
        camPanel.addMouseListener(this);
        //addMouseListener(this);
        //---------------2cam
        final JPanel mainFrame2 = null;
        //mainFrame_temp[1] = null;
        //------------------

        File wd = new File("");
        temp = wd.getAbsolutePath();
        System.load(temp + "\\facesdk.dll");

        try {
            int r = FSDK.ActivateLibrary("hEnFaGH0pxK3MjHQ97QM94lhHWQtKXWxZrUBRgHeBwyf0F1EYZZxtFyTfPT6ao9YSXeJtn6QyfdMgIMI6B59/+aS4ubj8ktKgsHjL4ZUXqGq8kNsMCv+zwWJv55MFvovxjTFKdyLXn3ZKIdmEc6W3vNCws4xjsFuTzFTrpAXYTI=");
            if (r != FSDK.FSDKE_OK) {
                JOptionPane.showMessageDialog(camPanel, "Please run the License Key Wizard (Start - Luxand - FaceSDK - License Key Wizard)", "Error activating FaceSDK", JOptionPane.ERROR_MESSAGE);
                System.exit(r);
            }
        } catch (java.lang.UnsatisfiedLinkError e) {
            JOptionPane.showMessageDialog(camPanel, e.toString(), "Link Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        FSDK.Initialize();

        // set realtime face detection parameters
        FSDK.SetFaceDetectionParameters(false, false, 100);
        FSDK.SetFaceDetectionThreshold(3);


        FSDKCam.InitializeCapturing();

        TCameras cameraList = new TCameras();
        int count[] = new int[1];
        int count_cams = FSDKCam.GetCameraList(cameraList, count);
        if (count[0] == 0) {
            JOptionPane.showMessageDialog(mainFrame, "Please attach a camera");
            System.exit(1);
        }
        //------------multicam
        String[] cameraName_temp = new String[cameraList.cameras.length];
        for (int ccam = 0; ccam < cameraList.cameras.length; ccam++) {
            cameraName_temp[ccam] = cameraList.cameras[ccam];
        }
        String cameraName = cameraName_temp[0];
        //---------------2cam
        String cameraName2 = cameraList.cameras[0];
        //------------------

        FSDK_VideoFormats formatList = new FSDK_VideoFormats();
        FSDKCam.GetVideoFormatList(cameraName, formatList, count);
        FSDKCam.SetVideoFormat(cameraName, formatList.formats[0]);

        //---------------2cam
        FSDKCam.GetVideoFormatList(cameraName2, formatList, count);
        FSDKCam.SetVideoFormat(cameraName2, formatList.formats[0]);
        //------------------

        cameraHandle = new HCamera();
        //---------------2cam
        cameraHandle2 = new HCamera();
        //------------------
        int r = FSDKCam.OpenVideoCamera(cameraName, cameraHandle);

        if (r != FSDK.FSDKE_OK) {
            JOptionPane.showMessageDialog(mainFrame, "Error opening camera");
            System.exit(r);
        }
        //---------------2cam
        int r2 = FSDKCam.OpenVideoCamera(cameraName2, cameraHandle2);
        if (r2 != FSDK.FSDKE_OK) {
            JOptionPane.showMessageDialog(mainFrame_temp[1], "Error opening camera");
            System.exit(r2);
        }
        //------------------

        // Timer to draw and process image from camera
        drawingTimer = new Timer(40, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                HImage imageHandle = new HImage();
                //---------------2cam
                HImage imageHandle2 = new HImage();
                //------------------

                if (FSDKCam.GrabFrame(cameraHandle, imageHandle) == FSDK.FSDKE_OK
                        && FSDKCam.GrabFrame(cameraHandle2, imageHandle2) == FSDK.FSDKE_OK) {
                    Image awtImage[] = new Image[1];
                    //---------------2cam
                    //------------------
                    if (FSDK.SaveImageToAWTImage(imageHandle, awtImage, FSDK_IMAGEMODE.FSDK_IMAGE_COLOR_24BIT) == FSDK.FSDKE_OK) {

                        BufferedImage bufImage = null;
                        TFacePosition.ByReference facePosition = new TFacePosition.ByReference();

                        // if a face is detected, we can recognize it
                        if (FSDK.DetectFace(imageHandle, facePosition) == FSDK.FSDKE_OK) {
                            bufImage = new BufferedImage(awtImage[0].getWidth(null), awtImage[0].getHeight(null), BufferedImage.TYPE_INT_ARGB);
                            Graphics gr = bufImage.getGraphics();
                            gr.drawImage(awtImage[0], 0, 0, null);
                            prof1 = awtImage[0];
                            bufImg = bufImage;


                            gr.setColor(Color.green);
                            tlc = new Point(facePosition.xc - facePosition.w / 2, facePosition.yc - facePosition.w / 2);
                            // make clickable true
                            brc = new Point(facePosition.xc + facePosition.w / 2, facePosition.yc + facePosition.w / 2);
                            gr.drawRect(tlc.x, tlc.y, facePosition.w, facePosition.w);
//prop1 = new Point(tlc.x, tlc.y);
//prop2 = new Point(facePosition.w, facePosition.w);
                            //--------------------

                            FSDK_FaceTemplate.ByReference template = new FSDK_FaceTemplate.ByReference();

                            if (programState == programStateRemember || programState == programStateRecognize) {
                                FSDK.GetFaceTemplateInRegion(imageHandle, facePosition, template);
                            }

                            switch (programState) {
                                case programStateNormal:
                                    break;

                                case programStateRemember:
                                    //jTextArea1.append("\n in state remember");
                                    ///-----------------------------------------
                                    if (regist != null && (!regist.isVisible())) {
                                        // jTextArea1.append("\n regist is not visible");
//                                        if (!regist.isVisible()) {
//                                            LiveFacerc_deskApp.getApplication().show(regist);
//                                        }
                                        userName = regist_temp.user_field.getText().toLowerCase();
                                        // jTextArea1.append("\n" + userav);
                                        //--------------------- 
                                        if (userName != null && (!userName.trim().equals("user"))
                                                && (!userName.trim().equals(""))) {
                                            for (int count_user = 0; count_user < userNames.length && userNames[count_user] != null; count_user++) {
                                                if (userName.trim().equals(userNames[count_user].trim())) {
                                                    userav = false;
                                                    break;
                                                }
                                            }
                                            userav = true;
                                        } else {
                                            userav = false;
                                        }

                                        if (userav) {
                                            regist.dispose();
                                            // jTextArea1.append("\n" + userav);
                                            regist = null;

                                        } else {//incorect or not available user name
                                            if (regist_temp.saveb) {
                                                LivefacenewApp.getApplication().show(regist);
                                            } else {
                                                regist.dispose();
                                                // jTextArea1.append("\n" + userav);
                                                regist = null;
                                            }
                                            //  jTextArea1.append("\n what u doing?");
                                        }
                                    }
                                    //} else { 
                                    if (regist == null && userav) {
                                        // jTextArea1.append("\nregist is null");
                                        ///-----------------------------------------
                                        //template.
                                        faceTemplates.add(template);
                                        //jLabel1.setText("Templates stored: " + Integer.toString(faceTemplates.size()));
                                        if (faceTemplates.size() > 9) {



                                            //--nma
                                            people.add(ii, faceTemplates);
                                            userNames[ii] = userName;

                                            //------save temp to files---------

                                            //File imgf = new File("");

                                            //System.load( + "\\facesdk.dll");

                                            for (int out_c = 0; out_c < faceTemplates.size() && faceTemplates.get(out_c) != null; out_c++) {

                                                String strFilePath = temp + "\\img_temp\\" + userNames[ii] + out_c + ".txt";

                                                try {
                                                    FileOutputStream fos = new FileOutputStream(strFilePath);

                                                    /*
                                                     * To write byte array to a file, use
                                                     * void write(byte[] bArray) method of Java FileOutputStream class.
                                                     *
                                                     * This method writes given byte array to a file.
                                                     */

                                                    fos.write(faceTemplates.get(out_c).template);

                                                    System.out.println("finish write template: " + out_c);

                                                } catch (FileNotFoundException ex) {
                                                    System.out.println("FileNotFoundException : " + ex);
                                                } catch (IOException ioe) {
                                                    System.out.println("IOException : " + ioe);
                                                }
                                            }

                                            //--nma
                                            //if (poses == 4) {


                                            programState = programStateRecognize;
                                            //}

                                            ///------------------------------
                                            ii++;
                                        }
                                    }
                                    break;

                                case programStateRecognize:
                                    boolean match = false;

                                    for (int k = 0; k < people.size() && people.elementAt(k) != null; k++) { //--nma

                                        Iterator it = people.elementAt(k).iterator();
                                        while (it.hasNext()) {
                                            float similarity[] = new float[]{0.0f};
                                            FSDK_FaceTemplate.ByReference t1 = (FSDK_FaceTemplate.ByReference) it.next();
                                            //System.out.printf("\n----%d----\n",t1.size());
                                            FSDK.MatchFaces(t1, template, similarity);
                                            float threshold[] = new float[]{0.0f};
                                            FSDK.GetMatchingThresholdAtFAR(0.01f, threshold);

                                            if (similarity[0] > threshold[0]) {
                                                match = true;
                                                usertemp = userNames[k]; //--nma
                                                prof = t1.template;
                                                break;
                                            }
                                        }
                                        if (match) {//--nma
                                            match_temp = match;
                                            break;
                                        }//--nma
                                    } //--nma
                                    if (match) {
                                        gr.setFont(new Font("Arial", Font.BOLD, 16));
                                        FontMetrics fm = gr.getFontMetrics();
                                        java.awt.geom.Rectangle2D textRect = fm.getStringBounds(usertemp, gr);
                                        gr.drawString(usertemp, (int) (facePosition.xc - textRect.getWidth() / 2), (int) (facePosition.yc + facePosition.w / 2 + textRect.getHeight()));

                                        if (nn == 0) {
                                            nn = 1;
                                            AudioInputStream audio;
                                            try {
                                                audio = AudioSystem.getAudioInputStream(new File(temp + "\\media\\smw_key.wav"));
                                                Clip clip0 = null;
                                                try {
                                                    clip0 = AudioSystem.getClip();
                                                } catch (LineUnavailableException ex) {
                                                    ex.toString();
                                                }
                                                try {
                                                    clip0.open(audio);
                                                } catch (LineUnavailableException ex) {
                                                    ex.toString();
                                                }
                                                clip0.start();
                                            } catch (UnsupportedAudioFileException ex) {
                                                ex.toString();
                                            } catch (IOException ex) {
                                                ex.toString();
                                            }

                                            //--nma table filling
                                            //jTextArea1.append("\n" + String.valueOf(people.size()));
//                                            javax.swing.table.TableModel model = cusInfo.getModel();
//
//                                            for (int j = 0; j < userNames.length; j++) {
//                                                try {
//                                                    model.setValueAt(userNames[j], j, 0);
//                                                } catch (Exception ex) {
//                                                    ex.toString();
//                                                }
//                                                //--
//                                            }
                                            // jTextArea1.append("\n Your username is " + usertemp);

                                            //JOptionPane.showConfirmDialog(mainFrame, userName + " found");
                                            prof1 = awtImage[0];
                                            bufImg = bufImage;
                                            prop1 = new Point(tlc.x, tlc.y);
                                            prop2 = new Point(facePosition.w, facePosition.w);
                                        }



                                    } else {
                                        usertemp = null;
                                    }

                                    break;
                            }
                        } else {
                            tlc = null;
                            brc = null;
                        }

                        // display current frame
                        mainFrame.getGraphics().drawImage((bufImage != null) ? bufImage : awtImage[0], 0, 0, null);
                        if (prof1 != null && bufImg != null) {
                            //Toolkit.getDefaultToolkit().createImage(prof);
                            //prof_pic.getGraphics().drawImage((bufImg != null) ? bufImg : prof1,
                            //        prop1.x, prop1.y, null);
                        }
                    }
                    FSDK.FreeImage(imageHandle); // delete the FaceSDK image handle
                    //---------------2cam
                    FSDK.FreeImage(imageHandle2); // delete the FaceSDK image handle
                    //-------------------
                }
            }
        });

        //-------------------------------------------------------------------------

        //--- desk app





        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = LivefacenewApp.getApplication().getMainFrame();
            aboutBox = new LivefacenewAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        LivefacenewApp.getApplication().show(aboutBox);
    }

    //-- desk app
    @Action
    public void buttonStart() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        if (this.start_but.getText().equals("start")) {


            this.start_but.setEnabled(false);
            this.close_but.setEnabled(true);
            ii = 0;
            //--nma
            people = this.loadtemp();

            drawingTimer.start();

            AudioInputStream audio;
            try {
                audio = AudioSystem.getAudioInputStream(new File(temp + "\\media\\smb3_1-up.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
            } catch (UnsupportedAudioFileException ex) {
                ex.toString();
            } catch (IOException ex) {
                ex.toString();
            }

            programState = programStateRecognize;
        }

        opencam();

    }

    @Action
    public void buttonRemember() {

        userav = false;
        //--open register form
        if (regist == null) {
            JFrame mainFrame = LivefacenewApp.getApplication().getMainFrame();
            regist_temp = new Registrat(mainFrame, userNames);
            regist = regist_temp;
            regist.setLocationRelativeTo(mainFrame);
        }
        LivefacenewApp.getApplication().show(regist);
//----
        //faceTemplates.clear();1
        // list where we store face templates
        faceTemplates = new ArrayList<FSDK_FaceTemplate.ByReference>();

        programState = programStateRemember;

        //this.jLabel1.setText("Look at the camera");
        nn = 0;
    }

    public void closeCamera() {
        FSDKCam.CloseVideoCamera(cameraHandle);
        FSDKCam.FinalizeCapturing();
        FSDK.Finalize();
    }

    public Vector<List<FSDK_FaceTemplate.ByReference>> loadtemp() {
        //++retrieve img bytes++++++++++
        List users = new ArrayList<String>(); //from database
        int templ_count = 10;  //this value will come from the database

        System.out.println("\n=====<< " + users.size());
        //users[0] = "Marcellin";  //from database

        //----------------------
        //usern = user_field.getText();
        Connection conn = null;

        try {
            String userName = "marcen0";
            String password = "From2to1";
            String url = "jdbc:mysql://198.101.215.122:3306/facerec";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("Database connection established");


            //-- insert data in database
            try {
                java.sql.Statement st = conn.createStatement();
                ResultSet val = st.executeQuery("SELECT id, user_n FROM customer");
                System.out.println("Status: " + val + "\n 1 row affected\n");
                //Extact result from ResultSet rs
                int ud = 0;
                while (val.next()) {
                    users.add(ud, val.getString("user_n"));

                    System.out.println("" + val.getInt("id") + "\t" + users.get(ud));
                    ud++;
                }
                // close ResultSet rs
                val.close();
                //.getString("user_n");

            } catch (SQLException s) {
                System.out.println("SQL statement is not executed! \n" + s.toString());
            }

            //--

        } catch (Exception e) {
            System.err.println("Cannot connect to database server" + e.toString());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Database connection terminated");
                } catch (Exception e) { /* ignore close errors */ }
            }
        }
        //---------------------------------------  /   
        //int people_count = 2; //from database
        //int pposes = 5; //from database
        File inPath;
        Vector<List<FSDK_FaceTemplate.ByReference>> prev_temps = new Vector<List<FSDK_FaceTemplate.ByReference>>();
        FSDK_FaceTemplate.ByReference template0;


        for (int pp = 0; pp < users.size(); pp++) {


            // for (int pos_n = 0; pos_n < pposes; pos_n++) {
            faceTemplates = new ArrayList<FSDK_FaceTemplate.ByReference>();
            for (int in_c = 0; in_c < templ_count; in_c++) {
                //create file object
                inPath = new File(temp + "\\img_temp\\" + users.get(pp) + in_c + ".txt");
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
                    template0 = new FSDK_FaceTemplate.ByReference();
                    template0.template = fileContent;
                    faceTemplates.add(template0);
                    //jTextArea1.append("\n" + String.valueOf(people.elementAt(0).get(1)));
                    System.out.println("File content : " + fileContent[0]);

                } catch (FileNotFoundException ex) {
                    System.out.println("File not found" + ex);
                } catch (IOException ioe) {
                    System.out.println("Exception while reading the file " + ioe);
                }
            }
            prev_temps.add(pp, faceTemplates);
            userNames[pp] = (String) users.get(pp);
            ii = pp;

            // }

        }

        //-------------------------------
        //jTextArea1.append(prev_temps.toString());
        users = null;
        return prev_temps;
    }

    //---desk app
    //check the mouse container
    public boolean contains(MouseEvent evt) {

        return (tlc != null) && (brc != null)
                && (evt.getX() > tlc.getX()) && (evt.getX() < brc.getX())
                && (evt.getY() > tlc.getY()) && (evt.getY() < brc.getY());
    }
    //==============

    //------------------------
    public void opencam() {
        profPanel.setVisible(false);
        camPanel.setVisible(false);
        camPanel.setSize(mainPanel.getSize());
        camPanel.validate();
        camPanel.setVisible(true);
        this.getFrame().validate();

        if (pieTimer.isRunning()) {
            pieTimer.stop();
        }
//        if (pieTimer.isRunning()) {
//            pieTimer.stop();
//        }
    }

    public void openprof() {
        camPanel.setVisible(false);
        profPanel.setVisible(false);
        profPanel.setSize(mainPanel.getSize());
        profPanel.setVisible(true);
        this.start_but.setText("Open camera");
        this.start_but.setEnabled(true);
        this.getFrame().validate();

        prof_pic.setLocation(5, 5);
        prof_pic.setBackground(Color.gray);
        // display current frame
        // if (prof1 != null) {
        //Toolkit.getDefaultToolkit().createImage(prof);
        //   prof_pic.getGraphics().drawImage((bufImg != null) ? bufImg : prof1,
        //         prop1.x, prop1.y, prop2.x, prop2.y, null);
        //}

        //items bought
        //---pie
        items_n = new ArrayList<String>();
        List<Double> items_q = new ArrayList<Double>();
        items_q.add(0, 10.0);
        items_q.add(1, 15.0);
        items_q.add(2, 20.0);
        items_q.add(3, 34.0);

        //---pie explain
        items_n.add(0, "0. Romance books");
        items_n.add(1, "1. Jewerlies");
        items_n.add(2, "2. Nike products");
        items_n.add(3, "3. Tech magazines");


        for (int i = 0; i < items_q.size(); i++) {
            slices[i] = new PieValue(items_q.get(i), new Color(75 + i, (50 * i) % 250, (100 * i) % 250),
                    items_n.get(i));
        }
        items = new ArrayList<JLabel>(5);
        items.add(0, it1);
        items.add(1, it2);
        items.add(2, it3);
        items.add(3, it4);

        if (!pieTimer.isRunning()) {
            pieTimer.start();
        }
        //drawPie((Graphics2D) pie_panel.getGraphics(), slices, items);
        //pieTimer.start();




        //---basic info

        String fnam = "John",
                lnam = "Smith",
                jobt = "Busnessman",
                family = "Married";
        int age = 18;
        String spend = "$ 500";
        info_area.setText("Name: " + fnam + " " + lnam + " \nAge: " + age
                + "\tJob: " + jobt + "\nFamily: " + family);

        spend_l.setText(spend);


    }

    @Action
    public void update_p() {
    }

    @Action
    public void disp_history() {
    }

    @Action
    public void sale_sugg() {
    }

    public void mouseClicked(MouseEvent e) {
        if (this.contains(e)) {
            //jTextArea1.append("\n yeap you got that");
            if (usertemp != null) {
                openprof();
            } else {
                opencam();
                this.buttonRemember();
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseEntered(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent e) {
        // throw new UnsupportedOperationException("Not supported yet.");
    }

    // Class to hold a value for a slice
    public class PieValue {

        double value;
        Color color;
        String itname;

        public PieValue(double value, Color color, String itname) {
            this.value = value;
            this.color = color;
            this.itname = itname;
        }
    }

    // slices is an array of values that represent the size of each slice.
    public void drawPie(Graphics2D g, PieValue[] slices, List<JLabel> its) {
        // Get total value of all slices
        double total = 0.0D;
        for (int i = 0; i < slices.length; i++) {
            total += slices[i].value;
        }

        // Draw each pie slice
        double curValue = 0.0D;
        int startAngle = 0;
        for (int i = 0; i < slices.length; i++) {
            // Compute the start and stop angles
            startAngle = (int) (curValue * 360 / total);
            int arcAngle = (int) (slices[i].value * 360 / total);

            // Ensure that rounding errors do not leave a gap between the first and last slice
            if (i == slices.length - 1) {
                arcAngle = 360 - startAngle;
            }

            // Set the color and draw a filled arc
            g.setColor(slices[i].color);
            its.get(i).setForeground(slices[i].color);
            its.get(i).setText(slices[i].itname);
            g.fillArc(0, 0, 100, 100, startAngle, arcAngle);
            //g.setColor(Color.black);
            //g.drawString(String.valueOf(i), i+startAngle, i+arcAngle);

            curValue += slices[i].value;
        }
    }

    //------------------------
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        start_but = new javax.swing.JButton();
        close_but = new javax.swing.JButton();
        camPanel = new javax.swing.JPanel();
        profPanel = new javax.swing.JPanel();
        pie_panel = new javax.swing.JPanel();
        prof_pic = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        info_area = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        star_panel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        upsale_but = new javax.swing.JButton();
        upd_but = new javax.swing.JButton();
        phist_but = new javax.swing.JButton();
        spend_l = new javax.swing.JLabel();
        it1 = new javax.swing.JLabel();
        it2 = new javax.swing.JLabel();
        it3 = new javax.swing.JLabel();
        it4 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(livefacenew.LivefacenewApp.class).getContext().getResourceMap(LivefacenewView.class);
        mainPanel.setBackground(resourceMap.getColor("mainPanel.background")); // NOI18N
        mainPanel.setName("mainPanel"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(livefacenew.LivefacenewApp.class).getContext().getActionMap(LivefacenewView.class, this);
        start_but.setAction(actionMap.get("buttonStart")); // NOI18N
        start_but.setText(resourceMap.getString("start_but.text")); // NOI18N
        start_but.setName("start_but"); // NOI18N

        close_but.setAction(actionMap.get("quit")); // NOI18N
        close_but.setText(resourceMap.getString("close_but.text")); // NOI18N
        close_but.setName("close_but"); // NOI18N

        camPanel.setBackground(resourceMap.getColor("camPanel.background")); // NOI18N
        camPanel.setName("camPanel"); // NOI18N

        javax.swing.GroupLayout camPanelLayout = new javax.swing.GroupLayout(camPanel);
        camPanel.setLayout(camPanelLayout);
        camPanelLayout.setHorizontalGroup(
            camPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 217, Short.MAX_VALUE)
        );
        camPanelLayout.setVerticalGroup(
            camPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 548, Short.MAX_VALUE)
        );

        profPanel.setBackground(resourceMap.getColor("profPanel.background")); // NOI18N
        profPanel.setName("profPanel"); // NOI18N

        pie_panel.setBackground(resourceMap.getColor("pie_panel.background")); // NOI18N
        pie_panel.setName("pie_panel"); // NOI18N

        javax.swing.GroupLayout pie_panelLayout = new javax.swing.GroupLayout(pie_panel);
        pie_panel.setLayout(pie_panelLayout);
        pie_panelLayout.setHorizontalGroup(
            pie_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 111, Short.MAX_VALUE)
        );
        pie_panelLayout.setVerticalGroup(
            pie_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 95, Short.MAX_VALUE)
        );

        prof_pic.setName("prof_pic"); // NOI18N

        javax.swing.GroupLayout prof_picLayout = new javax.swing.GroupLayout(prof_pic);
        prof_pic.setLayout(prof_picLayout);
        prof_picLayout.setHorizontalGroup(
            prof_picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );
        prof_picLayout.setVerticalGroup(
            prof_picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 272, Short.MAX_VALUE)
        );

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        info_area.setBackground(resourceMap.getColor("info_area.background")); // NOI18N
        info_area.setColumns(20);
        info_area.setRows(5);
        info_area.setName("info_area"); // NOI18N
        jScrollPane1.setViewportView(info_area);

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        star_panel.setName("star_panel"); // NOI18N

        javax.swing.GroupLayout star_panelLayout = new javax.swing.GroupLayout(star_panel);
        star_panel.setLayout(star_panelLayout);
        star_panelLayout.setHorizontalGroup(
            star_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 294, Short.MAX_VALUE)
        );
        star_panelLayout.setVerticalGroup(
            star_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        upsale_but.setAction(actionMap.get("sale_sugg")); // NOI18N
        upsale_but.setBackground(resourceMap.getColor("upsale_but.background")); // NOI18N
        upsale_but.setText(resourceMap.getString("upsale_but.text")); // NOI18N
        upsale_but.setBorderPainted(false);
        upsale_but.setName("upsale_but"); // NOI18N

        upd_but.setAction(actionMap.get("update_p")); // NOI18N
        upd_but.setBackground(resourceMap.getColor("upd_but.background")); // NOI18N
        upd_but.setText(resourceMap.getString("upd_but.text")); // NOI18N
        upd_but.setName("upd_but"); // NOI18N

        phist_but.setAction(actionMap.get("disp_history")); // NOI18N
        phist_but.setBackground(resourceMap.getColor("phist_but.background")); // NOI18N
        phist_but.setText(resourceMap.getString("phist_but.text")); // NOI18N
        phist_but.setName("phist_but"); // NOI18N

        spend_l.setText(resourceMap.getString("spend_l.text")); // NOI18N
        spend_l.setName("spend_l"); // NOI18N

        it1.setBackground(resourceMap.getColor("it1.background")); // NOI18N
        it1.setFont(resourceMap.getFont("it1.font")); // NOI18N
        it1.setText(resourceMap.getString("it1.text")); // NOI18N
        it1.setName("it1"); // NOI18N

        it2.setFont(resourceMap.getFont("it2.font")); // NOI18N
        it2.setText(resourceMap.getString("it2.text")); // NOI18N
        it2.setName("it2"); // NOI18N

        it3.setFont(resourceMap.getFont("it3.font")); // NOI18N
        it3.setText(resourceMap.getString("it3.text")); // NOI18N
        it3.setName("it3"); // NOI18N

        it4.setFont(resourceMap.getFont("it4.font")); // NOI18N
        it4.setText(resourceMap.getString("it4.text")); // NOI18N
        it4.setName("it4"); // NOI18N

        javax.swing.GroupLayout profPanelLayout = new javax.swing.GroupLayout(profPanel);
        profPanel.setLayout(profPanelLayout);
        profPanelLayout.setHorizontalGroup(
            profPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(profPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(profPanelLayout.createSequentialGroup()
                        .addComponent(prof_pic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(profPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(profPanelLayout.createSequentialGroup()
                                .addComponent(pie_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(profPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(it4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(it3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(it2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(it1, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(profPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(upd_but)
                            .addComponent(phist_but, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(upsale_but, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(profPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spend_l, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addComponent(star_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        profPanelLayout.setVerticalGroup(
            profPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profPanelLayout.createSequentialGroup()
                .addGroup(profPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(profPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(profPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(upd_but, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(profPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(profPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(profPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(profPanelLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(it1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(it2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(it3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(it4))
                                    .addComponent(pie_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(profPanelLayout.createSequentialGroup()
                                .addGap(33, 33, 33)
                                .addComponent(phist_but, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(profPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(prof_pic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addGroup(profPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(spend_l))))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(star_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(upsale_but, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(close_but, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(start_but, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(camPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(profPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(camPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(profPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addComponent(start_but, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 428, Short.MAX_VALUE)
                        .addComponent(close_but, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 1093, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 923, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel camPanel;
    private javax.swing.JButton close_but;
    private javax.swing.JTextArea info_area;
    private javax.swing.JLabel it1;
    private javax.swing.JLabel it2;
    private javax.swing.JLabel it3;
    private javax.swing.JLabel it4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton phist_but;
    private javax.swing.JPanel pie_panel;
    private javax.swing.JPanel profPanel;
    private javax.swing.JPanel prof_pic;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel spend_l;
    private javax.swing.JPanel star_panel;
    private javax.swing.JButton start_but;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton upd_but;
    private javax.swing.JButton upsale_but;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Timer pieTimer;
    //private final Timer pieTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    PieValue[] slices = new PieValue[4];
    List<String> items_n;
    List<JLabel> items;
    //--- dek app
    //------
    public final Timer drawingTimer;
    private HCamera cameraHandle;
    //---------------2cam
    private HCamera cameraHandle2;
    private final JPanel[] mainFrame_temp = new JPanel[5];
    //---------------------
    private String userName;
    private String usertemp;
    private String[] userNames = new String[50];
    //---marcellin--------------
    private boolean match_temp = false;
    private Vector<List<FSDK_FaceTemplate.ByReference>> people;
    //--------------------------
    private List<FSDK_FaceTemplate.ByReference> faceTemplates; // set of face templates (we store 10)
    // program states: waiting for the user to click 'Remember Me', storing the user's template,
    // and recognizing user's face
    final int programStateNormal = 0;
    final int programStateRemember = 1;
    final int programStateRecognize = 2;
    private int programState = programStateNormal;
    //--nma
    private JDialog regist;
    private Registrat regist_temp;
    private JOptionPane regoption;
    private byte[] prof;
    private Image prof1 = null;
    private Point prop1, prop2;
    BufferedImage bufImg = null;
}
