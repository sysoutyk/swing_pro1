package scheduler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class LogInFrame extends JFrame{
		Container con = getContentPane();
		JTextField tfId = new JTextField(10);
		JPasswordField tfPw = new JPasswordField(10);
		JCheckBox checkBox = new JCheckBox("Show Password");
		JButton btnLogin= new JButton("로그인");
		JButton btnSignup = new JButton("회원가입");
		MyListener listener = new MyListener();
		SchedularDao dao = SchedularDao.getInstance();
		
	public LogInFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("로그인");
		setSize(500, 230);
		setUi();
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		
	}
	class MyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == btnLogin) {
				String username = tfId.getText();
				String password = tfPw.getText();
				if(username == null|| username.equals("")|| password == null || password.equals("")) {
					JOptionPane.showMessageDialog(con, "회원님의 정보가 없습니다. 다시 입력해주세요", "알림", 
							JOptionPane.INFORMATION_MESSAGE);
				}else {
					boolean result = dao.dup_id(username);
					System.out.println("result: " + result);
						if(result) {
							dao.setUsername(username);
							JOptionPane.showMessageDialog(con, "로그인 성공", "알림", 
							JOptionPane.INFORMATION_MESSAGE);
							LogInFrame.this.dispose();
							new Scheduler();
						}else{
							JOptionPane.showMessageDialog(con, "로그인 실패", "알림", 
							JOptionPane.INFORMATION_MESSAGE);
						}
				}//else
			}else if(obj == btnSignup) {
				new SignUp();
				LogInFrame.this.dispose();
			}
		}//action e
		
	}
	private void setUi() {
		JPanel pnlNorth = new JPanel();
		JLabel logIn = new JLabel("Log In\n");
		logIn.setFont(new Font("SansSerif", Font.BOLD, 50));
		pnlNorth.add(logIn);
		con.add(pnlNorth, BorderLayout.NORTH);
		btnLogin.addActionListener(listener);
		btnSignup.addActionListener(listener);
		checkBox.addActionListener(listener);
		
		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(new GridLayout(3,2,0,10));
		
		JLabel idLabel = new JLabel("username: ");
		JLabel pwLabel = new JLabel("password: ");
		idLabel.setFont(new Font("SansSerif", Font.BOLD,15));
		pwLabel.setFont(new Font("SansSerif", Font.BOLD,15));
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pwLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pnlCenter.add(idLabel);
		pnlCenter.add(tfId);
		pnlCenter.add(pwLabel);
		pnlCenter.add(tfPw);
	
		
		btnLogin.setBackground(Color.WHITE);
		btnSignup.setBackground(Color.WHITE);
		pnlCenter.add(btnLogin);
		pnlCenter.add(btnSignup);
	
		pnlCenter.setBackground(Color.pink);
		con.add(pnlCenter, BorderLayout.CENTER);
	}
	
	
	
	public static void main(String[] args) {
		new LogInFrame();
//		new Scheduler();
//		new SignUp();
//		new Mypage();
	}

}
