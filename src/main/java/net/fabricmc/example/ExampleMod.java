package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.*;
//import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
//import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.registry.*;
import net.minecraft.text.*;
import net.fabricmc.api.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mojang.brigadier.exceptions.*;

//import static net.minecraft.server.command.CommandManager.*;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;
import static net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents.*;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static com.mojang.brigadier.arguments.StringArgumentType.getString;

public class ExampleMod implements ClientModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");
	public static boolean shoutsVisible = true;
	public static boolean apiValidVersion = true;

	@Override
	public void onInitializeClient() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Shout Disable: loaded!");
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(literal("sthu")
				.then(literal("toggle")
					.executes(context -> 
					toggleShouts(context.getSource())
							))
				.then(literal("help")
						.executes(context -> {
						      context.getSource().sendFeedback(Text.literal("/sthu help -> Shows this menu\n/sthu toggle -> Toggles the visibility of shouts"));
								 
						      return 1;
						    }))
		        .executes(context -> {
				      context.getSource().sendFeedback(Text.literal("Invalid arguments.\nType /sthu help for a list of arguments."));
						 
				      return 1;
				    })));
		
		try
		{
			ALLOW_GAME.register(new AllowGameListener());
			ALLOW_CHAT.register(new AllowChatListener());
		}
		
		catch (NoClassDefFoundError e)
		{
			LOGGER.info("Was not able to register the Shout Disable listener! Try upgrading to the newest Fabric loader version.");
			apiValidVersion = false;
		}
	}
	
	private static int toggleShouts(FabricClientCommandSource source) throws CommandSyntaxException {
		if (!apiValidVersion)
		{
			source.sendFeedback(Text.literal("The mod was not able to register the Shout Disable listener! Try upgrading to the newest Fabric loader version."));
		}
		else
		{
			if (shoutsVisible)
			{
				source.sendFeedback(Text.literal("Shouts have been disabled!"));
				shoutsVisible = false;
			} else
			{
				source.sendFeedback(Text.literal("Shouts have been enabled!"));
				shoutsVisible = true;
			}
		}
		return 0;
	}
	
}
