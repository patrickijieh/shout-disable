package net.fabricmc.example;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents.*;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message.*;

import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;


public class AllowGameListener implements AllowGame {

	public static final Logger LOGGER = LoggerFactory.getLogger("modid");
	
	@Override
	public boolean allowReceiveGameMessage(Text message, boolean overlay)
	{
		if (!ExampleMod.shoutsVisible)
		{
			if((message.getString()).contains("shouts:") && (message.getString()).contains("[WC"))
			{
				LOGGER.info("Shout Blocked!");
				return false;
			}
		}
		
		return true;
	}
}
