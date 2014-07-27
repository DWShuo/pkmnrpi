package pokemon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import battle.BattleEngine;
import animations.Clock;
import animations.Sprite;

public class MoveAnimation implements ActionListener {
	public BattleEngine engine;
	public Sprite x, y;
	public boolean finished = false;
	public Move move;
	public Timer time = new Timer(Clock.FRAME_WAIT * 2, this);
	public int stage = 0, mod, increment = 0, gap = 3, damage;
	public int[] stage_waits = { 4, 4, 2, 2, 2, 0, 3, 5 }, starts = { 0, 0, 0, 0 };
	public Pokemon aa, bb;
	public double bonus, crit;

	public MoveAnimation(BattleEngine e, Move m, Pokemon c, Pokemon d) {
		move = m;
		engine = e;
		x = c.sprite;
		y = d.sprite;
		starts[0] = x.x;
		starts[1] = x.y;
		starts[2] = y.x;
		starts[3] = y.y;
		mod = x.x < y.x ? 1 : -1;
		aa = c;
		bb = d;
		bonus = Pokemon.typeModifier(d, m);
		crit = Pokemon.critRoll(m);
		damage = Pokemon.calculateDamage(c, d, m, e, crit, bonus);
		System.out.println(c.name + " used " + m.name + " on " + d.name + " for " + damage + "/" + d.stats.current_health);
		engine.panel.text.layText(c.name.toUpperCase() + " used " + m.name.toUpperCase() + "!");
		engine.panel.text.state = 0;
		time.start();
	}

	public void reset() {
		x.x = starts[0];
		x.y = starts[1];
		y.x = starts[2];
		y.y = starts[3];
		engine.panel.foreg.repaint();
	}

	public void tick() {
		// System.out.println("Stage " + stage + " (" + increment + ") " + stage_waits[stage]);
		if (increment < stage_waits[stage])
			++increment;
		else {
			++stage;
			increment = 0;
			if (stage >= stage_waits.length) {
				time.stop();
				engine.panel.text.layText(printEnding());
			}
		}
	}

	public String printEnding() {
		String str = crit > 1 ? "Critical Hit! " : "";
		if (bonus == 1)
			str += bb.name.toUpperCase() + " took normal damage.";
		else if (bonus > 1)
			str += "It was super-effective!!";
		else
			str += "It was not very effective.";
		return str;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (stage == 0) {
			x.x += mod * gap;
			engine.panel.foreg.repaint();
		} else if (stage == 1) {
			x.x -= mod * gap;
			reset();
		} else if (stage == 2 && increment == 0) {
			move.sprite.x = y.x + y.width / 2 - move.sprite.width / 2;
			move.sprite.y = y.y + y.height / 2 - move.sprite.height / 2;
			engine.panel.foreg.sprites.add(move.sprite);
			engine.panel.foreg.repaint();
		} else if (stage == 3 && increment == 0) {
			engine.panel.foreg.sprites.remove(move.sprite);
			engine.panel.foreg.repaint();
		} else if (stage == 4 && increment == 0) {
			engine.panel.foreg.sprites.add(move.sprite);
			engine.panel.foreg.repaint();

		} else if (stage == 5) {
			engine.panel.foreg.sprites.remove(move.sprite);
			engine.panel.foreg.repaint();
		} else if (stage == 7) {
			int gap = damage / (stage_waits[stage] - increment + 1);
			damage -= gap;
			bb.stats.current_health -= Math.min(gap, bb.stats.current_health);
			engine.panel.repaint();
		}
		tick();
	}
}
