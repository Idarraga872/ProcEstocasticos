package markovchains;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/*
 * File reader class to read the data from the txt file
 */
public class FileReaderMC {

  public String[] readFile() throws IOException {
    String file = "data" + File.separator + "file6.txt";

    BufferedReader br = new BufferedReader(new FileReader(new File(file)));

    String line = br.readLine();

    String[] cases = line.split(" ");
    int totalData = Integer.parseInt(cases[2]);
    String[] data = new String[totalData];
    int count = 0;
    line = br.readLine();
    while (count < totalData) {
      data[count] = line;

      line = br.readLine();
      count++;
    }
    br.close();

    return data;
  }
}
