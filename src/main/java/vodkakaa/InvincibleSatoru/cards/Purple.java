package vodkakaa.InvincibleSatoru.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import demoMod.invincibleOne.InvincibleOneMod;
import demoMod.invincibleOne.cards.invincible.AbstractInvincibleCard;
import demoMod.invincibleOne.powers.TauntPower;
import vodkakaa.InvincibleSatoru.InvincibleSatoruMod;

import java.util.stream.Collectors;

public class Purple extends AbstractInvincibleCard {
    public static final String ID = InvincibleSatoruMod.makeID("Purple");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Purple.png";

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 0;

    public Purple() {
        super(ID, NAME, InvincibleSatoruMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public Runnable getUpgradeAction() {
        return () -> {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        };
    }

    private static AbstractMonster getTarget() {
        AbstractMonster target = null;
        int maxHP = Integer.MIN_VALUE;

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters.stream().filter(m -> !m.isDeadOrEscaped() && m.hasPower(TauntPower.POWER_ID)).collect(Collectors.toList())) {
            if (monster.currentHealth > maxHP) {
                maxHP = monster.currentHealth;
                target = monster;
            }
        }
        if (target != null) {
            return target;
        }

        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters.stream().filter(m -> !m.isDeadOrEscaped()).collect(Collectors.toList())) {
            if (monster.currentHealth > maxHP) {
                maxHP = monster.currentHealth;
                target = monster;
            }
        }
        return target;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster t = getTarget();
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                t.currentHealth = 0;
                t.healthBarUpdatedEvent();
                t.die();
                isDone = true;
            }
        });
        Blue blue = new Blue();
        Red red = new Red();
        if (upgraded) {
            blue.upgrade();
            red.upgrade();
        }
        addToBot(new MakeTempCardInDrawPileAction(blue, 1, true, true));
        addToBot(new MakeTempCardInDrawPileAction(red, 1, true, true));
        addToBot(new DrawCardAction(1));
    }
}
