import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class verification {

	public static int getHash(String cc_hash) {
		String fileName = "hashes.csv";
		File file = new File(fileName);
		int cont = 0;

		// this gives you a 2-dimensional array of strings
		List<List<String>> lines = new ArrayList<>();
		Scanner inputStream;

		try {
			inputStream = new Scanner(file);

			while (inputStream.hasNext()) {
				String line = inputStream.next();
				String[] values = line.split(",");
				// this adds the currently parsed line to the 2-dimensional string array
				lines.add(Arrays.asList(values));
			}

			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// the following code lets you iterate through the 2-dimensional array

		for (List<String> line : lines) {

			for (String value : line) {
				if (value.equals(cc_hash)) {
					return 1;

				} else {

				}
			}
		}
		return 0;
	}
}
