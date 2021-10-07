
package susatisotomasyonu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import props.Musteri;


public class DB {
     private final String driver = "org.sqlite.JDBC";
    private final String url = "jdbc:sqlite:C:/Users/Casper/Documents/NetBeansProjects/SuSatisOtomasyonu/db/susatisotomasyonu.db";
    
    private Connection conn = null;
    private PreparedStatement pre = null;
    
    public static Musteri musteri=new Musteri();
    
    public DB(){
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url);
            System.out.println("Connection Success");
        } catch (Exception e) {
            System.err.println("Connection Error : " + e);
        }
    }
    
    public DefaultTableModel searchUser(String name, String surname){
        DefaultTableModel dtm=new DefaultTableModel();
        
        dtm.addColumn("id");
        dtm.addColumn("Adı");
        dtm.addColumn("Soyadı");
        dtm.addColumn("Telefon");
        dtm.addColumn("Adres");
        
        try {
            String sql="select * from musteriler where ad=? and soyad=?";
            pre = conn.prepareStatement(sql);
            pre.setString(1,name);
            pre.setString(2, surname);
            ResultSet rs=pre.executeQuery();
            while(rs.next()){
                int id=rs.getInt("id");
                String ad=rs.getString("ad");
                String soyad=rs.getString("soyad");
                String telefon=rs.getString("telefon");
                String adres=rs.getString("adres");
                
                Object [] row={id,ad,soyad,telefon,adres};
                dtm.addRow(row);
            }
            
        } catch (Exception e) {
            System.err.println("userSearch Error : " + e);
        }
        return dtm;
    }
    public int musteriInsert( String name, String surname, String tel, String adres ) {
        int status = 0;
        
        try {
            String sql = " insert into musteriler values ( null, ?, ?,?,? ) ";
            pre = conn.prepareStatement(sql);
            pre.setString(1, name);
            pre.setString(2, surname);
            pre.setString(3, tel);
            pre.setString(4, adres);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("musteriInsert Error : " + e);
        }
        return status;
    }
    int id=0;
    public DefaultTableModel allMusteri() {
        DefaultTableModel dtm = new DefaultTableModel();
        
        // add Cloumn
        dtm.addColumn("id");
        dtm.addColumn("Adı");
        dtm.addColumn("Soyadı");
        dtm.addColumn("Telefon");
        dtm.addColumn("Adres");
        
        // add Rows
        try {
            String sql = "select * from musteriler";
            pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while( rs.next() ) {
                id = rs.getInt("id");
               String name = rs.getString("ad");
               String surname = rs.getString("soyad");
               String tel = rs.getString("telefon");
               String adres = rs.getString("adres");
               
               Object[] row = { id, name, surname,tel,adres };
               dtm.addRow(row);
            }
        } catch (Exception e) {
            System.err.println("allMusteri Error : " + e);
        }
        
        
        return dtm;
    }
    
    public int musteriDelete( int id ) {
        
        int status = 0;
        
        try {
            String sql = "Delete From musteriler Where id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, id);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("musteriDelete Error : " + e);
        }
        
        return status;
        
    }
    
    public int musteriUpdate( String ad, String soyad,String telefon, String adres, int id ) {
        
        int status = 0;
        
        try {
            String sql = " update musteriler set ad = ?, soyad = ?, telefon = ?, adres = ? where id = ? ";
            pre = conn.prepareStatement(sql);
            pre.setString(1,ad);
            pre.setString(2, soyad);
            pre.setString(3, telefon);
            pre.setString(4, adres);
            pre.setInt(5, id);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("musteriUpdate Error : " + e);
            if ( e.toString().contains("SQLITE_CONSTRAINT_UNIQUE") ) {
                status = -1;
            } 
        }
        
        return status;
        
    }
    
     int sid=0;
    public DefaultTableModel allSiparis() {
        DefaultTableModel dtm = new DefaultTableModel();
        
        // add Cloumn
        dtm.addColumn("sid");
        dtm.addColumn("Müşteri Adı");
        dtm.addColumn("Müşteri Soyadı");
        dtm.addColumn("Durum");
        dtm.addColumn("Adres");
        dtm.addColumn("Tutar");
        
        // add Rows
        try {
            String sql = "select * from siparisler";
            pre = conn.prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while( rs.next() ) {
                sid = rs.getInt("sid");
               String mad = rs.getString("mad");
               String msoyad = rs.getString("msoyad");
               String durum = rs.getString("durum");
               String adres = rs.getString("adres");
               int tutar = rs.getInt("tutar");
               
               Object[] row = { sid, mad, msoyad,durum,adres,tutar };
               dtm.addRow(row);
            }
        } catch (Exception e) {
            System.err.println("allSiparis Error : " + e);
        }
        
        
        return dtm;
    }
   
     public int siparisDelete( int sid ) {
        
        int status = 0;
        
        try {
            String sql = "Delete From siparisler Where sid = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, sid);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("siparisDelete Error : " + e);
        }
        
        return status;
        
    }
     public int siparisAllDelete(){
         int status=0;
         try {
             String sql="Delete from siparisler";
             pre=conn.prepareStatement(sql);
             status=pre.executeUpdate();
         } catch (Exception e) {
             System.err.println("siparisAllDelete"+e);
         }
         return status;
     }
      public int siparisYoldaUpdate( int sid ) {
        
        int status = 0;
        
        try {
            String sql = " update siparisler set durum = 'Yolda' where sid = ? ";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, sid);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("siparisYoldaUpdate Error : " + e);
           
        }
        
        return status;
        
    }
    
      public int siparisTamamlandiUpdate( int sid ) {
        
        int status = 0;
        
        try {
            String sql = " update siparisler set durum = 'Teslim Edildi' where sid = ? ";
            pre = conn.prepareStatement(sql);
            pre.setInt(1,sid);
            status = pre.executeUpdate();
        } catch (Exception e) {
            System.err.println("siparisTamamlandıUpdate Error : " + e);
           
        }
        
        return status;
        
    }
    
      public int siparisEkle(String madi, String msoyad,  String adres, int tutar){
          int status=0;
          try {
              String sql = " insert into siparisler values ( null, ?, ?, 'Hazırlanıyor',?,? ) ";
            pre = conn.prepareStatement(sql);
            pre.setString(1, madi);
            pre.setString(2, msoyad);
            pre.setString(3, adres);
            pre.setInt(4, tutar);
            status=pre.executeUpdate();
          } catch (Exception e) {
              System.err.println("siparisEkle Error"+e);
          }
          return status;
      }
      public DefaultTableModel bugunSiparis(){
          DefaultTableModel dtm=new DefaultTableModel();
         
        dtm.addColumn("sid");
        dtm.addColumn("Müşteri Adı");
        dtm.addColumn("Müşteri Soyadı");
        dtm.addColumn("Durum");
        dtm.addColumn("Adres");
        dtm.addColumn("Tutar");
          try {
              String sql="select * from siparisler where durum = 'Hazırlanıyor' ";
              pre=conn.prepareStatement(sql);
              ResultSet rs=pre.executeQuery();
              while( rs.next() ) {
                sid = rs.getInt("sid");
               String mad = rs.getString("mad");
               String msoyad = rs.getString("msoyad");
               String durum = rs.getString("durum");
               String adres = rs.getString("adres");
               int tutar = rs.getInt("tutar");
               
               Object[] row = { sid, mad, msoyad,durum,adres,tutar };
               dtm.addRow(row);
            }
          } catch (Exception e) {
              System.err.println("bugunSiparis Error"+e);
          }
          return dtm;
      }
     public void close() {
        
        try {
           
            if ( conn != null && !conn.isClosed() ) {
                conn.close();
            }
            
            if ( pre != null ) {
                pre.close();
            }
            
        } catch (Exception e) {
            System.err.println("Close Error : " + e);
        }
        
    }
}
