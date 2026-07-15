package net.fullstackjones.bigbraincurrency.menu;

import net.fullstackjones.bigbraincurrency.BigBrainCurrency;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;

public class BrainBankScreen extends AbstractContainerScreen<BrainBankMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BigBrainCurrency.MODID,"textures/gui/brainbank.png");
    private static final int TEXTURE_WIDTH = 176;
    private static final int TEXTURE_HEIGHT = 168;
    private static final int INVENTORY_LABEL_X = 8;
    private static final int INVENTORY_LABEL_Y = 74;
    private static final int TITLE_LABEL_X = 62;
    private static final int TITLE_LABEL_Y = 8;
    private static final int COUNTDOWN_X_OFFSET = 26;
    private static final int COUNTDOWN_Y_OFFSET = 39;

    public BrainBankScreen(BrainBankMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = TEXTURE_WIDTH;
        this.imageHeight = TEXTURE_HEIGHT;
        this.inventoryLabelX = INVENTORY_LABEL_X;
        this.inventoryLabelY = INVENTORY_LABEL_Y;
        this.titleLabelX = TITLE_LABEL_X;
        this.titleLabelY = TITLE_LABEL_Y;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, TEXTURE_WIDTH, TEXTURE_HEIGHT);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY,  0xFFFFFF, false);
        this.renderCountdownTimer(guiGraphics);
    }

    private void renderCountdownTimer(GuiGraphics guiGraphics) {
        LocalDateTime next = this.menu.blockEntity.nextDistributionAvailableAt();
        if (next == null) {
            Component disabledMessage = Component.translatable(
                    "gui.bigbraincurrency.brain_bank.distributions_disabled"
            );
            guiGraphics.drawCenteredString(
                    this.font,
                    disabledMessage,
                    this.titleLabelX + COUNTDOWN_X_OFFSET,
                    this.titleLabelY + COUNTDOWN_Y_OFFSET,
                    0xFF5555
            );
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(next)) {
            // Make coins appear and avoid rendering text.
            this.menu.checkDistributionReady();
            return;
        }

        Duration difference = Duration.between(now, next);
        long days = difference.toDays();
        long hours = difference.toHoursPart();
        long minutes = difference.toMinutesPart();
        long seconds = difference.toSecondsPart();
        String timerText = String.format("%02d : %02d : %02d : %02d", days, hours, minutes, seconds);

        guiGraphics.drawCenteredString(
            this.font,
            timerText,
            this.titleLabelX + COUNTDOWN_X_OFFSET,
            this.titleLabelY + COUNTDOWN_Y_OFFSET,
            0xFFFFFFFF
        );
    }
}
