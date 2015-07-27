package net.lomeli.aod.core;

public enum EnumDifficulty {
    EASY(0, 0), NORMAL(1, 1), HARD(2, 5), VERY_HARD(3, 10);

    public static EnumDifficulty[] VALID_VALUES = new EnumDifficulty[4];
    static {
        VALID_VALUES[0] = EASY;
        VALID_VALUES[1] = NORMAL;
        VALID_VALUES[2] = HARD;
        VALID_VALUES[3] = VERY_HARD;
    }
    private final int id;
    private final int loss;

    EnumDifficulty(int id, int loss) {
        this.id = id;
        this.loss = loss;
    }

    public int getID() {
        return this.id;
    }

    public float heartLoss(int count, float maxHealth) {
        if (loss == 0)
            return 0;
        else if (loss == 1)
            return count;
        else
            return count * (maxHealth / loss);
    }


    public static EnumDifficulty getDifficulty(int i) {
        return i < VALID_VALUES.length ? VALID_VALUES[i] : NORMAL;
    }

}
