package micdoodle8.mods.galacticraft.core.client.render.tile;

import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.tile.GCCoreTileEntityAluminumWire;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * GCCoreRenderAluminumWire.java
 * 
 * This file is part of the Galacticraft project
 * 
 * @author micdoodle8
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
@SideOnly(Side.CLIENT)
public class GCCoreRenderAluminumWire extends TileEntitySpecialRenderer
{
	private static final ResourceLocation aluminumWireTexture = new ResourceLocation(GalacticraftCore.ASSET_DOMAIN, "textures/model/cable.png");

	public final IModelCustom model;
	public final IModelCustom model2;

	public GCCoreRenderAluminumWire()
	{
		this.model = AdvancedModelLoader.loadModel("/assets/galacticraftcore/models/cable.obj");
		this.model2 = AdvancedModelLoader.loadModel("/assets/galacticraftcore/models/cable.obj");
	}

	public void renderModelAt(GCCoreTileEntityAluminumWire tileEntity, double d, double d1, double d2, float f)
	{
		// Texture file
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(GCCoreRenderAluminumWire.aluminumWireTexture);
		GL11.glPushMatrix();
		GL11.glTranslatef((float) d + 0.5F, (float) d1 + 0.5F, (float) d2 + 0.5F);
//		GL11.glScalef(1.0F, -1F, -1F);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		
		TileEntity[] adjecentConnections = WorldUtil.getAdjacentPowerConnections(tileEntity);

		// for (byte i = 0; i < 6; i++)
		// {
		// ForgeDirection side = ForgeDirection.getOrientation(i);
		// Vector3 tileVec = new Vector3(tileEntity);
		// TileEntity adjacentTile =
		// tileVec.modifyPositionFromSide(side).getTileEntity(tileEntity.worldObj);
		//
		// if (adjacentTile instanceof IConnector)
		// {
		// if (((IConnector) adjacentTile).canConnect(side.getOpposite()))
		// {
		// adjecentConnections.add(adjacentTile);
		// }
		// else
		// {
		// adjecentConnections.add(null);
		// }
		// }
		// else if (PowerConfigHandler.isIndustrialCraft2Loaded() &&
		// adjacentTile instanceof IEnergyTile)
		// {
		// if (adjacentTile instanceof IEnergyAcceptor)
		// {
		// if (((IEnergyAcceptor) adjacentTile).acceptsEnergyFrom(tileEntity,
		// side.getOpposite()))
		// {
		// adjecentConnections.add(adjacentTile);
		// }
		// else
		// {
		// if (adjacentTile instanceof IEnergySource && ((IEnergyEmitter)
		// adjacentTile).emitsEnergyTo(tileEntity, side.getOpposite()))
		// {
		// adjecentConnections.add(adjacentTile);
		// }
		// else
		// {
		// adjecentConnections.add(null);
		// }
		// }
		// }
		// else
		// {
		// adjecentConnections.add(adjacentTile);
		// }
		// }
		// else if (PowerConfigHandler.isBuildcraftLoaded() && adjacentTile
		// instanceof IPowerReceptor)
		// {
		// adjecentConnections.add(adjacentTile);
		// }
		// else
		// {
		// adjecentConnections.add(null);
		// }
		// }

		int metadata = tileEntity.worldObj.getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);

		IModelCustom model = null;

		if (metadata == 0)
		{
			model = this.model;
		}
		else
		{
			model = this.model2;
		}

		if (adjecentConnections[0] != null)
		{
			model.renderPart("ringDown");
		}

		if (adjecentConnections[1] != null)
		{
			model.renderPart("ringUp");
		}

		GL11.glPushMatrix();
		GL11.glScalef(1.0F, 1.0F, 1.0F + (float)Math.sin(tileEntity.ticks / 5.0F) / 5.0F);
		GL11.glRotatef(tileEntity.ticks * 22.5F, 0, 0, 1);
		
		if (adjecentConnections[2] != null)
		{
			model.renderPart("ringWest");
		}

		if (adjecentConnections[3] != null)
		{
			model.renderPart("ringEast");
		}
		
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glScalef(1.0F + (float)Math.sin(tileEntity.ticks / 5.0F) / 5.0F, 1.0F, 1.0F);
		GL11.glRotatef(tileEntity.ticks * 22.5F, 1, 0, 0);
		
		if (adjecentConnections[4] != null)
		{
			model.renderPart("ringSouth");
		}

		if (adjecentConnections[5] != null)
		{
			model.renderPart("ringNorth");
		}
		
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glRotatef((tileEntity.ticks * 5) % 360, 0, 1, 0);
		GL11.glTranslatef(0, (float)Math.cos(tileEntity.ticks / 5.0F) / 15.0F, 0);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		model.renderPart("center");
		GL11.glPopMatrix();
		
		if (Math.abs((float)Math.cos(tileEntity.ticks / 5.0F) / 5.0F) < 0.1F)
		{
			if (adjecentConnections[0] != null)
			{
				model.renderPart("mainDown");
			}

			if (adjecentConnections[1] != null)
			{
				model.renderPart("mainUp");
			}

			if (adjecentConnections[2] != null)
			{
				model.renderPart("mainWest");
			}

			if (adjecentConnections[3] != null)
			{
				model.renderPart("mainEast");
			}
			
			if (adjecentConnections[4] != null)
			{
				model.renderPart("mainSouth");
			}

			if (adjecentConnections[5] != null)
			{
				model.renderPart("mainNorth");
			}
		}
		
		
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double var2, double var4, double var6, float var8)
	{
		this.renderModelAt((GCCoreTileEntityAluminumWire) tileEntity, var2, var4, var6, var8);
	}
}
