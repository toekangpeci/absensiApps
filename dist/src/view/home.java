/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import java.awt.Color;
import java.awt.Font; 
import java.awt.*;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import koneksi.koneksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author hana
 */
public class home extends javax.swing.JFrame {
    int int_data_list,int_home_list,int_items_list, int_trans_list, int_report_list, int_input_list = 0;
    private DefaultTableModel tabmode,tabmode2;
    private Connection conn = new koneksi().connect();
    public static String id_ekaryawan,id_jabatan,id_department,id_cuti,id_lembur,id_reimburse,id_dinas,id_tipe,id_karyawan,id_trans_barang = "";
      SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
      java.util.Date month = new java.util.Date();
      String var2 = formatter.format(month);
      SimpleDateFormat formatter2 = new SimpleDateFormat("MM-yyyy");
      java.util.Date date = new java.util.Date();
      String txtdate = formatter2.format(date);
      SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy");
      java.util.Date year = new java.util.Date();
      String years = formatter3.format(year);
      //public static String uname1;
      public String uname2;
      
    // public reg_company dataa=null;

    /**
     * Creates new form home
     */
    public home() {
        initComponents();
        Locale locale = new Locale("id","ID");
        Locale.setDefault(locale);
        coloring(); coloring2(); coloring3(); coloring5(); 
        panelHome();
    }
    
    public void getId(String id){
        txtUser.setText(login_form.user);
        panelHome();
    
    }
    
