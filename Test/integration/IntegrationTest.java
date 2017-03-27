package integration;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.sqlCmdMain;

import java.io.*;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;


public class IntegrationTest {

    private Input in;
    private ByteArrayOutputStream out;
    private String connectLine;
    private String wrongConnectLine;
    private String wrongConnectLine2;

    private String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Before
    public void setup(){
        connectLine = "connect -pg -localhost -test -postgres -function root";
        wrongConnectLine = "conect -pg -localhost -test -postgres -function root";

        out = new ByteArrayOutputStream();
        in = new Input();

        System.setIn(in);
        System.setOut(new PrintStream(out));

    }

    @Test
    public void testConnectDisconnect() throws IOException {

        //given
        in.add(wrongConnectLine);
        in.add(connectLine);
        in.add("disconnect");
        in.add("ext");
        in.add("exit");

        //when
        sqlCmdMain.main(new String[0]);

        //then

        String s = expected.readFile("Test\\integration\\expected\\testConnectDisconnect.tst", StandardCharsets.UTF_8);
        assertEquals(s,getData());
    }
    @Test
    public void testHelpWithConnect() throws IOException {

        //given
        in.add(connectLine);
        in.add("help");
        in.add("exit");
        //when
        sqlCmdMain.main(new String[0]);
        //then
        String s = expected.readFile("Test\\integration\\expected\\testHelpWithConnect.tst", StandardCharsets.UTF_8);
        assertEquals(s,getData());
    }
    @Test
    public void testHelpWithoutConnect() throws IOException {

        //given
        in.add("help");
        in.add("exit");
        //when
        sqlCmdMain.main(new String[0]);
        //then
        String s = expected.readFile("Test\\integration\\expected\\testHelpWithoutConnect.tst", StandardCharsets.UTF_8);
        assertEquals(s,getData());
    }
    @Test
    public void testUnknownCommand() throws IOException {

        //given
        in.add(connectLine);
        in.add("UnknownCommand");
        in.add("exit");
        //when
        sqlCmdMain.main(new String[0]);
        //then
        String s = expected.readFile("Test\\integration\\expected\\testUnknownCommand.tst", StandardCharsets.UTF_8);
        assertEquals(s,getData());
    }

    @Test
    public void testWorkWithTable() throws IOException {
        //given
        in.add(connectLine);
        in.add("create -1new_tab");
        in.add("create -new_tab -unnecessary");
        in.add("create -new_tab");
        in.add("new_field1|integer|y|n");
        in.add("new_field1|integer|y|n");
        in.add("new_field2|integer|n|n");
        in.add("end");
        in.add("create -new_tab");
        in.add("insert -tableName");
        in.add("insert -new_tab");
        in.add("new_field1=1|new_field2=20");
        in.add("insert -new_tab");
        in.add("new_field1=2|new_field2=40");
        in.add("insert -new_tab");
        in.add("new_field1=3|new_field2=60");
        in.add("find -new_tab");
        in.add("find -new_tab -2 -1");
        in.add("find -new_tab -2 -1 -unnecessary");
        in.add("tables");
        in.add("query -select * from new_tab");
        in.add("query -delete from new_tab where new_field1=3");
        in.add("update -tableName");
        in.add("update -new_tab");
        in.add("new_field2=44|new_field2=40");
        in.add("delete -tableName");
        in.add("y");
        in.add("delete -new_tab");
        in.add("y");
        in.add("new_field1=1");
        in.add("clear -new_tab");
        in.add("n");
        in.add("clear -new_tab");
        in.add("y");
        in.add("drop -tableName");
        in.add("y");
        in.add("drop -new_tab");
        in.add("n");
        in.add("drop -new_tab");
        in.add("y");
        in.add("exit");
        //when
        sqlCmdMain.main(new String[0]);
        //then
        String s = expected.readFile("Test\\integration\\expected\\testWorkWithTable.tst", StandardCharsets.UTF_8);
        assertEquals(s,getData());

    }
    @Test
    public void testWorkWithDB() throws IOException {

        //given
        in.add(connectLine);
        in.add("createdb -newDB");
        in.add("ldb");
        in.add("dropdb -newDB");
        in.add("n");
        in.add("dropdb -newDB");
        in.add("y");
        in.add("exit");
        //when
        sqlCmdMain.main(new String[0]);
        //then
        String s = expected.readFile("Test\\integration\\expected\\testWorkWithDB.tst", StandardCharsets.UTF_8);
        assertEquals(s,getData());
    }
}
