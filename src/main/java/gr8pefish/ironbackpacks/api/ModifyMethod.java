package gr8pefish.ironbackpacks.api;

public enum ModifyMethod {

    /**
     * When a player has obtained an item.
     *
     * ie: picking up an item on the ground
     */
    OBTAIN,
    /**
     * When a player has lost an item.
     *
     * ie: placing a block
     */
    LOSE,
    ;
}
