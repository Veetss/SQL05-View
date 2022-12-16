import java.sql.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Connection con = null;

        try {
            String db_url = "jdbc:mysql://localhost:3306/newdb";
            String user = "developer";
            String password = "Password!";

            con = DriverManager.getConnection(db_url, user, password);

            Statement s = con.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS student " +
                    "(student_id INTEGER(10) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    " last_name VARCHAR(30), " +
                    " first_name VARCHAR(30))";

            ArrayList<String> surnames = new ArrayList<>();

            s.executeUpdate(sql);

            System.out.println("Table created.");

            s.executeUpdate("insert into student (last_name,first_name) value (\"Boukoti\", \"Ismail\");"); // meglio usare execute update a meno che non sia una SELECT
            s.executeUpdate("insert into student (last_name,first_name) value (\"Vassarotti\", \"Vittoria\");");
            s.executeUpdate("insert into student (last_name,first_name) value (\"De Fenzo\", \"Marco\");");
            s.executeUpdate("insert into student (last_name,first_name) value (\"Mustata\", \"Elena Alina\");");

            System.out.println("Students added to table.");

            ResultSet rs = s.executeQuery("SELECT first_name, last_name FROM newdb.student"); //distinct rileva solo i casi "unici"

            while(rs.next())
            {
                surnames.add(rs.getString("last_name"));
                System.out.println(rs.getString("first_name"));
            }

            System.out.println(surnames);

            String sql1 = "ALTER TABLE student ADD country VARCHAR(30);";

            s.executeUpdate(sql1);

            s.executeUpdate("UPDATE student SET country = \"Italy\" where student_id = 1;");
            s.executeUpdate("UPDATE student SET country = \"Italy\" where student_id = 2;");
            s.executeUpdate("UPDATE student SET country = \"Germany\" where student_id = 3;");
            s.executeUpdate("UPDATE student SET country = \"Germany\" where student_id = 4;");

            String str = "CREATE VIEW italian_students AS\n" +
                    "SELECT last_name, first_name\n" +
                    "FROM student\n" +
                    "WHERE Country = \"Italy\";";

            String str1 = "CREATE VIEW german_students AS\n" +
                    "SELECT last_name, first_name\n" +
                    "FROM student\n" +
                    "WHERE Country = \"Germany\";";

            s.executeUpdate(str);
            s.executeUpdate(str1);

            ArrayList<Student> italianStudent = new ArrayList<>();

            ResultSet rs1 = s.executeQuery("SELECT first_name, last_name FROM italian_students");

            while (rs1.next()){
                italianStudent.add(new Student(rs1.getString("first_name"), rs1.getString("last_name")));
            }

            for (Student name : italianStudent) {
                name.printStudentDetails();
            }

            ResultSet rs2 = s.executeQuery("SELECT first_name, last_name FROM german_students");

            ArrayList<Student> germanStudent = new ArrayList<>();

            while (rs2.next()){
                germanStudent.add(new Student(rs2.getString("first_name"), rs2.getString("last_name")));
            }

            for (Student name : germanStudent) {
                name.printStudentDetails();
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try{
                if(con != null)
                    con.close();
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}
