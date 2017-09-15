package mockTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ua.avm.sqlCMD.controller.Commands;
import ua.avm.sqlCMD.controller.command.Command;
import ua.avm.sqlCMD.controller.command.ListDB;
import ua.avm.sqlCMD.model.DataBase;
import ua.avm.sqlCMD.view.View;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ListDBTest {

    private static final int COLUMN_SIZE = 30;
    private DataBase db;
    private View view;
    private Command command;
    private String COMMAND_SAMPLE;

    @Before
    public void setup(){

        COMMAND_SAMPLE = Commands.getCMD().get("Command lists the databases.");
        view = Mockito.mock(View.class);
        db = Mockito.mock(DataBase.class);
        command = new ListDB(db, view, COMMAND_SAMPLE);
        Mockito.when(view.getCommandDelimiter()).thenReturn("\u0020"+"-");


    }

    @Test
    public void testClearCanDoItQwe(){
        //given
        //when
        boolean canDoIt = command.canDoIt("Qwe");
        //then
        assertFalse(canDoIt);
    }
    @Test
    public void testClearCanDoIt(){
        //given
        //when
        boolean canDoIt = command.canDoIt("ldb");
        //then
        assertTrue(canDoIt);
    }
    @Test
    public void testListDBDoIt(){
        //given
        HashMap<String, String> myMap = new HashMap<>();
        ArgumentCaptor<HashMap> argumentHashMapCaptor = ArgumentCaptor.forClass(HashMap.class);
        ArgumentCaptor<String[]> argumentStringArrayCaptor = ArgumentCaptor.forClass(String[].class);
        ArgumentCaptor<Integer> argumentIntegerCaptor = ArgumentCaptor.forClass(int.class);
        myMap.put("dataBaseName1", "owner1");
        myMap.put("dataBaseName2", "owner2");
        myMap.put("dataBaseName3", "owner3");

        Mockito.when(db.getListDB()).thenReturn(myMap);

        //when
        command.doIt(new String[]{"ldb"});
        //then

        Mockito.verify(view).printTableData(argumentHashMapCaptor.capture(),
                argumentStringArrayCaptor.capture(),
                argumentIntegerCaptor.capture());
        String actual = Arrays.toString(argumentStringArrayCaptor.getValue()) +
                        argumentHashMapCaptor.getAllValues().toString();
        assertEquals("[DATABASE NAME, OWNER][{dataBaseName1=owner1, dataBaseName2=owner2, dataBaseName3=owner3}]", actual);
    }




}
