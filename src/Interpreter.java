import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Interpreter {

	private static boolean toPrint;

	public Interpreter() {
		this.toPrint = false;
	}

	public static void execute(String instruction, int processID, Scheduler s) throws Exception {
		SystemCalls systemCall = new SystemCalls(s);
		String[] split = instruction.split(" ");
		if (split[0].equals("print")) {
			if ((int) Integer.parseInt(s.getMemory()[0].getValue() + "") == processID) {
				for (int i = 7; i <= 9; i++) {
					if (s.getMemory()[i].getValue() != null && !(s.getMemory()[i].getValue() + "").equals("null")) {
						String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
						if (split[1].equals(splittedValue[0])) {
							String printedValue = "";
							for (int j = 1; j < splittedValue.length; j++) {
								printedValue += splittedValue[j] + " ";
							}
							systemCall.printOnScreen(printedValue);
						}
					}
				}
			} else {
				if ((int) Integer.parseInt(s.getMemory()[20].getValue() + "") == processID) {
					for (int i = 27; i <= 29; i++) {
						if (s.getMemory()[i].getValue() != null && !(s.getMemory()[i].getValue() + "").equals("null")) {
							String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
							if (split[1].equals(splittedValue[0])) {
								String printedValue = "";
								for (int j = 1; j < splittedValue.length; j++) {
									printedValue += splittedValue[j] + " ";
								}
								systemCall.printOnScreen(printedValue);
							}
						}
					}
				}
			}
		} else if (split[0].equals("assign")) {
			if (split[2].equals("input")) {
				if ((int) Integer.parseInt(s.getMemory()[0].getValue() + "") == processID) {
					if (s.getMemory()[5].getValue() != null && !(s.getMemory()[5].getValue() + "").equals("null")) {
						String dataType;
						String y = (String) systemCall.readFromMemory(5);
						if (y.matches("\\d+")) {
							dataType = "Integer";
						} else {
							dataType = "String";
						}
						if (dataType.equals("Integer")) {
							int x = (int) Integer.parseInt(y);
							int index = -1;
							boolean found = false;
							for (int i = 7; i <= 9; i++) {
								if (s.getMemory()[i].getValue() != null
										&& !(s.getMemory()[i].getValue() + "").equals("null")) {
									String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
									if (split[1].equals(splittedValue[0])) {
										systemCall.writeToMemory(split[1] + " " + x, i);
										found = true;
									}
								} else {
									index = i;
								}
							}
							if (!found) {
								if (index != -1) {
									systemCall.writeToMemory(split[1] + " " + x, index);
								}
							}
							systemCall.writeToMemory(null, 5);
							systemCall.writeToMemory("false", 6);

						} else {
							String x = y;
							int index = -1;
							boolean found = false;
							for (int i = 7; i <= 9; i++) {
								if (s.getMemory()[i].getValue() != null
										&& !(s.getMemory()[i].getValue() + "").equals("null")) {
									String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
									if (split[1].equals(splittedValue[0])) {
										systemCall.writeToMemory(split[1] + " " + x, i);
										found = true;
									}
								} else {
									index = i;
								}
							}
							if (!found) {
								if (index != -1) {
									systemCall.writeToMemory(split[1] + " " + x, index);
								}
							}
							systemCall.writeToMemory(null, 5);
							systemCall.writeToMemory("false", 6);

						}

					} else {
						systemCall.writeToMemory(systemCall.input(), 5);
						systemCall.writeToMemory("true", 6);
					}
				} else if ((int) Integer.parseInt(s.getMemory()[20].getValue() + "") == processID) {
					if (s.getMemory()[25].getValue() != null && !(s.getMemory()[25].getValue() + "").equals("null")) {
						String dataType;
						String y = (String) systemCall.readFromMemory(25);
						if (y.matches("\\d+")) {
							dataType = "Integer";
						} else {
							dataType = "String";
						}
						if (dataType.equals("Integer")) {
							int x = (int) Integer.parseInt(y);
							int index = -1;
							boolean found = false;
							for (int i = 27; i <= 29; i++) {
								if (s.getMemory()[i].getValue() != null
										&& !(s.getMemory()[i].getValue() + "").equals("null")) {
									String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
									if (split[1].equals(splittedValue[0])) {
										systemCall.writeToMemory(split[1] + " " + x, i);
										found = true;
									}
								} else {
									index = i;
								}
							}
							if (!found) {
								if (index != -1) {

									systemCall.writeToMemory(split[1] + " " + x, index);
								}
							}
							systemCall.writeToMemory(null, 25);
							systemCall.writeToMemory("false", 26);

						} else {
							String x = y;
							int index = -1;
							boolean found = false;
							for (int i = 27; i <= 29; i++) {
								if (s.getMemory()[i].getValue() != null
										&& !(s.getMemory()[i].getValue() + "").equals("null")) {
									String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
									if (split[1].equals(splittedValue[0])) {
										systemCall.writeToMemory(split[1] + " " + x, i);
										found = true;
									}
								} else {
									index = i;
								}
							}
							if (!found) {
								if (index != -1) {
									systemCall.writeToMemory(split[1] + " " + x, index);
								}
							}
							systemCall.writeToMemory(null, 25);
							systemCall.writeToMemory("false", 26);

						}

					} else {
						systemCall.writeToMemory(systemCall.input(), 25);
						systemCall.writeToMemory("true", 26);
					}
				}
			} else if (split[2].equals("readFile")) {
				if ((int) Integer.parseInt(s.getMemory()[0].getValue() + "") == processID) {
					if (s.getMemory()[5] != null && !(s.getMemory()[5].getValue() + "").equals("null")) {
						String dataType;
						String y = (String) systemCall.readFromMemory(5);
						if (y.matches("\\d+")) {
							dataType = "Integer";
						} else {
							dataType = "String";
						}
						if (dataType.equals("Integer")) {
							int x = (int) Integer.parseInt(y);
							int index = -1;
							boolean found = false;
							for (int i = 7; i <= 9; i++) {
								if (s.getMemory()[i].getValue() != null
										&& !(s.getMemory()[i].getValue() + "").equals("null")) {
									String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
									if (split[1].equals(splittedValue[0])) {
										systemCall.writeToMemory(split[1] + " " + x, i);
										found = true;
									}
								} else {
									index = i;
								}
							}
							if (!found) {
								if (index != -1) {
									systemCall.writeToMemory(split[1] + " " + x, index);
								}
							}
							systemCall.writeToMemory(null, 5);
							systemCall.writeToMemory("false", 6);

						} else {
							String x = y;
							int index = -1;
							boolean found = false;
							for (int i = 7; i <= 9; i++) {
								if (s.getMemory()[i].getValue() != null
										&& !(s.getMemory()[i].getValue() + "").equals("null")) {
									String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
									if (split[1].equals(splittedValue[0])) {
										systemCall.writeToMemory(split[1] + " " + x, i);
										found = true;
									}
								} else {
									index = i;
								}
							}
							if (!found) {
								if (index != -1) {
									systemCall.writeToMemory(split[1] + " " + x, index);
								}
							}
							systemCall.writeToMemory(null, 5);
							systemCall.writeToMemory("false", 6);

						}
					} else {
						Object fileName = null;
						for (int i = 7; i <= 9; i++) {
							if (s.getMemory()[i].getValue() != null
									&& !(s.getMemory()[i].getValue() + "").equals("null")) {
								String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
								if (split[3].equals(splittedValue[0])) {
									fileName = splittedValue[1];
								}
							}
						}
						systemCall.writeToMemory(systemCall.readFile(fileName + ".txt"), 5);
						systemCall.writeToMemory("true", 6);
					}
				} else {
					if ((int) Integer.parseInt(s.getMemory()[20].getValue() + "") == processID) {
						if (s.getMemory()[25] != null && !(s.getMemory()[25].getValue() + "").equals("null")) {
							String dataType;
							String y = (String) systemCall.readFromMemory(25);
							if (y.matches("\\d+")) {
								dataType = "Integer";
							} else {
								dataType = "String";
							}
							if (dataType.equals("Integer")) {
								int x = (int) Integer.parseInt(y);
								int index = -1;
								boolean found = false;
								for (int i = 27; i <= 29; i++) {
									if (s.getMemory()[i].getValue() != null
											&& !(s.getMemory()[i].getValue() + "").equals("null")) {
										String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
										if (split[1].equals(splittedValue[0])) {
											systemCall.writeToMemory(split[1] + " " + x, i);
											found = true;
										}
									} else {
										index = i;
									}
								}
								if (!found) {
									if (index != -1) {
										systemCall.writeToMemory(split[1] + " " + x, index);
									}
								}
								systemCall.writeToMemory(null, 25);
								systemCall.writeToMemory("false", 26);

							} else {
								String x = y;
								int index = -1;
								boolean found = false;
								for (int i = 27; i <= 29; i++) {
									if (s.getMemory()[i].getValue() != null
											&& !(s.getMemory()[i].getValue() + "").equals("null")) {
										String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
										if (split[1].equals(splittedValue[0])) {
											systemCall.writeToMemory(split[1] + " " + x, i);
											found = true;
										}
									} else {
										index = i;
									}
								}
								if (!found) {
									if (index != -1) {
										systemCall.writeToMemory(split[1] + " " + x, index);
									}
								}
								systemCall.writeToMemory(null, 25);
								systemCall.writeToMemory("false", 26);

							}
						} else {
							Object fileName = null;
							for (int i = 27; i <= 29; i++) {
								if (s.getMemory()[i].getValue() != null
										&& !(s.getMemory()[i].getValue() + "").equals("null")) {
									String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
									if (split[3].equals(splittedValue[0])) {
										fileName = splittedValue[1];
									}
								}
							}
							systemCall.writeToMemory(systemCall.readFile(fileName + ".txt"), 25);
							systemCall.writeToMemory("true", 26);
						}
					}
				}

			}
		} else if (split[0].equals("printFromTo")) {
			String tmp1 = split[1];
			String tmp2 = split[2];
			Object start = null;
			Object end = null;
			if ((int) Integer.parseInt(s.getMemory()[0].getValue() + "") == processID) {
				for (int i = 7; i <= 9; i++) {
					if (s.getMemory()[i].getValue() != null && !(s.getMemory()[i].getValue() + "").equals("null")) {
						String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
						if (tmp1.equals(splittedValue[0])) {
							start = splittedValue[1] + "";
						}
					}
				}
				for (int i = 7; i <= 9; i++) {
					if (s.getMemory()[i].getValue() != null && !(s.getMemory()[i].getValue() + "").equals("null")) {
						String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
						if (tmp2.equals(splittedValue[0])) {
							end = splittedValue[1] + "";
						}
					}
				}
			} else {
				if ((int) Integer.parseInt(s.getMemory()[20].getValue() + "") == processID) {
					for (int i = 27; i <= 29; i++) {
						if (s.getMemory()[i].getValue() != null && !(s.getMemory()[i].getValue() + "").equals("null")) {
							String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
							if (tmp1.equals(splittedValue[0])) {
								start = splittedValue[1];
							}
						}
					}
					for (int i = 27; i <= 29; i++) {
						if (s.getMemory()[i].getValue() != null && !(s.getMemory()[i].getValue() + "").equals("null")) {
							String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
							if (tmp2.equals(splittedValue[0])) {
								end = splittedValue[1];
							}
						}
					}
				}
			}
			int startValue = Integer.parseInt("" + start);
			int endValue = Integer.parseInt(("" + end));
			while (startValue <= endValue) {
				systemCall.printOnScreen(startValue + "");
				startValue++;
			}
		} else if (split[0].equals("writeFile")) {
			String tmp1 = split[1];
			String tmp2 = split[2];
			Object a = "";
			Object b = "";
			if ((int) Integer.parseInt(s.getMemory()[0].getValue() + "") == processID) {
				for (int i = 7; i <= 9; i++) {
					if (s.getMemory()[i].getValue() != null && !(s.getMemory()[i].getValue() + "").equals("null")) {
						String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
						if (tmp1.equals(splittedValue[0])) {
							for (int j = 1; j < splittedValue.length; j++) {
								a += splittedValue[j] + " ";
							}
						}
					}
				}
				for (int i = 7; i <= 9; i++) {
					if (s.getMemory()[i].getValue() != null && !(s.getMemory()[i].getValue() + "").equals("null")) {
						String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
						if (tmp2.equals(splittedValue[0])) {
							for (int j = 1; j < splittedValue.length; j++) {
								b += splittedValue[j] + " ";
							}
						}
					}
				}
			} else {
				if ((int) Integer.parseInt(s.getMemory()[20].getValue() + "") == processID) {
					for (int i = 27; i <= 29; i++) {
						if (s.getMemory()[i].getValue() != null && !(s.getMemory()[i].getValue() + "").equals("null")) {
							String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
							if (tmp1.equals(splittedValue[0])) {
								for (int j = 1; j < splittedValue.length; j++) {
									a += splittedValue[j] + " ";
								}
							}
						}
					}
					for (int i = 27; i <= 29; i++) {
						if (s.getMemory()[i].getValue() != null && !(s.getMemory()[i].getValue() + "").equals("null")) {
							String[] splittedValue = (systemCall.readFromMemory(i) + "").split(" ");
							if (tmp2.equals(splittedValue[0])) {
								for (int j = 1; j < splittedValue.length; j++) {
									b += splittedValue[j] + " ";
								}
							}
						}
					}
				}
			}
			systemCall.writeFile(a + "", b + "");
		} else if (split[0].equals("readFile")) {
			String fileName = split[1];
			String[] data = systemCall.readFromDisk(fileName);
		} else if (split[0].equals("semWait")) {
			if (split[1].equals("userOutput")) {
				s.getUserOutput().semWait(processID, s);
			} else if (split[1].equals("userInput")) {
				s.getUserInput().semWait(processID, s);
			} else if (split[1].equals("file")) {
				s.getFile().semWait(processID, s);
			}
		} else if (split[0].equals("semSignal")) {
			if (split[1].equals("userOutput")) {
				s.getUserOutput().semSignal(processID, s);
			} else if (split[1].equals("userInput")) {
				s.getUserInput().semSignal(processID, s);
			} else if (split[1].equals("file")) {
				s.getFile().semSignal(processID, s);

			}
		}

	}

	public boolean isToPrint() {
		return toPrint;
	}

	public void setToPrint(boolean toPrint) {
		this.toPrint = toPrint;
	}

}
