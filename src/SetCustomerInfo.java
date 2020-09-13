import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class SetCustomerInfo extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JTextField street;
	private JTextField town;
	private JTextField city;
	private JTextField surname;
	private JTextField description;
	private JTextField cCode;
	private JTextField number;
	private JLabel lblName;
	private JLabel lblSurname;
	private JLabel lblStreetName;
	private JLabel lblTown;
	private JLabel lblCity;
	private JLabel lblDescription;
	private JLabel lblCountryCode;
	private JLabel lblPhoneNumber;
	private JButton btnSave;
	private JTextField currentPassword;
	private JLabel lblCurrentPassword;
	private JPasswordField passwordField;
	private JLabel label_1;
	private JPasswordField passwordField_1;
	private JLabel label_2;
	private JSeparator separator;
	private JSeparator separator_1;

	//the classes that has frames inside it designed with WindowBuilder Editor so we mostly edited the action performed part of the code.
	//this class mainly used by customerframes class. it is called because the customer wanted to change his/her information.
	//the constructor of this class only takes a customer object because we are changing her/his information.
	public SetCustomerInfo(Customer c) {

		setTitle("Set Customer Info");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 667, 426);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(250, 128, 114));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Set Customer Info ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(12, 13, 625, 353);
		contentPane.add(panel);
		panel.setLayout(null);

		cCode = new JTextField();
		cCode.setBounds(70, 149, 116, 22);
		panel.add(cCode);
		cCode.setColumns(10);

		lblCountryCode = new JLabel("Country Code");
		lblCountryCode.setBounds(70, 131, 89, 16);
		panel.add(lblCountryCode);

		street = new JTextField();
		street.setBounds(70, 101, 116, 22);
		panel.add(street);
		street.setColumns(10);

		lblStreetName = new JLabel("Street");
		lblStreetName.setBounds(70, 84, 89, 16);
		panel.add(lblStreetName);

		number = new JTextField();
		number.setBounds(198, 149, 116, 22);
		panel.add(number);
		number.setColumns(10);

		lblPhoneNumber = new JLabel("Phone Number");
		lblPhoneNumber.setBounds(198, 131, 94, 16);
		panel.add(lblPhoneNumber);

		town = new JTextField();
		town.setBounds(198, 101, 116, 22);
		panel.add(town);
		town.setColumns(10);

		lblTown = new JLabel("Town");
		lblTown.setBounds(198, 84, 56, 16);
		panel.add(lblTown);

		city = new JTextField();
		city.setBounds(326, 101, 116, 22);
		panel.add(city);
		city.setColumns(10);

		lblCity = new JLabel("City");
		lblCity.setBounds(326, 84, 56, 16);
		panel.add(lblCity);

		description = new JTextField();
		description.setBounds(454, 101, 116, 22);
		panel.add(description);
		description.setColumns(10);

		lblDescription = new JLabel("Description");
		lblDescription.setBounds(454, 84, 77, 16);
		panel.add(lblDescription);

		name = new JTextField();
		surname = new JTextField();
		passwordField = new JPasswordField();
		passwordField_1 = new JPasswordField();

		name.setBounds(70, 55, 116, 22);
		panel.add(name);
		name.setColumns(10);
		name.setText(c.getName());

		lblName = new JLabel("Name");
		lblName.setBounds(70, 38, 33, 16);
		panel.add(lblName);


		surname.setBounds(198, 55, 116, 22);
		panel.add(surname);
		surname.setColumns(10);

		lblSurname = new JLabel("Surname");
		lblSurname.setBounds(198, 38, 56, 16);
		panel.add(lblSurname);


		passwordField.setBounds(213, 211, 121, 22);
		panel.add(passwordField);

		label_1 = new JLabel("New Password");
		label_1.setBounds(213, 194, 84, 16);
		panel.add(label_1);


		passwordField_1.setBounds(213, 254, 121, 22);
		panel.add(passwordField_1);

		label_2 = new JLabel("Type Again");
		label_2.setBounds(213, 235, 84, 16);
		panel.add(label_2);

		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(199, 191, 152, 98);
		panel.add(separator);

		currentPassword = new JPasswordField();
		currentPassword.setColumns(10);
		currentPassword.setBounds(70, 211, 116, 22);
		panel.add(currentPassword);

		lblCurrentPassword = new JLabel("Current Password");
		lblCurrentPassword.setBounds(70, 194, 102, 16);
		panel.add(lblCurrentPassword);

		separator_1 = new JSeparator();
		separator_1.setBounds(70, 185, 265, 117);
		panel.add(separator_1);

		btnSave = new JButton("Save");
		//at the beginning of the frame we want to show the customer's the information s/he gave us before so we are setting the text fields here.
		name.setText(c.getName());surname.setText(c.getSurname());cCode.setText(c.getPhone().getCountry_code());number.setText(c.getPhone().getNumber());
		street.setText(c.getAddress().getStreetName());town.setText(c.getAddress().getTown());city.setText(c.getAddress().getCity());description.setText(c.getAddress().getDescription());

		//at this part of the code the changes that the customer make is recording to the files.
		btnSave.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(passwordField.getText().equals("")&&passwordField_1.getText().equals("")&&currentPassword.getText().equals(c.getPassword())) {//if the password hasn't changed.
					//customer's information setting part.
					c.setUser(name.getText(), surname.getText(), new Address(street.getText(),town.getText(),city.getText(),description.getText()), 
							new Phone(cCode.getText(),number.getText()), c.getPassword());
					try {//saving customer's data into the text file
						Management m = new Management();
						m.selectfile(c.getID_customer()+","+name.getText()+","+surname.getText()+","+	street.getText()+","+town.getText()+","+city.getText()+","+description.getText()+","+ 
								cCode.getText()+","+number.getText()+","+c.getPassword(),2);

					} catch (QueueFull | IOException | QueueEmpty e1) {
						e1.printStackTrace();
					}
					//showing user a information box to inform.
					JOptionPane.showMessageDialog(getContentPane(),	"Your information has been changed.");
					setVisible(false);
					try {//opening customer menu frame again because we are done in here.
						Management m = new Management();
						Customerframe frame=new Customerframe(m.aList, m.fList, m.cList, c);
						frame.setVisible(true);
					} catch (QueueEmpty | QueueFull | IOException e1) {e1.printStackTrace();}
				}
				else {//at this part the program understands that the user wants to change his/her password too. so the system makes its changes accordingly
					if(currentPassword.getText().equals(c.getPassword())) {//if old password entered correctly
						if(passwordField.getText().equals(passwordField_1.getText())) {//if the new passwords correct

							try {//saving customer's data into the text file
								Management m = new Management();
								m.selectfile(c.getID_customer()+","+name.getText()+","+surname.getText()+","+	street.getText()+","+town.getText()+","+city.getText()+","+description.getText()+","+ 
										cCode.getText()+","+number.getText()+","+passwordField.getText(),2);
							} catch (QueueFull | IOException | QueueEmpty e1) {
								e1.printStackTrace();
							}
							setVisible(false);//closing current frame because we are done here for now.
							try {
								//opening customer menu frame again
								Management m = new Management();
								Customerframe frame=new Customerframe(m.aList, m.fList, m.cList, c);
								frame.setVisible(true);
							} catch (QueueEmpty | QueueFull | IOException e1) {e1.printStackTrace();}
							//setting customer's information with the password
							c.setUser(name.getText(), surname.getText(), new Address(street.getText(),town.getText(),city.getText(),description.getText()), 
									new Phone(cCode.getText(),number.getText()), passwordField.getText());
							JOptionPane.showMessageDialog(getContentPane(),	"Your information and password has been changed.");
						}
						else {
							//if the new passwords entered differently a message box shows up to the customer and wants him/her to re enter them again.
							//before reentering the system clears the password fields.
							JOptionPane.showMessageDialog(getContentPane(),	"Something went wrong. You entered different passwords.");
							currentPassword.setText("");passwordField.setText("");passwordField_1.setText("");
						}
					}
					else {//if old password entered wrong.
						JOptionPane.showMessageDialog(getContentPane(),	"Something went wrong. You should enter correct password.");
						currentPassword.setText("");passwordField.setText("");passwordField_1.setText("");
					}
				}
			}
		});
		btnSave.setBounds(264, 315, 97, 25);
		panel.add(btnSave);

	}

}
