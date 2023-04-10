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


public class Mypage extends JFrame{
		Container con = getContentPane();
		JTextField tfId = new JTextField(10);
		JPasswordField tfPw = new JPasswordField(10);
		JTextField tfEmail = new JTextField(10);
		JTextField tfDate = new JTextField(10);
		JButton btnExit= new JButton("회원탈퇴");
		JButton btnFinish = new JButton("수정 완료");
		MyListener listener = new MyListener();
		SchedularDao dao = SchedularDao.getInstance();
		String username = dao.getUsername();
		
		
	public Mypage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("마이페이지");
		setSize(500, 280);
		setData(username);
		setUi();
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	class MyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if(obj == btnExit) {
				int res = JOptionPane.showInternalConfirmDialog(con, "회원탈퇴 진행을 허가 하시겠습니까?","알림", JOptionPane.YES_NO_OPTION);
				if(res == JOptionPane.YES_OPTION) {
					boolean result = dao.deleteMember(username);
					if(result == true) {
						JOptionPane.showMessageDialog(con, "회원탈퇴가 성공적으로 처리되었습니다.", "알림", JOptionPane.PLAIN_MESSAGE);
						Mypage.this.dispose();
						new LogInFrame();
					}
				}else if(res == JOptionPane.NO_OPTION ) {
					new Scheduler();
				}
				
			}else if(obj == btnFinish) {
				String password = tfPw.getText();
				String email = tfEmail.getText();
				MemberVo mv = new MemberVo(username, password, email, null);
				if(password == null || password.equals("")||
					email == null || email.equals("")) {
					JOptionPane.showMessageDialog(con, "빈칸없이 입력해주세요", "알림", JOptionPane.PLAIN_MESSAGE);
				}
				else {
					//수정
					boolean result = dao.updateMemberInfo(mv);
					if(result) {
							JOptionPane.showMessageDialog(con, "수정성공", "알림", JOptionPane.PLAIN_MESSAGE);
							Mypage.this.dispose();
							new Scheduler();
					}else {
						JOptionPane.showMessageDialog(con, "수정실패", "알림", JOptionPane.PLAIN_MESSAGE);
					}
				}//else
				
			}//btnFinish
		}
		
	}//MyListener
	

	private void setUi() {
		JPanel pnlNorth = new JPanel();
		JLabel logIn = new JLabel("마이 페이지\n");
		logIn.setFont(new Font("SansSerif", Font.BOLD, 50));
		pnlNorth.add(logIn);
		con.add(pnlNorth, BorderLayout.NORTH);
		
		//center
		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(new GridLayout(4,2,0,10));
		
	
		JLabel idLabel = new JLabel("username");
		JLabel pwLabel = new JLabel("password ");
		JLabel emailLb = new JLabel("email");
		JLabel tfDateLb = new JLabel("joindate");
		
		//setFont
		idLabel.setFont(new Font("SansSerif", Font.BOLD,15));
		pwLabel.setFont(new Font("SansSerif", Font.BOLD,15));
		emailLb.setFont(new Font("SansSerif", Font.BOLD,15));
		tfDateLb.setFont(new Font("SansSerif", Font.BOLD,15));
		
		//setAlignment
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pwLabel.setHorizontalAlignment(SwingConstants.CENTER);
		emailLb.setHorizontalAlignment(SwingConstants.CENTER);
		tfDateLb.setHorizontalAlignment(SwingConstants.CENTER);
		
		//add 
		pnlCenter.add(idLabel);
		pnlCenter.add(tfId);
		pnlCenter.add(pwLabel);
		pnlCenter.add(tfPw);
		pnlCenter.add(emailLb);
		pnlCenter.add(tfEmail);
		pnlCenter.add(tfDateLb);
		pnlCenter.add(tfDate);
		
		//set Center
		pnlCenter.setBackground(Color.pink);
		con.add(pnlCenter, BorderLayout.CENTER);
		
		//set South
		JPanel pnlSouth = new JPanel();
		btnExit.addActionListener(listener);
		btnFinish.addActionListener(listener);
		btnExit.setBackground(Color.WHITE);
		btnFinish.setBackground(Color.WHITE);
		pnlSouth.add(btnExit);
		pnlSouth.add(btnFinish);
		con.add(pnlSouth, BorderLayout.SOUTH);
	}
	 private void setData(String username) {
		 MemberVo vo = dao.getList(username);
		 tfId.setText(vo.getUsername());
		 tfId.setEditable(false);
		 tfPw.setText(vo.getPassword());
		 tfEmail.setText(vo.getEmail());
		 tfDate.setText(vo.getJoindate().toString());
		 tfDate.setEditable(false);
		 
	 }
	
}
