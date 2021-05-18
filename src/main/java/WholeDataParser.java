import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class WholeDataParser {

    public void parse() throws FileNotFoundException {
        String source1 = "C:\\Users\\spmn9\\OneDrive\\Рабочий стол\\BoostingData\\DataForBoosting\\TemperatureALL.txt";
        String source2 = "C:\\Users\\spmn9\\OneDrive\\Рабочий стол\\BoostingData\\DataForBoosting\\Weekend.txt";
        String source3 = "C:\\Users\\spmn9\\OneDrive\\Рабочий стол\\BoostingData\\DataForBoosting\\MALL.txt";

        String writeFile = "C:\\Users\\spmn9\\OneDrive\\Рабочий стол\\BoostingData\\DataForBoosting\\NormalData.txt";

        File file1 = new File(source1);
        File file2 = new File(source2);
        File file3 = new File(source3);
        File file4 = new File(writeFile);

        Scanner scanner1 = new Scanner(file1);
        Scanner scanner2 = new Scanner(file2);
        Scanner scanner3 = new Scanner(file3);

        PrintWriter pw = new PrintWriter(file4);
        String currentLine;
        String finalLine="";
        String[] split;
       while(scanner1.hasNextLine()){
            currentLine = scanner1.nextLine();
            finalLine += currentLine;
            currentLine = scanner2.nextLine();
            split = currentLine.split(",");
            finalLine = finalLine + "," + split[1];
            currentLine = scanner3.nextLine();
            split = currentLine.split(",");
            finalLine = finalLine + "," + split[1];
            pw.println(finalLine);
            finalLine = "";
        }
       pw.close();
    }
}
