package com.github.asablock.mixin;

import com.github.asablock.UsefulEnchantmentsMod;
import com.github.asablock.enchantments.DEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowItem.class)
public class BowItemMixin {
    @Inject(method = "onStoppedUsing", at = @At(value = "INVOKE", shift = At.Shift.BEFORE,
            target = "Lnet/minecraft/entity/player/PlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;)V"))
    private void onStoppedUsing(ItemStack stack, World world, LivingEntity entity, int rut, CallbackInfo info) {
        if (!((DEnchantment) UsefulEnchantmentsMod.ARROW_RECYCLING_ENCHANTMENT).isDisabled()) {
            PlayerEntity user = (PlayerEntity) entity;
            ItemStack itemStack = user.getArrowType(stack);
            int infinityLevel = EnchantmentHelper.getLevel(Enchantments.INFINITY, stack);
            if (infinityLevel > 0) {
                int level = EnchantmentHelper.getLevel(UsefulEnchantmentsMod.ARROW_RECYCLING_ENCHANTMENT, stack);
                if (!(user.abilities.creativeMode || itemStack.isEmpty()) &&
                        user.getRandom().nextInt(100) < level * 20)
                    user.giveItemStack(new ItemStack(Items.ARROW));
            }
        }
    }
}
