package org.net.perorin.cevop2;

import java.awt.EventQueue;

import org.net.perorin.cevop2.contoroller.Controller;
import org.net.perorin.cevop2.view.BootingWindow;

public class Main {
	public static void main(String[] args) {
		BootingWindow b = new BootingWindow(320, 200);
		b.run();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Controller c = new Controller();
					c.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		b.stop();
	}
}
