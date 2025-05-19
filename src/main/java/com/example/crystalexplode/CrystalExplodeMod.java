package com.example.crystalexplode;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("crystalexplode")
public class CrystalExplodeMod {

    public CrystalExplodeMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onCrystalPlaced(EntityJoinLevelEvent event) {
        if (!(event.getEntity() instanceof EndCrystal crystal)) return;
        if (event.getLevel().isClientSide()) return;

        Level level = event.getLevel();
        ServerLevel serverLevel = (ServerLevel) level;
        BlockPos pos = crystal.blockPosition();

        // Delay to ensure crystal is fully initialized
        serverLevel.getServer().execute(() -> {
            // Create explosion at crystal position
            level.explode(null, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 6.0F, Level.ExplosionInteraction.MOB);
            crystal.discard();
        });
    }
}