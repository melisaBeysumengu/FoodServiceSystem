import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.io.IOException;
import javax.swing.JMenuBar;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AdminMenu extends JFrame {
	private JTable customerTable;
	private JTable foodMenu;
	private JTextField fName;
	private JTextField ingredients;
	private JTextField price;
	
	//the classes that has frames inside it designed with WindowBuilder Editor so we mostly edited the action performed part of the code.
	//This is the mainly frame that we show to the admin when s/he enter the system correctly.
	//In this frame we are showing admin 3 panels(Menu, Customers in Line, Add new ood to the Menu) 
	//All of the three panels and its activities (such as remove food, add food, remove customer in line, display menu, display customers in line) implemented in this class. 
	//and we are showing 1 menu bar which includes set User info, Set Restaurant info, Log out, shut down restaurant.
	//some of the admin's menu bar activities(setAdmin info,set restaurant info) implemented in different class called SetAdminInfo. 

	public AdminMenu(Admin a) throws QueueEmpty, QueueFull {
		setResizable(false);
		setTitle("ONLINE FOOD SYSTEM");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 865, 488);
		getContentPane().setBackground(new Color(250, 128, 114));
		getContentPane().setLayout(null);

		JPanel customer = new JPanel();
		customer.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Customers in Line", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		customer.setBounds(28, 55, 809, 194);
		getContentPane().add(customer);
		customer.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 18, 791, 133);
		customer.add(scrollPane);


		/*String [][] cQueue = new String[a.getRestaurant().getCustomerqueue().size()][4];
		for (int i = 0; i < cQueue.length; i++) {
			if(a.getRestaurant().getCustomerqueue().size()>i) {
				cQueue[i]=((Customer)a.getRestaurant().getCustomerqueue().peek()).getCurrentOrderedFood(a.getRestaurant()).split("-");
			}
		}*/
		
		//2D array implemented for the JTable implementation. We are taking the admin's restaurant's customer Queue attribute.
		//it contains list of Customers in the line with Customer type so that we don't have to create new customer again and again.
		String [][] cQueue = new String[a.getRestaurant().getCustomerqueue().size()][5];
		for (int i = 0; i < cQueue.length; i++) {
			cQueue[i]=((Customer)a.getRestaurant().getCustomerqueue().peek()).getCurrentOrderedFood(a.getRestaurant()).split("-");
			a.getRestaurant().getCustomerqueue().enqueue(a.getRestaurant().getCustomerqueue().dequeue());
		}
		customerTable = new JTable();
		scrollPane.setViewportView(customerTable);
		customerTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		customerTable.setModel(new DefaultTableModel(cQueue,new String[] {"Order Name","Ingredients","Price", "Name", "Surname", "Phone", "Address"}));
	
		//remove customer button works when it is clicked. It doesn't directly remove the customer firstly program asks the admin if s/he really wants to
		//remove the customer from the line and right after s/he presses yes button system  updates the Orders.txt file, removes the customer from the queue
		//and removes the row 0 from the customer in line JTable.
		JButton btnRemoveCustomer = new JButton("Remove Customer");
		btnRemoveCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) customerTable.getModel();
				int response = JOptionPane.showConfirmDialog(getContentPane(), "Do you want to remove customer?", "Confirm?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(response == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(getContentPane(),	"Customer has been removed.");
					try {
						Management m =new Management();
						//updating the Orders.txt file
						m.fileUpdate("Orders.txt",  m.findAdminid(a.getPhone().getNumber(),a.getPassword())+";"
								+m.findcustomerid(((Customer)a.getRestaurant().getCustomerqueue().peek()).getPhone().getNumber(), ((Customer)a.getRestaurant().getCustomerqueue().peek()).getPassword())+";"+
								((Customer)a.getRestaurant().getCustomerqueue().peek()).getOrderedFood(a.getRestaurant())+";"+true	);
						//removing customer from the queue
						a.getRestaurant().getCustomerqueue().dequeue();

					} catch (QueueEmpty | IOException | QueueFull e1) {
						e1.printStackTrace();
					}
					//updating the JTable using removeRow method
					model.removeRow(0);
				}
			}
		});
		btnRemoveCustomer.setBounds(305, 156, 144, 25);
		customer.add(btnRemoveCustomer);

		JPanel food = new JPanel();
		food.setBorder(new TitledBorder(null, "Menu", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		food.setBounds(366, 262, 471, 172);
		getContentPane().add(food);
		food.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 13, 449, 117);
		food.add(scrollPane_1);

		foodMenu = new JTable();
		scrollPane_1.setViewportView(foodMenu);
		foodMenu.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//foodMenu table shows the FoodList that the restaurant has.
		//it works same as customerqueue.
		String [][] aFood = new String[a.getRestaurant().getFood().size()][4];
		for (int i = 0; i < aFood.length; i++) {
			if(a.getRestaurant().getFood().size()>i)
				aFood[i]=a.getRestaurant().getFood().get(i).toString().split("-");
		}
		foodMenu.setModel(new DefaultTableModel(aFood,new String[] {"Food Name", "Ingredients", "Price"}));
		
		
		//Remove food button helps us to remove desired food to delet from the menu.
		//To accomplish this task, the system wants customer to select a row firstly. if the admin does not select a row, a message box pops up and
		//says to the admin that s/he has to choose a row before deleting. after user chooses a row and clicks the "remove food from menu button" 
		//an information box shown to the user and says "x is removed." After that Menu JTable updates it rows, the food's isDeleted attribute changes to true,
		//Food.txt file update starts and system removes the food from admin's food array list.
		//
		JButton btnNewButton = new JButton("Remove Food From Menu");
		btnNewButton.setBounds(130, 143, 206, 25);
		food.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) foodMenu.getModel();

				try {
					int selectedRow=0;
					//taking the selected row from the table
					selectedRow = foodMenu.getSelectedRow();
					//showing a message box
					JOptionPane.showMessageDialog(getContentPane(),	model.getValueAt(selectedRow, 0)+" is removed.");
					//removing the food from JTable
					model.removeRow(selectedRow);
					Management m = new Management();
					//setting food's isDeleted attribute to true
					a.getRestaurant().getFood().get(selectedRow).setDeleted(true);
					//updating Food.txt
					m.fileUpdate("Food.txt", m.findAdminid(a.getPhone().getNumber(),a.getPassword())+"-"+a.getRestaurant().getFood().get(selectedRow).toString().replace("[", "").replace("]", "").substring(0,a.getRestaurant().getFood().get(selectedRow).toString().replace("[", "").replace("]", "").lastIndexOf("-") )
							+"-"+a.getRestaurant().getFood().get(selectedRow).isDeleted());
					//removing food from the food list.
					a.getRestaurant().removeFood(a.getRestaurant().getFood().get(selectedRow));
				} catch (Exception e1) {
					//if the user hasn't choose a row a message box is shown to the user
					JOptionPane.showMessageDialog(getContentPane(),	"You should select a row to delete.");
				}
			}
		});
		foodMenu.getColumnModel().getColumn(2).setPreferredWidth(50);

		JPanel addFood = new JPanel();
		addFood.setBorder(new TitledBorder(null, "Add New Food to the Menu", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addFood.setBounds(28, 262, 313, 172);
		getContentPane().add(addFood);
		addFood.setLayout(null);

		fName = new JTextField();
		fName.setBounds(38, 49, 116, 22);
		addFood.add(fName);
		fName.setColumns(10);

		ingredients = new JTextField();
		ingredients.setBounds(38, 94, 244, 22);
		addFood.add(ingredients);
		ingredients.setColumns(10);

		price = new JTextField();
		price.setBounds(166, 49, 116, 22);
		addFood.add(price);
		price.setColumns(10);

		JLabel lblFoodName = new JLabel("Food Name");
		lblFoodName.setBounds(38, 34, 74, 16);
		addFood.add(lblFoodName);

		JLabel lblIngredients = new JLabel("Ingredients");
		lblIngredients.setBounds(38, 77, 74, 16);
		addFood.add(lblIngredients);

		JLabel lblPrice = new JLabel("Price");
		lblPrice.setBounds(166, 34, 56, 16);
		addFood.add(lblPrice);
		
		
		//At this part of the code user can add food to his/her restaurant. after clicking the "OK" button the program shows a message box if the user did not
		//fill all of the empty fields in the Add Food panel. After filling it the program firstly adds the food to the restaurant's food list then updates the 
		//JTable with the new food. After this tasks done system informs the admin about the food s/he added and updates the Food.txt file.
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fName.getText().equals("")||ingredients.getText().equals("")||price.getText().equals("")) {
					//the message box is gives info about filling all the text fields
					JOptionPane.showMessageDialog(getContentPane(),	"You should fill all fields to add food.");
				}
				else {
					//adding food to the food array list
					a.getRestaurant().getFood().add(new Food(fName.getText(), ingredients.getText(), Integer.parseInt(price.getText()), a.getRestaurant()));
					DefaultTableModel model = (DefaultTableModel) foodMenu.getModel();
					//updating JTable with new food
					model.addRow(new Object[]{fName.getText(), ingredients.getText(), price.getText()});
					//informing admin about the food.
					JOptionPane.showMessageDialog(getContentPane(),	fName.getText()+ " is added into your menu.");
					try {
						//updating the Food.txt with the new food
						Management m = new Management();
						m.selectfile(m.findAdminid(a.getPhone().getNumber(),a.getPassword())+"-"+fName.getText()+"-"+ingredients.getText()+"-"+Integer.parseInt(price.getText())+"-"+false, 4);
					} catch (QueueFull |IOException |QueueEmpty e1) {
						e1.printStackTrace();
					} 
					//clearing the add food part of the fields
					fName.setText("");ingredients.setText("");price.setText("");

				}
			}
		});
		btnOk.setBounds(108, 120, 97, 25);
		addFood.add(btnOk);
		//After pressing the "USER SETTINGS" button 4 options shown to the user.
		//Menu bar actions starts from here shut down restaurant and log out implemented here but the set user info and set restaurant info part implemented in
		//different class called SetAdminInfo. 
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.ORANGE);
		menuBar.setBounds(0, 0, 199, 26);
		getContentPane().add(menuBar);

		JMenu mnUser = new JMenu("USER SETTINGS");
		mnUser.setIcon(new ImageIcon(AdminMenu.class.getResource("/javax/swing/plaf/metal/icons/ocean/menu.gif")));
		mnUser.setForeground(Color.GRAY);
		mnUser.setBackground(Color.LIGHT_GRAY);
		mnUser.setFont(new Font("Californian FB", Font.PLAIN, 23));
		menuBar.add(mnUser);
		//For set user info part the system opens a new frame from SetAdminInfo and if the admin's info will be changed then the system sends a boolean to specify the option.
		JMenuItem SetUserInfo = new JMenuItem("Set User Information");
		SetUserInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SetAdminInfo frame = new SetAdminInfo(a,true);
				setVisible(false);
				frame.setVisible(true);

			}
		});
		SetUserInfo.setIcon(new ImageIcon(AdminMenu.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Background-Color-Black.png")));
		mnUser.add(SetUserInfo);
		
		//For set restaurant info part the system opens a new frame from SetAdminInfo and if the restaurant's info will be changed then the system sends a boolean to specify the option.
		JMenuItem SetRestaurantInformation = new JMenuItem("Set Restaurant Information");
		SetRestaurantInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SetAdminInfo frame = new SetAdminInfo(a,false);
				customerTable.removeAll();
				setVisible(false);
				frame.setVisible(true);
			}
		});
		SetRestaurantInformation.setIcon(new ImageIcon(AdminMenu.class.getResource("/com/sun/javafx/scene/control/skin/modena/HTMLEditor-Background-Color-Black.png")));
		mnUser.add(SetRestaurantInformation);
		
		//If the user presses this button the program wants shows a confirm Box to the user because if the restaurant shut down then the user will not enter
		//the program with his/her password and phone number again. after user clicks the yes button restaurant's isShutDown attribute set to be true and system 
		//shows a information box to the user about the shut down. and finally the last changes is saved in the Restaurant.txt file and the program closed.
		JMenuItem ShutDownRestaurant = new JMenuItem("Shut Down Restaurant");
		ShutDownRestaurant.setIcon(new ImageIcon(AdminMenu.class.getResource("/javax/swing/plaf/metal/icons/ocean/warning.png")));
		ShutDownRestaurant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//showing the user confirm box and take the answer.
				int response = JOptionPane.showConfirmDialog(getContentPane(), "Do you want to delete your account?", "Confirm?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(AdminMenu.class.getResource("/javax/swing/plaf/metal/icons/ocean/warning.png")));
				if(response == JOptionPane.YES_OPTION) {
					//setting restaurant's isShutDown attribute true.
					a.getRestaurant().setShutDown(true);
					//showing user information box about the shut down
					JOptionPane.showMessageDialog(getContentPane(),	"Your restaurant has been shut downed.");
					try {
						//saving the last changes to the Restaurant.txt file.
						Management m = new Management();
						m.selectfile(m.findAdminid(a.getPhone().getNumber(),a.getPassword())+","+a.getRestaurant().getRestaurant_name()+","+	
								a.getRestaurant().getAddress().getStreetName()+","+a.getRestaurant().getAddress().getTown()+","+a.getRestaurant().getAddress().getTown()+","+a.getRestaurant().getAddress().getDescription()+","+ 
								a.getRestaurant().getPhone().getCountry_code()+","+a.getRestaurant().getPhone().getNumber()+","+a.getRestaurant().isShutDown(),3);
					} catch (IOException | QueueFull | QueueEmpty e) {
						
						e.printStackTrace();
					}
					System.exit(0);
				}
			}
		});
		//Log out button helps the user to log out the system. the program just sets the visibility of the current program to false and
		//sets the Login Frame's visible to true.
		JMenuItem BackToMain = new JMenuItem("LogOut");
		BackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				Login l =new Login();
				l.setVisible(true);

			}
		});
		mnUser.add(BackToMain);
		mnUser.add(ShutDownRestaurant);

		JLabel lblNewLabel = new JLabel(a.getRestaurant().getRestaurant_name());
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBackground(Color.ORANGE);
		lblNewLabel.setFont(new Font("Lucida Handwriting", Font.BOLD, 25));
		lblNewLabel.setBounds(431, 13, 378, 35);
		getContentPane().add(lblNewLabel);


	}
}
