package com.JayPi4c;

public class Boundaries {
	private int leftEdge = 0;
	private int rightEdge = 0;
	private int topEdge = 0;
	private int bottomEdge = 0;

	private int x = 0;
	private int y = 0;
	private int w = 0;
	private int h = 0;

	public Boundaries(int left, int right, int top, int bottom) {
		this.leftEdge = left;
		this.rightEdge = right;
		this.topEdge = top;
		this.bottomEdge = bottom;

		this.setX(this.leftEdge);
		this.setY(this.topEdge);
		this.setWidth(this.rightEdge - this.leftEdge);
		this.setHeight(this.bottomEdge - this.topEdge);
	}

	public void setHeight(int h) {
		this.h = h;
	}

	public int getHeight() {
		return h;
	}

	public void setWidth(int w) {
		this.w = w;
	}

	public int getWidth() {
		return w;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}
}
