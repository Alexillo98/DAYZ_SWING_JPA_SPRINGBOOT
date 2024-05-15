package dayz.API;

public class flagsName {
    Flags flags;
    Name name;

    public flagsName() {
    }

    public flagsName(Flags flags, Name name) {
        this.flags = flags;
        this.name = name;
    }

    public Flags getFlags() {
        return flags;
    }

    public void setFlags(Flags flags) {
        this.flags = flags;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}
