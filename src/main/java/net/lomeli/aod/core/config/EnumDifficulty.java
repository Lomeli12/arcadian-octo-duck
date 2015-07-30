package net.lomeli.aod.core.config;

public enum EnumDifficulty {
    NORMAL(1), HARD(5), VERY_HARD(10);

    public static final EnumDifficulty[] VALID_VALUES = new EnumDifficulty[3];

    static {
        VALID_VALUES[0] = NORMAL;
        VALID_VALUES[1] = HARD;
        VALID_VALUES[2] = VERY_HARD;
    }

    private final int loss;

    EnumDifficulty(int loss) {
        this.loss = loss;
    }

    public float heartLoss(int count, float maxHealth) {
        if (loss == 1)
            return count * 2;
        else
            return count * (maxHealth / loss);
    }

    public static EnumDifficulty getDifficulty(int i) {
        return i < VALID_VALUES.length ? VALID_VALUES[i] : NORMAL;
    }
}
