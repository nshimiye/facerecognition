/*
 * Important info
 * 
 * The SQL script to list out the entire databases size
 * SELECT table_schema "Data Base Name", SUM( data_length + index_length) / 1024 / 1024 
"Data Base Size in MB" FROM information_schema.TABLES GROUP BY table_schema ;
 */

/*
 * Registrat.java
 *
 * Created on Jul 8, 2012, 5:27:47 PM
 */
package livefacenew;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.jdesktop.application.Action;

/**
 *
 * @author Marcellin
 */
public class Registrat extends javax.swing.JDialog {

    /** Creates new form Registrat */
    public Registrat(java.awt.Frame parent, String[] users) {
        super(parent);
        userNames = users;
        initComponents();
        getRootPane().setDefaultButton(saveButton);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lname = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        intrests_a = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        products_a = new javax.swing.JTextArea();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        user_field = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(livefacenew.LivefacenewApp.class).getContext().getResourceMap(Registrat.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        fname.setText(resourceMap.getString("fname.text")); // NOI18N
        fname.setName("fname"); // NOI18N

        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        lname.setText(resourceMap.getString("lname.text")); // NOI18N
        lname.setName("lname"); // NOI18N

        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        intrests_a.setColumns(20);
        intrests_a.setRows(5);
        intrests_a.setName("intrests_a"); // NOI18N
        jScrollPane1.setViewportView(intrests_a);

        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        products_a.setColumns(20);
        products_a.setRows(5);
        products_a.setName("products_a"); // NOI18N
        jScrollPane2.setViewportView(products_a);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(livefacenew.LivefacenewApp.class).getContext().getActionMap(Registrat.class, this);
        saveButton.setAction(actionMap.get("save_info")); // NOI18N
        saveButton.setText(resourceMap.getString("saveButton.text")); // NOI18N
        saveButton.setName("saveButton"); // NOI18N

        cancelButton.setAction(actionMap.get("closeAboutBox")); // NOI18N
        cancelButton.setText(resourceMap.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N

        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        user_field.setText(resourceMap.getString("user_field.text")); // NOI18N
        user_field.setName("user_field"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(jLabel1)
                .addContainerGap(167, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(49, 49, 49)
                .addComponent(user_field, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addGap(48, 48, 48))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(49, 49, 49)
                .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                    .addComponent(lname, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(saveButton)
                        .addGap(46, 46, 46)
                        .addComponent(cancelButton)))
                .addGap(49, 49, 49))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(user_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(cancelButton))
                .addGap(37, 37, 37))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//  
    public boolean saveb = false;
    @Action
    public void closeAboutBox() {
        setVisible(false);
        saveb = false;
        //this.dispose();
    }
    public String usern;
    private String[] userNames;

    @Action
    public void save_info() {

        saveb = true;
        
        usern = user_field.getText();
        boolean userav = true;
        for (int count_user = 0; count_user < userNames.length && userNames[count_user] != null; count_user++) {
            if (usern.trim().equals(userNames[count_user].trim())) {
                userav = false;
                break;
            }
        }

        //---get all required info
        String lnam = lname.getText();
        String fnam = fname.getText();
        String intr = intrests_a.getText();
         String addr = intrests_a.getText();
        String prod = products_a.getText();

        //----------------------
        //usern = user_field.getText();
        if (userav) {
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
                    int val = st.executeUpdate("INSERT INTO customer (user_n, fname, lname, Interest, Address) VALUES('" + usern + "','" + fnam + "','" + lnam + "','" + intr + "','" + addr+"')");
                    System.out.println("Status: " + val + "\n 1 row affected\n" + st.getResultSet().toString());
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
        }
        // hide();
        setVisible(false);
       
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField fname;
    private javax.swing.JTextArea intrests_a;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField lname;
    private javax.swing.JTextArea products_a;
    private javax.swing.JButton saveButton;
    public javax.swing.JTextField user_field;
    // End of variables declaration//GEN-END:variables
}