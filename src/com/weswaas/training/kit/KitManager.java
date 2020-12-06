package com.weswaas.training.kit;

import com.weswaas.training.kit.kits.other.Lobby;
import com.weswaas.training.kit.kits.other.SG;
import com.weswaas.training.kit.kits.potion.Debuff;
import com.weswaas.training.kit.kits.potion.GApple;
import com.weswaas.training.kit.kits.potion.NoDebuff;
import com.weswaas.training.kit.kits.soup.Soup;
import com.weswaas.training.kit.kits.soup.SuperSoup;
import com.weswaas.training.kit.kits.uhc.*;

import java.util.ArrayList;

/**
 * Created by Weswas on 23/12/2016.
 */
public class KitManager {

    private ArrayList<Kit> kits = new ArrayList<>();

    public Kit getKit(String name){
        for(Kit kit : kits){
            if(kit.getName().equalsIgnoreCase(name)){
                return kit;
            }
        }
        return null;
    }

    public void register(){

        kits.add(new BuildUHC());
        kits.add(new Lobby());
        kits.add(new NoDebuff());
        kits.add(new Debuff());
        kits.add(new Archer());
        kits.add(new Cobweb());
        kits.add(new FireAspect());
        kits.add(new GApple());
        kits.add(new SG());
        kits.add(new FinalUHC());
        kits.add(new Soup());
        kits.add(new SuperSoup());

    }

}
