
package schutzschild;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteFile {

	static String date;

	static boolean write = true;

	public WriteFile() {

	}

	public static void write() {
		date = CurrentDateAndTime.time();

		if (write) {

			try {
				File file = new File("data.txt");

				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				pw.close();
				System.out.println("Successfully wrote to the file.");

			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			} finally {
				write = false;
			}

		}

	}

	/**
	 * @return the write
	 */
	public static boolean isWrite() {
		return write;
	}

	/**
	 * @param write the write to set
	 */
	public static void setWrite(boolean write) {
		WriteFile.write = write;
	}

}