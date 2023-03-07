/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import java.sql.*;
import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;
import koneksi.koneksi;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;

/**
 *
 * @author nopal
 */
public class trans_reimburse extends javax.swing.JFrame {
private Connection conn = new koneksi().connect();
private String date,time;
String date1,date2;
SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");

java.util.Date month = new java.util.Date();
String var2 = formatter.format(month);
int check=0; String max=""; int sisa_cuti; int upah;

    

    /**
     * Creates new form reg_company
     */
    public trans_reimburse() {
        initComponents();
        kosong();
        aktif();
    }
    
    protected void aktif(){
        autoID();
        txt_nama.requestFocus();
        b_edit.setVisible(false);
        
//        SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm");
//        java.util.Date time = Calendar.getInstance().getTime();  
//        String strtime = timeformat.format(time);  

    };
    
  
    
    public void getDataid(String id){
        b_simpan.setVisible(false);
        b_edit.setVisible(true);
        txt_kode.setText(id);
        String a = txt_kode.getText();
        diajukan.setSelected(true);
        try{            
            String sql="SELECT reimburse.id,karyawan.nama,karyawan.department,karyawan.jabatan, reimburse.id_user, "
                    + "reimburse.nama_kegiatan,reimburse.alasan,reimburse.total,reimburse.date "
                    + "from Reimburse inner join karyawan on reimburse.id_user=karyawan.id "
                    + "where reimburse.id='"+a+"'";
            Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql);             
            while (rs.next()){
             txt_kode.setText(rs.getString(1));
             txt_nama.setText(rs.getString(2));
             txt_dept.setText(rs.getString(3));
             txt_jabatan.setText(rs.getString(4));
             txt_username.setText(rs.getString(5));
             txt_alasan.setText(rs.getString(6));
             txt_keterangan.setText(rs.getString(7));
             txt_total.setText(rs.getString(8));
             date_reimburse.setDate(rs.getDate(9));         
             }
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"data reimburse gagal dipanggil : "+ e);
            }
    }
    
    public void getDataid2(String id){
        autoID();
        b_simpan.setVisible(true); 
        txt_username.setText(id);
        String a = txt_username.getText();
        jPanel11.setVisible(false);
        jPanel12.setVisible(false);
        try{            
            String sql="SELECT nama, department, jabatan, id "         
                    + "from karyawan "
                    + "where karyawan.id='"+a+"'";
            Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql);             
            while (rs.next()){
             txt_nama.setText(rs.getString(1));
             txt_dept.setText(rs.getString(2));
             txt_jabatan.setText(rs.getString(3));
             txt_username.setText(rs.getString(4));
             }
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"data cuti gagal dipanggil : "+ e);
            }
    }
    
    protected void kosong(){
        autoID();
        txt_alasan.setText("");
        txt_alasan2.setText("");
        txt_keterangan.setText("");
        txt_total.setText("");
        diajukan.setSelected(true);
    };
    
    public void autoID(){
         //PM
        String id="";
        String var1 = "REM";
         
        //Seq berurut
        String var3 = null;
        newSeqMonth();
        if(check==0) { //check new seq
            var3 = "001";
        } else {
            String getSeqBefore = max; //Get from database seq max today
            int newSeq = Integer.parseInt(getSeqBefore) + 1;    
            if(newSeq >= 100) {
                System.out.println("Out of Maximal Seq");
            } else if (newSeq >= 10) {
                var3 = "0" + newSeq;
            } else if (newSeq >= 1) {
                var3 = "00" + newSeq;
            } else {
                System.out.println("Invalid Seq");
            }               
        }
        id=var1+var2+var3;
        txt_kode.setText(id);
        
     }
     
     public void newSeqMonth() {
         //TODO : Check database is today have seq
         //jika hari ini tidak punya seq, maka harus bikin seq baru = true
         String sql="select id from reimburse where id like '%"+var2+"%' order by id desc limit 1";
         try{
          Statement st = conn.createStatement(); ResultSet ars = st.executeQuery(sql); 
             if (ars.next()){check=1; 
             String cut=(ars.getString(1)); max=cut.substring(9);
             
                     }
             else {check=0;}             
         }
         catch (SQLException e){
            JOptionPane.showMessageDialog(null,"data gagal dipanggil : "+ e);
            }
    
         
     }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel5 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txt_kode = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        date_reimburse = new com.toedter.calendar.JDateChooser();
        jPanel8 = new javax.swing.JPanel();
        txt_alasan = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txt_nama = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txt_dept = new javax.swing.JLabel();
        txt_jabatan = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        txt_username = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel11 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        disetujui = new javax.swing.JRadioButton();
        ditolak = new javax.swing.JRadioButton();
        ditunda = new javax.swing.JRadioButton();
        revisi = new javax.swing.JRadioButton();
        diajukan = new javax.swing.JRadioButton();
        jPanel12 = new javax.swing.JPanel();
        txt_alasan2 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        txt_keterangan = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        b_edit = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        b_simpan = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(254, 255, 230));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(9, 10, 54));

        jLabel16.setBackground(new java.awt.Color(204, 204, 204));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(231, 238, 126));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Form Input Pengajuan Reimbursement");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 560, -1));

        jPanel3.setBackground(new java.awt.Color(251, 255, 161));

        txt_kode.setEditable(false);
        txt_kode.setBackground(new java.awt.Color(251, 255, 161));
        txt_kode.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_kode.setForeground(new java.awt.Color(9, 10, 54));
        txt_kode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kodeActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(204, 204, 204));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(9, 10, 54));
        jLabel10.setText("       No. Form Reimburse");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_kode, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_kode, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 69, -1, -1));

        jPanel7.setBackground(new java.awt.Color(251, 255, 161));

        jLabel14.setBackground(new java.awt.Color(204, 204, 204));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(9, 10, 54));
        jLabel14.setText("       Tanggal Kegiatan");

        date_reimburse.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                date_reimbursePropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(date_reimburse, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(date_reimburse, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 224, 558, -1));

        jPanel8.setBackground(new java.awt.Color(251, 255, 161));

        txt_alasan.setForeground(new java.awt.Color(9, 10, 54));

        jLabel25.setBackground(new java.awt.Color(204, 204, 204));
        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(9, 10, 54));
        jLabel25.setText("       Nama Kegiatan");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(txt_alasan, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_alasan, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 560, -1));

        jPanel6.setBackground(new java.awt.Color(251, 255, 161));

        txt_nama.setEditable(false);
        txt_nama.setBackground(new java.awt.Color(251, 255, 161));
        txt_nama.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_nama.setForeground(new java.awt.Color(9, 10, 54));
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });

        jLabel11.setBackground(new java.awt.Color(204, 204, 204));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(9, 10, 54));
        jLabel11.setText("       Nama");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_nama, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 103, 560, -1));

        jPanel9.setBackground(new java.awt.Color(251, 255, 161));

        jLabel13.setBackground(new java.awt.Color(204, 204, 204));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(9, 10, 54));
        jLabel13.setText("     Department");

        txt_dept.setBackground(new java.awt.Color(204, 204, 204));
        txt_dept.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_dept.setForeground(new java.awt.Color(9, 10, 54));
        txt_dept.setText("      Sisa Cuti Tahunan");

        txt_jabatan.setBackground(new java.awt.Color(204, 204, 204));
        txt_jabatan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_jabatan.setForeground(new java.awt.Color(9, 10, 54));
        txt_jabatan.setText("      Sisa Cuti Tahunan");

        jLabel19.setBackground(new java.awt.Color(204, 204, 204));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(9, 10, 54));
        jLabel19.setText("      Jabatan");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_dept, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_dept)
                .addComponent(txt_jabatan)
                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 137, 560, -1));

        jPanel10.setBackground(new java.awt.Color(251, 255, 161));

        txt_username.setEditable(false);
        txt_username.setBackground(new java.awt.Color(251, 255, 161));
        txt_username.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_username.setForeground(new java.awt.Color(9, 10, 54));
        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(204, 204, 204));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(9, 10, 54));
        jLabel18.setText("       Username");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_username, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 171, 558, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 210, 450, 8));

        jPanel11.setBackground(new java.awt.Color(251, 255, 161));

        jLabel27.setBackground(new java.awt.Color(204, 204, 204));
        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(9, 10, 54));
        jLabel27.setText("       Persetujuan Pengajuan Cuti   :");

        disetujui.setBackground(new java.awt.Color(251, 255, 161));
        buttonGroup1.add(disetujui);
        disetujui.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        disetujui.setText("Disetujui");
        disetujui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disetujuiActionPerformed(evt);
            }
        });

        ditolak.setBackground(new java.awt.Color(251, 255, 161));
        buttonGroup1.add(ditolak);
        ditolak.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ditolak.setText("Ditolak");

        ditunda.setBackground(new java.awt.Color(251, 255, 161));
        buttonGroup1.add(ditunda);
        ditunda.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ditunda.setText("Ditunda");

        revisi.setBackground(new java.awt.Color(251, 255, 161));
        buttonGroup1.add(revisi);
        revisi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        revisi.setText("Revisi");

        diajukan.setBackground(new java.awt.Color(251, 255, 161));
        buttonGroup1.add(diajukan);
        diajukan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        diajukan.setText("Diajukan");
        diajukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diajukanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(revisi)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(diajukan)
                            .addComponent(ditolak))
                        .addGap(73, 73, 73)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ditunda)
                            .addComponent(disetujui))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(disetujui)
                    .addComponent(diajukan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ditolak)
                    .addComponent(ditunda))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(revisi)
                .addContainerGap())
        );

        jPanel1.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 440, 558, -1));

        jPanel12.setBackground(new java.awt.Color(251, 255, 161));

        txt_alasan2.setForeground(new java.awt.Color(9, 10, 54));

        jLabel28.setBackground(new java.awt.Color(204, 204, 204));
        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(9, 10, 54));
        jLabel28.setText("       Alasan Persetujuan");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_alasan2, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(361, 361, 361))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_alasan2, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 558, -1));

        jPanel13.setBackground(new java.awt.Color(251, 255, 161));

        jLabel26.setBackground(new java.awt.Color(204, 204, 204));
        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(9, 10, 54));
        jLabel26.setText("     Total Pengeluaran");

        jLabel23.setBackground(new java.awt.Color(204, 204, 204));
        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(9, 10, 54));
        jLabel23.setText("Rp. ");

        txt_total.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_total.setForeground(new java.awt.Color(9, 10, 54));
        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel23))
            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel1.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 382, 558, -1));

        jPanel14.setBackground(new java.awt.Color(251, 255, 161));

        txt_keterangan.setForeground(new java.awt.Color(9, 10, 54));

        jLabel29.setBackground(new java.awt.Color(204, 204, 204));
        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(9, 10, 54));
        jLabel29.setText("       Keterangan Reimburse");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txt_keterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(355, 355, 355))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_keterangan, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 331, 558, -1));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(36, 426, 450, 8));

        jPanel4.setBackground(new java.awt.Color(254, 255, 230));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel16.setBackground(new java.awt.Color(9, 10, 54));
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
        });

        jLabel24.setBackground(new java.awt.Color(204, 204, 204));
        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(231, 238, 126));
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_back_24px.png"))); // NOI18N
        jLabel24.setText("   Kembali");
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 130, -1));

        b_edit.setBackground(new java.awt.Color(9, 10, 54));
        b_edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b_editMouseClicked(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(204, 204, 204));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(231, 238, 126));
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        jLabel17.setText("   Submit");
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout b_editLayout = new javax.swing.GroupLayout(b_edit);
        b_edit.setLayout(b_editLayout);
        b_editLayout.setHorizontalGroup(
            b_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(b_editLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        b_editLayout.setVerticalGroup(
            b_editLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        jPanel4.add(b_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, -1, -1));

        b_simpan.setBackground(new java.awt.Color(9, 10, 54));
        b_simpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b_simpanMouseClicked(evt);
            }
        });

        jLabel22.setBackground(new java.awt.Color(204, 204, 204));
        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(231, 238, 126));
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_save_24px.png"))); // NOI18N
        jLabel22.setText("   Simpan");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout b_simpanLayout = new javax.swing.GroupLayout(b_simpan);
        b_simpan.setLayout(b_simpanLayout);
        b_simpanLayout.setHorizontalGroup(
            b_simpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(b_simpanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        b_simpanLayout.setVerticalGroup(
            b_simpanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
        );

        jPanel4.add(b_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 120, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 614, 558, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 680));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
        // TODO add your handling code here:
        this.dispose();
        
    }//GEN-LAST:event_jPanel16MouseClicked

    private void b_simpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_simpanMouseClicked
        // TODO add your handling code here:
        String a=txt_username.getText(); 
        String sql="Insert into reimburse values(?,?,?,?,?,?,?,?)";
 
  
        try{                
                   PreparedStatement stat=conn.prepareStatement(sql);
                   stat.setString(1,txt_kode.getText());
                   stat.setString(2,txt_username.getText());
                   stat.setString(3,txt_alasan.getText());
                   stat.setString(4,txt_keterangan.getText());
                   stat.setString(5,txt_total.getText());
                   stat.setString(6,date1);
                   stat.setString(7,"Diajukan");
                   stat.setString(8,txt_alasan2.getText());  
                   stat.executeUpdate();
 
            JOptionPane.showMessageDialog(null,"Data Reimburse Berhasil Disimpan");
            kosong();
        }
        catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Data Gagal Disimpan : "+ e);
        }    
        
    }//GEN-LAST:event_b_simpanMouseClicked

    private void b_editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_editMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_b_editMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        // mengambil ukuran layar
        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();

        // membuat titik x dan y
        int x = layar.width / 2  - this.getSize().width / 2;
        int y = layar.height / 2 - this.getSize().height / 2;

        this.setLocation(x, y);
    }//GEN-LAST:event_formWindowOpened

    private void txt_kodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kodeActionPerformed

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameActionPerformed

    private void disetujuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disetujuiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_disetujuiActionPerformed

    private void diajukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diajukanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_diajukanActionPerformed

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
         //TODO add your handling code here:
        String a=txt_username.getText();
        String kode=txt_kode.getText();
        String sql="update reimburse set status=?, ket_status=? where id='"+kode+"'";
        
        String status="";
        
        if (disetujui.isSelected()){
        status="Disetujui";
        }
        else if (ditolak.isSelected()){
        status="Ditolak";
        }
        else if (ditunda.isSelected()){
        status="Ditunda";
        }
        else if (revisi.isSelected()){
        status="Revisi";
        }   
        
        String asql="insert into logs_transaksi values (?,?,?,?,?,?)";
        if (status.equals("Disetujui")){
              try{                
                   PreparedStatement astat=conn.prepareStatement(asql);
                   astat.setString(1,a);
                   astat.setString(2,kode);  
                   astat.setString(3,"Reimbursement");
                   astat.setString(4, date1);
                   astat.setString(5,txt_total.getText());
                    astat.setInt(6,0);
                   astat.executeUpdate();}
              catch(SQLException e){ JOptionPane.showMessageDialog(null,"Data Gagal Disimpan : "+ e);}
        } else{}
            try{
                   PreparedStatement stat=conn.prepareStatement(sql);
                   stat.setString(1,status);
                   stat.setString(2,txt_alasan2.getText());
                   stat.executeUpdate();
                   JOptionPane.showMessageDialog(null,"Data Approval Reimburse Berhasil Disimpan");
                   this.dispose();
            }
            catch(SQLException e){
             JOptionPane.showMessageDialog(null,"Data Gagal Disimpan : "+ e);
            }
            
        
    }//GEN-LAST:event_jLabel17MouseClicked

    private void date_reimbursePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_date_reimbursePropertyChange
        // TODO add your handling code here:
        if (date_reimburse.getDate() !=null){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         date1 = sdf.format(date_reimburse.getDate());
        }
    }//GEN-LAST:event_date_reimbursePropertyChange

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel b_edit;
    private javax.swing.JPanel b_simpan;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser date_reimburse;
    private javax.swing.JRadioButton diajukan;
    private javax.swing.JRadioButton disetujui;
    private javax.swing.JRadioButton ditolak;
    private javax.swing.JRadioButton ditunda;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    private javax.swing.JRadioButton revisi;
    private javax.swing.JTextField txt_alasan;
    private javax.swing.JTextField txt_alasan2;
    private javax.swing.JLabel txt_dept;
    private javax.swing.JLabel txt_jabatan;
    private javax.swing.JTextField txt_keterangan;
    private javax.swing.JTextField txt_kode;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
