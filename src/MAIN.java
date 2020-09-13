import java.awt.EventQueue;

public class MAIN {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					Login frame = new Login();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
