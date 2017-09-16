package mdd.teamlines;

public class Team {
    private final int name;
    private final int slug;
    private final int league;
    private final int imageResource;

    public Team(int name, int slug, int author, int imageResource) {
        this.name = name;
        this.slug = slug;
        this.league = author;
        this.imageResource = imageResource;
    }

    public int getName() {
        return name;
    }

    public int getSlug() {
        return slug;
    }

    public int getLeague() {
        return league;
    }

    public int getImageResource() {
        return imageResource;
    }

}
