#A Base to Build From!

Comes with most basic requirements for a mod.

This is simply a base for people to build from and includes no content, aside from a few example recipes. 

My hope is that it is simple to understand and easy to use.

##How to use:

1. Clone that repository using any Git client. (alternatively, download the zip)
2. Setup your Gradle workspace. I suggest following [this](https://www.youtube.com/watch?v=8VEdtQLuLO0) tutorial by LexManos. I also suggest running `setupDecompWorkspace` instead of `setupDevWorkspace`. Look in gradlew for what everything does.
3. Open it up in your IDE and refactor everything. 

       EG: BaseMod.java -> YourModName.java
      
4. Write the content of your mod.
5. Release to your loving fans and hope you didn't screw anything up.

I *do not* require any credit for this. I wrote it primarily for myself to use in my own mods and there's literally no reason for it to not be public.

##Suggestions or Feedback?

[Join my channel](https://webchat.esper.net/?channels=tehnut) (#TehNut) on [Espernet](https://www.esper.net/) and discuss there.

##FAQ:

* __Oh no! I found a bug/leak with it! D:__
   
   Please feel free to make a PR with a fix or open an issue on GitHub.

* __Can you include X feature?__
   
   Possibly. I don't want to do all the work for modders, and I don't want this to become a coremod/dependency.

* __Why should I use this?__
   
   I feel it saves time for beginning modders so they don't have to rewrite everything whenever they start a new project. If you don't feel it's useful, don't use it. Simple as that.

* __Can you port to Minecraft 1.X.X?__ 
   
   Sure, why not. However, most of this will work in all versions. One large exception being the config GUI.

* __Who the heck are you?__
  
   I am the lead dev of [Redstone Armory](http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2111983-). 

   I am working on a BigReactors addon-overhaul called [NuclearCubes](https://github.com/TehNut/NuclearCubes).

   I am a maintainer of [Tombenpotter's](https://github.com/Tombenpotter) Electro-Magic Tools.

   I am also the newest TPPI dev.
   
##Additional Tips:
   
* __How to update the Forge version-__
	
	View [this](http://www.minecraftforge.net/forum/index.php?topic=14048.0#post_update_forge) forum post for information on that.
	
* __How to update the ForgeGradle version-__
	
	View [this](http://www.minecraftforge.net/forum/index.php?topic=14048.0#post_update_forgegradle) forum post for information on that.
