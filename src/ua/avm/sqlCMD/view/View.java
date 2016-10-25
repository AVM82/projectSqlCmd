package ua.avm.sqlCMD.view;

/**
 * Created by AVM on 12.10.2016.
 */
public interface View {

    void writeln(String message);
    void write(String message);
    void setPrefix(String prefix);

    String read();
}
