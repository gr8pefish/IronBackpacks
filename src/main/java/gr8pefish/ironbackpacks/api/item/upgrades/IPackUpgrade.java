package gr8pefish.ironbackpacks.api.item.upgrades;

import java.util.List;

public interface IPackUpgrade {

    String getName();

    int getId();

    int getUpgradeCost();

    List<String> getTooltip();
}
