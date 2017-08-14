# API

## UPGRADES

* BackpackUpgrade
    * holds info about upgrades
    * defines any other necessary functionality

* IUpgrade
    * interface to mark item as upgrade and expose BackpackUpgrade


## INVENTORY

* IBackpackProvider
    * interface to expose the internal inventory of a backpack

* BackpackInvImpl (not in API)
    * class to instantiate an itemStackHandler capability for each backpack variant (needs list of variants, specifically their sizes, by this point)
      * Variants/Sizes can be obtained via the Registry, assuming the classes extend IForgeRegistryEntry.Impl<> correctly
    * Note: ItemBackpack initializes the BackpackInvImpl as a capability


## BACKPACKS

* BackpackInfo
    * holds all the data: BackpackVariant, List<BackpackUpgrade>, IBackpackInventoryProvider
    * defines any other necessary functionality

* IBackpack
    * interface to mark item as backpack and expose BackpackInfo
    
## VARIANTS

* BackpackVariant
    * holds BackpackType and BackpackSpecialty
        * Calculates BackpackSize from that (cached in a field)

* BackpackSize
    * simple class to calculate the size of the backpack given a type and specialty 
    * useful as a separate class so that custom dynamic sizes can be done easier

* BackpackSpecialty
   * enum (only enum in API) that has the two types, UPGRADE and STORAGE

* BackpackType
    * holds data about each type (e.g. RL identifier, tier, baseStats, etc.)
    * adds to registrar's List<BackpackType> on construction


## REGISTRATION

* RegistrarIronBackpacks
    * uses custom ForgeRegisters to make everything nice and accessible