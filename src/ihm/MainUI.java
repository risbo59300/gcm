package ihm;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import org.omg.CORBA.PUBLIC_MEMBER;

import gcm.dao.PatientDaoJDBC;
import gcm.model.Patient;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.ImageIcon;
import java.awt.Panel;
import javax.swing.SwingConstants;

public class MainUI extends JFrame {

	/**
	 * 
	 */
	private JPanel contentPane;
	private JTextField textFieldNom;
	private JLabel lblNewLabel_1;
	private JTextField textFieldPrenom;
	private JLabel lblNewLabel_2;
	private JTextField textFieldSecu;
	private JLabel lblNewLabel_3;
	private JTextField textFieldAge;

	private JScrollPane scrollPane;
	
	private JTable table;
	String[] colonnes = { "Nom", "Prénom", "Adresse", "Numéro Sécu", "Age", "Ville" };
	String[][] donnees = { { "LGS", "Farid", "rue de la joie", "101012030454", "26", "Lille" } };
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmNewMenuItem;
	private JMenu mnNewMenu_1;
	private JMenuItem mntmNewMenuItem_1;
	private JMenuItem mntmNewMenuItem_2;
	JPanel panelAjoutPatient;
	JPanel panelConsultation;
	private JToolBar toolBar;
	private JButton buttonShow;
	private JButton buttonAdd;

	static JDialog jdialog;
    JPanel panelAuthentification=new JPanel();
    JLabel lblUsername=new JLabel("Nom d'utilisateur");
    static JTextField txtUsername=new JTextField(10);
    JLabel lblPassword=new JLabel("Mot de passe");
    static JPasswordField txtpwd=new JPasswordField(10);

    static JButton btnLogin=new JButton("Valider");
    static JButton btnReset=new JButton("Annuler");
    private JPanel panelAction;
    private JButton btnUpdate;
    private JButton btnDelete;
    private Panel panelModification;
    private JLabel lblAdresseEdit;
    private JTextField textFieldVilleEdit;
    private JButton btnNewButton_2;
    static PatientDaoJDBC patientDaoJdbc = new PatientDaoJDBC();
    
    JDialog jDialogUpdate;
    
    int nss;
    String ville;
    String adresse;

    public void createDialogAuthentification() {
        panelAuthentification.add(lblUsername);
        panelAuthentification.add(txtUsername);
        panelAuthentification.add(lblPassword);
        panelAuthentification.add(txtpwd);
        panelAuthentification.add(btnLogin);
        panelAuthentification.add(btnReset);
        jdialog.getContentPane().add(panelAuthentification);
        jdialog.setBounds(300, 300, 250, 200);

    }
    //JPanel panelConsultation;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		MainUI frame = new MainUI();
        jdialog=new JDialog(frame, "Authentification");
        frame.createDialogAuthentification();
        jdialog.setVisible(true);
        
