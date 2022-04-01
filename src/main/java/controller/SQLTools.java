package controller;
import java.sql.*;
import java.util.Scanner;

public final class SQLTools {

    private static int i=0;
    private static SQLTools instance=null;
    public static String url = "jdbc:mysql://fournierfamily.ovh:3306/Nico_database", user_id = "jps", pwd = "poojava";
    public Connection conn;
    public Statement st;
    public ResultSet rs;
    PreparedStatement stmt;
    public SQLTools(){

    }

    /**
     * Inspired to Singleton
     * @return
     */
    public static SQLTools getInstance(){
        if(instance==null){
            instance=new SQLTools();
            i++;
            System.out.println(i);
        }

        return instance;
    }


    /**Executes a prepared statement (insert)
     *
     * @param query requete sql
     * @return  le result
     */
    public PreparedStatement executeQueryWithPS(String query){
    try{
        conn = DriverManager.getConnection(url, user_id, pwd);
        stmt=conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }
    catch (SQLException e){
        System.out.println(e);
        }
        System.out.println("Done");
    return stmt;
    }

    /**
     * Executes a statement (select)
     * @param query requete sql
     * @return result
     */
    public ResultSet executeQueryWithRs(String query) {
        try {
            conn = DriverManager.getConnection(url, user_id, pwd);
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * Récupère le nb de lignes dans une table SQL
     *
     * @param table table sql
     * @return retourne le nb de ligne
     */
    public int GetNbRow(String table) {
        int cpt_col = 0;
        try {  // Code permettant de compter le nb de rows dans une table sql

            String query = "Select count(*) from  " + table + ";";
            rs = executeQueryWithRs(query);


            while (rs.next()) {
                cpt_col = rs.getInt(1);   // fonction a retenir
            }
        } catch (SQLException e) {
            throw new Error("Problem", e);
        }
        return cpt_col;
    }

    /**
     * Récupère le nb de colonnes dans une table SQL
     *
     * @param table table sql
     * @return retourne le nb de colonne
     */
    public int GetNbCol(String table) {
        int cpt_col = 0;
        try {// Code permettant de compter le nb de colonnes d'attributs dans une table sql

            String query = "Select * from " + table + ";";
            rs = executeQueryWithRs(query);

            ResultSetMetaData rsmd = rs.getMetaData();
            cpt_col = rsmd.getColumnCount();
        } catch (SQLException e) {
            throw new Error("Problem", e);
        }
        return cpt_col;
    }

    /**Saisie string
     *
     * @return un string
     */
    public String inputString() {
        Scanner sc = new Scanner(System.in);
        String input = sc.next();
        return input;
    }


    /**
     * Saisie sécurisée 1 ou 2 INT
     *
     * @return
     */
    Integer input1Or2() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        boolean test = false;
        do {
            choice = sc.nextInt();
            if (choice == 1 || choice == 2)
                test = true;
        } while (!test);
        return choice;
    }
    Integer input1OrX(int x) {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        boolean test = false;
        do {
            choice = sc.nextInt();
            if (choice >=1 && choice<=x)
                test = true;
        } while (!test);
        return choice;
    }

    /**
     * Permet de traduire un string en tps pour l'insert dans le sql
     * @param retrievedTime
     * @return
     */
    public Time translateTime(int retrievedTime) {
        Time t;
        int cpt_hour = 0;
        while (retrievedTime >= 60) {
            cpt_hour++;
            retrievedTime -= 60;
        }
        t = new Time(cpt_hour, retrievedTime, 0);

        return t;
    }
}
