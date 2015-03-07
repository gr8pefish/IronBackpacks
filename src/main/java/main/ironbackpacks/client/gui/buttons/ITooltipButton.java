package main.ironbackpacks.client.gui.buttons;

import java.util.ArrayList;

public interface ITooltipButton {

    public boolean mouseInButton(int mouseX, int mouseY);

    public ArrayList<String> getTooltip();

    public int getHoverTime();
}
