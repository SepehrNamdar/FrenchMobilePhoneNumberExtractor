import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {

    private static final String START = "+33";

    public static void main(String[] args) {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        String sourceFile = "";
        int returnValue = jfc.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            sourceFile = selectedFile.getAbsolutePath();
        }

        try{
            InputStream flux=new FileInputStream(sourceFile);
            InputStreamReader lecture=new InputStreamReader(flux);
            BufferedReader buff=new BufferedReader(lecture);
            String ligne;
            Set<String> list = new TreeSet<>();
            while ((ligne=buff.readLine())!=null){
                if(ligne.contains(START)) {
                    int beginIndex = ligne.indexOf(START);
                    try {
                        list.add(ligne.substring(beginIndex, beginIndex + 18));
                    } catch(StringIndexOutOfBoundsException e) {
                        list.add(ligne.substring(beginIndex, beginIndex + 12));
                    }
                }
            }
            buff.close();

            List<String> newList = new LinkedList<>();
            for (String item : list) {
                String newItem = "00";
                for (Character c : item.toCharArray()) {
                    if(Character.isDigit(c)) {
                        newItem = newItem.concat(c.toString());
                    }
                }
                newList.add(newItem);
            }

            try (OutputStreamWriter writer =
                         new OutputStreamWriter(new FileOutputStream("result.txt"), StandardCharsets.UTF_8)) {
                writer.write(Arrays.toString(newList.toArray()));
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
