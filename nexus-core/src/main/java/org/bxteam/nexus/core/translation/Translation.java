package org.bxteam.nexus.core.translation;

import com.eternalcode.multification.notice.Notice;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.List;
import java.util.Map;

public interface Translation {
    ArgumentSection argument();

    interface ArgumentSection {
        Notice onlyPlayers();
        Notice noPermission();
        Notice usageMessage();
        Notice usageMessageHead();
        Notice usageMessageEntry();
        Notice noItem();
        Notice numberBiggerThanOrEqualZero();
        Notice noDamaged();
        Notice noDamagedItems();
        Notice noEnchantment();
        Notice noValidEnchantmentLevel();
        Notice noValidItem();
        Notice offlinePlayer();
    }

    Format format();

    interface Format {
        String enable();
        String disable();
    }

    PlayerSection player();

    interface PlayerSection {
        Notice feedMessage();
        Notice feedMessageBy();

        Notice healMessage();
        Notice healMessageBy();

        Notice killedMessage();

        Notice flyEnable();
        Notice flyDisable();
        Notice flySetEnable();
        Notice flySetDisable();

        Notice godEnable();
        Notice godDisable();
        Notice godSetEnable();
        Notice godSetDisable();

        Notice onlinePlayersCountMessage();
        Notice onlinePlayersMessage();

        Notice pingMessage();
        Notice pingOtherMessage();

        List<String> whoisCommand();

        Notice speedBetweenZeroAndTen();
        Notice speedTypeNotCorrect();

        Notice speedWalkSet();
        Notice speedFlySet();

        Notice speedWalkSetBy();
        Notice speedFlySetBy();

        Notice gameModeNotCorrect();
        Notice gameModeMessage();
        Notice gameModeSetMessage();

        List<String> fullServerSlots();
    }

    EventSection event();

    interface EventSection {
        Notice welcome();

        List<Notice> firstJoinMessage();
        List<Notice> joinMessage();
        List<Notice> quitMessage();

        List<Notice> deathMessage();
        List<Notice> unknownDeathCause();
        Map<EntityDamageEvent.DamageCause, List<Notice>> deathMessageByDamageCause();
    }

    AfkSection afk();

    interface AfkSection {
        Notice afkOn();
        Notice afkOff();

        String afkKickReason();

        String afkEnabledPlaceholder();
        String afkDisabledPlaceholder();
    }

    InventorySection inventory();

    interface InventorySection {
        Notice inventoryClearMessage();
        Notice inventoryClearMessageBy();
    }

    SudoSection sudo();

    interface SudoSection {
        Notice sudoMessageSpy();
    }

    TimeAndWeatherSection timeAndWeather();

    interface TimeAndWeatherSection {
        Notice timeSet();
        Notice timeAdd();

        Notice weatherSetRain();
        Notice weatherSetSun();
        Notice weatherSetThunder();
    }

    ItemSection item();

    interface ItemSection {
        Notice itemClearNameMessage();
        Notice itemClearLoreMessage();

        Notice itemChangeNameMessage();
        Notice itemChangeLoreMessage();

        Notice repairMessage();
        Notice repairAllMessage();

        Notice incorrectItem();

        Notice enchantedMessage();
        Notice enchantedMessageFor();
        Notice enchantedMessageBy();

        Notice giveReceived();
        Notice giveGiven();
    }

    WarpSection warp();

    interface WarpSection {
        Notice create();
        Notice remove();

        Notice warpAlreadyExists();
        Notice notExist();
    }

    HomeSection home();

    interface HomeSection {
        Notice homeList();

        Notice create();
        Notice delete();
        Notice homeAlreadyExists();

        Notice limit();
        Notice noHomes();

        String noHomesPlaceholder();
    }

    JailSection jail();

    interface JailSection {
        Notice jailLocationSet();
        Notice jailLocationRemove();
        Notice jailLocationExists();
        Notice jailLocationNotExists();

        Notice jailJailedPrivate();
        Notice jailJailedExecutor();
        Notice jailCountdown();
        Notice jailCannotJailAdmin();
        Notice jailCannotJailSelf();
        Notice jailAlreadyJailed();

        Notice jailReleasePrivate();
        Notice jailReleaseExecutor();
        Notice jailReleaseAll();
        Notice jailReleaseNoPlayers();
        Notice jailNotJailed();

        Notice jailListEmpty();
        Notice jailListHeader();
        Notice jailListEntry();

        Notice jailCannotUseCommand();
    }

    SpawnSection spawn();

    interface SpawnSection {
        Notice spawnTeleported();
        Notice spawnTeleportedOther();

        Notice setSpawn();
        Notice noSpawn();
    }

    TeleportSection teleport();

    interface TeleportSection {
        Notice teleportedToPlayer();
        Notice teleportedPlayerToPlayer();
        Notice teleportedToHighestBlock();

        Notice teleportedToCoordinates();
        Notice teleportedSpecifiedPlayerToCoordinates();

        Notice teleportedToLastLocation();
        Notice teleportedSpecifiedPlayerLastLocation();
        Notice lastLocationNoExist();

        Notice teleported();
        Notice teleporting();
        Notice teleportTaskCanceled();
    }

    RandomTeleportSection randomTeleport();

    interface RandomTeleportSection {
        Notice randomTeleportSearchStart();
        Notice randomTeleportSearchFailed();
        Notice randomTeleportTeleported();
        Notice teleportedSpecifiedPlayerToRandomLocation();
    }

    TeleportRequestSection teleportRequest();

    interface TeleportRequestSection {
        Notice tpaSelfMessage();
        Notice tpaAlreadySentMessage();
        Notice tpaSentMessage();
        Notice tpaReceivedMessage();
        Notice tpaIgnoredMessage();

        Notice tpaAcceptMessage();
        Notice tpaAcceptNoRequestMessage();
        Notice tpaAcceptReceivedMessage();
        Notice tpaAcceptAllAccepted();

        Notice tpaDenyNoRequestMessage();
        Notice tpaDenyMessage();
        Notice tpaDenyReceivedMessage();
        Notice tpaDenyAllDenied();
    }

    PrivateChatSection privateChat();

    interface PrivateChatSection {
        Notice youToTargetMessage();
        Notice targetToYouMessage();
        Notice socialSpyMessage();

        Notice socialSpyStatus();

        Notice noReply();
    }

    ChatSection chat();

    interface ChatSection {
        Notice enabled();
        Notice disabled();
        Notice cleared();

        Notice alreadyDisabled();
        Notice alreadyEnabled();

        Notice chatDisabled();

        Notice slowMode();

        Notice broadcastMessage();
    }

    IgnoreSection ignore();

    interface IgnoreSection {
        Notice ignoredPlayer();
        Notice ignoreAll();

        Notice unIgnoredPlayer();
        Notice unIgnoreAll();

        Notice alreadyIgnored();
        Notice notIgnored();
        Notice ignoreSelf();
        Notice unIgnoreSelf();
    }

    AdminChatSection adminChat();

    interface AdminChatSection {
        Notice message();
    }

    HelpSection help();

    interface HelpSection {
        Notice helpMessageSpy();
        Notice helpMessageSend();
    }

    SignEditorSection signEditor();

    interface SignEditorSection {
        Notice noSignFound();
        Notice invalidIndex();
        Notice lineSet();
    }

    FunSection fun();

    interface FunSection {
        Notice spitSound();
    }
}
