package de.greenman1805.bountyextra;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
public class PvPListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerKill(PlayerDeathEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = e.getEntity();
			int bounty_player = Bounty.getBounty(p);
			if (p.getKiller() instanceof Player) {
				Player k = p.getKiller();
				if (p != k) {
					int bounty_killer = Bounty.getBounty(k);
					bounty_killer += Main.bounty_kill;
					Bounty.setBounty(k, bounty_killer);
					Bounty.setBounty(p, Main.bounty_after_death);

					Main.econ.depositPlayer(k, bounty_player);

					if (bounty_player == 0) {
						k.sendMessage(Main.prefix + "Du hast " + p.getDisplayName() + " §7getötet.");
					} else {
						k.sendMessage(Main.prefix + "Du hast " + p.getDisplayName() + " §7getötet und §a" + bounty_player + " §7Shards erhalten.");
					}
					p.sendMessage(Main.prefix + "Du wurdest von " + k.getDisplayName() + "§7 getötet.");

					for (Player op : p.getWorld().getPlayers()) {
						if (!op.getName().equals(p.getName()) && !op.getName().equals(k.getName())) {
							op.sendMessage(Main.prefix + k.getDisplayName() + " §f[§a" + bounty_killer + "§f]" + "§7 hat " + p.getDisplayName() + "§7 getötet.");
						}
					}
				}
			} else {
				for (Player op : p.getWorld().getPlayers()) {
					if (!op.getName().equals(p.getName())) {
						op.sendMessage(Main.prefix + p.getDisplayName() + " §f[§a" + bounty_player + "§f]" + "§7 ist gestorben.");
					}
				}
			}
		}

	}

}
