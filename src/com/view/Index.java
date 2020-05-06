package com.view;

/**
 * @author xiebing
 */
public class Index {

	public static void main(String[] args) throws Exception {
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new com.view.datav.DataV();
			}
		});
	}

}
