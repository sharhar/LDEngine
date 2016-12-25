package game.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import game.GameClient;

import java.awt.List;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameSelect extends JFrame {
	private static final long serialVersionUID = 2838753828074383045L;
	private JPanel contentPane;
	
	public int selection = -1;
	
	public GameSelect(GameClient client) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 448, 413);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		List list = new List();
		list.setBounds(10, 10, 412, 222);
		for(String s: client.serverNames) {
			list.add(s);
		}
		contentPane.add(list);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.serverIndex = list.getSelectedIndex();
				dispose();
			}
		});
		btnConnect.setBounds(171, 306, 89, 23);
		contentPane.add(btnConnect);
		setVisible(true);
	}
}
