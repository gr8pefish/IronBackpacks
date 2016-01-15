package gr8pefish.ironbackpacks.items.backpacks;

public class TestSubItem {

    public int someInt;
    public String someString;
    public double someDouble;

    public MyObject(int someInt, String someString, double someDouble) {
        this.someInt = someInt;
        this.someString = someString;
        this.someDouble = someDouble;
    }

    public NBTTagCompound toNbt() {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setInteger("someInt", someInt);
        tagCompound.setString("someString", someString);
        tagCompound.setDouble("someDouble", someDouble);
        return tagCompound;
    }

    public static MyObject fromNbt(NBTTagCompound tagCompound) {
        if (tagCompound == null)
            tagCompound = new NBTTagCompound();

        int someInt = tagCompound.getInteger("someInt");
        String someString = tagCompound.getString("someString");
        double someDouble = tagCompound.getTagId("someDouble");

        return new MyObject(someInt, someString, someDouble);
    }
}
