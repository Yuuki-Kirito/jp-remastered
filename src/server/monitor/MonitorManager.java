package server.monitor;

// TODO ÐÑý­ÞÅª¦åøïÒªéª·ª¤£¿ù¼éÄ£¿
public class MonitorManager {
	private MessageMonitor msgMonitor;

	public MonitorManager() {
		msgMonitor = new MessageMonitor();
	}

	public void register(Monitorable m) {
		m.registerMonitor(msgMonitor);
	}

	public void remove(Monitorable m) {
		m.removeMonitor(msgMonitor);
	}
}