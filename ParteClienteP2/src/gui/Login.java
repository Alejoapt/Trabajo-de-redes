package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;


@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener{
	
	private JTextField textoLogin;
	
	private JLabel entra;
	
	private Principal prin;
	
	private JButton ingresar;
	
	private Banner banner;
	
	public Login() {
		textoLogin = new JTextField();
		entra = new JLabel("Nick", JLabel.CENTER);
//		Font auxiliar = entra.getFont();
		entra.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 25));
		
		prin = new Principal();
		banner = new Banner();
		ingresar = new JButton("Continuar");
		ingresar.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 18));
		ingresar.addActionListener(this);
		ingresar.setActionCommand("ahora");
		
		JPanel temp = new JPanel();
		
		temp.setLayout(new GridLayout(1,2));
		
		temp.add(entra);
		temp.add(textoLogin);
		setLayout(new BorderLayout());
		add(banner, BorderLayout.NORTH);
		add(temp, BorderLayout.CENTER);
		add(ingresar, BorderLayout.SOUTH);
		setResizable(false);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("ahora")) {
			try {
				if(!textoLogin.getText().equals("")) {
					prin.activarConexion(textoLogin.getText());
					setVisible(false);
					DataInputStream temp = prin.getMundo().getSocket();
					String linea = "";
					while(linea.equals("")){
						linea = temp.readUTF();
					}
					System.out.println(linea);
					prin.getMundo().inicialHilo();
					prin.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(this, "Ingrese un userName");
				}
			} catch (Exception e) {
				
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			Login log = new Login();
			log.setVisible(true);
		} catch (Exception e) {
			
		}
		
	}
}
