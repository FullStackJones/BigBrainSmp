package net.fullstackjones.bigbrainsmpmod.item.custom;

import net.minecraft.world.item.Item;

public class CoinItem extends Item {
    public CoinItem(Properties pProperties) {
        super(pProperties);
    }

    public static class Properties extends Item.Properties {
        private String coinType;
        private int coinValue;

        public Properties() {
            super();
        }

        public Properties coinType(String coinType) {
            this.coinType = coinType;
            return this;
        }

        public Properties coinValue(int coinValue) {
            this.coinValue = coinValue;
            return this;
        }

        public String getCoinType() {
            return this.coinType;
        }

        public int getCoinValue() {
            return this.coinValue;
        }
    }
}
