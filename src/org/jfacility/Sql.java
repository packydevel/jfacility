package org.jfacility;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**Classe di metodi riusabili del package java.sql
 *
 * @author luca
 */
public class Sql {
    /**Imposta i parametri e i tipi per l'sql
     *
     * @param stmt preparedStatement
     * @param indice
     * @param valore
     * @param tipo
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    public static void setParameters(PreparedStatement stmt, int indice,
                                    Object valore, int tipo) throws SQLException, Exception {
        if (valore == null)
          stmt.setNull(indice, tipo);
        else {
          if (tipo == Types.VARCHAR)
            stmt.setString(indice, (String)valore);
          else if (tipo == Types.INTEGER)
            stmt.setLong(indice, ((Integer) valore).intValue());
          else if (tipo == Types.TIMESTAMP)
            stmt.setTimestamp(indice, ((Timestamp) valore));
          else
            throw new Exception("Tipo di dato non gestito");
        } //end else valore == null
    } //end setParametri
}