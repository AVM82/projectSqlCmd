package ua.avm.sqlCMD.model;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by AVM on 12.10.2016.
 */
public class PostgreSQL extends DataBase{

    public PostgreSQL(String[] paramLine) throws Exception {
        String[] dbs = paramLine[1].split(":");
        server = dbs[0];
        if (dbs.length > 1){
            port = dbs[1];
        }else{
            port = "5432";
        }
        int index = 2;

        if (paramLine.length > NO_DB){
            dbaseName = paramLine[index++];
        }
        else{
            dbaseName = "";
        }
        userName = paramLine[index++];
        password = paramLine[index];
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://"+server+":"+port+"/"+dbaseName,userName,password);
        //connection = DriverManager.getConnection("jdbc:postgresql://"+server+":"+port+"/",userName,password);
        DBaseType = "PostgreSQL> ";

        //-pg -localhost -test -postgres -function root
    }
}
