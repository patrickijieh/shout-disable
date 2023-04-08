package net.fabricmc.example;

import java.time.Instant;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents.*;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message.*;

import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;


public class AllowChatListener implements AllowChat {
	
public static final Logger LOGGER = LoggerFactory.getLogger("modid");
	
	@Override
	public boolean allowReceiveChatMessage(Text message, @Nullable SignedMessage signedMessage, @Nullable GameProfile sender, MessageType.Parameters params, Instant receptionTimestamp)
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
