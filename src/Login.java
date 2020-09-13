import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

@SuppressWarnings("serial")
public class Login extends JFrame {


	private JFrame Login;
	private JButton btnLogn;
	private JFrame Start;
	private JLabel lblPhoneNumber;
	private JTextField textField;
	private JPasswordField textField_1;
	private JLabel lblPassword;
	private JCheckBox rdbtnCustomer ;
	private JCheckBox rdbtnAdmin ;
	private JButton btnLogin;
	private JButton btnSignup;
	


	//the classes that has frames inside it designed with WindowBuilder Editor so we mostly edited the action performed part of the code.
	//in this class we mainly show user 3 options admin login, customer login, sign up
	//three buttons directly take users to the LoginPage frame and wants user to enter phone number and password.
	public  Login() {

		Login = new JFrame();

		Login.getContentPane().setBackground(new Color(250, 128, 114));
		Login.setBounds(300, 90, 900, 600);

		Login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Login.setTitle("LogIn");
		Login.setSize(471, 495);
		Login.getContentPane().setLayout(null);

		rdbtnAdmin = new JCheckBox("Admin");
		rdbtnAdmin.setBounds(136, 112, 139, 29);
		Login.getContentPane().add(rdbtnAdmin);

		rdbtnCustomer = new JCheckBox("Customer");
		rdbtnCustomer.setBounds(136, 168, 152, 29);
		Login.getContentPane().add(rdbtnCustomer);

		btnSignup = new JButton("SignUp");
		btnSignup.setBounds(160, 330, 115, 29);
		Login.getContentPane().add(btnSignup);

		btnLogin = new JButton("OK");
		btnLogin.setBounds(160, 223, 115, 29);
		Login.getContentPane().add(btnLogin);

		JLabel lblNewToOfss = new JLabel("New to OFSS?");
		lblNewToOfss.setBounds(166, 308, 181, 20);
		Login.getContentPane().add(lblNewToOfss);
		Login.setVisible(true);

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnCustomer.isSelected()||rdbtnAdmin.isSelected())
					LoginPage();

				else
					JOptionPane.showMessageDialog(Login, "CHOOSE");}
		});
		//sign up button activates when it is clicked so when user clicks it system opens a new frame from signUp class
		//and sets the visibility of the current class false.
		btnSignup.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				Login.setVisible(false);
				SignUp sn = new SignUp();
				sn.setVisible(true);

			}
		});

		Login.getContentPane().add(btnSignup);


	}
	
	public void LoginPage() {
		Start = new JFrame();
		Start.getContentPane().setBackground(new Color(250, 128, 114));
		Start.setBounds(300, 90, 900, 600);

		Start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Start.setTitle("LogIn");
		Start.setSize(471, 495);
		Start.getContentPane().setLayout(null);

		btnLogn = new JButton("LogIn");
		btnLogn.setBounds(192, 257, 115, 29);
		Start.getContentPane().add(btnLogn);

		btnLogn.addActionListener(new ActionListener() {
			//this part of the code work when the user chooses admin or customer. controls if they entered the phone number and password correctly and 
			//take the user adminMenu frame or Customerframe frame
			public void actionPerformed(ActionEvent arg0) {
				Start.setVisible(false);
				try {	
					String number=textField.getText();
					@SuppressWarnings("deprecation")
					String password=textField_1.getText();
					Management m=new Management();

					if(rdbtnCustomer.isSelected()) {

						boolean entercustomer=m.searchCustomer(number, password);
						
						if(entercustomer==true)
						{	
							Start.setVisible(false);
							Login.setVisible(false);
							Customerframe cf=new Customerframe(m.aList,m.fList,m.cList,m.cList.get(m.findcustomerid(number, password)));
							cf.setVisible(true);
						}
						else {
							textField.setText("");
							textField_1.setText("");
							JOptionPane.showMessageDialog(Start, "Wrong password or phone number");
						}
					}
					if(rdbtnAdmin.isSelected()) {
						boolean enteradmin=m.searchAdmin(number, password);
						if(enteradmin==true && !m.aList.get(m.findAdminid(number, password)).getRestaurant().isShutDown()) {
							Start.setVisible(false);
							//System.out.println(m.aList.get(m.findAdminid(number, password)));
							AdminMenu admin=new AdminMenu(m.aList.get(m.findAdminid(number, password)));
							admin.setVisible(true);

						}
						else {
							textField.setText("");
							textField_1.setText("");
							JOptionPane.showMessageDialog(Start, "Wrong password or phone number");
						}
					}
				} catch (QueueFull | IOException | QueueEmpty e) {
					e.printStackTrace();
				}

			}
		});



		lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(55, 91, 188, 20);
		Start.getContentPane().add(lblPhoneNumber);

		textField = new JTextField();
		textField.setBounds(192, 88, 146, 26);
		Start.getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JPasswordField();
		textField_1.setBounds(192, 148, 146, 26);
		Start.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		lblPassword = new JLabel("Password");
		lblPassword.setBounds(55, 151, 69, 20);
		Start.getContentPane().add(lblPassword);
		Start.setVisible(true);

	}

	public JFrame getStart() {
		return Start;
	}


}
