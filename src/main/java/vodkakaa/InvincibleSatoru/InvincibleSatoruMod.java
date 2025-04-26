package vodkakaa.InvincibleSatoru;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import demoMod.invincibleOne.characters.InvincibleOne;
import demoMod.invincibleOne.skins.Skin;
import vodkakaa.InvincibleSatoru.cards.*;
import vodkakaa.InvincibleSatoru.skins.Satoru;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SpireInitializer
public class InvincibleSatoruMod implements EditCharactersSubscriber,
        EditStringsSubscriber,
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber,
        PostDungeonInitializeSubscriber,
        PostUpdateSubscriber,
        PostRenderSubscriber,
        StartGameSubscriber,
        AddAudioSubscriber {

    public static void initialize() {
        new InvincibleSatoruMod();
    }

    public InvincibleSatoruMod() {
        BaseMod.subscribe(this);
    }

    public static String makeID(String name) {
        return "INVSatoru:" + name;
    }

    public static String getResourcePath(String path) {
        return "SatoruImages/" + path;
    }

    @Override
    public void receiveEditCards() {
        List<CustomCard> cards = new ArrayList<>();
        cards.add(new Blue());
        cards.add(new Limitless());
        cards.add(new Purple());
        cards.add(new Red());
        cards.add(new UnlimitedVoid());
        for (CustomCard card : cards) {
            BaseMod.addCard(card);
            UnlockTracker.unlockCard(card.cardID);
        }
    }

    @Override
    public void receiveEditCharacters() {
        // 凑合凑合得了^^
        Skin.skins.add(new Satoru());
        InvincibleOne.registerSkins.add(new Satoru());
    }

    public static String getLanguageString() {
        String language;
        switch (Settings.language) {
            case ZHS:
                language = "zhs";
                break;
                /*
            case KOR:
                language = "kor";
                break;
            case JPN:
                language = "jpn";
                break;
                */
            default:
                language = "eng";
        }
        return language;
    }

    @Override
    public void receiveEditKeywords() {
        final Gson gson = new Gson();
        String language;
        language = getLanguageString();
        final String json = Gdx.files.internal("localizations/" + language + "/INVSatoru-KeywordStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword("inv", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditRelics() {
    }

    @Override
    public void receiveEditStrings() {
        String language;
        language = getLanguageString();

        String cardStrings = Gdx.files.internal("localizations/" + language + "/INVSatoru-CardStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
//        String relicStrings = Gdx.files.internal("localizations/" + language + "/INVSatoru-RelicStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
//        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
//        String powerStrings = Gdx.files.internal("localizations/" + language + "/INVSatoru-PowerStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
//        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        String charStrings = Gdx.files.internal("localizations/" + language + "/INVSatoru-CharacterStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CharacterStrings.class, charStrings);
        String uiStrings = Gdx.files.internal("localizations/" + language + "/INVSatoru-UIStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
//        String potionStrings = Gdx.files.internal("localizations/" + language + "/INVSatoru-PotionStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
//        BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
//        String eventStrings = Gdx.files.internal("localizations/" + language + "/INVSatoru-EventStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
//        BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
    }

    @Override
    public void receivePostDungeonInitialize() {

    }

    @Override
    public void receivePostInitialize() {
    }

    @Override
    public void receivePostRender(SpriteBatch sb) {
    }

    @Override
    public void receivePostUpdate() {
    }

    @Override
    public void receiveStartGame() {
    }

    @Override
    public void receiveAddAudio() {

    }
}
