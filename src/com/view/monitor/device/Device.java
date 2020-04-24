package com.view.monitor.device;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.alibaba.fastjson.JSONObject;
import com.util.CyFont;
import com.util.Layer;
import com.util.Layer.LayerCallback;
import com.util.Layer.LayerConfirmCallback;
import com.util.Layer.LayerLoadingCallback;

public class Device extends JPanel {

	private static final long serialVersionUID = 1L;

	private static JDialog addDeviceDialog = null;
	private static JDialog mngDeviceDialog = null;

	private static DefaultTableModel tableModel;;

	public Device() {
		setLayout(new BorderLayout());
		setBackground(Color.white);
		add(new JLabel("设备"));
		add(action(), BorderLayout.SOUTH);
		add(d_list(), BorderLayout.CENTER);
	}

	private Component d_list() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.add(new JButton("列表"));
		return panel;
	}

	private Component action() {
		JPanel panel = new JPanel();
		/********************* start设备管理 **********************/
		JButton deviceMngBtn = new JButton();
		deviceMngBtn.setText("设备管理");
		deviceMngBtn.setCursor(new Cursor(12));
		deviceMngBtn.setContentAreaFilled(false);
		deviceMngBtn.setFocusPainted(false);
		deviceMngBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Layer.loading(new LayerLoadingCallback() {
					@Override
					public void handle(JDialog dialog) {
						try {
//							ArrayList<JSONObject> rSet = new com.action.Device().select();
//							mngDeviceDialog = Layer.window(deviceMngBtn.getText(), mngDeviceComp(rSet), 815, 600);
//							mngDeviceDialog.setVisible(true);
							dialog.dispose();
							System.out.println(dialog);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		});

		panel.add(deviceMngBtn);
		/********************* end设备管理 **********************/

		/********************* start 添加设备 **********************/

		JButton addDeviceBtn = new JButton();
		addDeviceBtn.setText("添加设备");
		addDeviceBtn.setCursor(new Cursor(12));
		addDeviceBtn.setContentAreaFilled(false);
		addDeviceBtn.setFocusPainted(false);

		addDeviceBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addDeviceDialog = Layer.window("添加设备", addDeviceComp(), 428, 240);
				addDeviceDialog.setVisible(true);
			}
		});

		panel.add(addDeviceBtn);
		/********************* end 添加设备 **********************/

		return panel;
	}

	private JPanel mngDeviceComp(ArrayList<JSONObject> rSet) {
		// 表头（列名）
		Object[] columnNames = { "", "IP地址", "端口", "用户名", "密码" };
		Object[] columnField = { "", "ip", "port", "account", "password" };

		// 表格所有行数据
		Object[][] rowData = {};

		JTable table = new JTable(new DefaultTableModel(rowData, columnNames) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return column == 0;
			}
		});

		DefaultTableCellRenderer tableRender = new DefaultTableCellRenderer();
		tableRender.setHorizontalAlignment(SwingConstants.CENTER);

		table.setFont(CyFont.PuHuiTi(CyFont.Bold, 12));
		table.setForeground(new Color(51, 51, 51));
		table.setGridColor(Color.GRAY);
		table.setRowHeight(26);
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		table.setDefaultRenderer(Object.class, tableRender);

		table.getTableHeader().setFont(CyFont.PuHuiTi(CyFont.Bold, 14));
		table.getTableHeader().setForeground(Color.RED);
		table.getTableHeader().setResizingAllowed(true);
		table.getTableHeader().setReorderingAllowed(true);

		table.getColumnModel().getColumn(0).setMaxWidth(26);
		JCheckBox box = new JCheckBox();
		table.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				box.setSelected(isSelected);
				box.setHorizontalAlignment((int) 0.5f);
				return box;
			}
		});

		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = table.getSelectedRow();
					int col = table.getColumnCount();
					Map<String, Object> data = new HashMap<String, Object>();
					for (int j = 1; j < col; j++) {
						String field = String.valueOf(columnField[j]);
						String value = String.valueOf(table.getValueAt(row, j));
						data.put(field, value);
					}
					System.out.println(row + ":" + data);
				}
			}
		});

		tableModel = (DefaultTableModel) table.getModel();
		for (int i = 0; i < rSet.size(); i++) {
			String ip = rSet.get(i).getString("ip");
			String port = rSet.get(i).getString("port");
			String username = rSet.get(i).getString("username");
			String password = rSet.get(i).getString("password");
			tableModel.addRow(new Object[] { "", ip, port, username, password });
		}

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 36, 800, 526);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		JLabel label = new JLabel("<html><body>" + "<p>&nbsp;&nbsp;* 双击行可进行修改操作，按住ctrl可多选行进行操作</p>" + "</body></html>");
		label.setForeground(Color.RED);
		label.setFont(CyFont.PuHuiTi(CyFont.Medium, 12));

		JButton addBtn = new JButton("添加设备");
		addBtn.setFocusPainted(false);
		addBtn.setCursor(new Cursor(12));
		addBtn.setContentAreaFilled(false);
		addBtn.setFocusPainted(false);
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addDeviceDialog = Layer.window("添加设备", addDeviceComp(), 428, 240);
				addDeviceDialog.setVisible(true);
			}
		});

		JButton removeBtn = new JButton("删除设备");
		removeBtn.setFocusPainted(false);
		removeBtn.setCursor(new Cursor(12));
		removeBtn.setContentAreaFilled(false);
		removeBtn.setFocusPainted(false);
		removeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int SelecRowCount = table.getSelectedRowCount();
				if (SelecRowCount < 1) {
					Layer.alert("请选择要删除的设备", 300, 160);
				} else {
					Layer.confirm("确认要删除选中的设备数据吗?", 300, 160, new LayerCallback() {
						@Override
						public void clickOkBtn() {

						}

						@Override
						public void clickOkBtn(LayerConfirmCallback dispose) {
							int col = table.getColumnCount();
							for (int i = 0; i < SelecRowCount; i++) {
								int row = table.getSelectedRows()[i];
								Map<String, Object> data = new HashMap<String, Object>();
								for (int j = 1; j < col; j++) {
									String field = String.valueOf(columnField[j]);
									String value = String.valueOf(table.getValueAt(row, j));
									data.put(field, value);
								}
								System.out.println(row + ":" + data);
							}
							for (int j = 0; j < SelecRowCount; j++) {
								tableModel.removeRow(j);
							}
							dispose.destroy();
						}

						@Override
						public void clickCancelBtn() {

						}

						@Override
						public void clickBtn(String btn) {

						}

					});
				}
			}
		});

		JPanel btnGroup = new JPanel();
		btnGroup.add(addBtn);
		btnGroup.add(removeBtn);

		JPanel action = new JPanel();
		action.setLayout(new BorderLayout());
		action.add(btnGroup, BorderLayout.WEST);
		action.add(label, BorderLayout.CENTER);
		action.setBounds(0, 0, 800, 36);

		JPanel container = new JPanel();
		container.setLayout(null);

		container.add(action, BorderLayout.NORTH);
		container.add(scrollPane, BorderLayout.CENTER);
		container.add(scrollPane, BorderLayout.CENTER);

		return container;
	}

	private static JPanel addDeviceComp() {

		JPanel container = new JPanel();
		container.setLayout(new FlowLayout());
//		container.setPreferredSize(new Dimension(428, 240));
//		container.setBounds((Monitor.width - 428) / 2, 0, 428, 240);

		JLabel ipText = new JLabel("地址");
		ipText.setPreferredSize(new Dimension(80, 32));
		ipText.setHorizontalAlignment(SwingConstants.RIGHT);
		container.add(ipText);
		JTextField ipInput = new JTextField();
		ipInput.setPreferredSize(new Dimension(292, 32));
		ipInput.setMargin(new Insets(0, 5, 0, 5));
		container.add(ipInput);

		JLabel portText = new JLabel("端口");
		portText.setPreferredSize(new Dimension(80, 32));
		portText.setHorizontalAlignment(SwingConstants.RIGHT);
		container.add(portText);
		JTextField portInput = new JTextField("8000");
		portInput.setPreferredSize(new Dimension(292, 32));
		portInput.setMargin(new Insets(0, 5, 0, 5));
		container.add(portInput);

		JLabel accessText = new JLabel("用户名");
		accessText.setPreferredSize(new Dimension(80, 32));
		accessText.setHorizontalAlignment(SwingConstants.RIGHT);
		container.add(accessText);
		JTextField accessInput = new JTextField("admin");
		accessInput.setPreferredSize(new Dimension(292, 32));
		accessInput.setMargin(new Insets(0, 5, 0, 5));
		container.add(accessInput);

		JLabel passwordText = new JLabel("密码");
		passwordText.setPreferredSize(new Dimension(80, 32));
		passwordText.setHorizontalAlignment(SwingConstants.RIGHT);
		container.add(passwordText);
		JTextField passwordInput = new JTextField();
		passwordInput.setPreferredSize(new Dimension(292, 32));
		passwordInput.setMargin(new Insets(0, 5, 0, 5));
		container.add(passwordInput);

		JLabel emptyText = new JLabel();
		emptyText.setPreferredSize(new Dimension(80, 32));
		container.add(emptyText);

		JButton saveBtn = new JButton();
		saveBtn.setText("仅保存");
		saveBtn.setCursor(new Cursor(12));
		saveBtn.setContentAreaFilled(false);
		saveBtn.setFocusPainted(false);
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = ipInput.getText();
				String port = portInput.getText();
				String username = accessInput.getText();
				String password = passwordInput.getText();
				try {
					new com.action.Device(ip, port, username, password).insert();
					tableModel.addRow(new Object[] { "", ip, port, username, password });
					addDeviceDialog.dispose();
					Layer.alert("操作成功", 200, 120);
				} catch (Exception e1) {
					Layer.alert(e1.getMessage(), 240, 140);
				}
			}
		});
		container.add(saveBtn);

		JButton saveOrAddBtn = new JButton();
		saveOrAddBtn.setText("保存并继续添加");
		saveOrAddBtn.setCursor(new Cursor(12));
		saveOrAddBtn.setContentAreaFilled(false);
		saveOrAddBtn.setFocusPainted(false);
		saveOrAddBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ip = ipInput.getText();
				String port = portInput.getText();
				String username = accessInput.getText();
				String password = passwordInput.getText();
				try {
					new com.action.Device(ip, port, username, password).insert();
					ipInput.setText("");
					portInput.setText("8000");
					accessInput.setText("admin");
					passwordInput.setText("");
				} catch (Exception e1) {
					Layer.alert(e1.getMessage(), 240, 140);
				}
			}
		});
		container.add(saveOrAddBtn);

		JButton cancelBtn = new JButton();
		cancelBtn.setText("取消并关闭");
		cancelBtn.setCursor(new Cursor(12));
		cancelBtn.setContentAreaFilled(false);
		cancelBtn.setFocusPainted(false);
		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addDeviceDialog.dispose();
			}
		});
		container.add(cancelBtn);

		return container;
	}

}
