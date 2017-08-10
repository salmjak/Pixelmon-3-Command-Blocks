
package hobbyist.samIam.utilities;

import com.pixelmonmod.pixelmon.storage.NbtKeys;
import com.pixelmonmod.pixelmon.storage.PixelmonStorage;
import com.pixelmonmod.pixelmon.storage.PlayerStorage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.api.text.Text;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CheckPlayerUtility {
    
    public static int playerTeamFainted(EntityPlayerMP p)
    {
        ArrayList<NBTTagCompound> pokemon_party = getPlayerTeamOrNull(p);
        if(pokemon_party == null)
        {
            return 0;
        }

        for(NBTTagCompound poke : pokemon_party)
        {
            if(poke.getInteger(NbtKeys.HEALTH) > 0)
            {
                return 0;
            }
        }

        return 1;
    }
    
    /** Returns the average level of a players team*/
    public static int getPlayerTeamAvgLvl(EntityPlayerMP p)
    {
        int average_level = 0;
        ArrayList<NBTTagCompound> party = getPlayerTeamOrNull(p);
        if(party != null)
        {
            for(NBTTagCompound poke : party)
            {
                average_level += poke.getInteger(NbtKeys.LEVEL);
            }
            average_level = (int)((double)average_level / 6.0);
        }
        
        return average_level;
    }
    
    /** Returns the level of the highest leveled pokemon in a players team*/
    public static int getPlayerTeamMaxLvl(EntityPlayerMP p)
    {
        int max_level = 0;
        ArrayList<NBTTagCompound> party = getPlayerTeamOrNull(p);
        if(party != null){
            for(NBTTagCompound poke : party)
            {
                if(max_level <= poke.getInteger(NbtKeys.LEVEL))
                {
                    max_level = poke.getInteger(NbtKeys.LEVEL);
                }
            }
        }
        
        return max_level;
    }
    
    /** Returns the level of the lowest leveled pokemon in a players team*/
    public static int getPlayerTeamMinLvl(EntityPlayerMP p)
    {
        int min_level = Integer.MAX_VALUE;
        ArrayList<NBTTagCompound> party = getPlayerTeamOrNull(p);
        if(party != null){
            for(NBTTagCompound poke : party)
            {
                if(min_level >= poke.getInteger(NbtKeys.LEVEL))
                {
                    min_level = poke.getInteger(NbtKeys.LEVEL);
                }
            }
        }
        
        return min_level;
    }
    
    public static int getPlayerTeamSize(EntityPlayerMP p)
    {
        ArrayList<NBTTagCompound> party = getPlayerTeamOrNull(p);
        if(party != null)
        {
            return party.size();
        }
        return 0;
    }
    
    
    public static int getPlayerPokeDollarOrZero(EntityPlayerMP p)
    {
        int money = 0;
        Optional<?> storage = PixelmonStorage.pokeBallManager.getPlayerStorage(p);
        if (storage.isPresent())
        {
            PlayerStorage storageCompleted = (PlayerStorage) storage.get();
            money = storageCompleted.getCurrency();
        }
        
        return money;
    }
    
    /** Returns the amount of unique badges in a players inventory*/
    public static int getPlayerNumBadges(EntityPlayerMP p){
        int badges = 0;

        for(String id : badge_ids)
        {
            if(badge_stack.containsKey(id))
            {
                if(p.inventory.hasItemStack(badge_stack.get(id)))
                {
                    badges++;
                }
            } 
            else 
            {
                Item i = Item.getByNameOrId(id);
                ItemStack s = new ItemStack(i);
                if(p.inventory.hasItemStack(s))
                {
                    badges++;
                }
                badge_stack.put(id, s);
            }
        }
        
        return badges;
    }
    
    /** Returns the players team excluding eggs*/
    public static ArrayList<NBTTagCompound> getPlayerTeamOrNull(EntityPlayerMP p){
        Optional<?> storage = PixelmonStorage.pokeBallManager.getPlayerStorage(p);
        if (!storage.isPresent())
        {
            return null;
        }
        else
        {
            PlayerStorage storageCompleted = (PlayerStorage) storage.get();
            ArrayList<NBTTagCompound> pokemon_party = new ArrayList(6);
            for(int i=0; i<6; i++)
            {
                NBTTagCompound nbt = storageCompleted.partyPokemon[i];
                if(nbt != null)
                {
                    if(!nbt.getBoolean("isEgg"))
                    {
                        //A pokemon exists on that slot and it isn't an egg. Add it to the list.
                        pokemon_party.add(nbt);
                    }
                }
            }
            
            return pokemon_party;
        }
          
    }
    
    public static ArrayList<String> badge_ids = new ArrayList<String>() {{
        add("pixelmon:balance_badge");
        add("pixelmon:basic_badge");
        add("pixelmon:beacon_badge");
        add("pixelmon:bolt_badge");
        add("pixelmon:boulder_badge");
        add("pixelmon:bug_badge");
        add("pixelmon:cascade_badge");
        add("pixelmon:cliff_badge");
        add("pixelmon:coal_badge");
        add("pixelmon:cobble_badge");
        add("pixelmon:dynamo_badge");
        add("pixelmon:earth_badge");
        add("pixelmon:fairy_badge");
        add("pixelmon:feather_badge");
        add("pixelmon:fen_badge");
        add("pixelmon:fog_badge");
        add("pixelmon:forest_badge");
        add("pixelmon:freeze_badge");
        add("pixelmon:glacier_badge");
        add("pixelmon:heat_badge");
        add("pixelmon:hive_badge");
        add("pixelmon:iceberg_badge");
        add("pixelmon:icicle_badge");
        add("pixelmon:insect_badge");
        add("pixelmon:jet_badge");
        add("pixelmon:knuckle_badge");
        add("pixelmon:legend_badge");
        add("pixelmon:marsh_badge");
        add("pixelmon:mind_badge");
        add("pixelmon:mine_badge");
        add("pixelmon:mineral_badge");
        add("pixelmon:plain_badge");
        add("pixelmon:plant_badge");
        add("pixelmon:psychic_badge");
        add("pixelmon:quake_badge");
        add("pixelmon:rainbow_badge");
        add("pixelmon:rain_badge");
        add("pixelmon:relic_badge");
        add("pixelmon:rising_badge");
        add("pixelmon:rumble_badge");
        add("pixelmon:soul_badge");
        add("pixelmon:stone_badge");
        add("pixelmon:storm_badge");
        add("pixelmon:thunder_badge");
        add("pixelmon:toxic_badge");
        add("pixelmon:trio_badge");
        add("pixelmon:volcano_badge");
        add("pixelmon:voltage_badge");
        add("pixelmon:wave_badge");
        add("pixelmon:zephyr_badge");
    }};
    
    public static HashMap<String, ItemStack> badge_stack = new HashMap<>();
}