        btnLogin.addActionListener(new ActionListener() {
			
        	@Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String user = txtUsername.getText();
                String pwd = txtpwd.getText();

                
                if (patientDaoJdbc.findUsernameAndPassword(user, pwd)==true) {
                	frame.setBounds(300, 300, 400, 400);
                    frame.setVisible(true);
                    jdialog.setVisible(false);
                }else {
                    JOptionPane.showMessageDialog(frame, "Veuillez ressayer encore une fois","Erreur",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainUI frame = new MainUI();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

	/**
	 * Create the frame.
	 */
	public MainUI() {
		setTitle("Gestion Cabinet Médical");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 714, 494);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnNewMenu = new JMenu("Application");
		menuBar.add(mnNewMenu);

		mntmNewMenuItem = new JMenuItem("Quitter");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		mnNewMenu_1 = new JMenu("Patient");
		menuBar.add(mnNewMenu_1);
		
		/*******MENU ITEM 1**********/

		mntmNewMenuItem_1 = new JMenuItem("Ajouter nouveau patient");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Ajouter");
				contentPane.remove(panelConsultation);// Suppr panelConsul de panel principal
				contentPane.add(panelAjoutPatient, BorderLayout.CENTER);
				contentPane.updateUI();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		
		/*******MENU ITEM 2**********/


		mntmNewMenuItem_2 = new JMenuItem("Consulter liste des patients");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Consultation");
				PatientDaoJDBC patientDaoJDBC = new PatientDaoJDBC();
				List<Patient> listePatient = patientDaoJDBC.findAll();
				donnees = new String[listePatient.size()][6];

				for (int i = 0; i < listePatient.size(); i++) {

					Patient p = listePatient.get(i);
					donnees[i][0] = "" + p.getNom();
					donnees[i][1] = "" + p.getPrenom();
					donnees[i][2] = "" + p.getAdresse();
					donnees[i][3] = "" + p.getNss();
					donnees[i][4] = "" + p.getDateNaissance();
					donnees[i][5] = "" + p.getVille();

					// donnees[i][0]=""+listePatient.get(i).getNom();//permet de virer Patient p=...

				}
				table = new JTable(donnees, colonnes);
				scrollPane.setViewportView(table);
				panelConsultation.updateUI();
				contentPane.remove(panelAjoutPatient);// Suppr panelConsul de panel principal
				contentPane.add(panelConsultation, BorderLayout.CENTER);
				contentPane.updateUI();
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panelAjoutPatient = new JPanel();
		panelAjoutPatient.setBorder(
				new TitledBorder(null, "Ajout de Patient", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panelAjoutPatient, BorderLayout.EAST);
		panelAjoutPatient.setLayout(null);

		JLabel lblNewLabel = new JLabel("Nom");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 39, 45, 13);
		panelAjoutPatient.add(lblNewLabel);

		textFieldNom = new JTextField();
		textFieldNom.setBounds(119, 35, 96, 19);
		panelAjoutPatient.add(textFieldNom);
		textFieldNom.setColumns(10);

		lblNewLabel_1 = new JLabel("Prénom");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 68, 75, 13);
		panelAjoutPatient.add(lblNewLabel_1);

		textFieldPrenom = new JTextField();
		textFieldPrenom.setBounds(119, 64, 96, 19);
		panelAjoutPatient.add(textFieldPrenom);
		textFieldPrenom.setColumns(10);

		lblNewLabel_2 = new JLabel("N° sécu");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(10, 97, 87, 13);
		panelAjoutPatient.add(lblNewLabel_2);

		textFieldSecu = new JTextField();
		textFieldSecu.setBounds(119, 93, 96, 19);
		panelAjoutPatient.add(textFieldSecu);
		textFieldSecu.setColumns(10);

		lblNewLabel_3 = new JLabel("Adresse");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(10, 129, 69, 13);
		panelAjoutPatient.add(lblNewLabel_3);

		JTextArea textAreaAdresse = new JTextArea();
		textAreaAdresse.setBounds(119, 122, 337, 22);
		panelAjoutPatient.add(textAreaAdresse);

		JLabel lblNewLabel_4 = new JLabel("Age");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(10, 186, 45, 13);
		panelAjoutPatient.add(lblNewLabel_4);

		textFieldAge = new JTextField();
		textFieldAge.setBounds(119, 182, 96, 19);
		panelAjoutPatient.add(textFieldAge);
		textFieldAge.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("Ville");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_5.setBounds(10, 156, 45, 13);
		panelAjoutPatient.add(lblNewLabel_5);

		JComboBox comboBoxVille = new JComboBox();
		comboBoxVille.addItem("Valenciennes");
		comboBoxVille.addItem("Maubeuge");
		comboBoxVille.addItem("Condé sur Escaut");
		comboBoxVille.addItem("Fresnes sur Escaut");
		comboBoxVille.addItem("Lille");
		comboBoxVille.addItem("Roubaix");
		comboBoxVille.addItem("Paris");
		comboBoxVille.addItem("Saint-Denis");
		comboBoxVille.addItem("Raismes");
		comboBoxVille.setBounds(119, 151, 170, 21);
		panelAjoutPatient.add(comboBoxVille);

		JButton btnNewButton = new JButton("Confirmer");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Patient patient = new Patient();
				String val1 = textFieldSecu.getText();
				int nss = Integer.parseInt(val1);// Convertit le String en entier
				String nom = textFieldNom.getText();
				String prenom = textFieldPrenom.getText();
				String adresse = textAreaAdresse.getText();
				String val2 = textFieldAge.getText();
				int age = Integer.parseInt(val2);
				String ville = comboBoxVille.getSelectedItem().toString();

				patient.setNss(nss);
				patient.setNom(nom);
				patient.setPrenom(prenom);
				patient.setAdresse(adresse);
				//patient.setDateNaissance(new Date());
				patient.setVille(ville);

				System.out.println("Confirmer");
				//PatientDaoJDBC patientDaoJDBC = new PatientDaoJDBC();
				patientDaoJdbc.add(patient);

				JOptionPane.showMessageDialog(MainUI.this, "Ajout réalisé", "Succès", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(119, 211, 129, 40);
		panelAjoutPatient.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Annuler");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setBounds(269, 211, 130, 40);
		panelAjoutPatient.add(btnNewButton_1);

		panelConsultation = new JPanel();
		contentPane.add(panelConsultation, BorderLayout.CENTER);
		panelConsultation.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();

		table = new JTable(donnees, colonnes);
		scrollPane.setViewportView(table);
		panelConsultation.add(scrollPane);

		toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);

		buttonShow = new JButton("Afficher");
		buttonShow.setIcon(new ImageIcon("D:\\projets\\Java\\GCM\\images\\iconfinder_eye_115733.png"));
		buttonShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				System.out.println("Consultation");
				//PatientDaoJDBC patientDaoJDBC = new PatientDaoJDBC();
				List<Patient> listePatient = patientDaoJdbc.findAll();
				donnees = new String[listePatient.size()][6];

				for (int i = 0; i < listePatient.size(); i++) {

					Patient p = listePatient.get(i);
					donnees[i][0] = "" + p.getNom();
					donnees[i][1] = "" + p.getPrenom();
					donnees[i][2] = "" + p.getAdresse();
					donnees[i][3] = "" + p.getNss();
					donnees[i][4] = "" + p.getDateNaissance();
					donnees[i][5] = "" + p.getVille();

				}
				table = new JTable(donnees, colonnes);
				scrollPane.setViewportView(table);
				panelConsultation.updateUI();
				contentPane.remove(panelAjoutPatient);// Suppr panelConsul de panel principal
				contentPane.add(panelConsultation, BorderLayout.CENTER);
				contentPane.updateUI();
			
			}
		});
		toolBar.add(buttonShow);

