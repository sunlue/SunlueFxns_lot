package com.view.datav.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.util.CyFont;
import com.view.datav.Cpanel;
/**
 * 停车场数据
 * @author xiebing
 *
 */
public class Event extends JPanel {

	private static final long serialVersionUID = 1L;

	public Event(int width, int height) {
		JScrollPane scrollPane = new JScrollPane(table());
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		Cpanel panel = new Cpanel("事件处理", "parking lot", scrollPane);
		setOpaque(false);
		setPreferredSize(new Dimension(width, height));
//		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
	}

	private JTable table() {
		Object[] columnNames = { "", "", "", "", "" };
		Object[][] rowData = {};

		JTable table = new JTable(new DefaultTableModel(rowData, columnNames) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		DefaultTableCellRenderer tableRender = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				table.setOpaque(false);
				setOpaque(false);
				if (row == 0) {
					setForeground(Color.WHITE);
				} else {
					setForeground(new Color(153, 186, 240));
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		tableRender.setHorizontalAlignment(SwingConstants.CENTER);

		table.setFont(CyFont.puHuiTi(CyFont.Bold, 12));
		table.setForeground(new Color(51, 51, 51));
		table.setGridColor(new Color(153, 186, 240));
		table.setRowHeight(32);
		table.setDefaultRenderer(Object.class, tableRender);
		table.setBorder(BorderFactory.createLineBorder(new Color(153, 186, 240), 1));

		//设置默认表头大小为0来达到隐藏表头的目的
		table.getTableHeader().setPreferredSize(new Dimension(0, 0));

		table.getColumnModel().getColumn(1).setMaxWidth(62);
		table.getColumnModel().getColumn(2).setMaxWidth(62);
		table.getColumnModel().getColumn(3).setMaxWidth(62);
		table.getColumnModel().getColumn(4).setMaxWidth(90);

		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		//重新设置表头
		tableModel.addRow(new Object[] { "停车场", "共计", "已使用", "未使用", "使用占比" });
		tableModel.addRow(new Object[] { "竹艺村生态停车场", "50", "30", "20", "60%" });
		tableModel.addRow(new Object[] { "竹艺村生态停车场", "50", "30", "20", "60%" });
		tableModel.addRow(new Object[] { "竹艺村生态停车场", "50", "30", "20", "60%" });

		return table;

	}

}
