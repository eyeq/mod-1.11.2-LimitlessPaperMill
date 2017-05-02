package eyeq.limitlesspapermill;

import eyeq.limitlesspapermill.block.BlockPaperMill;
import eyeq.util.client.model.UModelLoader;
import eyeq.util.client.renderer.ResourceLocationFactory;
import eyeq.util.client.resource.ULanguageCreator;
import eyeq.util.client.resource.lang.LanguageResourceManager;
import eyeq.util.oredict.UOreDictionary;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.io.File;

import static eyeq.limitlesspapermill.LimitlessPaperMill.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
@Mod.EventBusSubscriber
public class LimitlessPaperMill {
    public static final String MOD_ID = "eyeq_limitlesspapermill";

    @Mod.Instance(MOD_ID)
    public static LimitlessPaperMill instance;

    private static final ResourceLocationFactory resource = new ResourceLocationFactory(MOD_ID);

    public static Block paperMill;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        addRecipes();
        if(event.getSide().isServer()) {
            return;
        }
        renderItemModels();
        createFiles();
    }

    @SubscribeEvent
    protected static void registerBlocks(RegistryEvent.Register<Block> event) {
        paperMill = new BlockPaperMill().setUnlocalizedName("paperMill").setCreativeTab(CreativeTabs.DECORATIONS);

        GameRegistry.register(paperMill, resource.createResourceLocation("tissue_box"));
    }

    @SubscribeEvent
    protected static void registerItems(RegistryEvent.Register<Item> event) {
        GameRegistry.register(new ItemBlock(paperMill), paperMill.getRegistryName());
    }

    public static void addRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(paperMill),
                "PPP", "TCT", "TTT",
                'P', UOreDictionary.OREDICT_PAPER, 'T', Blocks.TNT, 'C', UOreDictionary.OREDICT_CHEST));
    }

	@SideOnly(Side.CLIENT)
    public static void renderItemModels() {
        UModelLoader.setCustomModelResourceLocation(paperMill);
    }

    public static void createFiles() {
        File project = new File("../1.11.2-LimitlessPaperMill");

        LanguageResourceManager language = new LanguageResourceManager();

        language.register(LanguageResourceManager.EN_US, paperMill, "Tissue Box");
        language.register(LanguageResourceManager.JA_JP, paperMill, "テッシュ箱");

        ULanguageCreator.createLanguage(project, MOD_ID, language);
    }
}
