/*
 * Copyright (c) 2022. This is of full ownership of the fyg cafe server admins and owner lexy kobashigawa it was made for them and exclusively for them
 */

package me.youtissoum.fygplugin;

import github.scarsz.discordsrv.api.Subscribe;
import github.scarsz.discordsrv.api.events.DiscordReadyEvent;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.listeners.DiscordConsoleListener;
import github.scarsz.discordsrv.util.DiscordUtil;

public class DiscordSRVListener {
    private Fygplugin plugin;
    public DiscordSRVListener(Fygplugin _plugin) {
        plugin = _plugin;
    }

    @Subscribe
    public void discordReadyEvent(DiscordReadyEvent e) {
        DiscordUtil.sendMessage(DiscordUtil.getTextChannelById("968191770179555389"), plugin.getChangelog());
    }
}
