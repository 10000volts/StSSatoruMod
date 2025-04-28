package vodkakaa.InvincibleSatoru.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.unique.RemoveAllPowersAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import demoMod.invincibleOne.InvincibleOneMod;
import demoMod.invincibleOne.cards.invincible.AbstractInvincibleCard;
import demoMod.invincibleOne.cards.invincible.BrokenWatch;
import demoMod.invincibleOne.relics.TaskGoal;
import vodkakaa.InvincibleSatoru.InvincibleSatoruMod;

import java.util.stream.Collectors;

public class UnlimitedVoid extends AbstractInvincibleCard {
    public static final String ID = InvincibleSatoruMod.makeID("UnlimitedVoid");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/UnlimitedVoid.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final int COST = 1;

    public UnlimitedVoid() {
        super(ID, NAME, InvincibleSatoruMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 7;
    }

    @Override
    public Runnable getUpgradeAction() {
        return () -> {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            upgradeMagicNumber(-1);
        };
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster _m) {
        int maxCount = magicNumber;
        maxCount += AbstractDungeon.player.masterDeck.group.stream().filter(c -> c instanceof BrokenWatch && !c.upgraded).count();
        maxCount += 2 * AbstractDungeon.player.masterDeck.group.stream().filter(c -> c instanceof BrokenWatch && c.upgraded).count();
        if (TaskGoal.combatTurn >= maxCount) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    (AbstractDungeon.getCurrRoom()).cannotLose = false;
                    for (AbstractMonster m2 : AbstractDungeon.getMonsters().monsters.stream().filter(m2 -> !m2.isDeadOrEscaped() || m2.halfDead).collect(Collectors.toList())) {
                        addToBot(new RemoveAllPowersAction(m2, false));
                        addToBot(new AbstractGameAction() {
                            @Override
                            public void update() {
                                m2.currentHealth = 0;
                                m2.healthBarUpdatedEvent();
                                m2.die();
                                isDone = true;
                            }
                        });
                    }
                    addToBot(new AbstractGameAction() {
                        @Override
                        public void update() {
                            AbstractDungeon.getCurrRoom().endBattle();
                        }
                    });
                    isDone = true;
                }
            });
        } else {
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                if (!m.isDeadOrEscaped()) {
                    if (upgraded) {
                        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, 1, false)));
                    }
                    addToBot(new StunMonsterAction(m, p));
                }
            }
        }
    }
}