    public void panelHome(){
    txt_date.setText(txtdate);    
    txt_year.setText(years);   
    String uname=txtUser.getText();
    uname2=uname; 
    
    String sql=" select karyawan.nama, karyawan.department, karyawan.jabatan, cuti_jml.cuti_tahunan, cuti_jml.cuti_sisa,karyawan.nik"
            + " from karyawan inner join cuti_jml on karyawan.id = cuti_jml.id_user where karyawan.id='"+uname+"'";
    String asql="select count(id) from cuti_trans where (id_user='"+uname+"' and mulai_cuti like '%"+years+"%')";
    String bsql="select count(id) from lembur_trans where (id_user='"+uname+"' and tanggal like '%"+years+"%')";
    String csql="select count(id) from reimburse where (id_user='"+uname+"' and date like '%"+years+"%')";
    String dsql="select count(id_dinas) from dinas_luar where (id_user='"+uname+"' and mulai_dinas like '%"+years+"%')";
    
    try{
             Statement st = conn.createStatement(); 
             ResultSet rs = st.executeQuery(sql);
             while (rs.next()){
             txtNama.setText(rs.getString(1));
             txtDept.setText(rs.getString(2));
             txtJabatan.setText(rs.getString(3));
             txtJatah.setText(rs.getString(4));
             txtSisa.setText(rs.getString(5));
             txtNik.setText(rs.getString(6));}
             ResultSet ars = st.executeQuery(asql);
             while (ars.next()){txtCuti.setText(ars.getString(1));}
             ResultSet brs = st.executeQuery(bsql);
             while (brs.next()){txtLembur.setText(brs.getString(1));}
             ResultSet crs = st.executeQuery(csql);
             while (crs.next()){txtReimburse.setText(crs.getString(1));}
             ResultSet drs = st.executeQuery(dsql);
             while (drs.next()){txtDinas.setText(drs.getString(1));}    
    }
    catch(Exception e){JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);}
    tab_home(); tab_home2();
    }
    
    public void combo_department(){
        
        cb_department1.removeAllItems();
        cb_department1.addItem(" - Pilih Department - ");  
         
        try { 
            
            String sql = "select kode, nama from department";           
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery(sql); 
            while (r.next()) {
                cb_department1.addItem(r.getString(1)+" - "+r.getString(2));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }         
    } 
    
     public void combo_karyawan(){
        
        cb_slipKaryawan.removeAllItems();
        cb_slipKaryawan.addItem(" - Pilih Karyawan - ");  
        cb_cuti.removeAllItems();
        cb_cuti.addItem(" - Pilih Karyawan - ");  
        cb_lembur.removeAllItems();
        cb_lembur.addItem(" - Pilih Karyawan - ");  
        cb_reimburse.removeAllItems();
        cb_reimburse.addItem(" - Pilih Karyawan - ");  
        cb_dinas.removeAllItems();
        cb_dinas.addItem(" - Pilih Karyawan - ");  
        
        try { 
            
            String sql = "select id,nama from karyawan";           
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery(sql); 
            while (r.next()) {
                cb_slipKaryawan.addItem(r.getString(1)+" - "+r.getString(2));
                cb_cuti.addItem(r.getString(1)+" - "+r.getString(2));
                cb_lembur.addItem(r.getString(1)+" - "+r.getString(2));
                cb_reimburse.addItem(r.getString(1)+" - "+r.getString(2));
                cb_dinas.addItem(r.getString(1)+" - "+r.getString(2));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }         
    } 
    
        public void cetakAllKaryawan(){
        try{
           
            String path="./src/report/repKaryawan.jasper";
            //int b = Integer.parseInt(a);
            HashMap parameter = new HashMap();
            //parameter.put("id_dept",b);
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }
        
        public void cetakAllDepartment(){
        try{
           
            String path="./src/report/repDepartment.jasper";
            //int b = Integer.parseInt(a);
            HashMap parameter = new HashMap();
            //parameter.put("id_dept",b);
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }
        
        public void cetakAllJabatan(){
        try{
           
            String path="./src/report/repJabatan.jasper";
            //int b = Integer.parseInt(a);
            HashMap parameter = new HashMap();
            //parameter.put("id_dept",b);
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }
        
         public void cetakSlip(){
             String b=cb_slipKaryawan.getSelectedItem().toString();
             String trimmed = b.replaceAll(" .*", "");
//             DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
//             String month = monthFormatter.format(Month.values()[slipMonth.getMonth()]);
             int bulan= slipMonth.getMonth()+1;
             int tahun  = slipYear.getYear();

        try{     
            String path="./src/report/repSlipGaji.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("id_karyawan",trimmed);
            parameter.put("periode",bulan);
            parameter.put("tahun",tahun);
            System.out.println(bulan+"  "+tahun+"  "+trimmed);
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }
            
         public void cetakRekap(){
             String b=cb_department1.getSelectedItem().toString();
             String trimmed = b.replaceAll(" .*", "");
//             DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
//             String month = monthFormatter.format(Month.values()[slipMonth.getMonth()]);
             int bulan= rekapMonth.getMonth()+1;
             int tahun  = rekapYear.getYear();
             

        try{     
            String path="./src/report/repRekap.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("id_dept",trimmed);
            parameter.put("periode",bulan);
            parameter.put("tahun",tahun);
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }
         
          public void cetakCuti(){
             String b=cb_cuti.getSelectedItem().toString();
             String trimmed = b.replaceAll(" .*", "");
             int bulan= cutiMonth.getMonth()+1;
             int tahun  = cutiYear.getYear();
        try{     
            String path="./src/report/repCuti.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("id_karyawan",trimmed);
            parameter.put("periode",bulan);
            parameter.put("tahun",tahun);
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }
                  
          public void cetakLembur(){
             String b=cb_lembur.getSelectedItem().toString();
             String trimmed = b.replaceAll(" .*", "");
             int bulan= lemburMonth.getMonth()+1;
             int tahun  = lemburYear.getYear();
        try{     
            String path="./src/report/repLembur.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("id_karyawan",trimmed);
            parameter.put("periode",bulan);
            parameter.put("tahun",tahun);
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }
         
                  
          public void cetakReimburse(){
             String b=cb_reimburse.getSelectedItem().toString();
             String trimmed = b.replaceAll(" .*", "");
             int bulan= reimburseMonth.getMonth()+1;
             int tahun  = reimburseYear.getYear();
        try{     
            String path="./src/report/repReimburse.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("id_karyawan",trimmed);
            parameter.put("periode",bulan);
            parameter.put("tahun",tahun);
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }
         
                  
          public void cetakDinas(){
             String b=cb_dinas.getSelectedItem().toString();
             String trimmed = b.replaceAll(" .*", "");
             int bulan= dinasMonth.getMonth()+1;
             int tahun  = dinasYear.getYear();
        try{     
            String path="./src/report/repDinas.jasper";
            
            HashMap parameter = new HashMap();
            parameter.put("id_karyawan",trimmed);
            parameter.put("periode",bulan);
            parameter.put("tahun",tahun);
            JasperPrint print = JasperFillManager.fillReport(path,parameter,conn);
            JasperViewer.viewReport(print,false);
        }
        catch (Exception ex){
                JOptionPane.showMessageDialog(null,"DOKUMEN TIDAK ADA"+ex);
        }
    }
         
         
         
     public void tab_home(){
        tab_home.setAutoCreateRowSorter(true);
        Object[] Baris = {"Nama","Department","Lama Cuti","Tanggal Mulai Cuti","Jenis Cuti"};
        tabmode = new DefaultTableModel(null,Baris);
        try {
            String sql="SELECT karyawan.nama, karyawan.department, cuti_trans.jumlah_cuti, cuti_trans.mulai_cuti,cuti_trans.jenis_cuti "
                    + "FROM cuti_trans inner join karyawan on cuti_trans.id_user=karyawan.id "
                    + "where (cuti_trans.status='Disetujui' and cuti_trans.id like '%"+var2+"%') order by cuti_trans.id asc";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5)
            });
            }
            tab_home.setModel(tabmode);
            tab_home.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
     
     public void tab_home2(){
        tab_home2.setAutoCreateRowSorter(true);
        Object[] Baris = {"Nama","Department","Lama Dinas","Tanggal Mulai Dinas"};
        tabmode = new DefaultTableModel(null,Baris);
        try {
            String sql="SELECT karyawan.nama, karyawan.department, dinas_luar.lama_dinas,dinas_luar.mulai_dinas "
                    + "FROM dinas_luar inner join karyawan on dinas_luar.id_user=karyawan.id "
                    + "where (dinas_luar.status='Disetujui' and dinas_luar.id_dinas like '%"+var2+"%') order by dinas_luar.id_dinas asc";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4)
            });
            }
            tab_home2.setModel(tabmode);
            tab_home2.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    
    
        public void tab_transDinas(){
        tab_transDinas.setAutoCreateRowSorter(true);
        Object[] Baris = {"ID Dinas","Pembuat","Lama Dinas Luar","Tanggal Mulai Dinas Luar","Status Pengajuan Cuti"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txt_dinas.getText();
        try {
            String sql="SELECT dinas_luar.id_dinas, karyawan.nama, dinas_luar.lama_dinas, dinas_luar.mulai_dinas, dinas_luar.status  "
                    + "FROM dinas_luar inner join karyawan on dinas_luar.id_user=karyawan.id "
                    + "where (dinas_luar.id_dinas like '%"+cariitem+"%' or karyawan.nama like'%"+cariitem+"%' or dinas_luar.id_user like'%"+cariitem+"%') order by dinas_luar.id_dinas asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5)
            });
            }
            tab_transDinas.setModel(tabmode);
            tab_transDinas.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    
     public void tab_transLembur(){
        tab_transLembur.setAutoCreateRowSorter(true);
        Object[] Baris = {"ID Lembur","Pembuat","Lama Lembur","Tanggal Lembur","Status Pengajuan Cuti"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txt_lembur.getText();
        try {
            String sql="SELECT lembur_trans.id, karyawan.nama, lembur_trans.durasi, lembur_trans.tanggal, lembur_trans.status  "
                    + "FROM lembur_trans inner join karyawan on lembur_trans.id_user=karyawan.id "
                    + "where (lembur_trans.id like '%"+cariitem+"%' or karyawan.nama like'%"+cariitem+"%' or lembur_trans.id_user like'%"+cariitem+"%') order by lembur_trans.id asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5)
            });
            }
            tab_transLembur.setModel(tabmode);
            tab_transLembur.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
     
     public void tab_transReimburse(){
        tab_transLembur.setAutoCreateRowSorter(true);
        Object[] Baris = {"ID Lembur","Pembuat","Nama Kegiatan","Tanggal Kegiatan","Status Pengajuan Reimburse"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txt_reimburse.getText();
        try {
            String sql="SELECT reimburse.id, karyawan.nama, reimburse.alasan, reimburse.date, reimburse.status  "
                    + "FROM reimburse inner join karyawan on reimburse.id_user=karyawan.id "
                    + "where (reimburse.id like '%"+cariitem+"%' or karyawan.nama like'%"+cariitem+"%' or reimburse.id_user like'%"+cariitem+"%') order by reimburse.id asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5)
            });
            }
            tab_transReimburse.setModel(tabmode);
            tab_transReimburse.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    
    public void tab_transCuti(){
        tab_transCuti.setAutoCreateRowSorter(true);
        Object[] Baris = {"ID Cuti","Pembuat","Jumlah Cuti","Mulai Cuti","Status Pengajuan Cuti"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txt_transCuti.getText();
        try {
            String sql="SELECT cuti_trans.id, karyawan.nama, cuti_trans.jumlah_cuti, cuti_trans.mulai_cuti, cuti_trans.status  "
                    + "FROM cuti_trans inner join karyawan on cuti_trans.id_user=karyawan.id "
                    + "where (cuti_trans.id like '%"+cariitem+"%' or karyawan.nama like'%"+cariitem+"%' or cuti_trans.id_user like'%"+cariitem+"%') order by cuti_trans.id asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5)
            });
            }
            tab_transCuti.setModel(tabmode);
            tab_transCuti.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    
     public void tab_transApprovalCuti(){
        tab_transCuti.setAutoCreateRowSorter(true);
        Object[] Baris = {"ID Cuti","Pembuat","Jumlah Cuti","Mulai Cuti","Status Pengajuan Cuti"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txt_AppCuti.getText();
        
        //query lama > (cuti_trans.id like '%"+cariitem+"%' or karyawan.nama like'%"+cariitem+"%' or cuti_trans.id_user like'%"+cariitem+"%') 
        try {
            String sql="SELECT cuti_trans.id, karyawan.nama, cuti_trans.jumlah_cuti, cuti_trans.mulai_cuti, cuti_trans.status  "
                    + "FROM cuti_trans inner join karyawan on cuti_trans.id_user=karyawan.id "
                    + "where karyawan.id_atasan='"+login_form.uName+"' and (cuti_trans.status='Diajukan' or cuti_trans.status='Ditunda' ) order by cuti_trans.id asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5)
            });
            }
            tab_appCuti.setModel(tabmode);
            tab_appCuti.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
     
     public void tab_transApprovalDinas(){
        tab_appDinas.setAutoCreateRowSorter(true);
        Object[] Baris = {"ID Dinas","Pembuat","Lama Dinas Luar","Tanggal Mulai Dinas Luar","Upah Dinas Luar","Status Pengajuan"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txt_appDinas.getText();
           
        try {
            String sql="SELECT dinas_luar.id_dinas, karyawan.nama, dinas_luar.lama_dinas, dinas_luar.mulai_dinas,dinas_luar.pendapatan_dinas, dinas_luar.status  "
                    + "FROM dinas_luar inner join karyawan on dinas_luar.id_user=karyawan.id "
                    + "where karyawan.id_atasan='"+login_form.uName+"' and (dinas_luar.status='Diajukan' or dinas_luar.status='Ditunda' ) order by dinas_luar.id_dinas asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5),
                hasil.getString(6)
            });
            }
            tab_appDinas.setModel(tabmode);
            tab_appDinas.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
     
     public void tab_transApprovalLembur(){
        tab_transCuti.setAutoCreateRowSorter(true);
        Object[] Baris = {"ID Lembur","Pembuat","Lama Lembur","Total Upah Lembur","Status Pengajuan Lembur"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txt_AppLembur.getText();
        
        //query lama > (cuti_trans.id like '%"+cariitem+"%' or karyawan.nama like'%"+cariitem+"%' or cuti_trans.id_user like'%"+cariitem+"%') 
        try {
            String sql="SELECT lembur_trans.id, karyawan.nama, lembur_trans.durasi,lembur_trans.total_lembur, lembur_trans.status  "
                    + "FROM lembur_trans inner join karyawan on lembur_trans.id_user=karyawan.id "
                    + "where karyawan.id_atasan='"+login_form.uName+"' and (lembur_trans.status='Diajukan' or lembur_trans.status='Ditunda' ) order by lembur_trans.id asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5)
            });
            }
            tab_appLembur.setModel(tabmode);
            tab_appLembur.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
     
     public void tab_transApprovalReimburse(){
        tab_appReimburse.setAutoCreateRowSorter(true);
        Object[] Baris = {"ID Lembur","Pembuat","Nama Kegiatan","Tanggal Kegiatan","Total Reimburse","Status Pengajuan Reimburse"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txt_AppReimburse.getText();
       
        try {
            String sql="SELECT reimburse.id, karyawan.nama, reimburse.alasan, reimburse.date, reimburse.total, reimburse.status   "
                    + "FROM reimburse inner join karyawan on reimburse.id_user=karyawan.id "
                    + "where karyawan.id_atasan='"+login_form.uName+"' and (reimburse.status='Diajukan' or reimburse.status='Ditunda' ) order by reimburse.id asc";
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5),
                hasil.getString(6)
            });
            }
            tab_appReimburse.setModel(tabmode);
            tab_appReimburse.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    
    
    public void tab_jabatan(){
        tab_department.setAutoCreateRowSorter(true);
        Object[] Baris = {"kode Jabatan","Nama Jabatan","Keterangan","Upah Lembur"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txt_department.getText();
        try {
            String sql="SELECT kode_jabatan, nama_jabatan,keterangan,upah_lembur "
                    + "FROM jabatan "
                    + "where (kode_jabatan like '%"+cariitem+"%' or nama_jabatan like'%"+cariitem+"%') order by kode_jabatan asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4)
            });
            }
            tab_jabatan.setModel(tabmode);
            tab_jabatan.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    
    
    public void tab_department(){
        tab_department.setAutoCreateRowSorter(true);
        Object[] Baris = {"kode Dept.","Nama Department","Keterangan"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txt_department.getText();
        try {
            String sql="SELECT kode, nama,keterangan "
                    + "FROM department "
                    + "where (kode like '%"+cariitem+"%' or nama like'%"+cariitem+"%') order by kode asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3)
            });
            }
            tab_department.setModel(tabmode);
            tab_department.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    
    public void tab_karyawan(){
        tab_karyawan.setAutoCreateRowSorter(true);
        Object[] Baris = {"USER ID","NIK","Nama","Department","Jabatan"};
        tabmode = new DefaultTableModel(null,Baris);
        String cariitem=txt_karyawan.getText();
        try {
            String sql="SELECT id,nik,nama,department,jabatan "
                    + "FROM karyawan "
                    + "where (id like '%"+cariitem+"%' or nik like '%"+cariitem+"%' or nama like'%"+cariitem+"%') order by nik asc" ;
            Statement stat = conn.createStatement();
            ResultSet hasil = stat.executeQuery(sql);
            while (hasil.next()){
                tabmode.addRow(new Object[]{
                hasil.getString(1),
                hasil.getString(2),
                hasil.getString(3),
                hasil.getString(4),
                hasil.getString(5)
            });
            }
            tab_karyawan.setModel(tabmode);
            tab_karyawan.setDefaultEditor(Object.class, null);

        } catch (Exception e){
            JOptionPane.showMessageDialog(null,"data gagal ditemukan, Kode : "+ e);
        }    
    }
    
    
    private void coloring(){
        if (int_data_list==1){
            menu_data_list.setVisible(true);
            menu_2.setBackground(new Color(67,131,113));
            menu_data_list.setBackground(new Color(67,131,113));
        }      
        else if (int_data_list==0){
            menu_data_list.setVisible(false);
            menu_2.setBackground(new Color(32,74,86));
            menu_data_list.setBackground(new Color(32,74,86));
        }         
    }

    private void coloring2(){
         if (int_home_list==1){
            menu_1.setBackground(new Color(67,131,113));
        }
          else if (int_home_list==0){         
            menu_1.setBackground(new Color(32,74,86));
        }    
    }

    private void coloring3(){
        if (int_items_list==1){
            menu_items_list.setVisible(true);
            menu_3.setBackground(new Color(67,131,113));
            menu_items_list.setBackground(new Color(67,131,113));
        }      
        else if (int_items_list==0){
            menu_items_list.setVisible(false);
            menu_3.setBackground(new Color(32,74,86));
            menu_items_list.setBackground(new Color(32,74,86));
        }         
    }    

    private void coloring4(){
         if (int_trans_list==1){
            menu_4.setBackground(new Color(67,131,113));
        }
          else if (int_trans_list==0){         
            menu_4.setBackground(new Color(32,74,86));
        }    
    }
    
    private void coloring5(){
         if (int_input_list==1){
            menu_input_list.setVisible(true);
            menu_5.setBackground(new Color(67,131,113));
            menu_input_list.setBackground(new Color(67,131,113));
        }      
        else if (int_input_list==0){
            menu_input_list.setVisible(false);
            menu_5.setBackground(new Color(32,74,86));
            menu_input_list.setBackground(new Color(32,74,86));
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

        jPanel1 = new javax.swing.JPanel();
        p_sidebar = new javax.swing.JPanel();
        menu_1 = new javax.swing.JPanel();
        menu_home = new javax.swing.JLabel();
        menu_2 = new javax.swing.JPanel();
        menu_data = new javax.swing.JLabel();
        menu_data_list = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        menu_3 = new javax.swing.JPanel();
        menu_items = new javax.swing.JLabel();
        menu_items_list = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        menu_4 = new javax.swing.JPanel();
        menu_home1 = new javax.swing.JLabel();
        menu_5 = new javax.swing.JPanel();
        menu_input = new javax.swing.JLabel();
        menu_input_list = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        menu_6 = new javax.swing.JPanel();
        menu_home2 = new javax.swing.JLabel();
        p_utama = new javax.swing.JPanel();
        p_home = new javax.swing.JPanel();
        p_perusahaan_header9 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        p_perusahaan_main9 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        noGaji = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        txtUser = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        txtNama = new javax.swing.JLabel();
        txtDept = new javax.swing.JLabel();
        txtJabatan = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        txtJatah = new javax.swing.JLabel();
        txtSisa = new javax.swing.JLabel();
        txtNik = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        b_password = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        tab_home = new javax.swing.JTable();
        jLabel71 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        txt_year = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        txtCuti = new javax.swing.JLabel();
        txtLembur = new javax.swing.JLabel();
        txtDinas = new javax.swing.JLabel();
        txtReimburse = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        bCuti = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        bLembur = new javax.swing.JLabel();
        jPanel54 = new javax.swing.JPanel();
        bReimburse = new javax.swing.JLabel();
        jPanel55 = new javax.swing.JPanel();
        bDinas = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        tab_home2 = new javax.swing.JTable();
        txt_date = new javax.swing.JLabel();
        jPanel50 = new javax.swing.JPanel();
        b_password1 = new javax.swing.JLabel();
        p_reports = new javax.swing.JPanel();
        p_perusahaan_header8 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        p_perusahaan_main8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        repKaryawan = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        cb_cuti = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        cb_department1 = new javax.swing.JComboBox<>();
        jLabel68 = new javax.swing.JLabel();
        rekapMonth = new com.toedter.calendar.JMonthChooser();
        rekapYear = new com.toedter.calendar.JYearChooser();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel56 = new javax.swing.JPanel();
        repRekap = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        cb_slipKaryawan = new javax.swing.JComboBox<>();
        jLabel78 = new javax.swing.JLabel();
        slipMonth = new com.toedter.calendar.JMonthChooser();
        slipYear = new com.toedter.calendar.JYearChooser();
        jPanel57 = new javax.swing.JPanel();
        repSlip = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel80 = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        repDepartment = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jPanel49 = new javax.swing.JPanel();
        repJabatan = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        repCuti = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel83 = new javax.swing.JLabel();
        cb_lembur = new javax.swing.JComboBox<>();
        jPanel59 = new javax.swing.JPanel();
        repLembur = new javax.swing.JLabel();
        cb_reimburse = new javax.swing.JComboBox<>();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel60 = new javax.swing.JPanel();
        repReimburse = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        cb_dinas = new javax.swing.JComboBox<>();
        jPanel61 = new javax.swing.JPanel();
        repDinas = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        cutiMonth = new com.toedter.calendar.JMonthChooser();
        cutiYear = new com.toedter.calendar.JYearChooser();
        jLabel86 = new javax.swing.JLabel();
        lemburMonth = new com.toedter.calendar.JMonthChooser();
        lemburYear = new com.toedter.calendar.JYearChooser();
        jLabel87 = new javax.swing.JLabel();
        reimburseMonth = new com.toedter.calendar.JMonthChooser();
        reimburseYear = new com.toedter.calendar.JYearChooser();
        jLabel88 = new javax.swing.JLabel();
        dinasMonth = new com.toedter.calendar.JMonthChooser();
        dinasYear = new com.toedter.calendar.JYearChooser();
        p_department = new javax.swing.JPanel();
        p_perusahaan_header1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        p_perusahaan_main1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tab_department = new javax.swing.JTable();
        txt_department = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        department_b_tambah = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        department_b_hapus = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        Department_b_edit = new javax.swing.JLabel();
        p_user = new javax.swing.JPanel();
        p_perusahaan_header3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        p_perusahaan_main3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tab_karyawan = new javax.swing.JTable();
        txt_karyawan = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        department_b_tambah1 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        karyawan_b_hapus = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        karyawan_b_edit = new javax.swing.JLabel();
        p_jabatan = new javax.swing.JPanel();
        p_perusahaan_header2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        p_perusahaan_main2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tab_jabatan = new javax.swing.JTable();
        txt_transCuti = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        department_b_tambah2 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        department_b_hapus1 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        Department_b_edit1 = new javax.swing.JLabel();
        p_transCuti = new javax.swing.JPanel();
        p_perusahaan_header4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        p_perusahaan_main4 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tab_transCuti = new javax.swing.JTable();
        txt_department2 = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        department_b_tambah3 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        department_b_hapus2 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        Department_b_edit2 = new javax.swing.JLabel();
        p_transAppCuti = new javax.swing.JPanel();
        p_perusahaan_header5 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        p_perusahaan_main5 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tab_appCuti = new javax.swing.JTable();
        txt_AppCuti = new javax.swing.JTextField();
        jPanel28 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        Department_b_edit3 = new javax.swing.JLabel();
        p_transLembur = new javax.swing.JPanel();
        p_perusahaan_header6 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        p_perusahaan_main6 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tab_transLembur = new javax.swing.JTable();
        txt_lembur = new javax.swing.JTextField();
        jPanel26 = new javax.swing.JPanel();
        department_b_tambah4 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        department_b_hapus3 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        Department_b_edit4 = new javax.swing.JLabel();
        p_transAppLembur = new javax.swing.JPanel();
        p_perusahaan_header7 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        p_perusahaan_main7 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tab_appLembur = new javax.swing.JTable();
        txt_AppLembur = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        Department_b_edit5 = new javax.swing.JLabel();
        p_transReimburse = new javax.swing.JPanel();
        p_perusahaan_header10 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        p_perusahaan_main10 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tab_transReimburse = new javax.swing.JTable();
        txt_reimburse = new javax.swing.JTextField();
        jPanel34 = new javax.swing.JPanel();
        department_b_tambah5 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        department_b_hapus4 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        Department_b_edit6 = new javax.swing.JLabel();
        p_transAppReimburse = new javax.swing.JPanel();
        p_perusahaan_header11 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        p_perusahaan_main11 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tab_appReimburse = new javax.swing.JTable();
        txt_AppReimburse = new javax.swing.JTextField();
        jPanel38 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        Department_b_edit7 = new javax.swing.JLabel();
        p_transDinas = new javax.swing.JPanel();
        p_perusahaan_header12 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        p_perusahaan_main12 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tab_transDinas = new javax.swing.JTable();
        txt_dinas = new javax.swing.JTextField();
        jPanel40 = new javax.swing.JPanel();
        department_b_tambah6 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        department_b_hapus5 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        Department_b_edit8 = new javax.swing.JLabel();
        p_transAppDinas = new javax.swing.JPanel();
        p_perusahaan_header13 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        p_perusahaan_main13 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        tab_appDinas = new javax.swing.JTable();
        txt_appDinas = new javax.swing.JTextField();
        jPanel44 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        Department_b_edit9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        p_sidebar.setBackground(new java.awt.Color(32, 74, 86));

        menu_1.setBackground(new java.awt.Color(32, 74, 86));

        menu_home.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        menu_home.setForeground(new java.awt.Color(231, 238, 126));
        menu_home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_home_40px_1.png"))); // NOI18N
        menu_home.setText("   H O M E");
        menu_home.setToolTipText("");
        menu_home.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu_homeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout menu_1Layout = new javax.swing.GroupLayout(menu_1);
        menu_1.setLayout(menu_1Layout);
        menu_1Layout.setHorizontalGroup(
            menu_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(menu_home)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menu_1Layout.setVerticalGroup(
            menu_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menu_1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(menu_home)
                .addContainerGap())
        );

        menu_2.setBackground(new java.awt.Color(32, 74, 86));

        menu_data.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        menu_data.setForeground(new java.awt.Color(231, 238, 126));
        menu_data.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_data_recovery_40px.png"))); // NOI18N
        menu_data.setText("   MANAGEMENT");
        menu_data.setToolTipText("");
        menu_data.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_data.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu_dataMouseClicked(evt);
            }
        });

        menu_data_list.setBackground(new java.awt.Color(32, 74, 86));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(231, 238, 126));
        jLabel5.setText("Data Karyawan");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(231, 238, 126));
        jLabel6.setText("Data Department");
        jLabel6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(231, 238, 126));
        jLabel8.setText("Data Jabatan");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jLabel8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jLabel8KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout menu_data_listLayout = new javax.swing.GroupLayout(menu_data_list);
        menu_data_list.setLayout(menu_data_listLayout);
        menu_data_listLayout.setHorizontalGroup(
            menu_data_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_data_listLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(menu_data_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addContainerGap())
        );
        menu_data_listLayout.setVerticalGroup(
            menu_data_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_data_listLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(5, 5, 5)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout menu_2Layout = new javax.swing.GroupLayout(menu_2);
        menu_2.setLayout(menu_2Layout);
        menu_2Layout.setHorizontalGroup(
            menu_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_2Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(menu_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menu_data_list, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menu_data, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        menu_2Layout.setVerticalGroup(
            menu_2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu_data)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menu_data_list, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menu_3.setBackground(new java.awt.Color(32, 74, 86));

        menu_items.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        menu_items.setForeground(new java.awt.Color(231, 238, 126));
        menu_items.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menu_items.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_pass_fail_40px.png"))); // NOI18N
        menu_items.setText("   APPROVAL");
        menu_items.setToolTipText("");
        menu_items.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_items.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu_itemsMouseClicked(evt);
            }
        });

        menu_items_list.setBackground(new java.awt.Color(32, 74, 86));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(231, 238, 126));
        jLabel21.setText("Approval Cuti");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel21MouseClicked(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(231, 238, 126));
        jLabel22.setText("Approval Lembur");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(231, 238, 126));
        jLabel23.setText("Approval Reimburse");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });

        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(231, 238, 126));
        jLabel43.setText("Approval Dinas Luar");
        jLabel43.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel43MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout menu_items_listLayout = new javax.swing.GroupLayout(menu_items_list);
        menu_items_list.setLayout(menu_items_listLayout);
        menu_items_listLayout.setHorizontalGroup(
            menu_items_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_items_listLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(menu_items_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        menu_items_listLayout.setVerticalGroup(
            menu_items_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_items_listLayout.createSequentialGroup()
                .addComponent(jLabel21)
                .addGap(5, 5, 5)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel43)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout menu_3Layout = new javax.swing.GroupLayout(menu_3);
        menu_3.setLayout(menu_3Layout);
        menu_3Layout.setHorizontalGroup(
            menu_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(menu_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menu_items_list, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menu_items, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        menu_3Layout.setVerticalGroup(
            menu_3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menu_3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(menu_items)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menu_items_list, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menu_4.setBackground(new java.awt.Color(32, 74, 86));

        menu_home1.setBackground(new java.awt.Color(32, 74, 86));
        menu_home1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        menu_home1.setForeground(new java.awt.Color(231, 238, 126));
        menu_home1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        menu_home1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo 150px.png"))); // NOI18N
        menu_home1.setToolTipText("");
        menu_home1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_home1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu_home1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout menu_4Layout = new javax.swing.GroupLayout(menu_4);
        menu_4.setLayout(menu_4Layout);
        menu_4Layout.setHorizontalGroup(
            menu_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu_home1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        menu_4Layout.setVerticalGroup(
            menu_4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu_home1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menu_5.setBackground(new java.awt.Color(32, 74, 86));

        menu_input.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        menu_input.setForeground(new java.awt.Color(231, 238, 126));
        menu_input.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        menu_input.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_form_40px.png"))); // NOI18N
        menu_input.setText("   FORM INPUT");
        menu_input.setToolTipText("");
        menu_input.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_input.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu_inputMouseClicked(evt);
            }
        });

        menu_input_list.setBackground(new java.awt.Color(32, 74, 86));

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(231, 238, 126));
        jLabel44.setText("Pengajuan Cuti");
        jLabel44.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel44MouseClicked(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(231, 238, 126));
        jLabel45.setText("Pengajuan Lembur");
        jLabel45.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel45MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel45MouseEntered(evt);
            }
        });

        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(231, 238, 126));
        jLabel46.setText("Pengajuan Reimburse");
        jLabel46.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel46MouseClicked(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(231, 238, 126));
        jLabel47.setText("Pengajuan Dinas Luar");
        jLabel47.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel47MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout menu_input_listLayout = new javax.swing.GroupLayout(menu_input_list);
        menu_input_list.setLayout(menu_input_listLayout);
        menu_input_listLayout.setHorizontalGroup(
            menu_input_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_input_listLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(menu_input_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        menu_input_listLayout.setVerticalGroup(
            menu_input_listLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_input_listLayout.createSequentialGroup()
                .addComponent(jLabel44)
                .addGap(5, 5, 5)
                .addComponent(jLabel45)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel47)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout menu_5Layout = new javax.swing.GroupLayout(menu_5);
        menu_5.setLayout(menu_5Layout);
        menu_5Layout.setHorizontalGroup(
            menu_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_5Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(menu_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menu_input_list, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menu_input, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        menu_5Layout.setVerticalGroup(
            menu_5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menu_5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(menu_input)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menu_input_list, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        menu_6.setBackground(new java.awt.Color(32, 74, 86));

        menu_home2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        menu_home2.setForeground(new java.awt.Color(231, 238, 126));
        menu_home2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_graph_report_script_40px.png"))); // NOI18N
        menu_home2.setText("   LAPORAN");
        menu_home2.setToolTipText("");
        menu_home2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_home2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menu_home2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout menu_6Layout = new javax.swing.GroupLayout(menu_6);
        menu_6.setLayout(menu_6Layout);
        menu_6Layout.setHorizontalGroup(
            menu_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menu_6Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(menu_home2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menu_6Layout.setVerticalGroup(
            menu_6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menu_6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(menu_home2)
                .addContainerGap())
        );

        javax.swing.GroupLayout p_sidebarLayout = new javax.swing.GroupLayout(p_sidebar);
        p_sidebar.setLayout(p_sidebarLayout);
        p_sidebarLayout.setHorizontalGroup(
            p_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_sidebarLayout.createSequentialGroup()
                .addGroup(p_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menu_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menu_3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menu_2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menu_5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(p_sidebarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(menu_4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(menu_6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        p_sidebarLayout.setVerticalGroup(
            p_sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_sidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menu_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(menu_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menu_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menu_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menu_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menu_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(p_sidebar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 740));

        p_utama.setLayout(new java.awt.CardLayout());

        p_home.setBackground(new java.awt.Color(254, 255, 230));

        p_perusahaan_header9.setBackground(new java.awt.Color(5, 32, 56));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(231, 238, 126));
        jLabel19.setText("HOME / HOME");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(231, 238, 126));
        jLabel20.setText("Tampilan awal/ dashboard/ pintasan ke menu yang dituju.");

        javax.swing.GroupLayout p_perusahaan_header9Layout = new javax.swing.GroupLayout(p_perusahaan_header9);
        p_perusahaan_header9.setLayout(p_perusahaan_header9Layout);
        p_perusahaan_header9Layout.setHorizontalGroup(
            p_perusahaan_header9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header9Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(p_perusahaan_header9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_perusahaan_header9Layout.setVerticalGroup(
            p_perusahaan_header9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header9Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        p_perusahaan_main9.setBackground(new java.awt.Color(254, 255, 230));

        jPanel4.setBackground(new java.awt.Color(254, 255, 230));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(32, 74, 86), 2));

        jLabel42.setBackground(new java.awt.Color(204, 204, 204));
        jLabel42.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(9, 10, 54));
        jLabel42.setText("NAMA : ");
        jLabel42.setIconTextGap(8);

        noGaji.setBackground(new java.awt.Color(204, 204, 204));
        noGaji.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        noGaji.setForeground(new java.awt.Color(9, 10, 54));
        noGaji.setText("NO GAJI :");
        noGaji.setIconTextGap(8);

        jLabel62.setBackground(new java.awt.Color(204, 204, 204));
        jLabel62.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(9, 10, 54));
        jLabel62.setText("DEPARTMENT : ");
        jLabel62.setIconTextGap(8);

        jLabel63.setBackground(new java.awt.Color(204, 204, 204));
        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(9, 10, 54));
        jLabel63.setText("JABATAN : ");
        jLabel63.setIconTextGap(8);

        jPanel5.setBackground(new java.awt.Color(32, 74, 86));

        jLabel64.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(231, 238, 126));
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("INFORMASI  KARYAWAN, ID : ");

        txtUser.setBackground(new java.awt.Color(204, 204, 204));
        txtUser.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtUser.setForeground(new java.awt.Color(231, 238, 126));
        txtUser.setText("..");
        txtUser.setIconTextGap(8);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(79, 79, 79))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel65.setBackground(new java.awt.Color(204, 204, 204));
        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(9, 10, 54));
        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_User_Menu_Female_70px.png"))); // NOI18N
        jLabel65.setIconTextGap(8);

        txtNama.setBackground(new java.awt.Color(204, 204, 204));
        txtNama.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtNama.setForeground(new java.awt.Color(9, 10, 54));
        txtNama.setText("..");
        txtNama.setIconTextGap(8);

        txtDept.setBackground(new java.awt.Color(204, 204, 204));
        txtDept.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtDept.setForeground(new java.awt.Color(9, 10, 54));
        txtDept.setText("..");
        txtDept.setIconTextGap(8);

        txtJabatan.setBackground(new java.awt.Color(204, 204, 204));
        txtJabatan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtJabatan.setForeground(new java.awt.Color(9, 10, 54));
        txtJabatan.setText("..");
        txtJabatan.setIconTextGap(8);

        jLabel66.setBackground(new java.awt.Color(204, 204, 204));
        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(9, 10, 54));
        jLabel66.setText("JATAH CUTI  :");
        jLabel66.setIconTextGap(8);

        jLabel67.setBackground(new java.awt.Color(204, 204, 204));
        jLabel67.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(9, 10, 54));
        jLabel67.setText("SISA CUTI  :");
        jLabel67.setIconTextGap(8);

        txtJatah.setBackground(new java.awt.Color(204, 204, 204));
        txtJatah.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtJatah.setForeground(new java.awt.Color(9, 10, 54));
        txtJatah.setText("..");
        txtJatah.setIconTextGap(8);

        txtSisa.setBackground(new java.awt.Color(204, 204, 204));
        txtSisa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSisa.setForeground(new java.awt.Color(9, 10, 54));
        txtSisa.setText("..");
        txtSisa.setIconTextGap(8);

        txtNik.setBackground(new java.awt.Color(204, 204, 204));
        txtNik.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtNik.setForeground(new java.awt.Color(9, 10, 54));
        txtNik.setText("..");
        txtNik.setIconTextGap(8);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtJatah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel62)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDept, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(noGaji, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel67))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(txtJabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtSisa, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txtNik, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(noGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNik, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                            .addComponent(txtJabatan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDept, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtJatah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSisa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(8, 8, 8))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel6.setBackground(new java.awt.Color(5, 32, 56));

        b_password.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        b_password.setForeground(new java.awt.Color(231, 238, 126));
        b_password.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        b_password.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_password_40px.png"))); // NOI18N
        b_password.setText("UBAH KATA SANDI ANDA");
        b_password.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b_password.setIconTextGap(10);
        b_password.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b_passwordMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(b_password, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(b_password)
                .addContainerGap())
        );

        tab_home.setBackground(new java.awt.Color(254, 255, 230));
        tab_home.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tab_home.setForeground(new java.awt.Color(5, 32, 56));
        tab_home.setModel(new javax.swing.table.DefaultTableModel(
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
        tab_home.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tab_home.setOpaque(false);
        tab_home.setRowHeight(25);
        tab_home.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tab_home.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tab_home.setShowHorizontalLines(false);
        tab_home.setShowVerticalLines(false);
        tab_home.getTableHeader().setReorderingAllowed(false);
        tab_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_homeMouseClicked(evt);
            }
        });
        jScrollPane14.setViewportView(tab_home);

        jLabel71.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(32, 74, 86));
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("INFORMASI KARYAWAN CUTI DAN DINAS LUAR KANTOR, PERIODE  :  ");
        jLabel71.setIconTextGap(10);

        jPanel7.setBackground(new java.awt.Color(254, 255, 230));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(32, 74, 86), 2));

        jLabel72.setBackground(new java.awt.Color(204, 204, 204));
        jLabel72.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(9, 10, 54));
        jLabel72.setText("PENGAJUAN CUTI                :");
        jLabel72.setIconTextGap(8);

        jLabel73.setBackground(new java.awt.Color(204, 204, 204));
        jLabel73.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(9, 10, 54));
        jLabel73.setText("PENGAJUAN LEMBUR          :");
        jLabel73.setIconTextGap(8);

        jLabel74.setBackground(new java.awt.Color(204, 204, 204));
        jLabel74.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(9, 10, 54));
        jLabel74.setText("PENGAJUAN REIMBURSE     :");
        jLabel74.setIconTextGap(8);

        jLabel75.setBackground(new java.awt.Color(204, 204, 204));
        jLabel75.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(9, 10, 54));
        jLabel75.setText("PENNGAJUAN DINAS LUAR  :");
        jLabel75.setIconTextGap(8);

        jPanel8.setBackground(new java.awt.Color(32, 74, 86));

        jLabel76.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(231, 238, 126));
        jLabel76.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel76.setText("AKTIVITAS  PENGAJUAN  ANDA TAHUN");

        txt_year.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_year.setForeground(new java.awt.Color(231, 238, 126));
        txt_year.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txt_year.setText("..");
        txt_year.setIconTextGap(10);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel76)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_year, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel76)
                    .addComponent(txt_year))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel77.setBackground(new java.awt.Color(204, 204, 204));
        jLabel77.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(9, 10, 54));
        jLabel77.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_ecg_70px.png"))); // NOI18N
        jLabel77.setIconTextGap(8);

        txtCuti.setBackground(new java.awt.Color(204, 204, 204));
        txtCuti.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtCuti.setForeground(new java.awt.Color(9, 10, 54));
        txtCuti.setText("..");
        txtCuti.setIconTextGap(8);

        txtLembur.setBackground(new java.awt.Color(204, 204, 204));
        txtLembur.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtLembur.setForeground(new java.awt.Color(9, 10, 54));
        txtLembur.setText("..");
        txtLembur.setIconTextGap(8);

        txtDinas.setBackground(new java.awt.Color(204, 204, 204));
        txtDinas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtDinas.setForeground(new java.awt.Color(9, 10, 54));
        txtDinas.setText("..");
        txtDinas.setIconTextGap(8);

        txtReimburse.setBackground(new java.awt.Color(204, 204, 204));
        txtReimburse.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtReimburse.setForeground(new java.awt.Color(9, 10, 54));
        txtReimburse.setText("..");
        txtReimburse.setIconTextGap(8);

        jPanel9.setBackground(new java.awt.Color(5, 32, 56));

        bCuti.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        bCuti.setForeground(new java.awt.Color(231, 238, 126));
        bCuti.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bCuti.setText("Tambah/Check");
        bCuti.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCuti.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bCutiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bCuti, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bCuti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel46.setBackground(new java.awt.Color(5, 32, 56));

        bLembur.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        bLembur.setForeground(new java.awt.Color(231, 238, 126));
        bLembur.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bLembur.setText("Tambah/Check");
        bLembur.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bLembur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bLemburMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel46Layout = new javax.swing.GroupLayout(jPanel46);
        jPanel46.setLayout(jPanel46Layout);
        jPanel46Layout.setHorizontalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bLembur, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jPanel46Layout.setVerticalGroup(
            jPanel46Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bLembur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel54.setBackground(new java.awt.Color(5, 32, 56));

        bReimburse.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        bReimburse.setForeground(new java.awt.Color(231, 238, 126));
        bReimburse.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bReimburse.setText("Tambah/Check");
        bReimburse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bReimburse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bReimburseMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel54Layout = new javax.swing.GroupLayout(jPanel54);
        jPanel54.setLayout(jPanel54Layout);
        jPanel54Layout.setHorizontalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bReimburse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jPanel54Layout.setVerticalGroup(
            jPanel54Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bReimburse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel55.setBackground(new java.awt.Color(5, 32, 56));

        bDinas.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        bDinas.setForeground(new java.awt.Color(231, 238, 126));
        bDinas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bDinas.setText("Tambah/Check");
        bDinas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bDinas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bDinasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel55Layout = new javax.swing.GroupLayout(jPanel55);
        jPanel55.setLayout(jPanel55Layout);
        jPanel55Layout.setHorizontalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bDinas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        jPanel55Layout.setVerticalGroup(
            jPanel55Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bDinas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel77, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel73, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(txtLembur, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCuti, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtReimburse, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDinas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(28, 28, 28)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                .addComponent(txtCuti, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel46, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel73, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtLembur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtReimburse, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel75, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDinas, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(24, 24, 24))))
        );

        tab_home2.setBackground(new java.awt.Color(254, 255, 230));
        tab_home2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tab_home2.setForeground(new java.awt.Color(5, 32, 56));
        tab_home2.setModel(new javax.swing.table.DefaultTableModel(
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
        tab_home2.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tab_home2.setOpaque(false);
        tab_home2.setRowHeight(25);
        tab_home2.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tab_home2.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tab_home2.setShowHorizontalLines(false);
        tab_home2.setShowVerticalLines(false);
        tab_home2.getTableHeader().setReorderingAllowed(false);
        tab_home2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_home2MouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(tab_home2);

        txt_date.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_date.setForeground(new java.awt.Color(32, 74, 86));
        txt_date.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txt_date.setText("..");
        txt_date.setIconTextGap(10);

        jPanel50.setBackground(new java.awt.Color(5, 32, 56));

        b_password1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        b_password1.setForeground(new java.awt.Color(231, 238, 126));
        b_password1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        b_password1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Money_Transfer_40px.png"))); // NOI18N
        b_password1.setText("CETAK SLIP GAJI");
        b_password1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        b_password1.setIconTextGap(10);
        b_password1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                b_password1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel50Layout = new javax.swing.GroupLayout(jPanel50);
        jPanel50.setLayout(jPanel50Layout);
        jPanel50Layout.setHorizontalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel50Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(b_password1, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel50Layout.setVerticalGroup(
            jPanel50Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel50Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(b_password1)
                .addContainerGap())
        );

        javax.swing.GroupLayout p_perusahaan_main9Layout = new javax.swing.GroupLayout(p_perusahaan_main9);
        p_perusahaan_main9.setLayout(p_perusahaan_main9Layout);
        p_perusahaan_main9Layout.setHorizontalGroup(
            p_perusahaan_main9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main9Layout.createSequentialGroup()
                .addGroup(p_perusahaan_main9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_perusahaan_main9Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(p_perusahaan_main9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, p_perusahaan_main9Layout.createSequentialGroup()
                                .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, 535, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(p_perusahaan_main9Layout.createSequentialGroup()
                                .addGroup(p_perusahaan_main9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(p_perusahaan_main9Layout.createSequentialGroup()
                                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(p_perusahaan_main9Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(jLabel71)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        p_perusahaan_main9Layout.setVerticalGroup(
            p_perusahaan_main9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p_perusahaan_main9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p_perusahaan_main9Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(p_perusahaan_main9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(p_perusahaan_main9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel71)
                    .addComponent(txt_date))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(p_perusahaan_main9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(139, 139, 139))
        );

        javax.swing.GroupLayout p_homeLayout = new javax.swing.GroupLayout(p_home);
        p_home.setLayout(p_homeLayout);
        p_homeLayout.setHorizontalGroup(
            p_homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_perusahaan_header9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p_perusahaan_main9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p_homeLayout.setVerticalGroup(
            p_homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_homeLayout.createSequentialGroup()
                .addComponent(p_perusahaan_header9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(p_perusahaan_main9, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(275, 275, 275))
        );

        p_utama.add(p_home, "p_home");

        p_reports.setBackground(new java.awt.Color(254, 255, 230));

        p_perusahaan_header8.setBackground(new java.awt.Color(5, 32, 56));

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(231, 238, 126));
        jLabel32.setText("R E P O R T S");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(231, 238, 126));
        jLabel33.setText("Mencetak data - data yang sudah terekam masuk kedalam database ___________");

        javax.swing.GroupLayout p_perusahaan_header8Layout = new javax.swing.GroupLayout(p_perusahaan_header8);
        p_perusahaan_header8.setLayout(p_perusahaan_header8Layout);
        p_perusahaan_header8Layout.setHorizontalGroup(
            p_perusahaan_header8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header8Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(p_perusahaan_header8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(jLabel32))
                .addContainerGap(548, Short.MAX_VALUE))
        );
        p_perusahaan_header8Layout.setVerticalGroup(
            p_perusahaan_header8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header8Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        p_perusahaan_main8.setBackground(new java.awt.Color(254, 255, 230));

        jPanel2.setBackground(new java.awt.Color(254, 255, 230));

        jPanel3.setBackground(new java.awt.Color(254, 255, 230));

        jLabel35.setBackground(new java.awt.Color(32, 74, 86));
        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(32, 74, 86));
        jLabel35.setText("CETAK SEMUA DATA KARYAWAN");

        jPanel47.setBackground(new java.awt.Color(5, 32, 56));

        repKaryawan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repKaryawan.setForeground(new java.awt.Color(231, 238, 126));
        repKaryawan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_doc_24px_1.png"))); // NOI18N
        repKaryawan.setText("C E T A K");
        repKaryawan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repKaryawan.setIconTextGap(8);
        repKaryawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repKaryawanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel47Layout = new javax.swing.GroupLayout(jPanel47);
        jPanel47.setLayout(jPanel47Layout);
        jPanel47Layout.setHorizontalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel47Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(repKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel47Layout.setVerticalGroup(
            jPanel47Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(repKaryawan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel36.setBackground(new java.awt.Color(32, 74, 86));
        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(32, 74, 86));
        jLabel36.setText("CETAK REKAP DATA GAJI KARYAWAN PER-DEPARTMENT");

        jLabel38.setBackground(new java.awt.Color(32, 74, 86));
        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(32, 74, 86));
        jLabel38.setText("CETAK LAPORAN DATA TRANSAKSI CUTI KARYAWAN :");

        cb_cuti.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cb_cuti.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel37.setBackground(new java.awt.Color(32, 74, 86));
        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(32, 74, 86));
        jLabel37.setText("PILIH DEPARTMENT :");

        cb_department1.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cb_department1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Pilih Department -" }));
        cb_department1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cb_department1MouseClicked(evt);
            }
        });
        cb_department1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_department1ActionPerformed(evt);
            }
        });

        jLabel68.setBackground(new java.awt.Color(32, 74, 86));
        jLabel68.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(32, 74, 86));
        jLabel68.setText("PILIH PERIODE :");

        jPanel56.setBackground(new java.awt.Color(5, 32, 56));

        repRekap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repRekap.setForeground(new java.awt.Color(231, 238, 126));
        repRekap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_doc_24px_1.png"))); // NOI18N
        repRekap.setText("C E T A K");
        repRekap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repRekap.setIconTextGap(8);
        repRekap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repRekapMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel56Layout = new javax.swing.GroupLayout(jPanel56);
        jPanel56.setLayout(jPanel56Layout);
        jPanel56Layout.setHorizontalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel56Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(repRekap, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel56Layout.setVerticalGroup(
            jPanel56Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(repRekap, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        jLabel69.setBackground(new java.awt.Color(32, 74, 86));
        jLabel69.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(32, 74, 86));
        jLabel69.setText("CETAK SLIP GAJI KARYAWAN");

        jLabel70.setBackground(new java.awt.Color(32, 74, 86));
        jLabel70.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(32, 74, 86));
        jLabel70.setText("PILIH KARYAWAN :");

        cb_slipKaryawan.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cb_slipKaryawan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "- Pilih Department -" }));
        cb_slipKaryawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cb_slipKaryawanMouseClicked(evt);
            }
        });
        cb_slipKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cb_slipKaryawanActionPerformed(evt);
            }
        });

        jLabel78.setBackground(new java.awt.Color(32, 74, 86));
        jLabel78.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(32, 74, 86));
        jLabel78.setText("PILIH PERIODE :");

        jPanel57.setBackground(new java.awt.Color(5, 32, 56));

        repSlip.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repSlip.setForeground(new java.awt.Color(231, 238, 126));
        repSlip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_doc_24px_1.png"))); // NOI18N
        repSlip.setText("C E T A K");
        repSlip.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repSlip.setIconTextGap(8);
        repSlip.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repSlipMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel57Layout = new javax.swing.GroupLayout(jPanel57);
        jPanel57.setLayout(jPanel57Layout);
        jPanel57Layout.setHorizontalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel57Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(repSlip, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel57Layout.setVerticalGroup(
            jPanel57Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(repSlip, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        jLabel80.setBackground(new java.awt.Color(32, 74, 86));
        jLabel80.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(32, 74, 86));
        jLabel80.setText("CETAK SEMUA DATA DEPARTMENT");

        jPanel48.setBackground(new java.awt.Color(5, 32, 56));

        repDepartment.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repDepartment.setForeground(new java.awt.Color(231, 238, 126));
        repDepartment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_doc_24px_1.png"))); // NOI18N
        repDepartment.setText("C E T A K");
        repDepartment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repDepartment.setIconTextGap(8);
        repDepartment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repDepartmentMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel48Layout = new javax.swing.GroupLayout(jPanel48);
        jPanel48.setLayout(jPanel48Layout);
        jPanel48Layout.setHorizontalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel48Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(repDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel48Layout.setVerticalGroup(
            jPanel48Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(repDepartment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel81.setBackground(new java.awt.Color(32, 74, 86));
        jLabel81.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(32, 74, 86));
        jLabel81.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel81.setText("|");

        jLabel82.setBackground(new java.awt.Color(32, 74, 86));
        jLabel82.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(32, 74, 86));
        jLabel82.setText("CETAK SEMUA DATA JABATAN");

        jPanel49.setBackground(new java.awt.Color(5, 32, 56));

        repJabatan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repJabatan.setForeground(new java.awt.Color(231, 238, 126));
        repJabatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_doc_24px_1.png"))); // NOI18N
        repJabatan.setText("C E T A K");
        repJabatan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repJabatan.setIconTextGap(8);
        repJabatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repJabatanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel49Layout = new javax.swing.GroupLayout(jPanel49);
        jPanel49.setLayout(jPanel49Layout);
        jPanel49Layout.setHorizontalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel49Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(repJabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel49Layout.setVerticalGroup(
            jPanel49Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(repJabatan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel58.setBackground(new java.awt.Color(5, 32, 56));

        repCuti.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repCuti.setForeground(new java.awt.Color(231, 238, 126));
        repCuti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_doc_24px_1.png"))); // NOI18N
        repCuti.setText("C E T A K");
        repCuti.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repCuti.setIconTextGap(8);
        repCuti.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repCutiMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel58Layout = new javax.swing.GroupLayout(jPanel58);
        jPanel58.setLayout(jPanel58Layout);
        jPanel58Layout.setHorizontalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel58Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(repCuti, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel58Layout.setVerticalGroup(
            jPanel58Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(repCuti, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        jLabel83.setBackground(new java.awt.Color(32, 74, 86));
        jLabel83.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(32, 74, 86));
        jLabel83.setText("CETAK LAPORAN DATA TRANSAKSI LEMBUR KARYAWAN :");

        cb_lembur.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cb_lembur.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel59.setBackground(new java.awt.Color(5, 32, 56));

        repLembur.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repLembur.setForeground(new java.awt.Color(231, 238, 126));
        repLembur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_doc_24px_1.png"))); // NOI18N
        repLembur.setText("C E T A K");
        repLembur.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repLembur.setIconTextGap(8);
        repLembur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repLemburMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel59Layout = new javax.swing.GroupLayout(jPanel59);
        jPanel59.setLayout(jPanel59Layout);
        jPanel59Layout.setHorizontalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel59Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(repLembur, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel59Layout.setVerticalGroup(
            jPanel59Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(repLembur, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        cb_reimburse.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cb_reimburse.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel60.setBackground(new java.awt.Color(5, 32, 56));

        repReimburse.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repReimburse.setForeground(new java.awt.Color(231, 238, 126));
        repReimburse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_doc_24px_1.png"))); // NOI18N
        repReimburse.setText("C E T A K");
        repReimburse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repReimburse.setIconTextGap(8);
        repReimburse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repReimburseMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel60Layout = new javax.swing.GroupLayout(jPanel60);
        jPanel60.setLayout(jPanel60Layout);
        jPanel60Layout.setHorizontalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel60Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(repReimburse, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel60Layout.setVerticalGroup(
            jPanel60Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(repReimburse, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        jLabel84.setBackground(new java.awt.Color(32, 74, 86));
        jLabel84.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(32, 74, 86));
        jLabel84.setText("CETAK LAPORAN DATA TRANSAKSI REIMBURSEMENT KARYAWAN :");

        jLabel85.setBackground(new java.awt.Color(32, 74, 86));
        jLabel85.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(32, 74, 86));
        jLabel85.setText("CETAK LAPORAN DATA TRANSAKSI DINAS LUAR KARYAWAN :");

        cb_dinas.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cb_dinas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPanel61.setBackground(new java.awt.Color(5, 32, 56));

        repDinas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        repDinas.setForeground(new java.awt.Color(231, 238, 126));
        repDinas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_doc_24px_1.png"))); // NOI18N
        repDinas.setText("C E T A K");
        repDinas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        repDinas.setIconTextGap(8);
        repDinas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                repDinasMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel61Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(repDinas, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(repDinas, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
        );

        jLabel79.setBackground(new java.awt.Color(32, 74, 86));
        jLabel79.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(32, 74, 86));
        jLabel79.setText("PILIH PERIODE :");

        jLabel86.setBackground(new java.awt.Color(32, 74, 86));
        jLabel86.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel86.setForeground(new java.awt.Color(32, 74, 86));
        jLabel86.setText("PILIH PERIODE :");

        jLabel87.setBackground(new java.awt.Color(32, 74, 86));
        jLabel87.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(32, 74, 86));
        jLabel87.setText("PILIH PERIODE :");

        jLabel88.setBackground(new java.awt.Color(32, 74, 86));
        jLabel88.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(32, 74, 86));
        jLabel88.setText("PILIH PERIODE :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cb_department1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel68)
                                .addGap(18, 18, 18)
                                .addComponent(rekapMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rekapYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel69)
                            .addComponent(jLabel36)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(jLabel80)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel82)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel81))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel38)
                                    .addGap(18, 18, 18)
                                    .addComponent(cb_cuti, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel79)
                                    .addGap(18, 18, 18)
                                    .addComponent(cutiMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cutiYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jPanel58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel83)
                                    .addGap(18, 18, 18)
                                    .addComponent(cb_lembur, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel86)
                                    .addGap(18, 18, 18)
                                    .addComponent(lemburMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(lemburYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jPanel59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel84)
                                    .addGap(18, 18, 18)
                                    .addComponent(cb_reimburse, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel87)
                                    .addGap(18, 18, 18)
                                    .addComponent(reimburseMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(reimburseYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jPanel60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel85)
                                    .addGap(18, 18, 18)
                                    .addComponent(cb_dinas, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel88)
                                    .addGap(18, 18, 18)
                                    .addComponent(dinasMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(dinasYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jPanel61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel70)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cb_slipKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel78)
                                .addGap(18, 18, 18)
                                .addComponent(slipMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(slipYear, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 1027, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel48, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel81, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel49, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel69, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(slipYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(slipMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel70, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cb_slipKaryawan))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rekapYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rekapMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cb_department1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cutiYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cutiMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cb_cuti, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel83, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cb_lembur, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel59, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lemburYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lemburMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel86, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel84, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cb_reimburse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel60, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(reimburseYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(reimburseMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel87, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel85, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel61, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dinasYear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dinasMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel88, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cb_dinas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(315, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout p_perusahaan_main8Layout = new javax.swing.GroupLayout(p_perusahaan_main8);
        p_perusahaan_main8.setLayout(p_perusahaan_main8Layout);
        p_perusahaan_main8Layout.setHorizontalGroup(
            p_perusahaan_main8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p_perusahaan_main8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        p_perusahaan_main8Layout.setVerticalGroup(
            p_perusahaan_main8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main8Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p_reportsLayout = new javax.swing.GroupLayout(p_reports);
        p_reports.setLayout(p_reportsLayout);
        p_reportsLayout.setHorizontalGroup(
            p_reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_perusahaan_header8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p_perusahaan_main8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p_reportsLayout.setVerticalGroup(
            p_reportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_reportsLayout.createSequentialGroup()
                .addComponent(p_perusahaan_header8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(p_perusahaan_main8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        p_utama.add(p_reports, "p_report");

        p_department.setBackground(new java.awt.Color(254, 255, 230));

        p_perusahaan_header1.setBackground(new java.awt.Color(5, 32, 56));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(231, 238, 126));
        jLabel3.setText("MANAGEMENT  /  Department");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(231, 238, 126));
        jLabel4.setText("Tambah, Ubah Dan Hapus Data Master Department Didalam Database Aplikasi ___________");

        javax.swing.GroupLayout p_perusahaan_header1Layout = new javax.swing.GroupLayout(p_perusahaan_header1);
        p_perusahaan_header1.setLayout(p_perusahaan_header1Layout);
        p_perusahaan_header1Layout.setHorizontalGroup(
            p_perusahaan_header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(p_perusahaan_header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_perusahaan_header1Layout.setVerticalGroup(
            p_perusahaan_header1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header1Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        p_perusahaan_main1.setBackground(new java.awt.Color(254, 255, 230));

        tab_department.setBackground(new java.awt.Color(254, 255, 230));
        tab_department.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tab_department.setForeground(new java.awt.Color(5, 32, 56));
        tab_department.setModel(new javax.swing.table.DefaultTableModel(
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
        tab_department.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tab_department.setOpaque(false);
        tab_department.setRowHeight(25);
        tab_department.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tab_department.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tab_department.setShowHorizontalLines(false);
        tab_department.setShowVerticalLines(false);
        tab_department.getTableHeader().setReorderingAllowed(false);
        tab_department.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_departmentMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tab_department);

        txt_department.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_department.setForeground(new java.awt.Color(5, 32, 56));
        txt_department.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_departmentActionPerformed(evt);
            }
        });
        txt_department.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_departmentKeyPressed(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(67, 131, 113));

        department_b_tambah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        department_b_tambah.setForeground(new java.awt.Color(231, 238, 126));
        department_b_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        department_b_tambah.setText("TAMBAH");
        department_b_tambah.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        department_b_tambah.setIconTextGap(8);
        department_b_tambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                department_b_tambahMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(department_b_tambah, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(department_b_tambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel11.setBackground(new java.awt.Color(162, 34, 39));

        department_b_hapus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        department_b_hapus.setForeground(new java.awt.Color(231, 238, 126));
        department_b_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        department_b_hapus.setText("   HAPUS");
        department_b_hapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        department_b_hapus.setIconTextGap(8);
        department_b_hapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                department_b_hapusMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(department_b_hapus, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(department_b_hapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel12.setBackground(new java.awt.Color(5, 32, 56));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(231, 238, 126));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        jLabel12.setText("CARI/REFRESH");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.setIconTextGap(8);
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel13.setBackground(new java.awt.Color(5, 32, 56));

        Department_b_edit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Department_b_edit.setForeground(new java.awt.Color(231, 238, 126));
        Department_b_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        Department_b_edit.setText("     EDIT");
        Department_b_edit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Department_b_edit.setIconTextGap(8);
        Department_b_edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Department_b_editMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Department_b_edit, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Department_b_edit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p_perusahaan_main1Layout = new javax.swing.GroupLayout(p_perusahaan_main1);
        p_perusahaan_main1.setLayout(p_perusahaan_main1Layout);
        p_perusahaan_main1Layout.setHorizontalGroup(
            p_perusahaan_main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(p_perusahaan_main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(p_perusahaan_main1Layout.createSequentialGroup()
                        .addComponent(txt_department, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        p_perusahaan_main1Layout.setVerticalGroup(
            p_perusahaan_main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main1Layout.createSequentialGroup()
                .addGroup(p_perusahaan_main1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_department, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p_departmentLayout = new javax.swing.GroupLayout(p_department);
        p_department.setLayout(p_departmentLayout);
        p_departmentLayout.setHorizontalGroup(
            p_departmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_perusahaan_header1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p_perusahaan_main1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p_departmentLayout.setVerticalGroup(
            p_departmentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_departmentLayout.createSequentialGroup()
                .addComponent(p_perusahaan_header1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(p_perusahaan_main1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        p_utama.add(p_department, "p_department");

        p_user.setBackground(new java.awt.Color(254, 255, 230));

        p_perusahaan_header3.setBackground(new java.awt.Color(5, 32, 56));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(231, 238, 126));
        jLabel7.setText("MANAGEMENT  /  Karyawan");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(231, 238, 126));
        jLabel9.setText("Tambah, Ubah Dan Hapus Data Master Karyawan Di Dalam Database Aplikasi ___________");

        javax.swing.GroupLayout p_perusahaan_header3Layout = new javax.swing.GroupLayout(p_perusahaan_header3);
        p_perusahaan_header3.setLayout(p_perusahaan_header3Layout);
        p_perusahaan_header3Layout.setHorizontalGroup(
            p_perusahaan_header3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header3Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(p_perusahaan_header3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_perusahaan_header3Layout.setVerticalGroup(
            p_perusahaan_header3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header3Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        p_perusahaan_main3.setBackground(new java.awt.Color(254, 255, 230));

        tab_karyawan.setBackground(new java.awt.Color(254, 255, 230));
        tab_karyawan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tab_karyawan.setForeground(new java.awt.Color(5, 32, 56));
        tab_karyawan.setModel(new javax.swing.table.DefaultTableModel(
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
        tab_karyawan.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tab_karyawan.setOpaque(false);
        tab_karyawan.setRowHeight(25);
        tab_karyawan.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tab_karyawan.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tab_karyawan.setShowHorizontalLines(false);
        tab_karyawan.setShowVerticalLines(false);
        tab_karyawan.getTableHeader().setReorderingAllowed(false);
        tab_karyawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_karyawanMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tab_karyawan);

        txt_karyawan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_karyawan.setForeground(new java.awt.Color(5, 32, 56));
        txt_karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_karyawanActionPerformed(evt);
            }
        });
        txt_karyawan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_karyawanKeyPressed(evt);
            }
        });

        jPanel18.setBackground(new java.awt.Color(67, 131, 113));

        department_b_tambah1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        department_b_tambah1.setForeground(new java.awt.Color(231, 238, 126));
        department_b_tambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        department_b_tambah1.setText("TAMBAH");
        department_b_tambah1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        department_b_tambah1.setIconTextGap(8);
        department_b_tambah1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                department_b_tambah1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(department_b_tambah1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(department_b_tambah1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel19.setBackground(new java.awt.Color(162, 34, 39));

        karyawan_b_hapus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        karyawan_b_hapus.setForeground(new java.awt.Color(231, 238, 126));
        karyawan_b_hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        karyawan_b_hapus.setText("   HAPUS");
        karyawan_b_hapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        karyawan_b_hapus.setIconTextGap(8);
        karyawan_b_hapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                karyawan_b_hapusMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(karyawan_b_hapus, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(karyawan_b_hapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel20.setBackground(new java.awt.Color(5, 32, 56));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(231, 238, 126));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        jLabel15.setText("CARI/REFRESH");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel15.setIconTextGap(8);
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel21.setBackground(new java.awt.Color(5, 32, 56));

        karyawan_b_edit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        karyawan_b_edit.setForeground(new java.awt.Color(231, 238, 126));
        karyawan_b_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        karyawan_b_edit.setText("     EDIT");
        karyawan_b_edit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        karyawan_b_edit.setIconTextGap(8);
        karyawan_b_edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                karyawan_b_editMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(karyawan_b_edit, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(karyawan_b_edit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p_perusahaan_main3Layout = new javax.swing.GroupLayout(p_perusahaan_main3);
        p_perusahaan_main3.setLayout(p_perusahaan_main3Layout);
        p_perusahaan_main3Layout.setHorizontalGroup(
            p_perusahaan_main3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main3Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(p_perusahaan_main3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(p_perusahaan_main3Layout.createSequentialGroup()
                        .addComponent(txt_karyawan, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        p_perusahaan_main3Layout.setVerticalGroup(
            p_perusahaan_main3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main3Layout.createSequentialGroup()
                .addGroup(p_perusahaan_main3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_karyawan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p_userLayout = new javax.swing.GroupLayout(p_user);
        p_user.setLayout(p_userLayout);
        p_userLayout.setHorizontalGroup(
            p_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_perusahaan_header3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p_perusahaan_main3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p_userLayout.setVerticalGroup(
            p_userLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_userLayout.createSequentialGroup()
                .addComponent(p_perusahaan_header3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(p_perusahaan_main3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        p_utama.add(p_user, "p_user");

        p_jabatan.setBackground(new java.awt.Color(254, 255, 230));

        p_perusahaan_header2.setBackground(new java.awt.Color(5, 32, 56));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(231, 238, 126));
        jLabel10.setText("MANAGEMENT  /  Jabatan Karyawan");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(231, 238, 126));
        jLabel13.setText("Tambah, Ubah Dan Hapus Data Master Department Didalam Database Aplikasi ___________");

        javax.swing.GroupLayout p_perusahaan_header2Layout = new javax.swing.GroupLayout(p_perusahaan_header2);
        p_perusahaan_header2.setLayout(p_perusahaan_header2Layout);
        p_perusahaan_header2Layout.setHorizontalGroup(
            p_perusahaan_header2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(p_perusahaan_header2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_perusahaan_header2Layout.setVerticalGroup(
            p_perusahaan_header2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header2Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        p_perusahaan_main2.setBackground(new java.awt.Color(254, 255, 230));

        tab_jabatan.setBackground(new java.awt.Color(254, 255, 230));
        tab_jabatan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tab_jabatan.setForeground(new java.awt.Color(5, 32, 56));
        tab_jabatan.setModel(new javax.swing.table.DefaultTableModel(
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
        tab_jabatan.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tab_jabatan.setOpaque(false);
        tab_jabatan.setRowHeight(25);
        tab_jabatan.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tab_jabatan.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tab_jabatan.setShowHorizontalLines(false);
        tab_jabatan.setShowVerticalLines(false);
        tab_jabatan.getTableHeader().setReorderingAllowed(false);
        tab_jabatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_jabatanMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tab_jabatan);

        txt_transCuti.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_transCuti.setForeground(new java.awt.Color(5, 32, 56));
        txt_transCuti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_transCutiActionPerformed(evt);
            }
        });
        txt_transCuti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_transCutiKeyPressed(evt);
            }
        });

        jPanel14.setBackground(new java.awt.Color(67, 131, 113));

        department_b_tambah2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        department_b_tambah2.setForeground(new java.awt.Color(231, 238, 126));
        department_b_tambah2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        department_b_tambah2.setText("TAMBAH");
        department_b_tambah2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        department_b_tambah2.setIconTextGap(8);
        department_b_tambah2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                department_b_tambah2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(department_b_tambah2, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(department_b_tambah2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel15.setBackground(new java.awt.Color(162, 34, 39));

        department_b_hapus1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        department_b_hapus1.setForeground(new java.awt.Color(231, 238, 126));
        department_b_hapus1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        department_b_hapus1.setText("   HAPUS");
        department_b_hapus1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        department_b_hapus1.setIconTextGap(8);
        department_b_hapus1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                department_b_hapus1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(department_b_hapus1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(department_b_hapus1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel16.setBackground(new java.awt.Color(5, 32, 56));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(231, 238, 126));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        jLabel14.setText("CARI/REFRESH");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel14.setIconTextGap(8);
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel17.setBackground(new java.awt.Color(5, 32, 56));

        Department_b_edit1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Department_b_edit1.setForeground(new java.awt.Color(231, 238, 126));
        Department_b_edit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        Department_b_edit1.setText("     EDIT");
        Department_b_edit1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Department_b_edit1.setIconTextGap(8);
        Department_b_edit1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Department_b_edit1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Department_b_edit1, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Department_b_edit1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p_perusahaan_main2Layout = new javax.swing.GroupLayout(p_perusahaan_main2);
        p_perusahaan_main2.setLayout(p_perusahaan_main2Layout);
        p_perusahaan_main2Layout.setHorizontalGroup(
            p_perusahaan_main2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main2Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(p_perusahaan_main2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(p_perusahaan_main2Layout.createSequentialGroup()
                        .addComponent(txt_transCuti, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        p_perusahaan_main2Layout.setVerticalGroup(
            p_perusahaan_main2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main2Layout.createSequentialGroup()
                .addGroup(p_perusahaan_main2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_transCuti, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p_jabatanLayout = new javax.swing.GroupLayout(p_jabatan);
        p_jabatan.setLayout(p_jabatanLayout);
        p_jabatanLayout.setHorizontalGroup(
            p_jabatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_perusahaan_header2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p_perusahaan_main2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p_jabatanLayout.setVerticalGroup(
            p_jabatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_jabatanLayout.createSequentialGroup()
                .addComponent(p_perusahaan_header2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(p_perusahaan_main2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        p_utama.add(p_jabatan, "p_jabatan");

        p_transCuti.setBackground(new java.awt.Color(254, 255, 230));

        p_perusahaan_header4.setBackground(new java.awt.Color(5, 32, 56));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(231, 238, 126));
        jLabel16.setText("Form Input  /  Pengajuan & Status Pengajuan Cuti");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(231, 238, 126));
        jLabel17.setText("Tambah Dan Monitoring Status Pengajuan Cuti Didalam Database Aplikasi ___________");

        javax.swing.GroupLayout p_perusahaan_header4Layout = new javax.swing.GroupLayout(p_perusahaan_header4);
        p_perusahaan_header4.setLayout(p_perusahaan_header4Layout);
        p_perusahaan_header4Layout.setHorizontalGroup(
            p_perusahaan_header4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header4Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(p_perusahaan_header4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_perusahaan_header4Layout.setVerticalGroup(
            p_perusahaan_header4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header4Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        p_perusahaan_main4.setBackground(new java.awt.Color(254, 255, 230));

        tab_transCuti.setBackground(new java.awt.Color(254, 255, 230));
        tab_transCuti.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tab_transCuti.setForeground(new java.awt.Color(5, 32, 56));
        tab_transCuti.setModel(new javax.swing.table.DefaultTableModel(
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
        tab_transCuti.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tab_transCuti.setOpaque(false);
        tab_transCuti.setRowHeight(25);
        tab_transCuti.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tab_transCuti.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tab_transCuti.setShowHorizontalLines(false);
        tab_transCuti.setShowVerticalLines(false);
        tab_transCuti.getTableHeader().setReorderingAllowed(false);
        tab_transCuti.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_transCutiMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tab_transCuti);

        txt_department2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_department2.setForeground(new java.awt.Color(5, 32, 56));
        txt_department2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_department2ActionPerformed(evt);
            }
        });
        txt_department2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_department2KeyPressed(evt);
            }
        });

        jPanel22.setBackground(new java.awt.Color(67, 131, 113));

        department_b_tambah3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        department_b_tambah3.setForeground(new java.awt.Color(231, 238, 126));
        department_b_tambah3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        department_b_tambah3.setText("TAMBAH");
        department_b_tambah3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        department_b_tambah3.setIconTextGap(8);
        department_b_tambah3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                department_b_tambah3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(department_b_tambah3, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(department_b_tambah3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel23.setBackground(new java.awt.Color(162, 34, 39));

        department_b_hapus2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        department_b_hapus2.setForeground(new java.awt.Color(231, 238, 126));
        department_b_hapus2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        department_b_hapus2.setText("   HAPUS");
        department_b_hapus2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        department_b_hapus2.setIconTextGap(8);
        department_b_hapus2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                department_b_hapus2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(department_b_hapus2, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(department_b_hapus2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel24.setBackground(new java.awt.Color(5, 32, 56));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(231, 238, 126));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        jLabel18.setText("CARI/REFRESH");
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel18.setIconTextGap(8);
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel25.setBackground(new java.awt.Color(5, 32, 56));

        Department_b_edit2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Department_b_edit2.setForeground(new java.awt.Color(231, 238, 126));
        Department_b_edit2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        Department_b_edit2.setText("     EDIT");
        Department_b_edit2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Department_b_edit2.setIconTextGap(8);
        Department_b_edit2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Department_b_edit2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Department_b_edit2, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Department_b_edit2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p_perusahaan_main4Layout = new javax.swing.GroupLayout(p_perusahaan_main4);
        p_perusahaan_main4.setLayout(p_perusahaan_main4Layout);
        p_perusahaan_main4Layout.setHorizontalGroup(
            p_perusahaan_main4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main4Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(p_perusahaan_main4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(p_perusahaan_main4Layout.createSequentialGroup()
                        .addComponent(txt_department2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        p_perusahaan_main4Layout.setVerticalGroup(
            p_perusahaan_main4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main4Layout.createSequentialGroup()
                .addGroup(p_perusahaan_main4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_department2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p_transCutiLayout = new javax.swing.GroupLayout(p_transCuti);
        p_transCuti.setLayout(p_transCutiLayout);
        p_transCutiLayout.setHorizontalGroup(
            p_transCutiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_perusahaan_header4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p_perusahaan_main4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p_transCutiLayout.setVerticalGroup(
            p_transCutiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_transCutiLayout.createSequentialGroup()
                .addComponent(p_perusahaan_header4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(p_perusahaan_main4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        p_utama.add(p_transCuti, "p_transCuti");

        p_transAppCuti.setBackground(new java.awt.Color(254, 255, 230));

        p_perusahaan_header5.setBackground(new java.awt.Color(5, 32, 56));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(231, 238, 126));
        jLabel24.setText("Form Approval  /  Persetujuan & Monitoring Status Cuti");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(231, 238, 126));
        jLabel25.setText("Tambah Dan Monitoring Status Pengajuan Cuti Didalam Database Aplikasi ___________");

        javax.swing.GroupLayout p_perusahaan_header5Layout = new javax.swing.GroupLayout(p_perusahaan_header5);
        p_perusahaan_header5.setLayout(p_perusahaan_header5Layout);
        p_perusahaan_header5Layout.setHorizontalGroup(
            p_perusahaan_header5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header5Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(p_perusahaan_header5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(jLabel24))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_perusahaan_header5Layout.setVerticalGroup(
            p_perusahaan_header5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header5Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        p_perusahaan_main5.setBackground(new java.awt.Color(254, 255, 230));

        tab_appCuti.setBackground(new java.awt.Color(254, 255, 230));
        tab_appCuti.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tab_appCuti.setForeground(new java.awt.Color(5, 32, 56));
        tab_appCuti.setModel(new javax.swing.table.DefaultTableModel(
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
        tab_appCuti.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tab_appCuti.setOpaque(false);
        tab_appCuti.setRowHeight(25);
        tab_appCuti.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tab_appCuti.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tab_appCuti.setShowHorizontalLines(false);
        tab_appCuti.setShowVerticalLines(false);
        tab_appCuti.getTableHeader().setReorderingAllowed(false);
        tab_appCuti.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_appCutiMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tab_appCuti);

        txt_AppCuti.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_AppCuti.setForeground(new java.awt.Color(5, 32, 56));
        txt_AppCuti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AppCutiActionPerformed(evt);
            }
        });
        txt_AppCuti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_AppCutiKeyPressed(evt);
            }
        });

        jPanel28.setBackground(new java.awt.Color(5, 32, 56));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(231, 238, 126));
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        jLabel26.setText("CARI/REFRESH");
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel26.setIconTextGap(8);
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel26MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel29.setBackground(new java.awt.Color(5, 32, 56));

        Department_b_edit3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Department_b_edit3.setForeground(new java.awt.Color(231, 238, 126));
        Department_b_edit3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        Department_b_edit3.setText("     PROSES APPROVAL");
        Department_b_edit3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Department_b_edit3.setIconTextGap(8);
        Department_b_edit3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Department_b_edit3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Department_b_edit3, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Department_b_edit3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p_perusahaan_main5Layout = new javax.swing.GroupLayout(p_perusahaan_main5);
        p_perusahaan_main5.setLayout(p_perusahaan_main5Layout);
        p_perusahaan_main5Layout.setHorizontalGroup(
            p_perusahaan_main5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main5Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(p_perusahaan_main5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(p_perusahaan_main5Layout.createSequentialGroup()
                        .addComponent(txt_AppCuti, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        p_perusahaan_main5Layout.setVerticalGroup(
            p_perusahaan_main5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main5Layout.createSequentialGroup()
                .addGroup(p_perusahaan_main5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_AppCuti, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jPanel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p_transAppCutiLayout = new javax.swing.GroupLayout(p_transAppCuti);
        p_transAppCuti.setLayout(p_transAppCutiLayout);
        p_transAppCutiLayout.setHorizontalGroup(
            p_transAppCutiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_perusahaan_header5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p_perusahaan_main5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p_transAppCutiLayout.setVerticalGroup(
            p_transAppCutiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_transAppCutiLayout.createSequentialGroup()
                .addComponent(p_perusahaan_header5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(p_perusahaan_main5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        p_utama.add(p_transAppCuti, "p_appCuti");

        p_transLembur.setBackground(new java.awt.Color(254, 255, 230));

        p_perusahaan_header6.setBackground(new java.awt.Color(5, 32, 56));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(231, 238, 126));
        jLabel27.setText("Form Input  /  Pengajuan & Status Pengajuan Lembur");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(231, 238, 126));
        jLabel28.setText("Tambah Dan Monitoring Status Pengajuan Lembur Didalam Database Aplikasi ___________");

        javax.swing.GroupLayout p_perusahaan_header6Layout = new javax.swing.GroupLayout(p_perusahaan_header6);
        p_perusahaan_header6.setLayout(p_perusahaan_header6Layout);
        p_perusahaan_header6Layout.setHorizontalGroup(
            p_perusahaan_header6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header6Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(p_perusahaan_header6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel27))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_perusahaan_header6Layout.setVerticalGroup(
            p_perusahaan_header6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header6Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        p_perusahaan_main6.setBackground(new java.awt.Color(254, 255, 230));

        tab_transLembur.setBackground(new java.awt.Color(254, 255, 230));
        tab_transLembur.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tab_transLembur.setForeground(new java.awt.Color(5, 32, 56));
        tab_transLembur.setModel(new javax.swing.table.DefaultTableModel(
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
        tab_transLembur.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tab_transLembur.setOpaque(false);
        tab_transLembur.setRowHeight(25);
        tab_transLembur.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tab_transLembur.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tab_transLembur.setShowHorizontalLines(false);
        tab_transLembur.setShowVerticalLines(false);
        tab_transLembur.getTableHeader().setReorderingAllowed(false);
        tab_transLembur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_transLemburMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tab_transLembur);

        txt_lembur.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_lembur.setForeground(new java.awt.Color(5, 32, 56));
        txt_lembur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_lemburActionPerformed(evt);
            }
        });
        txt_lembur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_lemburKeyPressed(evt);
            }
        });

        jPanel26.setBackground(new java.awt.Color(67, 131, 113));

        department_b_tambah4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        department_b_tambah4.setForeground(new java.awt.Color(231, 238, 126));
        department_b_tambah4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        department_b_tambah4.setText("TAMBAH");
        department_b_tambah4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        department_b_tambah4.setIconTextGap(8);
        department_b_tambah4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                department_b_tambah4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(department_b_tambah4, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(department_b_tambah4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel27.setBackground(new java.awt.Color(162, 34, 39));

        department_b_hapus3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        department_b_hapus3.setForeground(new java.awt.Color(231, 238, 126));
        department_b_hapus3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        department_b_hapus3.setText("   HAPUS");
        department_b_hapus3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        department_b_hapus3.setIconTextGap(8);
        department_b_hapus3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                department_b_hapus3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(department_b_hapus3, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(department_b_hapus3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel30.setBackground(new java.awt.Color(5, 32, 56));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(231, 238, 126));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        jLabel29.setText("CARI/REFRESH");
        jLabel29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel29.setIconTextGap(8);
        jLabel29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel29MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel31.setBackground(new java.awt.Color(5, 32, 56));

        Department_b_edit4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Department_b_edit4.setForeground(new java.awt.Color(231, 238, 126));
        Department_b_edit4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        Department_b_edit4.setText("     EDIT");
        Department_b_edit4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Department_b_edit4.setIconTextGap(8);
        Department_b_edit4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Department_b_edit4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Department_b_edit4, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Department_b_edit4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p_perusahaan_main6Layout = new javax.swing.GroupLayout(p_perusahaan_main6);
        p_perusahaan_main6.setLayout(p_perusahaan_main6Layout);
        p_perusahaan_main6Layout.setHorizontalGroup(
            p_perusahaan_main6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main6Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(p_perusahaan_main6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(p_perusahaan_main6Layout.createSequentialGroup()
                        .addComponent(txt_lembur, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        p_perusahaan_main6Layout.setVerticalGroup(
            p_perusahaan_main6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main6Layout.createSequentialGroup()
                .addGroup(p_perusahaan_main6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_lembur, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p_transLemburLayout = new javax.swing.GroupLayout(p_transLembur);
        p_transLembur.setLayout(p_transLemburLayout);
        p_transLemburLayout.setHorizontalGroup(
            p_transLemburLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_perusahaan_header6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p_perusahaan_main6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p_transLemburLayout.setVerticalGroup(
            p_transLemburLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_transLemburLayout.createSequentialGroup()
                .addComponent(p_perusahaan_header6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(p_perusahaan_main6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        p_utama.add(p_transLembur, "p_transLembur");

        p_transAppLembur.setBackground(new java.awt.Color(254, 255, 230));

        p_perusahaan_header7.setBackground(new java.awt.Color(5, 32, 56));

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(231, 238, 126));
        jLabel30.setText("Form Approval  /  Persetujuan & Monitoring Status Lembur");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(231, 238, 126));
        jLabel31.setText("Tambah Dan Monitoring Status Pengajuan Lemburan Didalam Database Aplikasi ___________");

        javax.swing.GroupLayout p_perusahaan_header7Layout = new javax.swing.GroupLayout(p_perusahaan_header7);
        p_perusahaan_header7.setLayout(p_perusahaan_header7Layout);
        p_perusahaan_header7Layout.setHorizontalGroup(
            p_perusahaan_header7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header7Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(p_perusahaan_header7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel30))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_perusahaan_header7Layout.setVerticalGroup(
            p_perusahaan_header7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header7Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        p_perusahaan_main7.setBackground(new java.awt.Color(254, 255, 230));

        tab_appLembur.setBackground(new java.awt.Color(254, 255, 230));
        tab_appLembur.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tab_appLembur.setForeground(new java.awt.Color(5, 32, 56));
        tab_appLembur.setModel(new javax.swing.table.DefaultTableModel(
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
        tab_appLembur.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tab_appLembur.setOpaque(false);
        tab_appLembur.setRowHeight(25);
        tab_appLembur.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tab_appLembur.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tab_appLembur.setShowHorizontalLines(false);
        tab_appLembur.setShowVerticalLines(false);
        tab_appLembur.getTableHeader().setReorderingAllowed(false);
        tab_appLembur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_appLemburMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tab_appLembur);

        txt_AppLembur.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_AppLembur.setForeground(new java.awt.Color(5, 32, 56));
        txt_AppLembur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AppLemburActionPerformed(evt);
            }
        });
        txt_AppLembur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_AppLemburKeyPressed(evt);
            }
        });

        jPanel32.setBackground(new java.awt.Color(5, 32, 56));

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(231, 238, 126));
        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        jLabel48.setText("CARI/REFRESH");
        jLabel48.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel48.setIconTextGap(8);
        jLabel48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel48MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel33.setBackground(new java.awt.Color(5, 32, 56));

        Department_b_edit5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Department_b_edit5.setForeground(new java.awt.Color(231, 238, 126));
        Department_b_edit5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        Department_b_edit5.setText("     PROSES APPROVAL");
        Department_b_edit5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Department_b_edit5.setIconTextGap(8);
        Department_b_edit5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Department_b_edit5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Department_b_edit5, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Department_b_edit5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p_perusahaan_main7Layout = new javax.swing.GroupLayout(p_perusahaan_main7);
        p_perusahaan_main7.setLayout(p_perusahaan_main7Layout);
        p_perusahaan_main7Layout.setHorizontalGroup(
            p_perusahaan_main7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main7Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(p_perusahaan_main7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(p_perusahaan_main7Layout.createSequentialGroup()
                        .addComponent(txt_AppLembur, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        p_perusahaan_main7Layout.setVerticalGroup(
            p_perusahaan_main7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main7Layout.createSequentialGroup()
                .addGroup(p_perusahaan_main7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel32, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_AppLembur, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jPanel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p_transAppLemburLayout = new javax.swing.GroupLayout(p_transAppLembur);
        p_transAppLembur.setLayout(p_transAppLemburLayout);
        p_transAppLemburLayout.setHorizontalGroup(
            p_transAppLemburLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_perusahaan_header7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p_perusahaan_main7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p_transAppLemburLayout.setVerticalGroup(
            p_transAppLemburLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_transAppLemburLayout.createSequentialGroup()
                .addComponent(p_perusahaan_header7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(p_perusahaan_main7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        p_utama.add(p_transAppLembur, "p_appLembur");

        p_transReimburse.setBackground(new java.awt.Color(254, 255, 230));

        p_perusahaan_header10.setBackground(new java.awt.Color(5, 32, 56));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(231, 238, 126));
        jLabel49.setText("Form Input  /  Pengajuan & Status Pengajuan Reimburse");

        jLabel50.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(231, 238, 126));
        jLabel50.setText("Tambah Dan Monitoring Status Pengajuan Reimburse Didalam Database Aplikasi ___________");

        javax.swing.GroupLayout p_perusahaan_header10Layout = new javax.swing.GroupLayout(p_perusahaan_header10);
        p_perusahaan_header10.setLayout(p_perusahaan_header10Layout);
        p_perusahaan_header10Layout.setHorizontalGroup(
            p_perusahaan_header10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header10Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(p_perusahaan_header10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50)
                    .addComponent(jLabel49))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_perusahaan_header10Layout.setVerticalGroup(
            p_perusahaan_header10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header10Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel49)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        p_perusahaan_main10.setBackground(new java.awt.Color(254, 255, 230));

        tab_transReimburse.setBackground(new java.awt.Color(254, 255, 230));
        tab_transReimburse.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tab_transReimburse.setForeground(new java.awt.Color(5, 32, 56));
        tab_transReimburse.setModel(new javax.swing.table.DefaultTableModel(
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
        tab_transReimburse.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tab_transReimburse.setOpaque(false);
        tab_transReimburse.setRowHeight(25);
        tab_transReimburse.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tab_transReimburse.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tab_transReimburse.setShowHorizontalLines(false);
        tab_transReimburse.setShowVerticalLines(false);
        tab_transReimburse.getTableHeader().setReorderingAllowed(false);
        tab_transReimburse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_transReimburseMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tab_transReimburse);

        txt_reimburse.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_reimburse.setForeground(new java.awt.Color(5, 32, 56));
        txt_reimburse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_reimburseActionPerformed(evt);
            }
        });
        txt_reimburse.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_reimburseKeyPressed(evt);
            }
        });

        jPanel34.setBackground(new java.awt.Color(67, 131, 113));

        department_b_tambah5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        department_b_tambah5.setForeground(new java.awt.Color(231, 238, 126));
        department_b_tambah5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        department_b_tambah5.setText("TAMBAH");
        department_b_tambah5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        department_b_tambah5.setIconTextGap(8);
        department_b_tambah5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                department_b_tambah5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(department_b_tambah5, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(department_b_tambah5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel35.setBackground(new java.awt.Color(162, 34, 39));

        department_b_hapus4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        department_b_hapus4.setForeground(new java.awt.Color(231, 238, 126));
        department_b_hapus4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        department_b_hapus4.setText("   HAPUS");
        department_b_hapus4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        department_b_hapus4.setIconTextGap(8);
        department_b_hapus4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                department_b_hapus4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(department_b_hapus4, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(department_b_hapus4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel36.setBackground(new java.awt.Color(5, 32, 56));

        jLabel51.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(231, 238, 126));
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        jLabel51.setText("CARI/REFRESH");
        jLabel51.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel51.setIconTextGap(8);
        jLabel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel51MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel37.setBackground(new java.awt.Color(5, 32, 56));

        Department_b_edit6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Department_b_edit6.setForeground(new java.awt.Color(231, 238, 126));
        Department_b_edit6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        Department_b_edit6.setText("     EDIT");
        Department_b_edit6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Department_b_edit6.setIconTextGap(8);
        Department_b_edit6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Department_b_edit6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Department_b_edit6, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Department_b_edit6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p_perusahaan_main10Layout = new javax.swing.GroupLayout(p_perusahaan_main10);
        p_perusahaan_main10.setLayout(p_perusahaan_main10Layout);
        p_perusahaan_main10Layout.setHorizontalGroup(
            p_perusahaan_main10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main10Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(p_perusahaan_main10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(p_perusahaan_main10Layout.createSequentialGroup()
                        .addComponent(txt_reimburse, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        p_perusahaan_main10Layout.setVerticalGroup(
            p_perusahaan_main10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main10Layout.createSequentialGroup()
                .addGroup(p_perusahaan_main10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel36, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_reimburse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jPanel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p_transReimburseLayout = new javax.swing.GroupLayout(p_transReimburse);
        p_transReimburse.setLayout(p_transReimburseLayout);
        p_transReimburseLayout.setHorizontalGroup(
            p_transReimburseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_perusahaan_header10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p_perusahaan_main10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p_transReimburseLayout.setVerticalGroup(
            p_transReimburseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_transReimburseLayout.createSequentialGroup()
                .addComponent(p_perusahaan_header10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(p_perusahaan_main10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        p_utama.add(p_transReimburse, "p_transReimburse");

        p_transAppReimburse.setBackground(new java.awt.Color(254, 255, 230));

        p_perusahaan_header11.setBackground(new java.awt.Color(5, 32, 56));

        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(231, 238, 126));
        jLabel52.setText("Form Approval  /  Persetujuan & Monitoring Status Reimbursement");

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(231, 238, 126));
        jLabel53.setText("Tambah Dan Monitoring Status Pengajuan Reimburse Didalam Database Aplikasi ___________");

        javax.swing.GroupLayout p_perusahaan_header11Layout = new javax.swing.GroupLayout(p_perusahaan_header11);
        p_perusahaan_header11.setLayout(p_perusahaan_header11Layout);
        p_perusahaan_header11Layout.setHorizontalGroup(
            p_perusahaan_header11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header11Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(p_perusahaan_header11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel53)
                    .addComponent(jLabel52))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_perusahaan_header11Layout.setVerticalGroup(
            p_perusahaan_header11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header11Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        p_perusahaan_main11.setBackground(new java.awt.Color(254, 255, 230));

        tab_appReimburse.setBackground(new java.awt.Color(254, 255, 230));
        tab_appReimburse.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tab_appReimburse.setForeground(new java.awt.Color(5, 32, 56));
        tab_appReimburse.setModel(new javax.swing.table.DefaultTableModel(
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
        tab_appReimburse.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tab_appReimburse.setOpaque(false);
        tab_appReimburse.setRowHeight(25);
        tab_appReimburse.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tab_appReimburse.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tab_appReimburse.setShowHorizontalLines(false);
        tab_appReimburse.setShowVerticalLines(false);
        tab_appReimburse.getTableHeader().setReorderingAllowed(false);
        tab_appReimburse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_appReimburseMouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tab_appReimburse);

        txt_AppReimburse.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_AppReimburse.setForeground(new java.awt.Color(5, 32, 56));
        txt_AppReimburse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_AppReimburseActionPerformed(evt);
            }
        });
        txt_AppReimburse.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_AppReimburseKeyPressed(evt);
            }
        });

        jPanel38.setBackground(new java.awt.Color(5, 32, 56));

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(231, 238, 126));
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        jLabel54.setText("CARI/REFRESH");
        jLabel54.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel54.setIconTextGap(8);
        jLabel54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel54MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel39.setBackground(new java.awt.Color(5, 32, 56));

        Department_b_edit7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Department_b_edit7.setForeground(new java.awt.Color(231, 238, 126));
        Department_b_edit7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        Department_b_edit7.setText("     PROSES APPROVAL");
        Department_b_edit7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Department_b_edit7.setIconTextGap(8);
        Department_b_edit7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Department_b_edit7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Department_b_edit7, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Department_b_edit7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p_perusahaan_main11Layout = new javax.swing.GroupLayout(p_perusahaan_main11);
        p_perusahaan_main11.setLayout(p_perusahaan_main11Layout);
        p_perusahaan_main11Layout.setHorizontalGroup(
            p_perusahaan_main11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main11Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(p_perusahaan_main11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(p_perusahaan_main11Layout.createSequentialGroup()
                        .addComponent(txt_AppReimburse, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        p_perusahaan_main11Layout.setVerticalGroup(
            p_perusahaan_main11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main11Layout.createSequentialGroup()
                .addGroup(p_perusahaan_main11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_AppReimburse, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jPanel39, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p_transAppReimburseLayout = new javax.swing.GroupLayout(p_transAppReimburse);
        p_transAppReimburse.setLayout(p_transAppReimburseLayout);
        p_transAppReimburseLayout.setHorizontalGroup(
            p_transAppReimburseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_perusahaan_header11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p_perusahaan_main11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p_transAppReimburseLayout.setVerticalGroup(
            p_transAppReimburseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_transAppReimburseLayout.createSequentialGroup()
                .addComponent(p_perusahaan_header11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(p_perusahaan_main11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        p_utama.add(p_transAppReimburse, "p_appReimburse");

        p_transDinas.setBackground(new java.awt.Color(254, 255, 230));

        p_perusahaan_header12.setBackground(new java.awt.Color(5, 32, 56));

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(231, 238, 126));
        jLabel55.setText("Form Input  /  Pengajuan & Status Pengajuan Dinas Luar");

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(231, 238, 126));
        jLabel56.setText("Tambah Dan Monitoring Status Pengajuan Dinas Luar Didalam Database Aplikasi ___________");

        javax.swing.GroupLayout p_perusahaan_header12Layout = new javax.swing.GroupLayout(p_perusahaan_header12);
        p_perusahaan_header12.setLayout(p_perusahaan_header12Layout);
        p_perusahaan_header12Layout.setHorizontalGroup(
            p_perusahaan_header12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header12Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(p_perusahaan_header12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel56)
                    .addComponent(jLabel55))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_perusahaan_header12Layout.setVerticalGroup(
            p_perusahaan_header12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header12Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        p_perusahaan_main12.setBackground(new java.awt.Color(254, 255, 230));

        tab_transDinas.setBackground(new java.awt.Color(254, 255, 230));
        tab_transDinas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tab_transDinas.setForeground(new java.awt.Color(5, 32, 56));
        tab_transDinas.setModel(new javax.swing.table.DefaultTableModel(
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
        tab_transDinas.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tab_transDinas.setOpaque(false);
        tab_transDinas.setRowHeight(25);
        tab_transDinas.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tab_transDinas.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tab_transDinas.setShowHorizontalLines(false);
        tab_transDinas.setShowVerticalLines(false);
        tab_transDinas.getTableHeader().setReorderingAllowed(false);
        tab_transDinas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_transDinasMouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tab_transDinas);

        txt_dinas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_dinas.setForeground(new java.awt.Color(5, 32, 56));
        txt_dinas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dinasActionPerformed(evt);
            }
        });
        txt_dinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_dinasKeyPressed(evt);
            }
        });

        jPanel40.setBackground(new java.awt.Color(67, 131, 113));

        department_b_tambah6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        department_b_tambah6.setForeground(new java.awt.Color(231, 238, 126));
        department_b_tambah6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_add_24px.png"))); // NOI18N
        department_b_tambah6.setText("TAMBAH");
        department_b_tambah6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        department_b_tambah6.setIconTextGap(8);
        department_b_tambah6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                department_b_tambah6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(department_b_tambah6, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(department_b_tambah6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel41.setBackground(new java.awt.Color(162, 34, 39));

        department_b_hapus5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        department_b_hapus5.setForeground(new java.awt.Color(231, 238, 126));
        department_b_hapus5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_trash_can_24px.png"))); // NOI18N
        department_b_hapus5.setText("   HAPUS");
        department_b_hapus5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        department_b_hapus5.setIconTextGap(8);
        department_b_hapus5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                department_b_hapus5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(department_b_hapus5, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(department_b_hapus5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel42.setBackground(new java.awt.Color(5, 32, 56));

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(231, 238, 126));
        jLabel57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        jLabel57.setText("CARI/REFRESH");
        jLabel57.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel57.setIconTextGap(8);
        jLabel57.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel57MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel43.setBackground(new java.awt.Color(5, 32, 56));

        Department_b_edit8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Department_b_edit8.setForeground(new java.awt.Color(231, 238, 126));
        Department_b_edit8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        Department_b_edit8.setText("     EDIT");
        Department_b_edit8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Department_b_edit8.setIconTextGap(8);
        Department_b_edit8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Department_b_edit8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Department_b_edit8, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Department_b_edit8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p_perusahaan_main12Layout = new javax.swing.GroupLayout(p_perusahaan_main12);
        p_perusahaan_main12.setLayout(p_perusahaan_main12Layout);
        p_perusahaan_main12Layout.setHorizontalGroup(
            p_perusahaan_main12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main12Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(p_perusahaan_main12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(p_perusahaan_main12Layout.createSequentialGroup()
                        .addComponent(txt_dinas, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        p_perusahaan_main12Layout.setVerticalGroup(
            p_perusahaan_main12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main12Layout.createSequentialGroup()
                .addGroup(p_perusahaan_main12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel42, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_dinas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jPanel43, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p_transDinasLayout = new javax.swing.GroupLayout(p_transDinas);
        p_transDinas.setLayout(p_transDinasLayout);
        p_transDinasLayout.setHorizontalGroup(
            p_transDinasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_perusahaan_header12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p_perusahaan_main12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p_transDinasLayout.setVerticalGroup(
            p_transDinasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_transDinasLayout.createSequentialGroup()
                .addComponent(p_perusahaan_header12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(p_perusahaan_main12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        p_utama.add(p_transDinas, "p_transDinas");

        p_transAppDinas.setBackground(new java.awt.Color(254, 255, 230));

        p_perusahaan_header13.setBackground(new java.awt.Color(5, 32, 56));

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(231, 238, 126));
        jLabel58.setText("Form Approval  /  Persetujuan & Monitoring Status Pengajuan Dinas Luar");

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(231, 238, 126));
        jLabel59.setText("Tambah Dan Monitoring Status Pengajuan Dinas Luar Didalam Database Aplikasi ___________");

        javax.swing.GroupLayout p_perusahaan_header13Layout = new javax.swing.GroupLayout(p_perusahaan_header13);
        p_perusahaan_header13.setLayout(p_perusahaan_header13Layout);
        p_perusahaan_header13Layout.setHorizontalGroup(
            p_perusahaan_header13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header13Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(p_perusahaan_header13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel59)
                    .addComponent(jLabel58))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        p_perusahaan_header13Layout.setVerticalGroup(
            p_perusahaan_header13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_header13Layout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel58)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        p_perusahaan_main13.setBackground(new java.awt.Color(254, 255, 230));

        tab_appDinas.setBackground(new java.awt.Color(254, 255, 230));
        tab_appDinas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tab_appDinas.setForeground(new java.awt.Color(5, 32, 56));
        tab_appDinas.setModel(new javax.swing.table.DefaultTableModel(
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
        tab_appDinas.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tab_appDinas.setOpaque(false);
        tab_appDinas.setRowHeight(25);
        tab_appDinas.setSelectionBackground(new java.awt.Color(5, 32, 56));
        tab_appDinas.setSelectionForeground(new java.awt.Color(231, 238, 126));
        tab_appDinas.setShowHorizontalLines(false);
        tab_appDinas.setShowVerticalLines(false);
        tab_appDinas.getTableHeader().setReorderingAllowed(false);
        tab_appDinas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tab_appDinasMouseClicked(evt);
            }
        });
        jScrollPane13.setViewportView(tab_appDinas);

        txt_appDinas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_appDinas.setForeground(new java.awt.Color(5, 32, 56));
        txt_appDinas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_appDinasActionPerformed(evt);
            }
        });
        txt_appDinas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_appDinasKeyPressed(evt);
            }
        });

        jPanel44.setBackground(new java.awt.Color(5, 32, 56));

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(231, 238, 126));
        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_search_24px.png"))); // NOI18N
        jLabel60.setText("CARI/REFRESH");
        jLabel60.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel60.setIconTextGap(8);
        jLabel60.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel60MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel45.setBackground(new java.awt.Color(5, 32, 56));

        Department_b_edit9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Department_b_edit9.setForeground(new java.awt.Color(231, 238, 126));
        Department_b_edit9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_edit_24px_1.png"))); // NOI18N
        Department_b_edit9.setText("     PROSES APPROVAL");
        Department_b_edit9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Department_b_edit9.setIconTextGap(8);
        Department_b_edit9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Department_b_edit9MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel45Layout = new javax.swing.GroupLayout(jPanel45);
        jPanel45.setLayout(jPanel45Layout);
        jPanel45Layout.setHorizontalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel45Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Department_b_edit9, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
        );
        jPanel45Layout.setVerticalGroup(
            jPanel45Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Department_b_edit9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p_perusahaan_main13Layout = new javax.swing.GroupLayout(p_perusahaan_main13);
        p_perusahaan_main13.setLayout(p_perusahaan_main13Layout);
        p_perusahaan_main13Layout.setHorizontalGroup(
            p_perusahaan_main13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main13Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(p_perusahaan_main13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(p_perusahaan_main13Layout.createSequentialGroup()
                        .addComponent(txt_appDinas, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        p_perusahaan_main13Layout.setVerticalGroup(
            p_perusahaan_main13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_perusahaan_main13Layout.createSequentialGroup()
                .addGroup(p_perusahaan_main13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_appDinas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(jPanel45, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout p_transAppDinasLayout = new javax.swing.GroupLayout(p_transAppDinas);
        p_transAppDinas.setLayout(p_transAppDinasLayout);
        p_transAppDinasLayout.setHorizontalGroup(
            p_transAppDinasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(p_perusahaan_header13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p_perusahaan_main13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        p_transAppDinasLayout.setVerticalGroup(
            p_transAppDinasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p_transAppDinasLayout.createSequentialGroup()
                .addComponent(p_perusahaan_header13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(p_perusahaan_main13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        p_utama.add(p_transAppDinas, "p_appDinas");

        jPanel1.add(p_utama, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 1120, 740));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menu_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_homeMouseClicked
        // TODO add your handling code here:

        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_home");
       // panelHome();

        int_data_list=0;
        int_home_list=1;
        int_items_list=0;
        int_trans_list=0;
        int_input_list=0;
        coloring();
        coloring2();
        coloring3();
        coloring4();
        coloring5();
    }//GEN-LAST:event_menu_homeMouseClicked

    private void menu_dataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_dataMouseClicked
        // TODO add your handling code here:
        int_data_list=1;
        int_home_list=0;
        int_items_list=0;
        int_trans_list=0;
        int_input_list=0;
        coloring();
        coloring2();
        coloring3();
        coloring4();
        coloring5();
    }//GEN-LAST:event_menu_dataMouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_user");
        tab_karyawan();
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_department");
        tab_department();
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_jabatan");
        tab_jabatan();
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jLabel8KeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jLabel8KeyPressed

    private void menu_itemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_itemsMouseClicked
        // TODO add your handling code here:
        int_data_list=0;
        int_home_list=0;
        int_items_list=1;
        int_trans_list=0;
        int_input_list=0;
        coloring();
        coloring2();
        coloring3();
        coloring4();
        coloring5();
    }//GEN-LAST:event_menu_itemsMouseClicked

    private void jLabel21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel21MouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_appCuti");
        tab_transApprovalCuti();
    }//GEN-LAST:event_jLabel21MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_appLembur");
        tab_transApprovalLembur();
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_appReimburse");
        tab_transApprovalReimburse();
    }//GEN-LAST:event_jLabel23MouseClicked

    private void tab_departmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_departmentMouseClicked
        // TODO add your handling code here:
        int bar=tab_department.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        id_department=a;
    }//GEN-LAST:event_tab_departmentMouseClicked

    private void txt_departmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_departmentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_departmentActionPerformed

    private void txt_departmentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_departmentKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            tab_department();
        }
    }//GEN-LAST:event_txt_departmentKeyPressed

    private void department_b_tambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_department_b_tambahMouseClicked
        // TODO add your handling code here:
        reg_department pp = new reg_department();
        pp.setVisible(true);
    }//GEN-LAST:event_department_b_tambahMouseClicked

    private void department_b_hapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_department_b_hapusMouseClicked
        // TODO add your handling code here:
        String a = id_department;
        int ok = JOptionPane.showConfirmDialog(null,"Hapus Data "+a+"?","konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql="delete from department where kode='"+a+"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"data department berhasil dihapus");
                tab_department();
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog (null,"data gagal dihapus"+e);
            }
        }
    }//GEN-LAST:event_department_b_hapusMouseClicked

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        // TODO add your handling code here:
        tab_department();
    }//GEN-LAST:event_jLabel12MouseClicked

    private void Department_b_editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Department_b_editMouseClicked
        // TODO add your handling code here:
        reg_department pp = new reg_department();
        //pp.datab = this;
        pp.getDataid(id_department);
        pp.setVisible(true);
    }//GEN-LAST:event_Department_b_editMouseClicked

    private void repKaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repKaryawanMouseClicked
        // TODO add your handling code here:
        cetakAllKaryawan();
    }//GEN-LAST:event_repKaryawanMouseClicked

    private void cb_department1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cb_department1MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cb_department1MouseClicked

    private void cb_department1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_department1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_cb_department1ActionPerformed

    private void jLabel43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel43MouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_appDinas");
        tab_transApprovalDinas();
    }//GEN-LAST:event_jLabel43MouseClicked

    private void menu_home1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_home1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_menu_home1MouseClicked

    private void menu_inputMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_inputMouseClicked
        // TODO add your handling code here:
        int_data_list=0;
        int_home_list=0;
        int_items_list=0;
        int_trans_list=0;
        int_input_list=1;
        coloring();
        coloring2();
        coloring3();
        coloring4();
        coloring5();
    }//GEN-LAST:event_menu_inputMouseClicked

    private void jLabel44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_transCuti");
        tab_transCuti();
    }//GEN-LAST:event_jLabel44MouseClicked

    private void jLabel45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_transLembur");
        tab_transLembur();
        
    }//GEN-LAST:event_jLabel45MouseClicked

    private void jLabel46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_transReimburse");
        tab_transReimburse();    
        
    }//GEN-LAST:event_jLabel46MouseClicked

    private void jLabel47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel47MouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_transDinas");
        tab_transDinas();  
    }//GEN-LAST:event_jLabel47MouseClicked

    private void tab_karyawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_karyawanMouseClicked
        // TODO add your handling code here:
        int bar=tab_karyawan.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        id_ekaryawan=a;
    }//GEN-LAST:event_tab_karyawanMouseClicked

    private void txt_karyawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_karyawanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_karyawanActionPerformed

    private void txt_karyawanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_karyawanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_karyawanKeyPressed

    private void department_b_tambah1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_department_b_tambah1MouseClicked
        // TODO add your handling code here:
        reg_karyawan pp = new reg_karyawan();
        pp.setVisible(true);
    }//GEN-LAST:event_department_b_tambah1MouseClicked

    private void karyawan_b_hapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karyawan_b_hapusMouseClicked
        // TODO add your handling code here:
        String a = id_ekaryawan;
        int ok = JOptionPane.showConfirmDialog(null,"Hapus Data "+a+"?","konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql="delete from karyawan where id='"+a+"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"data Karyawan berhasil dihapus");
                tab_karyawan();
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog (null,"data gagal dihapus"+e);
            }
        }
    }//GEN-LAST:event_karyawan_b_hapusMouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
        tab_karyawan();
    }//GEN-LAST:event_jLabel15MouseClicked

    private void karyawan_b_editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karyawan_b_editMouseClicked
        // TODO add your handling code here:
        reg_karyawan pp = new reg_karyawan();
        //pp.datab = this;
        pp.getDataid(id_ekaryawan);
        pp.setVisible(true);
    }//GEN-LAST:event_karyawan_b_editMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();

        // membuat titik x dan y
        int x = layar.width / 2  - this.getSize().width / 2;
        int y = layar.height / 2 - this.getSize().height / 2;

        this.setLocation(x, y);
    }//GEN-LAST:event_formWindowOpened

    private void tab_jabatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_jabatanMouseClicked
        // TODO add your handling code here:
        int bar=tab_jabatan.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        id_jabatan=a;
    }//GEN-LAST:event_tab_jabatanMouseClicked

    private void txt_transCutiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_transCutiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_transCutiActionPerformed

    private void txt_transCutiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_transCutiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_transCutiKeyPressed

    private void department_b_tambah2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_department_b_tambah2MouseClicked
        // TODO add your handling code here:
        reg_jabatan pp = new reg_jabatan();
        pp.setVisible(true);
        
    }//GEN-LAST:event_department_b_tambah2MouseClicked

    private void department_b_hapus1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_department_b_hapus1MouseClicked
        // TODO add your handling code here:
        
        String a = id_jabatan;
        int ok = JOptionPane.showConfirmDialog(null,"Hapus Data "+a+"?","konfirmasi Hapus Data",JOptionPane.YES_NO_OPTION);
        if (ok==0){
            String sql="delete from jabatan where kode_jabatan='"+a+"'";
            try{
                PreparedStatement stat = conn.prepareStatement(sql);
                stat.executeUpdate();
                JOptionPane.showMessageDialog(null,"data Jabatan berhasil dihapus");
                tab_jabatan();
            }
            catch (SQLException e){
                JOptionPane.showMessageDialog (null,"data gagal dihapus"+e);
            }
        }
    }//GEN-LAST:event_department_b_hapus1MouseClicked

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
        // TODO add your handling code here:
        tab_jabatan();
    }//GEN-LAST:event_jLabel14MouseClicked

    private void Department_b_edit1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Department_b_edit1MouseClicked
        // TODO add your handling code here:
        reg_jabatan pp = new reg_jabatan();
        //pp.datab = this;
        pp.getDataid(id_jabatan);
        pp.setVisible(true);
    }//GEN-LAST:event_Department_b_edit1MouseClicked

    private void tab_transCutiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_transCutiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tab_transCutiMouseClicked

    private void txt_department2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_department2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_department2ActionPerformed

    private void txt_department2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_department2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_department2KeyPressed

    private void department_b_tambah3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_department_b_tambah3MouseClicked
        // TODO add your handling code here:
        trans_cuti pp = new trans_cuti();
        pp.getDataid2(login_form.uName);
        pp.setVisible(true);
    }//GEN-LAST:event_department_b_tambah3MouseClicked

    private void department_b_hapus2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_department_b_hapus2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_department_b_hapus2MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        // TODO add your handling code here:
         CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_transCuti");
        tab_transCuti();
    }//GEN-LAST:event_jLabel18MouseClicked

    private void Department_b_edit2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Department_b_edit2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Department_b_edit2MouseClicked

    private void tab_appCutiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_appCutiMouseClicked
        // TODO add your handling code here:
        int bar=tab_appCuti.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        id_cuti=a;
        
    }//GEN-LAST:event_tab_appCutiMouseClicked

    private void txt_AppCutiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AppCutiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_AppCutiActionPerformed

    private void txt_AppCutiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_AppCutiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_AppCutiKeyPressed

    private void jLabel26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseClicked
        // TODO add your handling code here:
         CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_appCuti");
        tab_transApprovalCuti();
    }//GEN-LAST:event_jLabel26MouseClicked

    private void Department_b_edit3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Department_b_edit3MouseClicked
        // TODO add your handling code here:
        trans_cuti pp = new trans_cuti();
        pp.getDataid(id_cuti);
        pp.setVisible(true);
    }//GEN-LAST:event_Department_b_edit3MouseClicked

    private void tab_transLemburMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_transLemburMouseClicked
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_tab_transLemburMouseClicked

    private void txt_lemburActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_lemburActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_lemburActionPerformed

    private void txt_lemburKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_lemburKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_lemburKeyPressed

    private void department_b_tambah4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_department_b_tambah4MouseClicked
        // TODO add your handling code here:
                
        trans_lembur pp = new trans_lembur();
        pp.getDataid2(login_form.uName);
        pp.setVisible(true);
    }//GEN-LAST:event_department_b_tambah4MouseClicked

    private void department_b_hapus3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_department_b_hapus3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_department_b_hapus3MouseClicked

    private void jLabel29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel29MouseClicked
        
                // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_transLembur");
        tab_transLembur();
    }//GEN-LAST:event_jLabel29MouseClicked

    private void Department_b_edit4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Department_b_edit4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Department_b_edit4MouseClicked

    private void tab_appLemburMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_appLemburMouseClicked
        // TODO add your handling code here:
        int bar=tab_appLembur.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        id_lembur=a;
    }//GEN-LAST:event_tab_appLemburMouseClicked

    private void txt_AppLemburActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AppLemburActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_AppLemburActionPerformed

    private void txt_AppLemburKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_AppLemburKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_AppLemburKeyPressed

    private void jLabel48MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseClicked
        // TODO add your handling code here:
                // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_appLembur");
        tab_transApprovalLembur();
    }//GEN-LAST:event_jLabel48MouseClicked

    private void Department_b_edit5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Department_b_edit5MouseClicked
        // TODO add your handling code here:
        trans_lembur pp = new trans_lembur();
        pp.getDataid(id_lembur);
        pp.setVisible(true);
    }//GEN-LAST:event_Department_b_edit5MouseClicked

    private void jLabel45MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel45MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel45MouseEntered

    private void tab_transReimburseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_transReimburseMouseClicked
        // TODO add your handling code here:
        int bar=tab_transReimburse.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        id_reimburse=a;
    }//GEN-LAST:event_tab_transReimburseMouseClicked

    private void txt_reimburseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_reimburseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_reimburseActionPerformed

    private void txt_reimburseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_reimburseKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_reimburseKeyPressed

    private void department_b_tambah5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_department_b_tambah5MouseClicked
        // TODO add your handling code here:
        trans_reimburse pp = new trans_reimburse();
        pp.getDataid2(login_form.uName);
        pp.setVisible(true);
    }//GEN-LAST:event_department_b_tambah5MouseClicked

    private void department_b_hapus4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_department_b_hapus4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_department_b_hapus4MouseClicked

    private void jLabel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseClicked
        // TODO add your handling code here:
         CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_transReimburse");
        tab_transReimburse();
    }//GEN-LAST:event_jLabel51MouseClicked

    private void Department_b_edit6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Department_b_edit6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Department_b_edit6MouseClicked

    private void tab_appReimburseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_appReimburseMouseClicked
        // TODO add your handling code here:
        int bar=tab_appReimburse.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        id_reimburse=a;
    }//GEN-LAST:event_tab_appReimburseMouseClicked

    private void txt_AppReimburseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_AppReimburseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_AppReimburseActionPerformed

    private void txt_AppReimburseKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_AppReimburseKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_AppReimburseKeyPressed

    private void jLabel54MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_appReimburse");
        tab_transApprovalReimburse();
    }//GEN-LAST:event_jLabel54MouseClicked

    private void Department_b_edit7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Department_b_edit7MouseClicked
        // TODO add your handling code here:
        trans_reimburse pp = new trans_reimburse();
        pp.getDataid(id_reimburse);
        pp.setVisible(true);
    }//GEN-LAST:event_Department_b_edit7MouseClicked

    private void tab_transDinasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_transDinasMouseClicked
        // TODO add your handling code here:
        int bar=tab_transDinas.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        id_dinas=a;
    }//GEN-LAST:event_tab_transDinasMouseClicked

    private void txt_dinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dinasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dinasActionPerformed

    private void txt_dinasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dinasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dinasKeyPressed

    private void department_b_tambah6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_department_b_tambah6MouseClicked
        // TODO add your handling code here:
        trans_dinas pp = new trans_dinas();
        pp.getDataid2(login_form.uName);
        pp.setVisible(true);
        
    }//GEN-LAST:event_department_b_tambah6MouseClicked

    private void department_b_hapus5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_department_b_hapus5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_department_b_hapus5MouseClicked

    private void jLabel57MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel57MouseClicked
        // TODO add your handling code here:
          CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_transDinas");
        tab_transDinas();  
    }//GEN-LAST:event_jLabel57MouseClicked

    private void Department_b_edit8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Department_b_edit8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Department_b_edit8MouseClicked

    private void tab_appDinasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_appDinasMouseClicked
        // TODO add your handling code here:
        int bar=tab_appDinas.getSelectedRow();
        String a = tabmode.getValueAt(bar,0).toString();
        id_dinas=a;
    }//GEN-LAST:event_tab_appDinasMouseClicked

    private void txt_appDinasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_appDinasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_appDinasActionPerformed

    private void txt_appDinasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_appDinasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_appDinasKeyPressed

    private void jLabel60MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel60MouseClicked
        // TODO add your handling code here:
         CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_appDinas");
        tab_transApprovalDinas();
    }//GEN-LAST:event_jLabel60MouseClicked

    private void Department_b_edit9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Department_b_edit9MouseClicked
        // TODO add your handling code here:
       trans_dinas pp = new trans_dinas();
        pp.getDataid(id_dinas);
        pp.setVisible(true);
        
    }//GEN-LAST:event_Department_b_edit9MouseClicked

    private void tab_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_homeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tab_homeMouseClicked

    private void tab_home2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tab_home2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tab_home2MouseClicked

    private void b_passwordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_passwordMouseClicked
        // TODO add your handling code here:
        popup.popup_password pop = new popup.popup_password();
        pop.getId(uname2);
        pop.setVisible(true);
    }//GEN-LAST:event_b_passwordMouseClicked

    private void bCutiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bCutiMouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_transCuti");
        tab_transCuti();
    }//GEN-LAST:event_bCutiMouseClicked

    private void bLemburMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bLemburMouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_transLembur");
        tab_transLembur();
    }//GEN-LAST:event_bLemburMouseClicked

    private void bReimburseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bReimburseMouseClicked
        // TODO add your handling code here:
         CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_transReimburse");
        tab_transReimburse();  
    }//GEN-LAST:event_bReimburseMouseClicked

    private void bDinasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bDinasMouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_transDinas");
        tab_transDinas();  
    }//GEN-LAST:event_bDinasMouseClicked

    private void menu_home2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_home2MouseClicked
        // TODO add your handling code here:
        CardLayout card = (CardLayout)p_utama.getLayout();
        card.show(p_utama, "p_report");
        combo_karyawan();
        combo_department();
    }//GEN-LAST:event_menu_home2MouseClicked

    private void repRekapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repRekapMouseClicked
        // TODO add your handling code here:
        cetakRekap();
    }//GEN-LAST:event_repRekapMouseClicked

    private void cb_slipKaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cb_slipKaryawanMouseClicked
        // TODO add your handling code here:
   
    }//GEN-LAST:event_cb_slipKaryawanMouseClicked

    private void cb_slipKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cb_slipKaryawanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cb_slipKaryawanActionPerformed

    private void repSlipMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repSlipMouseClicked
        // TODO add your handling code here:
       cetakSlip();
    }//GEN-LAST:event_repSlipMouseClicked

    private void repDepartmentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repDepartmentMouseClicked
        // TODO add your handling code here:
        cetakAllDepartment();
    }//GEN-LAST:event_repDepartmentMouseClicked

    private void repJabatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repJabatanMouseClicked
        // TODO add your handling code here:
        cetakAllJabatan();
    }//GEN-LAST:event_repJabatanMouseClicked

    private void repCutiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repCutiMouseClicked
        // TODO add your handling code here:
        cetakCuti();
    }//GEN-LAST:event_repCutiMouseClicked

    private void repLemburMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repLemburMouseClicked
        // TODO add your handling code here:
        cetakLembur();
    }//GEN-LAST:event_repLemburMouseClicked

    private void repReimburseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repReimburseMouseClicked
        // TODO add your handling code here:
        cetakReimburse();
    }//GEN-LAST:event_repReimburseMouseClicked

    private void repDinasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_repDinasMouseClicked
        // TODO add your handling code here:
        cetakDinas();
    }//GEN-LAST:event_repDinasMouseClicked

    private void b_password1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_b_password1MouseClicked
        popup.popup_slip pop = new popup.popup_slip();
        pop.getId(uname2);
        pop.setVisible(true);
    
    }//GEN-LAST:event_b_password1MouseClicked

    /**
     * @param args the command line arguments
     */
    
    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Department_b_edit;
    private javax.swing.JLabel Department_b_edit1;
    private javax.swing.JLabel Department_b_edit2;
    private javax.swing.JLabel Department_b_edit3;
    private javax.swing.JLabel Department_b_edit4;
    private javax.swing.JLabel Department_b_edit5;
    private javax.swing.JLabel Department_b_edit6;
    private javax.swing.JLabel Department_b_edit7;
    private javax.swing.JLabel Department_b_edit8;
    private javax.swing.JLabel Department_b_edit9;
    private javax.swing.JLabel bCuti;
    private javax.swing.JLabel bDinas;
    private javax.swing.JLabel bLembur;
    private javax.swing.JLabel bReimburse;
    private javax.swing.JLabel b_password;
    private javax.swing.JLabel b_password1;
    private javax.swing.JComboBox<String> cb_cuti;
    private javax.swing.JComboBox<String> cb_department1;
    private javax.swing.JComboBox<String> cb_dinas;
    private javax.swing.JComboBox<String> cb_lembur;
    private javax.swing.JComboBox<String> cb_reimburse;
    private javax.swing.JComboBox<String> cb_slipKaryawan;
    private com.toedter.calendar.JMonthChooser cutiMonth;
    private com.toedter.calendar.JYearChooser cutiYear;
    private javax.swing.JLabel department_b_hapus;
    private javax.swing.JLabel department_b_hapus1;
    private javax.swing.JLabel department_b_hapus2;
    private javax.swing.JLabel department_b_hapus3;
    private javax.swing.JLabel department_b_hapus4;
    private javax.swing.JLabel department_b_hapus5;
    private javax.swing.JLabel department_b_tambah;
    private javax.swing.JLabel department_b_tambah1;
    private javax.swing.JLabel department_b_tambah2;
    private javax.swing.JLabel department_b_tambah3;
    private javax.swing.JLabel department_b_tambah4;
    private javax.swing.JLabel department_b_tambah5;
    private javax.swing.JLabel department_b_tambah6;
    private com.toedter.calendar.JMonthChooser dinasMonth;
    private com.toedter.calendar.JYearChooser dinasYear;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JLabel karyawan_b_edit;
    private javax.swing.JLabel karyawan_b_hapus;
    private com.toedter.calendar.JMonthChooser lemburMonth;
    private com.toedter.calendar.JYearChooser lemburYear;
    private javax.swing.JPanel menu_1;
    private javax.swing.JPanel menu_2;
    private javax.swing.JPanel menu_3;
    private javax.swing.JPanel menu_4;
    private javax.swing.JPanel menu_5;
    private javax.swing.JPanel menu_6;
    private javax.swing.JLabel menu_data;
    private javax.swing.JPanel menu_data_list;
    private javax.swing.JLabel menu_home;
    private javax.swing.JLabel menu_home1;
    private javax.swing.JLabel menu_home2;
    private javax.swing.JLabel menu_input;
    private javax.swing.JPanel menu_input_list;
    private javax.swing.JLabel menu_items;
    private javax.swing.JPanel menu_items_list;
    private javax.swing.JLabel noGaji;
    private javax.swing.JPanel p_department;
    private javax.swing.JPanel p_home;
    private javax.swing.JPanel p_jabatan;
    private javax.swing.JPanel p_perusahaan_header1;
    private javax.swing.JPanel p_perusahaan_header10;
    private javax.swing.JPanel p_perusahaan_header11;
    private javax.swing.JPanel p_perusahaan_header12;
    private javax.swing.JPanel p_perusahaan_header13;
    private javax.swing.JPanel p_perusahaan_header2;
    private javax.swing.JPanel p_perusahaan_header3;
    private javax.swing.JPanel p_perusahaan_header4;
    private javax.swing.JPanel p_perusahaan_header5;
    private javax.swing.JPanel p_perusahaan_header6;
    private javax.swing.JPanel p_perusahaan_header7;
    private javax.swing.JPanel p_perusahaan_header8;
    private javax.swing.JPanel p_perusahaan_header9;
    private javax.swing.JPanel p_perusahaan_main1;
    private javax.swing.JPanel p_perusahaan_main10;
    private javax.swing.JPanel p_perusahaan_main11;
    private javax.swing.JPanel p_perusahaan_main12;
    private javax.swing.JPanel p_perusahaan_main13;
    private javax.swing.JPanel p_perusahaan_main2;
    private javax.swing.JPanel p_perusahaan_main3;
    private javax.swing.JPanel p_perusahaan_main4;
    private javax.swing.JPanel p_perusahaan_main5;
    private javax.swing.JPanel p_perusahaan_main6;
    private javax.swing.JPanel p_perusahaan_main7;
    private javax.swing.JPanel p_perusahaan_main8;
    private javax.swing.JPanel p_perusahaan_main9;
    private javax.swing.JPanel p_reports;
    private javax.swing.JPanel p_sidebar;
    private javax.swing.JPanel p_transAppCuti;
    private javax.swing.JPanel p_transAppDinas;
    private javax.swing.JPanel p_transAppLembur;
    private javax.swing.JPanel p_transAppReimburse;
    private javax.swing.JPanel p_transCuti;
    private javax.swing.JPanel p_transDinas;
    private javax.swing.JPanel p_transLembur;
    private javax.swing.JPanel p_transReimburse;
    private javax.swing.JPanel p_user;
    private javax.swing.JPanel p_utama;
    private com.toedter.calendar.JMonthChooser reimburseMonth;
    private com.toedter.calendar.JYearChooser reimburseYear;
    private com.toedter.calendar.JMonthChooser rekapMonth;
    private com.toedter.calendar.JYearChooser rekapYear;
    private javax.swing.JLabel repCuti;
    private javax.swing.JLabel repDepartment;
    private javax.swing.JLabel repDinas;
    private javax.swing.JLabel repJabatan;
    private javax.swing.JLabel repKaryawan;
    private javax.swing.JLabel repLembur;
    private javax.swing.JLabel repReimburse;
    private javax.swing.JLabel repRekap;
    private javax.swing.JLabel repSlip;
    private com.toedter.calendar.JMonthChooser slipMonth;
    private com.toedter.calendar.JYearChooser slipYear;
    private javax.swing.JTable tab_appCuti;
    private javax.swing.JTable tab_appDinas;
    private javax.swing.JTable tab_appLembur;
    private javax.swing.JTable tab_appReimburse;
    private javax.swing.JTable tab_department;
    private javax.swing.JTable tab_home;
    private javax.swing.JTable tab_home2;
    private javax.swing.JTable tab_jabatan;
    private javax.swing.JTable tab_karyawan;
    private javax.swing.JTable tab_transCuti;
    private javax.swing.JTable tab_transDinas;
    private javax.swing.JTable tab_transLembur;
    private javax.swing.JTable tab_transReimburse;
    private javax.swing.JLabel txtCuti;
    private javax.swing.JLabel txtDept;
    private javax.swing.JLabel txtDinas;
    private javax.swing.JLabel txtJabatan;
    private javax.swing.JLabel txtJatah;
    private javax.swing.JLabel txtLembur;
    private javax.swing.JLabel txtNama;
    private javax.swing.JLabel txtNik;
    private javax.swing.JLabel txtReimburse;
    private javax.swing.JLabel txtSisa;
    private javax.swing.JLabel txtUser;
    private javax.swing.JTextField txt_AppCuti;
    private javax.swing.JTextField txt_AppLembur;
    private javax.swing.JTextField txt_AppReimburse;
    private javax.swing.JTextField txt_appDinas;
    private javax.swing.JLabel txt_date;
    private javax.swing.JTextField txt_department;
    private javax.swing.JTextField txt_department2;
    private javax.swing.JTextField txt_dinas;
    private javax.swing.JTextField txt_karyawan;
    private javax.swing.JTextField txt_lembur;
    private javax.swing.JTextField txt_reimburse;
    private javax.swing.JTextField txt_transCuti;
    private javax.swing.JLabel txt_year;
    // End of variables declaration//GEN-END:variables
}
