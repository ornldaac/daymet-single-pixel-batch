import java.net.URL;
import java.io.File;
import java.util.Arrays;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.util.stream.IntStream;
import java.net.MalformedURLException;

public class Daymet {
	static int startYear = 1980;
	static int endYear = 2020;
	static int[] range = IntStream.rangeClosed(startYear, endYear).toArray();
	static String[] allowedVariables = {"dayl", "prcp", "srad", "swe", "tmax", "tmin", "vp"};

	public static void main(String[] args) throws IOException {
		if (args.length < 1) {
			System.out.println("Error; must suppy a file name.");
			System.out.println("Usage: java -jar daymet.jar Filename");
			System.exit(0);
		}
		parseAndDownload(args[0]);
	}

	public static void getData(String vars, String years, ArrayList<String> names, ArrayList<Float> lats, ArrayList<Float> lons) throws IOException {
		Integer numCompleted = 0;
		// loop through each coordinate
		for (int i = 0; i < lats.size(); i++) {

			StringBuilder str = new StringBuilder();
			URL url;

			try {
				// get connection and input stream
				str.append("https://daymet.ornl.gov/single-pixel/api/data?lat=");
				str.append(lats.get(i));
				str.append("&lon=");
				str.append(lons.get(i));
				if (vars != null) {
					str.append("&measuredParams=" + vars);
				}
				if (years != null) {
					str.append("&year=" + years);
				}
				System.out.println("Processing: " + str.toString());
				url = new URL(str.toString());
				InputStream inputStream;
				URLConnection conn = url.openConnection();
				inputStream = conn.getInputStream();

				// get filename from content disposition
				String outF;
				if (names.get(i) == "NULL") {
					String content_disposition = conn.getHeaderField("Content-Disposition");
					String[] split_content_disposition = content_disposition.split("=");
					outF = split_content_disposition[split_content_disposition.length - 1];
				} else {
					outF = names.get(i);
				}


				// read contents of inputstream and write to file
				FileWriter file = new FileWriter(outF);
				BufferedWriter bf = new BufferedWriter(file);
				BufferedReader reader = new BufferedReader( new InputStreamReader( inputStream )  );
				String line = null;
				StringBuilder fileContents = new StringBuilder();

				while( ( line = reader.readLine() ) != null )  {
					fileContents.append(line + "\n");
				}
				bf.write(fileContents.toString());
				reader.close();
				bf.close();
				file.close();
				numCompleted++;

			} catch (MalformedURLException e) {

				e.printStackTrace();
				System.out.println("Failed to process a pixel.");
			}
		}
		System.out.println("Finished downloading " + numCompleted.toString() + " files.");
	}

	public static String getVars(String varString) {
		StringBuilder sb = new StringBuilder();
		int startIndex = varString.indexOf(":") + 1;
		try {
			String[] splitLine = varString.substring(startIndex, varString.length()).split(",");
			for (int i = 0; i < splitLine.length; i ++) {
				if (Arrays.asList(allowedVariables).contains(splitLine[i])) {
					sb.append(splitLine[i] + ",");
				}
			}
			if (sb.toString() != null) {
				return sb.toString().substring(0, sb.toString().length() - 1);
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getYears(String yearString) {
		StringBuilder sb = new StringBuilder();
		int startIndex = yearString.indexOf(":") + 1;
		try {
			String[] splitLine = yearString.substring(startIndex, yearString.length()).split(",");
			for (int i = 0; i < splitLine.length; i ++) {
				sb.append(Integer.parseInt(splitLine[i]) + ",");
			}
			if (sb.toString() != null) {
				return sb.toString().substring(0, sb.toString().length() - 1);
			}
			return null;

		} catch (Exception e){
			return null;
		}
	}


	public static void parseAndDownload(String filename) throws IOException {
		String line = null;
		String vars = null;
		String years = null;
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Float> lats = new ArrayList<Float>();
		ArrayList<Float> lons = new ArrayList<Float>();

		File file = new File(filename);
		if (!file.exists()) {
			System.out.println(filename + " does not exist in working directory.");
			System.exit(0);
		}

		BufferedReader reader = new BufferedReader(new FileReader(file));


		while( ( line = reader.readLine() ) != null ) {
			line = line.replaceAll("\\s+", "").toLowerCase();

			if (line.startsWith("variables:")) {
				vars = getVars(line);
			}else if (line.startsWith("years:")) {
				years = getYears(line);
			} else {
			String[] line_split = line.split(",");
			if (line_split.length > 2) {
				names.add(line_split[0]);
				lats.add(Float.parseFloat(line_split[1]));
				lons.add(Float.parseFloat(line_split[2]));
			} else {
				lats.add(Float.parseFloat(line_split[0]));
				lons.add(Float.parseFloat(line_split[1]));
				names.add("NULL");
			}
		  }
		}
		reader.close();

		getData(vars, years, names,lats,lons);
	}
}
