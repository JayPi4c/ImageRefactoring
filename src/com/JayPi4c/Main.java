package com.JayPi4c;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

	public static void main(String args[]) throws IOException {
		System.out.println("Starting up");
		int threshold = 15;
		boolean all = true;
		int toEdit = 0;
		if (args.length > 0) {
			if (args[0] != null)
				threshold = Integer.parseInt(args[0]);

			if (args[1] != null) {
				if (args[1].equalsIgnoreCase("all")) {
					all = true;
				} else {
					all = false;
					toEdit = Integer.parseInt(args[1]);
				}
			}
		}

		System.out.println("tresh: " + threshold + "; all: " + all + "; toEdit: " + toEdit);

		File currentDir = new File(getExecutionPath());
		if (all) {
			System.out.println(currentDir.getAbsolutePath());
			toEdit = currentDir.list().length;
		} else if (toEdit > currentDir.list().length) {
			toEdit = currentDir.list().length;
		}

		String dirName = currentDir.getName();
		// BufferedImage Image = ImageIO.read(new
		// File("./src/com/JayPi4c/IMG.JPG"));
		File editFolder = new File(currentDir.getParentFile().getAbsolutePath() + "/" + dirName + "Edited");
		editFolder.mkdir();
		for (int i = 1; i < toEdit; i++) {
			String loc = getExecutionPath() + "/" + dirName + "IMG" + i + ".JPG";
			System.out.println(loc);
			BufferedImage Image = ImageIO.read(new File(loc));

			Editor e = new Editor(Image, threshold);
			BufferedImage newImage = e.edit();

			ImageIO.write(newImage, "PNG", new File(editFolder.getAbsolutePath() + "/" + dirName + "IMG" + i + ".PNG"));
		}
	}

	public static String getExecutionPath() {
		String absolutePath = new File(".").getAbsolutePath();
		File file = new File(absolutePath);
		absolutePath = file.getParentFile().toString();
		return absolutePath;
	}
}
