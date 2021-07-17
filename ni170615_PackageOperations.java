/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.List;
import rs.etf.sab.operations.PackageOperations;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nedeljkovic
 */
public class ni170615_PackageOperations implements PackageOperations {
    
    Connection conn = DB.getInstance().getConnection();
    
    int xPoslednjeLokacije = Integer.MIN_VALUE;
    int yPoslednjeLokacije = Integer.MIN_VALUE;

    @Override
    public int insertPackage(int opstinaOd, int opstinaDo, String korisnickoIme, int tipPaketa, BigDecimal tezina) {
        try (
            PreparedStatement ps = conn.prepareStatement("insert into Paket(IdOpstOd, IdOpstDo, Korisnik, Tip, Tezina) values (?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);){
            ps.setInt(1, opstinaOd);
            ps.setInt(2, opstinaDo);
            ps.setString(3, korisnickoIme);
            ps.setInt(4, tipPaketa);
            ps.setBigDecimal(5, tezina);
            
            ps.executeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys();){
                if(rs.next())
                    return rs.getInt(1);
            }catch(Exception e){
                
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            //Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertTransportOffer(String kurirKorisnickoIme, int idPaket, BigDecimal procenat) {
        if(procenat == null){
            procenat = new BigDecimal(Math.random() * 10);
        }
        try (
            PreparedStatement ps1 = conn.prepareStatement("select * from Kurir where KorisnickoIme = ? AND Status = 0");){
            ps1.setString(1, kurirKorisnickoIme);
            ResultSet rs1 = ps1.executeQuery();
            if(rs1.next()){
                try(PreparedStatement ps2 = conn.prepareStatement("select * from Paket where IdP = ? AND Status = 0");){
                    ps2.setInt(1, idPaket);
                    ResultSet rs2 = ps2.executeQuery();
                    if(rs2.next()){
                        try(PreparedStatement ps3 = conn.prepareStatement("insert into Ponuda(KorisnickoIme, IdP, ProcenatCene) values (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)){
                            ps3.setString(1, kurirKorisnickoIme);
                            ps3.setInt(2, idPaket);
                            ps3.setBigDecimal(3, procenat);
                            ps3.executeUpdate();
                            ResultSet rs3 = ps3.getGeneratedKeys();
                            if(rs3.next()){
                                //System.out.println("ovde");
                                return rs3.getInt(1);
                            }
                        }catch(SQLException e){
                            
                        }
                    }
                }catch(SQLException e){
                    
                }
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    @Override
    public boolean acceptAnOffer(int idPonuda) {
        int idP = 0;
        BigDecimal procenat = null;
        String korisnickoIme = null;
        int xOpstinaOd;
        int yOpstinaOd;
        int xOpstinaDo;
        int yOpstinaDo;
        int idOpstinaOd;
        int idOpstinaDo;
        int tipPaketa;
        BigDecimal tezinaPaketa;
        int osnovnaCena = 0;
        int tezinskiFaktor = 0;
        int cenaPoKg = 0;
        double euklidskaDistanca = 0;
        try (
            PreparedStatement ps = conn.prepareStatement("select * from Ponuda where IdPonuda = ?");){
            ps.setInt(1, idPonuda);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                //System.out.println("evo me ");
                idP = rs.getInt("IdP");
                procenat = rs.getBigDecimal("ProcenatCene");
                korisnickoIme = rs.getString("KorisnickoIme");
                //System.out.println("evo me tu");
                
                try(PreparedStatement ps1 = conn.prepareStatement("delete from Ponuda where IdPonuda = ?")){
                    ps1.setInt(1, idPonuda);
                    if(ps1.executeUpdate() > 0){
                        // System.out.println("evo me2 ");
                        try(PreparedStatement ps2 = conn.prepareStatement("select * from Paket where IdP = ?")){
                            ps2.setInt(1, idP);
                            ResultSet rs2 = ps2.executeQuery();
                            if(rs2.next()){
                                 //System.out.println("evo me 3");
                                idOpstinaOd = rs2.getInt("IdOpstOd");
                                idOpstinaDo = rs2.getInt("IdOpstDo");
                                tipPaketa = rs2.getInt("Tip");
                                tezinaPaketa = rs2.getBigDecimal("Tezina");
                                try(PreparedStatement ps3 = conn.prepareStatement("select * from Opstina where IdO = ?")){
                                    ps3.setInt(1, idOpstinaOd);
                                    ResultSet rs3 = ps3.executeQuery();
                                    if(rs3.next()){
                                         //System.out.println("evo me 4");
                                        xOpstinaOd = rs3.getInt("Xkoord");
                                        yOpstinaOd = rs3.getInt("Ykoord");
                                        try(PreparedStatement ps4 = conn.prepareStatement("select * from Opstina where IdO = ?")){
                                            ps4.setInt(1, idOpstinaDo);
                                            ResultSet rs4 = ps4.executeQuery();
                                            if(rs4.next()){
                                                 //System.out.println("evo me 5");
                                                xOpstinaDo = rs4.getInt("Xkoord");
                                                yOpstinaDo = rs4.getInt("Ykoord");
                                                switch(tipPaketa){
                                                    case 0: {
                                                        osnovnaCena = 10;
                                                        tezinskiFaktor = 0;
                                                        cenaPoKg = 0;
                                                        break;
                                                    }
                                                    case 1: {
                                                        osnovnaCena = 25;
                                                        tezinskiFaktor = 1;
                                                        cenaPoKg = 100;
                                                        break;
                                                    }
                                                    case 2: {
                                                        osnovnaCena = 75;
                                                        tezinskiFaktor = 2;
                                                        cenaPoKg = 300;
                                                        break;
                                                    }
                                                }
                                                euklidskaDistanca = Math.sqrt(Math.pow(xOpstinaOd - xOpstinaDo, 2) + Math.pow(yOpstinaOd - yOpstinaDo, 2));
                                                BigDecimal Euklid = new BigDecimal(euklidskaDistanca);
                                                BigDecimal cena = new BigDecimal(0);
                                                cena = cena.add(new BigDecimal(tezinskiFaktor));
                                                cena = cena.multiply(tezinaPaketa);
                                                cena = cena.multiply(new BigDecimal(cenaPoKg));
                                                cena = cena.add(new BigDecimal(osnovnaCena));
                                                cena = cena.multiply(Euklid);
                                                procenat = procenat.add(new BigDecimal(100));
                                                procenat = procenat.divide(new BigDecimal(100));
                                                cena = cena.multiply(procenat);
                                                
                                                try(PreparedStatement ps5 = conn.prepareStatement("update Paket set Cena = ?, VremePrihvatanja = GETDATE(), Kurir = ?, Status = 1 where IdP = ?")){
                               
                                                    ps5.setBigDecimal(1, cena);
                                                    ps5.setString(2, korisnickoIme);
                                                    ps5.setInt(3, idP);
                                                    if(ps5.executeUpdate() > 0){
                                                         //System.out.println("Euklidska distanca: " + euklidskaDistanca);
                                                        return true;
                                                    }
                                                }catch(SQLException e){
                                                    
                                                }
                                            }
                                        }catch(SQLException e){
                                            
                                        }
                                    }
                                }catch(SQLException e){
                                    
                                }
                            }
                        }catch(SQLException e){
                            
                        }
                    }
                }catch(SQLException e){
                    
                }
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<Integer> getAllOffers() {
        LinkedList<Integer> lista = new LinkedList<Integer>();
        try (
            Statement stmt = conn.createStatement();){
            ResultSet rs = stmt.executeQuery("select * from Ponuda");
            while(rs.next()){
                lista.add(rs.getInt("IdPonuda"));
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_CityOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Pair<Integer, BigDecimal>> getAllOffersForPackage(int idP) {
        List<Pair<Integer,BigDecimal>> lista = new LinkedList<Pair<Integer,BigDecimal>>();
        try (
            PreparedStatement ps = conn.prepareStatement("select * from Ponuda where IdP = ?");){
            ps.setInt(1, idP);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(new ni170615_PackageOperationsPair<Integer, BigDecimal>(rs.getInt("IdPonuda"), rs.getBigDecimal("ProcenatCene")));
            }
            
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public boolean deletePackage(int idPaket) {
        try (
            PreparedStatement ps = conn.prepareStatement("delete from Paket where IdP = ?");){
            ps.setInt(1, idPaket);
            if(ps.executeUpdate() > 0)
                return true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            //Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean changeWeight(int idPaket, BigDecimal tezina) {
        try (
            PreparedStatement ps = conn.prepareStatement("update Paket set Tezina = ? where IdP = ?");){
            ps.setBigDecimal(1, tezina);
            ps.setInt(2, idPaket);
            if(ps.executeUpdate() != 0) return true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean changeType(int paketId, int tip) {
        try (
            PreparedStatement ps = conn.prepareStatement("update Paket set Tip = ? where IdP = ?");){
            ps.setInt(1, tip);
            ps.setInt(2, paketId);
            if(ps.executeUpdate() != 0) return true;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            //Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getDeliveryStatus(int paketId) {
        try (
            PreparedStatement ps = conn.prepareStatement("select * from Paket where IdP = ?");){
            ps.setInt(1, paketId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt("Status");
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public BigDecimal getPriceOfDelivery(int paketId) {
        try (
            PreparedStatement ps = conn.prepareStatement("select * from Paket where IdP = ?");){
            ps.setInt(1, paketId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getBigDecimal("Cena");
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date getAcceptanceTime(int paketId) {
        try (
            PreparedStatement ps = conn.prepareStatement("select * from Paket where IdP = ?");){
            ps.setInt(1, paketId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getDate("VremePrihvatanja");
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Integer> getAllPackagesWithSpecificType(int tip) {
        List<Integer> lista = new LinkedList<Integer>();
        try (
            PreparedStatement ps = conn.prepareStatement("select * from Paket where Tip = ?");){
            ps.setInt(1, tip);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(rs.getInt("IdP"));
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public List<Integer> getAllPackages() {
        List<Integer> lista = new LinkedList<Integer>();
        try (
            PreparedStatement ps = conn.prepareStatement("select * from Paket");){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(rs.getInt("IdP"));
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Integer> getDrive(String kurirUsername) {
        List<Integer> lista = new LinkedList<Integer>();
        try (
            PreparedStatement ps = conn.prepareStatement("select * from Paket where Kurir = ? and Status = 2");){
            ps.setString(1, kurirUsername);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                lista.add(rs.getInt("IdP"));
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    @Override
    public int driveNextPackage(String kurirUsername) {
        int statusKurira = 0;
        int odabraniPaket = 0;
        boolean imaJos = false;
        int idOpstinaOd = -1;
        int idOpstinaDo = -1;
        String registracioniBroj = null;
        BigDecimal potrosnja = null;
        int tipGoriva = -1;
        int xKoordOd = -1;
        int xKoordDo = -1;
        int yKoordOd = -1;
        int yKoordDo = -1;
        double euklidskaDistanca = -1;
        int cenaGoriva = -1;
        BigDecimal profit = null;
        BigDecimal cenaPaketa = null;
        try (
            PreparedStatement ps = conn.prepareStatement("update Kurir set Status = 1 where KorisnickoIme = ?");){
            ps.setString(1, kurirUsername);
            if(ps.executeUpdate() > 0){
                
            }else{
                return -2;
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } catch (SQLException ex) {
            Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        //if(statusKurira == 0){
            try (
                PreparedStatement ps1 = conn.prepareStatement("update Paket set Status = 2 where Status = 1 and Kurir = ?");){
                ps1.setString(1, kurirUsername);
                if(ps1.executeUpdate() > 0){
                    
                }
                else {
                    //return -1;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        //}
        
        try(PreparedStatement ps1 = conn.prepareStatement("select * from Paket where Kurir = ? AND Status = 2");){
            ps1.setString(1, kurirUsername);
            ResultSet rs1 = ps1.executeQuery();
            if(rs1.next()){
                odabraniPaket = rs1.getInt("IdP");
                idOpstinaOd = rs1.getInt("IdOpstOd");
                idOpstinaDo = rs1.getInt("IdOpstDo");
                cenaPaketa = rs1.getBigDecimal("Cena");
                if(rs1.next()) imaJos = true;
                else imaJos = false;
            }
            else{
                try (
                    PreparedStatement ps = conn.prepareStatement("update Kurir set Status = 0 where KorisnickoIme = ?");){
                    ps.setString(1, kurirUsername);
                    ps.executeUpdate();
            
                } catch (SQLException ex) {
                    Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
                }
                xPoslednjeLokacije = Integer.MIN_VALUE;
                yPoslednjeLokacije = Integer.MIN_VALUE;
                return -1;
            }
        }catch(SQLException e){
            
        }
        
        //if(odabraniPaket != 0){
            try (
                PreparedStatement ps3 = conn.prepareStatement("update Paket set Status = 3 where IdP = ?");){
                ps3.setInt(1, odabraniPaket);
                if(ps3.executeUpdate() > 0){
                    
                }else{
                    return -2;
                }
            } catch (SQLException ex) {
                Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
            try(PreparedStatement ps5 = conn.prepareStatement("select * from Kurir where KorisnickoIme = ?")){
                ps5.setString(1, kurirUsername);
                ResultSet rs5 = ps5.executeQuery();
                if(rs5.next()){
                    registracioniBroj = rs5.getString("RegistracioniBroj");
                }
            }catch(SQLException e){
                
            }
            try(PreparedStatement ps6 = conn.prepareStatement("select * from Opstina where IdO = ?")){
                ps6.setInt(1, idOpstinaOd);
                ResultSet rs6 = ps6.executeQuery();
                if(rs6.next()){
                    xKoordOd = rs6.getInt("Xkoord");
                    yKoordOd = rs6.getInt("Ykoord");
                }
            }catch(SQLException e){
                
            }
            try(PreparedStatement ps7 = conn.prepareStatement("select * from Opstina where IdO = ?")){
                ps7.setInt(1, idOpstinaDo);
                ResultSet rs7 = ps7.executeQuery();
                if(rs7.next()){
                    xKoordDo = rs7.getInt("Xkoord");
                    yKoordDo = rs7.getInt("Ykoord");
                }
            }catch(SQLException e){
                
            }
            
            try(PreparedStatement ps8 = conn.prepareStatement("select * from Vozilo where RegistracioniBroj = ?")){
                ps8.setString(1, registracioniBroj);
                ResultSet rs8 = ps8.executeQuery();
                if(rs8.next()){
                    tipGoriva = rs8.getInt("TipGoriva");
                    potrosnja = rs8.getBigDecimal("Potrosnja");
                }
            }catch(SQLException e){
                
            }
            
            switch(tipGoriva){
                case 0:{
                    cenaGoriva = 15;
                    break;
                }
                case 1:{
                    cenaGoriva = 32;
                    break;
                }
                case 2:{
                    cenaGoriva = 36;
                    break;
                }
                   
            }
            
            double distanca2;
            if(xPoslednjeLokacije != Integer.MIN_VALUE && yPoslednjeLokacije != Integer.MIN_VALUE){
                distanca2 = Math.sqrt(Math.pow(xPoslednjeLokacije - xKoordOd, 2) + Math.pow(yPoslednjeLokacije - yKoordOd, 2));
            }
            else distanca2 = 0;
            
            euklidskaDistanca = Math.sqrt(Math.pow(xKoordOd - xKoordDo, 2) + Math.pow(yKoordOd - yKoordDo, 2));
            
            BigDecimal put = new BigDecimal(distanca2);
            BigDecimal euklid = new BigDecimal(euklidskaDistanca);
            euklid = euklid.add(put);
            BigDecimal troskovi = potrosnja.multiply(euklid);
            
            //double troskovi1 = potrosnja.doubleValue() * (euklidskaDistanca + distanca2);
            
            BigDecimal CenaGoriva = new BigDecimal(cenaGoriva);
            troskovi = troskovi.multiply(CenaGoriva);
            //System.out.println("Troskovi " + troskovi.toString());
            //troskovi1 = troskovi1 * cenaGoriva;
            //System.out.println("Cena " + cenaPaketa.toString());
            
            profit = cenaPaketa.subtract(troskovi);
            //System.out.println("Profit 1 " + profit.toString());
            //double profit1 = cenaPaketa.doubleValue() - troskovi1;
            
            
            try (PreparedStatement ps4 = conn.prepareStatement("update Kurir set BrojIsporucenihPaketa = BrojIsporucenihPaketa + 1, OstvareniProfit = OstvareniProfit + ? where KorisnickoIme = ?")) {
                ps4.setBigDecimal(1, profit);
                ps4.setString(2, kurirUsername);
                if (ps4.executeUpdate() > 0) {
                    xPoslednjeLokacije = xKoordDo;
                    yPoslednjeLokacije = yKoordDo;
                    if (!imaJos) {
                        xPoslednjeLokacije = Integer.MIN_VALUE;
                        yPoslednjeLokacije = Integer.MIN_VALUE;
                        try (
                                PreparedStatement ps9 = conn.prepareStatement("update Kurir set Status = 0 where KorisnickoIme = ?");) {
                            ps9.setString(1, kurirUsername);
                            if (ps9.executeUpdate() > 0) {
                                return odabraniPaket;
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(ni170615_PackageOperations.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    return odabraniPaket;
                }else{
                    return -2;
                }
            }catch(SQLException e){
                
            }
        //}
        
        
        
        
        return -2;
    }
    
}
