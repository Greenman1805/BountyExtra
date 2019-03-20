package de.greenman1805.bountyextra;

import org.bukkit.entity.Player;

public class Bounty {

	public static int getBounty(Player p) {
		return Main.playerdata.getInt(p.getUniqueId().toString());
	}

	public static void setBounty(Player p, int bounty) {
		Main.playerdata.set(p.getUniqueId().toString(), bounty);
	}

}
