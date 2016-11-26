package org.net.perorin.cevop2;

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
		Controller.call(null, Controller.RUN);
		b.stop();
	}
}
