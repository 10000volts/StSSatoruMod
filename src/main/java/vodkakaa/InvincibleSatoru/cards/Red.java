package vodkakaa.InvincibleSatoru.cards;

import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import demoMod.invincibleOne.InvincibleOneMod;
import demoMod.invincibleOne.cards.invincible.AbstractInvincibleCard;
import vodkakaa.InvincibleSatoru.InvincibleSatoruMod;

public class Red extends AbstractInvincibleCard {
    public static final String ID = InvincibleSatoruMod.makeID("Red");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "cards/Red.png";

    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.NONE;

    private static final int COST = 1;

    public Red() {
        super(ID, NAME, InvincibleSatoruMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.baseDamage = 40;
        this.cardsToPreview = new Purple();
    }

    @Override
    public Runnable getUpgradeAction() {
        return () -> {
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
            upgradeDamage(30);
        };
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractCard me = this;
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                AbstractCard target = null;
                boolean flag = false;
                for (AbstractCard c: AbstractDungeon.player.hand.group) {
                    if (target == null && c.cardID.equals(Blue.ID)) {
                        target = c;
                    } else if (c.equals(me)) {
                        flag = true;
                    }
                }

                if (target != null && flag) {
                    AbstractDungeon.player.hand.moveToExhaustPile(target);
                    target.exhaustOnUseOnce = false;
                    target.freeToPlayOnce = false;
                    AbstractDungeon.player.hand.moveToExhaustPile(me);
                    me.exhaustOnUseOnce = false;
                    me.freeToPlayOnce = false;
                    Purple purple = new Purple();
                    if (upgraded) {
                        purple.upgrade();
                    }
                    addToBot(new MakeTempCardInHandAction(purple));
                }

                isDone = true;
            }
        });
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster _m) {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped() && m.intent.name().contains("ATTACK") && !m.hasPower(StunMonsterPower.POWER_ID)) {
                addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            }
        }
    }
}
