package ua.avm.sqlCMD.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

/**
 * Created by AVM on 12.10.2016.
 */

public class DBFireBird extends DataBase{

    public DBFireBird(String[] paramLine) throws Exception {

        String[] dbs = paramLine[2].split(":");
        server = dbs[0];
        if (dbs.length > 1){
            port = dbs[1];
        }else{
            port = "3050";
        }

        if (paramLine.length > NO_DB){
            dbaseName = paramLine[index++];

        }
        else{
            throw new Exception("Not supported.");
        }

        userName = paramLine[index++];
        password = paramLine[index];

        Class.forName("org.firebirdsql.jdbc.FBDriver");
        connection = DriverManager.getConnection("jdbc:firebirdsql:"+server+"/"+port + ":"+dbaseName,
                userName,password);

        DBaseType = FIREBIRD;
        //connect -fb -DBServer:3050 -D:/Andromeda/TestDB/DBase.FDB -SYSDBA -masterkey
        //-fb -DBServer:3050 -SYSDBA -masterkey

    }

    @Override
    public HashMap<String, String> getListDB() {
        //not supported
        return null;
    }
}
