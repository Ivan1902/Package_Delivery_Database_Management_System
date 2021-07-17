/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.etf.sab.operations.CourierOperations;

/**
 *
 * @author Nedeljkovic
 */
public class ni170615_CourierOperations implements CourierOperations {
    
    Connection conn = DB.getInstance().getConnection();

    @Override
    public boolean insertCourier(String korisnickoIme, String registracioniBroj) {
        try (
            PreparedStatement ps = conn.prepareStatement("select * from Korisnik where KorisnickoIme = ?", PreparedStatement.RETURN_GENERATED_KEYS);){
            ps.setString(1, korisnickoIme);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                PreparedStatement ps2 = conn.prepareStatement("select * from Vozilo where RegistracioniBroj = ?", PreparedStatement.RETURN_GENERATED_KEYS);
                ps2.setString(1, registracioniBroj);
                ResultSet rs1 = ps2.executeQuery();
                if(rs1.next()){
                    try(PreparedStatement ps1 = conn.prepareStatement("insert into Kurir (KorisnickoIme, RegistracioniBroj, Status) values (?, ?, 0)");){
                    ps1.setString(1, korisnickoIme);
                    ps1.setString(2, registracioniBroj);
                    if(ps1.executeUpdate() > 0)
                        return true;
                    }catch(SQLException e){
                    
                    }
                }
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_CourierOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteCourier(String korisnickoIme) {
        try (
            PreparedStatement ps = conn.prepareStatement("delete from Kurir where KorisnickoIme = ?");){
            ps.setString(1, korisnickoIme);
            if(ps.executeUpdate() != 0)
                return true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_DistrictOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getCouriersWithStatus(int status) {
        LinkedList<String> lista = new LinkedList<String>();
        try (
            PreparedStatement ps = conn.prepareStatement("select * from Kurir where Status = ?");){
            ps.setInt(1, status);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(rs.getString("KorisnickoIme"));
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAllCouriers() {
        LinkedList<String> lista = new LinkedList<String>();
        try (
            PreparedStatement ps = conn.prepareStatement("select * from Kurir order by OstvareniProfit DESC");){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(rs.getString("KorisnickoIme"));
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BigDecimal getAverageCourierProfit(int brojIsporuka) {
        BigDecimal suma = BigDecimal.ZERO;
        BigDecimal broj = BigDecimal.ZERO;
        try (
            PreparedStatement ps = conn.prepareStatement("select * from Kurir");){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                
                if(rs.getInt("BrojIsporucenihPaketa") >= brojIsporuka){
                    suma = suma.add(rs.getBigDecimal("OstvareniProfit"), MathContext.DECIMAL32);
                    broj = broj.add(new BigDecimal(1));
                }
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return broj.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : suma.divide(broj,3, RoundingMode.HALF_UP);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
