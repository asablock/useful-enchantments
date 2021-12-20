package com.github.asablock.mixin;

import com.github.asablock.UsefulEnchantmentsMod;
import com.github.asablock.enchantments.DEnchantment;
import com.github.asablock.enchantments.ObsidianWalkerEnchantment;
import com.github.asablock.mixinsupports.SoulSpeedSupporter;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.FrostWalkerEnchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntitMixin1 {
    @Redirect(method = "baseTick()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyMovementEffects(Lnet/minecraft/util/math/BlockPos;)V"))
    private void applyMovementEffects(LivingEntity entity, BlockPos pos) {
        LivinEntityAccessor ent = (LivinEntityAccessor) entity;
        int i = EnchantmentHelper.getEquipmentLevel(UsefulEnchantmentsMod.OBSIDIAN_WALKER_ENCHANTMENT, entity);
        if (i > 0 && !((DEnchantment) UsefulEnchantmentsMod.OBSIDIAN_WALKER_ENCHANTMENT).isDisabled()) {
            ObsidianWalkerEnchantment.coolingLava(entity, entity.world, pos, i);
        }
        int i2 = EnchantmentHelper.getEquipmentLevel(Enchantments.FROST_WALKER, entity);
        if (i2 > 0) {
            FrostWalkerEnchantment.freezeWater(entity, entity.world, pos, i2);
        }

        SoulSpeedSupporter.soulSpeedDetector(entity);
    }
}
