package semant.signexc;

import semant.Operations;

public class IntBoolOps implements Operations<Integer, Boolean> {

	@Override
	public Integer abs(Integer z) {
		return z;
	}

	@Override
	public Boolean abs(boolean b) {
		return b;
	}

	@Override
	public Integer add(Integer a1, Integer a2) {
		if (a1 == null || a2 == null)
			return null;
		return a1 + a2;
	}

	@Override
	public Integer subtract(Integer a1, Integer a2) {
		if (a1 == null || a2 == null)
			return null;
		return a1 - a2;
	}

	@Override
	public Integer multiply(Integer a1, Integer a2) {
		if (a1 == null || a2 == null)
			return null;
		return a1 * a2;
	}

	@Override
	public Integer divide(Integer a1, Integer a2) {
		if (a1 == null || a2 == null)
			return null;
		return a1 / a2;
	}

	@Override
	public Boolean eq(Integer a1, Integer a2) {
		if (a1 == null || a2 == null)
			return null;
		return a1.equals(a2);
	}

	@Override
	public Boolean leq(Integer a1, Integer a2) {
		if (a1 == null || a2 == null)
			return null;
		return a1 <= a2;
	}

	@Override
	public Boolean and(Boolean b1, Boolean b2) {
		if (b1 == null || b2 == null)
			return null;
		return b1.equals(b2);
	}

	@Override
	public Boolean neg(Boolean b) {
		return b == null ? null : !b;
	}

	@Override
	public boolean possiblyAErr(Integer a) {
		return a == null;
	}

	@Override
	public boolean possiblyBErr(Boolean b) {
		return b == null;
	}

	@Override
	public boolean possiblyTrue(Boolean b) {
		return b != null && b;
	}

	@Override
	public boolean possiblyFalse(Boolean b) {
		return b != null && !b;
	}

	@Override
	public boolean possiblyInt(Integer a) {
		return a != null;
	}

	@Override
	public boolean isInt(Integer a) {
		return a != null;
	}

	@Override
	public Integer removeZero(Integer a) {
		if (a == null || a == 0) return null;
		return a;
	}

	@Override
	public boolean possiblyZero(Integer a) {
		return a != null && a == 0;
	}

	@Override
	public boolean isDefZero(Integer a) {
		return a != null && a == 0;
	}

	@Override
	public Integer[] allAValues() {
		throw new RuntimeException("Really?!");
	}

}
