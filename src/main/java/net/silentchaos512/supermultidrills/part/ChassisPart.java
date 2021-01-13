package net.silentchaos512.supermultidrills.part;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.silentchaos512.gear.api.item.GearType;
import net.silentchaos512.gear.api.part.IPartSerializer;
import net.silentchaos512.gear.api.part.PartType;
import net.silentchaos512.gear.gear.part.AbstractGearPart;
import net.silentchaos512.gear.gear.part.PartData;
import net.silentchaos512.supermultidrills.SuperMultiDrills;
import net.silentchaos512.supermultidrills.init.SmdItems;
import net.silentchaos512.supermultidrills.item.DrillChassisItem;
import net.silentchaos512.supermultidrills.item.DrillItem;
import net.silentchaos512.utils.Color;

import javax.annotation.Nullable;

public class ChassisPart extends AbstractGearPart {
    private static final ResourceLocation TYPE_ID = SuperMultiDrills.getId("chassis");
    public static final IPartSerializer<ChassisPart> SERIALIZER = new Serializer<>(TYPE_ID, ChassisPart::new);
    public static final PartType TYPE = PartType.create(PartType.Builder.builder(TYPE_ID));

    public ChassisPart(ResourceLocation partId) {
        super(partId);
    }

    @Override
    public PartType getType() {
        return TYPE;
    }

    @Override
    public IPartSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public int getColor(PartData part, ItemStack gear, int layer, int animationFrame) {
        return DrillItem.getChassisColor(gear);
    }

    @Override
    public void onAddToGear(ItemStack gear, PartData part) {
        int color = DrillChassisItem.getColor(part.getItem());
        DrillItem.setChassisColor(gear, color);
    }

    @Override
    public String getModelKey(PartData part) {
        int color = DrillChassisItem.getColor(part.getItem());
        return super.getModelKey(part) + "#" + Color.format(color);
    }

    @Override
    public ITextComponent getDisplayName(@Nullable PartData part, ItemStack gear) {
        int color = DrillItem.getChassisColor(gear);
        if (color != Color.VALUE_WHITE) { // This gets called twice for some reason? First time also returns #FFFFFF
            // Append the chassis color for convenience
            return super.getDisplayName(part, gear).deepCopy()
                    .append(new StringTextComponent(" (" + Color.format(color) + ")")
                            .mergeStyle(TextFormatting.DARK_GRAY));
        }
        return super.getDisplayName(part, gear);
    }

    @Override
    public PartData randomizeData(GearType gearType, int tier) {
        // Add a random color to the chassis
        ItemStack chassis = SmdItems.DRILL_CHASSIS.get().getStack(getRandomColor());
        return PartData.of(this, chassis);
    }

    @Override
    public boolean canAddToGear(ItemStack gear, PartData part) {
        return gear.getItem() instanceof DrillItem;
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
