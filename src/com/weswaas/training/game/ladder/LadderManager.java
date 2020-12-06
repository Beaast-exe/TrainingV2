package com.weswaas.training.game.ladder;

import com.weswaas.training.kit.KitManager;
import com.weswaas.training.util.ItemBuilder;
import org.bukkit.Material;

import java.util.ArrayList;

/**
 * Created by Weswas on 01/12/2016.
 */
public class LadderManager {

    private KitManager kit;

    public ArrayList<Ladder> ladders;

    public LadderManager(KitManager kit){
        this.kit = kit;
        this.ladders = new ArrayList<>();
        registerLadders();
    }

    public Ladder getLadder(String name){
        for(Ladder l : this.ladders){
            if(l.getName().equalsIgnoreCase(name)){
                return l;
            }
        }
        return null;
    }

    public Ladder getByMaterial(Material mat){
        for(Ladder l : this.ladders){
            if(l.getIcon().getType() == mat){
                return l;
            }
        }
        return null;
    }

    public ArrayList<Ladder> getLadders(){
        return this.ladders;
    }

    private void registerLadders(){

        ladders.add(new Ladder("BuildUHC", new ItemBuilder(Material.LAVA_BUCKET).name("§bBuildUHC").build(), kit.getKit("BuildUHC"), false));
        ladders.add(new Ladder("NoDebuff", new ItemBuilder(Material.POTION).durability(16421).name("§bNoDebuff").build(), kit.getKit("NoDebuff"), true));
        ladders.add(new Ladder("Debuff", new ItemBuilder(Material.FERMENTED_SPIDER_EYE).name("§bDebuff").build(), kit.getKit("Debuff"), true));
        ladders.add(new Ladder("Archer", new ItemBuilder(Material.ARROW).name("§bArcher").build(), kit.getKit("Archer"), false));
        ladders.add(new Ladder("Cobweb", new ItemBuilder(Material.WEB).name("§bCobweb").build(), kit.getKit("Cobweb"), false));
        ladders.add(new Ladder("FireAspect", new ItemBuilder(Material.BLAZE_POWDER).name("§bFire Aspect").build(), kit.getKit("FireAspect"), false));
        ladders.add(new Ladder("GApple", new ItemBuilder(Material.GOLDEN_APPLE).data(1).name("§bGApple").build(), kit.getKit("GApple"), true));
        ladders.add(new Ladder("SG", new ItemBuilder(Material.FLINT_AND_STEEL).name("§bSG").build(), kit.getKit("SG"), true));
        ladders.add(new Ladder("FinalUHC", new ItemBuilder(Material.DIAMOND).name("§bFinalUHC").build(), kit.getKit("FinalUHC"), false));
        ladders.add(new Ladder("Soup", new ItemBuilder(Material.MUSHROOM_SOUP).name("§bSoup").build(), kit.getKit("Soup"), false));
        ladders.add(new Ladder("SuperSoup", new ItemBuilder(Material.NETHER_STAR).name("§bSuperSoup").build(), kit.getKit("SuperSoup"), false));

    }

}
