package main.ironbackpacks.items.upgrades;

import java.util.List;

public interface IPackUpgrade {

    String getName();

    int getId();

    int getUpgradeCost();

    List<String> getTooltip();
}
