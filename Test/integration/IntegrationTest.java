package integration;

import org.junit.Before;
import org.junit.Test;
import ua.avm.sqlCMD.sqlCmdMain;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


import static org.junit.Assert.assertEquals;


public class IntegrationTest {

    private Input in;
    private ByteArrayOutputStream out;
    private String connectLine;

    public String getData() {
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

        out = new ByteArrayOutputStream();
        in = new Input();

        System.setIn(in);
        System.setOut(new PrintStream(out));

    }

    @Test
    public void testConnectDisconnect() throws IOException {

        //given
        in.add(connectLine);
        in.add("exit");

        //when
        sqlCmdMain.main(new String[0]);

        //then

        String s = expected.readFile("Test\\expected\\testConnectDisconnect.tst", StandardCharsets.UTF_8);
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
        String s = expected.readFile("Test\\expected\\testHelpWithConnect.tst", StandardCharsets.UTF_8);
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
        String s = expected.readFile("Test\\expected\\testHelpWithoutConnect.tst", StandardCharsets.UTF_8);
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
        String s = expected.readFile("Test\\expected\\testUnknownCommand.tst", StandardCharsets.UTF_8);
        assertEquals(s,getData());
    }
}
