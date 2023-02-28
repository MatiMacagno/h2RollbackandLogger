import org.apache.log4j.Logger;
import java.sql.*;

public class Test {

    private final static Logger logger = Logger.getLogger(Test.class);

    private static final String SQL_CREATE_TABLE = "DROP TABLE IF EXISTS PACIENTE; CREATE TABLE PACIENTE" +
            "("
            + " ID INT PRIMARY KEY, "
            + " NOMBRE VARCHAR(100) NOT NULL, "
            + " APELLIDO VARCHAR(100) NOT NULL, "
            + " DOMICILIO VARCHAR(255) NOT NULL, "
            + " DNI INT NOT NULL, "
            + " FECHADEALTA VARCHAR(50) NOT NULL, "
            + " USUARIO VARCHAR(200) NOT NULL, "
            + " PASSWORD VARCHAR(100) NOT NULL"
            + ")";

    private static final String SQL_INSERT = "INSERT INTO PACIENTE (ID, NOMBRE, APELLIDO, DOMICILIO, DNI, FECHADEALTA, USUARIO, PASSWORD) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
    private static final String SQL_UPDATE = "UPDATE PACIENTE SET PASSWORD=? WHERE USUARIO=?";

    private static Connection getConnection() throws Exception {
        return DriverManager.getConnection("jdbc:h2:~/test5", "sa", "");
    }

    public static void main(String[] args) throws Exception {

        Paciente paciente1 = new Paciente("Matias", "Macagno", "boulevard 123", 41792, "03/10/18", "matimacagno10", "matias123");

        Connection connection = null;

        try {
            connection = getConnection();

            Statement stmt = connection.createStatement();
            stmt.execute(SQL_CREATE_TABLE);

            PreparedStatement psInsert = connection.prepareStatement(SQL_INSERT);

            psInsert.setInt(1, 1);
            psInsert.setString(2, paciente1.getNombre());
            psInsert.setString(3, paciente1.getApellido());
            psInsert.setString(4, paciente1.getDomicilio());
            psInsert.setInt(5, paciente1.getDNI());
            psInsert.setString(6, paciente1.getFechaAlta());
            psInsert.setString(7, paciente1.getUsuario());
            psInsert.setString(8, paciente1.getPassword());

            psInsert.execute();

            connection.setAutoCommit(false);

            PreparedStatement psUpdate = connection.prepareStatement(SQL_UPDATE);
            psUpdate.setString(1, "CAU123");
            psUpdate.setString(2, paciente1.getUsuario());
            psUpdate.execute();

            int a = 10 / 0;

            connection.commit();

            connection.setAutoCommit(true);

            String sql = "SELECT * FROM PACIENTE";
            Statement stmt2 = connection.createStatement();
            ResultSet rs = stmt2.executeQuery(sql);
            while (rs.next()){
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " +rs.getInt(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8));
            }


        }catch (Exception e){
            e.printStackTrace();
            connection.rollback();
            logger.info(e);

        } finally {
            connection.close();
        }

        Connection connection1 = getConnection();

        String sql = "SELECT * FROM PACIENTE";
        Statement stmt2 = connection1.createStatement();
        ResultSet rs = stmt2.executeQuery(sql);
        while (rs.next()){
            System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " +rs.getInt(5) + " " + rs.getString(6) + " " + rs.getString(7) + " " + rs.getString(8));
        }

    }
}
