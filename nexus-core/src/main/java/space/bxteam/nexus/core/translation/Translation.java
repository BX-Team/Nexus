package space.bxteam.nexus.core.translation;

import java.util.List;

public interface Translation {
    String getLanguage();

    ArgumentSection argument();

    interface ArgumentSection {
        String onlyPlayers();
        String noPermission();
        String usageMessage();
        String usageMessageHead();
        String usageMessageEntry();
        String noItem();
        String numberBiggerThanOrEqualZero();
        String noDamaged();
        String noDamagedItems();
        String noEnchantment();
        String noValidEnchantmentLevel();
        String giveNoItem();
    }

    Format format();

    interface Format {
        String enable();
        String disable();
    }

    PlayerSection player();

    interface PlayerSection {
        String feedMessage();
        String feedMessageBy();

        String healMessage();
        String healMessageBy();

        String killedMessage();

        String flyEnable();
        String flyDisable();
        String flySetEnable();
        String flySetDisable();

        String godEnable();
        String godDisable();
        String godSetEnable();
        String godSetDisable();

        String onlinePlayersCountMessage();
        String onlinePlayersMessage();

        String pingMessage();
        String pingOtherMessage();

        List<String> whoisCommand();

        String speedBetweenZeroAndTen();
        String speedTypeNotCorrect();

        String speedWalkSet();
        String speedFlySet();

        String speedWalkSetBy();
        String speedFlySetBy();

        String gameModeNotCorrect();
        String gameModeMessage();
        String gameModeSetMessage();
    }

    InventorySection inventory();

    interface InventorySection {
        String inventoryClearMessage();
        String inventoryClearMessageBy();
    }

    SudoSection sudo();

    interface SudoSection {
        String sudoMessageSpy();
        String sudoMessage();
    }

    TimeAndWeatherSection timeAndWeather();

    interface TimeAndWeatherSection {
        String timeSet();
        String timeAdd();

        String weatherSetRain();
        String weatherSetSun();
        String weatherSetThunder();
    }

    ItemSection item();

    interface ItemSection {
        String itemClearNameMessage();
        String itemClearLoreMessage();

        String itemChangeNameMessage();
        String itemChangeLoreMessage();

        String repairMessage();
        String repairAllMessage();

        String incorrectItem();

        String enchantedMessage();
        String enchantedMessageFor();
        String enchantedMessageBy();

        String giveReceived();
        String giveGiven();
        String giveReceivedEnchantment();
        String giveGivenEnchantment();
    }
}