		buttonAdd = new JButton("Ajouter");
		buttonAdd.setIcon(new ImageIcon("D:\\projets\\Java\\GCM\\images\\iconfinder_add_2199111.png"));
		buttonAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.remove(panelConsultation);// Suppr panelConsul de panel principal
				contentPane.add(panelAjoutPatient, BorderLayout.CENTER);
				contentPane.updateUI();
			}
		});
		toolBar.add(buttonAdd);
		
		panelAction = new JPanel();
		contentPane.add(panelAction, BorderLayout.SOUTH);
		panelModification = new Panel();
		
		btnUpdate = new JButton("Modifier");
		JTextArea textAreaEdit = new JTextArea();
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			int row = table.getSelectedRow();
			System.out.println("row= "+row);
			if(row==-1) {
				JOptionPane.showMessageDialog(null,"Veuillez selectionner un patient", "Attention",JOptionPane.WARNING_MESSAGE);
				
			}
			else
			{
			 adresse = table.getValueAt(row, 2).toString();
			 ville = table.getValueAt(row, 5).toString();
			
			String nsstext=table.getValueAt(row, 3).toString();
            nss=Integer.parseInt(nsstext);
            
			textAreaEdit.setText(adresse);
			textFieldVilleEdit.setText(ville);
			
			jDialogUpdate = new JDialog(MainUI.this, "Modification du patient");
			jDialogUpdate.getContentPane().add(panelModification);
			jDialogUpdate.setBounds(200, 200, 800, 500);
			jDialogUpdate.setVisible(true);
			}
			}
		});
		panelAction.add(btnUpdate);
		
		btnDelete = new JButton("Supprimer");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int row = table.getSelectedRow();
				if(row==-1) {
					JOptionPane.showMessageDialog(null,"Veuillez selectionner un patient", "Attention",JOptionPane.WARNING_MESSAGE);
					
				}
				String value = table.getValueAt(row, 3).toString();
				System.out.println("value="+value);
				PatientDaoJDBC patientDao = new PatientDaoJDBC();
				int nss = Integer.parseInt(value);
				patientDao.delete(nss);
			}
		});
		panelAction.add(btnDelete);
		
		//contentPane.add(panelModification, BorderLayout.CENTER);
		panelModification.setLayout(null);
		
		lblAdresseEdit = new JLabel("Adressel");
		lblAdresseEdit.setBounds(184, 53, 41, 14);
		panelModification.add(lblAdresseEdit);
		
		
		textAreaEdit.setBounds(256, 50, 195, 73);
		panelModification.add(textAreaEdit);
		
		JLabel lblVilleEdit = new JLabel("Ville");
		lblVilleEdit.setBounds(182, 171, 46, 14);
		panelModification.add(lblVilleEdit);
		
		textFieldVilleEdit = new JTextField();
		textFieldVilleEdit.setBounds(258, 169, 193, 20);
		panelModification.add(textFieldVilleEdit);
		textFieldVilleEdit.setColumns(10);
		
		JButton btnValideModification = new JButton("Valider");
		btnValideModification.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Modifier patient"+nss);
				//PatientDaoJDBC patientDaojdbc = new PatientDaoJDBC();
				patientDaoJdbc.update(nss,textFieldVilleEdit.getText(),textAreaEdit.getText());
				jDialogUpdate.setVisible(false);
			}
		});
		btnValideModification.setBounds(256, 235, 89, 23);
		panelModification.add(btnValideModification);
		
		
		btnReset = new JButton("Annuler");
		btnReset.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				resetFields();
				
			}

			private void resetFields() {
				textAreaEdit.setText(" ");
				textFieldVilleEdit.setText("");
			}
		});
		btnReset.setBounds(362, 235, 89, 23);
		panelModification.add(btnReset);
		
		
	}
}
