/*******************************************************************************
 * Copyright (c) 2012 Jakub Kováč, Katarína Kotrlová, Pavol Lukča, Viktor Tomkovič, Tatiana Tóthová
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package algvis.ds.dictionaries.treap;

import java.util.HashMap;

import algvis.core.Algorithm;
import algvis.core.visual.ZDepth;
import algvis.ds.dictionaries.bst.BSTInsert;

public class TreapInsert extends Algorithm {
	private final Treap T;
	private final int K;

	public TreapInsert(Treap T, int x) {
		super(T.panel);
		this.T = T;
		K = x;
	}

	@Override
	public void runAlgorithm() throws InterruptedException {
		final BSTInsert insert = new BSTInsert(T, new TreapNode(T, K,
				ZDepth.ACTIONNODE), this);
		insert.runAlgorithm();
		final HashMap<String, Object> insertResult = insert.getResult();
		final boolean inserted = (Boolean) insertResult.get("inserted");

		if (inserted) {
			pause();
			// bubleme nahor
			addStep("treapbubbleup");
			final TreapNode v = (TreapNode) insertResult.get("v");
			v.mark();
			while (!v.isRoot() && v.getParent().p < v.p) {
				T.rotate(v);
				pause();
			}
			v.unmark();
			addStep("done");
		}
	}
}
