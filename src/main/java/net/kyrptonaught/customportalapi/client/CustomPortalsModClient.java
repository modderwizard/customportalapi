package net.kyrptonaught.customportalapi.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.kyrptonaught.customportalapi.CustomPortalApiRegistry;
import net.kyrptonaught.customportalapi.CustomPortalBlock;
import net.kyrptonaught.customportalapi.CustomPortalsMod;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.DyeColor;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class CustomPortalsModClient implements ClientModInitializer {
    public static final ParticleType<BlockStateParticleEffect> CUSTOMPORTALPARTICLE = Registry.register(Registry.PARTICLE_TYPE, CustomPortalsMod.MOD_ID + ":customportalparticle", FabricParticleTypes.complex(BlockStateParticleEffect.PARAMETERS_FACTORY));

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(CustomPortalsMod.portalBlock, RenderLayer.getTranslucent());
        ColorProviderRegistryImpl.BLOCK.register((state, world, pos, tintIndex) -> {
            if (world != null && pos != null) {
                Block block = CustomPortalBlock.getPortalBase(world, pos);
                if (CustomPortalApiRegistry.portals.containsKey(block))
                    return CustomPortalApiRegistry.portals.get(block).colorID;
            }
            return DyeColor.BLACK.getMaterialColor().color;
        }, CustomPortalsMod.portalBlock);
        ParticleFactoryRegistry.getInstance().register(CUSTOMPORTALPARTICLE, CustomPortalParticle.Factory::new);
    }
}