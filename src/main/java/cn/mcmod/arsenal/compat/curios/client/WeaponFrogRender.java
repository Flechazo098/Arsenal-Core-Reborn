
package cn.mcmod.arsenal.compat.curios.client;

import cn.mcmod.arsenal.api.IDrawable;
import cn.mcmod.arsenal.item.WeaponFrogItem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

@OnlyIn(Dist.CLIENT)
public class WeaponFrogRender implements ICurioRenderer {
    public WeaponFrogRender() {
    }

    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemStack swordStack = WeaponFrogItem.getInventory(stack).getStackInSlot(0);
        if (swordStack.getItem() instanceof IDrawable sword) {
            this.renderItem(sword.getSheath(swordStack), matrixStack, renderTypeBuffer, light, slotContext.entity());
        }
    }

    public void renderItem(ItemStack item, PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, LivingEntity livingEntity) {
        matrixStack.pushPose();
        ICurioRenderer.translateIfSneaking(matrixStack, livingEntity);
        ICurioRenderer.rotateIfSneaking(matrixStack, livingEntity);
        matrixStack.translate((double)0.0F, (double)-0.34375F, (double)0.0F);
        matrixStack.mulPose(Axis.XP.rotationDegrees(180.0F));
        matrixStack.scale(0.625F, -0.625F, -0.625F);
        Minecraft.getInstance().getItemRenderer().renderStatic(livingEntity, item, ItemDisplayContext.HEAD, false, matrixStack, renderTypeBuffer, livingEntity.level(), light, LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F), livingEntity.getId());
        matrixStack.popPose();
    }
}