package vodkakaa.InvincibleSatoru.skins;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import demoMod.invincibleOne.InvincibleOneMod;
import demoMod.invincibleOne.cards.invincible.*;
import demoMod.invincibleOne.skins.Skin;
import vodkakaa.InvincibleSatoru.InvincibleSatoruMod;
import vodkakaa.InvincibleSatoru.cards.Blue;
import vodkakaa.InvincibleSatoru.cards.Limitless;
import vodkakaa.InvincibleSatoru.cards.Red;
import vodkakaa.InvincibleSatoru.cards.UnlimitedVoid;

import java.util.ArrayList;

import static vodkakaa.InvincibleSatoru.InvincibleSatoruMod.getResourcePath;
import static vodkakaa.InvincibleSatoru.InvincibleSatoruMod.makeID;

public class Satoru extends Skin {
    static final UIStrings charStrings = CardCrawlGame.languagePack.getUIString(makeID("Intro"));
    public Satoru() {
        // super("Satoru", charStrings.NAMES[0], charStrings.TEXT[0], charStrings.TEXT[1], "", "Satoru");
        super("Satoru", charStrings.TEXT[0], charStrings.TEXT[1], charStrings.TEXT[2],
                "", makeID("TrashTalk"));
    }

    @Override
    public void load() {
        portrait = new Texture(getResourcePath("charSelect/portrait.png"));
        sprite = new Texture(getResourcePath("char/satoru.png"));
        shoulder1 = new Texture(getResourcePath("char/satoru1.png"));
        shoulder2 = new Texture(getResourcePath("char/satoru2.png"));
        for (int i = 0; i < 3; i++) {
            deathCG[i] = new Texture(getResourcePath("char/victory1_" + (i+1) + ".png"));
        }
    }

    @Override
    public void onGameStart() {

    }

    public ArrayList<AbstractCard> getStartingCards() {
        ArrayList<AbstractCard> ret = new ArrayList<>();
        ret.add(new Blue());
        ret.add(new Red());
        ret.add(new Limitless());
        ret.add(new UnlimitedVoid());
        return ret;
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo dmg, int amount){
        if (amount > 0) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c.cardID.equals(Limitless.ID)) {
                    amount -= c.upgraded ? 30 : 20;
                }
            }
        }
        return Math.max(amount, 0);
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> ret = new ArrayList<>();
        ret.add(Blue.ID);
        ret.add(Blue.ID);
        ret.add(Blue.ID);
        ret.add(Blue.ID);
        ret.add(Blue.ID);
        ret.add(Red.ID);
        ret.add(Red.ID);
        ret.add(Red.ID);
        ret.add(Red.ID);
        ret.add(Red.ID);
        ret.add(Limitless.ID);
        ret.add(Limitless.ID);
        ret.add(Limitless.ID);
        ret.add(UnlimitedVoid.ID);
        ret.add(UnlimitedVoid.ID);
        ret.add(UnlimitedVoid.ID);
        ret.add(UnlimitedVoid.ID);
        if (InvincibleOneMod.seriousLevel[CardCrawlGame.saveSlot] >= 6) {
            ret.add(OutOfThinAir.ID);
        }
        return ret;
    }
}
