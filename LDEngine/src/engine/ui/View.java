package engine.ui;

import java.awt.Graphics;

public abstract class View {
	abstract public void render();
	abstract public void postRender(Graphics g);
}
