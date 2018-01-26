package com.kashdeya.tinyprogressions.items.misc;

import java.util.List;

import com.kashdeya.tinyprogressions.handlers.ConfigHandler;
import com.kashdeya.tinyprogressions.main.TinyProgressions;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MedKit extends Item {
	
	public MedKit() {
		this.setCreativeTab(TinyProgressions.tabTP);
		this.setUnlocalizedName("med_kit");
		this.setMaxStackSize(ConfigHandler.healStack);
	}
	
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayerMP entityplayer = (EntityPlayerMP)entityLiving;
	        CriteriaTriggers.CONSUME_ITEM.trigger(entityplayer, stack);
	        this.onFoodEaten(stack, worldIn, entityplayer);
	        worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.PLAYERS, 1.0F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
	        worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_HURT, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.7F);
	    }
		
		if (entityLiving instanceof EntityPlayer && !((EntityPlayer)entityLiving).capabilities.isCreativeMode) {
			stack.shrink(1);
	    }
		return stack;
	}
	
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		if (ConfigHandler.enableRegeneration){
			player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, ConfigHandler.healDuration * 20, ConfigHandler.healLevel, false, false));
		}
		if (ConfigHandler.healinstant){
		    player.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1 * 20, 0, false, false));
		}
	}
	  
	public int getMaxItemUseDuration(ItemStack stack) {
	    return ConfigHandler.useDuration;
	}
	  
	public EnumAction getItemUseAction(ItemStack stack) {
	    return EnumAction.BOW;
	}
	  
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		playerIn.setActiveHand(handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
	  
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(TextFormatting.YELLOW + new TextComponentTranslation("tooltip.medkit").getFormattedText());
	}
	
}