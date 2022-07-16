import java.util.Queue;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Mutex {
	private boolean available;
	private Queue<Integer> blockedQueue;
	private int ownerID;

	public Mutex() {
		this.available = true;
		this.blockedQueue = new LinkedList();
		this.ownerID = -1;
	}

	public void semWait(int processID, Scheduler s) {
		if (this.isAvailable()) {
			this.setOwnerID(processID);
			this.setAvailable(false);
		} else {
			this.getBlockedQueue().add(processID);
			s.getBlockedQueue().add(processID);
			if ((int) Integer.parseInt(s.getMemory()[0].getValue() + "") == processID) {
				s.getMemory()[1].setValue("BLOCKED");
			} else {
				if ((int) Integer.parseInt(s.getMemory()[20].getValue() + "") == processID) {
					s.getMemory()[21].setValue("BLOCKED");
				}
			}
		}
	}

	public void semSignal(int processID, Scheduler s) throws IOException {
		if (this.getOwnerID() == processID) {
			if (this.getBlockedQueue().isEmpty()) {
				this.setAvailable(true);
				this.setOwnerID(-1);
			} else {
				int processDequeuedID = this.getBlockedQueue().remove();
				for (int i = 0; i < s.getBlockedQueue().size(); i++) {
					if (processDequeuedID != s.getBlockedQueue().peek()) {
						int tmp = s.getBlockedQueue().remove();
						s.getBlockedQueue().add(tmp);
					} else {
						s.getBlockedQueue().remove();
					}
				}
				this.setOwnerID(processDequeuedID);
				if (processDequeuedID == (int) Integer.parseInt(s.getMemory()[0].getValue() + "")) {
					s.getMemory()[1].setValue("READY");
				} else {
					if (processDequeuedID == (int) Integer.parseInt(s.getMemory()[20].getValue() + "")) {
						s.getMemory()[21].setValue("READY");
					} else {
						SystemCalls systemCall = new SystemCalls(s);
						Object[] tmp = new Object[20];
						tmp = systemCall.readFromDisk("Disk.txt");
						tmp[1] = "READY";
						FileWriter fw = new FileWriter("Disk.txt", false);
						fw.flush();
						for (int i = 0; i < tmp.length; i++) {
							systemCall.writeToDisk("Disk", tmp[i] + "\n");
						}
					}
				}
				s.getReadyQueue().add(processDequeuedID);
			}
		}
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Queue<Integer> getBlockedQueue() {
		return blockedQueue;
	}

	public void setBlockedQueue(Queue<Integer> blockedQueue) {
		this.blockedQueue = blockedQueue;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

}