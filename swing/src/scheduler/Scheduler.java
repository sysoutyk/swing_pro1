package scheduler;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultRowSorter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.ScrollPaneConstants;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


@SuppressWarnings("serial")
public class Scheduler extends JFrame{

			Container con = getContentPane();
			String[] strDays = {"day", "sun","mon", "tue", "wed","thurs","fri", "sat"};
			
			
			//main panel
			JPanel pnlMain = new JPanel(null);
			
			//textField
			JTextField tfTask = new JTextField();
			JTextField tfFrom = new JTextField("00:00am");
			JTextField tfTo = new JTextField("00:00pm");

			//btn
			JButton btnAdd = new JButton("입력");
			JButton btnUpdate = new JButton("수정");
			JButton btnDelete = new JButton("삭제");
			JButton btnMypage = new JButton("마이페이지");
			
			//combo
			JComboBox<String> dayBox = new JComboBox<String>(strDays);
			JComboBox noBox;
			
			//table
			JTable table = new JTable();;
			JPanel pnlShow = new JPanel();
			
			//miscellaneous
			Insets insets;
			SchedularDao dao = SchedularDao.getInstance();
			DefaultTableModel model = new DefaultTableModel();
			SchedulerVo sVo = new SchedulerVo();
			
			//pnlInput
			String strDay = "";
			int pno = 1;
			String task = "";
			String fromTime = "";
			String toTime = "";
			String username = dao.getUsername();
			
		//constructor	
		public Scheduler() {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle("스케줄러");
			setSize(930,500);
			setCombo();
			setTable();
			setUi();
			inputPanel();
			setListeners();
			setResizable(true);
			setVisible(true);
			setLocationRelativeTo(null);
		}
		
		MyListener listeners = new MyListener();
		MyItemListener itemLis = new MyItemListener();
		
	class MyListener implements ActionListener{
			SchedularDao dao = SchedularDao.getInstance();
			String username = dao.getUsername();
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				
				if(obj == btnAdd) {
					addData(username);
				}else if(obj == btnUpdate) {
					updateData(username);
				}else if (obj == btnDelete) {
					deleteData(username);	
				}else if(obj == btnMypage) {
					System.out.println("click");
					Scheduler.this.dispose();
					new Mypage();
				}
			}//action

		
			private void addData(String username) {
				boolean isExist_pno = dao.dup_pno(username, pno, strDay);
				if(isExist_pno == true) {
					JOptionPane.showMessageDialog(con, "이미 사용한 prioriry no입니다. 다른 priority no를 선택해주세요", 
													"알림", JOptionPane.PLAIN_MESSAGE);
				}else {
					task = tfTask.getText();
					fromTime = tfFrom.getText();
					toTime = tfTo.getText();
					SchedulerVo vo = new SchedulerVo(strDay, pno, task, fromTime, toTime, username);
					boolean result = dao.addData(vo);
					if(result) {
						JOptionPane.showMessageDialog(con, "입력성공", "알림", JOptionPane.PLAIN_MESSAGE);
						showData();
					}else {
						JOptionPane.showMessageDialog(con, "입력실패", "알림", JOptionPane.ERROR_MESSAGE);
					}
				}
			}//addData
			private void updateData(String username) {
				task = tfTask.getText();
				fromTime = tfFrom.getText();
				toTime = tfTo.getText();
				SchedulerVo vo = new SchedulerVo(strDay, pno, task, fromTime, toTime, username);
				boolean result = dao.updateData(vo);
				if(result) {
					JOptionPane.showMessageDialog(con, "수정성공", "알림", JOptionPane.PLAIN_MESSAGE);
					showData();
				}else {
					JOptionPane.showMessageDialog(con, "수정실패", "알림", JOptionPane.ERROR_MESSAGE);
					JOptionPane.showMessageDialog(con, "요일과 priority no를 다시 확인해주세요", "알림", JOptionPane.ERROR_MESSAGE);
				}

			}//updateData

