package scheduler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class SignUp extends JFrame{
		Container con = getContentPane();
		JTextField tfId = new JTextField(10);
		JPasswordField tfPw = new JPasswordField(10);
		
		JTextField tfEmail = new JTextField(10);
		JButton btnCancel= new JButton("취소");
		JButton btnFinish = new JButton("회원가입 완료");
		MyListener listener = new MyListener();
		SchedularDao dao = SchedularDao.getInstance();
	public SignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Sign up");
		setSize(500, 280);
		setUi();
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	class MyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
		
			if(obj == btnCancel) {
				SignUp.this.dispose();
			}else if(obj == btnFinish) {
				String username = tfId.getText();
				String password = tfPw.getText();
				String email = tfEmail.getText();
				MemberVo mv = new MemberVo(username, password, email, null);
				//System.out.println("mv: " + mv);
				if(username == null || username.equals("")||
					password == null || password.equals("")||
					email == null || email.equals("")) {
					JOptionPane.showMessageDialog(con, "빈칸없이 입력해주세요", "알림", JOptionPane.PLAIN_MESSAGE);
				}
				else {
					//아이디 중복체크
					boolean check_id = dao.dup_id(username);
					if(check_id == true) {
						JOptionPane.showMessageDialog(con, "이미 사용중인 아이디입니다.", "알림", JOptionPane.PLAIN_MESSAGE);
						tfId.setBackground(Color.LIGHT_GRAY);
						tfId.setText("");
					}else if(check_id == false) {
						
						//이메일 중복체크
						boolean check_email = dao.dup_email(email);
						if(check_email == true) {
							JOptionPane.showMessageDialog(con, "이미 사용중인 이메일입니다.", "알림", JOptionPane.PLAIN_MESSAGE);
							tfEmail.setBackground(Color.LIGHT_GRAY);
							tfEmail.setText("");
						}else if(check_email == false) {
							
							//회원가입
							boolean result = dao.addMember(mv);
							if(result) {
									JOptionPane.showMessageDialog(con, "가입성공", "알림", JOptionPane.PLAIN_MESSAGE);
									SignUp.this.dispose();
									new LogInFrame();
								}else {
									JOptionPane.showMessageDialog(con, "가입실패", "알림", JOptionPane.PLAIN_MESSAGE);
								}
						}//else if(check_email)
					}
				}//else
				
			}//btnFinish
		}
		
	}//MyListener
	

	private void setUi() {
		JPanel pnlNorth = new JPanel();
		JLabel logIn = new JLabel("Sign up\n");
		logIn.setFont(new Font("SansSerif", Font.BOLD, 50));
		pnlNorth.add(logIn);
		con.add(pnlNorth, BorderLayout.NORTH);
		
		//center
		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(new GridLayout(3,2,0,10));
		
	
		JLabel idLabel = new JLabel("username");
		JLabel pwLabel = new JLabel("password ");
		JLabel emailLb = new JLabel("email");
		
		//setFont
		idLabel.setFont(new Font("SansSerif", Font.BOLD,15));
		pwLabel.setFont(new Font("SansSerif", Font.BOLD,15));
		emailLb.setFont(new Font("SansSerif", Font.BOLD,15));
		
		//setAlignment
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pwLabel.setHorizontalAlignment(SwingConstants.CENTER);
		emailLb.setHorizontalAlignment(SwingConstants.CENTER);
		
		//add 
		pnlCenter.add(idLabel);
		pnlCenter.add(tfId);
		pnlCenter.add(pwLabel);
		pnlCenter.add(tfPw);
		pnlCenter.add(emailLb);
		pnlCenter.add(tfEmail);
		
		//set Center
		pnlCenter.setBackground(Color.pink);
		con.add(pnlCenter, BorderLayout.CENTER);
		
		//set South
		JPanel pnlSouth = new JPanel();
		btnCancel.addActionListener(listener);
		btnFinish.addActionListener(listener);
		btnCancel.setBackground(Color.WHITE);
		btnFinish.setBackground(Color.WHITE);
		pnlSouth.add(btnCancel);
		pnlSouth.add(btnFinish);
		con.add(pnlSouth, BorderLayout.SOUTH);
	}
	
}
