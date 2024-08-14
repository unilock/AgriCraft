package com.agricraft.agricraft.mixin;

import net.neoforged.neoforge.data.loading.DatagenModLoader;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vectorwing.farmersdelight.common.event.CommonModBusEvents;

@Mixin(CommonModBusEvents.class)
public class FarmersDelightMixin {

	@Inject(method = "onModifyDefaultComponents(Lnet/neoforged/neoforge/event/ModifyDefaultComponentsEvent;)V", at = @At("HEAD"), cancellable = true)
	private static void fixDatagenCrash(ModifyDefaultComponentsEvent event, CallbackInfo ci) {
		// TODO: @Ketheroth remove that latter. this is used to fix a crash with farmer's delight 1.2.4 when running in datagen
		if (DatagenModLoader.isRunningDataGen()) {
			ci.cancel();
		}
	}
}
