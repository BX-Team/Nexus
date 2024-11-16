package space.bxteam.nexus.core.translation;

import com.eternalcode.multification.notice.Notice;

import java.util.List;

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
        Notice giveNoItem();
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
    }

    InventorySection inventory();

    interface InventorySection {
        Notice inventoryClearMessage();
        Notice inventoryClearMessageBy();
    }

    SudoSection sudo();

    interface SudoSection {
        Notice sudoMessageSpy();
        Notice sudoMessage();
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
        Notice noWarps();
    }

    HomeSection home();

    interface HomeSection {
        Notice homeList();
        Notice create();
        Notice delete();
        Notice limit();
        Notice noHomes();
        Notice homeAlreadyExists();
    }
}
