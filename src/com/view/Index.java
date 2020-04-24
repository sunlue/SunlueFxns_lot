package com.view;

import javax.swing.JDialog;

import com.util.Layer;
import com.util.Layer.LayerLoadingCallback;
import com.view.monitor.Monitor;

public class Index {
	
	
	public static void main(String[] args) throws Exception {
//		new Frame();
//		new Monitor();
		Layer.loading(new LayerLoadingCallback() {
			@Override
			public void handle(JDialog dialog) {
				System.out.println("加载完成");
				System.out.println(dialog);
				dialog.setVisible(false);
			}
		});
	}
}
