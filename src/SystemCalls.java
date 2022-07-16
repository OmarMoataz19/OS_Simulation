import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SystemCalls {
	Scheduler s;

	public SystemCalls(Scheduler scheduler) {
		this.s = scheduler;
	}

	public void printOnScreen(String s) {
		System.out.println(s);
	}

	public String[] readFromDisk(String path) throws IOException {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String s[] = new String[20];
		int i = 0;
		while (i != 20) {
			String temp = br.readLine();
			if ((temp != null || temp != "null") && temp != "") {
				s[i] = temp;
			} else {
				s[i] = null;
			}
			i++;
		}
		br.close();
		fr.close();
		return s;
	}

	public String readFile(String path) throws IOException {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		String s;
		String result = "";
		while ((s = br.readLine()) != null) {
			result += s;
		}
		br.close();
		fr.close();
		return result;
	}

	public void writeToDisk(String name, String data) throws IOException {
		FileWriter file = new FileWriter(name + ".txt", true);
		file.write(data + "");
		file.close();
	}

	public void writeFile(String name, String data) throws IOException {
		FileWriter file = new FileWriter(name + ".txt");
		file.write(data + "");
		file.close();
	}

	public String input() {
		System.out.print("Please enter a value: ");
		Scanner sc = new Scanner(System.in);
		String result = sc.nextLine();
		return result;
	}

	public void writeToMemory(String data, int index) {
		s.getMemory()[index].setValue(data);
		;
	}

	public Object readFromMemory(int index) {
		return s.getMemory()[index].getValue();
	}

}