package vodkakaa.InvincibleSatoru.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import demoMod.invincibleOne.InvincibleOneMod;
import demoMod.invincibleOne.cards.invincible.AbstractInvincibleCard;
import demoMod.invincibleOne.enums.CardTagsEnum;
import vodkakaa.InvincibleSatoru.InvincibleSatoruMod;

public class Limitless extends AbstractInvincibleCard {
    public static final String ID = InvincibleSatoruMod.makeID("Limitless");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Limitless.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = -2;

    public Limitless() {
        super(ID, NAME, InvincibleSatoruMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 20;
        this.selfRetain = true;
    }

    @Override
    public Runnable getUpgradeAction() {
        return () -> {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            upgradeMagicNumber(10);
        };
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }
}
