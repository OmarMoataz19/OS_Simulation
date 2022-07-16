import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws Exception {
		String path1 = "Program_1.txt";
		String path2 = "Program_2.txt";
		String path3 = "Program_3.txt";
		Scheduler s = new Scheduler(path1, path2, path3, 0, 1, 4, 2);
		s.start();
	}

}