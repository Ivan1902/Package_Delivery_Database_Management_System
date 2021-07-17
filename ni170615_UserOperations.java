/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.util.List;
import rs.etf.sab.operations.UserOperations;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nedeljkovic
 */
public class ni170615_UserOperations implements UserOperations {
    
    Connection conn = DB.getInstance().getConnection();

    @Override
    public boolean insertUser(String korisnickoIme, String ime, String prezime, String sifra) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        if(Character.isUpperCase(ime.charAt(0)) && Character.isUpperCase(prezime.charAt(0)) && sifra.matches(regex)){
            try (
            PreparedStatement ps = conn.prepareStatement("insert into Korisnik(KorisnickoIme, Ime, Prezime, Sifra) values (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);){
            ps.setString(1, korisnickoIme);
            ps.setString(2, ime);
            ps.setString(3, prezime);
            ps.setString(4, sifra);
            
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys();){
                if(rs.next())
                    return true;
            }catch(Exception e){
                
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            //Logger.getLogger(ni170615_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int declareAdmin(String korisnickoIme) {
        try (
            PreparedStatement ps = conn.prepareStatement("select * from Korisnik where KorisnickoIme = ?", PreparedStatement.RETURN_GENERATED_KEYS);){
            ps.setString(1, korisnickoIme);
            try(ResultSet rs = ps.executeQuery();){
                if(rs.next()){
                    //int idK = rs.getInt(1);
                    PreparedStatement ps2 = conn.prepareStatement("select * from Administrator where KorisnickoIme = ?", PreparedStatement.RETURN_GENERATED_KEYS);
                    ps2.setString(1, korisnickoIme);
                    ResultSet rs1 = ps2.executeQuery();
                    if(rs1.next()){
                        return 1;
                    }
                    else {
                        PreparedStatement ps1 = conn.prepareStatement("insert into Administrator(KorisnickoIme) values (?)");
                        ps1.setString(1, korisnickoIme);
                        ps1.executeUpdate();
                        return 0;
                    }
                    
                }
                else {
                    return 2;
                }
            }catch(Exception e){
                
            }
            
            
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 2;
    }

    @Override
    public Integer getSentPackages(String... korisnickaImena) {
        int br = 0;
        boolean nasaoKorisnika = false;
        for(String korisnickoIme: korisnickaImena){
            try (
                PreparedStatement ps = conn.prepareStatement("select * from korisnik where KorisnickoIme = ?");){
                ps.setString(1, korisnickoIme);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    br += rs.getInt("BrojPoslatihPaketa");
                    nasaoKorisnika = true;
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(ni170615_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return nasaoKorisnika? br : null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deleteUsers(String... korisnici) {
        int br = 0;
        for(String korisnik: korisnici){
            try (
                PreparedStatement ps = conn.prepareStatement("delete from Korisnik where KorisnickoIme = ?");){
                ps.setString(1, korisnik);
                br += ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ni170615_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return br;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> getAllUsers() {
        
        ArrayList<String> lista = new ArrayList<String>();
        try (
            Statement stmt = conn.createStatement();){
            ResultSet rs = stmt.executeQuery("select * from Korisnik");
            while(rs.next()){
                lista.add(rs.getString("KorisnickoIme"));
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_UserOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    
}
