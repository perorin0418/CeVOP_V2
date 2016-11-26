package org.net.perorin.cevop2.parts;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CeFileIcon extends JLabel implements Transferable, DragGestureListener {

	File file = new File("");
	DragSource dragSource = new DragSource();
	DragGestureRecognizer dgr = dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);

	public CeFileIcon(ImageIcon imageIcon) {
		super(imageIcon);
	}

	public void setData(File file) {
		this.file = file;
	}

	@Override
	public void dragGestureRecognized(DragGestureEvent dge) {
		if ((dge.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {
			dge.startDrag(DragSource.DefaultCopyDrop, this, null);
		}
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { DataFlavor.javaFileListFlavor };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(DataFlavor.javaFileListFlavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		ArrayList<File> filelist = new ArrayList<File>();
		filelist.add(file);
		System.out.println(file.toString());
		return filelist;
	}

}