			private void deleteData(String username) {
				
				boolean result = dao.deleteData(strDay, pno, username);
					if(result) {
						JOptionPane.showMessageDialog(con, "삭제성공", "알림", JOptionPane.PLAIN_MESSAGE);
						showData();
					}else {
						JOptionPane.showMessageDialog(con, "삭제실패", "알림", JOptionPane.ERROR_MESSAGE);
						JOptionPane.showMessageDialog(con, "요일과 priority no를 다시 확인해주세요", "알림", JOptionPane.ERROR_MESSAGE);
					}
			
			}//deleteData();
			
		}//Class-MyListener
	
		class MyItemListener implements ItemListener{

			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if(e.getSource() == noBox) {
					 pno = noBox.getSelectedIndex()+1;
				}else if(e.getSource()==dayBox) {
					int index = dayBox.getSelectedIndex();
					strDay = strDays[index];
				}
				showData();
			}
		}//class-myItemListener
		public void setCombo() {
			List<Integer> listNum = new ArrayList<>();
			for(int i=1; i<= 20; i++) {
				listNum.add(i);
			}
			noBox = new JComboBox<>(listNum.toArray());
		}

		private void inputPanel() {
			JPanel pnlInput = new JPanel();
			pnlInput.setLayout(null);
			insets = pnlInput.getInsets();
			pnlInput.setBounds(insets.left, insets.top, 914,180);

			//day
			dayBox.setBackground(Color.WHITE);
			dayBox.setBounds(30+insets.left,55+insets.top, 75, 25);
			pnlInput.add(dayBox);
		
			//priority no
			JLabel lbNo = new JLabel("priority no");
			lbNo.setFont(new Font("SansSerif", Font.BOLD,15));
			lbNo.setBounds(150+insets.left,25+insets.top, 85, 25);
			pnlInput.add(lbNo);
			
			noBox.setBackground(Color.WHITE);
			noBox.setBounds(170+insets.left,55+insets.top, 50, 25);
			pnlInput.add(noBox);
			
			//2.from
			JLabel lbFrom = new JLabel("from");
			lbFrom.setFont(new Font("SansSerif", Font.BOLD,15));
			lbFrom.setBounds(670+insets.left,25+insets.top, 85, 25);
			pnlInput.add(lbFrom);
			

			tfFrom.setBounds(650+insets.left, 55+insets.top, 75,25);
			pnlInput.add(tfFrom);	
			
			//4.to
			JLabel lbTo = new JLabel("to");
			lbTo.setFont(new Font("SansSerif", Font.BOLD,15));
			lbTo.setBounds(820+insets.left,25+insets.top, 85, 25);
			pnlInput.add(lbTo);
			

			tfTo.setBounds(800+insets.left, 55+insets.top, 75,25);
			pnlInput.add(tfTo);	
			
			
			//5.task
			JLabel lbTask = new JLabel("task");
			lbTask.setFont(new Font("SansSerif", Font.BOLD,15));
		   lbTask.setBounds(400+insets.left, 25+insets.top, 85,25);
		    pnlInput.add(lbTask);
		   tfTask.setBounds(300+insets.left, 55+insets.top, 250,25);
			pnlInput.add(tfTask);	
			
			//7.btn
			btnAdd.setBounds(300+insets.left, 120+insets.top, 80,25);
			btnUpdate.setBounds(400+insets.left, 120+insets.top, 80,25);
			btnDelete.setBounds(500+insets.left, 120+insets.top, 80,25);
			btnMypage.setBounds(800+insets.left, 150+insets.top, 110,25);
			btnUpdate.setBackground(Color.WHITE);
			btnDelete.setBackground(Color.WHITE);
			btnMypage.setBackground(Color.WHITE);
			btnAdd.setBackground(Color.WHITE);
			pnlInput.add(btnAdd);
			pnlInput.add(btnUpdate);
			pnlInput.add(btnDelete);
			pnlInput.add(btnMypage);
			pnlInput.setBackground(Color.PINK);
			
			
			//알림
			JLabel lbupdate = new JLabel("참고: 수정/삭제할 경우, 요일과 priority no을 기준점으로 시행합니다.");
			lbupdate.setFont(new Font("SansSerif", Font.BOLD,12));
			lbupdate.setBounds(240+insets.left,160+insets.top, 500, 25);
			pnlInput.add(lbupdate);
			
			pnlMain.add(pnlInput);
		}
		
		private void setTable() {
			
			String[] columnNames = {"day", "priority no", "task", "from", "to"};
			model.setColumnIdentifiers(columnNames);
			table.setModel(model);
		
			//column
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(0).setPreferredWidth(5);
			columnModel.getColumn(0).setCellRenderer(dtcr);
			columnModel.getColumn(1).setPreferredWidth(5);
			columnModel.getColumn(1).setCellRenderer(dtcr);
			columnModel.getColumn(2).setPreferredWidth(210);
			columnModel.getColumn(2).setCellRenderer(dtcr);
			columnModel.getColumn(3).setPreferredWidth(30);
			columnModel.getColumn(3).setCellRenderer(dtcr);
			columnModel.getColumn(4).setPreferredWidth(30);
			columnModel.getColumn(4).setCellRenderer(dtcr);
			
			//row
			table.setRowHeight(30);
			TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
			table.setAutoCreateRowSorter(true);
			sorter.setSortable(0, false);
			sorter.setSortable(2, false);
			sorter.setSortable(3, false);
			sorter.setSortable(4, false);
			table.setEnabled(false);
			
			//pnlShow
			table.setBackground(Color.WHITE);
			JScrollPane pane = new JScrollPane(table);
		    pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    pane.setWheelScrollingEnabled(true);
		    pane.setPreferredSize(new Dimension(900,250));
			insets = pnlShow.getInsets();
			pnlShow.setBounds(insets.left, 180+insets.top, 914, 734);
			new BorderLayout();
			pnlShow.add(pane, BorderLayout.CENTER);
			pnlMain.add(pnlShow);
			
		}//showTable
		
		public void showData() {
			List<SchedulerVo> lists = dao.searchData(strDay, username);
			model.setRowCount(0);
			for(SchedulerVo v:lists) {
				String day = v.getDay();
				int pno = v.getPno();
				String task = v.getTask();
				String fromTime = v.getFromTime();
				String toTime = v.getToTime();
				model.insertRow(0, new Object[] {day, pno, task, fromTime, toTime});
			}
			model.fireTableDataChanged();
			table.setModel(model);
		}//showData

		
		private void setListeners() {
			btnAdd.addActionListener(listeners);
			btnUpdate.addActionListener(listeners);
			btnDelete.addActionListener(listeners);
			btnMypage.addActionListener(listeners);
			tfTask.addActionListener(listeners);
		
			//combo hour:min
			noBox.addItemListener(itemLis);
			dayBox.addItemListener(itemLis);
				
			}//setLisetners
		
		private void setUi() {
			pnlMain.setLayout(null);
			con.add(pnlMain);
		}//setUi
}
