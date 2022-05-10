package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries(Map<Integer, Country> countryIdMap) {
		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				int code = rs.getInt("ccode");
				Country c = new Country(rs.getString("StateAbb"),code,rs.getString("StateNme"));
				Country old = countryIdMap.get(code);
				Country cc;
				if(old == null) {
					countryIdMap.put(code, c);
					cc = c;
				}
				else {
					cc = old;
				}
				result.add(cc);
			}
			conn.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno, Map<Integer, Country> countryIdMap) {
		String sql = "SELECT state1no, state2no "
				+ "FROM contiguity "
				+ "WHERE YEAR<=? AND conttype=1 ";
		List<Border> result = new ArrayList<Border>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				int n1 = rs.getInt("state1no");
				int n2 = rs.getInt("state2no");
				Country c1 = countryIdMap.get(n1);
				Country c2 = countryIdMap.get(n2);
				
				if( c1 == null || c2 == null) {
					throw new RuntimeException("Problema");
				}
				Border b = new Border(c1, c2);
				result.add(b);
			}
			conn.close();
			rs.close();
			st.close();
			return result;
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
