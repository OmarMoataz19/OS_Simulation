import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
	private static Mutex userOutput;
	private static Mutex userInput;
	private static Mutex file;
	private String path1;
	private String path2;
	private String path3;
	private int timing1;
	private int timing2;
	private int timing3;
	private int timeslice;
	private int counter;
	private Queue<Integer> blockedQueue;
	private Queue<Integer> readyQueue;
	private int runningID;
	private MemoryWord[] memory;
	private int firstCounter;
	private int secondCounter;
	private int timeTaken;
	private Parser p;

	public Scheduler(String path1, String path2, String path3, int timing1, int timing2, int timing3, int timeslice) {
		this.userOutput = new Mutex();
		this.userInput = new Mutex();
		this.file = new Mutex();
		this.path1 = path1;
		this.path2 = path2;
		this.path3 = path3;
		this.timing1 = timing1;
		this.timing2 = timing2;
		this.timing3 = timing3;
		this.timeslice = timeslice;
		this.timeTaken = 0;
		this.blockedQueue = new LinkedList();
		this.readyQueue = new LinkedList();
		this.memory = new MemoryWord[40];
		this.firstCounter = 0;
		this.secondCounter = 0;
		this.counter = 0;
		this.runningID = -1;
		this.p = new Parser();
	}

	public void start() throws Exception {
		if (this.timeslice == 0) {
			System.out.println("Time slice can not be 0");
			return;
		}
		this.initializeMemory();
		boolean flag_1 = false;
		boolean flag_2 = false;
		boolean flag_3 = false;
		Interpreter I = new Interpreter();
		SystemCalls systemCall = new SystemCalls(this);
		boolean finished = false;
		while (!finished) {
			System.out.println("cycle: " + counter);
			System.out.println("When cycle starts: ");
			if (runningID != -1) {
				if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
					System.out.println("processRunning: " + ((int) Integer.parseInt(memory[0].getValue() + "")));
				} else {
					if ((int) Integer.parseInt(memory[20].getValue() + "") == runningID) {
						System.out.println("processRunning: " + ((int) Integer.parseInt(memory[20].getValue() + "")));
					}
				}
			} else {
				System.out.println("No Process running");
			}
			if (counter == timing1) {
				System.out.println("Process 1 arrived");
				ArrayList<String> instructions = new ArrayList<String>();
				instructions = p.parser(path1);
				int pID = 1;
				String state = "READY";
				int pc = 0;
				int minBound = 0;
				int maxBound = 0;
				Object temp = null;
				String nestedFlag = "false";
				if (memory[0].getValue() == null || (memory[0].getValue() + "").equals("null")) {
					minBound = 0;
					maxBound = 19;
					this.memory[0].setValue(pID);
					this.memory[1].setValue(state);
					this.memory[2].setValue(pc);
					this.memory[3].setValue(minBound);
					this.memory[4].setValue(maxBound);
					this.memory[5].setValue(temp);
					this.memory[6].setValue(nestedFlag);
					for (int i = 0; i < instructions.size(); i++) {
						this.memory[i + 10].setValue(instructions.get(i));
					}
				} else if (memory[20].getValue() == null || (memory[20].getValue() + "").equals("null")) {
					minBound = 20;
					maxBound = 39;
					this.memory[20].setValue(pID);
					this.memory[21].setValue(state);
					this.memory[22].setValue(pc);
					this.memory[23].setValue(minBound);
					this.memory[24].setValue(maxBound);
					this.memory[25].setValue(temp);
					this.memory[26].setValue(nestedFlag);
					for (int i = 0; i < instructions.size(); i++) {
						this.memory[i + 30].setValue(instructions.get(i));
					}
				} else {
					int swappedOutID = (int) Integer.parseInt(memory[0].getValue() + "");
					if (firstCounter > secondCounter) {
						for (int i = 0; i < 20; i++) {
							if (i == 0) {
								if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
									systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
								}
							} else {
								if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
									systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
								} else {
									if (memory[i].getKey().equals("var1") || memory[i].getKey().equals("var2")
											|| memory[i].getKey().equals("var3")) {
										systemCall.writeToDisk("Disk", null + "\n");
									}
								}
							}
						}
						firstCounter = 0;
						this.clearMemory(0);
						minBound = 0;
						maxBound = 19;
						this.memory[0].setValue(pID);
						this.memory[1].setValue(state);
						this.memory[2].setValue(pc);
						this.memory[3].setValue(minBound);
						this.memory[4].setValue(maxBound);
						this.memory[5].setValue(temp);
						this.memory[6].setValue(nestedFlag);
						for (int i = 0; i < instructions.size(); i++) {
							this.memory[i + 10].setValue(instructions.get(i));
						}
					} else {
						swappedOutID = (int) Integer.parseInt(memory[20].getValue() + "");
						for (int i = 20; i < 40; i++) {
							if (i == 0) {
								if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
									systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
								}
							} else {
								if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
									systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
								} else {
									if (memory[i].getKey().equals("var1") || memory[i].getKey().equals("var2")
											|| memory[i].getKey().equals("var3")) {
										systemCall.writeToDisk("Disk", null + "\n");
									}
								}
							}
						}
						secondCounter = 0;
						this.clearMemory(20);
						minBound = 20;
						maxBound = 39;
						this.memory[20].setValue(pID);
						this.memory[21].setValue(state);
						this.memory[22].setValue(pc);
						this.memory[23].setValue(minBound);
						this.memory[24].setValue(maxBound);
						this.memory[25].setValue(temp);
						this.memory[26].setValue(nestedFlag);
						for (int i = 0; i < instructions.size(); i++) {
							this.memory[i + 30].setValue(instructions.get(i));
						}
					}
					System.out.println("Swap occurred!");
					System.out.println("Process 1 swapped in");
					System.out.println("Process " + swappedOutID + " swapped out");
				}
				this.readyQueue.add(pID);
			}
			if (counter == timing2) {
				System.out.println("Process 2 arrived");
				ArrayList<String> instructions = new ArrayList<String>();
				instructions = p.parser(path2);
				int pID = 2;
				String state = "READY";
				int pc = 0;
				int minBound = 0;
				int maxBound = 0;
				Object temp = null;
				String nestedFlag = "false";
				if (memory[0].getValue() == null || (memory[0].getValue() + "").equals("null")) {
					minBound = 0;
					maxBound = 19;
					this.memory[0].setValue(pID);
					this.memory[1].setValue(state);
					this.memory[2].setValue(pc);
					this.memory[3].setValue(minBound);
					this.memory[4].setValue(maxBound);
					this.memory[5].setValue(temp);
					this.memory[6].setValue(nestedFlag);
					for (int i = 0; i < instructions.size(); i++) {
						this.memory[i + 10].setValue(instructions.get(i));
					}
				} else if (memory[20].getValue() == null || (memory[20].getValue() + "").equals("null")) {
					minBound = 20;
					maxBound = 39;
					this.memory[20].setValue(pID);
					this.memory[21].setValue(state);
					this.memory[22].setValue(pc);
					this.memory[23].setValue(minBound);
					this.memory[24].setValue(maxBound);
					this.memory[25].setValue(temp);
					this.memory[26].setValue(nestedFlag);
					for (int i = 0; i < instructions.size(); i++) {
						this.memory[i + 30].setValue(instructions.get(i));
					}
				} else {
					int swappedOutID = (int) Integer.parseInt(memory[0].getValue() + "");
					if (firstCounter > secondCounter) {
						for (int i = 0; i < 20; i++) {
							if (i == 0) {
								if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
									systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
								}
							} else {
								if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
									systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
								} else {
									if (memory[i].getKey().equals("var1") || memory[i].getKey().equals("var2")
											|| memory[i].getKey().equals("var3")) {
										systemCall.writeToDisk("Disk", null + "\n");
									}
								}
							}
						}
						firstCounter = 0;
						this.clearMemory(0);
						minBound = 0;
						maxBound = 19;
						this.memory[0].setValue(pID);
						this.memory[1].setValue(state);
						this.memory[2].setValue(pc);
						this.memory[3].setValue(minBound);
						this.memory[4].setValue(maxBound);
						this.memory[5].setValue(temp);
						this.memory[6].setValue(nestedFlag);
						for (int i = 0; i < instructions.size(); i++) {
							this.memory[i + 10].setValue(instructions.get(i));
						}
					} else {
						swappedOutID = (int) Integer.parseInt(memory[20].getValue() + "");
						for (int i = 20; i < 40; i++) {
							if (i == 0) {
								if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
									systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
								}
							} else {
								if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
									systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
								} else {
									if (memory[i].getKey().equals("var1") || memory[i].getKey().equals("var2")
											|| memory[i].getKey().equals("var3")) {
										systemCall.writeToDisk("Disk", null + "\n");
									}
								}
							}
						}
						secondCounter = 0;
						this.clearMemory(20);
						minBound = 20;
						maxBound = 39;
						this.memory[20].setValue(pID);
						this.memory[21].setValue(state);
						this.memory[22].setValue(pc);
						this.memory[23].setValue(minBound);
						this.memory[24].setValue(maxBound);
						this.memory[25].setValue(temp);
						this.memory[26].setValue(nestedFlag);
						for (int i = 0; i < instructions.size(); i++) {
							this.memory[i + 30].setValue(instructions.get(i));
						}
					}
					System.out.println("Swap occurred!");
					System.out.println("Process 2 swapped in");
					System.out.println("Process " + swappedOutID + " swapped out");
				}
				this.readyQueue.add(pID);
			}
			if (counter == timing3) {
				System.out.println("Process 3 arrived");
				ArrayList<String> instructions = new ArrayList<String>();
				instructions = p.parser(path3);
				int pID = 3;
				String state = "READY";
				int pc = 0;
				int minBound = 0;
				int maxBound = 0;
				Object temp = null;
				String nestedFlag = "false";
				if (memory[0].getValue() == null || (memory[0].getValue() + "").equals("null")) {
					minBound = 0;
					maxBound = 19;
					this.memory[0].setValue(pID);
					this.memory[1].setValue(state);
					this.memory[2].setValue(pc);
					this.memory[3].setValue(minBound);
					this.memory[4].setValue(maxBound);
					this.memory[5].setValue(temp);
					this.memory[6].setValue(nestedFlag);
					for (int i = 0; i < instructions.size(); i++) {
						this.memory[i + 10].setValue(instructions.get(i));
					}
				} else if (memory[20].getValue() == null || (memory[20].getValue() + "").equals("null")) {
					minBound = 20;
					maxBound = 39;
					this.memory[20].setValue(pID);
					this.memory[21].setValue(state);
					this.memory[22].setValue(pc);
					this.memory[23].setValue(minBound);
					this.memory[24].setValue(maxBound);
					this.memory[25].setValue(temp);
					this.memory[26].setValue(nestedFlag);
					for (int i = 0; i < instructions.size(); i++) {
						this.memory[i + 30].setValue(instructions.get(i));
					}
				} else {
					int swappedOutID = (int) Integer.parseInt(memory[0].getValue() + "");
					if (firstCounter > secondCounter) {
						for (int i = 0; i < 20; i++) {
							if (i == 0) {
								if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
									systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
								}
							} else {
								if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
									systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
								} else {
									if (memory[i].getKey().equals("var1") || memory[i].getKey().equals("var2")
											|| memory[i].getKey().equals("var3") || memory[i].getKey().equals("temp")
											|| memory[i].getKey().equals("nestedFlag")) {
										systemCall.writeToDisk("Disk", "null" + "\n");
									}
								}
							}
						}
						firstCounter = 0;
						this.clearMemory(0);
						minBound = 0;
						maxBound = 19;
						this.memory[0].setValue(pID);
						this.memory[1].setValue(state);
						this.memory[2].setValue(pc);
						this.memory[3].setValue(minBound);
						this.memory[4].setValue(maxBound);
						this.memory[5].setValue(temp);
						this.memory[6].setValue(nestedFlag);
						for (int i = 0; i < instructions.size(); i++) {
							this.memory[i + 10].setValue(instructions.get(i));
						}
					} else {
						swappedOutID = (int) Integer.parseInt(memory[20].getValue() + "");
						for (int i = 20; i < 40; i++) {
							if (i == 0) {
								if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
									systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
								}
							} else {
								if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
									systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
								} else {
									if (memory[i].getKey().equals("var1") || memory[i].getKey().equals("var2")
											|| memory[i].getKey().equals("var3") || memory[i].getKey().equals("temp")
											|| memory[i].getKey().equals("nestedFlag")) {
										systemCall.writeToDisk("Disk", null + "\n");
									}
								}
							}
						}
						secondCounter = 0;
						this.clearMemory(20);
						minBound = 20;
						maxBound = 39;
						this.memory[20].setValue(pID);
						this.memory[21].setValue(state);
						this.memory[22].setValue(pc);
						this.memory[23].setValue(minBound);
						this.memory[24].setValue(maxBound);
						this.memory[25].setValue(temp);
						this.memory[26].setValue(nestedFlag);
						for (int i = 0; i < instructions.size(); i++) {
							this.memory[i + 30].setValue(instructions.get(i));
						}
					}
					System.out.println("Swap occurred!");
					System.out.println("Process 3 swapped in");
					System.out.println("Process " + swappedOutID + " swapped out");
				}
				this.readyQueue.add(pID);
			}
			// admission done, start scheduling
			if (!(flag_1 && flag_2 && flag_3)) {
				if (runningID == -1) {
					// dispatch
					if (!readyQueue.isEmpty()) {
						runningID = readyQueue.remove();
						if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
							systemCall.writeToMemory("RUNNING", 1);
							System.out.println("A new process dispatched.");
							System.out.println("process running: " + runningID);
							System.out.print("Ready Queue: ");
							printQueue(readyQueue);
							System.out.println();
							System.out.print("Blocked Queue: ");
							printQueue(blockedQueue);
							System.out.println();
						} else if ((int) Integer.parseInt(memory[20].getValue() + "") == runningID) {
							systemCall.writeToMemory("RUNNING", 21);
							System.out.println("A new process dispatched.");
							System.out.println("process running: " + runningID);
							System.out.print("Ready Queue: ");
							printQueue(readyQueue);
							System.out.println();
							System.out.print("Blocked Queue: ");
							printQueue(blockedQueue);
							System.out.println();
						} else {
							this.swap();
							if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
								systemCall.writeToMemory("RUNNING", 1);
							} else {
								systemCall.writeToMemory("RUNNING", 21);
							}
							System.out.println("A new process dispatched.");
							System.out.println("process running: " + runningID);
							System.out.print("Ready Queue: ");
							printQueue(readyQueue);
							System.out.println();
							System.out.print("Blocked Queue: ");
							printQueue(blockedQueue);
							System.out.println();
						}
					}
				} else {
					if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
						if (timeTaken == timeslice) {
							if (readyQueue.isEmpty()) {
								this.setTimeTaken(0);
								System.out.println("Time slice done. No processes to be admitted.");
							} else {
								// preemption
								this.setTimeTaken(0);
								systemCall.writeToMemory("READY", 1);
								readyQueue.add(runningID);
								System.out.println("Time slice done. Process " + runningID
										+ " preempted and moved to ready queue.");
								runningID = readyQueue.remove();
								if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
									systemCall.writeToMemory("RUNNING", 1);
								} else if ((int) Integer.parseInt(memory[20].getValue() + "") == runningID) {
									systemCall.writeToMemory("RUNNING", 21);
								} else {
									this.swap();
									if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
										systemCall.writeToMemory("RUNNING", 1);
									} else {
										systemCall.writeToMemory("RUNNING", 21);
									}
								}
								System.out.println("process " + runningID + " dispatched.");
								System.out.print("Ready Queue: ");
								printQueue(readyQueue);
								System.out.println();
								System.out.print("Blocked Queue: ");
								printQueue(blockedQueue);
								System.out.println();
							}
						}
					} else if ((int) Integer.parseInt(memory[20].getValue() + "") == runningID) {
						if (timeTaken == timeslice) {
							if (readyQueue.isEmpty()) {
								this.setTimeTaken(0);
								System.out.println("Time slice done. No processes to be admitted.");
							} else {
								// preemption
								this.setTimeTaken(0);
								systemCall.writeToMemory("READY", 21);
								readyQueue.add(runningID);
								System.out.println("Time slice done. Process " + runningID
										+ " preempted and moved to ready queue.");
								runningID = readyQueue.remove();
								if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
									systemCall.writeToMemory("RUNNING", 1);
								} else if ((int) Integer.parseInt(memory[20].getValue() + "") == runningID) {
									systemCall.writeToMemory("RUNNING", 21);
								} else {
									this.swap();
									if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
										systemCall.writeToMemory("RUNNING", 1);
									} else {
										systemCall.writeToMemory("RUNNING", 21);
									}
								}
								System.out.println("process " + runningID + " dispatched.");
								System.out.print("Ready Queue: ");
								printQueue(readyQueue);
								System.out.println();
								System.out.print("Blocked Queue: ");
								printQueue(blockedQueue);
								System.out.println();
							}
						}
					} else {
						this.swap();
						if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
							if (timeTaken == timeslice) {
								if (readyQueue.isEmpty()) {
									this.setTimeTaken(0);
									System.out.println("Time slice done. No processes to be admitted.");
								} else {
									// preemption
									this.setTimeTaken(0);
									systemCall.writeToMemory("READY", 1);
									readyQueue.add(runningID);
									System.out.println("Time slice done. Process " + runningID
											+ " preempted and moved to ready queue.");
									runningID = readyQueue.remove();
									if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
										systemCall.writeToMemory("RUNNING", 1);
									} else if ((int) Integer.parseInt(memory[20].getValue() + "") == runningID) {
										systemCall.writeToMemory("RUNNING", 21);
									} else {
										this.swap();
										if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
											systemCall.writeToMemory("RUNNING", 1);
										} else {
											systemCall.writeToMemory("RUNNING", 21);
										}
									}
									System.out.println("process " + runningID + " dispatched.");
									System.out.print("Ready Queue: ");
									printQueue(readyQueue);
									System.out.println();
									System.out.print("Blocked Queue: ");
									printQueue(blockedQueue);
									System.out.println();
								}
							}
						} else {
							if (timeTaken == timeslice) {
								if (readyQueue.isEmpty()) {
									this.setTimeTaken(0);
									System.out.println("Time slice done. No processes to be admitted.");
								} else {
									// preemption
									this.setTimeTaken(0);
									systemCall.writeToMemory("READY", 21);
									readyQueue.add(runningID);
									System.out.println("Time slice done. Process " + runningID
											+ " preempted and moved to ready queue.");
									runningID = readyQueue.remove();
									if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
										systemCall.writeToMemory("RUNNING", 1);
									} else if ((int) Integer.parseInt(memory[20].getValue() + "") == runningID) {
										systemCall.writeToMemory("RUNNING", 21);
									} else {
										this.swap();
										if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
											systemCall.writeToMemory("RUNNING", 1);
										} else {
											systemCall.writeToMemory("RUNNING", 21);
										}
									}
									System.out.println("process " + runningID + " dispatched.");
									System.out.print("Ready Queue: ");
									printQueue(readyQueue);
									System.out.println();
									System.out.print("Blocked Queue: ");
									printQueue(blockedQueue);
									System.out.println();
								}
							}
						}
					}
				}
				if (runningID != -1) {
					if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
						int x = (int) Integer.parseInt(systemCall.readFromMemory(2) + "") + 10;
						String nextInstruction = (String) systemCall.readFromMemory(x);
						System.out.println("Process " + runningID + " currently executing " + nextInstruction);
						I.execute(nextInstruction, runningID, this);
						if (((String) systemCall.readFromMemory(6)).equals("false")) {
							int y = (int) Integer.parseInt(systemCall.readFromMemory(2) + "") + 1;
							systemCall.writeToMemory(y + "", 2);
						}
						this.setTimeTaken(this.getTimeTaken() + 1);
						if (((String) systemCall.readFromMemory(1)).equals("BLOCKED")) {
							runningID = -1;
							this.setTimeTaken(0);
						}
						System.out.println("Queues after executing: ");
						System.out.print("Ready Queue: ");
						printQueue(readyQueue);
						System.out.println();
						System.out.print("Blocked Queue: ");
						printQueue(blockedQueue);
						System.out.println();
					} else if ((int) Integer.parseInt(systemCall.readFromMemory(20) + "") == runningID) {
						int x = (int) Integer.parseInt(systemCall.readFromMemory(22) + "") + 30;
						String nextInstruction = systemCall.readFromMemory(x) + "";
						System.out.println("Process " + runningID + " currently executing " + nextInstruction);
						I.execute(nextInstruction, runningID, this);
						if (((String) systemCall.readFromMemory(26)).equals("false")) {
							int y = (int) Integer.parseInt(systemCall.readFromMemory(22) + "") + 1;
							systemCall.writeToMemory(y + "", 22);
						}
						this.setTimeTaken(this.getTimeTaken() + 1);
						if (((String) systemCall.readFromMemory(21)).equals("BLOCKED")) {
							runningID = -1;
							this.setTimeTaken(0);
						}
						System.out.println("Queues after executing: ");
						System.out.print("Ready Queue: ");
						printQueue(readyQueue);
						System.out.println();
						System.out.print("Blocked Queue: ");
						printQueue(blockedQueue);
						System.out.println();
					} else {
						this.swap();
						if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
							int x = (int) Integer.parseInt(systemCall.readFromMemory(2) + "") + 10;
							String nextInstruction = (String) systemCall.readFromMemory(x);
							System.out.println("Process " + runningID + " currently executing " + nextInstruction);
							I.execute(nextInstruction, runningID, this);
							if (((String) systemCall.readFromMemory(6)).equals("false")) {
								int y = (int) Integer.parseInt(systemCall.readFromMemory(2) + "") + 1;
								systemCall.writeToMemory(y + "", 2);
							}
							this.setTimeTaken(this.getTimeTaken() + 1);
							if (((String) systemCall.readFromMemory(1)).equals("BLOCKED")) {
								runningID = -1;
								this.setTimeTaken(0);
							}
							System.out.println("Queues after executing: ");
							System.out.print("Ready Queue: ");
							printQueue(readyQueue);
							System.out.println();
							System.out.print("Blocked Queue: ");
							printQueue(blockedQueue);
							System.out.println();
						} else {
							int x = (int) Integer.parseInt(systemCall.readFromMemory(22) + "") + 30;
							String nextInstruction = "" + systemCall.readFromMemory(x);
							System.out.println("Process " + runningID + " currently executing " + nextInstruction);
							I.execute(nextInstruction, runningID, this);
							if (((String) systemCall.readFromMemory(26)).equals("false")) {
								int y = (int) Integer.parseInt(systemCall.readFromMemory(22) + "") + 1;
								systemCall.writeToMemory(y + "", 22);
							}
							this.setTimeTaken(this.getTimeTaken() + 1);
							if (((String) systemCall.readFromMemory(21)).equals("BLOCKED")) {
								runningID = -1;
								this.setTimeTaken(0);
							}
							System.out.println("Queues after executing: ");
							System.out.print("Ready Queue: ");
							printQueue(readyQueue);
							System.out.println();
							System.out.print("Blocked Queue: ");
							printQueue(blockedQueue);
							System.out.println();
						}
					}
				}
			}
			if (runningID != -1) {
				if ((int) Integer.parseInt(memory[0].getValue() + "") == runningID) {
					if (runningID == 1) {
						int x = (int) Integer.parseInt(systemCall.readFromMemory(2) + "");
						if (memory[x + 10].getValue() == null || (memory[x + 10].getValue() + "").equals("null")) {
							flag_1 = true;
							systemCall.writeToMemory("FINISHED", 1);
							System.out.println("Process 1 is done executing");
							runningID = -1;
							this.setTimeTaken(0);
						}
					} else if (runningID == 2) {
						int x = (int) Integer.parseInt(systemCall.readFromMemory(2) + "");
						if (memory[x + 10].getValue() == null || (memory[x + 10].getValue() + "").equals("null")) {
							flag_2 = true;
							systemCall.writeToMemory("FINISHED", 1);
							System.out.println("Process 2 is done executing");
							runningID = -1;
							this.setTimeTaken(0);
						}
					} else if (runningID == 3) {
						int x = (int) Integer.parseInt(systemCall.readFromMemory(2) + "");
						if (memory[x + 10].getValue() == null || (memory[x + 10].getValue() + "").equals("null")) {
							flag_3 = true;
							systemCall.writeToMemory("FINISHED", 1);
							System.out.println("Process 3 is done executing");
							runningID = -1;
							this.setTimeTaken(0);
						}
					}
				} else if (runningID == (int) Integer.parseInt(systemCall.readFromMemory(20) + "")) {
					if (runningID == 1) {
						int x = (int) Integer.parseInt(systemCall.readFromMemory(22) + "");
						if (memory[x + 30].getValue() == null || (memory[x + 30].getValue() + "").equals("null")) {
							flag_1 = true;
							systemCall.writeToMemory("FINISHED", 21);
							System.out.println("Process 1 is done executing");
							runningID = -1;
							this.setTimeTaken(0);
						}
					} else if (runningID == 2) {
						int x = (int) Integer.parseInt(systemCall.readFromMemory(22) + "");
						if (memory[x + 30].getValue() == null || (memory[x + 30].getValue() + "").equals("null")) {
							flag_2 = true;
							systemCall.writeToMemory("FINISHED", 21);
							System.out.println("Process 2 is done executing");
							runningID = -1;
							this.setTimeTaken(0);
						}
					} else if (runningID == 3) {
						int x = (int) Integer.parseInt(systemCall.readFromMemory(22) + "");
						if (memory[x + 30].getValue() == null || (memory[x + 30].getValue() + "").equals("null")) {
							flag_3 = true;
							systemCall.writeToMemory("FINISHED", 21);
							System.out.println("Process 3 is done executing");
							runningID = -1;
							this.setTimeTaken(0);
						}
					}
				}
			}
			if (flag_1 && flag_2 && flag_3) {
				finished = true;
				System.out.println("All proccesses done executing");
			}
			if (this.memory[0].getValue() != null || !(memory[0].getValue() + "").equals("null")) {
				firstCounter++;
			}
			if (this.memory[20].getValue() != null || !(memory[20].getValue() + "").equals("null")) {
				secondCounter++;
			}
			System.out.println("Memory after cycle " + counter + ":");
			this.printMemory();
			System.out.println("Disk after cycle " + counter + ":");
			String[] disk = systemCall.readFromDisk("Disk.txt");
			System.out.print("{");
			for (int i = 0; i < disk.length; i++) {
				if (i != disk.length - 1) {
					System.out.print(disk[i] + ", ");
				} else {
					System.out.println(disk[i] + "}");
				}
			}
			System.out.println();
			System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><>");
			System.out.println();
			counter++;
		}
	}

	public void swap() throws IOException {
		SystemCalls systemCall = new SystemCalls(this);
		Object[] temp = new Object[20];
		temp = systemCall.readFromDisk("Disk.txt");
		FileWriter fw = new FileWriter("Disk.txt", false);
		int swappedInID = (int) Integer.parseInt(temp[0] + "");
		int swappedOutID = (int) Integer.parseInt(memory[0].getValue() + "");
		fw.flush();
		if (firstCounter > secondCounter) {
			for (int i = 0; i < 20; i++) {
				if (i == 0) {
					if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
						systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
					}
				} else {
					if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
						systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
					} else {
						if (memory[i].getKey().equals("var1") || memory[i].getKey().equals("var2")
								|| memory[i].getKey().equals("var3") || memory[i].getKey().equals("temp")
								|| memory[i].getKey().equals("nestedFlag")) {
							systemCall.writeToDisk("Disk", "null" + "\n");
						}
					}
				}
			}
			firstCounter = 0;
			this.clearMemory(0);
			for (int i = 0; i < 20; i++) {
				if (temp[i] != null || !temp.equals("null")) {
					if (i == 3) {
						this.memory[i].setValue(0);
					} else if (i == 4) {
						this.memory[i].setValue(20);
					} else {
						this.memory[i].setValue(temp[i]);
					}
				} else {
					this.memory[i].setValue(null);
				}
			}
		} else {
			swappedOutID = (int) Integer.parseInt(memory[20].getValue() + "");
			for (int i = 20; i < 40; i++) {
				if (i == 0) {
					if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
						systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
					}
				} else {
					if (memory[i].getValue() != null || !(memory[i].getValue() + "").equals("null")) {
						systemCall.writeToDisk("Disk", memory[i].getValue() + "\n");
					} else {
						if (memory[i].getKey().equals("var1") || memory[i].getKey().equals("var2")
								|| memory[i].getKey().equals("var3") || memory[i].getKey().equals("temp")
								|| memory[i].getKey().equals("nestedFlag")) {
							systemCall.writeToDisk("Disk", null + "\n");
						}
					}
				}
			}
			secondCounter = 0;
			this.clearMemory(20);
			for (int i = 0; i < 20; i++) {
				if (temp[i] != null) {
					if (i == 3) {
						this.memory[i + 20].setValue(20);
					} else if (i == 4) {
						this.memory[i + 20].setValue(40);
					} else {
						this.memory[i + 20].setValue(temp[i]);
					}
				} else {
					this.memory[i + 20].setValue(null);
				}
			}
		}
		System.out.println("Swap occurred!");
		System.out.println("Process " + swappedInID + " swapped in");
		System.out.println("Process " + swappedOutID + " swapped out");
	}

	public void initializeMemory() {
		for (int i = 0; i < 40; i++) {
			this.memory[i] = new MemoryWord("", null);
		}
		this.memory[0].setKey("pID");
		this.memory[1].setKey("pState");
		this.memory[2].setKey("PC");
		this.memory[3].setKey("minBound");
		this.memory[4].setKey("maxBound");
		this.memory[5].setKey("temp");
		this.memory[6].setKey("nestedFlag");
		this.memory[7].setKey("var1");
		this.memory[8].setKey("var2");
		this.memory[9].setKey("var3");
		for (int i = 10; i < 20; i++) {
			this.memory[i].setKey("instruction" + (i - 9));
		}
		this.memory[20].setKey("pID");
		this.memory[21].setKey("pState");
		this.memory[22].setKey("PC");
		this.memory[23].setKey("minBound");
		this.memory[24].setKey("maxBound");
		this.memory[25].setKey("temp");
		this.memory[26].setKey("nestedFlag");
		this.memory[27].setKey("var1");
		this.memory[28].setKey("var2");
		this.memory[29].setKey("var3");
		for (int j = 30; j < 40; j++) {
			this.memory[j].setKey("instruction" + (j - 29));
		}
	}

	public void clearMemory(int startingBound) {
		for (int i = 0; i < 20; i++) {
			this.memory[i + startingBound].setValue(null);
		}
	}

	public static void printQueue(Queue<Integer> Q) {
		for (Integer s : Q) {
			System.out.print("Process " + s + ", ");
		}
	}

	public void printMemory() {
		System.out.print("{");
		for (int i = 0; i < 40; i++) {
			if (i != 39) {
				System.out.print(memory[i] + ", ");
				if (i == 9 || i == 19 || i == 29) {
					System.out.println();
				}
			} else {
				System.out.println(memory[i] + "}");
			}
		}
	}

	public static Mutex getUserOutput() {
		return userOutput;
	}

	public static void setUserOutput(Mutex userOutput) {
		Scheduler.userOutput = userOutput;
	}

	public static Mutex getUserInput() {
		return userInput;
	}

	public static void setUserInput(Mutex userInput) {
		Scheduler.userInput = userInput;
	}

	public static Mutex getFile() {
		return file;
	}

	public static void setFile(Mutex file) {
		Scheduler.file = file;
	}

	public String getPath1() {
		return path1;
	}

	public void setPath1(String path1) {
		this.path1 = path1;
	}

	public String getPath2() {
		return path2;
	}

	public void setPath2(String path2) {
		this.path2 = path2;
	}

	public String getPath3() {
		return path3;
	}

	public void setPath3(String path3) {
		this.path3 = path3;
	}

	public int getTiming1() {
		return timing1;
	}

	public void setTiming1(int timing1) {
		this.timing1 = timing1;
	}

	public int getTiming2() {
		return timing2;
	}

	public void setTiming2(int timing2) {
		this.timing2 = timing2;
	}

	public int getTiming3() {
		return timing3;
	}

	public void setTiming3(int timing3) {
		this.timing3 = timing3;
	}

	public int getTimeslice() {
		return timeslice;
	}

	public void setTimeslice(int timeslice) {
		this.timeslice = timeslice;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public Queue<Integer> getBlockedQueue() {
		return blockedQueue;
	}

	public void setBlockedQueue(Queue<Integer> blockedQueue) {
		this.blockedQueue = blockedQueue;
	}

	public Queue<Integer> getReadyQueue() {
		return readyQueue;
	}

	public void setReadyQueue(Queue<Integer> readyQueue) {
		this.readyQueue = readyQueue;
	}

	public int getRunningID() {
		return runningID;
	}

	public void setRunningID(int runningID) {
		this.runningID = runningID;
	}

	public MemoryWord[] getMemory() {
		return memory;
	}

	public void setMemory(MemoryWord[] memory) {
		this.memory = memory;
	}

	public int getFirstCounter() {
		return firstCounter;
	}

	public void setFirstCounter(int firstCounter) {
		this.firstCounter = firstCounter;
	}

	public int getSecondCounter() {
		return secondCounter;
	}

	public void setSecondCounter(int secondCounter) {
		this.secondCounter = secondCounter;
	}

	public int getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(int timeTaken) {
		this.timeTaken = timeTaken;
	}

	public Parser getP() {
		return p;
	}

	public void setP(Parser p) {
		this.p = p;
	}

}
