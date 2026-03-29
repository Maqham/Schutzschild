package schutzschild;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class UpdateFile {
	public static void update(ArrayList<TempRank> results) {
		try {
			File file = new File("data.txt");

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			pw.flush();
			Object[] updateResults = results.toArray();
			for (int i = 0; i < 10; i++) {

				pw.println(((TempRank) updateResults[i]).getDate() + " " + ((TempRank) updateResults[i]).getTime() + " "
						+ ((TempRank) updateResults[i]).getPlayTime() + " " + ((TempRank) updateResults[i]).getLevel());

			}
			System.out.println("Successfully Update file.");
			pw.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
