package edu.rcos.pkmnrpi.main.pokemon;

import edu.rcos.pkmnrpi.main.battle.BattleEngine;
import edu.rcos.pkmnrpi.main.animations.Clock;
import edu.rcos.pkmnrpi.main.animations.Sprite;

public class MoveAnimation {
	public BattleEngine engine;
	public Sprite x, y;
	public int mod, gap = 2, damage;
	public int[] starts = { 0, 0, 0, 0 };
	public double bonus, crit;
	public Pokemon a, b;
	public Move m;

	public MoveAnimation(BattleEngine e, Move m, Pokemon a, Pokemon b) {
		engine = e;
		this.a = a;
		this.b = b;
		this.m = m;
		x = a.sprite;
		y = b.sprite;
		starts[0] = x.x;
		starts[1] = x.y;
		starts[2] = y.x;
		starts[3] = y.y;
		mod = x.x < y.x ? 1 : -1;
		bonus = Pokemon.typeModifier(b, m);
		crit = Pokemon.critRoll(m);
		damage = Pokemon.calculateDamage(a, b, m, e, crit, bonus);
	}

	public void run() {
		//System.out.println(a.name + " used " + m.name + " on " + b.name + " for " + damage + "/" + b.stats.current_health);
		engine.panel.text.layText(a.name.toUpperCase() + " used " + m.name.toUpperCase() + "!");
		engine.panel.text.state = 0;
		int frames = 8;
		for (int i = 0; i < frames; ++i) {
			nap(Clock.FRAME_WAIT);
			x.x += mod * gap;
			rep();
		}
		for (int i = 0; i < frames; ++i) {
			nap(Clock.FRAME_WAIT);
			x.x -= mod * gap;
			rep();
		}
		reset();
		m.sprite.x = y.x + y.width / 2 - m.sprite.width / 2;
		m.sprite.y = y.y + y.height / 2 - m.sprite.height / 2;
		engine.panel.foreg.sprites.add(m.sprite);
		rep();
		nap(100);
		engine.panel.foreg.sprites.remove(m.sprite);
		rep();
		nap(100);
		engine.panel.foreg.sprites.add(m.sprite);
		rep();
		nap(100);
		engine.panel.foreg.sprites.remove(m.sprite);
		rep();
		nap(100);
		engine.panel.text.layText(printEnding());
		engine.animateHP(b, damage, 20);
	}

	private void rep() {
		engine.panel.foreg.paintImmediately(engine.panel.foreg.getBounds());
	}

	public static void nap(long time) {
		boolean temp = Clock.manual;
		Clock.manual = true;
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < time)
			;
		Clock.manual = temp;
	}

	public static void nap2(long time) {
		boolean temp = Clock.manual;
		Clock.manual = true;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Clock.manual = temp;
	}

	public void reset() {
		x.x = starts[0];
		x.y = starts[1];
		y.x = starts[2];
		y.y = starts[3];
		engine.panel.foreg.repaint();
	}

	public String printEnding() {
		String str = crit > 1 ? "Critical Hit! " : "";
		if (bonus == 1)
			;
		else if (bonus > 1)
			str += "It was super-effective!!";
		else
			str += "It was not very effective.";
		return str;
	}
}
