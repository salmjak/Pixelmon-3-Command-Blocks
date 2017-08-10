package hobbyist.samIam.PixelmonCBUtil;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import hobbyist.samIam.utilities.CheckPlayerUtility;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.SpongeExecutorService;
import net.minecraft.scoreboard.*;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.FMLCommonHandler;

//TODO: Increase performance by listening to Pixelmon Events and only change scores when necessary.

@Plugin
(
        id = "pixelmoncbutil",
        name = "PixelmonCommandBlockUtilities",
        version = "0.0.1",
        dependencies = @Dependency(id = "pixelmon"),
        description = "Adds scoreboards for different Pixelmon variables.",
        authors = "samIam"
        
)
public class PixelmonCBUtil {
    
    public static PixelmonCBUtil instance;
    
    @Inject
    public Logger log;
    
    public static Logger getLogger()
    {
        return instance.log;
    }
    
    SpongeExecutorService scheduler;
    
    Scoreboard sb;
    
    @Listener
    public void onInitialization(GameInitializationEvent event){
        instance = this;
    }
    
    @Listener
    public void onServerStart(GameStartedServerEvent event) 
    {
        scheduler = Sponge.getScheduler().createSyncExecutor(this);
        scheduler.scheduleAtFixedRate(new ScoreboardUpdater(), 500, 500, TimeUnit.SECONDS);
        
        sb = FMLCommonHandler.instance().getMinecraftServerInstance().getServer().worldServerForDimension(0).getScoreboard();
        if(sb != null)
        {
            createObjectives();
        } 
        else 
        {
            log.info("Failed to retrieve scoreboard");
        }
        
    }
    
    @Listener
    public void onServerStopped(GameStoppedServerEvent event)
    {
        scheduler.shutdown();
    }
    
    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event)
    {
        Player p = event.getTargetEntity();
        //Add a score for each objective to the new player.
        for(ScoreObjective obj : sb.getScoreObjectives())
        {
            sb.getOrCreateScore(p.getName(), obj);
        }
    }
    
    Random rand = new Random();
    class ScoreboardUpdater implements Runnable{

        int k=0;
        @Override
        public void run() 
        {
            ArrayList<Player> online_players = Lists.newArrayList(Sponge.getServer().getOnlinePlayers());
            
            //Look at 10 players at a time.
            for(int i=k*10; i<(k*10)+10;i++)
            {
                if(i >= online_players.size())
                {
                    break;
                }
                UpdateObjectives(online_players.get(i));
            }
            
            k++;
            
            //We have looped through all online players, return to 0.
            if(k*10 >= online_players.size())
            {
                k=0;
            }
        }
        
    }
    
    void createObjectives()
    {
        CreateObjective("badges", "Number of Badges");
        CreateObjective("money", "PokeDollar");
        CreateObjective("minLvl", "Lowest level of Party");
        CreateObjective("maxLvl", "Highest level of Party");
        CreateObjective("avgLvl", "Average level of Party");
        CreateObjective("isFainted", "Fainted Team");
        CreateObjective("partySize", "Party Size");
    }
    
    void CreateObjective(String name, String displayName)
    {
        if(sb.getObjective(name) == null)
        {
            sb.addScoreObjective(name, IScoreCriteria.DUMMY);
            sb.getObjective(name).setDisplayName(displayName);
            sb.setObjectiveInDisplaySlot(0, sb.getObjective(name));
        }
    }
    
    void UpdateObjectives(Player p)
    {
        EntityPlayerMP mp = (EntityPlayerMP)p;
        for(Map.Entry<ScoreObjective, Score> e : sb.getObjectivesForEntity(p.getName()).entrySet())
        {
            ScoreObjective o = e.getKey();
            switch(o.getName())
            {
                case "badges":
                    e.getValue().setScorePoints(CheckPlayerUtility.getPlayerNumBadges(mp));
                    break;
                case "money":
                    e.getValue().setScorePoints(CheckPlayerUtility.getPlayerPokeDollarOrZero(mp));
                    break;
                case "minLvl":
                    e.getValue().setScorePoints(CheckPlayerUtility.getPlayerTeamMinLvl(mp));
                    break;
                case "maxLvl":
                    e.getValue().setScorePoints(CheckPlayerUtility.getPlayerTeamMaxLvl(mp));
                    break;
                case "avgLvl":
                    e.getValue().setScorePoints(CheckPlayerUtility.getPlayerTeamAvgLvl(mp));
                    break;
                case "isFainted":
                    e.getValue().setScorePoints(CheckPlayerUtility.playerTeamFainted(mp));
                    break;
                case "partySize":
                    e.getValue().setScorePoints(CheckPlayerUtility.getPlayerTeamSize(mp));
                    break;
            }
        }
    }

}
