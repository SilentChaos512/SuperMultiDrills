package net.silentchaos512.supermultidrills.part;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.gear.api.item.ICoreItem;
import net.silentchaos512.gear.api.parts.IPartPosition;
import net.silentchaos512.gear.api.parts.IPartSerializer;
import net.silentchaos512.gear.api.parts.IUpgradePart;
import net.silentchaos512.gear.api.parts.PartType;
import net.silentchaos512.gear.parts.AbstractGearPart;
import net.silentchaos512.gear.parts.PartData;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.init.ModItems;
import net.silentchaos512.supermultidrills.item.DrillChassisItem;
import net.silentchaos512.supermultidrills.item.DrillItem;
import net.silentchaos512.utils.Color;

import javax.annotation.Nullable;

public class ChassisPart extends AbstractGearPart implements IUpgradePart {
    private static final ResourceLocation TYPE_ID = SuperMultiDrills.getId("chassis");
    public static final PartType TYPE = PartType.create(TYPE_ID, new Serializer<>(TYPE_ID, ChassisPart::new), SilentGear.getId("chassis"));
    public static final IPartPosition POSITION = new IPartPosition() {
        @Override
        public String getTexturePrefix() {
            return "chassis";
        }

        @Override
        public String getModelIndex() {
            return "chassis";
        }
    };

    public ChassisPart(ResourceLocation partId) {
        super(partId);
    }

    @Override
    public PartType getType() {
        return TYPE;
    }

    @Override
    public IPartPosition getPartPosition() {
        return POSITION;
    }

    @Override
    public IPartSerializer<?> getSerializer() {
        return TYPE.getSerializer();
    }

    @Override
    public boolean isValidFor(ICoreItem gearItem) {
        return gearItem instanceof DrillItem;
    }

    @Override
    public boolean replacesExisting() {
        return true;
    }

    @Override
    public void onAddToGear(ItemStack gear, ItemStack part) {
        int color = DrillChassisItem.getColor(part);
        DrillItem.setChassisColor(gear, color);
    }

    @Override
    public int getColor(PartData part, ItemStack gear, int animationFrame) {
        return DrillItem.getChassisColor(gear);
    }

    @Override
    public ITextComponent getDisplayName(@Nullable PartData part, ItemStack gear) {
        int color = DrillItem.getChassisColor(gear);
        if (color != Color.VALUE_WHITE) { // This gets called twice for some reason? First time also returns #FFFFFF
            // Append the chassis color for convenience
            return super.getDisplayName(part, gear)
                    .appendSibling(new StringTextComponent(" (" + Color.format(color) + ")")
                            .applyTextStyle(TextFormatting.DARK_GRAY));
        }
        return super.getDisplayName(part, gear);
    }

    @Override
    public PartData randomizeData() {
        // Add a random color to the chassis
        ItemStack chassis = ModItems.drillChassis.getStack(getRandomColor());
        return PartData.of(this, chassis);
    }

    // TODO: Should probably make colors accessor in silent-utils
    private static final String[] RANDOM_COLORS = {"AliceBlue", "AntiqueWhite", "Aqua", "Aquamarine", "Azure", "Beige", "Bisque", "Black", "BlanchedAlmond", "Blue", "BlueViolet", "Brown", "BurlyWood", "CadetBlue", "Chartreuse", "Chocolate", "Coral", "CornflowerBlue", "Cornsilk", "Crimson", "Cyan", "DarkBlue", "DarkCyan", "DarkGoldenrod", "DarkGray", "DarkGreen", "DarkGrey", "DarkKhaki", "DarkMagenta", "DarkOliveGreen", "DarkOrange", "DarkOrchid", "DarkRed", "DarkSalmon", "DarkSeaGreen", "DarkSlateBlue", "DarkSlateGray", "DarkSlateGrey", "DarkTurquoise", "DarkViolet", "DeepPink", "DeepSkyBlue", "DimGray", "DodgerBlue", "FireBrick", "FloralWhite", "ForestGreen", "Fuchsia", "Gainsboro", "GhostWhite", "Gold", "Goldenrod", "Gray", "Green", "GreenYellow", "Grey", "Honeydew", "HotPink", "IndianRed", "Indigo", "Ivory", "Khaki", "Lavender", "LavenderBlush", "LawnGreen", "LemonChiffon", "LightBlue", "LightCoral", "LightCyan", "LightGoldenrodYellow", "LightGray", "LightGreen", "LightGrey", "LightPink", "LightSalmon", "LightSeaGreen", "LightSkyBlue", "LightSlateGray", "LightSlateGrey", "LightSteelBlue", "LightYellow", "Lime", "LimeGreen", "Linen", "Magenta", "Maroon", "MediumAquamarine", "MediumBlue", "MediumOrchid", "MediumPurple", "MediumSeaGreen", "MediumSlateBlue", "MediumSpringGreen", "MediumTurquoise", "MediumVioletRed", "MidnightBlue", "MintCream", "MistyRose", "Moccasin", "NavajoWhite", "Navy", "OldLace", "Olive", "OliveDrab", "Orange", "OrangeRed", "Orchid", "PaleGoldenrod", "PaleGreen", "PaleTurquoise", "PaleVioletRed", "PapayaWhip", "PeachPuff", "Peru", "Pink", "Plum", "PowderBlue", "Purple", "Rebeccapurple", "Red", "RosyBrown", "RoyalBlue", "SaddleBrown", "Salmon", "SandyBrown", "SeaGreen", "Seashell", "Sienna", "Silver", "SkyBlue", "SlateBlue", "SlateGray", "SlateGrey", "Snow", "SpringGreen", "SteelBlue", "Tan", "Teal", "Thistle", "Tomato", "Turquoise", "Violet", "Wheat", "White", "WhiteSmoke", "Yellow", "YellowGreen"};

    private static int getRandomColor() {
        // Select a random CSS color
        String colorName = RANDOM_COLORS[SuperMultiDrills.random.nextInt(RANDOM_COLORS.length)];
        Color color = Color.parse(colorName);
        return color.getColor() & 0xFFFFFF;
    }
}
